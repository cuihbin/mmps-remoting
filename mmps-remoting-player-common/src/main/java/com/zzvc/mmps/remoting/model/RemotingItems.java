package com.zzvc.mmps.remoting.model;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

public class RemotingItems implements Serializable {
	private List<RemotingAttribute> items = new Vector<RemotingAttribute>();
	
	public List<RemotingAttribute> getItems() {
		return items;
	}
	
	public RemotingAttribute addItem() {
		RemotingAttribute item = new RemotingAttribute();
		items.add(item);
		return item;
	}
}
