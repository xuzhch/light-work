package com.baosight.iwater.bigdata.simulation.environment;

public class Sluice {
	private String name;
	private String liuliang;

	public Sluice(String name, String liuliang) {
		super();
		this.name = name;
		this.liuliang = liuliang;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLiuliang() {
		return liuliang;
	}

	public void setLiuliang(String liuliang) {
		this.liuliang = liuliang;
	}
}
