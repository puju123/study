package com.pujun.elasticsearchTest.client;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.delete.DeleteRequest;

public class TestESDeleteSingle {
	/**
	  *
	  *Author Pujun
	  *Date 2015年8月25日
	  *
	*/
	public void testDeleteSingle() {
		ESDeleteSingle esDeleteSingle=new ESDeleteSingle();
		esDeleteSingle.deleteSingle("test1", "test", "AU9igKOVLTGeufoebTkD");
		
	}
	public void testDeleteMult() {
		ESDeleteSingle esDeleteSingle=new ESDeleteSingle();
		List<DeleteRequest> list=new ArrayList<>();
			list.add(new DeleteRequest("test1", "test", "7"));
			list.add(new DeleteRequest("test1", "test", "8"));
			list.add(new DeleteRequest("test1", "test", "9"));

		esDeleteSingle.deleteMulti(list);
		
	}
	public static void main(String[] args) {
//		new TestESDeleteSingle().testDeleteSingle();
		new TestESDeleteSingle().testDeleteMult();
	}
}
