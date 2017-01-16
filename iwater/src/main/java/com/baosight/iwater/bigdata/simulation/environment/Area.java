package com.baosight.iwater.bigdata.simulation.environment;

public class Area {
	
	public static final String YU_LIANG = "YU_LIANG_DATA";
	
	public static final String SHUI_WEI = "SHUI_WEI_DATA";
	
	public static final String LIU_LIANG = "LIU_LIANG_DATA";
	
	private String name;
	
	private Sky sky;
	
	private Sluice sluice;	

	private River river;
	
	public Area(String name, River river, Sky sky, Sluice sluice) {
		super();
		this.name = name;
		this.sky = sky;
		this.sluice = sluice;
		this.river = river;
	}
	
	public double getData(String key){
		return 0;
	}

	public Sky getSky() {
		return sky;
	}

	public void setSky(Sky sky) {
		this.sky = sky;
	}

	public Sluice getSluice() {
		return sluice;
	}

	public void setSluice(Sluice sluice) {
		this.sluice = sluice;
	}

	public River getRiver() {
		return river;
	}

	public void setRiver(River river) {
		this.river = river;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
