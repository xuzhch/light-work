package com.baosight.iwater.bigdata;

import java.util.List;

import org.junit.Test;

import com.baosight.iwater.bigdata.simulation.RTU;
import com.baosight.iwater.bigdata.simulation.environment.River;
import com.baosight.iwater.bigdata.simulation.environment.Sky;
import com.baosight.iwater.bigdata.simulation.environment.Sluice;
import com.baosight.iwater.bigdata.simulation.rtu.SimpleRTU;
import com.baosight.iwater.bigdata.simulation.rtu.TimingRTU;
import com.baosight.iwater.bigdata.simulation.sensor.LiuliangSensor;
import com.baosight.iwater.bigdata.simulation.sensor.ShuiweiSensor;
import com.baosight.iwater.bigdata.simulation.sensor.YuliangSensor;

public class BigDataSenderTest {
	

	@Test
	public void sendToICG() throws Exception {
		RTU rtu = this.createSimpleRTU("1","112.64.186.70",4567);
		rtu.start();
	}
	
	@Test
	public void rtuCollectData() throws Exception {
		RTU rtu = this.createSimpleRTU("1","112.64.186.70",4567);
		List<IUserData> datas = rtu.collectData();
		for(IUserData data:datas){
			System.out.println("传感器数值为："+data.getValue());
			System.out.println("传感器报文为："+data.getData());
		}
	}
	
	public RTU createSimpleRTU(String rtuCode, String host, int port){
		River river = new River("xiaohe");
		Sky sky = new Sky("baoshan");
		Sluice sluice = new Sluice("xiaohezha");
		YuliangSensor ylSensor = new YuliangSensor(sky);
		ShuiweiSensor swSensor = new ShuiweiSensor(river);
		LiuliangSensor llSensor = new LiuliangSensor(sluice);
		RTU rtu = new SimpleRTU(rtuCode,host,port);
		rtu.addSensor(ylSensor);
		rtu.addSensor(swSensor);
		rtu.addSensor(llSensor);
		river.setShuiwei(9876.543);
		sky.setYuliang(6543.2);
		sluice.setLiuliang(999999.999);
		return rtu;
	}

}
