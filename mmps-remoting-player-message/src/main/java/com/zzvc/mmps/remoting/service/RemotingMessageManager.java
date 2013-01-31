package com.zzvc.mmps.remoting.service;

import com.zzvc.mmps.remoting.server.task.RemotingServerModuleTask;

public interface RemotingMessageManager {
	void pushUpdate(RemotingServerModuleTask handler);
	void notifyUpdate(RemotingServerModuleTask handler);
}
