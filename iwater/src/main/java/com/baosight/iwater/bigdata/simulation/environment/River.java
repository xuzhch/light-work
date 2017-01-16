package com.baosight.iwater.bigdata.simulation.environment;

public class River {
	private String name;
	private String shuiwei;

	public River(String name, String shuiwei) {
		super();
		this.name = name;
		this.shuiwei = shuiwei;
	}

	public String getShuiwei() {
		return shuiwei;
	}

	public void setShuiwei(String shuiwei) {
		this.shuiwei = shuiwei;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
