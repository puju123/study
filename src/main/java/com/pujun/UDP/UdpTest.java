package com.pujun.UDP;

import java.io.*;    
import java.net.*;    
    
/**  
 * Copyright 2007 GuangZhou Cotel Co. Ltd.  
 * All right reserved.      
 * UDP客户端程序，用于对服务端发送数据，并接收服务端的回应信息.  
 * @author QPING 
 */    
public class UdpTest {    
    private byte[] buffer = new byte[1024];    
    
    private DatagramSocket ds = null;    
    
    /**  
     * 构造函数，创建UDP客户端  
     * @throws Exception  
     */    
    public UdpTest() throws Exception {    
        ds = new DatagramSocket();    
    }    
        
    /**  
     * 设置超时时间，该方法必须在bind方法之后使用.  
     * @param timeout 超时时间  
     * @throws Exception  
     */    
    public final void setSoTimeout(final int timeout) throws Exception {    
        ds.setSoTimeout(timeout);    
    }    
    
    /**  
     * 获得超时时间.  
     * @return 返回超时时间  
     * @throws Exception  
     */    
    public final int getSoTimeout() throws Exception {    
        return ds.getSoTimeout();    
    }    
    
    public final DatagramSocket getSocket() {    
        return ds;    
    }    
    
    /**  
     * 向指定的服务端发送数据信息.  
     * @param host 服务器主机地址  
     * @param port 服务端端口  
     * @param bytes 发送的数据信息  
     * @return 返回构造后俄数据报  
     * @throws IOException  
     */    
    public final DatagramPacket send(final String host, final int port,    
            final byte[] bytes) throws IOException {    
        DatagramPacket dp = new DatagramPacket(bytes, bytes.length, InetAddress    
                .getByName(host), port);    
        ds.send(dp);    
        Socket socket=new Socket();
        return dp;    
    }    
    
    /**  
     * 接收从指定的服务端发回的数据.  
     * @param lhost 服务端主机  
     * @param lport 服务端端口  
     * @return 返回从指定的服务端发回的数据.  
     * @throws Exception  
     */    
    public final String receive(final String lhost, final int lport)    
            throws Exception {    
        DatagramPacket dp = new DatagramPacket(buffer, buffer.length);    
        ds.receive(dp);    
        String info = new String(dp.getData(), 0, dp.getLength());    
        return info;    
    }    
    
    /**  
     * 关闭udp连接.  
     */    
    public final void close() {    
        try {    
            ds.close();    
        } catch (Exception ex) {    
            ex.printStackTrace();    
        }    
    }    
    
    /**  
     * 测试客户端发包和接收回应信息的方法.  
     * @param args  
     * @throws Exception  
     */    
    public static void main(String[] args) throws Exception {    
        UdpTest client = new UdpTest();    
        String serverHost = "58.63.236.248";    
        int serverPort = 80;    
        client.setSoTimeout(10000);
        client.send(serverHost, serverPort, ("").getBytes());    
        String info = client.receive(serverHost, serverPort);    
        System.out.println("服务端回应数据：" + info);   
        client.close();
    }    
}   
