package com.whty.euicc.sms.exception;

public class SMSRuntimeException extends RuntimeException
{
  private static final long serialVersionUID = 1L;

  public SMSRuntimeException(String message)
  {
    super(message);
  }
}
