package com.pujun.elasticsearchTest.client;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.lang3.StringUtils;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pujun.util.JsonUtils;

public class ESIndexSingle implements ESIndex {
	/**
	  *
	  *Author Pujun
	  *Date 2015年8月25日
	  *
	*/

    private Client client= new ESClientSingle().getClient();
	
    @Override
	public void indexSingle(String indices, String type, String id, Object content) {
		if (client==null) {
			System.out.println("连接服务器失败！");
		}
		if (StringUtils.isBlank(indices)||StringUtils.isBlank(type)) {
			System.out.println("参数indices或者type为空！");
		}
		String json=null;
		if (content!=null) {
			try {
				json=JsonUtils.writeValueAsString(content);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		IndexResponse response=null;
		if (StringUtils.isBlank(id)) {
			//如果id为空则自动生成id
			response=client.prepareIndex(indices, type).setSource(json).execute().actionGet();
		}else {
			response=client.prepareIndex(indices, type, id).setSource(json).execute().actionGet();
		}
		if (response!=null) {
			System.out.println("id="+response.getId());
			System.out.println("type="+response.getType());
			System.out.println("version="+response.getVersion());
			System.out.println("isCreated="+response.isCreated());
	}
    }
	@Override
	public void indexMulti(List<? extends IndexRequest> requestsList) {
//        YazuoBulkProcessor processor=new YazuoBulkProcessor(client);
//        processor.executeIndex(requestsList);
		BulkRequestBuilder bulkRequest=client.prepareBulk();
		int size=requestsList.size();
		for (int i = 0; i < size; i++) {
			bulkRequest.add(requestsList.get(i));
			System.out.println("index id="+requestsList.get(i).id()+" source="+requestsList.get(i).source().toString());
		}
		BulkResponse bulkResponse=bulkRequest.execute().actionGet();
		if (bulkResponse.hasFailures()) {
			System.out.println("执行出错："+bulkResponse.buildFailureMessage());
		}
	}

}
