package gsta.com.tls.util;


import gsta.com.tls.crypto.prng.ThreadedSeedGenerator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class TlsProtocolHandler
  implements StreamConnection
{
  protected static final short RL_CHANGE_CIPHER_SPEC = 20;
  protected static final short RL_ALERT = 21;
  protected static final short RL_HANDSHAKE = 22;
  protected static final short RL_APPLICATION_DATA = 23;
  protected static final short HP_HELLO_REQUEST = 0;
  protected static final short HP_CLIENT_HELLO = 1;
  protected static final short HP_SERVER_HELLO = 2;
  protected static final short HP_CERTIFICATE = 11;
  protected static final short HP_SERVER_KEY_EXCHANGE = 12;
  protected static final short HP_CERTIFICATE_REQUEST = 13;
  protected static final short HP_SERVER_HELLO_DONE = 14;
  protected static final short HP_CERTIFICATE_VERIFY = 15;
  protected static final short HP_CLIENT_KEY_EXCHANGE = 16;
  protected static final short HP_FINISHED = 20;
  protected static final short CS_CLIENT_HELLO_SEND = 1;
  protected static final short CS_SERVER_HELLO_RECEIVED = 2;
  protected static final short CS_SERVER_CERTIFICATE_RECEIVED = 3;
  protected static final short CS_SERVER_KEY_EXCHANGE_RECEIVED = 4;
  protected static final short CS_CERTIFICATE_REQUEST_RECEIVED = 5;
  protected static final short CS_SERVER_HELLO_DONE_RECEIVED = 6;
  protected static final short CS_CLIENT_KEY_EXCHANGE_SEND = 7;
  protected static final short CS_CLIENT_CHANGE_CIPHER_SPEC_SEND = 8;
  protected static final short CS_CLIENT_FINISHED_SEND = 9;
  protected static final short CS_SERVER_CHANGE_CIPHER_SPEC_RECEIVED = 10;
  protected static final short CS_DONE = 11;
  protected static final short AP_close_notify = 0;
  protected static final short AP_unexpected_message = 10;
  protected static final short AP_bad_record_mac = 20;
  protected static final short AP_decryption_failed = 21;
  protected static final short AP_record_overflow = 22;
  protected static final short AP_decompression_failure = 30;
  protected static final short AP_handshake_failure = 40;
  protected static final short AP_bad_certificate = 42;
  protected static final short AP_unsupported_certificate = 43;
  protected static final short AP_certificate_revoked = 44;
  protected static final short AP_certificate_expired = 45;
  protected static final short AP_certificate_unknown = 46;
  protected static final short AP_illegal_parameter = 47;
  protected static final short AP_unknown_ca = 48;
  protected static final short AP_access_denied = 49;
  protected static final short AP_decode_error = 50;
  protected static final short AP_decrypt_error = 51;
  protected static final short AP_export_restriction = 60;
  protected static final short AP_protocol_version = 70;
  protected static final short AP_insufficient_security = 71;
  protected static final short AP_internal_error = 80;
  protected static final short AP_user_canceled = 90;
  protected static final short AP_no_renegotiation = 100;
  protected static final short AL_warning = 1;
  protected static final short AL_fatal = 2;
  protected static final byte[] emptybuf = new byte[0];
  protected static final String TLS_ERROR_MESSAGE = "Internal TLS error, this could be an attack";
  private ByteQueue applicationDataQueue = new ByteQueue();

  private ByteQueue changeCipherSpecQueue = new ByteQueue();

  private ByteQueue alertQueue = new ByteQueue();

  private ByteQueue handshakeQueue = new ByteQueue();
  private RecordStream rs;
  private SecureRandom random;
  private TlsInputStream tlsInputStream = null;
  private TlsOuputStream tlsOutputStream = null;

  private boolean closed = false;
  private boolean failedWithError = false;
  private boolean appDataReady = false;
  private byte[] clientRandom;
  private byte[] serverRandom;
  private byte[] ms;
  private TlsCipherSuite chosenCipherSuite = null;
  private byte[] pms;
  private String cipherSuiteName = null;
  private PSK aPsk;
  private short connection_state;

  public TlsProtocolHandler()
  {
  }

  public TlsProtocolHandler(InputStream is, OutputStream os, PSK psk)
  {
    ThreadedSeedGenerator tsg = new ThreadedSeedGenerator();
    this.random = new SecureRandom();

    this.random.setSeed(tsg.generateSeed(20, true));

    this.rs = new RecordStream(this, is, os);

    this.aPsk = psk;
  }

  public TlsProtocolHandler(InputStream is, OutputStream os, SecureRandom sr) {
    this.random = sr;
    this.rs = new RecordStream(this, is, os);
  }

  protected void processData(short protocol, byte[] buf, int offset, int len)
    throws IOException
  {
    switch (protocol) {
    case 20:
      this.changeCipherSpecQueue.addData(buf, offset, len);
      processChangeCipherSpec();
      break;
    case 21:
      this.alertQueue.addData(buf, offset, len);
      processAlert();
      break;
    case 22:
      this.handshakeQueue.addData(buf, offset, len);
      processHandshake();
      break;
    case 23:
      if (!this.appDataReady) {
        failWithError((short)2, (short)10);
      }
      this.applicationDataQueue.addData(buf, offset, len);
      processApplicationData();
    }
  }

  private void processHandshake() throws IOException
  {
    boolean read;
    do {
      read = false;

      if (this.handshakeQueue.size() >= 4) {
        byte[] beginning = new byte[4];
        this.handshakeQueue.read(beginning, 0, 4, 0);
        ByteArrayInputStream bis = new ByteArrayInputStream(beginning);
        short type = TlsUtils.readUint8(bis);
        int len = TlsUtils.readUint24(bis);

        if (this.handshakeQueue.size() >= len + 4)
        {
          byte[] buf = new byte[len];
          this.handshakeQueue.read(buf, 0, len, 4);
          this.handshakeQueue.removeData(len + 4);

          if (type != 20) {
            this.rs.hash1.update(beginning, 0, 4);
            this.rs.hash2.update(beginning, 0, 4);
            this.rs.hash3.update(beginning, 0, 4);
            if (buf.length != 0) {
              this.rs.hash1.update(buf, 0, len);
              this.rs.hash2.update(buf, 0, len);
              this.rs.hash3.update(buf, 0, len);
            }

          }

          ByteArrayInputStream is = new ByteArrayInputStream(buf);

          switch (type)
          {
          case 20:
            System.out.println("client HP_FINISHED paser beging====");
            switch (this.connection_state)
            {
            case 10:
              byte[] receivedChecksum = new byte[12];
              TlsUtils.readFully(receivedChecksum, is);
              assertEmpty(is);

              byte[] checksum = new byte[12];
              byte[] md5andsha1 = new byte[36];

              this.rs.hash2.doFinal(md5andsha1, 0);
              TlsUtils.PRF(this.ms, 
                TlsUtils.toByteArray("server finished"), 
                md5andsha1, checksum);

              for (int i = 0; i < receivedChecksum.length; i++) {
                if (receivedChecksum[i] != checksum[i])
                {
                  failWithError((short)2, 
                    (short)40);
                }
              }
              this.connection_state = 11;
              System.out.println("client HP_FINISHED paser end ====");

              this.appDataReady = true;
              read = true;
              break;
            default:
              failWithError((short)2, (short)10);
            }
            break;
          case 2:
            System.out.println("TLS clinet HP_SERVER_HELLO paser");

            switch (this.connection_state)
            {
            case 1:
              TlsUtils.checkVersion(is, this);

              this.serverRandom = new byte[32];
              TlsUtils.readFully(this.serverRandom, is);

              short sessionIdLength = TlsUtils.readUint8(is);
              byte[] sessionId = new byte[sessionIdLength];
              TlsUtils.readFully(sessionId, is);

              int ciperLength = TlsUtils.readUint8(is);
              int number = TlsUtils.readUint16(is);
              this.chosenCipherSuite = 
                TlsCipherSuiteManager.getCipherSuite(number, this);
              this.cipherSuiteName = 
                TlsCipherSuiteManager.getCipherSuiteName(number);

              short compressionMethodLength = TlsUtils.readUint8(is);
              byte[] compressionMethod = new byte[compressionMethodLength];
              TlsUtils.readFully(compressionMethod, is);
              if (compressionMethod[0] != 0) {
                failWithError(
                  (short)2, 
                  (short)47);
              }
              assertEmpty(is);
              this.connection_state = 2;
              System.out.println("TLS clinet HP_SERVER_HELLO end");
              read = true;
              break;
            default:
              failWithError((short)2, (short)10);
            }
            break;
          case 14:
            System.out.println("HP_SERVER_HELLO_DONE begin====");
            switch (this.connection_state)
            {
            case 2:
              if (this.chosenCipherSuite
                .getKeyExchangeAlgorithm() != 10) {
                failWithError((short)2, 
                  (short)10);
              }

              short ke = this.chosenCipherSuite
                .getKeyExchangeAlgorithm();
              switch (ke)
              {
              case 10:
                short pskIdentityLength = 32;

                byte[] pskIdentity = this.aPsk.getId();

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                TlsUtils.writeUint8((short)16, bos);
                TlsUtils.writeUint24(pskIdentity.length + 2, bos);
                TlsUtils.writeUint16(pskIdentity.length, bos);
                bos.write(pskIdentity);
                byte[] message = bos.toByteArray();
                this.rs.writeMessage((short)22, message, 
                  0, message.length);
                System.out.println("HP_SERVER_HELLO_DONE end===");
                read = true;
                break;
              default:
                failWithError((short)2, 
                  (short)10);
              }

              this.connection_state = 7;
              System.out.println("we send change cipher state===");

              byte[] cmessage = new byte[1];
              cmessage[0] = 1;
              this.rs.writeMessage((short)20, 
                cmessage, 0, cmessage.length);

              this.connection_state = 8;

              byte[] psk = this.aPsk.getKeyValue();
              this.pms = new byte[(psk.length + 2) * 2];
              ByteArrayOutputStream bosPms = new ByteArrayOutputStream();
              TlsUtils.writeUint16(psk.length, bosPms);
              byte[] padding0 = new byte[psk.length];
              bosPms.write(padding0);
              TlsUtils.writeUint16(psk.length, bosPms);
              bosPms.write(psk);
              this.pms = bosPms.toByteArray();

              this.ms = new byte[48];
              byte[] random = new byte[this.clientRandom.length + 
                this.serverRandom.length];
              System.arraycopy(this.clientRandom, 0, random, 0, 
                this.clientRandom.length);
              System.arraycopy(this.serverRandom, 0, random, 
                this.clientRandom.length, this.serverRandom.length);
              TlsUtils.PRF(this.pms, 
                TlsUtils.toByteArray("master secret"), random, 
                this.ms);

              this.rs.writeSuite = this.chosenCipherSuite;
              this.rs.writeSuite.init(this.ms, this.clientRandom, 
                this.serverRandom, (short)3, (short)1);

              byte[] checksum = new byte[12];
              byte[] md5andsha1 = new byte[36];
              this.rs.hash1.doFinal(md5andsha1, 0);
              TlsUtils.PRF(this.ms, 
                TlsUtils.toByteArray("client finished"), 
                md5andsha1, checksum);
              ByteArrayOutputStream bos = new ByteArrayOutputStream();
              TlsUtils.writeUint8((short)20, bos);
              TlsUtils.writeUint24(12, bos);
              bos.write(checksum);
              byte[] message = bos.toByteArray();
              this.rs.writeMessage((short)22, message, 0, 
                message.length);
              this.connection_state = 9;
              System.out.println("CS_CLIENT_FINISHED_SEND end===");
              read = true;
              break;
            default:
              failWithError((short)2, (short)40);
            }
            break;
          case 12:
            break;
          case 0:
          case 1:
          case 15:
          case 16:
          default:
            failWithError((short)2, (short)10);
          }
        }
      }
    }
    while (
      read);
  }

  private void processApplicationData()
  {
  }

  private void processAlert()
    throws IOException
  {
    while (this.alertQueue.size() >= 2)
    {
      byte[] tmp = new byte[2];
      this.alertQueue.read(tmp, 0, 2, 0);
      this.alertQueue.removeData(2);
      short level = tmp[0];
      short description = tmp[1];
      if (level == 2)
      {
        this.failedWithError = true;
        this.closed = true;
        try
        {
          this.rs.close();
        }
        catch (Exception localException)
        {
        }
        failWithError((short)1, (short)0);
      }
      else if (description == 0)
      {
        failWithError((short)1, (short)0);
      }
    }
  }

  private void processChangeCipherSpec()
    throws IOException
  {
    while (this.changeCipherSpecQueue.size() > 0)
    {
      byte[] b = new byte[1];
      this.changeCipherSpecQueue.read(b, 0, 1, 0);
      this.changeCipherSpecQueue.removeData(1);
      if (b[0] != 1)
      {
        failWithError((short)2, (short)10);
      }
      else if (this.connection_state == 9) {
        this.rs.readSuite = this.rs.writeSuite;
        this.connection_state = 10;
      }
      else
      {
        failWithError((short)2, (short)40);
      }
    }
  }

  protected int readApplicationData(byte[] buf, int offset, int len)
    throws IOException
  {
    while (this.applicationDataQueue.size() == 0)
    {
      if (this.failedWithError)
      {
        throw new IOException("Internal TLS error, this could be an attack");
      }
      if (this.closed)
      {
        return -1;
      }
      try {
        this.rs.readData();
      } catch (IOException e) {
        if (!this.closed) {
          failWithError((short)2, (short)80);
        }
        throw e;
      } catch (RuntimeException e) {
        if (!this.closed) {
          failWithError((short)2, (short)80);
        }
        throw e;
      }
    }
    len = Math.min(len, this.applicationDataQueue.size());
    this.applicationDataQueue.read(buf, offset, len, 0);
    this.applicationDataQueue.removeData(len);
    return len;
  }

  protected void writeData(byte[] buf, int offset, int len)
    throws IOException
  {
    if (this.failedWithError) {
      throw new IOException("Internal TLS error, this could be an attack");
    }
    if (this.closed) {
      throw new IOException(
        "Sorry, connection has been closed, you cannot write more data");
    }

    this.rs.writeMessage((short)23, emptybuf, 0, 0);
    do
    {
      int toWrite = Math.min(len, 16384);
      try
      {
        this.rs.writeMessage((short)23, buf, offset, toWrite);
      } catch (IOException e) {
        if (!this.closed) {
          failWithError((short)2, (short)80);
        }
        throw e;
      } catch (RuntimeException e) {
        if (!this.closed) {
          failWithError((short)2, (short)80);
        }
        throw e;
      }
      offset += toWrite;
      len -= toWrite;
    }while (len > 0);
  }

  protected void failWithError(short alertLevel, short alertDescription)
    throws IOException
  {
    if (!this.closed)
    {
      byte[] error = new byte[2];
      error[0] = ((byte)alertLevel);
      error[1] = ((byte)alertDescription);
      this.closed = true;
      if (alertLevel == 2)
      {
        this.failedWithError = true;
      }
      this.rs.writeMessage((short)21, error, 0, 2);
      this.rs.close();
      if (alertLevel == 2)
        throw new IOException("Internal TLS error, this could be an attack");
    }
    else {
      throw new IOException("Internal TLS error, this could be an attack");
    }
  }

  public void close()
    throws IOException
  {
    if (!this.closed)
      failWithError((short)1, (short)0);
  }

  protected void assertEmpty(ByteArrayInputStream is)
    throws IOException
  {
    if (is.available() > 0)
      failWithError((short)2, (short)50);
  }

  protected void flush() throws IOException
  {
    this.rs.flush();
  }

  public DataInputStream openDataInputStream() throws IOException {
    return new DataInputStream(openInputStream());
  }

  public synchronized InputStream openInputStream() throws IOException {
    return this.tlsInputStream;
  }

  public DataOutputStream openDataOutputStream() throws IOException {
    return new DataOutputStream(openOutputStream());
  }

  public synchronized OutputStream openOutputStream() throws IOException {
    return this.tlsOutputStream;
  }

  public String getCipherSuiteName()
  {
    return this.cipherSuiteName;
  }

  public void disConnect()
  {
  }
}


