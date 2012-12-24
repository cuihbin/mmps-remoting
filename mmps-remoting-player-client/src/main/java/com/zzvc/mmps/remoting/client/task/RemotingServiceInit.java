package com.zzvc.mmps.remoting.client.task;

import com.zzvc.mmps.remoting.service.PlayerRemotingServiceManager;
import com.zzvc.mmps.task.Task;

public interface RemotingServiceInit extends Task {
	void setRemotingService(PlayerRemotingServiceManager remotingService);
}
