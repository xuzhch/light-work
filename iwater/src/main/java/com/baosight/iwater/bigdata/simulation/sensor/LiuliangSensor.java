package com.baosight.iwater.bigdata.simulation.sensor;

import com.baosight.iwater.bigdata.simulation.environment.Area;

public class LiuliangSensor extends AbstractWaterSensor{

	public LiuliangSensor(Area area) {
		super(area);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getData() {
		return this.getArea().getData(Area.LIU_LIANG);
	}


}
