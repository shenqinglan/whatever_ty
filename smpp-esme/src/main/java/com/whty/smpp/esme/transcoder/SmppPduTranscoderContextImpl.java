/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2017-1-20
 * Id: SmppPduTranscoderContextImpl.java,v 1.0 2017-1-20 上午9:43:20 Administrator
 */
package com.whty.smpp.esme.transcoder;

import com.whty.smpp.esme.constants.SmppConstants;

/**
 * @ClassName SmppPduTranscoderContextImpl
 * @author Administrator
 * @date 2017-1-20 上午9:43:20
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class SmppPduTranscoderContextImpl implements ISmppPduTranscoderContext {
	
	private final ISmppPduTranscoderContext contextHandler;

	public SmppPduTranscoderContextImpl() {
		this(null);
	}

	/**
	 * @param overrideContext
	 */
	public SmppPduTranscoderContextImpl(
			ISmppPduTranscoderContext contextHandler) {
		this.contextHandler = contextHandler;
	}

	/** 
	 * @author Administrator
	 * @date 2017-1-20
	 * @param commandStatus
	 * @return
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 * @see com.whty.smpp.netty.transcoder.ISmppPduTranscoderContext#lookupResultMessage(int)
	 */
	@Override
	public String lookupResultMessage(int commandStatus) {
		 String resultMessage = null;
        if (contextHandler != null) {
            resultMessage = contextHandler.lookupResultMessage(commandStatus);
        }
        if (resultMessage == null) {
            resultMessage = SmppConstants.STATUS_MESSAGE_MAP.get(new Integer(commandStatus));
        }
        return resultMessage;
	}

	/** 
	 * @author Administrator
	 * @date 2017-1-20
	 * @param tag
	 * @return
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 * @see com.whty.smpp.netty.transcoder.ISmppPduTranscoderContext#lookupTlvTagName(short)
	 */
	@Override
	public String lookupTlvTagName(short tag) {
		String tagName = null;
        if (contextHandler != null) {
            tagName = contextHandler.lookupTlvTagName(tag);
        }
        if (tagName == null) {
            tagName = SmppConstants.TAG_NAME_MAP.get(new Short(tag));
        }
        return tagName;
	}

}
