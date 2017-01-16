package com.baosight.iwater.bigdata.userdata;

import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import com.baosight.iwater.bigdata.StringUtils;

/**
 * 查询雨量参数：雨量值 3 字节，取值范围为 0～99999.9，单位为 mm，数据域 3＋4 字节。
 * 如果 采用的雨量仪表是翻斗式雨量计，则取值范围为 0～999999（循环计数累计值），单位为每斗 的分辨率。
 * 数据格式见表 84。 
 * @author chaos
 * @email xuzhengchao@baosight.com
 * @date 2017年1月12日下午5:09:04
 */
public class YuliangData extends AbstractSelfReportData {
	private static Logger logger = Logger.getLogger(YuliangData.class);
	
	public static final int CFN_CODE = 1;
	
	private DecimalFormat df = new DecimalFormat("######0.0");  

	public YuliangData(double value) {
		super(value);
	}

	@Override
	public int getCFNCode() {
		logger.info("生成雨量消息数据！");
		return CFN_CODE;
	}

	@Override
	public String getData() throws Exception {
		if(Double.compare(value, 99999.9)>0||Double.compare(value, 0)<0){
			throw new Exception("雨量数值超出上限值99999.9或雨量数值不能小于0!");
		}
		
		String formatStr = df.format(this.value);	
		String str = StringUtils.getFixLengthString(formatStr, 7);
		String byte1 = StringUtils.getPosHexString(str,4,2)+ " "+StringUtils.getPosHexString(str,6,2);
		String byte2 = StringUtils.getPosHexString(str,2,2)+ " "+StringUtils.getPosHexString(str,3,2);
		String byte3 = StringUtils.getPosHexString(str,0,2)+ " "+StringUtils.getPosHexString(str,1,2);	
		String data = byte1+" "+byte2+" "+byte3;
		logger.info("雨量数值："+value+",报文值："+data);
		return this.getAFNCode()+" "+data;
	}
	
	public static void main(String args[]) {
		try {
			System.out.println(new YuliangData(20.2).getData());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
