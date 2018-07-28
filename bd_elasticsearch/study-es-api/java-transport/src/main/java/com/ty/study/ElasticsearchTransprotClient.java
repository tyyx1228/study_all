package com.ty.study;

import com.ty.study.client.ElasticsearchTransportClientHolder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.node.DiscoveryNode;

import java.util.List;

/**
 * Transport 客户端，不加入集群
 *
 * @author relax tongyu
 * @create 2018-03-23 10:39
 **/
public class ElasticsearchTransprotClient {

    public static void main(String[] args) throws Exception {

        TransportClient client = new ElasticsearchTransportClientHolder().getClient();
        List<DiscoveryNode> discoveryNodes = client.connectedNodes();
        System.out.println(discoveryNodes.size());

        client.close();
    }
}
