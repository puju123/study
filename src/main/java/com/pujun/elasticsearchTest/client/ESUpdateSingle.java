package com.pujun.elasticsearchTest.client;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pujun.util.JsonUtils;

public class ESUpdateSingle implements ESUpdate{
	Client client=new ESClientSingle().getClient();
	@Override
//	public void updateSingle(String indices, String type, String id, Object content) {
//		String json=null;
//		try {
//			json=JsonUtils.writeValueAsString(content);
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		UpdateResponse response=client.prepareUpdate(indices, type, id).setDoc(json).get();
//		System.out.println("更新成功，创建新纪录="+response.isCreated());
//	}
    public void updateSingle(String indices, String type, String id, Object content) {
		String json=getJson(content);
		UpdateRequest request=new UpdateRequest(indices,type,id).doc(json);
		UpdateResponse response;
		try {
			response = client.update(request).get();
			System.out.println("更新成功，创建新纪录="+response.isCreated());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	private String getJson(Object content) {
		String json=null;
		try {
			json=JsonUtils.writeValueAsString(content);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	@Override
	public void updateMulti(List<String> json) {
		// TODO Auto-generated method stub
		
	}
    /*
     * 
    * <p>Title: upSertSingle</p> 
    * <p>Description: 更新索引，如果doc存在就更新updaterequest中内容，如果不存在则插入indexrequest中内容。</p> 
    * @param indices
    * @param type
    * @param id
    * @param search 查询条件
    * @param content 更新内容
    * @see com.pujun.elasticsearchTest.client.ESUpdate#upSertSingle(java.lang.String, java.lang.String, java.lang.String, java.lang.Object, java.lang.Object)
     */
	@Override
	public void upSertSingle(String indices, String type, String id,Object search, Object content) {
		String json=getJson(content);
		String searchStr=getJson(search);
		IndexRequest indexRequest=new IndexRequest(indices, type, id).source(searchStr);
		UpdateRequest request=new UpdateRequest(indices,type,id).doc(json).upsert(indexRequest);
		UpdateResponse response;
		try {
			response = client.update(request).get();
			System.out.println("更新成功，创建新纪录="+response.isCreated());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void upSertMulti(List<String> json) {
		// TODO Auto-generated method stub
		
	}
	/**
	  *
	  *Author Pujun
	  *Date 2015年8月25日
	  *
	*/
}
