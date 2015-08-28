package com.pujun.elasticsearchTest.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.index.IndexRequest;

public class TestESIndexSingle {
	/**
	  *
	  *Author Pujun
	  *Date 2015年8月24日
	  *
	*/
	public void testIndexSingle() {
		ESIndexSingle esIndexTest=new ESIndexSingle();
		Map<String, String> map=new HashMap<>();
		map.put("name", "indexSingle test!");
		esIndexTest.indexSingle("test1", "test", "4", map);
		
	}
	public void testIndexMult() {
		ESIndexSingle esIndexTest=new ESIndexSingle();
		Map<String, String> map=new HashMap<>();
		map.put("name", "indexSingle test!");
		List<IndexRequest> list=new ArrayList<>();
//		try {
//			list.add(new IndexRequest("test1", "test", "7").source(JsonUtils.writeValueAsString(map)));
//			list.add(new IndexRequest("test1", "test", "8").source(JsonUtils.writeValueAsString(map)));
//			list.add(new IndexRequest("test1", "test", "9").source(JsonUtils.writeValueAsString(map)));
			list.add(new IndexRequest("testcars","transacitons").source("{ \"price\" : 10000, \"color\" : \"red\", \"make\" : \"honda\", \"sold\" : \"2014-10-28\" }"));
			list.add(new IndexRequest("testcars","transacitons").source("{ \"price\" : 20000, \"color\" : \"red\", \"make\" : \"honda\", \"sold\" : \"2014-11-05\" }"));
			list.add(new IndexRequest("testcars","transacitons").source("{ \"price\" : 30000, \"color\" : \"green\", \"make\" : \"ford\", \"sold\" : \"2014-05-18\" }"));
			list.add(new IndexRequest("testcars","transacitons").source("{ \"price\" : 15000, \"color\" : \"blue\", \"make\" : \"toyota\", \"sold\" : \"2014-07-02\" }"));
			list.add(new IndexRequest("testcars","transacitons").source("{ \"price\" : 12000, \"color\" : \"green\", \"make\" : \"toyota\", \"sold\" : \"2014-08-19\" }"));
			list.add(new IndexRequest("testcars","transacitons").source("{ \"price\" : 20000, \"color\" : \"red\", \"make\" : \"honda\", \"sold\" : \"2014-11-05\" }"));
			list.add(new IndexRequest("testcars","transacitons").source("{ \"price\" : 80000, \"color\" : \"red\", \"make\" : \"bmw\", \"sold\" : \"2014-01-01\" }"));
			list.add(new IndexRequest("testcars","transacitons").source("{ \"price\" : 25000, \"color\" : \"blue\", \"make\" : \"ford\", \"sold\" : \"2014-02-12\" }"));
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		esIndexTest.indexMulti(list);
		
	}
	public static void main(String[] args) {
//		new TestESIndexSingle().testIndexSingle();
		new TestESIndexSingle().testIndexMult();
	}
}
