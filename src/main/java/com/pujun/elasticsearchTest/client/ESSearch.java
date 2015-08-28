package com.pujun.elasticsearchTest.client;

import java.util.List;

public interface ESSearch {
    String get(String index,String type,String id);
    <T> List<? extends Object> search(List<String> indecas,List<String> types,List<T> filters,int from ,int size);
    void searchAll(String index,String alia);
}
