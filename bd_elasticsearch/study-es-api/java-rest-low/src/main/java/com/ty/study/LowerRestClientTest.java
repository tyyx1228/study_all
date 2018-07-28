package com.ty.study;

import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

/**
 * 测试elasticsearch-java-rest-lower-api
 *
 * @author relax tongyu
 * @create 2018-07-17 15:20
 **/
@Log4j2
public class LowerRestClientTest {


    /*  全局排序分页  添加其他条件过滤时写法类似 */
    /* post http://10.10.1.60:9200/warn_event/point_warn/_search */
/*    {
        "from": 91864,
        "size": 10,
        "query": {
            "bool": {
                "must": [
                    {
                        "match_all": {}
                    }
                ],
                "must_not": [],
                "should": []
            }
        },
        "sort": [
            {
                "w_start_time": {
                    "order": "desc"
                }
            }
        ]
    }*/
    @Test
    public void pageAndSortByTime() throws IOException {
        String stringJson =
                "{" +
                    "\"from\":0," +
                    "\"size\":10," +
                    "\"query\":{" +
                        "\"bool\":{" +
                            "\"must\":[" +
                                "{\"match_all\":{}}" +
                            "]," +
                            "\"must_not\":[]," +
                            "\"should\":[]" +
                        "}" +
                    "}," +
                    "\"sort\":[" +
                        "{" +
                            "\"w_start_time\":{\"order\":\"desc\"}" +
                        "}" +
                    "]" +
                "}";
        HttpEntity requestEntity = new NStringEntity(stringJson, ContentType.APPLICATION_JSON);
        Map<String, String> params = Collections.emptyMap();
        Response response = restClient.performRequest("POST", "warn_event/point_warn/_search", params, requestEntity);
        print(response);
    }


    /**
     *
     * @throws IOException
     */
    @Test
    public void getData() throws IOException {
        Response response = restClient.performRequest("GET", "warn_event/point_warn/_search");
        print(response);
    }

    /**
     * 查看elasticsearch集群基本信息
     * @throws IOException
     */
    @Test
    public void test01() throws IOException {
        Response response = restClient.performRequest("GET", "/");
        print(response);
    }


    private RestClient restClient;
    @Before
    public void before() {
        RestClientBuilder builder = RestClient.builder(new HttpHost("10.10.1.60", 9200, "http"));
        /*Header[] defaultHeaders = new Header[]{new BasicHeader("header", "value")};
        builder.setDefaultHeaders(defaultHeaders);*/
        restClient = builder.build();
    }
    @After
    public void after(){
        try {
            restClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印响应体
     * @param response
     * @throws IOException
     */
    private void print(Response response) throws IOException {

        log.info(EntityUtils.toString(response.getEntity()));
    }
}
