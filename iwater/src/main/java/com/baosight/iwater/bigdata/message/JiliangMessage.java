package com.baosight.iwater.bigdata.message;

import org.apache.log4j.Logger;

import com.baosight.iwater.bigdata.AesEncodeUtil;
import com.baosight.iwater.bigdata.CHexConverter;
import com.baosight.iwater.bigdata.IMessage;
import com.baosight.iwater.bigdata.IUserData;
import com.baosight.iwater.bigdata.StringUtils;
import com.baosight.iwater.bigdata.userdata.JiliangData;
import com.baosight.iwater.bigdata.userdata.YuliangData;

import sun.misc.BASE64Encoder;

public class JiliangMessage implements IMessage {
	private static Logger logger = Logger.getLogger(JiliangMessage.class);

	private String userData; // 用户数据

	private String rtuCode;
	
	private static final String PASSWORD = "C509A6F7";

	public JiliangMessage(String rtuCode, String userData) {
		super();
		this.rtuCode = rtuCode;
		this.userData = userData;
	}

	@Override
	public String getMessage() throws Exception {
		String head = "1F 1F"; // 头部
		String rtuCode = "00 01 A0 1C 01";// 设备编码
		
		//处理能耗数据，得到能耗数据UTF-8编码xml字符串的hex传输字符串
		String dataString = new String(this.getUserData().getBytes("UTF-8"), "UTF-8");// 能耗数据转换为UTF8编码
		byte[] dataBytes = AesEncodeUtil.encrypt(dataString,PASSWORD);// 经过AES加密后的能耗数据
		String dataHexStr = CHexConverter.byte2HexStr(dataBytes, dataBytes.length);// 转换为Hex字符串，通过TCP协议传输
		
		//获得能耗数据包长度的hex字符串
		String lengthHexStr = StringUtils.getFixLengthString(Integer.toHexString(dataHexStr.split("[ ]").length), 4);

		String messageHexStr = StringUtils.formatHexStr(head + rtuCode + lengthHexStr + dataHexStr);
		logger.debug("能耗数据传输数据包为:" + messageHexStr);

		return messageHexStr;
	}


	public static void main(String args[]) {
		try {
			String rtu = "B12";
			System.out.println(CHexConverter.str2HexStr(rtu));
			StringBuffer sb = new StringBuffer();
			sb.append("<?XML version=\"1.0\" encoding=\"utf-8\"?>");
			sb.append("<state>resent</state>");
			sb.append("<uploadtime>20170705100211</uploadtime>");
			sb.append("<data>");
			sb.append("<tp>");
			sb.append("<tpid>0001A01U01E01</tpid>");
			sb.append("<datatime>201706010900</datatime>");
			sb.append("<quality>normal</quality>");
			sb.append("<columns>");
			sb.append("<column>");
			sb.append("<key>01</key>");
			sb.append("<value>234567.00</value>");
			sb.append("<unit>01001000</unit>");
			sb.append("</column>");
			sb.append("<column>");
			sb.append("<key>02</key>");
			sb.append("<value>2345.00</value>");
			sb.append("<unit>01001000</unit>");
			sb.append("</column>");
			sb.append("</columns>");
			sb.append("</tp>");
			sb.append("</data>");
			String userData = sb.toString();
			String msg = new JiliangMessage("100", userData).getMessage();
			System.out.println("能耗数据包为："+msg);
			// new WaterMessage("B2", new ShuiweiData(-20.2)).getMessage();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getUserData() {
		return userData;
	}

	public void setUserData(String userData) {
		this.userData = userData;
	}

}
