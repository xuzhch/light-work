package com.baosight.iwater.bigdata.simulation;

import java.util.List;

import com.baosight.iwater.bigdata.IUserData;

public interface RTU {
	
	public String getRTUCode();
	
	public List<ISensor> getSensors();
	
	public void send();
	
	public List<IUserData> collectData();
	
	public String getHost();
	
	public int getPort();

}
