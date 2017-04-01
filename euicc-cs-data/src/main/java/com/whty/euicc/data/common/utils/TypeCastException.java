// Copyright (C) 2012 WHTY
package com.whty.euicc.data.common.utils;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * 类型转换工具类<br>
 *
 * @author XiongChun
 * @since 2009-07-06
 * @see RuntimeException
 */
public class TypeCastException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Throwable nested;

    public TypeCastException() {
        nested = null;
    }

    public TypeCastException(String msg) {
        super(msg);
        nested = null;
    }

    public TypeCastException(String msg, Throwable nested) {
        super(msg);
        this.nested = null;
        this.nested = nested;
    }

    public TypeCastException(Throwable nested) {

        this.nested = null;
        this.nested = nested;
    }

    public String getMessage() {
        if (nested != null) {
            return super.getMessage() + " (" + nested.getMessage() + ")";
        }
        else {
            return super.getMessage();
        }
    }

    public String getNonNestedMessage() {
        return super.getMessage();
    }

    public Throwable getNested() {
        if (nested == null) {
            return this;
        }
        else {
            return nested;
        }
    }

    public void printStackTrace() {
        super.printStackTrace();
        if (nested != null) {
            nested.printStackTrace();
        }
    }

    public void printStackTrace(PrintStream ps) {
        super.printStackTrace(ps);
        if (nested != null) {
            nested.printStackTrace(ps);
        }
    }

    public void printStackTrace(PrintWriter pw) {
        super.printStackTrace(pw);
        if (nested != null) {
            nested.printStackTrace(pw);
        }
    }

	public void setNested(Throwable nested) {
		this.nested = nested;
	}
}
