package com.baosight.iwater.bigdata.simulation;

import com.baosight.iwater.bigdata.IUserData;
import com.baosight.iwater.bigdata.simulation.environment.Area;

public interface ISensor {
	
	public IUserData getData();
	
	public Area getArea();

}
