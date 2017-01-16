package com.baosight.iwater.bigdata.userdata;

import com.baosight.iwater.bigdata.IUserData;

public abstract class AbstractSelfReportData  implements IUserData {
	private final String AFN_CODE = "C0"; // 应用层功能码 AFN, C0H为遥测终端自报实时数据 
	
	protected double value;

	public AbstractSelfReportData(double value) {
		super();
		this.value = value;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getAFNCode() {
		return AFN_CODE;
	}

}
