package com.whty.smpp.manager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.smpp.pdu.PduConstants;
import com.whty.smpp.pdu.SubmitSM;
import com.whty.smpp.pojo.MessageState;
import com.whty.smpp.service.Smsc;
/**
 * @ClassName LifeCycleManager
 * @author Administrator
 * @date 2017-1-12 上午10:50:38
 * @Description TODO(生命周期管理)
 */
public class LifeCycleManager {

    private static Logger logger = LoggerFactory.getLogger(LifeCycleManager.class);
//	private static Logger logger = Logger.getLogger("com.seleniumsoftware.smppsim");
	private Smsc smsc = Smsc.getInstance();
	private double transitionThreshold;
	private double deliveredThreshold;
	private double undeliverableThreshold;
	private double acceptedThreshold;
	private double rejectedThreshold;
	private double enrouteThreshold;
	private int maxTimeEnroute;
	private int discardThreshold;
	private double transition;
	private double stateChoice;

	public LifeCycleManager() {
		/*double a = (double) Main.getPercentageThatTransition() + 1.0;
		transitionThreshold =  (a / 100);
		logger.debug("transitionThreshold="+transitionThreshold);
		logger.debug("SMPPSim.getPercentageThatTransition()="+Main.getPercentageThatTransition());
		maxTimeEnroute = Main.getMaxTimeEnroute();
		logger.debug("maxTimeEnroute="+maxTimeEnroute);
		discardThreshold = Main.getDiscardFromQueueAfter();
		logger.debug("discardThreshold="+discardThreshold);
		deliveredThreshold = ((double) Main.getPercentageDelivered() / 100);
		logger.debug("deliveredThreshold="+deliveredThreshold);
		// .90
		undeliverableThreshold =
			deliveredThreshold + ((double) Main.getPercentageUndeliverable() / 100);
		logger.debug("undeliverableThreshold="+undeliverableThreshold);
		// .90 + .06 = .96
		acceptedThreshold =
			undeliverableThreshold + ((double) Main.getPercentageAccepted() / 100);
		logger.debug("acceptedThreshold="+acceptedThreshold);
		// .96 + .02 = .98
		rejectedThreshold =
			acceptedThreshold + ((double) Main.getPercentageRejected() / 100);
		logger.debug("rejectedThreshold="+rejectedThreshold);*/
		// .98 + .02 = 1.00
	}
	
	public MessageState setState(MessageState m) {
		// Should a transition take place at all?
		if (isTerminalState(m.getState()))
			return m;	
		byte currentState = m.getState();
		boolean stateSet = false;
		/*for (String pattern : Main.getUndeliverable_phoneNumbers()) {
			if (m.getPdu().getDestination_addr().matches(pattern)) {
				m.setState(PduConstants.UNDELIVERABLE);
				logger.debug("State set to UNDELIVERABLE due to undeliverable pattern match.");
				stateSet = true;
				break;
			}
		}*/
		if (!stateSet) {
			transition = Math.random();
			if ((transition < transitionThreshold)
				|| ((System.currentTimeMillis() - m.getSubmit_time())
					> maxTimeEnroute)) {
				// so which transition should it be?
				stateChoice = Math.random();
				if (stateChoice < deliveredThreshold) {
					m.setState(PduConstants.DELIVERED);
					logger.debug("State set to DELIVERED");
				} else if (stateChoice < undeliverableThreshold) {
					m.setState(PduConstants.UNDELIVERABLE);
					logger.debug("State set to UNDELIVERABLE");
				} else if (stateChoice < acceptedThreshold) {
					m.setState(PduConstants.ACCEPTED);
					logger.debug("State set to ACCEPTED");
				} else {
					m.setState(PduConstants.REJECTED);
					logger.debug("State set to REJECTED");
				}			
			}
		}
		if (isTerminalState(m.getState())) {
			m.setFinal_time(System.currentTimeMillis());
			// If delivery receipt requested prepare it....
			SubmitSM p = m.getPdu();
			boolean dr = ((p.getRegistered_delivery_flag() & 0x01) == 0x01);
			if (dr && currentState != m.getState()) {
				// delivery_receipt requested
				//logger.info("Delivery Receipt requested");
				smsc.prepareDeliveryReceipt(p, m.getMessage_id(), m.getState(),1, 1, m.getErr());
			}
		}

		return m;
	}

	public boolean isTerminalState(byte state) {
		if ((state == PduConstants.DELIVERED)
			|| (state == PduConstants.EXPIRED)
			|| (state == PduConstants.DELETED)
			|| (state == PduConstants.UNDELIVERABLE)
			|| (state == PduConstants.ACCEPTED)
			|| (state == PduConstants.REJECTED))
			return true;
		else
			return false;
	}

	public boolean messageShouldBeDiscarded(MessageState m) {
		long now = System.currentTimeMillis();
		long age = now - m.getSubmit_time();
		if (isTerminalState(m.getState())) {
			if (age	> discardThreshold)
				return true;
		}
		return false;
	}

}