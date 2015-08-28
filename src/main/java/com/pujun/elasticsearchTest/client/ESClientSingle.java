package com.pujun.elasticsearchTest.client;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

public class ESClientSingle implements ESClient {
    private static TransportClient client=null;
    @Override
    public Client getClient(String clusterName,String server){
    	Settings settings=fetchSettings(clusterName);
    	client=new TransportClient(settings);
    	client.addTransportAddress(new InetSocketTransportAddress(server, 9300));
    	System.out.println("已经连接："+client.connectedNodes().size()+client.connectedNodes().toString());
		return client;
    }

	private Settings fetchSettings(String clusterName) {
    	return ImmutableSettings.settingsBuilder()
    			.put("client.transport.sniff", false)
    			.put("cluster.name", clusterName)
    			.build();
	}

	@Override
	public void shutDown() {
        if (client!=null) {
			client.close();
			System.out.println("关闭连接："+client.settings().get("cluster.name"));
		}		
	}

	@Override
	public Client getClient() {
    	Settings settings=fetchSettings("elasticsearch_pujun_fortest");
    	client=new TransportClient(settings);
    	client.addTransportAddress(new InetSocketTransportAddress("192.168.233.94", 9300));
    	System.out.println("已经连接："+client.connectedNodes().size()+client.connectedNodes().toString());
		return client;
	}
}
