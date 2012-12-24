package com.zzvc.mmps.remoting.client.task.manager;

import java.net.MalformedURLException;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.zzvc.mmps.remoting.client.task.RemotingServiceInit;
import com.zzvc.mmps.remoting.service.PlayerRemotingServiceManager;
import com.zzvc.mmps.task.TaskException;
import com.zzvc.mmps.task.TaskSupport;

public class RemotingServiceInitManager extends TaskSupport {
	private static Logger logger = Logger.getLogger(RemotingServiceInitManager.class);
	
	@Autowired
	private List<RemotingServiceInit> tasks;

	public RemotingServiceInitManager() {
		super();
		pushBundle("PlayerRemotingClientResources");
	}

	@Override
	public void init() {
		ResourceBundle bundle = ResourceBundle.getBundle("config");
		String remotingServiceAddress = null;
		try {
			remotingServiceAddress = bundle.getString("player.service.address");
		} catch (MissingResourceException e) {
			errorMessage("player.remoting.client.error.config.loadfailed");
			logger.error("Error loading client config file", e);
			throw new TaskException("Error loading client config file", e);
		}
		
		HessianProxyFactory factory = new HessianProxyFactory();
		PlayerRemotingServiceManager remotingService = null;
		try {
			remotingService = (PlayerRemotingServiceManager) factory.create(PlayerRemotingServiceManager.class, remotingServiceAddress);
		} catch (MalformedURLException e) {
			errorMessage("player.remoting.client.error.config.invalidurl");
			logger.error("Error parsing remoting service url", e);
			throw new TaskException("Error parsing remoting service url", e);
		}
		
		for (RemotingServiceInit task : tasks) {
			task.setRemotingService(remotingService);
		}
	}
	
}
