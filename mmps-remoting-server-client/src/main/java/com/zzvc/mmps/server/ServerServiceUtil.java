package com.zzvc.mmps.server;

import java.net.MalformedURLException;
import java.util.ResourceBundle;

import com.caucho.hessian.client.HessianProxyFactory;
import com.zzvc.mmps.remoting.service.ServerRemotingServiceManager;

public class ServerServiceUtil {
	private String serverCode;
	
	private String serverServiceAddress;
	
	private ServerRemotingServiceManager serverService;
	
	public void init() {
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("server-service");
			serverCode = bundle.getString("server.code");
			serverServiceAddress = bundle.getString("server.service.address");
		} catch (Exception e) {
			throw new ServerServiceUtilException("Cannot initializing ServerServiceUtil", e);
		}
	}

	public void startup() {
		try {
			getRemotingService().startup(serverCode);
		} catch (Exception e) {
			throw new ServerServiceUtilException("Unknown Exception calling webservice 'ServerService.startup()'", e);
		}
	}
	
	public void heartbeat() {
		try {
			getRemotingService().heartBeat(serverCode);
		} catch (Exception e) {
			throw new ServerServiceUtilException("Unknown Exception calling webservice 'ServerService.heartBeat()'", e);
		}
	}
	
	public void shutdown() {
		try {
			getRemotingService().shutdown(serverCode);
		} catch (Exception e) {
			throw new ServerServiceUtilException("Unknown Exception calling webservice 'ServerService.shutdown()'", e);
		}
	}
	
	private ServerRemotingServiceManager getRemotingService() {
		if (serverService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			try {
				serverService = (ServerRemotingServiceManager) factory.create(ServerRemotingServiceManager.class, serverServiceAddress);
			} catch (MalformedURLException e) {
				throw new ServerServiceUtilException("Invalid service address: " + serverServiceAddress, e);
			}
		}
		return serverService;
	}
	
}
