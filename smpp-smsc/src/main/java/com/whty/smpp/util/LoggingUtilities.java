package com.whty.smpp.util;
import java.util.StringTokenizer;

import org.slf4j.LoggerFactory;

import com.whty.smpp.pdu.Pdu;

/**
 * @ClassName LoggingUtilities
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */

public class LoggingUtilities {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(LoggingUtilities.class);
	public static void hexDump(String title, byte[] m, int l) {
		int p = 0;
		StringBuffer line = new StringBuffer();
		logger.info(title);
		logger.info("Hex dump (" + l + ") bytes:");
		for (int i = 0; i < l; i++) {
			if ((m[i] >= 0) & (m[i] < 16))
				line.append("0");
			line.append(Integer.toString(m[i] & 0xff, 16).toUpperCase());
			if ((++p % 4) == 0) {
				line.append(":");
			}
			if (p == 16) {
				p = 0;
				logger.info(line.toString());
				line = new StringBuffer();
			}
		}
		if (p != 16) {
			logger.info(line.toString());
		}
    }
	

	public static void logDecodedPdu(Pdu p) {
		// Split into max 80 character lines around comma delimited boundaries
		int i=0;
		String pdustring = p.toString();
		StringBuffer line=new StringBuffer();
		String token="";
		StringTokenizer st = new StringTokenizer(pdustring,",");
		while (st.hasMoreElements()) {
			token = st.nextToken();
			if ((line.length() + token.length())<79) {
				if (i > 0) {
					line.append(",");
				}
				line.append(token);
				i++;
			} else {
				logger.info(line.toString());
				line = new StringBuffer();
				line.append(token);
				i = 1;
			}			
		}
		if (line.length() > 0)
			logger.info(line.toString());	
    }
}