package com.baosight.iwater.bigdata.simulation.sensor;

import com.baosight.iwater.bigdata.IUserData;
import com.baosight.iwater.bigdata.simulation.environment.Area;
import com.baosight.iwater.bigdata.userdata.YuliangData;

public class YuliangSensor extends AbstractWaterSensor{

	public YuliangSensor(Area area) {
		super(area);
		// TODO Auto-generated constructor stub
	}

	@Override
	public IUserData getData() {
		Double value = this.getArea().getData(Area.YU_LIANG);
		return new YuliangData(value);
	}

}
