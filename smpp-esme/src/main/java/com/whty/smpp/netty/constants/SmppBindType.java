package com.whty.smpp.netty.constants;

import org.apache.commons.lang3.StringUtils;

/*
 * #%L
 * ch-smpp
 * %%
 * Copyright (C) 2009 - 2015 Cloudhopper by Twitter
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

/**
 * Enumeration of all SMPP session types.
 * 
 * @author joelauer (twitter: @jjlauer or <a href="http://twitter.com/jjlauer" target=window>http://twitter.com/jjlauer</a>)
 */
public enum SmppBindType {

    TRANSCEIVER,
    TRANSMITTER,
    RECEIVER;
    
    public static SmppBindType getBindType(String bindType) {
    	SmppBindType type = null;
    	if (StringUtils.equals(bindType, "transceiver")) {
    		type = SmppBindType.TRANSCEIVER;
    	} else if (StringUtils.equals(bindType, "transmitter")) {
    		type = SmppBindType.TRANSMITTER;
    	} else if (StringUtils.equals(bindType, "receiver")) {
    		type = SmppBindType.RECEIVER;
    	} else {
    		type = SmppBindType.TRANSCEIVER;
    	}
    	return type;
    }
}
