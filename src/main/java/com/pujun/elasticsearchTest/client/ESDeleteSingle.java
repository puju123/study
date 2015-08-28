package com.pujun.elasticsearchTest.client;

import java.util.List;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.Client;

public class ESDeleteSingle implements ESDelete {
	private Client client= new ESClientSingle().getClient();
	@Override
	public void deleteSingle(String indices, String type, String id) {
		DeleteResponse response=client.prepareDelete(indices, type, id)
				.setOperationThreaded(false)
				.execute()
				.actionGet();
        System.out.println("索引被刪除="+response.isFound());
	}

	@Override
	public void deleteMulti(List<? extends DeleteRequest> requestsList) {
		BulkRequestBuilder bulkRequest=client.prepareBulk();
		int size=requestsList.size();
		for (int i = 0; i < size; i++) {
			bulkRequest.add(requestsList.get(i));
			System.out.println("delete id="+requestsList.get(i).id());
		}
		BulkResponse bulkResponse=bulkRequest.execute().actionGet();
		if (bulkResponse.hasFailures()) {
			System.out.println("执行出错："+bulkResponse.buildFailureMessage());
		}
	}
	/**
	  *
	  *Author Pujun
	  *Date 2015年8月25日
	  *
	*/
}
