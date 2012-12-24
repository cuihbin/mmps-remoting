package com.zzvc.mmps.remoting.client.task;

import com.zzvc.mmps.remoting.model.RemotingAttribute;
import com.zzvc.mmps.remoting.model.RemotingData;

public interface RemotingClientModule extends PopulatePlayer {
	void setClientHome(String clientHome);
	RemotingAttribute getRemotingAttribute();
	void onRemotingUpdate(RemotingData data);
	void remotingUpdate();
}
