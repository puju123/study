package com.pujun.algorithm.classify;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class Bayes implements Classify {
	// 分类总数
	private BayesModel model = new BayesModel();

	/**
	 * 训练样本数据
	 * 
	 * @param classify      分类
	 * @param trainData  训练数据，必须是已经分好词，并用半角逗号分隔。
	 * @Return void
	 */
	public void train(String classify, String trainData) {
		if (StringUtils.isBlank(classify) || StringUtils.isBlank(trainData)) {
			System.out.println("输入样本为空！");
			return;
		}
		model.addRecordCount(1);
		model.addClassCountMap(classify, 1);
        String[] data=StringUtils.split(trainData, ",");
        model.addClassWordCountMap(classify,data.length);
        model.addWordCount(data.length);
        for (int i = 0; i < data.length; i++) {
			model.addFeatureCountMap(new StringBuffer().append(classify).append(",").append(data[i]).toString(), 1);
		}
	}
    public void cauculate() {
		model.cauculateClassPr();
		model.cauculateFeaturePr();
	}
	public String gainClass(String content) {
		if (StringUtils.isBlank(content)) {
			System.out.println("输入内容为空！");
		}
		String[] word=StringUtils.split(content, ",");
		Map<String, Float> map=model.getClassPrMap();
		Iterator<String> classifyPr=map.keySet().iterator();
		String result=null;
		double maxPr=0.0;
		while (classifyPr.hasNext()) {
			String classify=classifyPr.next();
			double pr=cauculatePrClass(classify,map.get(classify),word);
			System.out.println("当前分类："+classify+" , "+pr);
			if (pr>maxPr) {
				result=classify;
				maxPr=pr;
				
			}
		}
		return result;
	}
	private double cauculatePrClass(String classify, Float classifyPr, String[] word) {
		double result=classifyPr;
		for (int i = 0; i < word.length; i++) {
		    String key=new StringBuffer().append(classify).append(",").append(word[i]).toString();
		    if (model.getFeaturePrMap().containsKey(key)) {
				result*=model.getFeaturePrMap().get(key);
			}else {
				result*=0.5;
			}
		}
		return result;
	}
	public void train(List<HashMap<String, String>> sampleList) {
		
	}

}
