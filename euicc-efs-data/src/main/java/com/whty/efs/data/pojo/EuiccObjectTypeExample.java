package com.whty.efs.data.pojo;

import java.util.ArrayList;
import java.util.List;

public class EuiccObjectTypeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EuiccObjectTypeExample() {
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

        public Criteria andTypeIdIsNull() {
            addCriterion("type_id is null");
            return (Criteria) this;
        }

        public Criteria andTypeIdIsNotNull() {
            addCriterion("type_id is not null");
            return (Criteria) this;
        }

        public Criteria andTypeIdEqualTo(String value) {
            addCriterion("type_id =", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdNotEqualTo(String value) {
            addCriterion("type_id <>", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdGreaterThan(String value) {
            addCriterion("type_id >", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdGreaterThanOrEqualTo(String value) {
            addCriterion("type_id >=", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdLessThan(String value) {
            addCriterion("type_id <", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdLessThanOrEqualTo(String value) {
            addCriterion("type_id <=", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdLike(String value) {
            addCriterion("type_id like", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdNotLike(String value) {
            addCriterion("type_id not like", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdIn(List<String> values) {
            addCriterion("type_id in", values, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdNotIn(List<String> values) {
            addCriterion("type_id not in", values, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdBetween(String value1, String value2) {
            addCriterion("type_id between", value1, value2, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdNotBetween(String value1, String value2) {
            addCriterion("type_id not between", value1, value2, "typeId");
            return (Criteria) this;
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

        public Criteria andContentIsNull() {
            addCriterion("content is null");
            return (Criteria) this;
        }

        public Criteria andContentIsNotNull() {
            addCriterion("content is not null");
            return (Criteria) this;
        }

        public Criteria andContentEqualTo(String value) {
            addCriterion("content =", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotEqualTo(String value) {
            addCriterion("content <>", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThan(String value) {
            addCriterion("content >", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThanOrEqualTo(String value) {
            addCriterion("content >=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThan(String value) {
            addCriterion("content <", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThanOrEqualTo(String value) {
            addCriterion("content <=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLike(String value) {
            addCriterion("content like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotLike(String value) {
            addCriterion("content not like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentIn(List<String> values) {
            addCriterion("content in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotIn(List<String> values) {
            addCriterion("content not in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentBetween(String value1, String value2) {
            addCriterion("content between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotBetween(String value1, String value2) {
            addCriterion("content not between", value1, value2, "content");
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

        public Criteria andMimeTypeIsNull() {
            addCriterion("mime_type is null");
            return (Criteria) this;
        }

        public Criteria andMimeTypeIsNotNull() {
            addCriterion("mime_type is not null");
            return (Criteria) this;
        }

        public Criteria andMimeTypeEqualTo(String value) {
            addCriterion("mime_type =", value, "mimeType");
            return (Criteria) this;
        }

        public Criteria andMimeTypeNotEqualTo(String value) {
            addCriterion("mime_type <>", value, "mimeType");
            return (Criteria) this;
        }

        public Criteria andMimeTypeGreaterThan(String value) {
            addCriterion("mime_type >", value, "mimeType");
            return (Criteria) this;
        }

        public Criteria andMimeTypeGreaterThanOrEqualTo(String value) {
            addCriterion("mime_type >=", value, "mimeType");
            return (Criteria) this;
        }

        public Criteria andMimeTypeLessThan(String value) {
            addCriterion("mime_type <", value, "mimeType");
            return (Criteria) this;
        }

        public Criteria andMimeTypeLessThanOrEqualTo(String value) {
            addCriterion("mime_type <=", value, "mimeType");
            return (Criteria) this;
        }

        public Criteria andMimeTypeLike(String value) {
            addCriterion("mime_type like", value, "mimeType");
            return (Criteria) this;
        }

        public Criteria andMimeTypeNotLike(String value) {
            addCriterion("mime_type not like", value, "mimeType");
            return (Criteria) this;
        }

        public Criteria andMimeTypeIn(List<String> values) {
            addCriterion("mime_type in", values, "mimeType");
            return (Criteria) this;
        }

        public Criteria andMimeTypeNotIn(List<String> values) {
            addCriterion("mime_type not in", values, "mimeType");
            return (Criteria) this;
        }

        public Criteria andMimeTypeBetween(String value1, String value2) {
            addCriterion("mime_type between", value1, value2, "mimeType");
            return (Criteria) this;
        }

        public Criteria andMimeTypeNotBetween(String value1, String value2) {
            addCriterion("mime_type not between", value1, value2, "mimeType");
            return (Criteria) this;
        }

        public Criteria andEncodingIsNull() {
            addCriterion("encoding is null");
            return (Criteria) this;
        }

        public Criteria andEncodingIsNotNull() {
            addCriterion("encoding is not null");
            return (Criteria) this;
        }

        public Criteria andEncodingEqualTo(String value) {
            addCriterion("encoding =", value, "encoding");
            return (Criteria) this;
        }

        public Criteria andEncodingNotEqualTo(String value) {
            addCriterion("encoding <>", value, "encoding");
            return (Criteria) this;
        }

        public Criteria andEncodingGreaterThan(String value) {
            addCriterion("encoding >", value, "encoding");
            return (Criteria) this;
        }

        public Criteria andEncodingGreaterThanOrEqualTo(String value) {
            addCriterion("encoding >=", value, "encoding");
            return (Criteria) this;
        }

        public Criteria andEncodingLessThan(String value) {
            addCriterion("encoding <", value, "encoding");
            return (Criteria) this;
        }

        public Criteria andEncodingLessThanOrEqualTo(String value) {
            addCriterion("encoding <=", value, "encoding");
            return (Criteria) this;
        }

        public Criteria andEncodingLike(String value) {
            addCriterion("encoding like", value, "encoding");
            return (Criteria) this;
        }

        public Criteria andEncodingNotLike(String value) {
            addCriterion("encoding not like", value, "encoding");
            return (Criteria) this;
        }

        public Criteria andEncodingIn(List<String> values) {
            addCriterion("encoding in", values, "encoding");
            return (Criteria) this;
        }

        public Criteria andEncodingNotIn(List<String> values) {
            addCriterion("encoding not in", values, "encoding");
            return (Criteria) this;
        }

        public Criteria andEncodingBetween(String value1, String value2) {
            addCriterion("encoding between", value1, value2, "encoding");
            return (Criteria) this;
        }

        public Criteria andEncodingNotBetween(String value1, String value2) {
            addCriterion("encoding not between", value1, value2, "encoding");
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