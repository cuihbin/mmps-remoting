package com.zzvc.mmps.remoting.client.task.impl;

import com.zzvc.mmps.remoting.client.task.RemotingServiceInit;
import com.zzvc.mmps.remoting.service.PlayerRemotingServiceManager;

abstract public class RemotingServiceInitTaskSupport extends ClientInitTaskSupport implements RemotingServiceInit {
	protected PlayerRemotingServiceManager remotingService;

	@Override
	public boolean isWaitingPrequisiteInit() {
		return super.isWaitingPrequisiteInit() || remotingService == null;
	}

	@Override
	public void setRemotingService(PlayerRemotingServiceManager remotingService) {
		this.remotingService = remotingService;
	}
}
