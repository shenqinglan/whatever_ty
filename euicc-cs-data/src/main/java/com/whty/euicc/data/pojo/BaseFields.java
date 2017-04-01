// Copyright (C) 2012 WHTY
package com.whty.euicc.data.pojo;

/**
 * 系统字段设置表(系统参数表)
 */
public class BaseFields {

    /**
     * 字段ID
     */
    private String fieldId;

    /**
     * 字段
     */
    private String field;

    /**
     * 字段名称
     */
    private String fieldName;

    /**
     * 字段值
     */
    private String valueField;

    /**
     * 字段显示值
     */
    private String displayField;

    /**
     * 是否启用
     */
    private Short enabled;

    /**
     * 排序
     */
    private Short sort;

    /**
     * @return 字段ID
     */
    public String getFieldId() {
        return fieldId;
    }

    /**
     * @param fieldId
     *            字段ID
     */
    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    /**
     * @return 字段
     */
    public String getField() {
        return field;
    }

    /**
     * @param field
     *            字段
     */
    public void setField(String field) {
        this.field = field;
    }

    /**
     * @return 字段名称
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * @param fieldName
     *            字段名称
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * @return 字段值
     */
    public String getValueField() {
        return valueField;
    }

    /**
     * @param valueField
     *            字段值
     */
    public void setValueField(String valueField) {
        this.valueField = valueField;
    }

    /**
     * @return 字段显示值
     */
    public String getDisplayField() {
        return displayField;
    }

    /**
     * @param displayField
     *            字段显示值
     */
    public void setDisplayField(String displayField) {
        this.displayField = displayField;
    }

    /**
     * @return 是否启用
     */
    public Short getEnabled() {
        return enabled;
    }

    /**
     * @param enabled
     *            是否启用
     */
    public void setEnabled(Short enabled) {
        this.enabled = enabled;
    }

    /**
     * @return 排序
     */
    public Short getSort() {
        return sort;
    }

    /**
     * @param sort
     *            排序
     */
    public void setSort(Short sort) {
        this.sort = sort;
    }
}
