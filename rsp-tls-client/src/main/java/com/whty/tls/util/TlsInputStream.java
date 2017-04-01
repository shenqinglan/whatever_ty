package com.whty.tls.util;

import java.io.IOException;
import java.io.InputStream;

public class TlsInputStream
  extends InputStream
{
  private byte[] buf = new byte[1];
  private TlsProtocolHandler handler = null;
  
  TlsInputStream(TlsProtocolHandler handler) {
    this.handler = handler;
  }
  
  @Override
public int read(byte[] buf, int offset, int len) throws IOException {
    int a = handler.readApplicationData(buf, offset, len);
    return a;
  }
  
  @Override
public int read() throws IOException {
    if (read(buf) < 0) {
      return -1;
    }
    return buf[0] & 0xFF;
  }
  
  @Override
public void close() throws IOException {
    handler.close();
  }
}
