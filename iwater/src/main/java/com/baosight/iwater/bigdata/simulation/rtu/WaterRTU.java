package com.baosight.iwater.bigdata.simulation.rtu;

import java.util.ArrayList;
import java.util.List;

import com.baosight.iwater.bigdata.IUserData;
import com.baosight.iwater.bigdata.simulation.ISensor;
import com.baosight.iwater.bigdata.simulation.RTU;

public class WaterRTU implements RTU{
	private List<ISensor> sensors = new ArrayList<ISensor>();
	
	private String RTUCode;

	private String host;
	
	private int port;
	
	
	public WaterRTU(String rTUCode, String host, int port) {
		super();
		RTUCode = rTUCode;
		this.host = host;
		this.port = port;
	}


	@Override
	public List<ISensor> getSensors() {
		// TODO Auto-generated method stub
		return sensors;
	}

	@Override
	public void send() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<IUserData> collectData() {
		List<IUserData> datas = new ArrayList<IUserData>();
		for(ISensor sensor:this.getSensors()){
			IUserData data = sensor.getData();
			datas.add(data);
		}
		return datas;
	}

	@Override
	public String getHost() {
		// TODO Auto-generated method stub
		return this.host;
	}

	@Override
	public int getPort() {
		// TODO Auto-generated method stub
		return this.port;
	}

	@Override
	public String getRTUCode() {
		// TODO Auto-generated method stub
		return this.RTUCode;
	}


	public void setSensors(List<ISensor> sensors) {
		this.sensors = sensors;
	}


	public void setRTUCode(String rTUCode) {
		RTUCode = rTUCode;
	}


	public void setHost(String host) {
		this.host = host;
	}


	public void setPort(int port) {
		this.port = port;
	}

}
