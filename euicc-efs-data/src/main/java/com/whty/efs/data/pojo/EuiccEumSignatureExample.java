package com.whty.efs.data.pojo;

import java.util.ArrayList;
import java.util.List;

public class EuiccEumSignatureExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EuiccEumSignatureExample() {
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

        public Criteria andSignatureIdIsNull() {
            addCriterion("signature_id is null");
            return (Criteria) this;
        }

        public Criteria andSignatureIdIsNotNull() {
            addCriterion("signature_id is not null");
            return (Criteria) this;
        }

        public Criteria andSignatureIdEqualTo(String value) {
            addCriterion("signature_id =", value, "signatureId");
            return (Criteria) this;
        }

        public Criteria andSignatureIdNotEqualTo(String value) {
            addCriterion("signature_id <>", value, "signatureId");
            return (Criteria) this;
        }

        public Criteria andSignatureIdGreaterThan(String value) {
            addCriterion("signature_id >", value, "signatureId");
            return (Criteria) this;
        }

        public Criteria andSignatureIdGreaterThanOrEqualTo(String value) {
            addCriterion("signature_id >=", value, "signatureId");
            return (Criteria) this;
        }

        public Criteria andSignatureIdLessThan(String value) {
            addCriterion("signature_id <", value, "signatureId");
            return (Criteria) this;
        }

        public Criteria andSignatureIdLessThanOrEqualTo(String value) {
            addCriterion("signature_id <=", value, "signatureId");
            return (Criteria) this;
        }

        public Criteria andSignatureIdLike(String value) {
            addCriterion("signature_id like", value, "signatureId");
            return (Criteria) this;
        }

        public Criteria andSignatureIdNotLike(String value) {
            addCriterion("signature_id not like", value, "signatureId");
            return (Criteria) this;
        }

        public Criteria andSignatureIdIn(List<String> values) {
            addCriterion("signature_id in", values, "signatureId");
            return (Criteria) this;
        }

        public Criteria andSignatureIdNotIn(List<String> values) {
            addCriterion("signature_id not in", values, "signatureId");
            return (Criteria) this;
        }

        public Criteria andSignatureIdBetween(String value1, String value2) {
            addCriterion("signature_id between", value1, value2, "signatureId");
            return (Criteria) this;
        }

        public Criteria andSignatureIdNotBetween(String value1, String value2) {
            addCriterion("signature_id not between", value1, value2, "signatureId");
            return (Criteria) this;
        }

        public Criteria andSingedInfoIdIsNull() {
            addCriterion("singed_info_id is null");
            return (Criteria) this;
        }

        public Criteria andSingedInfoIdIsNotNull() {
            addCriterion("singed_info_id is not null");
            return (Criteria) this;
        }

        public Criteria andSingedInfoIdEqualTo(String value) {
            addCriterion("singed_info_id =", value, "singedInfoId");
            return (Criteria) this;
        }

        public Criteria andSingedInfoIdNotEqualTo(String value) {
            addCriterion("singed_info_id <>", value, "singedInfoId");
            return (Criteria) this;
        }

        public Criteria andSingedInfoIdGreaterThan(String value) {
            addCriterion("singed_info_id >", value, "singedInfoId");
            return (Criteria) this;
        }

        public Criteria andSingedInfoIdGreaterThanOrEqualTo(String value) {
            addCriterion("singed_info_id >=", value, "singedInfoId");
            return (Criteria) this;
        }

        public Criteria andSingedInfoIdLessThan(String value) {
            addCriterion("singed_info_id <", value, "singedInfoId");
            return (Criteria) this;
        }

        public Criteria andSingedInfoIdLessThanOrEqualTo(String value) {
            addCriterion("singed_info_id <=", value, "singedInfoId");
            return (Criteria) this;
        }

        public Criteria andSingedInfoIdLike(String value) {
            addCriterion("singed_info_id like", value, "singedInfoId");
            return (Criteria) this;
        }

