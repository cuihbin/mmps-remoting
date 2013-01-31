package com.zzvc.mmps.remoting.message;

import java.io.Serializable;

import com.zzvc.mmps.remoting.model.RemotingData;
import com.zzvc.mmps.remoting.model.RemotingAttribute;

public class RemotingPushMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String serviceId;
	private RemotingAttribute remotingAttribute;
	private RemotingData remotingData;

	public RemotingPushMessage(String serviceName, RemotingAttribute remotingFilter, RemotingData remotingData) {
		this.serviceId = serviceName;
		this.remotingAttribute = remotingFilter;
		this.remotingData = remotingData;
	}
	
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String getServiceId) {
		this.serviceId = getServiceId;
	}
	
	public RemotingAttribute getRemotingAttribute() {
		return remotingAttribute;
	}
	public void setRemotingFilter(RemotingAttribute remotingFilter) {
		this.remotingAttribute = remotingFilter;
	}
	
	public RemotingData getRemotingData() {
		return remotingData;
	}
	public void setRemotingData(RemotingData remotingData) {
		this.remotingData = remotingData;
	}
	
}
