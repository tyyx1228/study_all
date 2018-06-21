package com.ty.study.coprocessor.test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class HPointNameIndexCoprocessorTest {

    @Test
    public void bufferSave(){
        Put put = new Put(Bytes.toBytes("00798194748344745660304670"));
        put.addColumn(Bytes.toBytes("POINT_INFO"), Bytes.toBytes("NAME"), Bytes.toBytes("NMDH:S70AN001CT"));
        put.addColumn(Bytes.toBytes("POINT_INFO"), Bytes.toBytes("TIMESTAMP"), Bytes.toBytes("2018/03/12 00:22:21"));
        put.addColumn(Bytes.toBytes("POINT_INFO"), Bytes.toBytes("VALUE"), Bytes.toBytes(new BigDecimal(1.23)));
        put.addColumn(Bytes.toBytes("POINT_INFO"), Bytes.toBytes("STR_VALUE"), null);
        put.addColumn(Bytes.toBytes("POINT_INFO"), Bytes.toBytes("FLAG"), Bytes.toBytes(true));

        ArrayList<Put> puts = new ArrayList<Put>();
        puts.add(put);

        Configuration hconf = HBaseConfiguration.create();
        try{
            Connection hconn = ConnectionFactory.createConnection(hconf);
            Table table = hconn.getTable(TableName.valueOf("NS_SY:H_POINT"));
            BufferedMutator.ExceptionListener listener = new BufferedMutator.ExceptionListener() {
                @Override
                public void onException(RetriesExhaustedWithDetailsException e, BufferedMutator mutator) {
                    int numExceptions = e.getNumExceptions();
                    for (int i = 0; i < numExceptions; i++) {
                        System.out.println("Failed to sent put " + e.getRow(i) + ".");
                        // logger.error("Failed to sent put " + e.getRow(i) + ".");
                    }
                }
            };
            BufferedMutatorParams params = new BufferedMutatorParams(TableName.valueOf("NS_SY:H_POINT")).listener(listener);
            params.writeBufferSize(5 * 1024 * 1024);

            BufferedMutator mutator = hconn.getBufferedMutator(params);
            mutator.mutate(puts);
            mutator.flush();
            hconn.close();
        }catch (IOException e) {
            e.printStackTrace();
        }





    }

    @Test
    public void tablePut(){
        Configuration hconf = HBaseConfiguration.create();
        try {
            Connection hconn = ConnectionFactory.createConnection(hconf);

            Table table = hconn.getTable(TableName.valueOf("NS_SY:H_POINT"));
            Put put = new Put(Bytes.toBytes("02201803021205001234567890"));
            put.addColumn(Bytes.toBytes("POINT_INFO"), Bytes.toBytes("NAME"), Bytes.toBytes("ABDDD"));
            put.addColumn(Bytes.toBytes("POINT_INFO"), Bytes.toBytes("TIMESTAMP"), Bytes.toBytes("2018/03/12 00:22:21"));
            put.addColumn(Bytes.toBytes("POINT_INFO"), Bytes.toBytes("VALUE"), Bytes.toBytes(new BigDecimal(1.23)));
            put.addColumn(Bytes.toBytes("POINT_INFO"), Bytes.toBytes("STR_VALUE"), null);
            put.addColumn(Bytes.toBytes("POINT_INFO"), Bytes.toBytes("FLAG"), Bytes.toBytes(true));
            table.put(put);
            hconn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}