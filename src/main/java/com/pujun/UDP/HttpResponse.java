/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pujun.UDP;

// JDK imports
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.input.ReaderInputStream;

/** An HTTP response. */
public class HttpResponse {

  private final URL url;
  private byte[] content;
  private int code;
  private final Map<String, String> headers = new HashMap<String, String>();
  private int timeout=10000;

  public HttpResponse(URL url)
  throws  IOException {

    this.url = url;

    if (!"http".equals(url.getProtocol()))
      System.out.println("Not an HTTP url:" + url);

    String path = "".equals(url.getFile()) ? "/" : url.getFile();

    // some servers will redirect a request with a host line like
    // "Host: <hostname>:80" to "http://<hpstname>/<orig_path>"- they
    // don't want the :80...

    String host = url.getHost();
    int port;
    String portString;
    if (url.getPort() == -1) {
      port= 80;
      portString= "";
    } else {
      port= url.getPort();
      portString= ":" + port;
    }
    Socket socket = null;

    try {
      socket = new Socket();                    // create the socket
      socket.setSoTimeout(timeout);


      // connect
      String sockHost = host;
      int sockPort = port;
      InetSocketAddress sockAddr= new InetSocketAddress(sockHost, sockPort);
      socket.connect(sockAddr, timeout);

      // make request
      OutputStream req = socket.getOutputStream();

      StringBuffer reqStr = new StringBuffer("GET ");
      reqStr.append(path);

      reqStr.append(" HTTP/1.0\r\n");

      reqStr.append("Host: ");
      reqStr.append(host);
      reqStr.append(portString);
      reqStr.append("\r\n");
     //update by pujun 20150130 start
//      reqStr.append("Accept-Encoding: x-gzip, gzip\r\n");
      reqStr.append("Accept-Encoding: gzip, deflate\r\n");
    //update by pujun 20150130 end

      reqStr.append("Accept: ");
      reqStr.append("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
      reqStr.append("\r\n");
      //add by pujun 20150129 start
//      reqStr.append("Cookie: ");
//      reqStr.append(this.http.getCookies());
//      reqStr.append("\r\n");
//      reqStr.append("Connection:keep-alive\r\n");
      //add by pujun 20150129 end
      String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0";
      if ((userAgent == null) || (userAgent.length() == 0)) {
        ;
      } else {
        reqStr.append("User-Agent: ");
        reqStr.append(userAgent);
        reqStr.append("\r\n");
      }

//      if (page.isReadable(WebPage.Field.MODIFIED_TIME.getIndex())) {
//        reqStr.append("If-Modified-Since: " +
//                      HttpDateFormat.toString(page.getModifiedTime()));
//        reqStr.append("\r\n");
//      }
      reqStr.append("\r\n");
//      System.out.println(reqStr);
      byte[] reqBytes= reqStr.toString().getBytes();

      req.write(reqBytes);
      req.flush();

      PushbackInputStream in =                  // process response
        new PushbackInputStream(
          new BufferedInputStream(socket.getInputStream())) ;
      
      
//      StringBuffer sBuffer=new StringBuffer();
//      for (int c = in.read(); c != -1; c = in.read()) {
//    	   sBuffer.append((char)c);
//      }
//      System.out.println(new String(sBuffer.toString().getBytes(),"GBK"));
      
      
      
      StringBuffer line = new StringBuffer();
//      while (readLine(in, line, true)>0) {
//          System.out.println(line.toString());
//	}

      boolean haveSeenNonContinueStatus= false;
      while (!haveSeenNonContinueStatus) {
        // parse status code line
        this.code = parseStatusLine(in, line);
        // parse headers
        try {
			parseHeaders(in, line);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        haveSeenNonContinueStatus= code != 100; // 100 is "Continue"
      }

      try {
 		readPlainContent(in);
 		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

      String contentEncoding = getHeader(Response.CONTENT_ENCODING);
      if ("gzip".equals(contentEncoding) || "x-gzip".equals(contentEncoding)) {
        content = GZIPUtils.unzipBestEffort(content);
      } else {
//        if (Http.LOG.isTraceEnabled()) {
//          Http.LOG.trace("fetched " + content.length + " bytes from " + url);
//        }
      }

      // add headers in metadata to row
      for (String key : headers.keySet()) {
            System.out.println(key+":"+ headers.get(key));
      }

    } finally {
      if (socket != null)
        socket.close();
    }

  }


  /* ------------------------- *
   * <implementation:Response> *
   * ------------------------- */

  public URL getUrl() {
    return url;
  }

  public int getCode() {
    return code;
  }

  public String getHeader(String name) {
    return headers.get(name);
  }


  public byte[] getContent() {
    return content;
  }

  /* ------------------------- *
   * <implementation:Response> *
   * ------------------------- */


  private void readPlainContent(InputStream in)
    throws Exception, IOException {

    int contentLength = Integer.MAX_VALUE;    // get content length
    String contentLengthString = headers.get(Response.CONTENT_LENGTH);
    if (contentLengthString != null) {
      contentLengthString = contentLengthString.trim();
      try {
        if (!contentLengthString.isEmpty())
          contentLength = Integer.parseInt(contentLengthString);
      } catch (NumberFormatException e) {
        throw new Exception("bad content length: "+contentLengthString);
      }
    }
//    if (http.getMaxContent() >= 0
//      && contentLength > http.getMaxContent())   // limit download size
//      contentLength  = http.getMaxContent();

    ByteArrayOutputStream out = new ByteArrayOutputStream(8*1024);
    byte[] bytes = new byte[8*1024];
    int length = 0;                           // read content
    for (int i = in.read(bytes); i != -1 && length + i <= contentLength; i = in.read(bytes)) {

      out.write(bytes, 0, i);
      length += i;
    }
    content = out.toByteArray();
  }

  private int parseStatusLine(PushbackInputStream in, StringBuffer line)
    throws IOException {
    readLine(in, line, false);

    int codeStart = line.indexOf(" ");
    int codeEnd = line.indexOf(" ", codeStart+1);

    // handle lines with no plaintext result code, ie:
    // "HTTP/1.1 200" vs "HTTP/1.1 200 OK"
    if (codeEnd == -1)
      codeEnd= line.length();

    int code=0;
    try {
      code= Integer.parseInt(line.substring(codeStart+1, codeEnd));
    } catch (NumberFormatException e) {
//      throw new Exception("bad status line '" + line
//                              + "': " + e.getMessage(), e);
    }

    return code;
  }


  private void processHeaderLine(StringBuffer line)
    throws IOException, Exception {

    int colonIndex = line.indexOf(":");       // key is up to colon
    if (colonIndex == -1) {
      int i;
      for (i= 0; i < line.length(); i++)
        if (!Character.isWhitespace(line.charAt(i)))
          break;
      if (i == line.length())
        return;
      throw new Exception("No colon in header:" + line);
    }
    String key = line.substring(0, colonIndex);

    int valueStart = colonIndex+1;            // skip whitespace
    while (valueStart < line.length()) {
      int c = line.charAt(valueStart);
      if (c != ' ' && c != '\t')
        break;
      valueStart++;
    }
    String value = line.substring(valueStart);
    headers.put(key, value);
  }


  // Adds headers to our headers Metadata
  private void parseHeaders(PushbackInputStream in, StringBuffer line)
    throws IOException, Exception {

    while (readLine(in, line, true) != 0) {

      // handle HTTP responses with missing blank line after headers
      int pos;
      if ( ((pos= line.indexOf("<!DOCTYPE")) != -1)
           || ((pos= line.indexOf("<HTML")) != -1)
           || ((pos= line.indexOf("<html")) != -1) ) {

        in.unread(line.substring(pos).getBytes("UTF-8"));
        line.setLength(pos);

        try {
            //TODO: (CM) We don't know the header names here
            //since we're just handling them generically. It would
            //be nice to provide some sort of mapping function here
            //for the returned header names to the standard metadata
            //names in the ParseData class
          processHeaderLine(line);
        } catch (Exception e) {
          // fixme:
          System.out.println("Failed with the following exception: "+ e);
        }
        return;
      }

      processHeaderLine(line);
    }
  }

  private static int readLine(PushbackInputStream in, StringBuffer line,
                      boolean allowContinuedLine)
    throws IOException {
    line.setLength(0);
    for (int c = in.read(); c != -1; c = in.read()) {
      switch (c) {
        case '\r':
          if (peek(in) == '\n') {
            in.read();
          }
        case '\n':
          if (line.length() > 0) {
            // at EOL -- check for continued line if the current
            // (possibly continued) line wasn't blank
            if (allowContinuedLine)
              switch (peek(in)) {
                case ' ' : case '\t':                   // line is continued
                  in.read();
                  continue;
              }
          }
          return line.length();      // else complete
        default :
          line.append((char)c);
      }
    }
    throw new EOFException();
  }

  private static int peek(PushbackInputStream in) throws IOException {
    int value = in.read();
    in.unread(value);
    return value;
  }
  public static void main(String[] args) {
		 URL url=null;
		try {
			url = new URL("http://zhidao.baidu.com/link?url=HpRoAUh3bLwz9M0A9aZ9sx4aN3qnpv0wiMep2S0lblmdUm8FVJGaJuBHOF4AZ60-GivqQbnlIjNXnMbKWKPRt-YXwX2hXMiqNxNb3EqtZoq");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			long start=System.currentTimeMillis();
			HttpResponse httpResponse=new HttpResponse(url);
			String cntString=new String(httpResponse.getContent(),"GBK");
			System.out.println("正文："+cntString);
			long end=System.currentTimeMillis();
			System.out.println("用时："+(end-start));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
