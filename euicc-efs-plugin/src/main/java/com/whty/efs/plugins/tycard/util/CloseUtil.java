package com.whty.efs.plugins.tycard.util;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;

public class CloseUtil {

	/**
	 * 按顺序，关闭多个Closeable对象，
	 * 
	 * @param cs
	 * @throws java.io.IOException
	 */
	public static void close(Closeable... cs) throws IOException {
		for (Closeable c : cs) {
			if (null != c) {
				c.close();
			}
		}
	}

	/**
	 * 按顺序，关闭多个Closeable对象，不抛出异常信息
	 * 
	 * @param cs
	 * @throws java.io.IOException
	 */
	public static void quietClose(Closeable... cs) {
		for (Closeable c : cs) {
			if (null != c) {
				try {
					c.close();
				} catch (IOException e) {
					// do nothing
				}
			}
		}
	}

	/**
	 * 关闭socket对象
	 * 
	 * @param socket
	 * @throws java.io.IOException
	 */
	public static void close(Socket socket) throws IOException {
		if (socket != null) {
			socket.close();
		}
	}

	/**
	 * 关闭socket对象，不抛出异常信息
	 * 
	 * @param socket
	 */
	public static void quietClose(Socket socket) {
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				// do nothing
			}
		}
	}
}
