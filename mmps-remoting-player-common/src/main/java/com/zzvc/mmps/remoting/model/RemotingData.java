package com.zzvc.mmps.remoting.model;

import java.io.Serializable;

public class RemotingData implements Serializable {
	private RemotingAttribute properties = new RemotingAttribute();
	
	private RemotingItems items = new RemotingItems();

	public RemotingAttribute getProperties() {
		return properties;
	}

	public RemotingItems getItems() {
		return items;
	}
	
	public void addPropertyValue(String key, String value) {
		properties.addAttribute(key, value);
	}
	
	public void addPropertyValue(String key, String[] value) {
		properties.addAttribute(key, value);
	}
	
	public String getPropertyValueScalar(String key) {
		return properties.getAttribute(key)[0];
	}
	
	public String[] getPropertyValue(String key) {
		return properties.getAttribute(key);
	}

}
