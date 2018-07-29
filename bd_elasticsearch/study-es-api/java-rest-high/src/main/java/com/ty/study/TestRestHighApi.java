package com.ty.study;

import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * elasticsearch java rest high-api
 * 这种api的编程风格采用了类似transport-api的风格，但是这种api底层用的却是http方式与elasticsearch交互的
 * 官方目前主推这种api，而且transport-api有可能将在elasticsearch7中删除
 */
@Log4j2
public class TestRestHighApi {



    /**
     * document：https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.1/java-rest-high-document-index.html#_optional_arguments_2
     * Document source provided as Object key-pairs, which gets converted to JSON format
     */
    public void indexAPI_04(){
        IndexRequest indexRequest = new IndexRequest("posts", "doc", "1")
                .source("user", "kimchy",
                        "postDate", new Date(),
                        "message", "trying out Elasticsearch");
    }


    /**
     * document：https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.1/java-rest-high-document-index.html#_optional_arguments_2
     * Document source provided as an XContentBuilder object, the Elasticsearch built-in helpers to generate JSON content
     */
    @Test
    public void indexAPI_03() throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.field("user", "kimchy");
            builder.field("postDate", new Date());
            builder.field("message", "trying out Elasticsearch");
        }
        builder.endObject();
        IndexRequest indexRequest = new IndexRequest("posts", "doc", "1")
                .source(builder);
    }


    /**
     * document：https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.1/java-rest-high-document-index.html#_optional_arguments_2
     * Document source provided as a Map which gets automatically converted to JSON format
     */
    @Test
    public void indexAPI_02(){
        Map<String, Object> jsonMap = new HashMap();
        jsonMap.put("user", "kimchy");
        jsonMap.put("postDate", new Date());
        jsonMap.put("message", "trying out Elasticsearch");
        IndexRequest indexRequest = new IndexRequest("posts", "doc", "1")
                .source(jsonMap);
    }



    /**
     * document：https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.1/java-rest-high-document-index.html#_optional_arguments_2
     *  An IndexRequest requires the following arguments:
     */
    @Test
    public void indexAPI_01(){
        IndexRequest request = new IndexRequest(
                "posts",
                "doc",
                "1");
        String jsonString = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        request.source(jsonString, XContentType.JSON);
    }



    private RestHighLevelClient client;
    @Before
    public void init(){
        client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("10.10.1.60", 9200, "http"),
                        new HttpHost("10.10.1.60", 9200, "http")));
    }

    @After
    public void destroy() throws IOException {
        if (client != null) {
            client.close();
        }
    }

}
