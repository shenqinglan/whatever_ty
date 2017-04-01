package com.whty.efs.data.pojo;

import java.util.ArrayList;
import java.util.List;

public class EuiccSignedInfoTypeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EuiccSignedInfoTypeExample() {
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

        public Criteria andSignatureHMacOutputLengthIsNull() {
            addCriterion("signature_h_mac_output_length is null");
            return (Criteria) this;
        }

        public Criteria andSignatureHMacOutputLengthIsNotNull() {
            addCriterion("signature_h_mac_output_length is not null");
            return (Criteria) this;
        }

        public Criteria andSignatureHMacOutputLengthEqualTo(String value) {
            addCriterion("signature_h_mac_output_length =", value, "signatureHMacOutputLength");
            return (Criteria) this;
        }

        public Criteria andSignatureHMacOutputLengthNotEqualTo(String value) {
            addCriterion("signature_h_mac_output_length <>", value, "signatureHMacOutputLength");
            return (Criteria) this;
        }

        public Criteria andSignatureHMacOutputLengthGreaterThan(String value) {
            addCriterion("signature_h_mac_output_length >", value, "signatureHMacOutputLength");
            return (Criteria) this;
        }

        public Criteria andSignatureHMacOutputLengthGreaterThanOrEqualTo(String value) {
            addCriterion("signature_h_mac_output_length >=", value, "signatureHMacOutputLength");
            return (Criteria) this;
        }

        public Criteria andSignatureHMacOutputLengthLessThan(String value) {
            addCriterion("signature_h_mac_output_length <", value, "signatureHMacOutputLength");
            return (Criteria) this;
        }

        public Criteria andSignatureHMacOutputLengthLessThanOrEqualTo(String value) {
            addCriterion("signature_h_mac_output_length <=", value, "signatureHMacOutputLength");
            return (Criteria) this;
        }

        public Criteria andSignatureHMacOutputLengthLike(String value) {
            addCriterion("signature_h_mac_output_length like", value, "signatureHMacOutputLength");
            return (Criteria) this;
        }

        public Criteria andSignatureHMacOutputLengthNotLike(String value) {
            addCriterion("signature_h_mac_output_length not like", value, "signatureHMacOutputLength");
            return (Criteria) this;
        }

        public Criteria andSignatureHMacOutputLengthIn(List<String> values) {
            addCriterion("signature_h_mac_output_length in", values, "signatureHMacOutputLength");
            return (Criteria) this;
        }

        public Criteria andSignatureHMacOutputLengthNotIn(List<String> values) {
            addCriterion("signature_h_mac_output_length not in", values, "signatureHMacOutputLength");
            return (Criteria) this;
        }

        public Criteria andSignatureHMacOutputLengthBetween(String value1, String value2) {
            addCriterion("signature_h_mac_output_length between", value1, value2, "signatureHMacOutputLength");
            return (Criteria) this;
        }

        public Criteria andSignatureHMacOutputLengthNotBetween(String value1, String value2) {
            addCriterion("signature_h_mac_output_length not between", value1, value2, "signatureHMacOutputLength");
            return (Criteria) this;
        }

        public Criteria andSignatureAlgorithmIsNull() {
            addCriterion("signature_algorithm is null");
            return (Criteria) this;
        }

        public Criteria andSignatureAlgorithmIsNotNull() {
            addCriterion("signature_algorithm is not null");
            return (Criteria) this;
        }

        public Criteria andSignatureAlgorithmEqualTo(String value) {
            addCriterion("signature_algorithm =", value, "signatureAlgorithm");
            return (Criteria) this;
        }

        public Criteria andSignatureAlgorithmNotEqualTo(String value) {
            addCriterion("signature_algorithm <>", value, "signatureAlgorithm");
            return (Criteria) this;
        }

        public Criteria andSignatureAlgorithmGreaterThan(String value) {
            addCriterion("signature_algorithm >", value, "signatureAlgorithm");
            return (Criteria) this;
        }

        public Criteria andSignatureAlgorithmGreaterThanOrEqualTo(String value) {
            addCriterion("signature_algorithm >=", value, "signatureAlgorithm");
            return (Criteria) this;
        }

        public Criteria andSignatureAlgorithmLessThan(String value) {
            addCriterion("signature_algorithm <", value, "signatureAlgorithm");
            return (Criteria) this;
        }

        public Criteria andSignatureAlgorithmLessThanOrEqualTo(String value) {
            addCriterion("signature_algorithm <=", value, "signatureAlgorithm");
            return (Criteria) this;
        }

        public Criteria andSignatureAlgorithmLike(String value) {
            addCriterion("signature_algorithm like", value, "signatureAlgorithm");
            return (Criteria) this;
        }

        public Criteria andSignatureAlgorithmNotLike(String value) {
            addCriterion("signature_algorithm not like", value, "signatureAlgorithm");
            return (Criteria) this;
        }

        public Criteria andSignatureAlgorithmIn(List<String> values) {
            addCriterion("signature_algorithm in", values, "signatureAlgorithm");
            return (Criteria) this;
        }

        public Criteria andSignatureAlgorithmNotIn(List<String> values) {
            addCriterion("signature_algorithm not in", values, "signatureAlgorithm");
            return (Criteria) this;
        }

        public Criteria andSignatureAlgorithmBetween(String value1, String value2) {
            addCriterion("signature_algorithm between", value1, value2, "signatureAlgorithm");
            return (Criteria) this;
        }

        public Criteria andSignatureAlgorithmNotBetween(String value1, String value2) {
            addCriterion("signature_algorithm not between", value1, value2, "signatureAlgorithm");
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