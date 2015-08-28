package com.pujun.elasticsearchTest.client;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogram;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;

public class AggregationSingle implements AggregationSearch {
	private Client client= new ESClientSingle().getClient();
	@Override
	public void doAggregat(String index) {
		//按type分组，每个type下按小时分类
		SearchResponse response=client.prepareSearch(index)
				.addAggregation(AggregationBuilders
						.terms("by_name")
						.field("_type")
						.subAggregation(AggregationBuilders
								.dateHistogram("sum_count")
								.field("@timestamp").interval(DateHistogram.Interval.HOUR)))
				.execute()
				.actionGet();
		Terms a=response.getAggregations().get("by_name");
		for (Terms.Bucket b:a.getBuckets()) {
			System.out.println(b.getKey()+" "+b.getDocCount());
			DateHistogram d=b.getAggregations().get("sum_count");
			for (DateHistogram.Bucket db:d.getBuckets()) {
				System.out.println(db.getKey()+" "+db.getDocCount());
			}
		}
	}
	/**
	* @Title: doAggBycolor 
	* @Description: 按颜色分组计数
	* @param  
	* @return void
	* @throws
	 */
	public void doAggBycolor() {
		SearchResponse response=client.prepareSearch("testcars")
				.setSearchType(SearchType.COUNT)
				.addAggregation(AggregationBuilders.terms("by_color")
						.field("color"))
				.execute()
				.actionGet();
		Terms a=response.getAggregations().get("by_color");
		for (Terms.Bucket b:a.getBuckets()) {
			System.out.println(b.getKey()+":"+b.getDocCount());
		}
	}
	public static void main(String[] args) {
		AggregationSingle a=new AggregationSingle();
		a.doAggBycolor();
	}
	/**
	  *
	  *Author Pujun
	  *Date 2015年8月27日
	  *
	*/
}
