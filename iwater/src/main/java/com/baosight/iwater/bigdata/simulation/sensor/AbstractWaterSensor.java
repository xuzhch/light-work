package com.baosight.iwater.bigdata.simulation.sensor;

import com.baosight.iwater.bigdata.simulation.IEnvironment;
import com.baosight.iwater.bigdata.simulation.ISensor;

public abstract class AbstractWaterSensor implements ISensor{
	private IEnvironment env;

	public AbstractWaterSensor(IEnvironment env) {
		super();
		this.env = env;
	}

	@Override
	public IEnvironment getEnvironment() {
		// TODO Auto-generated method stub
		return this.env;
	}

}
