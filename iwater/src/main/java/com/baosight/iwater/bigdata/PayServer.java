package com.baosight.iwater.bigdata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class PayServer {

	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(5203);
		while (true) {
			Socket socket = server.accept();
			System.out.println("Start...");
			exSocketServer(socket);			
		}
	}

	public static void exSocketServer(final Socket socket) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				BufferedReader in = null;
				PrintWriter out = null;
				try {
					in = new BufferedReader(new InputStreamReader(
							socket.getInputStream()));
					out = new PrintWriter(socket.getOutputStream());
					while (true) {
						String msg = in.readLine();
						if (msg == null) {
							break;
						}
						System.out.println("Client Message:" + msg);
						// 返回消息给客户端。
						out.println("OK!");
						out.flush();

					}
				} catch (Exception e) {
					// e.printStackTrace();
					System.out.println("Error:" + e);
				} finally {
					try {
						in.close();
						out.close();
						socket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}
		}).start();
	}
}