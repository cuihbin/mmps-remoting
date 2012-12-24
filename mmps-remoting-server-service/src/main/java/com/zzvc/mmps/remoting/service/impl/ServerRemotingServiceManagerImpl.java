package com.zzvc.mmps.remoting.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.caucho.services.server.ServiceContext;
import com.zzvc.mmps.dao.ServerDao;
import com.zzvc.mmps.remoting.service.ServerRemotingServiceManager;

@Service("serverRemotingServiceManager")
public class ServerRemotingServiceManagerImpl implements ServerRemotingServiceManager {
	
	@Resource
	private ServerDao serverDao;

	public void startup(String code) {
		serverDao.reportStartup(code, getRemoteAddr());
	}

	public void heartBeat(String code) {
		serverDao.reportHeartbeat(code, getRemoteAddr());
	}

	public void shutdown(String code) {
		serverDao.reportShutdown(code, getRemoteAddr());
	}

	private String getRemoteAddr() {
		try {
			return ServiceContext.getContextRequest().getRemoteAddr();
		} catch (Exception e) {
			return "127.0.0.1";
		}
	}

}
