package com.baosight.iwater.bigdata.simulation.rtu;

import java.util.List;

import com.baosight.iwater.bigdata.IUserData;
import com.baosight.iwater.bigdata.WaterMessage;
import com.baosight.iwater.bigdata.transport.SocketSender;

public class SimpleRTU extends AbstractRTU{
	
	public SimpleRTU(String rTUCode) {
		super();
		RTUCode = rTUCode;
	}
	
	public SimpleRTU(String rTUCode, String host, int port) {
		super();
		RTUCode = rTUCode;
		this.host = host;
		this.port = port;
	}

	@Override
	public void start() throws Exception {
		SocketSender sender = new SocketSender(this.getHost(),this.getPort());
		List<IUserData> userDatas = this.collectData();
		for(IUserData userData:userDatas){
			WaterMessage message = new WaterMessage(this.getRTUCode(),userData);
			sender.send(message);
		}		
	}

	@Override
	public void stop() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
