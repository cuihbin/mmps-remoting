package com.zzvc.mmps.remoting.client.task;

import java.util.ResourceBundle;

import com.zzvc.mmps.task.Task;

public interface ClientInit extends Task {
	void setClientResources(ResourceBundle clientResources);
}
