package com.pujun.elasticsearchTest.client;

import org.elasticsearch.search.aggregations.AggregationBuilders;

public class TestAggregationSingle {
	/**
	  *
	  *Author Pujun
	  *Date 2015年8月27日
	  *
	*/
public static void main(String[] args) {
//	new TestESDeleteSingle().testDeleteSingle();
	new TestAggregationSingle().testDoAggregat(".marvel-2015.08.21");
}
private void testDoAggregat(String index) {
	new AggregationSingle().doAggregat(index);
	
}
}
