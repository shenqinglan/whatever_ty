package com.whty.efs.data.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EuiccAuditTrailRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EuiccAuditTrailRecordExample() {
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

        public Criteria andEidIsNull() {
            addCriterion("eid is null");
            return (Criteria) this;
        }

        public Criteria andEidIsNotNull() {
            addCriterion("eid is not null");
            return (Criteria) this;
        }

        public Criteria andEidEqualTo(String value) {
            addCriterion("eid =", value, "eid");
            return (Criteria) this;
        }

        public Criteria andEidNotEqualTo(String value) {
            addCriterion("eid <>", value, "eid");
            return (Criteria) this;
        }

        public Criteria andEidGreaterThan(String value) {
            addCriterion("eid >", value, "eid");
            return (Criteria) this;
        }

        public Criteria andEidGreaterThanOrEqualTo(String value) {
            addCriterion("eid >=", value, "eid");
            return (Criteria) this;
        }

        public Criteria andEidLessThan(String value) {
            addCriterion("eid <", value, "eid");
            return (Criteria) this;
        }

        public Criteria andEidLessThanOrEqualTo(String value) {
            addCriterion("eid <=", value, "eid");
            return (Criteria) this;
        }

        public Criteria andEidLike(String value) {
            addCriterion("eid like", value, "eid");
            return (Criteria) this;
        }

        public Criteria andEidNotLike(String value) {
            addCriterion("eid not like", value, "eid");
            return (Criteria) this;
        }

        public Criteria andEidIn(List<String> values) {
            addCriterion("eid in", values, "eid");
            return (Criteria) this;
        }

        public Criteria andEidNotIn(List<String> values) {
            addCriterion("eid not in", values, "eid");
            return (Criteria) this;
        }

        public Criteria andEidBetween(String value1, String value2) {
            addCriterion("eid between", value1, value2, "eid");
            return (Criteria) this;
        }

        public Criteria andEidNotBetween(String value1, String value2) {
            addCriterion("eid not between", value1, value2, "eid");
            return (Criteria) this;
        }

        public Criteria andIccidIsNull() {
            addCriterion("iccid is null");
            return (Criteria) this;
        }

        public Criteria andIccidIsNotNull() {
            addCriterion("iccid is not null");
            return (Criteria) this;
        }

        public Criteria andIccidEqualTo(String value) {
            addCriterion("iccid =", value, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidNotEqualTo(String value) {
            addCriterion("iccid <>", value, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidGreaterThan(String value) {
            addCriterion("iccid >", value, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidGreaterThanOrEqualTo(String value) {
            addCriterion("iccid >=", value, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidLessThan(String value) {
            addCriterion("iccid <", value, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidLessThanOrEqualTo(String value) {
            addCriterion("iccid <=", value, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidLike(String value) {
            addCriterion("iccid like", value, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidNotLike(String value) {
            addCriterion("iccid not like", value, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidIn(List<String> values) {
            addCriterion("iccid in", values, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidNotIn(List<String> values) {
            addCriterion("iccid not in", values, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidBetween(String value1, String value2) {
            addCriterion("iccid between", value1, value2, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidNotBetween(String value1, String value2) {
            addCriterion("iccid not between", value1, value2, "iccid");
            return (Criteria) this;
        }

        public Criteria andIsdPAidIsNull() {
            addCriterion("isd_p_aid is null");
            return (Criteria) this;
        }

        public Criteria andIsdPAidIsNotNull() {
            addCriterion("isd_p_aid is not null");
            return (Criteria) this;
        }

        public Criteria andIsdPAidEqualTo(String value) {
            addCriterion("isd_p_aid =", value, "isdPAid");
            return (Criteria) this;
        }

        public Criteria andIsdPAidNotEqualTo(String value) {
            addCriterion("isd_p_aid <>", value, "isdPAid");
            return (Criteria) this;
        }

        public Criteria andIsdPAidGreaterThan(String value) {
            addCriterion("isd_p_aid >", value, "isdPAid");
            return (Criteria) this;
        }

        public Criteria andIsdPAidGreaterThanOrEqualTo(String value) {
            addCriterion("isd_p_aid >=", value, "isdPAid");
            return (Criteria) this;
        }

        public Criteria andIsdPAidLessThan(String value) {
            addCriterion("isd_p_aid <", value, "isdPAid");
            return (Criteria) this;
        }

        public Criteria andIsdPAidLessThanOrEqualTo(String value) {
            addCriterion("isd_p_aid <=", value, "isdPAid");
            return (Criteria) this;
        }

        public Criteria andIsdPAidLike(String value) {
            addCriterion("isd_p_aid like", value, "isdPAid");
            return (Criteria) this;
        }

        public Criteria andIsdPAidNotLike(String value) {
            addCriterion("isd_p_aid not like", value, "isdPAid");
            return (Criteria) this;
        }

        public Criteria andIsdPAidIn(List<String> values) {
            addCriterion("isd_p_aid in", values, "isdPAid");
            return (Criteria) this;
        }

        public Criteria andIsdPAidNotIn(List<String> values) {
            addCriterion("isd_p_aid not in", values, "isdPAid");
            return (Criteria) this;
        }

        public Criteria andIsdPAidBetween(String value1, String value2) {
            addCriterion("isd_p_aid between", value1, value2, "isdPAid");
            return (Criteria) this;
        }

        public Criteria andIsdPAidNotBetween(String value1, String value2) {
            addCriterion("isd_p_aid not between", value1, value2, "isdPAid");
            return (Criteria) this;
        }

        public Criteria andOperationDateIsNull() {
            addCriterion("operation_date is null");
            return (Criteria) this;
        }

        public Criteria andOperationDateIsNotNull() {
            addCriterion("operation_date is not null");
            return (Criteria) this;
        }

        public Criteria andOperationDateEqualTo(Date value) {
            addCriterion("operation_date =", value, "operationDate");
            return (Criteria) this;
        }

        public Criteria andOperationDateNotEqualTo(Date value) {
            addCriterion("operation_date <>", value, "operationDate");
            return (Criteria) this;
        }

        public Criteria andOperationDateGreaterThan(Date value) {
            addCriterion("operation_date >", value, "operationDate");
            return (Criteria) this;
        }

        public Criteria andOperationDateGreaterThanOrEqualTo(Date value) {
            addCriterion("operation_date >=", value, "operationDate");
            return (Criteria) this;
        }

        public Criteria andOperationDateLessThan(Date value) {
            addCriterion("operation_date <", value, "operationDate");
            return (Criteria) this;
        }

        public Criteria andOperationDateLessThanOrEqualTo(Date value) {
            addCriterion("operation_date <=", value, "operationDate");
            return (Criteria) this;
        }

        public Criteria andOperationDateIn(List<Date> values) {
            addCriterion("operation_date in", values, "operationDate");
            return (Criteria) this;
        }

        public Criteria andOperationDateNotIn(List<Date> values) {
            addCriterion("operation_date not in", values, "operationDate");
            return (Criteria) this;
        }

        public Criteria andOperationDateBetween(Date value1, Date value2) {
            addCriterion("operation_date between", value1, value2, "operationDate");
            return (Criteria) this;
        }

        public Criteria andOperationDateNotBetween(Date value1, Date value2) {
            addCriterion("operation_date not between", value1, value2, "operationDate");
            return (Criteria) this;
        }

        public Criteria andOperationTypeIsNull() {
            addCriterion("operation_type is null");
            return (Criteria) this;
        }

        public Criteria andOperationTypeIsNotNull() {
            addCriterion("operation_type is not null");
            return (Criteria) this;
        }

        public Criteria andOperationTypeEqualTo(String value) {
            addCriterion("operation_type =", value, "operationType");
            return (Criteria) this;
        }

        public Criteria andOperationTypeNotEqualTo(String value) {
            addCriterion("operation_type <>", value, "operationType");
            return (Criteria) this;
        }

        public Criteria andOperationTypeGreaterThan(String value) {
            addCriterion("operation_type >", value, "operationType");
            return (Criteria) this;
        }

        public Criteria andOperationTypeGreaterThanOrEqualTo(String value) {
            addCriterion("operation_type >=", value, "operationType");
            return (Criteria) this;
        }

        public Criteria andOperationTypeLessThan(String value) {
            addCriterion("operation_type <", value, "operationType");
            return (Criteria) this;
        }

        public Criteria andOperationTypeLessThanOrEqualTo(String value) {
            addCriterion("operation_type <=", value, "operationType");
            return (Criteria) this;
        }

        public Criteria andOperationTypeLike(String value) {
            addCriterion("operation_type like", value, "operationType");
            return (Criteria) this;
        }

        public Criteria andOperationTypeNotLike(String value) {
            addCriterion("operation_type not like", value, "operationType");
            return (Criteria) this;
        }

        public Criteria andOperationTypeIn(List<String> values) {
            addCriterion("operation_type in", values, "operationType");
            return (Criteria) this;
        }

        public Criteria andOperationTypeNotIn(List<String> values) {
            addCriterion("operation_type not in", values, "operationType");
            return (Criteria) this;
        }

        public Criteria andOperationTypeBetween(String value1, String value2) {
            addCriterion("operation_type between", value1, value2, "operationType");
            return (Criteria) this;
        }

        public Criteria andOperationTypeNotBetween(String value1, String value2) {
            addCriterion("operation_type not between", value1, value2, "operationType");
            return (Criteria) this;
        }

        public Criteria andRequesterIdIsNull() {
            addCriterion("requester_id is null");
            return (Criteria) this;
        }

        public Criteria andRequesterIdIsNotNull() {
            addCriterion("requester_id is not null");
            return (Criteria) this;
        }

        public Criteria andRequesterIdEqualTo(String value) {
            addCriterion("requester_id =", value, "requesterId");
            return (Criteria) this;
        }

        public Criteria andRequesterIdNotEqualTo(String value) {
            addCriterion("requester_id <>", value, "requesterId");
            return (Criteria) this;
        }

        public Criteria andRequesterIdGreaterThan(String value) {
            addCriterion("requester_id >", value, "requesterId");
            return (Criteria) this;
        }

        public Criteria andRequesterIdGreaterThanOrEqualTo(String value) {
            addCriterion("requester_id >=", value, "requesterId");
            return (Criteria) this;
        }

        public Criteria andRequesterIdLessThan(String value) {
            addCriterion("requester_id <", value, "requesterId");
            return (Criteria) this;
        }

        public Criteria andRequesterIdLessThanOrEqualTo(String value) {
            addCriterion("requester_id <=", value, "requesterId");
            return (Criteria) this;
        }

        public Criteria andRequesterIdLike(String value) {
            addCriterion("requester_id like", value, "requesterId");
            return (Criteria) this;
        }

        public Criteria andRequesterIdNotLike(String value) {
            addCriterion("requester_id not like", value, "requesterId");
            return (Criteria) this;
        }

        public Criteria andRequesterIdIn(List<String> values) {
            addCriterion("requester_id in", values, "requesterId");
            return (Criteria) this;
        }

        public Criteria andRequesterIdNotIn(List<String> values) {
            addCriterion("requester_id not in", values, "requesterId");
            return (Criteria) this;
        }

        public Criteria andRequesterIdBetween(String value1, String value2) {
            addCriterion("requester_id between", value1, value2, "requesterId");
            return (Criteria) this;
        }

        public Criteria andRequesterIdNotBetween(String value1, String value2) {
            addCriterion("requester_id not between", value1, value2, "requesterId");
            return (Criteria) this;
        }

        public Criteria andImeiIsNull() {
            addCriterion("imei is null");
            return (Criteria) this;
        }

        public Criteria andImeiIsNotNull() {
            addCriterion("imei is not null");
            return (Criteria) this;
        }

        public Criteria andImeiEqualTo(String value) {
            addCriterion("imei =", value, "imei");
            return (Criteria) this;
        }

        public Criteria andImeiNotEqualTo(String value) {
            addCriterion("imei <>", value, "imei");
            return (Criteria) this;
        }

        public Criteria andImeiGreaterThan(String value) {
            addCriterion("imei >", value, "imei");
            return (Criteria) this;
        }

        public Criteria andImeiGreaterThanOrEqualTo(String value) {
            addCriterion("imei >=", value, "imei");
            return (Criteria) this;
        }

        public Criteria andImeiLessThan(String value) {
            addCriterion("imei <", value, "imei");
            return (Criteria) this;
        }

        public Criteria andImeiLessThanOrEqualTo(String value) {
            addCriterion("imei <=", value, "imei");
            return (Criteria) this;
        }

        public Criteria andImeiLike(String value) {
            addCriterion("imei like", value, "imei");
            return (Criteria) this;
        }

        public Criteria andImeiNotLike(String value) {
            addCriterion("imei not like", value, "imei");
            return (Criteria) this;
        }

        public Criteria andImeiIn(List<String> values) {
            addCriterion("imei in", values, "imei");
            return (Criteria) this;
        }

        public Criteria andImeiNotIn(List<String> values) {
            addCriterion("imei not in", values, "imei");
            return (Criteria) this;
        }

        public Criteria andImeiBetween(String value1, String value2) {
            addCriterion("imei between", value1, value2, "imei");
            return (Criteria) this;
        }

        public Criteria andImeiNotBetween(String value1, String value2) {
            addCriterion("imei not between", value1, value2, "imei");
            return (Criteria) this;
        }

        public Criteria andMeidIsNull() {
            addCriterion("meid is null");
            return (Criteria) this;
        }

        public Criteria andMeidIsNotNull() {
            addCriterion("meid is not null");
            return (Criteria) this;
        }

        public Criteria andMeidEqualTo(String value) {
            addCriterion("meid =", value, "meid");
            return (Criteria) this;
        }

        public Criteria andMeidNotEqualTo(String value) {
            addCriterion("meid <>", value, "meid");
            return (Criteria) this;
        }

        public Criteria andMeidGreaterThan(String value) {
            addCriterion("meid >", value, "meid");
            return (Criteria) this;
        }

        public Criteria andMeidGreaterThanOrEqualTo(String value) {
            addCriterion("meid >=", value, "meid");
            return (Criteria) this;
        }

        public Criteria andMeidLessThan(String value) {
            addCriterion("meid <", value, "meid");
            return (Criteria) this;
        }

        public Criteria andMeidLessThanOrEqualTo(String value) {
            addCriterion("meid <=", value, "meid");
            return (Criteria) this;
        }

        public Criteria andMeidLike(String value) {
            addCriterion("meid like", value, "meid");
            return (Criteria) this;
        }

        public Criteria andMeidNotLike(String value) {
            addCriterion("meid not like", value, "meid");
            return (Criteria) this;
        }

        public Criteria andMeidIn(List<String> values) {
            addCriterion("meid in", values, "meid");
            return (Criteria) this;
        }

        public Criteria andMeidNotIn(List<String> values) {
            addCriterion("meid not in", values, "meid");
            return (Criteria) this;
        }

        public Criteria andMeidBetween(String value1, String value2) {
            addCriterion("meid between", value1, value2, "meid");
            return (Criteria) this;
        }

        public Criteria andMeidNotBetween(String value1, String value2) {
            addCriterion("meid not between", value1, value2, "meid");
            return (Criteria) this;
        }

        public Criteria andSmsrIdIsNull() {
            addCriterion("smsr_id is null");
            return (Criteria) this;
        }

        public Criteria andSmsrIdIsNotNull() {
            addCriterion("smsr_id is not null");
            return (Criteria) this;
        }

        public Criteria andSmsrIdEqualTo(String value) {
            addCriterion("smsr_id =", value, "smsrId");
            return (Criteria) this;
        }

        public Criteria andSmsrIdNotEqualTo(String value) {
            addCriterion("smsr_id <>", value, "smsrId");
            return (Criteria) this;
        }

        public Criteria andSmsrIdGreaterThan(String value) {
            addCriterion("smsr_id >", value, "smsrId");
            return (Criteria) this;
        }

        public Criteria andSmsrIdGreaterThanOrEqualTo(String value) {
            addCriterion("smsr_id >=", value, "smsrId");
            return (Criteria) this;
        }

        public Criteria andSmsrIdLessThan(String value) {
            addCriterion("smsr_id <", value, "smsrId");
            return (Criteria) this;
        }

        public Criteria andSmsrIdLessThanOrEqualTo(String value) {
            addCriterion("smsr_id <=", value, "smsrId");
            return (Criteria) this;
        }

        public Criteria andSmsrIdLike(String value) {
            addCriterion("smsr_id like", value, "smsrId");
            return (Criteria) this;
        }

        public Criteria andSmsrIdNotLike(String value) {
            addCriterion("smsr_id not like", value, "smsrId");
            return (Criteria) this;
        }

        public Criteria andSmsrIdIn(List<String> values) {
            addCriterion("smsr_id in", values, "smsrId");
            return (Criteria) this;
        }

        public Criteria andSmsrIdNotIn(List<String> values) {
            addCriterion("smsr_id not in", values, "smsrId");
            return (Criteria) this;
        }

        public Criteria andSmsrIdBetween(String value1, String value2) {
            addCriterion("smsr_id between", value1, value2, "smsrId");
            return (Criteria) this;
        }

        public Criteria andSmsrIdNotBetween(String value1, String value2) {
            addCriterion("smsr_id not between", value1, value2, "smsrId");
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