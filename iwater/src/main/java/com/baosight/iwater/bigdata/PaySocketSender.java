package com.baosight.iwater.bigdata;

import java.io.*;

import java.net.*;
import java.util.Random;

import com.baosight.iwater.bigdata.userdata.YuliangData;

public class PaySocketSender {

	public static void main(String args[]) {
		int RTU_NUM = 1;
		int SEND_NUM = 1;

		for (int i = 1; i <= RTU_NUM; i++) {
			String RTU_CODE = ""+i;
			
			new Thread(new Runnable() {

				@Override
				public void run() {
					Socket socket = null;
					BufferedReader in = null;
					PrintWriter out = null;
					
					try {
						InetAddress addr = InetAddress.getByName("112.64.186.70");
						socket = new Socket(addr, 4567);
						int count = 0;

						while (true) {
							Thread.sleep(new Random().nextInt(5000));
							in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
							out = new PrintWriter(socket.getOutputStream());						

							// 将字符串输出到Server
							String message = getMessage(RTU_CODE);
							out.println(message);
							// 刷新输出流，使Server马上收到该字符串
							out.flush();
							System.out.println("Client:" + message);
							//System.out.println("Server:" + in.readLine());

							count++;
							if (count >= SEND_NUM) {
								break;
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("Error:" + e);
					} finally {
						try {
							in.close();
							out.close();
							socket.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} // 关闭Socket
					}

				}
			}).start();
		}
	}
	
	public static String getMessage(String RTU_CODE) throws Exception{	
		String msg = new WaterMessage(RTU_CODE,new YuliangData(20.2)).getMessage();
		return msg;
	}


}
