package com.whty.euicc.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
/**
 * socket帮助类
 * @author Administrator
 *
 */
public class SocketHelper {

	public static void send(String host, int port, byte[] data, SocketResponseHandler handler) throws IOException {
		Socket socket = new Socket();
		OutputStream out = null;
		InputStream in = null;

		try {
			socket.connect(new InetSocketAddress(host, port));
			out = socket.getOutputStream();
			out.write(data);
			out.flush();

			in = socket.getInputStream();

			handler.read(in);

		} finally {
			CloseUtil.close(in);
			CloseUtil.close(out);
		}
	}

	public static class ReadByteArray implements SocketResponseHandler {

		private ByteArrayOutputStream baos = new ByteArrayOutputStream();

		@Override
		public void read(InputStream in) throws IOException {
			byte[] b = new byte[1024];
			int len = -1;
			while ((len = in.read(b)) != -1) {
				baos.write(b, 0, len);
			}
		}

		public byte[] getContent() {
			return baos.toByteArray();
		}
	}

	public static byte[] send(String host, int port, byte[] data) throws IOException {
		ReadByteArray handler = new ReadByteArray();
		
		SocketHelper.send(host, port, data, handler);

		return handler.getContent();
	}

}
