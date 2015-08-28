package com.pujun.elasticsearchTest.client;

import java.util.List;

import org.elasticsearch.action.index.IndexRequest;

public interface ESIndex {
	void indexSingle(String indices,String type,String id,Object content);
	void indexMulti(List<? extends IndexRequest> requestsList);
}
