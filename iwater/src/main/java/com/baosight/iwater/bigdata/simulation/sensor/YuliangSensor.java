package com.baosight.iwater.bigdata.simulation.sensor;

import com.baosight.iwater.bigdata.simulation.environment.Area;

public class YuliangSensor extends AbstractWaterSensor{

	public YuliangSensor(Area area) {
		super(area);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getData() {
		// TODO Auto-generated method stub
		return this.getArea().getData(Area.YU_LIANG);
	}

}
