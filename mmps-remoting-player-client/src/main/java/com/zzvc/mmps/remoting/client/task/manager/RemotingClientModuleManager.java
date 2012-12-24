package com.zzvc.mmps.remoting.client.task.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.zzvc.mmps.jms.task.TopicMessageListenerTask;
import com.zzvc.mmps.remoting.client.task.RemotingClientModule;
import com.zzvc.mmps.remoting.client.task.impl.PopulatePlayerTaskSupport;
import com.zzvc.mmps.remoting.message.RemotingNotifyMessage;
import com.zzvc.mmps.remoting.message.RemotingPushMessage;
import com.zzvc.mmps.task.TaskException;
import com.zzvc.mmps.task.utils.TaskUtils;

public class RemotingClientModuleManager extends PopulatePlayerTaskSupport implements TopicMessageListenerTask {
	private static Logger logger = Logger.getLogger(RemotingClientModuleManager.class);
	
	private String clientHome;
	
	@Autowired(required=false)
	private List<RemotingClientModule> tasks;
	
	public RemotingClientModuleManager() {
		super();
		pushBundle("PlayerRemotingClientResources");
	}
	
	@Override
	public void init() {
		clientHome = readClientHome();
		
		if (tasks == null) {
			tasks = new ArrayList<RemotingClientModule>();
		}
		
		for (RemotingClientModule task : tasks) {
			task.setClientHome(clientHome);
		}
	}

	@Override
	public void afterStartup() {
		for (RemotingClientModule task : tasks) {
			if (TaskUtils.isTaskInited(task)) {
				task.remotingUpdate();
			}
		}
	}

	@Override
	public void handleMessage(Object message) {
		if (message instanceof RemotingPushMessage) {
			handlePushMessage((RemotingPushMessage) message);
		} else if (message instanceof RemotingNotifyMessage) {
			handleNotifyMessage((RemotingNotifyMessage) message);
		}
	}
	
	private void handlePushMessage(RemotingPushMessage message) {
		if (keyModelUtil.matches(message.getRemotingAttribute())) {
			for (RemotingClientModule task : tasks) {
				if (TaskUtils.isTaskInited(task) && message.getServiceId().equals(task.getId())) {
					task.onRemotingUpdate(message.getRemotingData());
				}
			}
		}
	}
	
	private void handleNotifyMessage(RemotingNotifyMessage message) {
		for (RemotingClientModule task : tasks) {
			if (TaskUtils.isTaskInited(task) && message.getServiceId().equals(task.getId())) {
				task.remotingUpdate();
			}
		}
	}
	
	private String readClientHome() {
		String homePath = null;
		try {
			ResourceBundle clientResource = ResourceBundle.getBundle("client");
			homePath = clientResource.getString("client.home");
			File clientHomeDirectory = new File(homePath);
			if (clientHomeDirectory.exists() && clientHomeDirectory.isDirectory() 
					|| !clientHomeDirectory.exists() && clientHomeDirectory.mkdirs()) {
				return clientHomeDirectory.getAbsolutePath().replaceAll("[\\\\/]", "/") + "/";
			}
			logger.error("Creating client home directory error: " + homePath);
		} catch (Exception e) {
			errorMessage("player.remoting.client.error.config.homeinvalid", homePath);
			logger.error("Creating client home directory error: " + homePath, e);
		}
		throw new TaskException("Creating client home directory error");
	}

}
