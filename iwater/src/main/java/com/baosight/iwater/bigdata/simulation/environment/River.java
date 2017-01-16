package com.baosight.iwater.bigdata.simulation.environment;

import com.baosight.iwater.bigdata.simulation.IEnvironment;

public class River implements IEnvironment{
	private String name;
	private double shuiwei;

	public River(String name) {
		super();
		this.name = name;
	}

	public double getShuiwei() {
		return shuiwei;
	}

	public void setShuiwei(double shuiwei) {
		this.shuiwei = shuiwei;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public double getStatus() {
		return this.shuiwei;
	}

}
