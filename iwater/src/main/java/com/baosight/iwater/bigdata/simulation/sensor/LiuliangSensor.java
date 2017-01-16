package com.baosight.iwater.bigdata.simulation.sensor;

import org.apache.log4j.Logger;

import com.baosight.iwater.bigdata.IUserData;
import com.baosight.iwater.bigdata.simulation.IEnvironment;
import com.baosight.iwater.bigdata.userdata.LiuliangData;

public class LiuliangSensor extends AbstractWaterSensor{
	private static Logger logger = Logger.getLogger(LiuliangSensor.class);

	public LiuliangSensor(IEnvironment env) {
		super(env);
		// TODO Auto-generated constructor stub
	}

	@Override
	public IUserData getData() {
		double value = this.getEnvironment().getStatus();
		logger.debug("取得水闸'"+this.getEnvironment().getName()+"'的流量值为："+value);  
		return new LiuliangData(value);
	}


}
