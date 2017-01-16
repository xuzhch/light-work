package com.baosight.iwater.bigdata.simulation.sensor;

import org.apache.log4j.Logger;

import com.baosight.iwater.bigdata.IUserData;
import com.baosight.iwater.bigdata.simulation.IEnvironment;
import com.baosight.iwater.bigdata.userdata.ShuiweiData;

public class ShuiweiSensor extends AbstractWaterSensor{
	private static Logger logger = Logger.getLogger(ShuiweiSensor.class);

	public ShuiweiSensor(IEnvironment env) {
		super(env);
		// TODO Auto-generated constructor stub
	}

	@Override
	public IUserData getData() {
		double value = this.getEnvironment().getStatus();
		logger.debug("取得河流'"+this.getEnvironment().getName()+"'的水位值为："+value);
		return new ShuiweiData(value);
	}

}
