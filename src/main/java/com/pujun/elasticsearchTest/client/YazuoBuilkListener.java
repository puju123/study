package com.pujun.elasticsearchTest.client;

import java.util.Iterator;

import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkProcessor.Listener;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;

public class YazuoBuilkListener implements Listener {


	@Override
	public void beforeBulk(long executionId, BulkRequest request) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
		Iterator<BulkItemResponse> iterator=response.iterator();
		while (iterator.hasNext()) {
			BulkItemResponse bulkItemResponse=iterator.next();
			System.out.println("执行成功：index="+bulkItemResponse.getIndex()+" type="+bulkItemResponse.getType()+" id="+bulkItemResponse.getId());
		}
	}

	@Override
	public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
		System.err.println("执行失败："+failure.getMessage());
	}
	/**
	  *
	  *Author Pujun
	  *Date 2015年8月25日
	  *
	*/
}
