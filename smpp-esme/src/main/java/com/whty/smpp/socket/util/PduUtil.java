package com.whty.smpp.socket.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.smpp.socket.constants.Address;
import com.whty.smpp.socket.constants.SmppConstants;
/**
 * 
 * @ClassName PduUtil
 * @author Administrator
 * @date 2017-3-10 下午1:45:05
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class PduUtil {
	
	private static Logger logger = LoggerFactory.getLogger(PduUtil.class);
	
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
	
	public static final int getBytesAsInt(byte i_byte) {
		return i_byte & 0xff;
	}
	
	public static int getIntegerValue(byte[] msg, int start, int len) throws Exception {
		if (len > 4) {
			throw new Exception("Invalid length (" + len + ") for integer conversion");
		}
		int inx = start;
		byte[] ba = new byte[4];
		int newInt = 0x00000000;
		int l = len - 1;
		int ia = 3;

		for (int i = l; i >= 0; i--) {
			ba[ia] = msg[inx + i];
			ia--;
		}
		for (int i = ia; i >= 0; i--) {
			ba[i] = 0x00;
		}

		newInt |= ((((int) ba[0]) << 24) & 0xff000000);
		newInt |= ((((int) ba[1]) << 16) & 0x00ff0000);
		newInt |= ((((int) ba[2]) << 8) & 0x0000ff00);
		newInt |= (((int) ba[3]) & 0x000000ff);

		return newInt;
	}

    /**
     * Calculates size of a "C-String" by returning the length of the String
     * plus 1 (for the NULL byte).  If the parameter is null, will return 1.
     * @param value
     * @return
     */
    static public int calculateByteSizeOfNullTerminatedString(String value) {
        if (value == null) {
            return 1;
        }
        return value.length() + 1;
    }

    /**
     * Calculates the byte size of an Address.  Safe to call with nulls as a
     * parameter since this will then just return the size of an empty address.
     * @param value
     * @return
     */
    static public int calculateByteSizeOfAddress(Address value) {
        if (value == null) {
            return SmppConstants.EMPTY_ADDRESS.calculateByteSize();
        } else {
            return value.calculateByteSize();
        }
    }

    static public boolean isRequestCommandId(int commandId) {
        // if the 31st bit is not set, this is a request
        return ((commandId & SmppConstants.PDU_CMD_ID_RESP_MASK) == 0);
    }

    static public boolean isResponseCommandId(int commandId) {
        // if the 31st bit is not set, this is a request
        return ((commandId & SmppConstants.PDU_CMD_ID_RESP_MASK) == SmppConstants.PDU_CMD_ID_RESP_MASK);
    }
}
