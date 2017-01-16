package com.baosight.iwater.bigdata.simulation.sensor;

import org.apache.log4j.Logger;

import com.baosight.iwater.bigdata.IUserData;
import com.baosight.iwater.bigdata.simulation.IEnvironment;
import com.baosight.iwater.bigdata.userdata.YuliangData;

public class YuliangSensor extends AbstractWaterSensor{
	private static Logger logger = Logger.getLogger(YuliangSensor.class);

	public YuliangSensor(IEnvironment env) {
		super(env);
		// TODO Auto-generated constructor stub
	}

	@Override
	public IUserData getData() {
		double value = this.getEnvironment().getStatus();
		logger.debug("取得天空'"+this.getEnvironment().getName()+"'的雨量值为："+value);
		return new YuliangData(value);
	}

}
