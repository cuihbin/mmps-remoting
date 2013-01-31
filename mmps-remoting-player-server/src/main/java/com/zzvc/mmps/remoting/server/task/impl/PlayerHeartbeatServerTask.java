package com.zzvc.mmps.remoting.server.task.impl;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import com.zzvc.mmps.dao.PlayerDao;
import com.zzvc.mmps.model.Player;
import com.zzvc.mmps.remoting.constants.KeyConstants;
import com.zzvc.mmps.remoting.model.RemotingAttribute;
import com.zzvc.mmps.remoting.model.RemotingData;
import com.zzvc.mmps.remoting.server.task.RemotingServerModuleTask;
import com.zzvc.mmps.task.TaskSupport;

public class PlayerHeartbeatServerTask extends TaskSupport implements RemotingServerModuleTask {
	@Resource
	PlayerDao playerDao;

	public PlayerHeartbeatServerTask() {
		super();
		pushBundle("PlayerRemotingServerResources");
	}

	@Override
	public RemotingData invoke(RemotingAttribute filter) {
		String playerAddress = filter.getAttribute(KeyConstants.KEY_PLAYER_ADDRESS)[0];
		Player player = playerDao.findByAddress(playerAddress);
		player.setLastHeartbeat(new Date());
		playerDao.save(player);
		return null;
	}

	@Override
	public Map<RemotingAttribute, RemotingData> invoke() {
		return null;
	}

}
