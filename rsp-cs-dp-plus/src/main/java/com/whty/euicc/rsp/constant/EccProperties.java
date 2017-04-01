package com.whty.euicc.rsp.constant;

import com.whty.euicc.common.spring.SpringPropertyPlaceholderConfigurer;

public class EccProperties {
	
	public final static String P = SpringPropertyPlaceholderConfigurer.getStringProperty("P");
	public final static String A = SpringPropertyPlaceholderConfigurer.getStringProperty("A");
	public final static String B = SpringPropertyPlaceholderConfigurer.getStringProperty("B");
	public final static String Gx = SpringPropertyPlaceholderConfigurer.getStringProperty("Gx");
	public final static String Gy = SpringPropertyPlaceholderConfigurer.getStringProperty("Gy");
	public final static String N = SpringPropertyPlaceholderConfigurer.getStringProperty("N");
	public final static String H = SpringPropertyPlaceholderConfigurer.getStringProperty("H");
	public final static String D_CI_ECDSA = SpringPropertyPlaceholderConfigurer.getStringProperty("D_CI_ECDSA");
	public final static String eskEcdsa = SpringPropertyPlaceholderConfigurer.getStringProperty("eskEcdsa");
	public final static String Keylen = SpringPropertyPlaceholderConfigurer.getStringProperty("Keylen");
	public final static String share = SpringPropertyPlaceholderConfigurer.getStringProperty("share");
	public final static String Q_ECASD_ECKA = SpringPropertyPlaceholderConfigurer.getStringProperty("Q_ECASD_ECKA");
	public final static String Qx_ECASD_ECKA = SpringPropertyPlaceholderConfigurer.getStringProperty("Qx_ECASD_ECKA");
	public final static String Qy_ECASD_ECKA = SpringPropertyPlaceholderConfigurer.getStringProperty("Qy_ECASD_ECKA");
	public final static String CERT_DPauth_ECDSA = SpringPropertyPlaceholderConfigurer.getStringProperty("CERT_DPauth_ECDSA");
	public final static String SK_DPauth_ECDSA = SpringPropertyPlaceholderConfigurer.getStringProperty("SK_DPauth_ECDSA");
	public final static String PK_EUICC_ECDSA = SpringPropertyPlaceholderConfigurer.getStringProperty("PK_EUICC_ECDSA");
	public final static String SK_DPpb_ECDSA = SpringPropertyPlaceholderConfigurer.getStringProperty("SK_DPpb_ECDSA");
	public final static String CERT_DPpb_ECDSA = SpringPropertyPlaceholderConfigurer.getStringProperty("CERT_DPpb_ECDSA");
	public final static String EUICC_OTPK = SpringPropertyPlaceholderConfigurer.getStringProperty("euiccOtpk");
	public final static String Qx = SpringPropertyPlaceholderConfigurer.getStringProperty("Qx");
	public final static String Qy = SpringPropertyPlaceholderConfigurer.getStringProperty("Qy");
	public final static String RSP_SHARE = SpringPropertyPlaceholderConfigurer.getStringProperty("rsp_share");
	public final static String RSP_KEY_LEN = SpringPropertyPlaceholderConfigurer.getStringProperty("rsp_key_len");
	public final static String SMDP_OTSK = SpringPropertyPlaceholderConfigurer.getStringProperty("smdpOtsk");
	
}
