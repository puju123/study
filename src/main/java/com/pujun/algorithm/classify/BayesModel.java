package com.pujun.algorithm.classify;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class BayesModel {
	  //各分类含有样本数量
      private Map<String, Integer> classCountMap=new HashMap<String, Integer>();
	  //各分类含有单词总数数量
      private Map<String, Integer> classWordCountMap=new HashMap<String, Integer>();
      //所有样本中的各单词统计数量（按分类）
      private Map<String, Integer> featureCountMap=new HashMap<String, Integer>();
      //各分类占总样本概率
      private Map<String, Float> classPrMap=new HashMap<String, Float>();
      //各单词占各分类总单词量的概率
      private Map<String, Float> featurePrMap=new HashMap<String, Float>();
      //样本总数
      private int recordCount=0;
      //单词总数
      private int wordCount=0;
      
	/**
	 * @return the classWordCountMap
	 */
	public Map<String, Integer> getClassWordCountMap() {
		return classWordCountMap;
	}
	/**
	 * @param classWordCountMap the classWordCountMap to set
	 */
	public void addClassWordCountMap(String key,int value) {
		if (this.classWordCountMap.containsKey(key)) {
			this.classWordCountMap.put(key, this.classWordCountMap.get(key)+value);
		}
		this.classWordCountMap.put(key, value);
	}
	/**
	 * @return the wordCount
	 */
	public int getWordCount() {
		return wordCount;
	}
	/**
	 * @param wordCount the wordCount to set
	 */
	public void addWordCount(int wordCount) {
		this.wordCount += wordCount;
	}
	/**
	 * @return the recordCount
	 */
	public int getRecordCount() {
		return recordCount;
	}
	/**
	 * @param recordCount the recordCount to set
	 */
	public void addRecordCount(int count) {
		this.recordCount += count;
	}
	/**
	 * @param classPrMap the classPrMap to set
	 */
	public void setClassPrMap(Map<String, Float> classPrMap) {
		this.classPrMap = classPrMap;
	}
	/**
	 * @param featurePrMap the featurePrMap to set
	 */
	public void setFeaturePrMap(Map<String, Float> featurePrMap) {
		this.featurePrMap = featurePrMap;
	}
	/**
	 * @return the classCountMap
	 */
	public Map<String, Integer> getClassCountMap() {
		return classCountMap;
	}
	/**
	 * @param classCountMap the classCountMap to set
	 */
	public void addClassCountMap(String key,int value) {
		if (classCountMap.containsKey(key)) {
			classCountMap.put(key, classCountMap.get(key)+value);
		}else {
			classCountMap.put(key, value);
		}
	}
	/**
	 * @return the featureCountMap
	 */
	public Map<String, Integer> getFeatureCountMap() {
		return featureCountMap;
	}
	/**
	 * @param featureCountMap the featureCountMap to set
	 */
	public void addFeatureCountMap(String key,int value) {
		if (featureCountMap.containsKey(key)) {
			featureCountMap.put(key, featureCountMap.get(key)+value);
		}else {
			featureCountMap.put(key, value);
		}
	}
	/**
	 * @return the classPrMap
	 */
	public Map<String, Float> getClassPrMap() {
		return classPrMap;
	}
	/**
	 * @param classPrMap the classPrMap to set
	 */
	public void cauculateClassPr() {
		Iterator<String> r=classCountMap.keySet().iterator();
		while (r.hasNext()) {
			String key=r.next();
			float pr=classCountMap.get(key)/(float)recordCount;
			classPrMap.put(key, pr);
		}
	}
	/**
	 * @return the featurePrMap
	 */
	public Map<String, Float> getFeaturePrMap() {
		return featurePrMap;
	}
	/**
	 * @param featurePrMap the featurePrMap to set
	 */
	public void cauculateFeaturePr() {
		float nunmda=1/recordCount;
		Iterator<String> iterator=featureCountMap.keySet().iterator();
		while (iterator.hasNext()) {
			String key=iterator.next();
			String classify=StringUtils.split(key, ",")[0];
			float numerator=nunmda+featureCountMap.get(key);
			float denominator=nunmda*wordCount+classWordCountMap.get(classify);
			featurePrMap.put(key, numerator/denominator);
		}
	}
	
      
}
