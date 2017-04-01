package com.whty.efs.data.pojo;

import java.util.ArrayList;
import java.util.List;

public class EuiccExecutionStatusTypeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EuiccExecutionStatusTypeExample() {
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

        public Criteria andStatusIdIsNull() {
            addCriterion("status_id is null");
            return (Criteria) this;
        }

        public Criteria andStatusIdIsNotNull() {
            addCriterion("status_id is not null");
            return (Criteria) this;
        }

        public Criteria andStatusIdEqualTo(String value) {
            addCriterion("status_id =", value, "statusId");
            return (Criteria) this;
        }

        public Criteria andStatusIdNotEqualTo(String value) {
            addCriterion("status_id <>", value, "statusId");
            return (Criteria) this;
        }

        public Criteria andStatusIdGreaterThan(String value) {
            addCriterion("status_id >", value, "statusId");
            return (Criteria) this;
        }

        public Criteria andStatusIdGreaterThanOrEqualTo(String value) {
            addCriterion("status_id >=", value, "statusId");
            return (Criteria) this;
        }

        public Criteria andStatusIdLessThan(String value) {
            addCriterion("status_id <", value, "statusId");
            return (Criteria) this;
        }

        public Criteria andStatusIdLessThanOrEqualTo(String value) {
            addCriterion("status_id <=", value, "statusId");
            return (Criteria) this;
        }

        public Criteria andStatusIdLike(String value) {
            addCriterion("status_id like", value, "statusId");
            return (Criteria) this;
        }

        public Criteria andStatusIdNotLike(String value) {
            addCriterion("status_id not like", value, "statusId");
            return (Criteria) this;
        }

        public Criteria andStatusIdIn(List<String> values) {
            addCriterion("status_id in", values, "statusId");
            return (Criteria) this;
        }

        public Criteria andStatusIdNotIn(List<String> values) {
            addCriterion("status_id not in", values, "statusId");
            return (Criteria) this;
        }

        public Criteria andStatusIdBetween(String value1, String value2) {
            addCriterion("status_id between", value1, value2, "statusId");
            return (Criteria) this;
        }

        public Criteria andStatusIdNotBetween(String value1, String value2) {
            addCriterion("status_id not between", value1, value2, "statusId");
            return (Criteria) this;
        }

        public Criteria andAuditIdIsNull() {
            addCriterion("audit_id is null");
            return (Criteria) this;
        }

        public Criteria andAuditIdIsNotNull() {
            addCriterion("audit_id is not null");
            return (Criteria) this;
        }

        public Criteria andAuditIdEqualTo(String value) {
            addCriterion("audit_id =", value, "auditId");
            return (Criteria) this;
        }

        public Criteria andAuditIdNotEqualTo(String value) {
            addCriterion("audit_id <>", value, "auditId");
            return (Criteria) this;
        }

        public Criteria andAuditIdGreaterThan(String value) {
            addCriterion("audit_id >", value, "auditId");
            return (Criteria) this;
        }

        public Criteria andAuditIdGreaterThanOrEqualTo(String value) {
            addCriterion("audit_id >=", value, "auditId");
            return (Criteria) this;
        }

        public Criteria andAuditIdLessThan(String value) {
            addCriterion("audit_id <", value, "auditId");
            return (Criteria) this;
        }

        public Criteria andAuditIdLessThanOrEqualTo(String value) {
            addCriterion("audit_id <=", value, "auditId");
            return (Criteria) this;
        }

        public Criteria andAuditIdLike(String value) {
            addCriterion("audit_id like", value, "auditId");
            return (Criteria) this;
        }

        public Criteria andAuditIdNotLike(String value) {
            addCriterion("audit_id not like", value, "auditId");
            return (Criteria) this;
        }

        public Criteria andAuditIdIn(List<String> values) {
            addCriterion("audit_id in", values, "auditId");
            return (Criteria) this;
        }

        public Criteria andAuditIdNotIn(List<String> values) {
            addCriterion("audit_id not in", values, "auditId");
            return (Criteria) this;
        }

        public Criteria andAuditIdBetween(String value1, String value2) {
            addCriterion("audit_id between", value1, value2, "auditId");
            return (Criteria) this;
        }

        public Criteria andAuditIdNotBetween(String value1, String value2) {
            addCriterion("audit_id not between", value1, value2, "auditId");
            return (Criteria) this;
        }

