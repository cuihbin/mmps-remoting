package com.zzvc.mmps.remoting.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.caucho.services.server.ServiceContext;
import com.zzvc.mmps.dao.ServerDao;
import com.zzvc.mmps.model.Server;
import com.zzvc.mmps.remoting.service.ServerRemotingServiceManager;

@Service("serverRemotingServiceManager")
public class ServerRemotingServiceManagerImpl implements
		ServerRemotingServiceManager {
	private static Logger logger = Logger
			.getLogger(ServerRemotingServiceManagerImpl.class);

	@Resource
	private ServerDao serverDao;

	public void startup(String code) {
		reportStartup(code, getRemoteAddr());
	}

	public void heartBeat(String code) {
		reportHeartbeat(code, getRemoteAddr());
	}

	public void shutdown(String code) {
		reportShutdown(code, getRemoteAddr());
	}

	private void reportStartup(String code, String address) {
		Server server = findUniqueByCode(code, address);
		server.setStarted(true);
		server.setStartupTime(new Date());
		server.setLastHeartbeat(new Date());
		serverDao.save(server);
	}

	private void reportHeartbeat(String code, String address) {
		Server server = findUniqueByCode(code, address);
		server.setStarted(true);
		server.setLastHeartbeat(new Date());
		serverDao.save(server);
	}

	private void reportShutdown(String code, String address) {
		Server server = findUniqueByCode(code, address);
		server.setStarted(false);
		server.setShutdownTime(new Date());
		serverDao.save(server);
	}

	private Server findUniqueByCode(String code, String address) {
		List<Server> servers = serverDao.findByCode(code);
		for (Server server : servers) {
			if (!StringUtils.hasText(server.getLastConnectAddress())) {
				server.setLastConnectAddress(address);
			} else if (!address.equals(server.getLastConnectAddress())) {
				logger.warn("Server with code '" + code
						+ "' connected from different address: previously ["
						+ server.getLastConnectAddress() + "], this time ["
						+ address + "]");
				server.setLastConnectAddress(address);
			}
			return server;
		}

		Server server = new Server();
		server.setCode(code);
		server.setName("");
		server.setLastConnectAddress(address);
		server.setEnabled(false);
		return server;
	}

	private String getRemoteAddr() {
		try {
			return ServiceContext.getContextRequest().getRemoteAddr();
		} catch (Exception e) {
			logger.error("Unable to get remote client address", e);
			return "127.0.0.1";
		}
	}
}
