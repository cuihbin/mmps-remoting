package com.zzvc.mmps.remoting.service;

public interface ServerRemotingServiceManager {
	void startup(String code);
	void heartBeat(String code);
	void shutdown(String code);
}
