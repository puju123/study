package com.pujun.elasticsearchTest.client;

import java.util.Iterator;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.collect.MapMaker;
import org.elasticsearch.common.recycler.Recycler.V;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogram;
import org.elasticsearch.search.aggregations.bucket.nested.Nested;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.avg.Avg;
import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.elasticsearch.search.aggregations.metrics.min.Min;

import com.sun.corba.se.spi.ior.MakeImmutable;
import com.sun.java_cup.internal.runtime.virtual_parse_stack;

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
				//按颜色分组计数
				.addAggregation(AggregationBuilders
						.terms("by_color")
						.field("color")
						//计算每组平均值
						.subAggregation(AggregationBuilders
								.avg("avg_by_color")
								.field("price"))
						//在每组颜色中按制造商再分组并计数
						.subAggregation(AggregationBuilders
								.terms("nest_make")
								.field("make")
								//制造商分组中的最低价格
								.subAggregation(AggregationBuilders
										.min("nest_min")
										.field("price"))
								//制造商分组中的最高价格
								.subAggregation(AggregationBuilders
										.max("nest_max")
										.field("price"))))
				
				.execute()
				.actionGet();
		Terms a=response.getAggregations().get("by_color");
		for (Terms.Bucket b:a.getBuckets()) {
			System.out.println(b.getKey()+":"+b.getDocCount());
			Avg avg=b.getAggregations().get("avg_by_color");
		    System.out.println("avg_price:"+avg.getValue());
			Terms nested=b.getAggregations().get("nest_make");
			for (Terms.Bucket n:nested.getBuckets()) {
				System.out.println("nest:"+n.getKey()+" "+n.getDocCount());
				Min min=n.getAggregations().get("nest_min");
				System.out.println("nest_min:"+min.getValue());
				Max max=n.getAggregations().get("nest_max");
				System.out.println("nest_max:"+max.getValue());
			}
		    
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
