package com.baosight.iwater.bigdata;

import org.apache.log4j.Logger;

import com.baosight.iwater.bigdata.userdata.ShuiweiData;
import com.baosight.iwater.bigdata.userdata.YuliangData;

public class WaterMessage {
	private static Logger logger = Logger.getLogger(WaterMessage.class);

	private static String START_STR = "68";

	private String END_STR = "16";

	private String DIR = "1"; // 0下行，1上行

	private String DIV = "0"; // 0单帧，1多个帧

	private String FCB = "01"; // 帧计数位

	private IUserData userData; // 用户数据

	private String rtuCode;

	public WaterMessage(String rtuCode, IUserData userData) {
		super();
		this.rtuCode = rtuCode;
		this.userData = userData;
	}

	public String getMessage() throws Exception {
		StringBuffer message = new StringBuffer();
		StringBuffer messageHeader = new StringBuffer();
		StringBuffer messageBody = new StringBuffer();

		messageBody.append(getControlC());
		messageBody.append(getAddressA());
		messageBody.append(getMessageData());

		messageHeader.append(START_STR);
		// 用户数据区长度L，由D0～D7（1字节）组成，采用BIN编码，是控制域、地址域、用户数据域（应用层）的字节总数。
		// 数据为图片数据流时，数据长度为L*1K。采用无线数传信道，SMS的帧长字节数不大于140，北斗卫星通信的帧长字节数不大于98。
		String L = StringUtils.getFixLengthString(Integer.toBinaryString(messageBody.toString().getBytes().length), 8);
		logger.info("用户数据区长度L:" + L);
		messageHeader.append(L);
		messageHeader.append(START_STR);

		String CS = getCRC(messageBody.toString());

		message.append(messageHeader);
		message.append(messageBody);
		message.append(CS);
		message.append(END_STR);

		String messageStr = message.toString();
		logger.info("报文为:" + messageStr);
		return messageStr;
	}

	private String getAlert() {
		return "0000000000000000";
	}

	private String getStatus() {
		return "0000000000000000";
	}

	private String getCRC(String messageBody) {
		return "1000001";
	}

	private String getMessageData() throws Exception {
		String userData = this.userData.getData();
		logger.info("用户数据区UserData:" + userData);

		String alert = getAlert();
		String status = getStatus();
		logger.info("报警值:" + alert);
		logger.info("状态值:" + status);

		String messageData = userData + alert + status;
		return messageData;
	}

	/**
	 * 控制域C 传输方向位DIR 拆分标志位DIV 帧计数位FCB 功能码
	 * 
	 * @return
	 * @throws Exception
	 */
	private String getControlC() throws Exception {
		String FN_BIN = StringUtils.getFixLengthString(Integer.toBinaryString(userData.getCFNCode()), 4);
		String CStr = DIR + DIV + FCB + FN_BIN;
		logger.info("控制域C:" + CStr);
		return CStr;
	}

	/**
	 * 地址域A 第二种方式的地址域由水文特征码、水文测站编码组成，也为5个字节。其中水文特征码取00H，
	 * 水文测站编码码按水利部水文局《水文测站编码》的规定，由八位HEX码构成。地址域格式见表9。 地址域格式 BYTE 1 水文特征码 BYTE 2
	 * 水文测站编码第8位（高位） 水文测站编码第7位 BYTE 3 水文测站编码第6位 水文测站编码第5位 BYTE4 水文测站编码第4位
	 * 水文测站编码第3位 BYTE 5 水文测站编码第2位 水文测站编码第1位（低位）
	 * 
	 * @return
	 * @throws Exception
	 */
	private String getAddressA() throws Exception {
		String waterCode = "00000000";
		String hexCode = CHexConverter.str2HexStr(rtuCode).trim();// 去除空格
		logger.info("rtuCode:" + rtuCode + ",hexCode:" + hexCode);
		String fixHexCode = StringUtils.getFixLengthString(hexCode, 8);
		String stationCode = "";
		for (int i = 0; i < 8; i++) {
			stationCode += StringUtils.getPosFixLengthString(fixHexCode, i, 4);
		}
		String AStr = waterCode + stationCode;
		logger.info("地址域A:" + AStr);
		return AStr;
	}

	/**
	 * CDT中的CRC8，多项式为 X7+X6+X5+X2+1。E5
	 * @param abyte
	 * @param count
	 * @return
	 */
	public static char GetCheckCRC8(char[] abyte, int count) {
		char crc = 0;
		int i, j;

		for (i = 0; i < count; i++) {
			crc ^= abyte[i];
			for (j = 0; j < 8; j++) {
				if ((crc & 0x80) != 0) {
					crc <<= 1;
					crc ^= 0xE5;
				} else {
					crc <<= 1;
				}
			}
		}
		return crc;
	}

	public static void main(String args[]) {
		try {
			String aa = "b3 01 02 03 04 56 c0 aa aa aa aa aa aa aa aa aa aa 20 00 70 00 11 00 15 06 05 ";
			char crc = WaterMessage.GetCheckCRC8(aa.toCharArray(),aa.length());
			String hexCode = CHexConverter.str2HexStr(String.valueOf(crc));
			System.out.println(hexCode);
			byte[] bt = "C0000000100000001000000000".getBytes();
			//new WaterMessage("B1", new YuliangData(20.2)).getMessage();
			//new WaterMessage("B2", new ShuiweiData(-20.2)).getMessage();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
