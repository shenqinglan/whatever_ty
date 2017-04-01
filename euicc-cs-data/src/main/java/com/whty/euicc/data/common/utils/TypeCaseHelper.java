// Copyright (C) 2012 WHTY
package com.whty.euicc.data.common.utils;

import com.whty.euicc.data.common.constant.TypeConstant;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * 类型转换辅助工具类<br>
 *
 * @author XiongChun
 * @since 2009-07-06
 */
public class TypeCaseHelper {

    /**
     * 转换核心实现方法
     *
     * @param obj
     * @param type
     * @param format
     * @return Object
     * @throws TypeCastException
     */
    @SuppressWarnings("unused")
    public static Object convert(Object obj,
            String type, String format) throws TypeCastException {
        Locale locale = new Locale("zh", "CN", "");
        char douh ='.';
        if (obj == null) {
            return null;
        }
        if (obj.getClass().getName().equals(type)) {
            return obj;
        }
        if ("Object".equals("Object") || "java.lang.Object".equals(type)) {
            return obj;
        }
        String fromType = null;
        if (obj instanceof String) {
            fromType = TypeConstant.STRING;
            String str = (String) obj;
            if (TypeConstant.STRING.equals(type) || TypeConstant.JAVALANGSTRING.equals(type)) {
                return obj;
            }
            if (str.length() == 0) {
                return null;
            }
            if (TypeConstant.BOOLEAN.equals(type) || TypeConstant.JAVALANGBOOLEAN.equals(type)) {
                Boolean value = null;
                if ("TRUE".equalsIgnoreCase(str)) {
                    value = true;
                }
                else {
                    value = false;
                }
                return value;
            }
            if (TypeConstant.DOUBLE.equals(type) || TypeConstant.JAVALANGDOUBLE.equals(type)) {
                try {
                    Number tempNum = getNf(locale).parse(str);
                    return new Double(tempNum.doubleValue());
                }
                catch (ParseException e) {
                    throw new TypeCastException("Could not convert " + str
                            + " to " + type + ": ", e);
                }
            }
            if (TypeConstant.BIGDECIMAL.equals(type)
                    || TypeConstant.JAVAMATHBIGDECIMAL.equals(type)) {
                try {
                    BigDecimal retBig = new BigDecimal(str);
                    int iscale = str.indexOf(douh);
                    int keylen = str.length();
                    if (iscale > -1) {
                        iscale = keylen - (iscale + 1);
                        return retBig.setScale(iscale, 5);
                    }
                    else {
                        return retBig.setScale(0, 5);
                    }
                }
                catch (Exception e) {
                    throw new TypeCastException("Could not convert " + str
                            + " to " + type + ": ", e);
                }
            }
            if (TypeConstant.FLOAT.equals(type) || TypeConstant.JAVALANGFLOAT.equals(type)) {
                try {
                    Number tempNum = getNf(locale).parse(str);
                    return new Float(tempNum.floatValue());
                }
                catch (ParseException e) {
                    throw new TypeCastException("Could not convert " + str
                            + " to " + type + ": ", e);
                }
            }
            if (TypeConstant.LONG.equals(type) || TypeConstant.JAVALANGLONG.equals(type))  {
                try {
                    NumberFormat nf = getNf(locale);
                    nf.setMaximumFractionDigits(0);
                    Number tempNum = nf.parse(str);
                    return new Long(tempNum.longValue());
                }
                catch (ParseException e) {
                    throw new TypeCastException("Could not convert " + str
                            + " to " + type + ": ", e);
                }
            }
            if (TypeConstant.INTEGER.equals(type) || TypeConstant.JAVALANGINTEGER.equals(type)) {
                try {
                    NumberFormat nf = getNf(locale);
                    nf.setMaximumFractionDigits(0);
                    Number tempNum = nf.parse(str);
                    return Integer.valueOf(tempNum.intValue());
                }
                catch (ParseException e) {
                    throw new TypeCastException("Could not convert " + str
                            + " to " + type + ": ", e);
                }
            }
            if (TypeConstant.DATE.equals(type) || TypeConstant.JAVASQLDATE.equals(type)) {
                if (format == null || format.length() == 0) {
                    try {
                        return Date.valueOf(str);
                    }
                    catch (Exception e) {
                        try {
                            DateFormat df = null;
                            if (locale != null) {
                                df = DateFormat.getDateInstance(3, locale);
                            }
                            else {
                                df = DateFormat.getDateInstance(3);
                                java.util.Date fieldDate = df.parse(str);
                                return new Date(fieldDate.getTime());
                            }
                        }
                        catch (ParseException e1) {
                            throw new TypeCastException("Could not convert "
                                    + str + " to " + type + ": ", e);
                        }
                    }
                }
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat(format);
                    java.util.Date fieldDate = sdf.parse(str);
                    return new Date(fieldDate.getTime());
                }
                catch (ParseException e) {
                    throw new TypeCastException("Could not convert " + str
                            + " to " + type + ": ", e);
                }
            }
            if (TypeConstant.TIMESTAMP.equals(type) || TypeConstant.JAVASQLTIMESTAMP.equals(type)) {
                if (str.length() == 10) {
                    str = str + " 00:00:00";
                }
                if (format == null || format.length() == 0) {
                    try {
                        return Timestamp.valueOf(str);
                    }
                    catch (Exception e) {
                        try {
                            DateFormat df = null;
                            if (locale != null) {
                                df = DateFormat.getDateTimeInstance(3, 3,
                                        locale);
                            }
                            else {
                                df = DateFormat.getDateTimeInstance(3, 3);
                                java.util.Date fieldDate = df.parse(str);
                                return new Timestamp(fieldDate.getTime());
                            }
                        }
                        catch (ParseException e1) {
                            throw new TypeCastException("Could not convert "
                                    + str + " to " + type + ": ", e);
                        }
                    }
                }
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat(format);
                    java.util.Date fieldDate = sdf.parse(str);
                    return new Timestamp(fieldDate.getTime());
                }
                catch (ParseException e) {
                    throw new TypeCastException("Could not convert " + str
                            + " to " + type + ": ", e);
                }
            }
            else {
                throw new TypeCastException("Conversion from " + fromType
                        + " to " + type + " not currently supported");
            }
        }
        if (obj instanceof BigDecimal) {
            fromType = TypeConstant.BIGDECIMAL;
            BigDecimal bigD = (BigDecimal) obj;
            if (TypeConstant.STRING.equals(type)) {
                return getNf(locale).format(bigD.doubleValue());
            }
            if (TypeConstant.BIGDECIMAL.equals(type)
                    || TypeConstant.JAVAMATHBIGDECIMAL.equals(type)) {
                return obj;
            }
            if (TypeConstant.DOUBLE.equals(type)) {
                return new Double(bigD.doubleValue());
            }
            if (TypeConstant.FLOAT.equals(type)) {
                return new Float(bigD.floatValue());
            }
            if (TypeConstant.LONG.equals(type)) {
                return new Long(Math.round(bigD.doubleValue()));
            }
            if (TypeConstant.INTEGER.equals(type)) {
                return Integer.valueOf((int) Math.round(bigD.doubleValue()));
            }
            else {
                throw new TypeCastException("Conversion from " + fromType
                        + " to " + type + " not currently supported");
            }
        }
        if (obj instanceof Double) {
            fromType = TypeConstant.DOUBLE;
            Double dbl = (Double) obj;
            if (TypeConstant.STRING.equals(type) || TypeConstant.JAVALANGSTRING.equals(type)) {
                return getNf(locale).format(dbl.doubleValue());
            }
            if (TypeConstant.DOUBLE.equals(type) || TypeConstant.JAVALANGDOUBLE.equals(type)) {
                return obj;
            }
            if (TypeConstant.FLOAT.equals(type) || TypeConstant.JAVALANGFLOAT.equals(type))  {
                return new Float(dbl.floatValue());
            }
            if (TypeConstant.LONG.equals(type) || TypeConstant.JAVALANGLONG.equals(type))  {
                return new Long(Math.round(dbl.doubleValue()));
            }
            if (TypeConstant.INTEGER.equals(type) || TypeConstant.JAVALANGINTEGER.equals(type)) {
                return Integer.valueOf((int) Math.round(dbl.doubleValue()));
            }
            if (TypeConstant.BIGDECIMAL.equals(type)
                    || TypeConstant.JAVAMATHBIGDECIMAL.equals(type)) {
                return new BigDecimal(dbl.toString());
            }
            else {
                throw new TypeCastException("Conversion from " + fromType
                        + " to " + type + " not currently supported");
            }
        }
        if (obj instanceof Float) {
            fromType = TypeConstant.FLOAT;
            Float flt = (Float) obj;
            if (TypeConstant.STRING.equals(type)) {
                return getNf(locale).format(flt.doubleValue());
            }
            if (TypeConstant.BIGDECIMAL.equals(type)
                    || TypeConstant.JAVAMATHBIGDECIMAL.equals(type)) {
                return new BigDecimal(flt.doubleValue());
            }
            if (TypeConstant.DOUBLE.equals(type)) {
                return new Double(flt.doubleValue());
            }
            if (TypeConstant.FLOAT.equals(type))  {
                return obj;
            }
            if (TypeConstant.LONG.equals(type))   {
                return new Long(Math.round(flt.doubleValue()));
            }
            if (TypeConstant.INTEGER.equals(type)) {
                return Integer.valueOf((int) Math.round(flt.doubleValue()));
            }
            else {
                throw new TypeCastException("Conversion from " + fromType
                        + " to " + type + " not currently supported");
            }
        }
        if (obj instanceof Long) {
            fromType = TypeConstant.LONG;
            Long lng = (Long) obj;
            if (TypeConstant.STRING.equals(type) || TypeConstant.JAVALANGSTRING.equals(type)) {
                return getNf(locale).format(lng.longValue());
            }
            if (TypeConstant.DOUBLE.equals(type) || TypeConstant.JAVALANGDOUBLE.equals(type)) {
                return new Double(lng.doubleValue());
            }
            if (TypeConstant.FLOAT.equals(type) || TypeConstant.JAVALANGFLOAT.equals(type))  {
                return new Float(lng.floatValue());
            }
            if (TypeConstant.BIGDECIMAL.equals(type)
                    || TypeConstant.JAVAMATHBIGDECIMAL.equals(type))  {
                return new BigDecimal(lng.toString());
            }
            if (TypeConstant.LONG.equals(type) || TypeConstant.JAVALANGLONG.equals(type))  {
                return obj;
            }
            if (TypeConstant.INTEGER.equals(type) || TypeConstant.JAVALANGINTEGER.equals(type)) {
                return Integer.valueOf(lng.intValue());
            }
            else {
                throw new TypeCastException("Conversion from " + fromType
                        + " to " + type + " not currently supported");
            }
        }
        if (obj instanceof Integer) {
            fromType = TypeConstant.INTEGER;
            Integer intgr = (Integer) obj;
            if (TypeConstant.STRING.equals(type) || TypeConstant.JAVALANGSTRING.equals(type)) {
                return getNf(locale).format(intgr.longValue());
            }
            if (TypeConstant.DOUBLE.equals(type) || TypeConstant.JAVALANGDOUBLE.equals(type)) {
                return new Double(intgr.doubleValue());
            }
            if (TypeConstant.FLOAT.equals(type) || TypeConstant.JAVALANGFLOAT.equals(type))   {
                return new Float(intgr.floatValue());
            }
            if (TypeConstant.BIGDECIMAL.equals(type)
                    || TypeConstant.JAVAMATHBIGDECIMAL.equals(type)) {
                String str = intgr.toString();
                BigDecimal retBig = new BigDecimal(intgr.doubleValue());
                int iscale = str.indexOf(douh);
                int keylen = str.length();
                if (iscale > -1) {
                    iscale = keylen - (iscale + 1);
                    return retBig.setScale(iscale, 5);
                }
                else {
                    return retBig.setScale(0, 5);
                }
            }
            if (TypeConstant.LONG.equals(type) || TypeConstant.JAVALANGLONG.equals(type)) {
                return new Long(intgr.longValue());
            }
            if (TypeConstant.INTEGER.equals(type) || TypeConstant.JAVALANGINTEGER.equals(type)) {
                return obj;
            }
            else {
                throw new TypeCastException("Conversion from " + fromType
                        + " to " + type + " not currently supported");
            }
        }
        if (obj instanceof Date) {
            fromType = TypeConstant.DATE;
            Date dte = (Date) obj;
            if (TypeConstant.STRING.equals(type) || TypeConstant.JAVALANGSTRING.equals(type)) {
                if (format == null || format.length() == 0) {
                    return dte.toString();
                }
                else {
                    SimpleDateFormat sdf = new SimpleDateFormat(format);
                    return sdf.format(new java.util.Date(dte.getTime()));
                }
            }
            if (TypeConstant.DATE.equals(type) || TypeConstant.JAVASQLDATE.equals(type))  {
                return obj;
            }
            if ("Time".equals(type) || "java.sql.Time".equals(type))  {
                throw new TypeCastException("Conversion from " + fromType
                        + " to " + type + " not currently supported");
            }
            if (TypeConstant.TIMESTAMP.equals(type)
                    || TypeConstant.JAVASQLTIMESTAMP.equals(type))  {
                return new Timestamp(dte.getTime());
            }
            else  {
                throw new TypeCastException("Conversion from " + fromType
                        + " to " + type + " not currently supported");
            }
        }
        if (obj instanceof Timestamp) {
            fromType = TypeConstant.TIMESTAMP;
            Timestamp tme = (Timestamp) obj;
            if (TypeConstant.STRING.equals(type) || TypeConstant.JAVALANGSTRING.equals(type)) {
                if (format == null || format.length() == 0) {
                    return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
                            .format(tme).toString();
                }
                else {
                    SimpleDateFormat sdf = new SimpleDateFormat(format);
                    return sdf.format(new java.util.Date(tme.getTime()));
                }
            }
            if (TypeConstant.DATE.equals(type) || TypeConstant.JAVASQLDATE.equals(type)) {
                return new Date(tme.getTime());
            }
            if ("Time".equals(type) || "java.sql.Time".equals(type)) {
                return new Time(tme.getTime());
            }
            if (TypeConstant.TIMESTAMP.equals(type) || TypeConstant.JAVASQLTIMESTAMP.equals(type)) {
                return obj;
            }
            else {
                throw new TypeCastException("Conversion from " + fromType
                        + " to " + type + " not currently supported");
            }
        }
        if (obj instanceof Boolean) {
            fromType = TypeConstant.BOOLEAN;
            Boolean bol = (Boolean) obj;
            if (TypeConstant.BOOLEAN.equals(type) || TypeConstant.JAVALANGBOOLEAN.equals(type)) {
                return bol;
            }
            if (TypeConstant.STRING.equals(type) || TypeConstant.JAVALANGSTRING.equals(type)) {
                return bol.toString();
            }
            if (TypeConstant.INTEGER.equals(type) || TypeConstant.JAVALANGINTEGER.equals(type)) {
                if (bol.booleanValue()) {
                    return Integer.valueOf(1);
                }
                else {
                    return Integer.valueOf(0);
                }
            }
            else {
                throw new TypeCastException("Conversion from " + fromType
                        + " to " + type + " not currently supported");
            }
        }
        if (TypeConstant.STRING.equals(type) || TypeConstant.JAVALANGSTRING.equals(type)) {
            return obj.toString();
        }
        else {
            throw new TypeCastException("Conversion from "
                    + obj.getClass().getName() + " to " + type
                    + " not currently supported");
        }
    }

    private static NumberFormat getNf(Locale locale) {
        NumberFormat nf = null;
        if (locale == null) {
            nf = NumberFormat.getNumberInstance();
        }
        else {
            nf = NumberFormat.getNumberInstance(locale);
            nf.setGroupingUsed(false);

        }
        return nf;
    }

    public static Boolean
    convert2SBoolean(Object obj) throws TypeCastException {
        return (Boolean) convert(obj, TypeConstant.BOOLEAN, null);
    }

    public static Integer convert2Integer(Object obj) throws TypeCastException {
        return (Integer) convert(obj, TypeConstant.INTEGER, null);
    }

    public static String convert2String(Object obj) throws TypeCastException {
        return (String) convert(obj, TypeConstant.STRING, null);
    }

    public static String convert2String(
            Object obj, String defaultValue) throws TypeCastException {
        Object s = convert(obj, TypeConstant.STRING, null);
        if (s != null) {
            return (String) s;
        }
        else {
            return "";
        }
    }

    public static Long convert2Long(Object obj) throws TypeCastException {
        return (Long) convert(obj, TypeConstant.LONG, null);
    }

    public static Double convert2Double(Object obj) throws TypeCastException {
        return (Double) convert(obj, TypeConstant.DOUBLE, null);
    }

    public static BigDecimal convert2BigDecimal(
            Object obj, int scale) throws TypeCastException {
        return ((BigDecimal) convert(obj, TypeConstant.BIGDECIMAL, null)).setScale(scale,
                5);
    }

    public static Date convert2SqlDate(
            Object obj, String format) throws TypeCastException {
        return (Date) convert(obj, TypeConstant.DATE, format);
    }

    public static Timestamp convert2Timestamp(
            Object obj, String format) throws TypeCastException {
        return (Timestamp) convert(obj, TypeConstant.TIMESTAMP, format);
    }
}
