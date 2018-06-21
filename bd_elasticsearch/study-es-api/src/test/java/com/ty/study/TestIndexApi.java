package com.ty.study;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.ty.study.client.ElasticsearchTransportClientHolder;
import com.ty.study.document.TwitterUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.*;
import static org.elasticsearch.common.xcontent.XContentFactory.*;

/**
 * 测试文档序列化，同时建立索引
 *
 * 附：date format
 * https://www.elastic.co/guide/en/elasticsearch/reference/6.2/mapping-date-format.html#built-in-date-formats
 *
 * @author relax tongyu
 * @create 2018-03-23 11:11
 **/
public class TestIndexApi {
    private Logger log;
    private TransportClient client;


    /**
     * 1.文档格式采用手动书写json字符串
     */
    @Test
    public void testString(){
        //手动书写纯json文档
        String json = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";

/*        TwitterUser twitterUser = new TwitterUser("kimchy", new Date(), "trying out Elasticsearch");
        Gson gson = new Gson();
        String json = gson.toJson(twitterUser);*/


        //prepareIndex(...)方法：若指定的索引存不存在则会主动创建，相同ID的数据会被覆盖，并记录版本号
        IndexResponse response = client.prepareIndex("twitter", "tweet")
                .setSource(json, XContentType.JSON)
                .get();

        printResponse(response);

    }

    /**
     * 2. 使用Java Map数据结构存储数据到elasticsearch
     */
    @Test
    public void testJavaMap(){
        //使用map数据结构，数据会自动转为JSON
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("user","kimchy");
        json.put("postDate",new Date());
        json.put("message","trying out Elasticsearch");

        IndexResponse response = client.prepareIndex("twitter", "tweet")
                .setSource(json, XContentType.JSON)
                .get();
        printResponse(response);
    }

    /**
     * 3. 自定义javabean，借助jackson将其序列化
     */
    @Test
    public void testJackson(){
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("user","kimchy");
        json.put("postDate",new Date());
        json.put("message","trying out Elasticsearch");


        // instance a json mapper
        ObjectMapper mapper = new ObjectMapper(); // create once, reuse

        // generate json
        try {
            byte[] jackson = mapper.writeValueAsBytes(new TwitterUser("kimchy", new Date(), "trying out Elasticsearch"));

           // byte[] jackson = mapper.writeValueAsBytes(json); //本方式存时间（date），时间将转为long性数字
            IndexResponse response = client.prepareIndex("twitter", "tweet", "abs122222")
                    .setSource(jackson, XContentType.JSON)
                    .get();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 4. 采用Elasticsearch api自带的文档构建工具
     */
    @Test
    public void testElasticsearchProvide(){


        try {
            XContentBuilder builder = jsonBuilder()
                    .startObject()
                    .field("user", "kimchy")
                    .field("postDate", new Date())
                    .field("message", "trying out Elasticsearch")
                    .endObject();
            System.out.println(builder.string());

            IndexResponse response = client.prepareIndex("twitter", "tweet", "1")
                    .setSource(builder)
                    .get();

            printResponse(response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Before
    public void initClient() {
        try {
            log = LogManager.getLogger(this.getClass());
            client = new ElasticsearchTransportClientHolder().getClient();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
    @After
    public void destroy(){
        if(client!=null)
            client.close();
    }


    //打印响应信息
    public void printResponse(IndexResponse response){
        // Index name
        String index = response.getIndex();
        // Type name
        String type = response.getType();
        // Document ID (generated or not)
        String id = response.getId();
        // Version (if it's the first time you index this document, you will get: 1)
        long version = response.getVersion();
        // status has stored current instance statement.
        RestStatus status = response.status();
        log.info("_index={}, _type={}, _id={}, _version={}", index, type, id, version);
    }

}
