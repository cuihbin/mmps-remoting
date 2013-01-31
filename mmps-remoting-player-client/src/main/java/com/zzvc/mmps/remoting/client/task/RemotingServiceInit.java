package com.zzvc.mmps.remoting.client.task;

import com.zzvc.mmps.remoting.service.PlayerRemotingServiceManager;

public interface RemotingServiceInit extends ClientInit {
	void setRemotingService(PlayerRemotingServiceManager remotingService);
}
