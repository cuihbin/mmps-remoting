package com.zzvc.mmps.remoting.message;

import java.io.Serializable;

public class RemotingNotifyMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String serviceId;

	public RemotingNotifyMessage(String serviceId) {
		this.serviceId = serviceId;
	}
	
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	
}
