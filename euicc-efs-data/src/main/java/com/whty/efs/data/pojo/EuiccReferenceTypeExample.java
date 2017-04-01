package com.whty.efs.data.pojo;

import java.util.ArrayList;
import java.util.List;

public class EuiccReferenceTypeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EuiccReferenceTypeExample() {
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

        public Criteria andReferenceIdIsNull() {
            addCriterion("reference_id is null");
            return (Criteria) this;
        }

        public Criteria andReferenceIdIsNotNull() {
            addCriterion("reference_id is not null");
            return (Criteria) this;
        }

        public Criteria andReferenceIdEqualTo(String value) {
            addCriterion("reference_id =", value, "referenceId");
            return (Criteria) this;
        }

        public Criteria andReferenceIdNotEqualTo(String value) {
            addCriterion("reference_id <>", value, "referenceId");
            return (Criteria) this;
        }

        public Criteria andReferenceIdGreaterThan(String value) {
            addCriterion("reference_id >", value, "referenceId");
            return (Criteria) this;
        }

        public Criteria andReferenceIdGreaterThanOrEqualTo(String value) {
            addCriterion("reference_id >=", value, "referenceId");
            return (Criteria) this;
        }

        public Criteria andReferenceIdLessThan(String value) {
            addCriterion("reference_id <", value, "referenceId");
            return (Criteria) this;
        }

        public Criteria andReferenceIdLessThanOrEqualTo(String value) {
            addCriterion("reference_id <=", value, "referenceId");
            return (Criteria) this;
        }

        public Criteria andReferenceIdLike(String value) {
            addCriterion("reference_id like", value, "referenceId");
            return (Criteria) this;
        }

        public Criteria andReferenceIdNotLike(String value) {
            addCriterion("reference_id not like", value, "referenceId");
            return (Criteria) this;
        }

        public Criteria andReferenceIdIn(List<String> values) {
            addCriterion("reference_id in", values, "referenceId");
            return (Criteria) this;
        }

        public Criteria andReferenceIdNotIn(List<String> values) {
            addCriterion("reference_id not in", values, "referenceId");
            return (Criteria) this;
        }

        public Criteria andReferenceIdBetween(String value1, String value2) {
            addCriterion("reference_id between", value1, value2, "referenceId");
            return (Criteria) this;
        }

        public Criteria andReferenceIdNotBetween(String value1, String value2) {
            addCriterion("reference_id not between", value1, value2, "referenceId");
            return (Criteria) this;
        }

        public Criteria andDigestValueIsNull() {
            addCriterion("digest_value is null");
            return (Criteria) this;
        }

        public Criteria andDigestValueIsNotNull() {
            addCriterion("digest_value is not null");
            return (Criteria) this;
        }

        public Criteria andDigestValueEqualTo(String value) {
            addCriterion("digest_value =", value, "digestValue");
            return (Criteria) this;
        }

        public Criteria andDigestValueNotEqualTo(String value) {
            addCriterion("digest_value <>", value, "digestValue");
            return (Criteria) this;
        }

        public Criteria andDigestValueGreaterThan(String value) {
            addCriterion("digest_value >", value, "digestValue");
            return (Criteria) this;
        }

        public Criteria andDigestValueGreaterThanOrEqualTo(String value) {
            addCriterion("digest_value >=", value, "digestValue");
            return (Criteria) this;
        }

        public Criteria andDigestValueLessThan(String value) {
            addCriterion("digest_value <", value, "digestValue");
            return (Criteria) this;
        }

        public Criteria andDigestValueLessThanOrEqualTo(String value) {
            addCriterion("digest_value <=", value, "digestValue");
            return (Criteria) this;
        }

        public Criteria andDigestValueLike(String value) {
            addCriterion("digest_value like", value, "digestValue");
            return (Criteria) this;
        }

        public Criteria andDigestValueNotLike(String value) {
            addCriterion("digest_value not like", value, "digestValue");
            return (Criteria) this;
        }

        public Criteria andDigestValueIn(List<String> values) {
            addCriterion("digest_value in", values, "digestValue");
            return (Criteria) this;
        }

        public Criteria andDigestValueNotIn(List<String> values) {
            addCriterion("digest_value not in", values, "digestValue");
            return (Criteria) this;
        }

        public Criteria andDigestValueBetween(String value1, String value2) {
            addCriterion("digest_value between", value1, value2, "digestValue");
            return (Criteria) this;
        }

