package com.pujun.elasticsearchTest.client;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;

public class YazuoBulkProcessor {
    public static BulkProcessor processor=null;
	/**
	  *
	  *Author Pujun
	  *Date 2015年8月25日
	  *
	*/
    @Inject
    public YazuoBulkProcessor(Client client) {
		initProcessor(client);
	}
	/**
	 * @return the processor
	 */
	public void initProcessor(Client client) {
		if (processor==null) {
			processor=BulkProcessor.builder(client, new YazuoBuilkListener())
					.setBulkActions(-1)
					.setBulkSize(new ByteSizeValue(1, ByteSizeUnit.GB))
					.setFlushInterval(TimeValue.timeValueMillis(100))
					.setConcurrentRequests(1)
					.build();
		}
	}
	public void executeIndex(List<? extends IndexRequest> requestList) {
		if (requestList==null||requestList.size()==0) {
			System.out.println("wrong parameter!");
		}
		int size=requestList.size();
		for (int i = 0; i < size; i++) {
			processor.add(requestList.get(i));
			System.out.println("id="+requestList.get(i).id()+" source="+requestList.get(i).source().toString());
		}
		try {
			processor.awaitClose(1000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void executeDelete(List<? extends DeleteRequest> requestList) {
		if (requestList==null||requestList.size()==0) {
			System.out.println("wrong parameter!");
		}
		int size=requestList.size();
		for (int i = 0; i < size; i++) {
			processor.add(requestList.get(i));
		}
		
	}
}
