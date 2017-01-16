package com.baosight.iwater.bigdata.simulation.rtu;

import java.util.List;

import com.baosight.iwater.bigdata.IUserData;
import com.baosight.iwater.bigdata.WaterMessage;
import com.baosight.iwater.bigdata.transport.SocketSender;

public class TimingRTU extends AbstractRTU{
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
		long timeout = this.getCollectInterval();
		SocketSender sender = new SocketSender(this.getHost(),this.getPort());
		String rtuCode = this.getRTUCode();
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
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

}
