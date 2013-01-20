package com.zzvc.mmps.remoting.client.task.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.zzvc.mmps.remoting.client.task.ClientInit;
import com.zzvc.mmps.task.TaskException;
import com.zzvc.mmps.task.TaskSupport;

public class ClientInitManager extends TaskSupport {
	private static Logger logger = Logger.getLogger(RemotingClientModuleManager.class);
	
	@Autowired(required=false)
	private List<ClientInit> tasks;
	
	private ResourceBundle clientResources;
	
	public ClientInitManager() {
		super();
		pushBundle("PlayerRemotingClientResources");
	}
	
	@Override
	public void init() {
		clientResources = loadClientResources();
		
		if (tasks == null) {
			tasks = new ArrayList<ClientInit>();
		}
		
		for (ClientInit task : tasks) {
			task.setClientResources(clientResources);
		}
	}
	
	private ResourceBundle loadClientResources() {
		try {
			return ResourceBundle.getBundle("client");
		} catch (MissingResourceException e) {
			errorMessage("player.remoting.client.error.init.config.loadingfailed");
			logger.error("Missing client configure file", e);
		}
		throw new TaskException("Missing client configure file");
	}
}
