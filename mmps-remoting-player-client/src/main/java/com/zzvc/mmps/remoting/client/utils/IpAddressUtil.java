package com.zzvc.mmps.remoting.client.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;

public class IpAddressUtil {
	
	/**
	 * get all local network addresses.
	 * @return a collection of local network address.
	 */
	public static Collection<InetAddress> getLocalAddresses() {
		Collection<InetAddress> addrs = new ArrayList<InetAddress>();
		
		try {
			for (InetAddress addr : InetAddress.getAllByName(InetAddress.getLocalHost().getHostName())) {
				if (!addr.isLoopbackAddress() && addr.getHostAddress().indexOf(":") == -1) {
					addrs.add(addr);
				}
			}
		} catch (IOException e) {
		}
		if (!addrs.isEmpty()) {
			return addrs;
		}

		Enumeration<NetworkInterface> netInterfaces = null;
		try {
			netInterfaces = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		while (netInterfaces.hasMoreElements()) {
			NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
			if (ni.getInetAddresses().hasMoreElements()) {
				InetAddress addr = (InetAddress) ni.getInetAddresses().nextElement();
				if (!addr.isLoopbackAddress() && addr.getHostAddress().indexOf(":") == -1) {
					addrs.add(addr);
				}
			}
		}
		return addrs;
	}

}
