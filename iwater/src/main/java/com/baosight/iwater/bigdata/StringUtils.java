package com.baosight.iwater.bigdata;

public class StringUtils {
	
	public static String string2Bytes(String number,int byteNum){
		return "";
	}

	public static String getFixLengthString(String inStr,int fixLength) throws Exception{
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
	
	public static String getPosHexString(String inStr,int pos, int fixLength) throws Exception{
		int c = Integer.parseInt(String.valueOf(inStr.charAt(pos)));
		String bc = Integer.toHexString(c);
		bc = StringUtils.getFixLengthString(bc,fixLength);
		return bc;
	}
	
	
}
