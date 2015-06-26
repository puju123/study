package com.pujun;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.pujun.UDP.HttpResponse;
import com.pujun.phantomjs.Phantomjs;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
//        testPhantomjs();
        testSocket();
    }

	private static void testSocket() {
		// TODO Auto-generated method stub
		 URL url=null;
		try {
			url = new URL("http://jasonshieh.iteye.com/blog/927376");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			long start=System.currentTimeMillis();
			HttpResponse httpResponse=new HttpResponse(url);
			long end=System.currentTimeMillis();
			System.out.println("Socket用时："+(end-start));
//			System.out.println(new String(httpResponse.getContent()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void testPhantomjs() {
		// TODO Auto-generated method stub
    	Phantomjs phantomjs=new Phantomjs();
    	URL url;
		try {
			url = new URL("http://jasonshieh.iteye.com/blog/927376");
			long start=System.currentTimeMillis();
			phantomjs.getHtml(url);
			long end=System.currentTimeMillis();
			System.out.println("Phantomjs用时："+(end-start));
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (phantomjs.getCode()==200) {
//			System.out.println(phantomjs.getContent());
		}
	}
}
