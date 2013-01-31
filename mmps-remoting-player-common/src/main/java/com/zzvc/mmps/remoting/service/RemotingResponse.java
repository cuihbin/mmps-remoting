package com.zzvc.mmps.remoting.service;

import com.zzvc.mmps.remoting.model.RemotingData;

public class RemotingResponse {
	private RemotingData remotingData;
	
	public RemotingResponse(RemotingData remotingData) {
		this.remotingData = remotingData;
	}
	
	public RemotingData getRemotingData() {
		return remotingData;
	}
	public void setRemotingData(RemotingData remotingData) {
		this.remotingData = remotingData;
	}
}
