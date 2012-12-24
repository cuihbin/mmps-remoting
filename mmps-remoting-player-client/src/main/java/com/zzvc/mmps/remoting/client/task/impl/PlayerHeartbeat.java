package com.zzvc.mmps.remoting.client.task.impl;

import com.zzvc.mmps.remoting.constants.KeyConstants;
import com.zzvc.mmps.remoting.model.RemotingAttribute;
import com.zzvc.mmps.remoting.model.RemotingData;
import com.zzvc.mmps.scheduler.task.SchedulerTask;

public class PlayerHeartbeat extends RemotingClientModuleTaskSupport implements SchedulerTask {
	private String playerAddress; 
	private int heartbeatInterval;
	
	private int heartbeatIntervalCount = 0;
	private RemotingAttribute remotingAttribute;

	public PlayerHeartbeat() {
		super();
		pushBundle("PlayerRemotingClientResources");
	}

	@Override
	public void init() {
		super.init();
		
		playerAddress = keyModelUtil.getValueScalar(KeyConstants.KEY_PLAYER_ADDRESS);
		try {
			heartbeatInterval = Integer.parseInt(keyModelUtil.getValueScalar(KeyConstants.KEY_HEARTBEAT_INTERVAL));
		} catch (NumberFormatException e) {
		}
		
		remotingAttribute = createRemotingAttribute();
	}

	@Override
	public void onSchedule() {
		remotingUpdate();
	}

	@Override
	public RemotingAttribute getRemotingAttribute() {
		return remotingAttribute;
	}

	@Override
	public void onRemotingUpdate(RemotingData data) {
		if (heartbeatIntervalCount == 0) {
			remotingService.invoke(getId(), remotingAttribute);
			heartbeatIntervalCount = heartbeatInterval;
		} else {
			heartbeatIntervalCount--;
		}
	}
	
	private RemotingAttribute createRemotingAttribute() {
		RemotingAttribute remotingAttribute = new RemotingAttribute();
		remotingAttribute.addAttribute(KeyConstants.KEY_PLAYER_ADDRESS, playerAddress);
		return remotingAttribute;
	}

}
