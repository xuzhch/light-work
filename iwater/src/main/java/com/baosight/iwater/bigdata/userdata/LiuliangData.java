package com.baosight.iwater.bigdata.userdata;

import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import com.baosight.iwater.bigdata.StringUtils;

/**
 * 查询流量（水量）参数：流量（水量）仪表数量为N。每个流量数据为5个字节压缩BCD。
 * 取值范围为-999999.999～+999999.999，单位为m3/s，m3/h（水资源）。
 * 水量为累计取水量， 5个字节为压缩BCD码，取值范围为0～7999999999，单位为m3，
 * BYTE 5的D7位为累计水量的符号位： 0为正值； 1为负值。数据域5*N＋2字节。数据格式见表86，表87 
 * @author chaos
 * @email xuzhengchao@baosight.com
 * @date 2017年1月12日下午5:09:04
 */
public class LiuliangData extends AbstractSelfReportData {
	private static Logger logger = Logger.getLogger(LiuliangData.class);
	
	public static final int CFN_CODE = 3;
	private DecimalFormat df = new DecimalFormat("######0.000");

	public LiuliangData(double value) {
		super(value);
	}

	@Override
	public int getCFNCode() {
		// TODO Auto-generated method stub
		logger.info("生成流量消息数据！");
		return CFN_CODE;
	}

	@Override
	public String getData() throws Exception {		
		if(Double.compare(value, 999999.999)>0||Double.compare(value, -999999.999)<0){
			throw new Exception("数值超出上限值999999.999或超出下限值-999999.999!");
		}
		boolean isMinus = Double.compare(value, 0)<0?true:false;
		String formatStr = df.format(Math.abs(this.value));		
		String str = StringUtils.getFixLengthString(formatStr, 10);
		String byte0 = StringUtils.getPosHexString(str,8,1)+" "+ StringUtils.getPosHexString(str,9,1);
		String byte1 = StringUtils.getPosHexString(str,5,1)+" "+ StringUtils.getPosHexString(str,7,1);
		String byte2 = StringUtils.getPosHexString(str,3,1)+" "+ StringUtils.getPosHexString(str,4,1);
		String byte3 = StringUtils.getPosHexString(str,1,1)+" "+ StringUtils.getPosHexString(str,2,1);	
		String mstr = "0";
		if(isMinus){
			mstr = "F";
		}
		String byte4 = mstr+" "+ StringUtils.getPosHexString(str,0,1);	
		
		String data = byte0+" "+byte1+" "+byte2+" "+byte3+" "+byte4;
		logger.info("流量数值："+value+",报文值："+data);
		return this.getAFNCode()+" "+data;
	}
	
	public static void main(String args[]) {
		try {
			System.out.println(new LiuliangData(-20.2).getData());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
