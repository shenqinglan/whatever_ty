package com.whty.smpp.netty.constants;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
/**
 * 
 * @ClassName SmppSessionState
 * @author Administrator
 * @date 2017-3-10 下午1:38:44
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class SmppSessionState {
	
	static public final int STATE_INITIAL = 0;
	static public final int STATE_OPEN = 1;
	static public final int STATE_BINDING = 2;
	static public final int STATE_BOUND = 3;
	static public final int STATE_UNBINDING = 4;
	static public final int STATE_CLOSED = 5;
	static public final String[] STATES = { "INITIAL", "OPEN", "BINDING",
			"BOUND", "UNBINDING", "CLOSED" };
	
	private final AtomicInteger state;
	private String stateName;
	private final AtomicLong boundTime;

	/**
	 * @return the state
	 */
	public AtomicInteger getState() {
		return state;
	}

	/**
	 * @param state
	 */
	public SmppSessionState() {
		this.state = new AtomicInteger(SmppSessionState.STATE_OPEN);
		this.boundTime = new AtomicLong(0);
	}

	/**
	 * @return the stateName
	 */
	public String getStateName() {
		int s = state.get();
       if (s >= 0 || s < SmppSessionState.STATES.length) {
           return SmppSessionState.STATES[s];
       } else {
           return "UNKNOWN (" + s + ")";
       }
	}

	/**
	 * @return the boundTime
	 */
	public AtomicLong getBoundTime() {
		return boundTime;
	}
	
	public boolean isOpen() {
		return (this.state.get() == SmppSessionState.STATE_OPEN);
	}

	public boolean isBinding() {
		return (this.state.get() == SmppSessionState.STATE_BINDING);
	}

	public boolean isBound() {
		return (this.state.get() == SmppSessionState.STATE_BOUND);
	}

	public boolean isUnbinding() {
		return (this.state.get() == SmppSessionState.STATE_UNBINDING);
	}

	public boolean isClosed() {
		return (this.state.get() == SmppSessionState.STATE_CLOSED);
	}
}
