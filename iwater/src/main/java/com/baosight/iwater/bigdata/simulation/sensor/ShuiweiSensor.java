package com.baosight.iwater.bigdata.simulation.sensor;

import com.baosight.iwater.bigdata.IUserData;
import com.baosight.iwater.bigdata.simulation.environment.Area;
import com.baosight.iwater.bigdata.userdata.ShuiweiData;

public class ShuiweiSensor extends AbstractWaterSensor{

	public ShuiweiSensor(Area area) {
		super(area);
		// TODO Auto-generated constructor stub
	}

	@Override
	public IUserData getData() {
		Double value = this.getArea().getData(Area.SHUI_WEI);
		return new ShuiweiData(value);
	}

}
