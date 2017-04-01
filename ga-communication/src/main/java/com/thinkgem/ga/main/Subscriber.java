package com.thinkgem.ga.main;

import java.util.concurrent.TimeUnit;

import com.thinkgem.ga.bizdata.GADataSubscriber;
import com.thinkgem.ga.device.GAHighFrequencySubscriber;
import com.thinkgem.ga.device.GALowFrequencySubscriber;

/**
 * 主监听类
 * @author liuwsh
 * @version 2017-02-17
 */
public class Subscriber {
	public static void main(String[] args) {
		new Thread() {
			@Override
			public void run() {
				GADataSubscriber client = new GADataSubscriber();
		        client.start();
			}
		}.start();
		new Thread() {
			@Override
			public void run() {
				while(true) {
					GAHighFrequencySubscriber g = new GAHighFrequencySubscriber();
					g.start();
					try {
						TimeUnit.MINUTES.sleep(2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
		new Thread() {
			@Override
			public void run() {
				GALowFrequencySubscriber g = new GALowFrequencySubscriber();
		    	g.start();
			}
		}.start();
	}
}