        public Criteria andStatusTypeIsNull() {
            addCriterion("status_type is null");
            return (Criteria) this;
        }

        public Criteria andStatusTypeIsNotNull() {
            addCriterion("status_type is not null");
            return (Criteria) this;
        }

        public Criteria andStatusTypeEqualTo(String value) {
            addCriterion("status_type =", value, "statusType");
            return (Criteria) this;
        }

        public Criteria andStatusTypeNotEqualTo(String value) {
            addCriterion("status_type <>", value, "statusType");
            return (Criteria) this;
        }

        public Criteria andStatusTypeGreaterThan(String value) {
            addCriterion("status_type >", value, "statusType");
            return (Criteria) this;
        }

        public Criteria andStatusTypeGreaterThanOrEqualTo(String value) {
            addCriterion("status_type >=", value, "statusType");
            return (Criteria) this;
        }

        public Criteria andStatusTypeLessThan(String value) {
            addCriterion("status_type <", value, "statusType");
            return (Criteria) this;
        }

        public Criteria andStatusTypeLessThanOrEqualTo(String value) {
            addCriterion("status_type <=", value, "statusType");
            return (Criteria) this;
        }

        public Criteria andStatusTypeLike(String value) {
            addCriterion("status_type like", value, "statusType");
            return (Criteria) this;
        }

        public Criteria andStatusTypeNotLike(String value) {
            addCriterion("status_type not like", value, "statusType");
            return (Criteria) this;
        }

        public Criteria andStatusTypeIn(List<String> values) {
            addCriterion("status_type in", values, "statusType");
            return (Criteria) this;
        }

        public Criteria andStatusTypeNotIn(List<String> values) {
            addCriterion("status_type not in", values, "statusType");
            return (Criteria) this;
        }

        public Criteria andStatusTypeBetween(String value1, String value2) {
            addCriterion("status_type between", value1, value2, "statusType");
            return (Criteria) this;
        }

        public Criteria andStatusTypeNotBetween(String value1, String value2) {
            addCriterion("status_type not between", value1, value2, "statusType");
            return (Criteria) this;
        }

        public Criteria andSubjectIsNull() {
            addCriterion("subject is null");
            return (Criteria) this;
        }

        public Criteria andSubjectIsNotNull() {
            addCriterion("subject is not null");
            return (Criteria) this;
        }

