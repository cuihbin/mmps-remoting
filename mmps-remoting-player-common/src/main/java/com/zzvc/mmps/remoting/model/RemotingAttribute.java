package com.zzvc.mmps.remoting.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RemotingAttribute implements Serializable {
	private Map<String, String[]> attributes = new HashMap<String, String[]>();

	public Map<String, String[]> getAttributes() {
		return attributes;
	}
	
	public void addAttribute(String key, String value) {
		attributes.put(key, new String[] {value});
	}
	public void addAttribute(String key, String[] value) {
		attributes.put(key, value);
	}
	
	public String[] getAttribute(String key) {
		return attributes.get(key);
	}
}
