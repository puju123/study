package com.pujun.elasticsearchTest.client;

import java.util.List;

public interface ESUpdate {
    void updateSingle(String indices,String type,String id,Object content);
    void updateMulti(List<String> json);
    void upSertSingle(String indices,String type,String id,Object search,Object content);
    void upSertMulti(List<String> json);
}
