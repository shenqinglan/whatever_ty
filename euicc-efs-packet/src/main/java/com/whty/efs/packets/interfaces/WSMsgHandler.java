package com.whty.efs.packets.interfaces;

import com.whty.efs.packets.message.EuiccEntity;

public interface WSMsgHandler {
	public byte[] handler(@SuppressWarnings("rawtypes") EuiccEntity tsm) throws Exception;
}
