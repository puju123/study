package com.pujun.algorithm.classify;

import org.apache.commons.lang3.StringUtils;

public class Pretreatment {
	  private static final String[] NWORDS=new String[]{"/an","/i","/l","/n","/nr","/ns","/nt","/nx","/nz","/vn"};
      public static String getNwords(String content) {
		String[] words=StringUtils.split(content,",");
		StringBuffer sBuffer=new StringBuffer();
		for (int i = 0; i < words.length; i++) {
			if (StringUtils.endsWithAny(words[i], NWORDS)) {
				sBuffer.append(StringUtils.trim(words[i]));
				sBuffer.append(",");
			}
		}
		return sBuffer.toString();
	}
}
