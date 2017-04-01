package com.whty.efs.data.pojo;

import java.util.ArrayList;
import java.util.List;

public class EuiccEcasdExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EuiccEcasdExample() {
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

        public Criteria andAidIsNull() {
            addCriterion("aid is null");
            return (Criteria) this;
        }

        public Criteria andAidIsNotNull() {
            addCriterion("aid is not null");
            return (Criteria) this;
        }

        public Criteria andAidEqualTo(String value) {
            addCriterion("aid =", value, "aid");
            return (Criteria) this;
        }

        public Criteria andAidNotEqualTo(String value) {
            addCriterion("aid <>", value, "aid");
            return (Criteria) this;
        }

        public Criteria andAidGreaterThan(String value) {
            addCriterion("aid >", value, "aid");
            return (Criteria) this;
        }

        public Criteria andAidGreaterThanOrEqualTo(String value) {
            addCriterion("aid >=", value, "aid");
            return (Criteria) this;
        }

        public Criteria andAidLessThan(String value) {
            addCriterion("aid <", value, "aid");
            return (Criteria) this;
        }

        public Criteria andAidLessThanOrEqualTo(String value) {
            addCriterion("aid <=", value, "aid");
            return (Criteria) this;
        }

        public Criteria andAidLike(String value) {
            addCriterion("aid like", value, "aid");
            return (Criteria) this;
        }

        public Criteria andAidNotLike(String value) {
            addCriterion("aid not like", value, "aid");
            return (Criteria) this;
        }

        public Criteria andAidIn(List<String> values) {
            addCriterion("aid in", values, "aid");
            return (Criteria) this;
        }

        public Criteria andAidNotIn(List<String> values) {
            addCriterion("aid not in", values, "aid");
            return (Criteria) this;
        }

        public Criteria andAidBetween(String value1, String value2) {
            addCriterion("aid between", value1, value2, "aid");
            return (Criteria) this;
        }

        public Criteria andAidNotBetween(String value1, String value2) {
            addCriterion("aid not between", value1, value2, "aid");
            return (Criteria) this;
        }

        public Criteria andSinIsNull() {
            addCriterion("sin is null");
            return (Criteria) this;
        }

        public Criteria andSinIsNotNull() {
            addCriterion("sin is not null");
            return (Criteria) this;
        }

        public Criteria andSinEqualTo(String value) {
            addCriterion("sin =", value, "sin");
            return (Criteria) this;
        }

        public Criteria andSinNotEqualTo(String value) {
            addCriterion("sin <>", value, "sin");
            return (Criteria) this;
        }

        public Criteria andSinGreaterThan(String value) {
            addCriterion("sin >", value, "sin");
            return (Criteria) this;
        }

        public Criteria andSinGreaterThanOrEqualTo(String value) {
            addCriterion("sin >=", value, "sin");
            return (Criteria) this;
        }

        public Criteria andSinLessThan(String value) {
            addCriterion("sin <", value, "sin");
            return (Criteria) this;
        }

        public Criteria andSinLessThanOrEqualTo(String value) {
            addCriterion("sin <=", value, "sin");
            return (Criteria) this;
        }

        public Criteria andSinLike(String value) {
            addCriterion("sin like", value, "sin");
            return (Criteria) this;
        }

        public Criteria andSinNotLike(String value) {
            addCriterion("sin not like", value, "sin");
            return (Criteria) this;
        }

        public Criteria andSinIn(List<String> values) {
            addCriterion("sin in", values, "sin");
            return (Criteria) this;
        }

        public Criteria andSinNotIn(List<String> values) {
            addCriterion("sin not in", values, "sin");
            return (Criteria) this;
        }

        public Criteria andSinBetween(String value1, String value2) {
            addCriterion("sin between", value1, value2, "sin");
            return (Criteria) this;
        }

        public Criteria andSinNotBetween(String value1, String value2) {
            addCriterion("sin not between", value1, value2, "sin");
            return (Criteria) this;
        }

        public Criteria andSdinIsNull() {
            addCriterion("sdin is null");
            return (Criteria) this;
        }

        public Criteria andSdinIsNotNull() {
            addCriterion("sdin is not null");
            return (Criteria) this;
        }

        public Criteria andSdinEqualTo(String value) {
            addCriterion("sdin =", value, "sdin");
            return (Criteria) this;
        }

        public Criteria andSdinNotEqualTo(String value) {
            addCriterion("sdin <>", value, "sdin");
            return (Criteria) this;
        }

        public Criteria andSdinGreaterThan(String value) {
            addCriterion("sdin >", value, "sdin");
            return (Criteria) this;
        }

        public Criteria andSdinGreaterThanOrEqualTo(String value) {
            addCriterion("sdin >=", value, "sdin");
            return (Criteria) this;
        }

        public Criteria andSdinLessThan(String value) {
            addCriterion("sdin <", value, "sdin");
            return (Criteria) this;
        }

        public Criteria andSdinLessThanOrEqualTo(String value) {
            addCriterion("sdin <=", value, "sdin");
            return (Criteria) this;
        }

        public Criteria andSdinLike(String value) {
            addCriterion("sdin like", value, "sdin");
            return (Criteria) this;
        }

        public Criteria andSdinNotLike(String value) {
            addCriterion("sdin not like", value, "sdin");
            return (Criteria) this;
        }

        public Criteria andSdinIn(List<String> values) {
            addCriterion("sdin in", values, "sdin");
            return (Criteria) this;
        }

        public Criteria andSdinNotIn(List<String> values) {
            addCriterion("sdin not in", values, "sdin");
            return (Criteria) this;
        }

        public Criteria andSdinBetween(String value1, String value2) {
            addCriterion("sdin between", value1, value2, "sdin");
            return (Criteria) this;
        }

        public Criteria andSdinNotBetween(String value1, String value2) {
            addCriterion("sdin not between", value1, value2, "sdin");
            return (Criteria) this;
        }

        public Criteria andRoleIsNull() {
            addCriterion("role is null");
            return (Criteria) this;
        }

        public Criteria andRoleIsNotNull() {
            addCriterion("role is not null");
            return (Criteria) this;
        }

        public Criteria andRoleEqualTo(String value) {
            addCriterion("role =", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleNotEqualTo(String value) {
            addCriterion("role <>", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleGreaterThan(String value) {
            addCriterion("role >", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleGreaterThanOrEqualTo(String value) {
            addCriterion("role >=", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleLessThan(String value) {
            addCriterion("role <", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleLessThanOrEqualTo(String value) {
            addCriterion("role <=", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleLike(String value) {
            addCriterion("role like", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleNotLike(String value) {
            addCriterion("role not like", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleIn(List<String> values) {
            addCriterion("role in", values, "role");
            return (Criteria) this;
        }

        public Criteria andRoleNotIn(List<String> values) {
            addCriterion("role not in", values, "role");
            return (Criteria) this;
        }

        public Criteria andRoleBetween(String value1, String value2) {
            addCriterion("role between", value1, value2, "role");
            return (Criteria) this;
        }

        public Criteria andRoleNotBetween(String value1, String value2) {
            addCriterion("role not between", value1, value2, "role");
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