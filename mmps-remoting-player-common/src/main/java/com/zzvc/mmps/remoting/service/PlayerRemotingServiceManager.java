package com.zzvc.mmps.remoting.service;

import java.util.List;
import java.util.Map;

import com.zzvc.mmps.remoting.model.KeyModel;
import com.zzvc.mmps.remoting.model.RemotingData;
import com.zzvc.mmps.remoting.model.RemotingAttribute;

public interface PlayerRemotingServiceManager {
	Map<String, List<KeyModel>> populatePlayer(List<String> addresses);
	RemotingData invoke(String serviceName, RemotingAttribute filter);
}
