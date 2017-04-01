package com.whty.euicc.data.pojo;

import java.util.ArrayList;
import java.util.List;

public class AcInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AcInfoExample() {
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

        public Criteria andIdIsNull() {
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andHashIsNull() {
            addCriterion("HASH is null");
            return (Criteria) this;
        }

        public Criteria andHashIsNotNull() {
            addCriterion("HASH is not null");
            return (Criteria) this;
        }

        public Criteria andHashEqualTo(String value) {
            addCriterion("HASH =", value, "hash");
            return (Criteria) this;
        }

        public Criteria andHashNotEqualTo(String value) {
            addCriterion("HASH <>", value, "hash");
            return (Criteria) this;
        }

        public Criteria andHashGreaterThan(String value) {
            addCriterion("HASH >", value, "hash");
            return (Criteria) this;
        }

        public Criteria andHashGreaterThanOrEqualTo(String value) {
            addCriterion("HASH >=", value, "hash");
            return (Criteria) this;
        }

        public Criteria andHashLessThan(String value) {
            addCriterion("HASH <", value, "hash");
            return (Criteria) this;
        }

        public Criteria andHashLessThanOrEqualTo(String value) {
            addCriterion("HASH <=", value, "hash");
            return (Criteria) this;
        }

        public Criteria andHashLike(String value) {
            addCriterion("HASH like", value, "hash");
            return (Criteria) this;
        }

        public Criteria andHashNotLike(String value) {
            addCriterion("HASH not like", value, "hash");
            return (Criteria) this;
        }

        public Criteria andHashIn(List<String> values) {
            addCriterion("HASH in", values, "hash");
            return (Criteria) this;
        }

        public Criteria andHashNotIn(List<String> values) {
            addCriterion("HASH not in", values, "hash");
            return (Criteria) this;
        }

        public Criteria andHashBetween(String value1, String value2) {
            addCriterion("HASH between", value1, value2, "hash");
            return (Criteria) this;
        }

        public Criteria andHashNotBetween(String value1, String value2) {
            addCriterion("HASH not between", value1, value2, "hash");
            return (Criteria) this;
        }

        public Criteria andApduIsNull() {
            addCriterion("APDU is null");
            return (Criteria) this;
        }

        public Criteria andApduIsNotNull() {
            addCriterion("APDU is not null");
            return (Criteria) this;
        }

        public Criteria andApduEqualTo(String value) {
            addCriterion("APDU =", value, "apdu");
            return (Criteria) this;
        }

        public Criteria andApduNotEqualTo(String value) {
            addCriterion("APDU <>", value, "apdu");
            return (Criteria) this;
        }

        public Criteria andApduGreaterThan(String value) {
            addCriterion("APDU >", value, "apdu");
            return (Criteria) this;
        }

        public Criteria andApduGreaterThanOrEqualTo(String value) {
            addCriterion("APDU >=", value, "apdu");
            return (Criteria) this;
        }

        public Criteria andApduLessThan(String value) {
            addCriterion("APDU <", value, "apdu");
            return (Criteria) this;
        }

        public Criteria andApduLessThanOrEqualTo(String value) {
            addCriterion("APDU <=", value, "apdu");
            return (Criteria) this;
        }

        public Criteria andApduLike(String value) {
            addCriterion("APDU like", value, "apdu");
            return (Criteria) this;
        }

        public Criteria andApduNotLike(String value) {
            addCriterion("APDU not like", value, "apdu");
            return (Criteria) this;
        }

        public Criteria andApduIn(List<String> values) {
            addCriterion("APDU in", values, "apdu");
            return (Criteria) this;
        }

        public Criteria andApduNotIn(List<String> values) {
            addCriterion("APDU not in", values, "apdu");
            return (Criteria) this;
        }

        public Criteria andApduBetween(String value1, String value2) {
            addCriterion("APDU between", value1, value2, "apdu");
            return (Criteria) this;
        }

        public Criteria andApduNotBetween(String value1, String value2) {
            addCriterion("APDU not between", value1, value2, "apdu");
            return (Criteria) this;
        }

        public Criteria andNfcIsNull() {
            addCriterion("NFC is null");
            return (Criteria) this;
        }

        public Criteria andNfcIsNotNull() {
            addCriterion("NFC is not null");
            return (Criteria) this;
        }

        public Criteria andNfcEqualTo(String value) {
            addCriterion("NFC =", value, "nfc");
            return (Criteria) this;
        }

        public Criteria andNfcNotEqualTo(String value) {
            addCriterion("NFC <>", value, "nfc");
            return (Criteria) this;
        }

        public Criteria andNfcGreaterThan(String value) {
            addCriterion("NFC >", value, "nfc");
            return (Criteria) this;
        }

        public Criteria andNfcGreaterThanOrEqualTo(String value) {
            addCriterion("NFC >=", value, "nfc");
            return (Criteria) this;
        }

        public Criteria andNfcLessThan(String value) {
            addCriterion("NFC <", value, "nfc");
            return (Criteria) this;
        }

        public Criteria andNfcLessThanOrEqualTo(String value) {
            addCriterion("NFC <=", value, "nfc");
            return (Criteria) this;
        }

        public Criteria andNfcLike(String value) {
            addCriterion("NFC like", value, "nfc");
            return (Criteria) this;
        }

        public Criteria andNfcNotLike(String value) {
            addCriterion("NFC not like", value, "nfc");
            return (Criteria) this;
        }

        public Criteria andNfcIn(List<String> values) {
            addCriterion("NFC in", values, "nfc");
            return (Criteria) this;
        }

        public Criteria andNfcNotIn(List<String> values) {
            addCriterion("NFC not in", values, "nfc");
            return (Criteria) this;
        }

        public Criteria andNfcBetween(String value1, String value2) {
            addCriterion("NFC between", value1, value2, "nfc");
            return (Criteria) this;
        }

        public Criteria andNfcNotBetween(String value1, String value2) {
            addCriterion("NFC not between", value1, value2, "nfc");
            return (Criteria) this;
        }

        public Criteria andAcAidIsNull() {
            addCriterion("AC_AID is null");
            return (Criteria) this;
        }

        public Criteria andAcAidIsNotNull() {
            addCriterion("AC_AID is not null");
            return (Criteria) this;
        }

        public Criteria andAcAidEqualTo(String value) {
            addCriterion("AC_AID =", value, "acAid");
            return (Criteria) this;
        }

        public Criteria andAcAidNotEqualTo(String value) {
            addCriterion("AC_AID <>", value, "acAid");
            return (Criteria) this;
        }

        public Criteria andAcAidGreaterThan(String value) {
            addCriterion("AC_AID >", value, "acAid");
            return (Criteria) this;
        }

        public Criteria andAcAidGreaterThanOrEqualTo(String value) {
            addCriterion("AC_AID >=", value, "acAid");
            return (Criteria) this;
        }

        public Criteria andAcAidLessThan(String value) {
            addCriterion("AC_AID <", value, "acAid");
            return (Criteria) this;
        }

        public Criteria andAcAidLessThanOrEqualTo(String value) {
            addCriterion("AC_AID <=", value, "acAid");
            return (Criteria) this;
        }

        public Criteria andAcAidLike(String value) {
            addCriterion("AC_AID like", value, "acAid");
            return (Criteria) this;
        }

        public Criteria andAcAidNotLike(String value) {
            addCriterion("AC_AID not like", value, "acAid");
            return (Criteria) this;
        }

        public Criteria andAcAidIn(List<String> values) {
            addCriterion("AC_AID in", values, "acAid");
            return (Criteria) this;
        }

        public Criteria andAcAidNotIn(List<String> values) {
            addCriterion("AC_AID not in", values, "acAid");
            return (Criteria) this;
        }

        public Criteria andAcAidBetween(String value1, String value2) {
            addCriterion("AC_AID between", value1, value2, "acAid");
            return (Criteria) this;
        }

        public Criteria andAcAidNotBetween(String value1, String value2) {
            addCriterion("AC_AID not between", value1, value2, "acAid");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("STATUS is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("STATUS =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("STATUS <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("STATUS >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("STATUS >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("STATUS <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("STATUS <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("STATUS like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("STATUS not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("STATUS in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("STATUS not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("STATUS between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("STATUS not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andAcIndexIsNull() {
            addCriterion("AC_INDEX is null");
            return (Criteria) this;
        }

        public Criteria andAcIndexIsNotNull() {
            addCriterion("AC_INDEX is not null");
            return (Criteria) this;
        }

        public Criteria andAcIndexEqualTo(String value) {
            addCriterion("AC_INDEX =", value, "acIndex");
            return (Criteria) this;
        }

        public Criteria andAcIndexNotEqualTo(String value) {
            addCriterion("AC_INDEX <>", value, "acIndex");
            return (Criteria) this;
        }

        public Criteria andAcIndexGreaterThan(String value) {
            addCriterion("AC_INDEX >", value, "acIndex");
            return (Criteria) this;
        }

        public Criteria andAcIndexGreaterThanOrEqualTo(String value) {
            addCriterion("AC_INDEX >=", value, "acIndex");
            return (Criteria) this;
        }

        public Criteria andAcIndexLessThan(String value) {
            addCriterion("AC_INDEX <", value, "acIndex");
            return (Criteria) this;
        }

        public Criteria andAcIndexLessThanOrEqualTo(String value) {
            addCriterion("AC_INDEX <=", value, "acIndex");
            return (Criteria) this;
        }

        public Criteria andAcIndexLike(String value) {
            addCriterion("AC_INDEX like", value, "acIndex");
            return (Criteria) this;
        }

        public Criteria andAcIndexNotLike(String value) {
            addCriterion("AC_INDEX not like", value, "acIndex");
            return (Criteria) this;
        }

        public Criteria andAcIndexIn(List<String> values) {
            addCriterion("AC_INDEX in", values, "acIndex");
            return (Criteria) this;
        }

        public Criteria andAcIndexNotIn(List<String> values) {
            addCriterion("AC_INDEX not in", values, "acIndex");
            return (Criteria) this;
        }

        public Criteria andAcIndexBetween(String value1, String value2) {
            addCriterion("AC_INDEX between", value1, value2, "acIndex");
            return (Criteria) this;
        }

        public Criteria andAcIndexNotBetween(String value1, String value2) {
            addCriterion("AC_INDEX not between", value1, value2, "acIndex");
            return (Criteria) this;
        }

        public Criteria andAppAidIsNull() {
            addCriterion("APP_AID is null");
            return (Criteria) this;
        }

        public Criteria andAppAidIsNotNull() {
            addCriterion("APP_AID is not null");
            return (Criteria) this;
        }

        public Criteria andAppAidEqualTo(String value) {
            addCriterion("APP_AID =", value, "appAid");
            return (Criteria) this;
        }

        public Criteria andAppAidNotEqualTo(String value) {
            addCriterion("APP_AID <>", value, "appAid");
            return (Criteria) this;
        }

        public Criteria andAppAidGreaterThan(String value) {
            addCriterion("APP_AID >", value, "appAid");
            return (Criteria) this;
        }

        public Criteria andAppAidGreaterThanOrEqualTo(String value) {
            addCriterion("APP_AID >=", value, "appAid");
            return (Criteria) this;
        }

        public Criteria andAppAidLessThan(String value) {
            addCriterion("APP_AID <", value, "appAid");
            return (Criteria) this;
        }

        public Criteria andAppAidLessThanOrEqualTo(String value) {
            addCriterion("APP_AID <=", value, "appAid");
            return (Criteria) this;
        }

        public Criteria andAppAidLike(String value) {
            addCriterion("APP_AID like", value, "appAid");
            return (Criteria) this;
        }

        public Criteria andAppAidNotLike(String value) {
            addCriterion("APP_AID not like", value, "appAid");
            return (Criteria) this;
        }

        public Criteria andAppAidIn(List<String> values) {
            addCriterion("APP_AID in", values, "appAid");
            return (Criteria) this;
        }

        public Criteria andAppAidNotIn(List<String> values) {
            addCriterion("APP_AID not in", values, "appAid");
            return (Criteria) this;
        }

        public Criteria andAppAidBetween(String value1, String value2) {
            addCriterion("APP_AID between", value1, value2, "appAid");
            return (Criteria) this;
        }

        public Criteria andAppAidNotBetween(String value1, String value2) {
            addCriterion("APP_AID not between", value1, value2, "appAid");
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