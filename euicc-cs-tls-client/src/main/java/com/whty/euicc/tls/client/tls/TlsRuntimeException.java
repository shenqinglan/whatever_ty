package com.whty.euicc.tls.client.tls;

public class TlsRuntimeException extends RuntimeException
{
Throwable e;

public TlsRuntimeException(String message, Throwable e)
{
  super(message);
  
  this.e = e;
}

public TlsRuntimeException(String message)
{
  super(message);
}

public Throwable getCause()
{
  return e;
}
}
