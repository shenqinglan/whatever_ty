package com.whty.euicc.base.pojo;

import java.util.ArrayList;
import java.util.List;

public class ApprovalInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ApprovalInfoExample() {
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

        public Criteria andApprovalUserIdIsNull() {
            addCriterion("approval_user_id is null");
            return (Criteria) this;
        }

        public Criteria andApprovalUserIdIsNotNull() {
            addCriterion("approval_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andApprovalUserIdEqualTo(String value) {
            addCriterion("approval_user_id =", value, "approvalUserId");
            return (Criteria) this;
        }

        public Criteria andApprovalUserIdNotEqualTo(String value) {
            addCriterion("approval_user_id <>", value, "approvalUserId");
            return (Criteria) this;
        }

        public Criteria andApprovalUserIdGreaterThan(String value) {
            addCriterion("approval_user_id >", value, "approvalUserId");
            return (Criteria) this;
        }

        public Criteria andApprovalUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("approval_user_id >=", value, "approvalUserId");
            return (Criteria) this;
        }

        public Criteria andApprovalUserIdLessThan(String value) {
            addCriterion("approval_user_id <", value, "approvalUserId");
            return (Criteria) this;
        }

        public Criteria andApprovalUserIdLessThanOrEqualTo(String value) {
            addCriterion("approval_user_id <=", value, "approvalUserId");
            return (Criteria) this;
        }

        public Criteria andApprovalUserIdLike(String value) {
            addCriterion("approval_user_id like", value, "approvalUserId");
            return (Criteria) this;
        }

        public Criteria andApprovalUserIdNotLike(String value) {
            addCriterion("approval_user_id not like", value, "approvalUserId");
            return (Criteria) this;
        }

        public Criteria andApprovalUserIdIn(List<String> values) {
            addCriterion("approval_user_id in", values, "approvalUserId");
            return (Criteria) this;
        }

        public Criteria andApprovalUserIdNotIn(List<String> values) {
            addCriterion("approval_user_id not in", values, "approvalUserId");
            return (Criteria) this;
        }

        public Criteria andApprovalUserIdBetween(String value1, String value2) {
            addCriterion("approval_user_id between", value1, value2, "approvalUserId");
            return (Criteria) this;
        }

        public Criteria andApprovalUserIdNotBetween(String value1, String value2) {
            addCriterion("approval_user_id not between", value1, value2, "approvalUserId");
            return (Criteria) this;
        }

        public Criteria andTotalApprovalAmountIsNull() {
            addCriterion("total_approval_amount is null");
            return (Criteria) this;
        }

        public Criteria andTotalApprovalAmountIsNotNull() {
            addCriterion("total_approval_amount is not null");
            return (Criteria) this;
        }

