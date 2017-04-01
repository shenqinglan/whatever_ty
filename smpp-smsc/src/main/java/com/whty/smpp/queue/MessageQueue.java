/****************************************************************************
 * MessageQueue.java
 *
 * Copyright (C) Selenium Software Ltd 2006
 *
 * This file is part of SMPPSim.
 *
 * SMPPSim is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * SMPPSim is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SMPPSim; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * @author martin@seleniumsoftware.com
 * http://www.woolleynet.com
 * http://www.seleniumsoftware.com
 * $Header: /var/cvsroot/SMPPSim2/distribution/2.6.9/SMPPSim/src/java/com/seleniumsoftware/SMPPSim/MessageQueue.java,v 1.1 2012/07/24 14:48:59 martin Exp $
 ****************************************************************************
*/
package com.whty.smpp.queue;

import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.smpp.pdu.Pdu;
/**
 * @ClassName BindReceiver
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class MessageQueue {
    
    private static Logger logger = LoggerFactory.getLogger(MessageQueue.class);
//	private static Logger logger = Logger.getLogger("com.seleniumsoftware.smppsim");
	private String queueName;
	private Vector<Pdu> queue;
	private Object message = new Object();

	public MessageQueue(String name) {
		queueName = name;
		queue = new Vector<Pdu>();
	}

	protected synchronized void addMessage(Pdu message) {
		logger.debug(
			"MessageQueue(" + queueName + ") : adding message to queue");
		queue.add(message);
		notifyAll();
	}

	protected synchronized Object getMessage() {
		while (isEmpty()) {
			try {
				logger.debug(
					"MessageQueue("
						+ queueName
						+ "):  waiting for message from queue");
				wait();
			} catch (InterruptedException e) {
				logger.error(
					"Exception in MessageQueue("
						+ queueName
						+ ") : "
						+ e.getMessage());
				e.printStackTrace();
			}
		}
		message = queue.firstElement();
		queue.remove(message);
		notifyAll();
		return message;
	}

	protected synchronized boolean isEmpty() {
		return queue.isEmpty();
	}

}
