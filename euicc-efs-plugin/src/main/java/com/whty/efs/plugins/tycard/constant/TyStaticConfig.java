package com.whty.efs.plugins.tycard.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class TyStaticConfig {
	private static String tyPersonalIP;
	
	private static int tyPersonalPort;

	/**
	 * @return the tyPersonalIP
	 */
	public static String getTyPersonalIP() {
		return tyPersonalIP;
	}

	/**
	 * @param tyPersonalIP the tyPersonalIP to set
	 */
	@Value("${tyPersonalIP}")
	public  void setTyPersonalIP(String tyPersonalIP) {
		TyStaticConfig.tyPersonalIP = tyPersonalIP;
	}

	/**
	 * @return the tyPersonalPort
	 */
	public static int getTyPersonalPort() {
		return tyPersonalPort;
	}

	/**
	 * @param tyPersonalPort the tyPersonalPort to set
	 */
	@Value("${tyPersonalPort}")
	public  void setTyPersonalPort(int tyPersonalPort) {
		TyStaticConfig.tyPersonalPort = tyPersonalPort;
	}

}