        public Criteria andTotalApprovalAmountEqualTo(Integer value) {
            addCriterion("total_approval_amount =", value, "totalApprovalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalApprovalAmountNotEqualTo(Integer value) {
            addCriterion("total_approval_amount <>", value, "totalApprovalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalApprovalAmountGreaterThan(Integer value) {
            addCriterion("total_approval_amount >", value, "totalApprovalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalApprovalAmountGreaterThanOrEqualTo(Integer value) {
            addCriterion("total_approval_amount >=", value, "totalApprovalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalApprovalAmountLessThan(Integer value) {
            addCriterion("total_approval_amount <", value, "totalApprovalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalApprovalAmountLessThanOrEqualTo(Integer value) {
            addCriterion("total_approval_amount <=", value, "totalApprovalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalApprovalAmountIn(List<Integer> values) {
            addCriterion("total_approval_amount in", values, "totalApprovalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalApprovalAmountNotIn(List<Integer> values) {
            addCriterion("total_approval_amount not in", values, "totalApprovalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalApprovalAmountBetween(Integer value1, Integer value2) {
            addCriterion("total_approval_amount between", value1, value2, "totalApprovalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalApprovalAmountNotBetween(Integer value1, Integer value2) {
            addCriterion("total_approval_amount not between", value1, value2, "totalApprovalAmount");
            return (Criteria) this;
        }

        public Criteria andApprovalAmountIsNull() {
            addCriterion("approval_amount is null");
            return (Criteria) this;
        }

        public Criteria andApprovalAmountIsNotNull() {
            addCriterion("approval_amount is not null");
            return (Criteria) this;
        }

        public Criteria andApprovalAmountEqualTo(Integer value) {
            addCriterion("approval_amount =", value, "approvalAmount");
            return (Criteria) this;
        }

        public Criteria andApprovalAmountNotEqualTo(Integer value) {
            addCriterion("approval_amount <>", value, "approvalAmount");
            return (Criteria) this;
        }

        public Criteria andApprovalAmountGreaterThan(Integer value) {
            addCriterion("approval_amount >", value, "approvalAmount");
            return (Criteria) this;
        }

        public Criteria andApprovalAmountGreaterThanOrEqualTo(Integer value) {
            addCriterion("approval_amount >=", value, "approvalAmount");
            return (Criteria) this;
        }

        public Criteria andApprovalAmountLessThan(Integer value) {
            addCriterion("approval_amount <", value, "approvalAmount");
            return (Criteria) this;
        }

        public Criteria andApprovalAmountLessThanOrEqualTo(Integer value) {
            addCriterion("approval_amount <=", value, "approvalAmount");
            return (Criteria) this;
        }

        public Criteria andApprovalAmountIn(List<Integer> values) {
            addCriterion("approval_amount in", values, "approvalAmount");
            return (Criteria) this;
        }

        public Criteria andApprovalAmountNotIn(List<Integer> values) {
            addCriterion("approval_amount not in", values, "approvalAmount");
            return (Criteria) this;
        }

        public Criteria andApprovalAmountBetween(Integer value1, Integer value2) {
            addCriterion("approval_amount between", value1, value2, "approvalAmount");
            return (Criteria) this;
        }

        public Criteria andApprovalAmountNotBetween(Integer value1, Integer value2) {
            addCriterion("approval_amount not between", value1, value2, "approvalAmount");
            return (Criteria) this;
        }

        public Criteria andRealAmountIsNull() {
            addCriterion("real_amount is null");
            return (Criteria) this;
        }

        public Criteria andRealAmountIsNotNull() {
            addCriterion("real_amount is not null");
            return (Criteria) this;
        }

        public Criteria andRealAmountEqualTo(Integer value) {
            addCriterion("real_amount =", value, "realAmount");
            return (Criteria) this;
        }

        public Criteria andRealAmountNotEqualTo(Integer value) {
            addCriterion("real_amount <>", value, "realAmount");
            return (Criteria) this;
        }

        public Criteria andRealAmountGreaterThan(Integer value) {
            addCriterion("real_amount >", value, "realAmount");
            return (Criteria) this;
        }

        public Criteria andRealAmountGreaterThanOrEqualTo(Integer value) {
            addCriterion("real_amount >=", value, "realAmount");
            return (Criteria) this;
        }

        public Criteria andRealAmountLessThan(Integer value) {
            addCriterion("real_amount <", value, "realAmount");
            return (Criteria) this;
        }

        public Criteria andRealAmountLessThanOrEqualTo(Integer value) {
            addCriterion("real_amount <=", value, "realAmount");
            return (Criteria) this;
        }

        public Criteria andRealAmountIn(List<Integer> values) {
            addCriterion("real_amount in", values, "realAmount");
            return (Criteria) this;
        }

        public Criteria andRealAmountNotIn(List<Integer> values) {
            addCriterion("real_amount not in", values, "realAmount");
            return (Criteria) this;
        }

        public Criteria andRealAmountBetween(Integer value1, Integer value2) {
            addCriterion("real_amount between", value1, value2, "realAmount");
            return (Criteria) this;
        }

        public Criteria andRealAmountNotBetween(Integer value1, Integer value2) {
            addCriterion("real_amount not between", value1, value2, "realAmount");
            return (Criteria) this;
        }

        public Criteria andApprovalStateIsNull() {
            addCriterion("approval_state is null");
            return (Criteria) this;
        }

        public Criteria andApprovalStateIsNotNull() {
            addCriterion("approval_state is not null");
            return (Criteria) this;
        }

        public Criteria andApprovalStateEqualTo(String value) {
            addCriterion("approval_state =", value, "approvalState");
            return (Criteria) this;
        }

        public Criteria andApprovalStateNotEqualTo(String value) {
            addCriterion("approval_state <>", value, "approvalState");
            return (Criteria) this;
        }

        public Criteria andApprovalStateGreaterThan(String value) {
            addCriterion("approval_state >", value, "approvalState");
            return (Criteria) this;
        }

        public Criteria andApprovalStateGreaterThanOrEqualTo(String value) {
            addCriterion("approval_state >=", value, "approvalState");
            return (Criteria) this;
        }

        public Criteria andApprovalStateLessThan(String value) {
            addCriterion("approval_state <", value, "approvalState");
            return (Criteria) this;
        }

        public Criteria andApprovalStateLessThanOrEqualTo(String value) {
            addCriterion("approval_state <=", value, "approvalState");
            return (Criteria) this;
        }

        public Criteria andApprovalStateLike(String value) {
            addCriterion("approval_state like", value, "approvalState");
            return (Criteria) this;
        }

        public Criteria andApprovalStateNotLike(String value) {
            addCriterion("approval_state not like", value, "approvalState");
            return (Criteria) this;
        }

        public Criteria andApprovalStateIn(List<String> values) {
            addCriterion("approval_state in", values, "approvalState");
            return (Criteria) this;
        }

        public Criteria andApprovalStateNotIn(List<String> values) {
            addCriterion("approval_state not in", values, "approvalState");
            return (Criteria) this;
        }

        public Criteria andApprovalStateBetween(String value1, String value2) {
            addCriterion("approval_state between", value1, value2, "approvalState");
            return (Criteria) this;
        }

        public Criteria andApprovalStateNotBetween(String value1, String value2) {
            addCriterion("approval_state not between", value1, value2, "approvalState");
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