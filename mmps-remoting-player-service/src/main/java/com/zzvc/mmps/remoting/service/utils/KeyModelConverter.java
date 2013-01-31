package com.zzvc.mmps.remoting.service.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zzvc.mmps.model.Player;
import com.zzvc.mmps.model.PlayerKey;
import com.zzvc.mmps.model.PlayerKeyValue;
import com.zzvc.mmps.remoting.constants.KeyConstants;
import com.zzvc.mmps.remoting.model.KeyModel;

public class KeyModelConverter {
	public static Map<String, List<KeyModel>> convertToKeyModel(Player player) {
		Map<String, List<KeyModel>> keysModel = convertKeys(player.getPlayerKeies());
		keysModel.put(KeyConstants.KEY_PLAYER_ADDRESS, addKey(KeyConstants.KEY_PLAYER_ADDRESS, player.getAddress()));
		keysModel.put(KeyConstants.KEY_PLAYER_NAME, addKey(KeyConstants.KEY_PLAYER_NAME, player.getName()));
		return keysModel;
	}
	
	private static Map<String, List<KeyModel>> convertKeys(Set<PlayerKey> keys) {
		Map<String, List<KeyModel>> _keysMap = new HashMap<String, List<KeyModel>>();
		for (PlayerKey key : keys) {
			if (!_keysMap.containsKey(key.getKeyName())) {
				_keysMap.put(key.getKeyName(), new ArrayList<KeyModel>());
			}
			_keysMap.get(key.getKeyName()).add(convertKey(key));
		}
		return _keysMap;
	}
	
	private static KeyModel convertKey(PlayerKey key) {
		KeyModel _key = new KeyModel();
		_key.setName(key.getKeyName());
		_key.setValues(getPlayerKeyValues(key.getPlayerKeyValues()));
		_key.setKeys(convertKeys(key.getPlayerKeies()));
		return _key;
	}
	
	private static String[] getPlayerKeyValues(Set<PlayerKeyValue> entityPlayerKeyValues) {
		List<String> values = new ArrayList<String>();
		for (PlayerKeyValue entityPlayerKeyValue : entityPlayerKeyValues) {
			values.add(entityPlayerKeyValue.getValue());
		}
		return values.toArray(new String[values.size()]);
	}
	
	private static List<KeyModel> addKey(String keyName, String keyValue) {
		KeyModel keyModel = new KeyModel();
		keyModel.setName(keyName);
		keyModel.setValues(new String[] {keyValue});
		keyModel.setKeys(new HashMap<String, List<KeyModel>>());
		return Arrays.asList(keyModel);
	}
}
