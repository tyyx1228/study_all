package com.ty.study;

import com.ty.study.client.ElasticsearchTransportClientHolder;
import lombok.extern.log4j.Log4j2;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.joda.time.format.DateTimeFormat;

import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.google.common.collect.Lists.newArrayList;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * 向ES中导入 测点报警数据
 *
 * @author relax tongyu
 * @create 2018-06-13 11:07
 **/
@Log4j2
public class ImportPointWarnData {

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(32);
        try{
            ArrayList<PointWarnData> list = newArrayList();

            Iterator<String> lines = null;
            lines = Files.lines(Paths.get("d:/part-m-00012")).iterator();

            while (lines.hasNext()){
                PointWarnData pwd = parseLine(lines.next());
//            log.info(pwd.toString());
                if(pwd !=null){
                    list.add(pwd);
                }
                if(list.size()%300==0) {
                    pool.execute(new ImportTask(list));
                    list = newArrayList();
                }
            }

            if(list.size()>0 ){
                pool.execute(new ImportTask(list));
            }

        }catch (Exception e){

        }finally {
            pool.shutdown();
        }

    }






    public static PointWarnData parseLine(String line){
        try{
            line = line.replaceAll("\\\\N", "");
            PointWarnData pd = new PointWarnData();
            String[] sp = line.split(",");
            if(sp!=null){
                pd.setPointId(sp.length >= 2 ? sp[1] : null);
                pd.setKkscode(sp.length >=3 ? sp[2] : null);
                pd.setOrgId(sp.length >=4 ? sp[3] : null);
                pd.setWarnStartTime(sp.length >=5 ? parseDate(sp[4]) : null);
                pd.setWarnLevel(sp.length >=6 ? sp[5] : null);
                pd.setWarnValue(sp.length >=7 ? sp[6] : null);
                pd.setWarnDuration(sp.length >=13 ? sp[12] : null);
                pd.setStatus(sp.length >= 14 ? sp[13] : null);
                pd.setCost(sp.length >= 15 ? sp[14]: null);
                pd.setUnitStatus(sp.length >= 16 ? sp[15]: null);
                pd.setRid(sp.length >= 17 ? Long.parseLong(sp[16]) : null);
                pd.setWarnEndTime(sp.length >= 18 ? parseDate(sp[17]): null);
                pd.setServiceType(sp.length >=21 ? sp[20]: null);
                pd.setAssessment(sp.length >=23 ? sp[22] : null);
            }
            return pd;
        }catch (Exception e){
            return null;
        }
    }

    public static Date parseDate(String s){
        try {
            return DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS").parseDateTime(s).toDate();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

}

@Log4j2
class ImportTask implements Runnable{
        private List<PointWarnData> list;

        public ImportTask(List<PointWarnData> list){
            this.list = list;
        }

        @Override
        public void run() {
            ElasticsearchTransportClientHolder holder = new ElasticsearchTransportClientHolder();
            try(TransportClient client = holder.getClient()) {


                BulkRequestBuilder bulkRequest = client.prepareBulk();

                for(PointWarnData p : list){
                    XContentBuilder builder = jsonBuilder()
                            .startObject()
                            .field("pointId", p.getPointId())
                            .field("kkscode", p.getKkscode())
                            .field("orgId", p.getOrgId())
                            .field("warnStartTime", p.getWarnStartTime())
                            .field("warnLevel", p.getWarnLevel())
                            .field("warnValue", p.getWarnValue())
                            .field("warnDuration", p.getWarnDuration())
                            .field("status", p.getStatus())
                            .field("cost", p.getCost())
                            .field("unitStatus", p.getUnitStatus())
                            .field("rid", p.getRid())
                            .field("warnEndTime", p.getWarnEndTime())
                            .field("serviceType", p.getServiceType())
                            .field("assessment", p.getAssessment())
                            .endObject();

                    bulkRequest.add(
                            client.prepareIndex("point_warn", "itemList").setSource(builder)
                    );
                }
                BulkResponse bulkItemResponses = bulkRequest.get();
                log.info("条目数:{}, 状态： {}", bulkItemResponses.getItems().length, bulkItemResponses.status().getStatus());
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
