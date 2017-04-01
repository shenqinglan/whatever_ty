package com.whty.euicc.base.pojo;

import java.util.ArrayList;
import java.util.List;

public class BaseFieldsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BaseFieldsExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andFieldIdIsNull() {
            addCriterion("FIELD_ID is null");
            return (Criteria) this;
        }

        public Criteria andFieldIdIsNotNull() {
            addCriterion("FIELD_ID is not null");
            return (Criteria) this;
        }

        public Criteria andFieldIdEqualTo(String value) {
            addCriterion("FIELD_ID =", value, "fieldId");
            return (Criteria) this;
        }

        public Criteria andFieldIdNotEqualTo(String value) {
            addCriterion("FIELD_ID <>", value, "fieldId");
            return (Criteria) this;
        }

        public Criteria andFieldIdGreaterThan(String value) {
            addCriterion("FIELD_ID >", value, "fieldId");
            return (Criteria) this;
        }

        public Criteria andFieldIdGreaterThanOrEqualTo(String value) {
            addCriterion("FIELD_ID >=", value, "fieldId");
            return (Criteria) this;
        }

        public Criteria andFieldIdLessThan(String value) {
            addCriterion("FIELD_ID <", value, "fieldId");
            return (Criteria) this;
        }

        public Criteria andFieldIdLessThanOrEqualTo(String value) {
            addCriterion("FIELD_ID <=", value, "fieldId");
            return (Criteria) this;
        }

        public Criteria andFieldIdLike(String value) {
            addCriterion("FIELD_ID like", value, "fieldId");
            return (Criteria) this;
        }

        public Criteria andFieldIdNotLike(String value) {
            addCriterion("FIELD_ID not like", value, "fieldId");
            return (Criteria) this;
        }

        public Criteria andFieldIdIn(List<String> values) {
            addCriterion("FIELD_ID in", values, "fieldId");
            return (Criteria) this;
        }

        public Criteria andFieldIdNotIn(List<String> values) {
            addCriterion("FIELD_ID not in", values, "fieldId");
            return (Criteria) this;
        }

        public Criteria andFieldIdBetween(String value1, String value2) {
            addCriterion("FIELD_ID between", value1, value2, "fieldId");
            return (Criteria) this;
        }

        public Criteria andFieldIdNotBetween(String value1, String value2) {
            addCriterion("FIELD_ID not between", value1, value2, "fieldId");
            return (Criteria) this;
        }

        public Criteria andFieldIsNull() {
            addCriterion("FIELD is null");
            return (Criteria) this;
        }

        public Criteria andFieldIsNotNull() {
            addCriterion("FIELD is not null");
            return (Criteria) this;
        }

        public Criteria andFieldEqualTo(String value) {
            addCriterion("FIELD =", value, "field");
            return (Criteria) this;
        }

        public Criteria andFieldNotEqualTo(String value) {
            addCriterion("FIELD <>", value, "field");
            return (Criteria) this;
        }

        public Criteria andFieldGreaterThan(String value) {
            addCriterion("FIELD >", value, "field");
            return (Criteria) this;
        }

        public Criteria andFieldGreaterThanOrEqualTo(String value) {
            addCriterion("FIELD >=", value, "field");
            return (Criteria) this;
        }

        public Criteria andFieldLessThan(String value) {
            addCriterion("FIELD <", value, "field");
            return (Criteria) this;
        }

        public Criteria andFieldLessThanOrEqualTo(String value) {
            addCriterion("FIELD <=", value, "field");
            return (Criteria) this;
        }

        public Criteria andFieldLike(String value) {
            addCriterion("FIELD like", value, "field");
            return (Criteria) this;
        }

        public Criteria andFieldNotLike(String value) {
            addCriterion("FIELD not like", value, "field");
            return (Criteria) this;
        }

        public Criteria andFieldIn(List<String> values) {
            addCriterion("FIELD in", values, "field");
            return (Criteria) this;
        }

        public Criteria andFieldNotIn(List<String> values) {
            addCriterion("FIELD not in", values, "field");
            return (Criteria) this;
        }

        public Criteria andFieldBetween(String value1, String value2) {
            addCriterion("FIELD between", value1, value2, "field");
            return (Criteria) this;
        }

        public Criteria andFieldNotBetween(String value1, String value2) {
            addCriterion("FIELD not between", value1, value2, "field");
            return (Criteria) this;
        }

        public Criteria andFieldNameIsNull() {
            addCriterion("FIELD_NAME is null");
            return (Criteria) this;
        }

        public Criteria andFieldNameIsNotNull() {
            addCriterion("FIELD_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andFieldNameEqualTo(String value) {
            addCriterion("FIELD_NAME =", value, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNameNotEqualTo(String value) {
            addCriterion("FIELD_NAME <>", value, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNameGreaterThan(String value) {
            addCriterion("FIELD_NAME >", value, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNameGreaterThanOrEqualTo(String value) {
            addCriterion("FIELD_NAME >=", value, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNameLessThan(String value) {
            addCriterion("FIELD_NAME <", value, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNameLessThanOrEqualTo(String value) {
            addCriterion("FIELD_NAME <=", value, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNameLike(String value) {
            addCriterion("FIELD_NAME like", value, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNameNotLike(String value) {
            addCriterion("FIELD_NAME not like", value, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNameIn(List<String> values) {
            addCriterion("FIELD_NAME in", values, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNameNotIn(List<String> values) {
            addCriterion("FIELD_NAME not in", values, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNameBetween(String value1, String value2) {
            addCriterion("FIELD_NAME between", value1, value2, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNameNotBetween(String value1, String value2) {
            addCriterion("FIELD_NAME not between", value1, value2, "fieldName");
            return (Criteria) this;
        }

        public Criteria andValueFieldIsNull() {
            addCriterion("VALUE_FIELD is null");
            return (Criteria) this;
        }

        public Criteria andValueFieldIsNotNull() {
            addCriterion("VALUE_FIELD is not null");
            return (Criteria) this;
        }

        public Criteria andValueFieldEqualTo(String value) {
            addCriterion("VALUE_FIELD =", value, "valueField");
            return (Criteria) this;
        }

        public Criteria andValueFieldNotEqualTo(String value) {
            addCriterion("VALUE_FIELD <>", value, "valueField");
            return (Criteria) this;
        }

        public Criteria andValueFieldGreaterThan(String value) {
            addCriterion("VALUE_FIELD >", value, "valueField");
            return (Criteria) this;
        }

        public Criteria andValueFieldGreaterThanOrEqualTo(String value) {
            addCriterion("VALUE_FIELD >=", value, "valueField");
            return (Criteria) this;
        }

        public Criteria andValueFieldLessThan(String value) {
            addCriterion("VALUE_FIELD <", value, "valueField");
            return (Criteria) this;
        }

        public Criteria andValueFieldLessThanOrEqualTo(String value) {
            addCriterion("VALUE_FIELD <=", value, "valueField");
            return (Criteria) this;
        }

        public Criteria andValueFieldLike(String value) {
            addCriterion("VALUE_FIELD like", value, "valueField");
            return (Criteria) this;
        }

        public Criteria andValueFieldNotLike(String value) {
            addCriterion("VALUE_FIELD not like", value, "valueField");
            return (Criteria) this;
        }

        public Criteria andValueFieldIn(List<String> values) {
            addCriterion("VALUE_FIELD in", values, "valueField");
            return (Criteria) this;
        }

        public Criteria andValueFieldNotIn(List<String> values) {
            addCriterion("VALUE_FIELD not in", values, "valueField");
            return (Criteria) this;
        }

        public Criteria andValueFieldBetween(String value1, String value2) {
            addCriterion("VALUE_FIELD between", value1, value2, "valueField");
            return (Criteria) this;
        }

        public Criteria andValueFieldNotBetween(String value1, String value2) {
            addCriterion("VALUE_FIELD not between", value1, value2, "valueField");
            return (Criteria) this;
        }

        public Criteria andDisplayFieldIsNull() {
            addCriterion("DISPLAY_FIELD is null");
            return (Criteria) this;
        }

        public Criteria andDisplayFieldIsNotNull() {
            addCriterion("DISPLAY_FIELD is not null");
            return (Criteria) this;
        }

        public Criteria andDisplayFieldEqualTo(String value) {
            addCriterion("DISPLAY_FIELD =", value, "displayField");
            return (Criteria) this;
        }

        public Criteria andDisplayFieldNotEqualTo(String value) {
            addCriterion("DISPLAY_FIELD <>", value, "displayField");
            return (Criteria) this;
        }

        public Criteria andDisplayFieldGreaterThan(String value) {
            addCriterion("DISPLAY_FIELD >", value, "displayField");
            return (Criteria) this;
        }

        public Criteria andDisplayFieldGreaterThanOrEqualTo(String value) {
            addCriterion("DISPLAY_FIELD >=", value, "displayField");
            return (Criteria) this;
        }

        public Criteria andDisplayFieldLessThan(String value) {
            addCriterion("DISPLAY_FIELD <", value, "displayField");
            return (Criteria) this;
        }

        public Criteria andDisplayFieldLessThanOrEqualTo(String value) {
            addCriterion("DISPLAY_FIELD <=", value, "displayField");
            return (Criteria) this;
        }

        public Criteria andDisplayFieldLike(String value) {
            addCriterion("DISPLAY_FIELD like", value, "displayField");
            return (Criteria) this;
        }

        public Criteria andDisplayFieldNotLike(String value) {
            addCriterion("DISPLAY_FIELD not like", value, "displayField");
            return (Criteria) this;
        }

        public Criteria andDisplayFieldIn(List<String> values) {
            addCriterion("DISPLAY_FIELD in", values, "displayField");
            return (Criteria) this;
        }

        public Criteria andDisplayFieldNotIn(List<String> values) {
            addCriterion("DISPLAY_FIELD not in", values, "displayField");
            return (Criteria) this;
        }

        public Criteria andDisplayFieldBetween(String value1, String value2) {
            addCriterion("DISPLAY_FIELD between", value1, value2, "displayField");
            return (Criteria) this;
        }

        public Criteria andDisplayFieldNotBetween(String value1, String value2) {
            addCriterion("DISPLAY_FIELD not between", value1, value2, "displayField");
            return (Criteria) this;
        }

        public Criteria andEnabledIsNull() {
            addCriterion("ENABLED is null");
            return (Criteria) this;
        }

        public Criteria andEnabledIsNotNull() {
            addCriterion("ENABLED is not null");
            return (Criteria) this;
        }

        public Criteria andEnabledEqualTo(Short value) {
            addCriterion("ENABLED =", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledNotEqualTo(Short value) {
            addCriterion("ENABLED <>", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledGreaterThan(Short value) {
            addCriterion("ENABLED >", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledGreaterThanOrEqualTo(Short value) {
            addCriterion("ENABLED >=", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledLessThan(Short value) {
            addCriterion("ENABLED <", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledLessThanOrEqualTo(Short value) {
            addCriterion("ENABLED <=", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledIn(List<Short> values) {
            addCriterion("ENABLED in", values, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledNotIn(List<Short> values) {
            addCriterion("ENABLED not in", values, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledBetween(Short value1, Short value2) {
            addCriterion("ENABLED between", value1, value2, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledNotBetween(Short value1, Short value2) {
            addCriterion("ENABLED not between", value1, value2, "enabled");
            return (Criteria) this;
        }

        public Criteria andSortIsNull() {
            addCriterion("SORT is null");
            return (Criteria) this;
        }

        public Criteria andSortIsNotNull() {
            addCriterion("SORT is not null");
            return (Criteria) this;
        }

        public Criteria andSortEqualTo(Short value) {
            addCriterion("SORT =", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotEqualTo(Short value) {
            addCriterion("SORT <>", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThan(Short value) {
            addCriterion("SORT >", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThanOrEqualTo(Short value) {
            addCriterion("SORT >=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThan(Short value) {
            addCriterion("SORT <", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThanOrEqualTo(Short value) {
            addCriterion("SORT <=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortIn(List<Short> values) {
            addCriterion("SORT in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotIn(List<Short> values) {
            addCriterion("SORT not in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortBetween(Short value1, Short value2) {
            addCriterion("SORT between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotBetween(Short value1, Short value2) {
            addCriterion("SORT not between", value1, value2, "sort");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}