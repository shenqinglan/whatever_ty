package com.whty.tls.util;

import java.io.IOException;
import java.io.OutputStream;

public class TlsOuputStream
  extends OutputStream
{
  private byte[] buf = new byte[1];
  private TlsProtocolHandler handler;
  
  TlsOuputStream(TlsProtocolHandler handler) {
    this.handler = handler;
  }
  
  @Override
public void write(byte[] buf, int offset, int len) throws IOException {
    handler.writeData(buf, offset, len);
  }
  
  @Override
public void write(int arg0) throws IOException {
    buf[0] = ((byte)arg0);
    write(buf, 0, 1); }
  
  /**
   * @deprecated
   */
  @Deprecated
public void cose() throws IOException { handler.close(); }
  
  @Override
public void close() throws IOException
  {
    handler.close();
  }
  
  @Override
public void flush() throws IOException {
    handler.flush();
  }
}
