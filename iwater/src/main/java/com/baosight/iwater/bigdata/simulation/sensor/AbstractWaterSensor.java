package com.baosight.iwater.bigdata.simulation.sensor;

import com.baosight.iwater.bigdata.simulation.ISensor;
import com.baosight.iwater.bigdata.simulation.environment.Area;

public abstract class AbstractWaterSensor implements ISensor{
	private Area area;

	public AbstractWaterSensor(Area area) {
		super();
		this.area = area;
	}

	@Override
	public Area getArea() {
		// TODO Auto-generated method stub
		return this.area;
	}

}
