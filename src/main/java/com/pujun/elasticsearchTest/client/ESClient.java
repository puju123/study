package com.pujun.elasticsearchTest.client;

import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;

public interface ESClient {
    Client getClient(String clusterName,String server);
    Client getClient();
    void shutDown();
}
