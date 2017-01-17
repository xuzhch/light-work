package com.baosight.iwater.bigdata.simulation.environment;

import com.baosight.iwater.bigdata.simulation.IEnvironment;

public class Sky  implements IEnvironment{
	private int count = 1;
	private String name;
	private double yuliang;

	public Sky(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getYuliang() {
		return yuliang;
	}

	public void setYuliang(double yuliang) {
		this.yuliang = yuliang;
	}

	@Override
	public double getStatus() {
		this.yuliang = count++;
		// TODO Auto-generated method stub
		return this.yuliang;
	}
}
