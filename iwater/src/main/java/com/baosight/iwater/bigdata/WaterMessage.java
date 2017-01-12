package com.baosight.iwater.bigdata;

import org.springframework.util.StringUtils;

public class WaterMessage {
	
	private static String START_STR = "68H";
	
	private String END_STR = "16H";
	
	private String L = "10001100";
	
	private String DIR = "1"; //0下行，1上行
	
	private String DIV = "1"; //0单帧，1多个帧
	
	private String FCB = "01"; //帧计数位
	
	private int FN = 3; //功能码
	
	private String rtuCode = "B1";
	
	private String AFN = "C0H";
	
	private String getMessage() throws Exception{
		StringBuffer message = new StringBuffer();
		StringBuffer messageHeader = new StringBuffer();
		StringBuffer messageBody = new StringBuffer();
		
		messageBody.append(getControlC());
		messageBody.append(getAddressA());
		messageBody.append(getMessageData());
		
		messageHeader.append(START_STR);
		//用户数据区长度L，由D0～D7（1字节）组成，采用BIN编码，是控制域、地址域、用户数据域（应用层）的字节总数。
		//数据为图片数据流时，数据长度为L*1K。采用无线数传信道，SMS的帧长字节数不大于140，北斗卫星通信的帧长字节数不大于98。
		String L = getFixLengthString(Integer.toBinaryString(messageBody.length()),8);
		messageHeader.append(L);
		messageHeader.append(START_STR);
		
		String CS = getCRC(messageBody.toString());
		
		message.append(messageHeader);
		message.append(messageBody);
		message.append(CS);
		message.append(END_STR);
		
		return message.toString();
	}
	
	private String getCRC(String messageBody){
		return "1000001";
	}
	
	private String getMessageData() throws Exception{
		return "100000001111110001";
	}
	
	/**
	 * 控制域C
	 * 传输方向位DIR 拆分标志位DIV 帧计数位FCB 功能码
	 * @return
	 * @throws Exception 
	 */
	private String getControlC() throws Exception{
		String FN_BIN = getFixLengthString(Integer.toBinaryString(FN),4);
		return DIR+DIV+FCB+FN_BIN;
	}
	
	
	/**
	 * 地址域A
	 * 第二种方式的地址域由水文特征码、水文测站编码组成，也为5个字节。其中水文特征码取00H，
	 * 水文测站编码码按水利部水文局《水文测站编码》的规定，由八位HEX码构成。地址域格式见表9。
	 * 地址域格式
		BYTE 1
		水文特征码
		BYTE 2
		水文测站编码第8位（高位）
		水文测站编码第7位
		BYTE 3
		水文测站编码第6位
		水文测站编码第5位
		BYTE4
		水文测站编码第4位
		水文测站编码第3位
		BYTE 5
		水文测站编码第2位
		水文测站编码第1位（低位）
	 * @return
	 * @throws Exception
	 */
	private String getAddressA() throws Exception{
		String waterCode = "00000000";
		String hexCode = CHexConverter.str2HexStr(rtuCode).trim();//去除空格
		String fixHexCode = getFixLengthString(hexCode,8);
		String stationCode = "";
		for(int i=0;i<8;i++){
			int c = Integer.parseInt(String.valueOf(fixHexCode.charAt(i)));
			String bc = Integer.toBinaryString(c);
			stationCode += getFixLengthString(bc,4);
		}
		return waterCode+stationCode;
	}
	
	private String getFixLengthString(String inStr,int fixLength) throws Exception{
		String outStr = inStr;
		int length = inStr.length();
		if(length>fixLength){
			throw new Exception("输入字符串超出指定的长度！字符串为："+inStr);
		}
		
		for(int i=0;i<fixLength-length;i++){
			outStr = "0" + outStr;
		}
		return outStr;
	}
	
	public static void main(String args[]) {
		try {
			System.out.println(Integer.toBinaryString(2));
			System.out.println(new WaterMessage().getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
