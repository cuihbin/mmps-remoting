package com.zzvc.mmps.remoting.client.task;

import com.zzvc.mmps.remoting.client.utils.KeyModelUtil;

public interface PopulatePlayer extends RemotingServiceInit {
	void setPlayerKeys(KeyModelUtil keyModelUtil);
}
