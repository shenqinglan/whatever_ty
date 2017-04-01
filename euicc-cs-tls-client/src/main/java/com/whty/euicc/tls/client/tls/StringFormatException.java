package com.whty.euicc.tls.client.tls;

public class StringFormatException extends RuntimeException
{
  private static final long serialVersionUID = 1L;

  public StringFormatException(String message)
  {
    super(message);
  }

  public StringFormatException(String message, Throwable cause)
  {
    super(message, cause);
  }

  public StringFormatException(Throwable cause)
  {
    super(cause);
  }
}
