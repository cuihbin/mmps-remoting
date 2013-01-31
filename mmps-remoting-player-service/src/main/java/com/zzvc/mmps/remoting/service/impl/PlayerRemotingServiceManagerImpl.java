package com.zzvc.mmps.remoting.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.zzvc.mmps.dao.PlayerDao;
import com.zzvc.mmps.model.Player;
import com.zzvc.mmps.remoting.model.KeyModel;
import com.zzvc.mmps.remoting.model.RemotingAttribute;
import com.zzvc.mmps.remoting.model.RemotingData;
import com.zzvc.mmps.remoting.server.task.RemotingServerModuleTask;
import com.zzvc.mmps.remoting.service.RemotingException;
import com.zzvc.mmps.remoting.service.PlayerRemotingServiceManager;
import com.zzvc.mmps.remoting.service.utils.KeyModelConverter;

@Service("playerRemotingServiceManager")
public class PlayerRemotingServiceManagerImpl implements PlayerRemotingServiceManager, InitializingBean {
	private static Logger logger = Logger.getLogger(PlayerRemotingServiceManagerImpl.class);
	
	@Resource
	private PlayerDao playerDao;
	
	@Autowired(required=false)
	private List<RemotingServerModuleTask> serverModuleTasks;

	@Override
	public void afterPropertiesSet() throws Exception {
		if (serverModuleTasks == null) {
			serverModuleTasks = new ArrayList<RemotingServerModuleTask>();
		}
	}

	@Override
	public Map<String,List<KeyModel>> populatePlayer(List<String> addresses) {
		List<Player> players;
		try {
			players = playerDao.findByAddresses(addresses);
		} catch (DataAccessException e) {
			throw new RemotingException("Data access error");
		}
		if (players.isEmpty()) {
			throw new RemotingException("No player found");
		}
		return KeyModelConverter.convertToKeyModel(players.get(0));
	}
	
	@Override
	public RemotingData invoke(String serviceName, RemotingAttribute attributes) {
		for (RemotingServerModuleTask task : serverModuleTasks) {
			if (task.getId().equals(serviceName)) {
				try {
					return task.invoke(attributes);
				} catch (Exception e) {
					logger.error("Remoting service invoking error", e);
					throw new RemotingException("Remoting service invoking error: " + e.getMessage());
				}
			}
		}
		logger.error("Remoting service '" + serviceName + "' not exists");
		throw new RemotingException("Remoting service '" + serviceName + "' not exists");
	}
	
}
