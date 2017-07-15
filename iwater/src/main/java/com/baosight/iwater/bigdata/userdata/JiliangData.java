package com.baosight.iwater.bigdata.userdata;

import com.baosight.iwater.bigdata.IUserData;

public class JiliangData implements IUserData{
	private String data;	

	public JiliangData(String data) {
		super();
		this.data = data;
	}

	@Override
	public int getCFNCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getAFNCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getData() throws Exception {
		// TODO Auto-generated method stub
		return data;
	}

	@Override
	public double getValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setData(String data) {
		this.data = data;
	}

}