        public Criteria andSubjectEqualTo(String value) {
            addCriterion("subject =", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectNotEqualTo(String value) {
            addCriterion("subject <>", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectGreaterThan(String value) {
            addCriterion("subject >", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectGreaterThanOrEqualTo(String value) {
            addCriterion("subject >=", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectLessThan(String value) {
            addCriterion("subject <", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectLessThanOrEqualTo(String value) {
            addCriterion("subject <=", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectLike(String value) {
            addCriterion("subject like", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectNotLike(String value) {
            addCriterion("subject not like", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectIn(List<String> values) {
            addCriterion("subject in", values, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectNotIn(List<String> values) {
            addCriterion("subject not in", values, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectBetween(String value1, String value2) {
            addCriterion("subject between", value1, value2, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectNotBetween(String value1, String value2) {
            addCriterion("subject not between", value1, value2, "subject");
            return (Criteria) this;
        }

        public Criteria andReasonIsNull() {
            addCriterion("reason is null");
            return (Criteria) this;
        }

        public Criteria andReasonIsNotNull() {
            addCriterion("reason is not null");
            return (Criteria) this;
        }

        public Criteria andReasonEqualTo(String value) {
            addCriterion("reason =", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotEqualTo(String value) {
            addCriterion("reason <>", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonGreaterThan(String value) {
            addCriterion("reason >", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonGreaterThanOrEqualTo(String value) {
            addCriterion("reason >=", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLessThan(String value) {
            addCriterion("reason <", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLessThanOrEqualTo(String value) {
            addCriterion("reason <=", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLike(String value) {
            addCriterion("reason like", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotLike(String value) {
            addCriterion("reason not like", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonIn(List<String> values) {
            addCriterion("reason in", values, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotIn(List<String> values) {
            addCriterion("reason not in", values, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonBetween(String value1, String value2) {
            addCriterion("reason between", value1, value2, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotBetween(String value1, String value2) {
            addCriterion("reason not between", value1, value2, "reason");
            return (Criteria) this;
        }

        public Criteria andSubjectIdentifierIsNull() {
            addCriterion("subject_identifier is null");
            return (Criteria) this;
        }

        public Criteria andSubjectIdentifierIsNotNull() {
            addCriterion("subject_identifier is not null");
            return (Criteria) this;
        }

        public Criteria andSubjectIdentifierEqualTo(String value) {
            addCriterion("subject_identifier =", value, "subjectIdentifier");
            return (Criteria) this;
        }

        public Criteria andSubjectIdentifierNotEqualTo(String value) {
            addCriterion("subject_identifier <>", value, "subjectIdentifier");
            return (Criteria) this;
        }

        public Criteria andSubjectIdentifierGreaterThan(String value) {
            addCriterion("subject_identifier >", value, "subjectIdentifier");
            return (Criteria) this;
        }

        public Criteria andSubjectIdentifierGreaterThanOrEqualTo(String value) {
            addCriterion("subject_identifier >=", value, "subjectIdentifier");
            return (Criteria) this;
        }

        public Criteria andSubjectIdentifierLessThan(String value) {
            addCriterion("subject_identifier <", value, "subjectIdentifier");
            return (Criteria) this;
        }

        public Criteria andSubjectIdentifierLessThanOrEqualTo(String value) {
            addCriterion("subject_identifier <=", value, "subjectIdentifier");
            return (Criteria) this;
        }

        public Criteria andSubjectIdentifierLike(String value) {
            addCriterion("subject_identifier like", value, "subjectIdentifier");
            return (Criteria) this;
        }

        public Criteria andSubjectIdentifierNotLike(String value) {
            addCriterion("subject_identifier not like", value, "subjectIdentifier");
            return (Criteria) this;
        }

        public Criteria andSubjectIdentifierIn(List<String> values) {
            addCriterion("subject_identifier in", values, "subjectIdentifier");
            return (Criteria) this;
        }

        public Criteria andSubjectIdentifierNotIn(List<String> values) {
            addCriterion("subject_identifier not in", values, "subjectIdentifier");
            return (Criteria) this;
        }

        public Criteria andSubjectIdentifierBetween(String value1, String value2) {
            addCriterion("subject_identifier between", value1, value2, "subjectIdentifier");
            return (Criteria) this;
        }

        public Criteria andSubjectIdentifierNotBetween(String value1, String value2) {
            addCriterion("subject_identifier not between", value1, value2, "subjectIdentifier");
            return (Criteria) this;
        }

        public Criteria andMessageIsNull() {
            addCriterion("message is null");
            return (Criteria) this;
        }

        public Criteria andMessageIsNotNull() {
            addCriterion("message is not null");
            return (Criteria) this;
        }

        public Criteria andMessageEqualTo(String value) {
            addCriterion("message =", value, "message");
            return (Criteria) this;
        }

        public Criteria andMessageNotEqualTo(String value) {
            addCriterion("message <>", value, "message");
            return (Criteria) this;
        }

        public Criteria andMessageGreaterThan(String value) {
            addCriterion("message >", value, "message");
            return (Criteria) this;
        }

        public Criteria andMessageGreaterThanOrEqualTo(String value) {
            addCriterion("message >=", value, "message");
            return (Criteria) this;
        }

        public Criteria andMessageLessThan(String value) {
            addCriterion("message <", value, "message");
            return (Criteria) this;
        }

        public Criteria andMessageLessThanOrEqualTo(String value) {
            addCriterion("message <=", value, "message");
            return (Criteria) this;
        }

        public Criteria andMessageLike(String value) {
            addCriterion("message like", value, "message");
            return (Criteria) this;
        }

        public Criteria andMessageNotLike(String value) {
            addCriterion("message not like", value, "message");
            return (Criteria) this;
        }

        public Criteria andMessageIn(List<String> values) {
            addCriterion("message in", values, "message");
            return (Criteria) this;
        }

        public Criteria andMessageNotIn(List<String> values) {
            addCriterion("message not in", values, "message");
            return (Criteria) this;
        }

        public Criteria andMessageBetween(String value1, String value2) {
            addCriterion("message between", value1, value2, "message");
            return (Criteria) this;
        }

        public Criteria andMessageNotBetween(String value1, String value2) {
            addCriterion("message not between", value1, value2, "message");
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