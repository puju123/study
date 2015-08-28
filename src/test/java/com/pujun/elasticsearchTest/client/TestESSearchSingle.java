package com.pujun.elasticsearchTest.client;


public class TestESSearchSingle {
	/**
	  *
	  *Author Pujun
	  *Date 2015年8月25日
	  *
	*/
	public void testGet() {
		ESSearchSingle esSearchSingle=new ESSearchSingle();
		String result=esSearchSingle.get("test1", "test", "1");
		System.out.println(result);
	}
	public static void main(String[] args) {
//		new TestESSearchSingle().testGet();
		new TestESSearchSingle().testSearch();
//		new TestESSearchSingle().testSearchAll();
	}
	private void testSearchAll() {
		ESSearchSingle esSearchSingle=new ESSearchSingle();
		esSearchSingle.searchAll("test1", "");
		
	}
	private void testSearch() {
        ESSearchSingle esSearchSingle=new ESSearchSingle();
        esSearchSingle.search(null, null, null, 0, 0);
	}
}
