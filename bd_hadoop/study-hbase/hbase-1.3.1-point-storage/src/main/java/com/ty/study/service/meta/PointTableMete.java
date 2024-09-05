package com.ty.study.service.meta;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 测点表元数据维护
 * @author relax
 * @date 2018/8/6 11:31
 */
public class PointTableMete {
    private String tblNamePrefix = "H_POINT";

    private static Connection conn;
    private static Admin admin;

    private LoadingCache<String, Set<String>> tableFamilyMapper = CacheBuilder.newBuilder()
            .maximumSize(10000)  //设置缓存大小，这里指的是Cache中元素最多有1000个
            .expireAfterWrite(10, TimeUnit.SECONDS)  //设置Cache中每个元素的生命周期
            .build(
                    new CacheLoader<String, Set<String>>() {
                        @Override
                        public Set<String> load(String s) throws Exception {
                             return getTblFamily(s);
                        }
                    }
            );

    private LoadingCache<String, Boolean> tableExsitsMapper = CacheBuilder.newBuilder()
            .maximumSize(10000)  //设置缓存大小，这里指的是Cache中元素最多有1000个
            .expireAfterWrite(10, TimeUnit.SECONDS)  //设置Cache中每个元素的生命周期
            .build(
                    new CacheLoader<String, Boolean>() {
                        @Override
                        public Boolean load(String s) throws Exception {
                            return existTbl(s);
                        }
                    }
            );

    static {
        try {
            conn = ConnectionFactory.createConnection();
            admin = conn.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    //表名维护
    public void checkAndGenerTbl(String tblName) throws IOException {
        if(existTbl(tblName)) {

        }
    }

    //创建表
    public void createTbl(String tblName){
    }


    //列簇维护




    public String generTblNameByDate(Date dt){
        return tblNamePrefix+new DateTime(dt).year();
    }


    /**
     * 表是否存在
     * @param tblName
     * @return
     * @throws IOException
     */
    public boolean existTbl(String tblName) throws IOException {
        return admin.tableExists(TableName.valueOf(tblName));
    }

    /**
     * 查询表中的所有列族
     * @param tblName
     * @return
     * @throws IOException
     */
    public Set<String> getTblFamily(String tblName) throws IOException {
        HashSet<String> families = Sets.newHashSet();
        HTableDescriptor tableDescriptor = admin.getTableDescriptor(TableName.valueOf(tblName));
        tableDescriptor.getFamilies().forEach( f-> {
            families.add(f.getNameAsString());
        });
        return families;
    }


    /**
     * 表tblName中是否存在列族familyName
     * @param tblName
     * @param familyName
     * @return
     * @throws Exception
     */
    public boolean tblExistFamily(String tblName, String familyName) throws Exception {
        return tableFamilyMapper.get(tblName).contains(familyName);
    }

}
