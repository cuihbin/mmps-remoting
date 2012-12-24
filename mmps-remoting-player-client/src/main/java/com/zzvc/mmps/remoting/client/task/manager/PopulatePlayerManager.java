package com.zzvc.mmps.remoting.client.task.manager;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.zzvc.mmps.documents.KeysDocument;
import com.zzvc.mmps.remoting.client.task.PopulatePlayer;
import com.zzvc.mmps.remoting.client.task.impl.RemotingServiceInitTaskSupport;
import com.zzvc.mmps.remoting.client.utils.KeyModelUtil;
import com.zzvc.mmps.remoting.client.utils.PlayerDocumentConverter;
import com.zzvc.mmps.task.TaskException;
import com.zzvc.mmps.utils.net.IpAddressUtil;
import com.zzvc.mmps.utils.xmlbeans.XmlBeansUtil;

public class PopulatePlayerManager extends RemotingServiceInitTaskSupport {
	private static final String KEYS_DOCUMENT_PATH = "key.xml";
	
	@Autowired(required=false)
	private List<PopulatePlayer> tasks;
	
	private KeyModelUtil keyModelUtil;
	
	public PopulatePlayerManager() {
		super();
		pushBundle("PlayerRemotingClientResources");
	}

	@Override
	public void init() {
		keyModelUtil = loadPlayerKey();
		
		for (PopulatePlayer task : tasks) {
			task.setPlayerKeys(keyModelUtil);
		}
		
	}
	
	private KeyModelUtil loadPlayerKey() {
		KeyModelUtil keyModelUtil = loadPlayerKeyFromRemotingService();
		
		if (keyModelUtil == null) {
			keyModelUtil = loadPlayerKeyFromLocalFile();
		} else {
			savePlayerKeyToLocalFile(keyModelUtil);
		}
		
		if (keyModelUtil == null) {
			throw new TaskException("Cannot load player key");
		}
		
		return keyModelUtil;
	}
	
	private KeyModelUtil loadPlayerKeyFromRemotingService() {
		try {
			KeyModelUtil keyModelUtil = new KeyModelUtil(remotingService.populatePlayer(getLocalAddresses()));
			return keyModelUtil;
		} catch (Exception e) {
			return null;
		}
	}
	
	private KeyModelUtil loadPlayerKeyFromLocalFile() {
		try {
			return PlayerDocumentConverter.convertFromDocument(KeysDocument.Factory.parse(new File(KEYS_DOCUMENT_PATH)));
		} catch (Exception e) {
			return null;
		}
	}
	
	private void savePlayerKeyToLocalFile(KeyModelUtil keyModelUtil) {
		try {
			XmlBeansUtil.saveXmlDocument(PlayerDocumentConverter.convertToDocument(keyModelUtil), KEYS_DOCUMENT_PATH);
		} catch (IOException e) {
		}
	}
	
	private List<String> getLocalAddresses() {
		List<String> addresses = new ArrayList<String>();
		for (InetAddress netAddr : IpAddressUtil.getLocalAddresses()) {
			addresses.add(netAddr.getHostAddress());
		}
		return addresses;
	}
}
