package com.ty.study;

import com.ty.study.client.ElasticsearchTransportClientHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.UnknownHostException;

/**
 * 增删查 API
 *
 * @author relax tongyu
 * @create 2018-03-23 16:47
 **/
public class TestCrudApi {
    private Logger log;
    private TransportClient client;

    /**
     * 1. 根据ID查询
     */
    @Test
    public void testGetAPI() {
        GetResponse response = client.prepareGet("twitter", "tweet", "1").get();
        printResponse(response);
    }

    /**
     * 2. 根据ID删除文档
     */
    @Test
    public void testDeleteById(){
        DeleteResponse response = client.prepareDelete("twitter", "tweet", "1").get();
        log.info(response);
    }


    /**
     * 3. 根据条件删除文档
     */
    @Test
    public void testDeleteBysearch(){
        BulkByScrollResponse response = DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
                .filter(QueryBuilders.matchQuery("gender", "male"))
                .source("persons")
                .get();
        long deleted = response.getDeleted();
    }

    /**
     * 4. 根据条件异步删除文档
     */
    @Test
    public void testDeleteBySearchAndSyncr(){
        DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
                .filter(QueryBuilders.matchQuery("gender", "male"))
                .source("persons")
                .execute(new ActionListener<BulkByScrollResponse>() {
                    @Override
                    public void onResponse(BulkByScrollResponse response) {
                        long deleted = response.getDeleted();
                    }
                    @Override
                    public void onFailure(Exception e) {
                        // Handle the exception
                    }
                });
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
    public void printResponse(GetResponse response){
        String sourceAsString = response.getSourceAsString();
        log.info(sourceAsString);
    }

}
