package com.zzvc.mmps.remoting.service;

import com.zzvc.mmps.remoting.model.RemotingAttribute;

public class RemotingRequest {
	private String serviceName;
	private RemotingAttribute remotingFilter;
	
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	public RemotingAttribute getRemotingFilter() {
		return remotingFilter;
	}
	public void setRemotingFilter(RemotingAttribute remotingFilter) {
		this.remotingFilter = remotingFilter;
	}
}
