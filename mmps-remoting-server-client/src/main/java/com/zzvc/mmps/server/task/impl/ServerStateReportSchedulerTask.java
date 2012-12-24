package com.zzvc.mmps.server.task.impl;

import org.apache.log4j.Logger;

import com.zzvc.mmps.scheduler.task.SchedulerTask;
import com.zzvc.mmps.server.ServerServiceUtil;
import com.zzvc.mmps.server.ServerServiceUtilException;
import com.zzvc.mmps.task.TaskSupport;

public class ServerStateReportSchedulerTask extends TaskSupport implements SchedulerTask {
	private static Logger logger = Logger.getLogger(ServerStateReportSchedulerTask.class);
	
	private ServerServiceUtil serverServiceUtil;
	
	private boolean heartbeatSucceeded = false;
	
	public ServerStateReportSchedulerTask() {
		super();
		pushBundle("ServerReporterResources");
	}

	@Override
	public void init() {
		serverServiceUtil = new ServerServiceUtil();
		serverServiceUtil.init();
	}

	@Override
	public void afterStartup() {
		try {
			serverServiceUtil.startup();
			infoMessage("server.reporter.state.report.success", new Object[] {findText("server.reporter.state.startup")});
		} catch (ServerServiceUtilException e) {
			logger.error("Error connect Webservice server on init", e);
			warnMessage("server.reporter.state.report.failed", new Object[] {findText("server.reporter.state.startup")});
		}
	}

	@Override
	public void beforeShutdown() {
		try {
			serverServiceUtil.shutdown();
			infoMessage("server.reporter.state.report.success", new Object[] {findText("server.reporter.state.shutdown")});
		} catch (ServerServiceUtilException e) {
			logger.error("Error connect Webservice server on destroy", e);
			warnMessage("server.reporter.state.report.failed", new Object[] {findText("server.reporter.state.shutdown")});
		}
	}

	@Override
	public void onSchedule() {
		try {
			serverServiceUtil.heartbeat();
			if (!heartbeatSucceeded) {
				infoMessage("server.reporter.state.report.success", new Object[] {findText("server.reporter.state.heartbeat")});
				heartbeatSucceeded = true;
			}
		} catch (ServerServiceUtilException e) {
			if (heartbeatSucceeded) {
				logger.error("Error connect Webservice server on heartbeat", e);
				warnMessage("server.reporter.state.report.failed", new Object[] {findText("server.reporter.state.heartbeat")});
				heartbeatSucceeded = false;
			}
		}
	}

}
