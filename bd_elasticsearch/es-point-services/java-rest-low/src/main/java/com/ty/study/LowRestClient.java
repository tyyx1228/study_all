package com.ty.study;

import lombok.extern.log4j.Log4j2;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * elasticsearch低级API
 *
 * @author relax tongyu
 * @create 2018-07-17 10:43
 **/
@Log4j2
public class LowRestClient {

    //作为工具作用
    private static RestClientBuilder builder;
    static {
        builder = RestClient.builder(new HttpHost("10.10.1.60", 9200, "http"));
    }
    public static RestClient getRestClient(){
        return builder.build();
    }



    public static void main(String[] args) throws IOException {

        // https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.1/java-rest-low-usage-initialization.html
        RestClient restClient = getRestClient();

        Response response = restClient.performRequest("GET", "/");
        String s = EntityUtils.toString(response.getEntity());
        log.info(s);
        restClient.close();

        RestClient restClient1 = getRestClient();
        Response response1 = restClient1.performRequest("GET", "/");
        String e = EntityUtils.toString(response1.getEntity());
        log.info(s);
        restClient1.close();

    }

}
