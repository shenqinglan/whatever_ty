package com.whty.euicc.tls;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * socket工具类
 * 
 * @author Administrator
 *
 */
public class SocketUtils {
	public static void close(Socket s) {
		try {
			s.shutdownInput();
			s.shutdownOutput();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 读取输入流中特定长度的数据
	 * @param in 输入流
	 * @param length 数据长度
	 * @return byte[]
	 * @throws IOException
	 */
	public static byte[] readBytes(DataInputStream in, int length)
			throws IOException {
		int r = 0;
		byte[] data = new byte[length];
		while (r < length) {
			r += in.read(data, r, length - r);
		}
		return data;
	}

	/*
	 * 向输出流中写数据
	 * @param out 输出流
	 * @param byte[] 要写的data
	 * @param length 数据长度
	 * @throws IOException
	 */
	public static void writeBytes(DataOutputStream out, byte[] bytes, int length)
			throws IOException {
		out.writeInt(length);
		out.write(bytes, 0, length);
		out.flush();
	}
}
