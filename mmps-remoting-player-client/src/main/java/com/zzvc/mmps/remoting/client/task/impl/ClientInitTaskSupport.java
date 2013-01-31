package com.zzvc.mmps.remoting.client.task.impl;

import java.util.ResourceBundle;

import com.zzvc.mmps.remoting.client.task.ClientInit;
import com.zzvc.mmps.task.TaskSupport;

abstract public class ClientInitTaskSupport extends TaskSupport implements ClientInit {
	private ResourceBundle clientResources;

	@Override
	public void setClientResources(ResourceBundle clientResources) {
		this.clientResources = clientResources;
	}

	@Override
	public boolean isWaitingPrequisiteInit() {
		return super.isWaitingPrequisiteInit() || clientResources == null;
	}
	
	protected String getClientResource(String key) {
		return clientResources.getString(key);
	}
}
