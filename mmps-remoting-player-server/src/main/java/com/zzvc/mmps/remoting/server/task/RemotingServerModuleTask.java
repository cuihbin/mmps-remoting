package com.zzvc.mmps.remoting.server.task;

import java.util.Map;

import com.zzvc.mmps.remoting.model.RemotingData;
import com.zzvc.mmps.remoting.model.RemotingAttribute;
import com.zzvc.mmps.task.Task;

public interface RemotingServerModuleTask extends Task {
	RemotingData invoke(RemotingAttribute filter);
	Map<RemotingAttribute, RemotingData> invoke();
}
