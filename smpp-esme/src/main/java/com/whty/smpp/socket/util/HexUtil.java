package com.whty.smpp.socket.util;
/**
 * 
 * @ClassName HexUtil
 * @author Administrator
 * @date 2017-3-10 下午1:45:09
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class HexUtil {

	public static char[] HEX_TABLE = { '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	public static String toHexString(byte[] bytes) {
		if (bytes == null) {
			return "";
		}
		return toHexString(bytes, 0, bytes.length);
	}

	public static String toHexString(byte[] bytes, int offset, int length) {
		if (bytes == null) {
			return "";
		}
		assertOffsetLengthValid(offset, length, bytes.length);
		// each byte is 2 chars in string
		StringBuilder buffer = new StringBuilder(length * 2);
		appendHexString(buffer, bytes, offset, length);
		return buffer.toString();
	}

	public static void appendHexString(StringBuilder buffer, byte[] bytes) {
		assertNotNull(buffer);
		if (bytes == null) {
			return; // do nothing (a noop)
		}
		appendHexString(buffer, bytes, 0, bytes.length);
	}

	static public void appendHexString(StringBuilder buffer, byte[] bytes,
			int offset, int length) {
		assertNotNull(buffer);
		if (bytes == null) {
			return; // do nothing (a noop)
		}
		assertOffsetLengthValid(offset, length, bytes.length);
		int end = offset + length;
		for (int i = offset; i < end; i++) {
			int nibble1 = (bytes[i] & 0xF0) >>> 4;
			int nibble0 = (bytes[i] & 0x0F);
			buffer.append(HEX_TABLE[nibble1]);
			buffer.append(HEX_TABLE[nibble0]);
		}
	}

	static public String toHexString(byte value) {
		StringBuilder buffer = new StringBuilder(2);
		appendHexString(buffer, value);
		return buffer.toString();
	}

	static public void appendHexString(StringBuilder buffer, byte value) {
		assertNotNull(buffer);
		int nibble = (value & 0xF0) >>> 4;
		buffer.append(HEX_TABLE[nibble]);
		nibble = (value & 0x0F);
		buffer.append(HEX_TABLE[nibble]);
	}

	static public String toHexString(short value) {
		StringBuilder buffer = new StringBuilder(4);
		appendHexString(buffer, value);
		return buffer.toString();
	}

	static public void appendHexString(StringBuilder buffer, short value) {
		assertNotNull(buffer);
		int nibble = (value & 0xF000) >>> 12;
		buffer.append(HEX_TABLE[nibble]);
		nibble = (value & 0x0F00) >>> 8;
		buffer.append(HEX_TABLE[nibble]);
		nibble = (value & 0x00F0) >>> 4;
		buffer.append(HEX_TABLE[nibble]);
		nibble = (value & 0x000F);
		buffer.append(HEX_TABLE[nibble]);
	}

	static public String toHexString(int value) {
		StringBuilder buffer = new StringBuilder(8);
		appendHexString(buffer, value);
		return buffer.toString();
	}

	static public void appendHexString(StringBuilder buffer, int value) {
		assertNotNull(buffer);
		int nibble = (value & 0xF0000000) >>> 28;
		buffer.append(HEX_TABLE[nibble]);
		nibble = (value & 0x0F000000) >>> 24;
		buffer.append(HEX_TABLE[nibble]);
		nibble = (value & 0x00F00000) >>> 20;
		buffer.append(HEX_TABLE[nibble]);
		nibble = (value & 0x000F0000) >>> 16;
		buffer.append(HEX_TABLE[nibble]);
		nibble = (value & 0x0000F000) >>> 12;
		buffer.append(HEX_TABLE[nibble]);
		nibble = (value & 0x00000F00) >>> 8;
		buffer.append(HEX_TABLE[nibble]);
		nibble = (value & 0x000000F0) >>> 4;
		buffer.append(HEX_TABLE[nibble]);
		nibble = (value & 0x0000000F);
		buffer.append(HEX_TABLE[nibble]);
	}

	static public String toHexString(long value) {
		StringBuilder buffer = new StringBuilder(16);
		appendHexString(buffer, value);
		return buffer.toString();
	}

	static public void appendHexString(StringBuilder buffer, long value) {
		appendHexString(buffer, (int) ((value & 0xFFFFFFFF00000000L) >>> 32));
		appendHexString(buffer, (int) (value & 0x00000000FFFFFFFFL));
	}

	static private void assertNotNull(StringBuilder buffer) {
		if (buffer == null) {
			throw new NullPointerException("The buffer cannot be null");
		}
	}

	static private void assertOffsetLengthValid(int offset, int length,
			int arrayLength) {
		if (offset < 0) {
			throw new IllegalArgumentException("The array offset was negative");
		}
		if (length < 0) {
			throw new IllegalArgumentException("The array length was negative");
		}
		if (offset + length > arrayLength) {
			throw new ArrayIndexOutOfBoundsException(
					"The array offset+length would access past end of array");
		}
	}

	static public int hexCharToIntValue(char c) {
		if (c == '0') {
			return 0;
		} else if (c == '1') {
			return 1;
		} else if (c == '2') {
			return 2;
		} else if (c == '3') {
			return 3;
		} else if (c == '4') {
			return 4;
		} else if (c == '5') {
			return 5;
		} else if (c == '6') {
			return 6;
		} else if (c == '7') {
			return 7;
		} else if (c == '8') {
			return 8;
		} else if (c == '9') {
			return 9;
		} else if (c == 'A' || c == 'a') {
			return 10;
		} else if (c == 'B' || c == 'b') {
			return 11;
		} else if (c == 'C' || c == 'c') {
			return 12;
		} else if (c == 'D' || c == 'd') {
			return 13;
		} else if (c == 'E' || c == 'e') {
			return 14;
		} else if (c == 'F' || c == 'f') {
			return 15;
		} else {
			throw new IllegalArgumentException("The character [" + c
					+ "] does not represent a valid hex digit");
		}
	}

	public static byte[] toByteArray(CharSequence hexString) {
		if (hexString == null) {
			return null;
		}
		return toByteArray(hexString, 0, hexString.length());
	}

	public static byte[] toByteArray(CharSequence hexString, int offset,
			int length) {
		if (hexString == null) {
			return null;
		}
		assertOffsetLengthValid(offset, length, hexString.length());

		// a hex string must be in increments of 2
		if ((length % 2) != 0) {
			throw new IllegalArgumentException(
					"The hex string did not contain an even number of characters [actual="
							+ length + "]");
		}

		// convert hex string to byte array
		byte[] bytes = new byte[length / 2];

		int j = 0;
		int end = offset + length;

		for (int i = offset; i < end; i += 2) {
			int highNibble = hexCharToIntValue(hexString.charAt(i));
			int lowNibble = hexCharToIntValue(hexString.charAt(i + 1));
			bytes[j++] = (byte) (((highNibble << 4) & 0xF0) | (lowNibble & 0x0F));
		}
		return bytes;
	}
}
