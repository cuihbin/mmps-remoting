package com.zzvc.mmps.remoting.client.task.impl;

import com.zzvc.mmps.remoting.client.task.RemotingClientModule;

abstract public class RemotingClientModuleTaskSupport extends PopulatePlayerTaskSupport implements RemotingClientModule {
	protected String clientHome;

	@Override
	public boolean isWaitingPrequisiteInit() {
		return super.isWaitingPrequisiteInit() || clientHome == null;
	}

	public void setClientHome(String clientHome) {
		this.clientHome = clientHome;
	}

	@Override
	public void remotingUpdate() {
		onRemotingUpdate(remotingService.invoke(getId(), getRemotingAttribute()));
	}
	
}
