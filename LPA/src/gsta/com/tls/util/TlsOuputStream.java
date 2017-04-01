package gsta.com.tls.util;

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
  
  public void write(byte[] buf, int offset, int len) throws IOException {
    handler.writeData(buf, offset, len);
  }
  
  public void write(int arg0) throws IOException {
    buf[0] = ((byte)arg0);
    write(buf, 0, 1); }
  
  /**
   * @deprecated
   */
  public void cose() throws IOException { handler.close(); }
  
  public void close() throws IOException
  {
    handler.close();
  }
  
  public void flush() throws IOException {
    handler.flush();
  }
}
