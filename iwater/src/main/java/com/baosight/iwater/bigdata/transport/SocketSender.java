package com.baosight.iwater.bigdata.transport;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import com.baosight.iwater.bigdata.WaterMessage;

public class SocketSender {
	private static Logger logger = Logger.getLogger(SocketSender.class);

	private String host;

	private int port;
	
	private Socket socket = null;
	private BufferedReader in = null;
	private DataOutputStream out = null;

	public SocketSender(String host, int port) {
		super();
		this.host = host;
		this.port = port;
		init();
	}
	
	private void init(){
		logger.info("SocketSender开始连接服务端！host:" + this.getHost() + ",port:" + this.getPort());
		try {
			InetAddress addr = InetAddress.getByName(this.getHost());
			socket = new Socket(addr, this.getPort());

			//in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new DataOutputStream(socket.getOutputStream());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void send(WaterMessage message) throws Exception {
		try {
			boolean isConnected = socket.isConnected() && !socket.isClosed();
			if(!isConnected){
				InetAddress addr = InetAddress.getByName(this.getHost());
				socket = new Socket(addr, this.getPort());
				out = new DataOutputStream(socket.getOutputStream());
			}
			//此处要将hex字符串转为byte发送到Server，和CRC校验那里一样，转换每个字节，不能直接使用字符串
			String messageData = message.getMessage();
			byte[] bytes = hex2byte(messageData);
			//messageData = "1";
			out.write(bytes);
			// 刷新输出流，使Server马上收到该字符串
			out.flush();
			logger.debug("SocketSender发送数据:" + messageData + ",host:" + this.getHost() + ",port:" + this.getPort());
			// String rcvData = in.readLine();
			// System.out.println("Server:" + rcvData);

		} catch (Exception e) {
			logger.error("SocketSender发送数据出现异常！host:" + this.getHost() + ",port:" + this.getPort(), e);
			throw e;
		}
	}
	
	public void close() throws IOException{
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
	
	public static byte[] hex2byte(String hex) {
        String digital = "0123456789ABCDEF";
        String hex1 = hex.replace(" ", "");
        char[] hex2char = hex1.toCharArray();
        byte[] bytes = new byte[hex1.length() / 2];
        byte temp;
        for (int p = 0; p < bytes.length; p++) {
            temp = (byte) (digital.indexOf(hex2char[2 * p]) * 16);
            temp += digital.indexOf(hex2char[2 * p + 1]);
            bytes[p] = (byte) (temp & 0xff);
        }
        return bytes;
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
