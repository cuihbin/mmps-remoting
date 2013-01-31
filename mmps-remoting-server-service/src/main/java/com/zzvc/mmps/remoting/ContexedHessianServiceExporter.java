package com.zzvc.mmps.remoting;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.remoting.caucho.HessianServiceExporter;

import com.caucho.services.server.ServiceContext;

/**
 * Save http request in @ServiceContext for later reference
 * 
 * @author cuihbin
 * 
 */
public class ContexedHessianServiceExporter extends HessianServiceExporter {

	@Override
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String serviceId = request.getPathInfo();
		String objectId = request.getParameter("id");
		if (objectId == null)
			objectId = request.getParameter("ejbid");

		ServiceContext.begin(request, serviceId, objectId);
		try {
			super.handleRequest(request, response);
		} finally {
			ServiceContext.end();
		}
	}
}
