package com.zzvc.mmps.remoting.client.task.impl;

import com.zzvc.mmps.remoting.client.task.RemotingClientModule;

abstract public class RemotingClientModuleTaskSupport extends PopulatePlayerTaskSupport implements RemotingClientModule {

	@Override
	public void remotingUpdate() {
		onRemotingUpdate(remotingService.invoke(getId(), getRemotingAttribute()));
	}
}
