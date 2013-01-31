package com.zzvc.mmps.remoting.client.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.zzvc.mmps.remoting.model.KeyModel;
import com.zzvc.mmps.remoting.model.RemotingAttribute;

public class KeyModelUtil {
	/** Root keys &lt;key_name, corresponding_key_model&gt; */
	private Map<String, List<KeyModel>> keys;
	
	public KeyModelUtil() {
	}

	public KeyModelUtil(Map<String, List<KeyModel>> keys) {
		this.keys = keys;
	}
	
	public void setKeys(Map<String, List<KeyModel>> keys) {
		this.keys = keys;
	};
	
	public Map<String, List<KeyModel>> getKeys() {
		return keys;
	}

	public String getValueScalar(String keyPath) {
		String[] values = getValue(keyPath);
		if (values.length != 1) {
			throw new KeyModelUtilException("Key path reference to a non-scalar value");
		}
		return values[0];
	}
	
	public String[] getValue(String keyPath) {
		List<KeyModel> key = getKey(keyPath);
		if (key.size() > 1) {
			throw new KeyModelUtilException("Key path \"" + keyPath + "\" reference to multiple keys");
		}
		return key.get(0).getValues();
	}
	
	public List<KeyModel> getKey(String keyPath) {
		if (keyPath == null) {
			throw new KeyModelUtilException("Key path cannot be null");
		}
		
		List<String> keyNodes = Arrays.asList(keyPath.split("\\\\"));
		Map<String, List<KeyModel>> currentKeys = keys;
		String currentKeyPath = "";
		for (int i = 0; i < keyNodes.size() - 1; i++) {
			String keyNode = keyNodes.get(i);
			currentKeyPath += "\\\\" + keyNode;
			if (!currentKeys.containsKey(keyNode)) {
				throw new KeyModelUtilException("Key path \"" + currentKeyPath + "\" not found.");
			}
			if (currentKeys.get(keyNode).size() > 1) {
				throw new KeyModelUtilException("Key path \"" + currentKeyPath + "\" reference to multiple keys");
			}
			currentKeys = currentKeys.get(keyNode).get(0).getKeys();
		}
		
		String keyName = keyNodes.get(keyNodes.size() - 1);
		if (!currentKeys.containsKey(keyName)) {
			throw new KeyModelUtilException("Key path \"" + currentKeyPath + "\" not found.");
		}
		return currentKeys.get(keyName);
	}
	
	public boolean matches(RemotingAttribute propertyFilter) {
		if (propertyFilter == null) {
			return true;
		}
		Map<String, String[]> keys = propertyFilter.getAttributes();
		for (String key : keys.keySet()) {
			if (!matches(key, keys.get(key))) {
				return false;
			}
		}
		return true;
	}
	
	private boolean matches(String keyPath, String[] value) {
		try {
			return isValueMatch(getValue(keyPath), value);
		} catch (KeyModelUtilException e) {
			return true;
		}
	}
	
	private boolean isValueMatch(String[] v1, String[] v2) {
		List<String> l1 = Arrays.asList(v1);
		for (String s : v2) {
			if (l1.contains(s)) {
				return true;
			}
		}
		return false;
	}
}