        public Criteria andDigestValueNotBetween(String value1, String value2) {
            addCriterion("digest_value not between", value1, value2, "digestValue");
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

        public Criteria andUriIsNull() {
            addCriterion("uri is null");
            return (Criteria) this;
        }

        public Criteria andUriIsNotNull() {
            addCriterion("uri is not null");
            return (Criteria) this;
        }

        public Criteria andUriEqualTo(String value) {
            addCriterion("uri =", value, "uri");
            return (Criteria) this;
        }

        public Criteria andUriNotEqualTo(String value) {
            addCriterion("uri <>", value, "uri");
            return (Criteria) this;
        }

        public Criteria andUriGreaterThan(String value) {
            addCriterion("uri >", value, "uri");
            return (Criteria) this;
        }

        public Criteria andUriGreaterThanOrEqualTo(String value) {
            addCriterion("uri >=", value, "uri");
            return (Criteria) this;
        }

        public Criteria andUriLessThan(String value) {
            addCriterion("uri <", value, "uri");
            return (Criteria) this;
        }

        public Criteria andUriLessThanOrEqualTo(String value) {
            addCriterion("uri <=", value, "uri");
            return (Criteria) this;
        }

        public Criteria andUriLike(String value) {
            addCriterion("uri like", value, "uri");
            return (Criteria) this;
        }

        public Criteria andUriNotLike(String value) {
            addCriterion("uri not like", value, "uri");
            return (Criteria) this;
        }

        public Criteria andUriIn(List<String> values) {
            addCriterion("uri in", values, "uri");
            return (Criteria) this;
        }

        public Criteria andUriNotIn(List<String> values) {
            addCriterion("uri not in", values, "uri");
            return (Criteria) this;
        }

        public Criteria andUriBetween(String value1, String value2) {
            addCriterion("uri between", value1, value2, "uri");
            return (Criteria) this;
        }

        public Criteria andUriNotBetween(String value1, String value2) {
            addCriterion("uri not between", value1, value2, "uri");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("type like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("type not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andSignedInfoIdIsNull() {
            addCriterion("signed_info_id is null");
            return (Criteria) this;
        }

        public Criteria andSignedInfoIdIsNotNull() {
            addCriterion("signed_info_id is not null");
            return (Criteria) this;
        }

        public Criteria andSignedInfoIdEqualTo(String value) {
            addCriterion("signed_info_id =", value, "signedInfoId");
            return (Criteria) this;
        }

        public Criteria andSignedInfoIdNotEqualTo(String value) {
            addCriterion("signed_info_id <>", value, "signedInfoId");
            return (Criteria) this;
        }

        public Criteria andSignedInfoIdGreaterThan(String value) {
            addCriterion("signed_info_id >", value, "signedInfoId");
            return (Criteria) this;
        }

        public Criteria andSignedInfoIdGreaterThanOrEqualTo(String value) {
            addCriterion("signed_info_id >=", value, "signedInfoId");
            return (Criteria) this;
        }

        public Criteria andSignedInfoIdLessThan(String value) {
            addCriterion("signed_info_id <", value, "signedInfoId");
            return (Criteria) this;
        }

        public Criteria andSignedInfoIdLessThanOrEqualTo(String value) {
            addCriterion("signed_info_id <=", value, "signedInfoId");
            return (Criteria) this;
        }

        public Criteria andSignedInfoIdLike(String value) {
            addCriterion("signed_info_id like", value, "signedInfoId");
            return (Criteria) this;
        }

        public Criteria andSignedInfoIdNotLike(String value) {
            addCriterion("signed_info_id not like", value, "signedInfoId");
            return (Criteria) this;
        }

        public Criteria andSignedInfoIdIn(List<String> values) {
            addCriterion("signed_info_id in", values, "signedInfoId");
            return (Criteria) this;
        }

        public Criteria andSignedInfoIdNotIn(List<String> values) {
            addCriterion("signed_info_id not in", values, "signedInfoId");
            return (Criteria) this;
        }

        public Criteria andSignedInfoIdBetween(String value1, String value2) {
            addCriterion("signed_info_id between", value1, value2, "signedInfoId");
            return (Criteria) this;
        }

        public Criteria andSignedInfoIdNotBetween(String value1, String value2) {
            addCriterion("signed_info_id not between", value1, value2, "signedInfoId");
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