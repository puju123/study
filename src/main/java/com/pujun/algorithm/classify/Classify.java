package com.pujun.algorithm.classify;

import java.util.HashMap;
import java.util.List;

public interface Classify {
    public void train(List<HashMap<String, String>> sampleList) ;
    public String gainClass(String content) ;
}
