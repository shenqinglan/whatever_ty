// Copyright (C) 2012 WHTY
package com.whty.efs.data.dto;

import java.util.HashMap;
import java.util.Map;



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

}
