package com.pujun.elasticsearchTest.client;

import java.util.HashMap;
import java.util.Map;

public class TestESUpdateSingle {
	/**
	  *
	  *Author Pujun
	  *Date 2015年8月25日
	  *
	*/
	public void testUpdateSingle() {
		ESUpdateSingle esUpdateSingle=new ESUpdateSingle();
		Map<String, String> sMap=new HashMap<>();
		sMap.put("user", "pujun2WSSerer");
		Map<String, String> map=new HashMap<>();
		map.put("postDate", "2015-8-26");
//		esUpdateSingle.updateSingle("test1", "test", "AU9ip9_1LTGeufoebUMH",map);
		esUpdateSingle.upSertSingle("test1", "test", "3",sMap,map);
		
	}
	public static void main(String[] args) {
		new TestESUpdateSingle().testUpdateSingle();
	}
}
