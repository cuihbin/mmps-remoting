package com.zzvc.mmps.remoting.client.task.manager;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.zzvc.mmps.jms.task.TopicMessageListenerTask;
import com.zzvc.mmps.remoting.client.task.RemotingClientModule;
import com.zzvc.mmps.remoting.client.task.impl.PopulatePlayerTaskSupport;
import com.zzvc.mmps.remoting.message.RemotingNotifyMessage;
import com.zzvc.mmps.remoting.message.RemotingPushMessage;
import com.zzvc.mmps.remoting.model.RemotingData;
import com.zzvc.mmps.task.utils.TaskUtils;

public class RemotingClientModuleManager extends PopulatePlayerTaskSupport implements TopicMessageListenerTask {
	private static Logger logger = Logger.getLogger(RemotingClientModuleManager.class);
	
	@Autowired(required=false)
	private List<RemotingClientModule> tasks;
	
	public RemotingClientModuleManager() {
		super();
		pushBundle("PlayerRemotingClientResources");
	}

	@Override
	public void afterStartup() {
		for (RemotingClientModule task : tasks) {
			if (TaskUtils.isTaskInited(task)) {
				invokeService(task);
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
					updateTask(task, message.getRemotingData());
				}
			}
		}
	}
	
	private void handleNotifyMessage(RemotingNotifyMessage message) {
		for (RemotingClientModule task : tasks) {
			if (TaskUtils.isTaskInited(task) && message.getServiceId().equals(task.getId())) {
				invokeService(task);
			}
		}
	}
	
	private void invokeService(RemotingClientModule task) {
		updateTask(task, remotingService.invoke(task.getId(), task.getRemotingAttribute()));
	}
	
	private void updateTask(RemotingClientModule task, RemotingData data) {
		try {
			task.onRemotingUpdate(data);
		} catch (Exception e) {
			warnMessage("player.remoting.client.error.module.updatingerror", task.getLabel());
			logger.error("Client module updating error", e);
		}
	}
}
