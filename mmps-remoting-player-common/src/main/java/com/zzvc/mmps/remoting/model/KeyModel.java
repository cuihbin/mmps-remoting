package com.zzvc.mmps.remoting.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class KeyModel implements Serializable {
	private String name;
	private String[] values;
	private Map<String, List<KeyModel>> keys;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String[] getValues() {
		return values;
	}
	public void setValues(String[] values) {
		this.values = values;
	}
	public Map<String, List<KeyModel>> getKeys() {
		return keys;
	}
	public void setKeys(Map<String, List<KeyModel>> keys) {
		this.keys = keys;
	}
}
