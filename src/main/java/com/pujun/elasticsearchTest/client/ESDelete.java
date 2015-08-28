package com.pujun.elasticsearchTest.client;

import java.util.List;

import org.elasticsearch.action.delete.DeleteRequest;

public interface ESDelete {
    void deleteSingle(String indices,String type,String id);
    void deleteMulti(List<? extends DeleteRequest> json);
}
