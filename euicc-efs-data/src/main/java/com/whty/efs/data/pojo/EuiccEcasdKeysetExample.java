package com.whty.efs.data.pojo;

import java.util.ArrayList;
import java.util.List;

public class EuiccEcasdKeysetExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EuiccEcasdKeysetExample() {
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

        public Criteria andKeysetIdIsNull() {
            addCriterion("keyset_id is null");
            return (Criteria) this;
        }

        public Criteria andKeysetIdIsNotNull() {
            addCriterion("keyset_id is not null");
            return (Criteria) this;
        }

        public Criteria andKeysetIdEqualTo(String value) {
            addCriterion("keyset_id =", value, "keysetId");
            return (Criteria) this;
        }

        public Criteria andKeysetIdNotEqualTo(String value) {
            addCriterion("keyset_id <>", value, "keysetId");
            return (Criteria) this;
        }

        public Criteria andKeysetIdGreaterThan(String value) {
            addCriterion("keyset_id >", value, "keysetId");
            return (Criteria) this;
        }

        public Criteria andKeysetIdGreaterThanOrEqualTo(String value) {
            addCriterion("keyset_id >=", value, "keysetId");
            return (Criteria) this;
        }

        public Criteria andKeysetIdLessThan(String value) {
            addCriterion("keyset_id <", value, "keysetId");
            return (Criteria) this;
        }

        public Criteria andKeysetIdLessThanOrEqualTo(String value) {
            addCriterion("keyset_id <=", value, "keysetId");
            return (Criteria) this;
        }

        public Criteria andKeysetIdLike(String value) {
            addCriterion("keyset_id like", value, "keysetId");
            return (Criteria) this;
        }

        public Criteria andKeysetIdNotLike(String value) {
            addCriterion("keyset_id not like", value, "keysetId");
            return (Criteria) this;
        }

        public Criteria andKeysetIdIn(List<String> values) {
            addCriterion("keyset_id in", values, "keysetId");
            return (Criteria) this;
        }

        public Criteria andKeysetIdNotIn(List<String> values) {
            addCriterion("keyset_id not in", values, "keysetId");
            return (Criteria) this;
        }

        public Criteria andKeysetIdBetween(String value1, String value2) {
            addCriterion("keyset_id between", value1, value2, "keysetId");
            return (Criteria) this;
        }

        public Criteria andKeysetIdNotBetween(String value1, String value2) {
            addCriterion("keyset_id not between", value1, value2, "keysetId");
            return (Criteria) this;
        }

        public Criteria andEcasdIdIsNull() {
            addCriterion("ecasd_id is null");
            return (Criteria) this;
        }

        public Criteria andEcasdIdIsNotNull() {
            addCriterion("ecasd_id is not null");
            return (Criteria) this;
        }

        public Criteria andEcasdIdEqualTo(String value) {
            addCriterion("ecasd_id =", value, "ecasdId");
            return (Criteria) this;
        }

        public Criteria andEcasdIdNotEqualTo(String value) {
            addCriterion("ecasd_id <>", value, "ecasdId");
            return (Criteria) this;
        }

        public Criteria andEcasdIdGreaterThan(String value) {
            addCriterion("ecasd_id >", value, "ecasdId");
            return (Criteria) this;
        }

        public Criteria andEcasdIdGreaterThanOrEqualTo(String value) {
            addCriterion("ecasd_id >=", value, "ecasdId");
            return (Criteria) this;
        }

        public Criteria andEcasdIdLessThan(String value) {
            addCriterion("ecasd_id <", value, "ecasdId");
            return (Criteria) this;
        }

        public Criteria andEcasdIdLessThanOrEqualTo(String value) {
            addCriterion("ecasd_id <=", value, "ecasdId");
            return (Criteria) this;
        }

        public Criteria andEcasdIdLike(String value) {
            addCriterion("ecasd_id like", value, "ecasdId");
            return (Criteria) this;
        }

        public Criteria andEcasdIdNotLike(String value) {
            addCriterion("ecasd_id not like", value, "ecasdId");
            return (Criteria) this;
        }

        public Criteria andEcasdIdIn(List<String> values) {
            addCriterion("ecasd_id in", values, "ecasdId");
            return (Criteria) this;
        }

        public Criteria andEcasdIdNotIn(List<String> values) {
            addCriterion("ecasd_id not in", values, "ecasdId");
            return (Criteria) this;
        }

        public Criteria andEcasdIdBetween(String value1, String value2) {
            addCriterion("ecasd_id between", value1, value2, "ecasdId");
            return (Criteria) this;
        }

        public Criteria andEcasdIdNotBetween(String value1, String value2) {
            addCriterion("ecasd_id not between", value1, value2, "ecasdId");
            return (Criteria) this;
        }

        public Criteria andVersionIsNull() {
            addCriterion("version is null");
            return (Criteria) this;
        }

        public Criteria andVersionIsNotNull() {
            addCriterion("version is not null");
            return (Criteria) this;
        }

        public Criteria andVersionEqualTo(String value) {
            addCriterion("version =", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotEqualTo(String value) {
            addCriterion("version <>", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThan(String value) {
            addCriterion("version >", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThanOrEqualTo(String value) {
            addCriterion("version >=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThan(String value) {
            addCriterion("version <", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThanOrEqualTo(String value) {
            addCriterion("version <=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLike(String value) {
            addCriterion("version like", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotLike(String value) {
            addCriterion("version not like", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionIn(List<String> values) {
            addCriterion("version in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotIn(List<String> values) {
            addCriterion("version not in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionBetween(String value1, String value2) {
            addCriterion("version between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotBetween(String value1, String value2) {
            addCriterion("version not between", value1, value2, "version");
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

        public Criteria andCntrIsNull() {
            addCriterion("cntr is null");
            return (Criteria) this;
        }

        public Criteria andCntrIsNotNull() {
            addCriterion("cntr is not null");
            return (Criteria) this;
        }

        public Criteria andCntrEqualTo(String value) {
            addCriterion("cntr =", value, "cntr");
            return (Criteria) this;
        }

        public Criteria andCntrNotEqualTo(String value) {
            addCriterion("cntr <>", value, "cntr");
            return (Criteria) this;
        }

        public Criteria andCntrGreaterThan(String value) {
            addCriterion("cntr >", value, "cntr");
            return (Criteria) this;
        }

        public Criteria andCntrGreaterThanOrEqualTo(String value) {
            addCriterion("cntr >=", value, "cntr");
            return (Criteria) this;
        }

        public Criteria andCntrLessThan(String value) {
            addCriterion("cntr <", value, "cntr");
            return (Criteria) this;
        }

        public Criteria andCntrLessThanOrEqualTo(String value) {
            addCriterion("cntr <=", value, "cntr");
            return (Criteria) this;
        }

        public Criteria andCntrLike(String value) {
            addCriterion("cntr like", value, "cntr");
            return (Criteria) this;
        }

        public Criteria andCntrNotLike(String value) {
            addCriterion("cntr not like", value, "cntr");
            return (Criteria) this;
        }

        public Criteria andCntrIn(List<String> values) {
            addCriterion("cntr in", values, "cntr");
            return (Criteria) this;
        }

        public Criteria andCntrNotIn(List<String> values) {
            addCriterion("cntr not in", values, "cntr");
            return (Criteria) this;
        }

        public Criteria andCntrBetween(String value1, String value2) {
            addCriterion("cntr between", value1, value2, "cntr");
            return (Criteria) this;
        }

        public Criteria andCntrNotBetween(String value1, String value2) {
            addCriterion("cntr not between", value1, value2, "cntr");
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