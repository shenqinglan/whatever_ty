/****************************************************************************
 * DeliverSMResp.java
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
 * $Header: /var/cvsroot/SMPPSim2/distribution/2.6.9/SMPPSim/src/java/com/seleniumsoftware/SMPPSim/pdu/DeliverSMResp.java,v 1.1 2012/07/24 14:48:59 martin Exp $
 ****************************************************************************/

package com.whty.smpp.pdu;
import org.slf4j.LoggerFactory;

import com.whty.smpp.util.PduUtilities;
/**
 * @ClassName DeliverSMResp
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */

public class DeliverSMResp extends Request implements Demarshaller {

  private static org.slf4j.Logger logger = LoggerFactory.getLogger(DeliverSMResp.class);
	// PDU attributes

	private String message_id;

	public void demarshall(byte[] request) throws Exception {

		// demarshall the header
		super.demarshall(request);
		// now set atributes of this specific PDU type
		int inx = 16;
		try {
			message_id = PduUtilities.getStringValueFixedLength(request, inx, 1);
		} catch (Exception e) {
			logger.debug("DELIVER_SM_RESP PDU is malformed. message_id is incorrect");
			throw (e);
		}

	}

	/**
	 * @return
	 */
	public String getMessage_id() {
		return message_id;
	}
	/**
	 * *returns String representation of PDU
	 */
	public String toString() {
		return 	super.toString()+","+
				"system_id="+message_id;
	}

}