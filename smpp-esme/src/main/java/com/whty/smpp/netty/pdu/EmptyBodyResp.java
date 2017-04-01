package com.whty.smpp.netty.pdu;

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

import com.whty.smpp.netty.exception.RecoverablePduException;
import com.whty.smpp.netty.exception.UnrecoverablePduException;

import org.jboss.netty.buffer.ChannelBuffer;
/**
 * 
 * @ClassName EmptyBodyResp
 * @author Administrator
 * @date 2017-3-10 下午1:33:47
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public abstract class EmptyBodyResp extends PduResponse {

    public EmptyBodyResp(int commandId, String name) {
        super(commandId, name);
    }

    @Override
    public void readBody(ChannelBuffer buffer) throws UnrecoverablePduException, RecoverablePduException {
        // no body
    }

    @Override
    public int calculateByteSizeOfBody() {
        return 0;   // no body
    }

    @Override
    public void writeBody(ChannelBuffer buffer) throws UnrecoverablePduException, RecoverablePduException {
        // no body
    }

    @Override
    public void appendBodyToString(StringBuilder buffer) {
        // no body
    }
    
}