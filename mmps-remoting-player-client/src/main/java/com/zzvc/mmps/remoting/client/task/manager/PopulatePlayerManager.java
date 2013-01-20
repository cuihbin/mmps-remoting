package com.zzvc.mmps.remoting.client.task.manager;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.springframework.beans.factory.annotation.Autowired;

import com.zzvc.mmps.documents.KeysDocument;
import com.zzvc.mmps.remoting.client.task.PopulatePlayer;
import com.zzvc.mmps.remoting.client.task.impl.RemotingServiceInitTaskSupport;
import com.zzvc.mmps.remoting.client.utils.IpAddressUtil;
import com.zzvc.mmps.remoting.client.utils.KeyModelUtil;
import com.zzvc.mmps.remoting.client.utils.PlayerDocumentConverter;
import com.zzvc.mmps.remoting.client.utils.XmlBeansUtil;
import com.zzvc.mmps.task.TaskException;

public class PopulatePlayerManager extends RemotingServiceInitTaskSupport {
	private static Logger logger = Logger.getLogger(PopulatePlayerManager.class);
	
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
		KeyModelUtil keyModelUtil = null;
		File localFile = new File(new File(getClientResource("client.home")), KEYS_DOCUMENT_PATH);
		
		try {
			keyModelUtil = loadPlayerKeyFromRemotingService();
			storePlayerKeyToLocalFile(keyModelUtil, localFile);
		} catch (Exception e1) {
			logger.error("Error loading player key from remoting service", e1);
			try {
				keyModelUtil = loadPlayerKeyFromLocalFile(localFile);
			} catch (Exception e2) {
				logger.error("Error loading player key from local file", e2);
				throw new TaskException("Error loading player key");
			}
		}
		
		return keyModelUtil;
	}
	
	private KeyModelUtil loadPlayerKeyFromRemotingService() {
		return new KeyModelUtil(remotingService.populatePlayer(getLocalAddresses()));
	}
	
	private KeyModelUtil loadPlayerKeyFromLocalFile(File localFile) throws XmlException, IOException {
		return PlayerDocumentConverter.convertFromDocument(KeysDocument.Factory.parse(localFile));
	}
	
	private void storePlayerKeyToLocalFile(KeyModelUtil keyModelUtil, File localFile) throws IOException {
		XmlBeansUtil.saveXmlDocument(PlayerDocumentConverter.convertToDocument(keyModelUtil), localFile);
	}
	
	private List<String> getLocalAddresses() {
		List<String> addresses = new ArrayList<String>();
		for (InetAddress netAddr : IpAddressUtil.getLocalAddresses()) {
			addresses.add(netAddr.getHostAddress());
		}
		return addresses;
	}
}
