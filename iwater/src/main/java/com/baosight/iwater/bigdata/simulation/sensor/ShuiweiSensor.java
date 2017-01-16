package com.baosight.iwater.bigdata.simulation.sensor;

import com.baosight.iwater.bigdata.simulation.environment.Area;

public class ShuiweiSensor extends AbstractWaterSensor{

	public ShuiweiSensor(Area area) {
		super(area);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getData() {
		// TODO Auto-generated method stub
		return this.getArea().getData(Area.SHUI_WEI);
	}

}
