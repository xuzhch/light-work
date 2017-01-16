package com.baosight.iwater.bigdata.simulation.environment;

import com.baosight.iwater.bigdata.simulation.IEnvironment;

public class Sluice  implements IEnvironment{
	private String name;
	private double liuliang;

	public Sluice(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLiuliang() {
		return liuliang;
	}

	public void setLiuliang(double liuliang) {
		this.liuliang = liuliang;
	}

	@Override
	public double getStatus() {
		// TODO Auto-generated method stub
		return this.liuliang;
	}
}
