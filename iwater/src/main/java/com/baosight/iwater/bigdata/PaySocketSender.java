package com.baosight.iwater.bigdata;

import java.io.*;

import java.net.*;
import java.util.Random;

public class PaySocketSender {

	public static void main(String args[]) {
		
		for (int i = 1; i <= 100; i++) {
			String RTU_CODE = "B"+i;
			String data = "water info";
			int offset = i;
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {						
						while (true) {
							exSocket(RTU_CODE,data,offset);
						}
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("Error:" + e);
					} finally {		

					}

				}
			}).start();
		}
	}

	public static void exSocket(String rtuCode, String data,int offset) {
		try {
			InetAddress addr = InetAddress.getByName("localhost");
			Socket socket = new Socket(addr, 5203);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			for (int i = 0; i < 1000000; i++) {
				String readline = rtuCode +" "+ data;
				; // sin.readLine(); //从系统标准输入读入一字符串
				// 将从系统标准输入读入的字符串输出到Server
				Thread.sleep(new Random().nextInt(10000));
				out.println(readline);
				// 刷新输出流，使Server马上收到该字符串
				out.flush();
				System.out.println("Client:" + readline);

				System.out.println("Servlet :" + in.readLine());
			}
			socket.close(); // 关闭Socket

		} catch (Exception e) {

			System.out.println("Error" + e); // 出错，则打印出错信息

		}

	}

}
