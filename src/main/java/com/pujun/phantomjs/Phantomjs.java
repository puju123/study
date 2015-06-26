package com.pujun.phantomjs;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

public class Phantomjs {
    private final static String exeFile="F:\\test\\phantomjs\\phantomjs.exe";
    private final static String loadPageFile="F:\\test\\loadpagew.js";  
	private String content;
	private int code;

	public String getContent() {
		return content;
	}

	public int getCode() {
		return code;
	}

	public void getHtml(URL url) throws IOException {

		String sockHost = "";
		int sockPort = 0;
		String proxyStr = "";
		if (StringUtils.isNotBlank(sockHost)) {
			proxyStr = new StringBuffer().append(" --proxy=").append(sockHost)
					.append(":").append(sockPort).toString();
		}
		System.out.println("excute:"+ exeFile + proxyStr + " "+ loadPageFile+ " "+ url.toString());
		Runtime rt = Runtime.getRuntime();
		Process p = rt.exec(exeFile + proxyStr + " "
				+loadPageFile + " "
				+ url.toString());// 这里我的codes.js是保存在c盘下面的phantomjs目录
		InputStream is = p.getInputStream();
		// long newTime=System.currentTimeMillis();
		// System.out.println("使用时间："+ (newTime-oldTime));
		String contentStr = readStream(is);
		if (contentStr == null) {
			content = null;
			code = 404;
		} else {
			content = new String(contentStr);
			code = 200;
		}
		is.close();
	}

	public String readStream(InputStream inStream) throws IOException {
		if (inStream == null) {
			return null;
		}
		String resultString = IOUtils.toString(inStream, "UTF-8");
		return resultString;// outSteam.toString();
	}
	public static void main(String[] args) {
    	Phantomjs phantomjs=new Phantomjs();
    	URL url;
		try {
			url = new URL("http://mp.weixin.qq.com/mp/appmsg/show?__biz=MjM5NDM1NzcwMA==&appmsgid=100016709&itemidx=4&sign=f2947bd8a0cec3ba3b1f8874d109d1c5&3rd=MzA3MDU4NTYzMw==&scene=6");
			phantomjs.getHtml(url);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (phantomjs.getCode()==200) {
			System.out.println(phantomjs.getContent());
		}
	}
}
