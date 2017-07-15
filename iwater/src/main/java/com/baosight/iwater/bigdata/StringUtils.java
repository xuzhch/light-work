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
	
	public static String formatHexStr(String hexStr){
		String inStr = hexStr.replace(" ", "");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < inStr.length(); i++){  
			if(i!=0&&i%2==0){
				sb.append(" ");
				sb.append(inStr.charAt(i));
			}else{
				sb.append(inStr.charAt(i));
			}
		}		
		return sb.toString();
	}
	
	
	public static String getPosHexString(String inStr,int pos, int fixLength) throws Exception{
		int c = Integer.parseInt(String.valueOf(inStr.charAt(pos)));
		String bc = Integer.toHexString(c);
		bc = StringUtils.getFixLengthString(bc,fixLength);
		return bc;
	}
	
	
}
