package com.whty.smpp.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.smpp.pdu.PduConstants;
import com.whty.smpp.pdu.SubmitSM;
import com.whty.smpp.pojo.MessageState;
import com.whty.smpp.service.Smsc;
/**
 * @ClassName DeterministicLifeCycleManager
 * @author Administrator
 * @date 2017-1-12 上午10:50:38
 * @Description TODO(决定生命周期管理)
 */
public class DeterministicLifeCycleManager extends LifeCycleManager {

    private static Logger logger = LoggerFactory.getLogger(DeterministicLifeCycleManager.class);
	private Smsc smsc = Smsc.getInstance();
	private int discardThreshold;

	public DeterministicLifeCycleManager() {
		//discardThreshold = Main.getDiscardFromQueueAfter();
		logger.debug("discardThreshold=" + discardThreshold);
	}

	public MessageState setState(MessageState m) {
		// Should a transition take place at all?
		if (isTerminalState(m.getState()))
			return m;	
		byte currentState = m.getState();
		String dest = m.getPdu().getDestination_addr();
		if (dest.substring(0, 1).equals("1")) {
			m.setState(PduConstants.EXPIRED);
			m.setErr(903);
		} else if (dest.substring(0, 1).equals("2")) {
			m.setState(PduConstants.DELETED);
			m.setErr(904);
		} else if (dest.substring(0, 1).equals("3")) {
			m.setState(PduConstants.UNDELIVERABLE);
			m.setErr(901);
		} else if (dest.substring(0, 1).equals("4")) {
			m.setState(PduConstants.ACCEPTED);
			m.setErr(2);
		} else if (dest.substring(0, 1).equals("5")) {
			m.setState(PduConstants.REJECTED);
			m.setErr(902);
		} else {
			m.setState(PduConstants.DELIVERED);
			m.setErr(0);
		}
		if (isTerminalState(m.getState())) {
			m.setFinal_time(System.currentTimeMillis());
			// If delivery receipt requested prepare it....
			SubmitSM p = m.getPdu();
			if (p.getRegistered_delivery_flag() == 1 &&
			    currentState != m.getState()) {
				logger.info("Delivery Receipt requested");
				smsc.prepareDeliveryReceipt(
					p,
					m.getMessage_id(),
					m.getState(),
					1,
					1,
					m.getErr());
			}
		}
		return m;
	}
}