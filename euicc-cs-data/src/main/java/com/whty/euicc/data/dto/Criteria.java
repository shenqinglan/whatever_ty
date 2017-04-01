// Copyright (C) 2012 WHTY
package com.whty.euicc.data.dto;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import com.whty.euicc.data.common.constant.TypeConstant;
import com.whty.euicc.data.common.utils.TypeCaseHelper;

/**
 * 公用条件查询类<br>
 * 也可以用于MVC层之间的参数传递
 *
 * 这个POJO是后台传参的工具集合类，要了解清楚这里面的含义
 *
 *
 */
public class Criteria {
    /**
     * 存放条件查询值
     */
    private Map<String, Object> condition;

    /**
     * 是否相异
     */
    protected boolean distinct;

    /**
     * 排序字段
     */
    protected String orderByClause;

    private Integer oracleStart;

    private Integer oracleEnd;

    protected Criteria(Criteria example) {
        this.orderByClause = example.orderByClause;
        this.condition = example.condition;
        this.distinct = example.distinct;
        this.oracleStart = example.oracleStart;
        this.oracleEnd = example.oracleEnd;
    }

    public Criteria() {
        condition = new HashMap<String, Object>();
    }

    public void clear() {
        this.condition.clear();
        this.orderByClause = null;
        this.distinct = false;
        this.oracleStart = null;
        this.oracleEnd = null;
    }

    /**
     * @param condition
     *            查询的条件名称
     * @param value
     *            查询的值
     */
    public Criteria put(String condition, Object value) {
        this.condition.put(condition, value);
        return (Criteria) this;
    }

    /**
     * 得到键值，C层和S层的参数传递时取值所用<br>
     * 自行转换对象
     *
     * @param key
     *            键值
     * @return 返回指定键所映射的值
     */
    public Object get(String key) {
        return this.condition.get(key);
    }

    /**
     * @param orderByClause
     *            排序字段
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * @param distinct
     *            是否相异
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public void setCondition(Map<String, Object> condition) {
        this.condition = condition;
    }

    public Map<String, Object> getCondition() {
        return condition;
    }

    /**
     * @param oracleStart
     *            开始记录数
     */
    public void setOracleStart(Integer oracleStart) {
        this.oracleStart = oracleStart;
    }

    /**
     * @param oracleEnd
     *            结束记录数
     */
    public void setOracleEnd(Integer oracleEnd) {
        this.oracleEnd = oracleEnd;
    }

    /**
     * 以BigDecimal类型返回键值
     *BigDecimal类：
	 *             BigDecimal的实现利用到了BigInteger，不同的是BigDecimal加入了小数的概念。
 	 *             一般的float型和Double型数据只可 以用来做科学计算或者是工程计算，由于在商业计算中，要求的数字精度比较高，
     *             所以要用到java.math.BigDecimal类，它支持任何精度的定 点数，可以用它来精确计算货币值。
     * @param key
     *            键名
     * @return BigDecimal 键值
     */
    public BigDecimal getAsBigDecimal(String key) {
        Object obj = TypeCaseHelper.convert(get(key), TypeConstant.BIGDECIMAL, null);
        if (obj != null) {
            return (BigDecimal) obj;
        }
        else {
            return null;
        }
    }

    /**
     * 以Date类型返回键值
     *
     * @param key
     *            键名
     * @return Date 键值
     */
    public Date getAsDate(String key) {
        Object obj = TypeCaseHelper.convert(get(key), TypeConstant.DATE, "yyyy-MM-dd");
        if (obj != null) {
            return (Date) obj;
        }
        else {
            return null;
        }
    }

    /**
     * 以Integer类型返回键值
     *
     * @param key
     *            键名
     * @return Integer 键值
     */
    public Integer getAsInteger(String key) {
        Object obj = TypeCaseHelper.convert(get(key), TypeConstant.INTEGER, null);
        if (obj != null) {
            return (Integer) obj;
        }
        else {
            return null;
        }
    }

    /**
     * 以Long类型返回键值
     *
     * @param key
     *            键名
     * @return Long 键值
     */
    public Long getAsLong(String key) {
        Object obj = TypeCaseHelper.convert(get(key), TypeConstant.LONG, null);
        if (obj != null) {
            return (Long) obj;
        }
        else {
            return null;
        }
    }

    /**
     * 以String类型返回键值
     *
     * @param key
     *            键名
     * @return String 键值
     */
    public String getAsString(String key) {
        Object obj = TypeCaseHelper.convert(get(key), TypeConstant.STRING, null);
        if (obj != null) {
            return (String) obj;
        }
        else {
            return "";
        }
    }

    /**
     * 以Timestamp类型返回键值
     *
     * @param key
     *            键名
     * @return Timestamp 键值
     */
    public Timestamp getAsTimestamp(String key) {
        Object obj = TypeCaseHelper.convert(get(key), TypeConstant.TIMESTAMP,
                "yyyy-MM-dd HH:mm:ss");
        if (obj != null) {
            return (Timestamp) obj;
        }
        else {
            return null;
        }
    }

    /**
     * 以Boolean类型返回键值
     *
     * @param key
     *            键名
     * @return Timestamp 键值
     */
    public Boolean getAsBoolean(String key) {
        Object obj = TypeCaseHelper.convert(get(key), TypeConstant.BOOLEAN, null);
        if (obj != null) {
            return (Boolean) obj;
        }
        else {
            return false;
        }
    }

}
