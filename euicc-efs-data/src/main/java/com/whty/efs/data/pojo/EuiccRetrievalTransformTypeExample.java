package com.whty.efs.data.pojo;

import java.util.ArrayList;
import java.util.List;

public class EuiccRetrievalTransformTypeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EuiccRetrievalTransformTypeExample() {
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

        public Criteria andTransformIdIsNull() {
            addCriterion("transform_id is null");
            return (Criteria) this;
        }

        public Criteria andTransformIdIsNotNull() {
            addCriterion("transform_id is not null");
            return (Criteria) this;
        }

        public Criteria andTransformIdEqualTo(String value) {
            addCriterion("transform_id =", value, "transformId");
            return (Criteria) this;
        }

        public Criteria andTransformIdNotEqualTo(String value) {
            addCriterion("transform_id <>", value, "transformId");
            return (Criteria) this;
        }

        public Criteria andTransformIdGreaterThan(String value) {
            addCriterion("transform_id >", value, "transformId");
            return (Criteria) this;
        }

        public Criteria andTransformIdGreaterThanOrEqualTo(String value) {
            addCriterion("transform_id >=", value, "transformId");
            return (Criteria) this;
        }

        public Criteria andTransformIdLessThan(String value) {
            addCriterion("transform_id <", value, "transformId");
            return (Criteria) this;
        }

        public Criteria andTransformIdLessThanOrEqualTo(String value) {
            addCriterion("transform_id <=", value, "transformId");
            return (Criteria) this;
        }

        public Criteria andTransformIdLike(String value) {
            addCriterion("transform_id like", value, "transformId");
            return (Criteria) this;
        }

        public Criteria andTransformIdNotLike(String value) {
            addCriterion("transform_id not like", value, "transformId");
            return (Criteria) this;
        }

        public Criteria andTransformIdIn(List<String> values) {
            addCriterion("transform_id in", values, "transformId");
            return (Criteria) this;
        }

        public Criteria andTransformIdNotIn(List<String> values) {
            addCriterion("transform_id not in", values, "transformId");
            return (Criteria) this;
        }

        public Criteria andTransformIdBetween(String value1, String value2) {
            addCriterion("transform_id between", value1, value2, "transformId");
            return (Criteria) this;
        }

        public Criteria andTransformIdNotBetween(String value1, String value2) {
            addCriterion("transform_id not between", value1, value2, "transformId");
            return (Criteria) this;
        }

        public Criteria andRetrievalIdIsNull() {
            addCriterion("retrieval_id is null");
            return (Criteria) this;
        }

        public Criteria andRetrievalIdIsNotNull() {
            addCriterion("retrieval_id is not null");
            return (Criteria) this;
        }

        public Criteria andRetrievalIdEqualTo(String value) {
            addCriterion("retrieval_id =", value, "retrievalId");
            return (Criteria) this;
        }

        public Criteria andRetrievalIdNotEqualTo(String value) {
            addCriterion("retrieval_id <>", value, "retrievalId");
            return (Criteria) this;
        }

        public Criteria andRetrievalIdGreaterThan(String value) {
            addCriterion("retrieval_id >", value, "retrievalId");
            return (Criteria) this;
        }

        public Criteria andRetrievalIdGreaterThanOrEqualTo(String value) {
            addCriterion("retrieval_id >=", value, "retrievalId");
            return (Criteria) this;
        }

        public Criteria andRetrievalIdLessThan(String value) {
            addCriterion("retrieval_id <", value, "retrievalId");
            return (Criteria) this;
        }

        public Criteria andRetrievalIdLessThanOrEqualTo(String value) {
            addCriterion("retrieval_id <=", value, "retrievalId");
            return (Criteria) this;
        }

        public Criteria andRetrievalIdLike(String value) {
            addCriterion("retrieval_id like", value, "retrievalId");
            return (Criteria) this;
        }

        public Criteria andRetrievalIdNotLike(String value) {
            addCriterion("retrieval_id not like", value, "retrievalId");
            return (Criteria) this;
        }

        public Criteria andRetrievalIdIn(List<String> values) {
            addCriterion("retrieval_id in", values, "retrievalId");
            return (Criteria) this;
        }

        public Criteria andRetrievalIdNotIn(List<String> values) {
            addCriterion("retrieval_id not in", values, "retrievalId");
            return (Criteria) this;
        }

        public Criteria andRetrievalIdBetween(String value1, String value2) {
            addCriterion("retrieval_id between", value1, value2, "retrievalId");
            return (Criteria) this;
        }

        public Criteria andRetrievalIdNotBetween(String value1, String value2) {
            addCriterion("retrieval_id not between", value1, value2, "retrievalId");
            return (Criteria) this;
        }

        public Criteria andXpathIsNull() {
            addCriterion("xPath is null");
            return (Criteria) this;
        }

        public Criteria andXpathIsNotNull() {
            addCriterion("xPath is not null");
            return (Criteria) this;
        }

        public Criteria andXpathEqualTo(String value) {
            addCriterion("xPath =", value, "xpath");
            return (Criteria) this;
        }

        public Criteria andXpathNotEqualTo(String value) {
            addCriterion("xPath <>", value, "xpath");
            return (Criteria) this;
        }

        public Criteria andXpathGreaterThan(String value) {
            addCriterion("xPath >", value, "xpath");
            return (Criteria) this;
        }

        public Criteria andXpathGreaterThanOrEqualTo(String value) {
            addCriterion("xPath >=", value, "xpath");
            return (Criteria) this;
        }

        public Criteria andXpathLessThan(String value) {
            addCriterion("xPath <", value, "xpath");
            return (Criteria) this;
        }

        public Criteria andXpathLessThanOrEqualTo(String value) {
            addCriterion("xPath <=", value, "xpath");
            return (Criteria) this;
        }

        public Criteria andXpathLike(String value) {
            addCriterion("xPath like", value, "xpath");
            return (Criteria) this;
        }

        public Criteria andXpathNotLike(String value) {
            addCriterion("xPath not like", value, "xpath");
            return (Criteria) this;
        }

        public Criteria andXpathIn(List<String> values) {
            addCriterion("xPath in", values, "xpath");
            return (Criteria) this;
        }

        public Criteria andXpathNotIn(List<String> values) {
            addCriterion("xPath not in", values, "xpath");
            return (Criteria) this;
        }

        public Criteria andXpathBetween(String value1, String value2) {
            addCriterion("xPath between", value1, value2, "xpath");
            return (Criteria) this;
        }

        public Criteria andXpathNotBetween(String value1, String value2) {
            addCriterion("xPath not between", value1, value2, "xpath");
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