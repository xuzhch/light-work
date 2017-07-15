package com.baosight.iwater.bigdata;

public interface IMessage {
	/**
	 * @return 返回TCP协议传输的Hex字符串
	 * @throws Exception
	 */
	public String getMessage() throws Exception;
}
