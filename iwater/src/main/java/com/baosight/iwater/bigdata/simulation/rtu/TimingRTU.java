package com.baosight.iwater.bigdata.simulation.rtu;

import java.util.List;

import org.apache.log4j.Logger;

import com.baosight.iwater.bigdata.IUserData;
import com.baosight.iwater.bigdata.WaterMessage;
import com.baosight.iwater.bigdata.simulation.RTU;
import com.baosight.iwater.bigdata.simulation.environment.River;
import com.baosight.iwater.bigdata.simulation.environment.Sky;
import com.baosight.iwater.bigdata.simulation.environment.Sluice;
import com.baosight.iwater.bigdata.simulation.sensor.LiuliangSensor;
import com.baosight.iwater.bigdata.simulation.sensor.ShuiweiSensor;
import com.baosight.iwater.bigdata.simulation.sensor.YuliangSensor;
import com.baosight.iwater.bigdata.transport.SocketSender;

public class TimingRTU extends AbstractRTU{
	private static Logger logger = Logger.getLogger(TimingRTU.class);
	private long collectInterval;
	
	private boolean stopFlag = false;

	public TimingRTU(String rTUCode) {
		super();
		RTUCode = rTUCode;
	}
	
	public TimingRTU(String rTUCode, String host, int port) {
		super();
		RTUCode = rTUCode;
		this.host = host;
		this.port = port;
	}

	@Override
	public void start() throws Exception {
		logger.info("RTU开始启动，rtuCode:"+this.getRTUCode());
		stopFlag = false;
		long timeout = this.getCollectInterval();
		SocketSender sender = new SocketSender(this.getHost(),this.getPort());
		String rtuCode = this.getRTUCode();
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						logger.info("RTU:'"+rtuCode+"'线程运行中...");
						Thread.sleep(timeout);					
						List<IUserData> userDatas = collectData();
						for(IUserData userData:userDatas){
							WaterMessage message = new WaterMessage(rtuCode,userData);
							sender.send(message);
						}
						if(stopFlag){
							break;
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}				
			}
		}
	).start();
		
	}

	public long getCollectInterval() {
		return collectInterval;
	}

	public void setCollectInterval(long collectInterval) {
		this.collectInterval = collectInterval;
	}

	@Override
	public void stop() throws Exception {
		this.stopFlag = true;		
	}
	
	public static void main(String args[]) {
		RTU rtu = createTimingRTU("1",1000,"112.64.186.70",5191);
		try {
			rtu.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static RTU createTimingRTU(String rtuCode, long interval, String host, int port){
		River river = new River("xiaohe");
		Sky sky = new Sky("baoshan");
		Sluice sluice = new Sluice("xiaohezha");
		YuliangSensor ylSensor = new YuliangSensor(sky);
		ShuiweiSensor swSensor = new ShuiweiSensor(river);
		LiuliangSensor llSensor = new LiuliangSensor(sluice);
		TimingRTU rtu = new TimingRTU(rtuCode,host,port);
		rtu.setCollectInterval(interval);
		rtu.addSensor(ylSensor);
		rtu.addSensor(swSensor);
		rtu.addSensor(llSensor);
		river.setShuiwei(9876.543);
		sky.setYuliang(6543.2);
		sluice.setLiuliang(999999.999);
		return rtu;
	}

}
