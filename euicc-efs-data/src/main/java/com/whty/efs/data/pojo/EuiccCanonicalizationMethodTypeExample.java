package com.whty.efs.data.pojo;

import java.util.ArrayList;
import java.util.List;

public class EuiccCanonicalizationMethodTypeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EuiccCanonicalizationMethodTypeExample() {
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

        public Criteria andAlgorithmIsNull() {
            addCriterion("algorithm is null");
            return (Criteria) this;
        }

        public Criteria andAlgorithmIsNotNull() {
            addCriterion("algorithm is not null");
            return (Criteria) this;
        }

        public Criteria andAlgorithmEqualTo(String value) {
            addCriterion("algorithm =", value, "algorithm");
            return (Criteria) this;
        }

        public Criteria andAlgorithmNotEqualTo(String value) {
            addCriterion("algorithm <>", value, "algorithm");
            return (Criteria) this;
        }

        public Criteria andAlgorithmGreaterThan(String value) {
            addCriterion("algorithm >", value, "algorithm");
            return (Criteria) this;
        }

        public Criteria andAlgorithmGreaterThanOrEqualTo(String value) {
            addCriterion("algorithm >=", value, "algorithm");
            return (Criteria) this;
        }

        public Criteria andAlgorithmLessThan(String value) {
            addCriterion("algorithm <", value, "algorithm");
            return (Criteria) this;
        }

        public Criteria andAlgorithmLessThanOrEqualTo(String value) {
            addCriterion("algorithm <=", value, "algorithm");
            return (Criteria) this;
        }

        public Criteria andAlgorithmLike(String value) {
            addCriterion("algorithm like", value, "algorithm");
            return (Criteria) this;
        }

        public Criteria andAlgorithmNotLike(String value) {
            addCriterion("algorithm not like", value, "algorithm");
            return (Criteria) this;
        }

        public Criteria andAlgorithmIn(List<String> values) {
            addCriterion("algorithm in", values, "algorithm");
            return (Criteria) this;
        }

        public Criteria andAlgorithmNotIn(List<String> values) {
            addCriterion("algorithm not in", values, "algorithm");
            return (Criteria) this;
        }

        public Criteria andAlgorithmBetween(String value1, String value2) {
            addCriterion("algorithm between", value1, value2, "algorithm");
            return (Criteria) this;
        }

        public Criteria andAlgorithmNotBetween(String value1, String value2) {
            addCriterion("algorithm not between", value1, value2, "algorithm");
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