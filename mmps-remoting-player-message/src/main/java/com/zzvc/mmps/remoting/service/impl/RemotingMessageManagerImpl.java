package com.zzvc.mmps.remoting.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jms.core.JmsTemplate;

import com.zzvc.mmps.remoting.message.RemotingNotifyMessage;
import com.zzvc.mmps.remoting.message.RemotingPushMessage;
import com.zzvc.mmps.remoting.model.RemotingAttribute;
import com.zzvc.mmps.remoting.model.RemotingData;
import com.zzvc.mmps.remoting.server.task.RemotingServerModuleTask;
import com.zzvc.mmps.remoting.service.RemotingMessageManager;

public class RemotingMessageManagerImpl implements RemotingMessageManager {
	
	@Resource
	protected JmsTemplate topicJmsTemplate;

	@Override
	public void pushUpdate(RemotingServerModuleTask handler) {
		for (Map.Entry<RemotingAttribute,RemotingData> entry : handler.invoke().entrySet()) {
			topicJmsTemplate.convertAndSend(new RemotingPushMessage(handler.getId(), entry.getKey(), entry.getValue()));
		}
	}

	@Override
	public void notifyUpdate(RemotingServerModuleTask handler) {
		topicJmsTemplate.convertAndSend(new RemotingNotifyMessage(handler.getId()));
	}

}
