package com.baosight.iwater.bigdata.simulation;

import java.util.List;

import com.baosight.iwater.bigdata.IUserData;

public interface RTU {
	
	public List<ISensor> getSensors();
	
	public void setSensors(List<ISensor> sensors);
	
	public void setCollectInterval(long timeout);
	
	public void setSendInterval(long timeout);
	
	public void start();
	
	public List<IUserData> getDatas();
	
	public void setTarget(String host, int port);

}
