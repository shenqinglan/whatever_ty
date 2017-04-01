package com.whty.efs.data.pojo;

import java.util.ArrayList;
import java.util.List;

public class EuiccIsdrTarExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EuiccIsdrTarExample() {
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

        public Criteria andTarIdIsNull() {
            addCriterion("tar_id is null");
            return (Criteria) this;
        }

        public Criteria andTarIdIsNotNull() {
            addCriterion("tar_id is not null");
            return (Criteria) this;
        }

        public Criteria andTarIdEqualTo(String value) {
            addCriterion("tar_id =", value, "tarId");
            return (Criteria) this;
        }

        public Criteria andTarIdNotEqualTo(String value) {
            addCriterion("tar_id <>", value, "tarId");
            return (Criteria) this;
        }

        public Criteria andTarIdGreaterThan(String value) {
            addCriterion("tar_id >", value, "tarId");
            return (Criteria) this;
        }

        public Criteria andTarIdGreaterThanOrEqualTo(String value) {
            addCriterion("tar_id >=", value, "tarId");
            return (Criteria) this;
        }

        public Criteria andTarIdLessThan(String value) {
            addCriterion("tar_id <", value, "tarId");
            return (Criteria) this;
        }

        public Criteria andTarIdLessThanOrEqualTo(String value) {
            addCriterion("tar_id <=", value, "tarId");
            return (Criteria) this;
        }

        public Criteria andTarIdLike(String value) {
            addCriterion("tar_id like", value, "tarId");
            return (Criteria) this;
        }

        public Criteria andTarIdNotLike(String value) {
            addCriterion("tar_id not like", value, "tarId");
            return (Criteria) this;
        }

        public Criteria andTarIdIn(List<String> values) {
            addCriterion("tar_id in", values, "tarId");
            return (Criteria) this;
        }

        public Criteria andTarIdNotIn(List<String> values) {
            addCriterion("tar_id not in", values, "tarId");
            return (Criteria) this;
        }

        public Criteria andTarIdBetween(String value1, String value2) {
            addCriterion("tar_id between", value1, value2, "tarId");
            return (Criteria) this;
        }

        public Criteria andTarIdNotBetween(String value1, String value2) {
            addCriterion("tar_id not between", value1, value2, "tarId");
            return (Criteria) this;
        }

        public Criteria andRIdIsNull() {
            addCriterion("r_id is null");
            return (Criteria) this;
        }

        public Criteria andRIdIsNotNull() {
            addCriterion("r_id is not null");
            return (Criteria) this;
        }

        public Criteria andRIdEqualTo(String value) {
            addCriterion("r_id =", value, "rId");
            return (Criteria) this;
        }

        public Criteria andRIdNotEqualTo(String value) {
            addCriterion("r_id <>", value, "rId");
            return (Criteria) this;
        }

        public Criteria andRIdGreaterThan(String value) {
            addCriterion("r_id >", value, "rId");
            return (Criteria) this;
        }

        public Criteria andRIdGreaterThanOrEqualTo(String value) {
            addCriterion("r_id >=", value, "rId");
            return (Criteria) this;
        }

        public Criteria andRIdLessThan(String value) {
            addCriterion("r_id <", value, "rId");
            return (Criteria) this;
        }

        public Criteria andRIdLessThanOrEqualTo(String value) {
            addCriterion("r_id <=", value, "rId");
            return (Criteria) this;
        }

        public Criteria andRIdLike(String value) {
            addCriterion("r_id like", value, "rId");
            return (Criteria) this;
        }

        public Criteria andRIdNotLike(String value) {
            addCriterion("r_id not like", value, "rId");
            return (Criteria) this;
        }

        public Criteria andRIdIn(List<String> values) {
            addCriterion("r_id in", values, "rId");
            return (Criteria) this;
        }

        public Criteria andRIdNotIn(List<String> values) {
            addCriterion("r_id not in", values, "rId");
            return (Criteria) this;
        }

        public Criteria andRIdBetween(String value1, String value2) {
            addCriterion("r_id between", value1, value2, "rId");
            return (Criteria) this;
        }

        public Criteria andRIdNotBetween(String value1, String value2) {
            addCriterion("r_id not between", value1, value2, "rId");
            return (Criteria) this;
        }

        public Criteria andTarIsNull() {
            addCriterion("tar is null");
            return (Criteria) this;
        }

        public Criteria andTarIsNotNull() {
            addCriterion("tar is not null");
            return (Criteria) this;
        }

        public Criteria andTarEqualTo(String value) {
            addCriterion("tar =", value, "tar");
            return (Criteria) this;
        }

        public Criteria andTarNotEqualTo(String value) {
            addCriterion("tar <>", value, "tar");
            return (Criteria) this;
        }

        public Criteria andTarGreaterThan(String value) {
            addCriterion("tar >", value, "tar");
            return (Criteria) this;
        }

        public Criteria andTarGreaterThanOrEqualTo(String value) {
            addCriterion("tar >=", value, "tar");
            return (Criteria) this;
        }

        public Criteria andTarLessThan(String value) {
            addCriterion("tar <", value, "tar");
            return (Criteria) this;
        }

        public Criteria andTarLessThanOrEqualTo(String value) {
            addCriterion("tar <=", value, "tar");
            return (Criteria) this;
        }

        public Criteria andTarLike(String value) {
            addCriterion("tar like", value, "tar");
            return (Criteria) this;
        }

        public Criteria andTarNotLike(String value) {
            addCriterion("tar not like", value, "tar");
            return (Criteria) this;
        }

        public Criteria andTarIn(List<String> values) {
            addCriterion("tar in", values, "tar");
            return (Criteria) this;
        }

        public Criteria andTarNotIn(List<String> values) {
            addCriterion("tar not in", values, "tar");
            return (Criteria) this;
        }

        public Criteria andTarBetween(String value1, String value2) {
            addCriterion("tar between", value1, value2, "tar");
            return (Criteria) this;
        }

        public Criteria andTarNotBetween(String value1, String value2) {
            addCriterion("tar not between", value1, value2, "tar");
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