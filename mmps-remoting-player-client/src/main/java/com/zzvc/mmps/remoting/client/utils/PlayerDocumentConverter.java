package com.zzvc.mmps.remoting.client.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zzvc.mmps.documents.KeyDocument.Key;
import com.zzvc.mmps.documents.KeysDocument;
import com.zzvc.mmps.documents.KeysDocument.Keys;
import com.zzvc.mmps.remoting.model.KeyModel;

public class PlayerDocumentConverter {

	public static KeyModelUtil convertFromDocument(KeysDocument document) {
		return load(document.getKeys());
	}
	
	public static KeysDocument convertToDocument(KeyModelUtil keysModel) {
		KeysDocument keysRootDoc = KeysDocument.Factory.newInstance();
		Keys keysDoc = keysRootDoc.addNewKeys();
		
		convertKeys(keysDoc, keysModel);
		
		return keysRootDoc;
	}

	private static KeyModelUtil load(Keys keys) {
		KeyModelUtil _keys = new KeyModelUtil();
		_keys.setKeys(load(keys.getKeyArray()));
		return _keys;
	}
	
	private static Map<String, List<KeyModel>> load(Key[] keyArray) {
		Map<String, List<KeyModel>> _keysMap = new HashMap<String, List<KeyModel>>();
		for (int i = 0; i < keyArray.length; i++) {
			if (!_keysMap.containsKey(keyArray[i].getName())) {
				_keysMap.put(keyArray[i].getName(), new ArrayList<KeyModel>());
			}
			_keysMap.get(keyArray[i].getName()).add(load(keyArray[i]));
		}
		return _keysMap;
	}
	
	private static KeyModel load(Key key) {
		KeyModel _key = new KeyModel();
		_key.setName(key.getName());
		_key.setValues(key.getValueArray());
		_key.setKeys(load(key.getKeyArray()));
		return _key;
	}
	
	
	private static void convertKeys(Keys keysDoc, KeyModelUtil keysModel) {
		for (List<KeyModel> keyModelList : keysModel.getKeys().values()) {
			for (KeyModel keyModel : keyModelList) {
				convertKey(keysDoc.addNewKey(), keyModel);
			}
		}
	}
	
	private static void convertKey(Key keyDoc, KeyModel keyModel) {
		keyDoc.setName(keyModel.getName());
		
		for (String value : keyModel.getValues()) {
			keyDoc.addValue(value);
		}
		
		for (List<KeyModel> subKeyModelList : keyModel.getKeys().values()) {
			for (KeyModel subKeyModel : subKeyModelList) {
				convertKey(keyDoc.addNewKey(), subKeyModel);
			}
		}
	}

}
