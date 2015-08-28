package com.pujun.elasticsearchTest.client;

import java.util.List;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

public class ESSearchSingle implements ESSearch {
    Client client=new ESClientSingle().getClient();
	@Override
	public String get(String index,String type,String id) {
		GetResponse response=client.prepareGet(index,type,id)
				.setOperationThreaded(false)
				.execute()
				.actionGet();
		return response.getSource().toString();
	}

	@Override
	public<T> List<String> search(List<String> indecas,List<String> types,List<T> filters,int from ,int size) {
		SearchResponse response=client.prepareSearch("test1").setTypes("test")
				.setSearchType(SearchType.QUERY_THEN_FETCH)
				//not_analyzed类型字段检索，完全匹配
				.setQuery(QueryBuilders.termQuery("message", "out"))
				.setPostFilter(FilterBuilders.existsFilter("postDate"))
				.execute()
				.actionGet();
		SearchHit[] sh=response.getHits().getHits();
		for (SearchHit s:sh) {
			System.out.println(s.getSourceAsString());
		}
		
		return null;
	}

	@Override
	public void searchAll(String index, String alia) {
		SearchResponse response=client.prepareSearch(index)
				.setSearchType(SearchType.SCAN)
				.setScroll(new TimeValue(10000))
				//每次返回记录数
				.setSize(1)
				.execute()
				.actionGet();
		while (true) {
			SearchHit[] result=response.getHits().getHits();
			for (SearchHit s:result) {
				System.out.println("开始一次scroll！");
				System.out.println(s.getSourceAsString());
			}
			response=client.prepareSearchScroll(response.getScrollId())
					.setScroll(new TimeValue(10000))
					.execute()
					.actionGet();
			if (response.getHits().getHits().length==0) {
				System.out.println("scan完成！");
				break;
			}
		}
	}
	/**
	  *
	  *Author Pujun
	  *Date 2015年8月25日
	  *
	*/
}
