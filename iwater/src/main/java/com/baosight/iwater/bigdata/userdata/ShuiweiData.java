package com.baosight.iwater.bigdata.userdata;

import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import com.baosight.iwater.bigdata.StringUtils;

/**
 * 查询水位参数：水位仪表数量有 N 个，每个水位值 4 字节，取值范围为-9999.999～+9999.999， 单位为 m，
 * 数据域 4*N＋4 字节。数据格式见表 85。  
 * @author chaos
 * @email xuzhengchao@baosight.com
 * @date 2017年1月12日下午5:09:04
 */
public class ShuiweiData extends AbstractSelfReportData {
	private static Logger logger = Logger.getLogger(ShuiweiData.class);
	
	public static final int CFN_CODE = 2;
	private DecimalFormat df = new DecimalFormat("######0.000");   	
	private double value;

	public ShuiweiData(double value) {
		super();
		this.value = value;
	}

	@Override
	public int getCFNCode() {
		// TODO Auto-generated method stub
		logger.info("生成水位消息数据！");
		return CFN_CODE;
	}

	@Override
	public String getData() throws Exception {		
		if(Double.compare(value, 99999.9)>0||Double.compare(value, -99999.9)<0){
			throw new Exception("数值超出上限值99999.9或超出下限值-99999.9!");
		}
		boolean isMinus = Double.compare(value, 0)<0?true:false;
		String formatStr = df.format(Math.abs(this.value));		
		String str = StringUtils.getFixLengthString(formatStr, 8);
		String byte1 = StringUtils.getPosFixLengthString(str,6,2)+" "+ StringUtils.getPosFixLengthString(str,7,2);
		String byte2 = StringUtils.getPosFixLengthString(str,3,2)+" "+ StringUtils.getPosFixLengthString(str,5,2);
		String byte3 = StringUtils.getPosFixLengthString(str,1,2)+" "+ StringUtils.getPosFixLengthString(str,2,2);	
		String mstr = "00";
		if(isMinus){
			mstr = "0F";
		}
		String byte4 = mstr+" "+ StringUtils.getPosFixLengthString(str,0,2);	
		
		String data = byte1+" "+byte2+" "+byte3+" "+byte4;
		logger.info("水位数值："+value+",报文值："+data);
		return AFN_CODE+" "+data;
	}
	
	public static void main(String args[]) {
		try {
			System.out.println(new ShuiweiData(-20.2).getData());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
