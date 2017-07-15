package com.baosight.iwater.bigdata.message;

import org.apache.log4j.Logger;

import com.baosight.iwater.bigdata.AesEncodeUtil;
import com.baosight.iwater.bigdata.CHexConverter;
import com.baosight.iwater.bigdata.IMessage;
import com.baosight.iwater.bigdata.StringUtils;
import com.baosight.iwater.bigdata.transport.SocketSender;

public class JiliangMessage implements IMessage {
	private static Logger logger = Logger.getLogger(JiliangMessage.class);

	private String energyData; // 未加密的能耗数据，XML格式的消息，UTF-8编码

	private String rtuCode; //采集设备编码
	
	private String secretKey; //数据传输密钥
	
	private static final String HEAD = "1F1F"; //传输消息头，2个字节，固定为0x1F1F

	public JiliangMessage(String rtuCode, String secretKey,String energyData) {
		super();
		this.rtuCode = rtuCode;
		this.secretKey = secretKey;
		this.energyData = energyData;
	}

	@Override
	public String getMessage() throws Exception {
		// 处理能耗数据，得到能耗数据UTF-8编码xml字符串的hex传输字符串
		// 能耗数据转换为UTF8编码
		String dataString = new String(this.getEnergyData().getBytes("UTF-8"), "UTF-8");
		
		// 经过AES加密后的能耗数据
		byte[] dataBytes = AesEncodeUtil.encrypt(dataString, this.getSecretKey());
		
		// 转换为Hex字符串，通过TCP协议传输
		String dataHexStr = CHexConverter.byte2HexStr(dataBytes, dataBytes.length);

		// 获得能耗数据包长度的hex字符串
		String lengthHexStr = StringUtils.getFixLengthString(Integer.toHexString(dataHexStr.split("[ ]").length), 4);

		// 组成能耗数据传输数据包
		String messageHexStr = StringUtils.formatHexStr(HEAD + rtuCode + lengthHexStr + dataHexStr);
		logger.debug("能耗数据传输数据包为:" + messageHexStr);

		return messageHexStr;
	}

	public static void main(String args[]) {
		try {
			String rtuCode = "0001A01C01"; //采集设备编码
			String secretKey = "C509A6F7"; //数据传输密钥
			String host = "127.0.0.1"; //采集服务器IP地址
			int port = 80; //采集服务器端口号

			//测试能耗原始数据
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
			
			JiliangMessage msg = new JiliangMessage(rtuCode,secretKey,userData);
			System.out.println("能耗数据包为：" + msg.getMessage());
			SocketSender sender = new SocketSender(host, port);
			sender.send(msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getEnergyData() {
		return energyData;
	}

	public void setEnergyData(String energyData) {
		this.energyData = energyData;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

}
