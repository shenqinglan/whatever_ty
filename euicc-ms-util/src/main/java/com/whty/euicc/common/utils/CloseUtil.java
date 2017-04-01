package com.whty.euicc.common.utils;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;
import java.util.zip.ZipFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CloseUtil {

	private static Logger log = LoggerFactory.getLogger(CloseUtil.class);

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
					log.debug("close object", e);
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
	public static void close(Socket... sockets) throws IOException {
		if (null != sockets)
			for (Socket socket : sockets)
				if (socket != null)
					socket.close();
	}

	/**
	 * 关闭socket对象，不抛出异常信息
	 * 
	 * @param socket
	 */
	public static void quietClose(Socket... sockets) {
		if (null != sockets)
			for (Socket socket : sockets)
				if (socket != null)
					try {
						socket.close();
					} catch (IOException e) {
						// do nothing
						log.debug("close socket", e);
					}
	}

	/**
	 * 关闭ZipFile对象
	 * 
	 * @param zipFiles
	 * @throws java.io.IOException
	 */
	public static void close(ZipFile... zipFiles) throws IOException {
		if (null != zipFiles)
			for (ZipFile zipFile : zipFiles)
				if (zipFile != null)
					zipFile.close();
	}

	/**
	 * 关闭ZipFile对象，不抛出异常信息
	 * 
	 * @param zipFiles
	 */
	public static void quietClose(ZipFile... zipFiles) {
		if (null != zipFiles)
			for (ZipFile zipFile : zipFiles)
				if (zipFile != null)
					try {
						zipFile.close();
					} catch (IOException e) {
						// do nothing
						log.debug("close socket", e);
					}
	}
}