        public Criteria andSingedInfoIdNotLike(String value) {
            addCriterion("singed_info_id not like", value, "singedInfoId");
            return (Criteria) this;
        }

        public Criteria andSingedInfoIdIn(List<String> values) {
            addCriterion("singed_info_id in", values, "singedInfoId");
            return (Criteria) this;
        }

        public Criteria andSingedInfoIdNotIn(List<String> values) {
            addCriterion("singed_info_id not in", values, "singedInfoId");
            return (Criteria) this;
        }

        public Criteria andSingedInfoIdBetween(String value1, String value2) {
            addCriterion("singed_info_id between", value1, value2, "singedInfoId");
            return (Criteria) this;
        }

        public Criteria andSingedInfoIdNotBetween(String value1, String value2) {
            addCriterion("singed_info_id not between", value1, value2, "singedInfoId");
            return (Criteria) this;
        }

        public Criteria andKeyInfoIdIsNull() {
            addCriterion("key_info_id is null");
            return (Criteria) this;
        }

        public Criteria andKeyInfoIdIsNotNull() {
            addCriterion("key_info_id is not null");
            return (Criteria) this;
        }

        public Criteria andKeyInfoIdEqualTo(String value) {
            addCriterion("key_info_id =", value, "keyInfoId");
            return (Criteria) this;
        }

        public Criteria andKeyInfoIdNotEqualTo(String value) {
            addCriterion("key_info_id <>", value, "keyInfoId");
            return (Criteria) this;
        }

        public Criteria andKeyInfoIdGreaterThan(String value) {
            addCriterion("key_info_id >", value, "keyInfoId");
            return (Criteria) this;
        }

        public Criteria andKeyInfoIdGreaterThanOrEqualTo(String value) {
            addCriterion("key_info_id >=", value, "keyInfoId");
            return (Criteria) this;
        }

        public Criteria andKeyInfoIdLessThan(String value) {
            addCriterion("key_info_id <", value, "keyInfoId");
            return (Criteria) this;
        }

        public Criteria andKeyInfoIdLessThanOrEqualTo(String value) {
            addCriterion("key_info_id <=", value, "keyInfoId");
            return (Criteria) this;
        }

        public Criteria andKeyInfoIdLike(String value) {
            addCriterion("key_info_id like", value, "keyInfoId");
            return (Criteria) this;
        }

        public Criteria andKeyInfoIdNotLike(String value) {
            addCriterion("key_info_id not like", value, "keyInfoId");
            return (Criteria) this;
        }

        public Criteria andKeyInfoIdIn(List<String> values) {
            addCriterion("key_info_id in", values, "keyInfoId");
            return (Criteria) this;
        }

        public Criteria andKeyInfoIdNotIn(List<String> values) {
            addCriterion("key_info_id not in", values, "keyInfoId");
            return (Criteria) this;
        }

        public Criteria andKeyInfoIdBetween(String value1, String value2) {
            addCriterion("key_info_id between", value1, value2, "keyInfoId");
            return (Criteria) this;
        }

