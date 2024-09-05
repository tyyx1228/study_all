package com.ty.study.coprocessor;

import com.google.common.collect.Lists;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.coprocessor.BaseRegionObserver;
import org.apache.hadoop.hbase.coprocessor.ObserverContext;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.regionserver.*;
import org.apache.hadoop.hbase.regionserver.wal.WALEdit;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutput;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * rowkey按测点名称一次排序，按时间二次排序，索引测点数据
 *
 * rowkey示例
 * 转换前
 *      00798194748344745660304670
 * 转换后
 *      00_N5660304670_A00000000000000
 *      00_N5660304670_D79819474834474
 *      00_N5660304670_Z99999999999999
 *
 * 打包请注意：使用mvn package -DskipTests
 * @author relax tongyu
 * @create 2018-05-24 10:36
 **/
public class HPointNameIndexCoprocessor extends BaseRegionObserver {
    private static Logger logger = LoggerFactory.getLogger(HPointNameIndexCoprocessor.class);

    private String NAME_SIGNAL = "_N";
    private String TIME_SIGNAL = "_D";
    private String TIME_SIGNAL_INIT_START = "_A00000000000000";
    private String TIME_SIGNAL_INIT_END = "_Z99999999999999";

    private static String registerTblName;
    private static String[] registerTblCol;
    private static String registerTblFamily;

    private static String tagetTblName;

    private static String tagetTblFamily;

    private static Configuration hconf = HBaseConfiguration.create();
    private static Properties htableInfo = new Properties();
    private static Connection hconn;
    static {
        try {
            InputStream in = HPointNameIndexCoprocessor.class.getClassLoader().getResourceAsStream("table-info.properties");
            htableInfo.load(in);
            registerTblName = htableInfo.get("name.coprocessor.register.table.name").toString().trim();
            registerTblFamily = htableInfo.get("name.coprocessor.register.table.family").toString().trim();
            registerTblCol = htableInfo.get("name.coprocessor.register.table.columns").toString().trim().split("\\s*,\\s*");

            tagetTblName = htableInfo.get("name.coprocessor.index.table.name").toString().trim();
            tagetTblFamily = htableInfo.get("name.coprocessor.index.table.family").toString().trim();
            logger.info("正在初始化连接 ...");
//            hconn = ConnectionFactory.createConnection(hconf);
            logger.info("初始化连接完毕 ..." + hconn);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void initCellData(Table table, String rowkey) throws IOException {
        byte[] bRow = Bytes.toBytes(rowkey);
        for(String col: registerTblCol){
            Put put = new Put(bRow);
            put.addColumn(Bytes.toBytes(tagetTblFamily), Bytes.toBytes(col), null);
            table.put(put);
        }
    }

    private void checkInitData(Table table, String row){
        try {
            boolean exists = table.exists(new Get(Bytes.toBytes(row)));
            if(!exists){
                initCellData(table, row);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }


    @Override
    public void prePut(ObserverContext<RegionCoprocessorEnvironment> e, Put put, WALEdit edit, Durability durability) throws IOException {

        Table indexTbl = hconn.getTable(TableName.valueOf(tagetTblName));
        byte[] row = put.getRow();
        // 01 20180201120023 1234567890
        String rowkey = new String(row, "UTF-8");
        String regionNumber = rowkey.substring(0, 2);
        String timeNumber = rowkey.substring(2, 16);
        String nameNumber = rowkey.substring(16);

        String startRow = new StringBuffer().append(regionNumber).append(NAME_SIGNAL)
                .append(nameNumber).append(TIME_SIGNAL_INIT_START)
                .toString();
        String endRow = new StringBuffer().append(regionNumber).append(NAME_SIGNAL)
                .append(nameNumber).append(TIME_SIGNAL_INIT_END)
                .toString();

        checkInitData(indexTbl, startRow);
        checkInitData(indexTbl, endRow);

        String indexTblRow = new StringBuffer().append(regionNumber).append(NAME_SIGNAL)
                .append(nameNumber).append(TIME_SIGNAL)
                .append(timeNumber).toString();


        byte[] registerTblFamilyBtyes = Bytes.toBytes(registerTblFamily);
        byte[] tagetTblFamilyBytes = Bytes.toBytes(tagetTblFamily);
        Put indexPut = new Put(Bytes.toBytes(indexTblRow));

        for (String col : registerTblCol) {
            boolean has = put.has(registerTblFamilyBtyes, Bytes.toBytes(col));
            if(has){
                Cell cell = put.get(registerTblFamilyBtyes, Bytes.toBytes(col)).get(0);
                byte[] value = Bytes.copy(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                indexPut.addColumn(tagetTblFamilyBytes, Bytes.toBytes(col), value);
            }else{
                logger.warn("config column: " + registerTblFamily+ ":"+ col + " , not found !");
            }
        }
        indexTbl.put(indexPut);
        indexTbl.close();
    }

    @Override
    public void preBatchMutate(final ObserverContext<RegionCoprocessorEnvironment> ctx,
                               final MiniBatchOperationInProgress<Mutation> miniBatchOp) throws IOException {
        //

        HRegionServer rs = (HRegionServer) ctx.getEnvironment().getRegionServerServices();
        HRegionInfo regionInfo = ctx.getEnvironment().getRegionInfo();


        byte[] startKey = regionInfo.getStartKey();

        List<Region> indexTblRegions = rs.getOnlineRegions(TableName.valueOf("table name of index"));

        //查找index表的region
        Region indexRegion = null;
        for(Region r: indexTblRegions){
            if(Bytes.equals(startKey, r.getRegionInfo().getStartKey())){
                indexRegion = r;
                break;
            }
        }

        //为索引表生成数据
        Mutation[] indexMutations = new Mutation[miniBatchOp.size()];
        for(int i=0; i<miniBatchOp.size(); i++){
            WALEdit walEdit = miniBatchOp.getWalEdit(i);
            Mutation operation = miniBatchOp.getOperation(i);

            //判断WAL机制，索引表与原表保持一致
            Durability durability = operation.getDurability();


            if(operation instanceof Put){
                indexMutations[i] = operation;

            }else if(operation instanceof Delete){
                indexMutations[i] = operation;

            }
        }

        /*indexRegion.batchMutate(indexMutations, HConstants.NO_NONCE,
                HConstants.NO_NONCE);*/

    }


    public static void main(String[] args) {
        logger.info(String.valueOf(Integer.MAX_VALUE).length() + "");
        String s = "   NAME  , TIMESTAMP   , VALUE,   STR_VALUE  ,  FLAG  ".trim();
        List<String> strings = Arrays.asList(s.split("\\s*,\\s*"));
        strings.forEach(a-> logger.info("3"+a+"9"));
        String value=null;


        Put put = new Put(Bytes.toBytes("2311"));
        put.addColumn(Bytes.toBytes("INFO"),Bytes.toBytes("NAME"), Bytes.toBytes("ABCD"));
        Cell cell = put.get(Bytes.toBytes("INFO"), Bytes.toBytes("NAME")).get(0);
        String s1 = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
        byte[] bytes = Bytes.toBytes(s1);
        byte[] copy = Bytes.copy(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());

        logger.info(Bytes.toString(copy));

        logger.info(put.has(Bytes.toBytes("INFO"), Bytes.toBytes("NAME1"))+"");
    }
}
