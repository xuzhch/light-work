package com.baosight.iwater.bigdata.userdata;

import java.text.DecimalFormat;

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
	
	public static final int CFN_CODE = 1;
	
	private DecimalFormat df = new DecimalFormat("######0.0");   
	private double value;

	public YuliangData(double value) {
		super();
		this.value = value;
	}

	@Override
	public int getCFNCode() {
		// TODO Auto-generated method stub
		return CFN_CODE;
	}

	@Override
	public String getData() throws Exception {
		if(Double.compare(value, 99999.9)>0){
			throw new Exception("数值超出上限值99999.9!");
		}
		
		String formatStr = df.format(this.value);	
		String str = StringUtils.getFixLengthString(formatStr, 7);
		String byte1 = StringUtils.getPosFixLengthString(str,4,4)+ StringUtils.getPosFixLengthString(str,6,4);
		String byte2 = StringUtils.getPosFixLengthString(str,2,4)+ StringUtils.getPosFixLengthString(str,3,4);
		String byte3 = StringUtils.getPosFixLengthString(str,0,4)+ StringUtils.getPosFixLengthString(str,1,4);		
		return AFN_CODE+byte1+byte2+byte3;
	}
	
	public static void main(String args[]) {
		try {
			System.out.println(new YuliangData(20.22).getData());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
