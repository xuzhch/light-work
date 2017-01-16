package com.baosight.iwater.bigdata.transport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;

import org.apache.log4j.Logger;

import com.baosight.iwater.bigdata.WaterMessage;

public class SocketSender {
	private static Logger logger = Logger.getLogger(SocketSender.class);

	private String host;

	private int port;

	public SocketSender(String host, int port) {
		super();
		this.host = host;
		this.port = port;
		init();
	}
	
	private void init(){
		
	}

	public void send(WaterMessage message) throws Exception {

		Socket socket = null;
		BufferedReader in = null;
		PrintWriter out = null;

		try {
			logger.info("SocketSender开始连接服务端！host:" + this.getHost() + ",port:" + this.getPort());
			InetAddress addr = InetAddress.getByName(this.getHost());
			socket = new Socket(addr, this.getPort());

			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());

			// 将字符串输出到Server
			String messageData = message.getMessage();
			out.println(messageData);
			// 刷新输出流，使Server马上收到该字符串
			out.flush();
			logger.info("SocketSender发送数据:" + message + ",host:" + this.getHost() + ",port:" + this.getPort());
			// String rcvData = in.readLine();
			// System.out.println("Server:" + rcvData);

		} catch (Exception e) {
			logger.error("SocketSender发送数据出现异常！host:" + this.getHost() + ",port:" + this.getPort(), e);
			throw e;
		} finally {
			try {
				if(in!=null){
					in.close();
				}
				if(out!=null){
					out.close();
				}
				if(socket!=null){
					socket.close();
				}
			} catch (IOException e) {
				logger.error("SocketSender关闭流出现异常！host:" + this.getHost() + ",port:" + this.getPort(), e);
				throw e;
			} // 关闭Socket
		}

	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
