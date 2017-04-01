package com.whty.tls.util;

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

@Override
public Throwable getCause()
{
  return e;
}
}
