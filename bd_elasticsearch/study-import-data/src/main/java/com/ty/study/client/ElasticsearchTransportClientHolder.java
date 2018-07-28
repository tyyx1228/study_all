package com.ty.study.client;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 *
 * Transport 客户端，不加入集群
 *
 * @author relax tongyu
 * @create 2018-03-23 11:01
 **/
public class ElasticsearchTransportClientHolder implements ElasticsearchClientHolder {

    public TransportClient getClient() throws UnknownHostException {
        /*
         *
         * client.transport.ignore_cluster_name
         * Set to true to ignore cluster name validation of connected nodes. (since 0.19.4)
         *
         * client.transport.ping_timeout
         *he time to wait for a ping response from a node. Defaults to 5s.
         *
         *client.transport.nodes_sampler_interval
         *How often to sample / ping the nodes listed and connected. Defaults to 5s.
         *
         * */
        //Settings.EMPTY会使用默认的集群名称‘elasticsearch’
        Settings settings = Settings.builder()
                .put("cluster.name", "elasticsearch")
                .put("client.transport.sniff", true).build();

        TransportClient client = new PreBuiltTransportClient(settings)
//                .addTransportAddress(new TransportAddress(InetAddress.getByName("host1"), 9300))
                .addTransportAddress(new TransportAddress(InetAddress.getByName("10.10.1.60"), 9300));
        List<DiscoveryNode> discoveryNodes = client.listedNodes();
        System.out.println(String.format("nodes size=%d, node list: %s", discoveryNodes.size(), discoveryNodes));
        return client;
    }


}
