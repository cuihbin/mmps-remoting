package com.zzvc.mmps.remoting.client.task.impl;

import com.zzvc.mmps.remoting.client.task.PopulatePlayer;
import com.zzvc.mmps.remoting.client.utils.KeyModelUtil;

abstract public class PopulatePlayerTaskSupport extends RemotingServiceInitTaskSupport implements PopulatePlayer {
	
	protected KeyModelUtil keyModelUtil;

	@Override
	public boolean isWaitingPrequisiteInit() {
		return super.isWaitingPrequisiteInit() || keyModelUtil == null;
	}

	@Override
	public void setPlayerKeys(KeyModelUtil keyModelUtil) {
		this.keyModelUtil = keyModelUtil;
	}

}
