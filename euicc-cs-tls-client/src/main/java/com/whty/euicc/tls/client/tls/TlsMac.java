package com.whty.euicc.tls.client.tls;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import com.whty.euicc.tls.client.crypto.Digest;
import com.whty.euicc.tls.client.crypto.macs.HMac;
import com.whty.euicc.tls.client.crypto.params.KeyParameter;

 public class TlsMac
 {
   private long seqNo;
   private HMac mac;
   private short majorVersion;
   private short minorVersion;
   
   public TlsMac(Digest digest, byte[] key_block, int offset, int len, short majorVersion, short minorVersion)
   {
     mac = new HMac(digest);
     KeyParameter param = new KeyParameter(key_block, offset, len);
     mac.init(param);
     seqNo = 0L;
     this.majorVersion = majorVersion;
     this.minorVersion = minorVersion;
   }

   public int getSize()
   {
     return mac.getMacSize();
   }
  











/*    */ 
   public byte[] calculateMac(short type, byte[] message, int offset, int len)
   {
     try
     {
       ByteArrayOutputStream bosMac = new ByteArrayOutputStream();
       TlsUtils.writeUint64(seqNo++, bosMac);
       TlsUtils.writeUint8(type, bosMac);
       TlsUtils.writeVersion(bosMac, majorVersion, minorVersion);
       TlsUtils.writeUint16(len, bosMac);
       bosMac.write(message, offset, len);
       byte[] macData = bosMac.toByteArray();
       
       mac.update(macData, 0, macData.length);
       byte[] result = new byte[mac.getMacSize()];
       mac.doFinal(result, 0);
       
 
       mac.reset();
       return result;
 
     }
     catch (IOException e)
     {
       throw new IllegalStateException("Internal error during mac calculation");
     }
   }
 }

/* Location:           E:\Repository\com\whty\TelecomHttps\0.0.1\TelecomHttps-0.0.1.jar
 * Qualified Name:     com.telecom.http.tls.TlsMac
 * Java Class Version: 6 (50.0)
 * JD-Core Version:    0.7.1
 */