package com.whty.euicc.base.pojo;

public class BaseFields {
    private String fieldId;

    private String field;

    private String fieldName;

    private String valueField;

    private String displayField;

    private Short enabled;

    private Short sort;

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId == null ? null : fieldId.trim();
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field == null ? null : field.trim();
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName == null ? null : fieldName.trim();
    }

    public String getValueField() {
        return valueField;
    }

    public void setValueField(String valueField) {
        this.valueField = valueField == null ? null : valueField.trim();
    }

    public String getDisplayField() {
        return displayField;
    }

    public void setDisplayField(String displayField) {
        this.displayField = displayField == null ? null : displayField.trim();
    }

    public Short getEnabled() {
        return enabled;
    }

    public void setEnabled(Short enabled) {
        this.enabled = enabled;
    }

    public Short getSort() {
        return sort;
    }

    public void setSort(Short sort) {
        this.sort = sort;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BaseFields [fieldId=" + fieldId + ", field=" + field
				+ ", fieldName=" + fieldName + ", valueField=" + valueField
				+ ", displayField=" + displayField + ", enabled=" + enabled
				+ ", sort=" + sort + "]";
	}

}