        public Criteria andKeyInfoIdNotBetween(String value1, String value2) {
            addCriterion("key_info_id not between", value1, value2, "keyInfoId");
            return (Criteria) this;
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andValueTypeValueIsNull() {
            addCriterion("value_type_value is null");
            return (Criteria) this;
        }

        public Criteria andValueTypeValueIsNotNull() {
            addCriterion("value_type_value is not null");
            return (Criteria) this;
        }

        public Criteria andValueTypeValueEqualTo(String value) {
            addCriterion("value_type_value =", value, "valueTypeValue");
            return (Criteria) this;
        }

        public Criteria andValueTypeValueNotEqualTo(String value) {
            addCriterion("value_type_value <>", value, "valueTypeValue");
            return (Criteria) this;
        }

        public Criteria andValueTypeValueGreaterThan(String value) {
            addCriterion("value_type_value >", value, "valueTypeValue");
            return (Criteria) this;
        }

        public Criteria andValueTypeValueGreaterThanOrEqualTo(String value) {
            addCriterion("value_type_value >=", value, "valueTypeValue");
            return (Criteria) this;
        }

        public Criteria andValueTypeValueLessThan(String value) {
            addCriterion("value_type_value <", value, "valueTypeValue");
            return (Criteria) this;
        }

        public Criteria andValueTypeValueLessThanOrEqualTo(String value) {
            addCriterion("value_type_value <=", value, "valueTypeValue");
            return (Criteria) this;
        }

        public Criteria andValueTypeValueLike(String value) {
            addCriterion("value_type_value like", value, "valueTypeValue");
            return (Criteria) this;
        }

        public Criteria andValueTypeValueNotLike(String value) {
            addCriterion("value_type_value not like", value, "valueTypeValue");
            return (Criteria) this;
        }

        public Criteria andValueTypeValueIn(List<String> values) {
            addCriterion("value_type_value in", values, "valueTypeValue");
            return (Criteria) this;
        }

        public Criteria andValueTypeValueNotIn(List<String> values) {
            addCriterion("value_type_value not in", values, "valueTypeValue");
            return (Criteria) this;
        }

        public Criteria andValueTypeValueBetween(String value1, String value2) {
            addCriterion("value_type_value between", value1, value2, "valueTypeValue");
            return (Criteria) this;
        }

        public Criteria andValueTypeValueNotBetween(String value1, String value2) {
            addCriterion("value_type_value not between", value1, value2, "valueTypeValue");
            return (Criteria) this;
        }

        public Criteria andValueTypeIdIsNull() {
            addCriterion("value_type_id is null");
            return (Criteria) this;
        }

        public Criteria andValueTypeIdIsNotNull() {
            addCriterion("value_type_id is not null");
            return (Criteria) this;
        }

        public Criteria andValueTypeIdEqualTo(String value) {
            addCriterion("value_type_id =", value, "valueTypeId");
            return (Criteria) this;
        }

        public Criteria andValueTypeIdNotEqualTo(String value) {
            addCriterion("value_type_id <>", value, "valueTypeId");
            return (Criteria) this;
        }

        public Criteria andValueTypeIdGreaterThan(String value) {
            addCriterion("value_type_id >", value, "valueTypeId");
            return (Criteria) this;
        }

        public Criteria andValueTypeIdGreaterThanOrEqualTo(String value) {
            addCriterion("value_type_id >=", value, "valueTypeId");
            return (Criteria) this;
        }

        public Criteria andValueTypeIdLessThan(String value) {
            addCriterion("value_type_id <", value, "valueTypeId");
            return (Criteria) this;
        }

        public Criteria andValueTypeIdLessThanOrEqualTo(String value) {
            addCriterion("value_type_id <=", value, "valueTypeId");
            return (Criteria) this;
        }

        public Criteria andValueTypeIdLike(String value) {
            addCriterion("value_type_id like", value, "valueTypeId");
            return (Criteria) this;
        }

        public Criteria andValueTypeIdNotLike(String value) {
            addCriterion("value_type_id not like", value, "valueTypeId");
            return (Criteria) this;
        }

        public Criteria andValueTypeIdIn(List<String> values) {
            addCriterion("value_type_id in", values, "valueTypeId");
            return (Criteria) this;
        }

        public Criteria andValueTypeIdNotIn(List<String> values) {
            addCriterion("value_type_id not in", values, "valueTypeId");
            return (Criteria) this;
        }

        public Criteria andValueTypeIdBetween(String value1, String value2) {
            addCriterion("value_type_id between", value1, value2, "valueTypeId");
            return (Criteria) this;
        }

        public Criteria andValueTypeIdNotBetween(String value1, String value2) {
            addCriterion("value_type_id not between", value1, value2, "valueTypeId");
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