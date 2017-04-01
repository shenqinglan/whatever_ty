package com.whty.euicc.data.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TerminalDeviceInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TerminalDeviceInfoExample() {
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

        public Criteria andSnIsNull() {
            addCriterion("sn is null");
            return (Criteria) this;
        }

        public Criteria andSnIsNotNull() {
            addCriterion("sn is not null");
            return (Criteria) this;
        }

        public Criteria andSnEqualTo(String value) {
            addCriterion("sn =", value, "sn");
            return (Criteria) this;
        }

        public Criteria andSnNotEqualTo(String value) {
            addCriterion("sn <>", value, "sn");
            return (Criteria) this;
        }

        public Criteria andSnGreaterThan(String value) {
            addCriterion("sn >", value, "sn");
            return (Criteria) this;
        }

        public Criteria andSnGreaterThanOrEqualTo(String value) {
            addCriterion("sn >=", value, "sn");
            return (Criteria) this;
        }

        public Criteria andSnLessThan(String value) {
            addCriterion("sn <", value, "sn");
            return (Criteria) this;
        }

        public Criteria andSnLessThanOrEqualTo(String value) {
            addCriterion("sn <=", value, "sn");
            return (Criteria) this;
        }

        public Criteria andSnLike(String value) {
            addCriterion("sn like", value, "sn");
            return (Criteria) this;
        }

        public Criteria andSnNotLike(String value) {
            addCriterion("sn not like", value, "sn");
            return (Criteria) this;
        }

        public Criteria andSnIn(List<String> values) {
            addCriterion("sn in", values, "sn");
            return (Criteria) this;
        }

        public Criteria andSnNotIn(List<String> values) {
            addCriterion("sn not in", values, "sn");
            return (Criteria) this;
        }

        public Criteria andSnBetween(String value1, String value2) {
            addCriterion("sn between", value1, value2, "sn");
            return (Criteria) this;
        }

        public Criteria andSnNotBetween(String value1, String value2) {
            addCriterion("sn not between", value1, value2, "sn");
            return (Criteria) this;
        }

        public Criteria andHardwareIdIsNull() {
            addCriterion("hardware_id is null");
            return (Criteria) this;
        }

        public Criteria andHardwareIdIsNotNull() {
            addCriterion("hardware_id is not null");
            return (Criteria) this;
        }

        public Criteria andHardwareIdEqualTo(String value) {
            addCriterion("hardware_id =", value, "hardwareId");
            return (Criteria) this;
        }

        public Criteria andHardwareIdNotEqualTo(String value) {
            addCriterion("hardware_id <>", value, "hardwareId");
            return (Criteria) this;
        }

        public Criteria andHardwareIdGreaterThan(String value) {
            addCriterion("hardware_id >", value, "hardwareId");
            return (Criteria) this;
        }

        public Criteria andHardwareIdGreaterThanOrEqualTo(String value) {
            addCriterion("hardware_id >=", value, "hardwareId");
            return (Criteria) this;
        }

        public Criteria andHardwareIdLessThan(String value) {
            addCriterion("hardware_id <", value, "hardwareId");
            return (Criteria) this;
        }

        public Criteria andHardwareIdLessThanOrEqualTo(String value) {
            addCriterion("hardware_id <=", value, "hardwareId");
            return (Criteria) this;
        }

        public Criteria andHardwareIdLike(String value) {
            addCriterion("hardware_id like", value, "hardwareId");
            return (Criteria) this;
        }

        public Criteria andHardwareIdNotLike(String value) {
            addCriterion("hardware_id not like", value, "hardwareId");
            return (Criteria) this;
        }

        public Criteria andHardwareIdIn(List<String> values) {
            addCriterion("hardware_id in", values, "hardwareId");
            return (Criteria) this;
        }

        public Criteria andHardwareIdNotIn(List<String> values) {
            addCriterion("hardware_id not in", values, "hardwareId");
            return (Criteria) this;
        }

        public Criteria andHardwareIdBetween(String value1, String value2) {
            addCriterion("hardware_id between", value1, value2, "hardwareId");
            return (Criteria) this;
        }

        public Criteria andHardwareIdNotBetween(String value1, String value2) {
            addCriterion("hardware_id not between", value1, value2, "hardwareId");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeIsNull() {
            addCriterion("device_type is null");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeIsNotNull() {
            addCriterion("device_type is not null");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeEqualTo(String value) {
            addCriterion("device_type =", value, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeNotEqualTo(String value) {
            addCriterion("device_type <>", value, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeGreaterThan(String value) {
            addCriterion("device_type >", value, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeGreaterThanOrEqualTo(String value) {
            addCriterion("device_type >=", value, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeLessThan(String value) {
            addCriterion("device_type <", value, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeLessThanOrEqualTo(String value) {
            addCriterion("device_type <=", value, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeLike(String value) {
            addCriterion("device_type like", value, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeNotLike(String value) {
            addCriterion("device_type not like", value, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeIn(List<String> values) {
            addCriterion("device_type in", values, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeNotIn(List<String> values) {
            addCriterion("device_type not in", values, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeBetween(String value1, String value2) {
            addCriterion("device_type between", value1, value2, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeNotBetween(String value1, String value2) {
            addCriterion("device_type not between", value1, value2, "deviceType");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNull() {
            addCriterion("create_date is null");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNotNull() {
            addCriterion("create_date is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDateEqualTo(Date value) {
            addCriterion("create_date =", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualTo(Date value) {
            addCriterion("create_date <>", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThan(Date value) {
            addCriterion("create_date >", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("create_date >=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThan(Date value) {
            addCriterion("create_date <", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(Date value) {
            addCriterion("create_date <=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateIn(List<Date> values) {
            addCriterion("create_date in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotIn(List<Date> values) {
            addCriterion("create_date not in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateBetween(Date value1, Date value2) {
            addCriterion("create_date between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotBetween(Date value1, Date value2) {
            addCriterion("create_date not between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNull() {
            addCriterion("update_date is null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNotNull() {
            addCriterion("update_date is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateEqualTo(Date value) {
            addCriterion("update_date =", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotEqualTo(Date value) {
            addCriterion("update_date <>", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThan(Date value) {
            addCriterion("update_date >", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("update_date >=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThan(Date value) {
            addCriterion("update_date <", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThanOrEqualTo(Date value) {
            addCriterion("update_date <=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIn(List<Date> values) {
            addCriterion("update_date in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotIn(List<Date> values) {
            addCriterion("update_date not in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateBetween(Date value1, Date value2) {
            addCriterion("update_date between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotBetween(Date value1, Date value2) {
            addCriterion("update_date not between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andLockStateIsNull() {
            addCriterion("lock_state is null");
            return (Criteria) this;
        }

        public Criteria andLockStateIsNotNull() {
            addCriterion("lock_state is not null");
            return (Criteria) this;
        }

        public Criteria andLockStateEqualTo(String value) {
            addCriterion("lock_state =", value, "lockState");
            return (Criteria) this;
        }

        public Criteria andLockStateNotEqualTo(String value) {
            addCriterion("lock_state <>", value, "lockState");
            return (Criteria) this;
        }

        public Criteria andLockStateGreaterThan(String value) {
            addCriterion("lock_state >", value, "lockState");
            return (Criteria) this;
        }

        public Criteria andLockStateGreaterThanOrEqualTo(String value) {
            addCriterion("lock_state >=", value, "lockState");
            return (Criteria) this;
        }

        public Criteria andLockStateLessThan(String value) {
            addCriterion("lock_state <", value, "lockState");
            return (Criteria) this;
        }

        public Criteria andLockStateLessThanOrEqualTo(String value) {
            addCriterion("lock_state <=", value, "lockState");
            return (Criteria) this;
        }

        public Criteria andLockStateLike(String value) {
            addCriterion("lock_state like", value, "lockState");
            return (Criteria) this;
        }

        public Criteria andLockStateNotLike(String value) {
            addCriterion("lock_state not like", value, "lockState");
            return (Criteria) this;
        }

        public Criteria andLockStateIn(List<String> values) {
            addCriterion("lock_state in", values, "lockState");
            return (Criteria) this;
        }

        public Criteria andLockStateNotIn(List<String> values) {
            addCriterion("lock_state not in", values, "lockState");
            return (Criteria) this;
        }

        public Criteria andLockStateBetween(String value1, String value2) {
            addCriterion("lock_state between", value1, value2, "lockState");
            return (Criteria) this;
        }

        public Criteria andLockStateNotBetween(String value1, String value2) {
            addCriterion("lock_state not between", value1, value2, "lockState");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(String value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(String value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(String value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(String value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(String value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLike(String value) {
            addCriterion("user_id like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotLike(String value) {
            addCriterion("user_id not like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<String> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<String> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(String value1, String value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(String value1, String value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andLockCountIsNull() {
            addCriterion("lock_count is null");
            return (Criteria) this;
        }

        public Criteria andLockCountIsNotNull() {
            addCriterion("lock_count is not null");
            return (Criteria) this;
        }

        public Criteria andLockCountEqualTo(Integer value) {
            addCriterion("lock_count =", value, "lockCount");
            return (Criteria) this;
        }

        public Criteria andLockCountNotEqualTo(Integer value) {
            addCriterion("lock_count <>", value, "lockCount");
            return (Criteria) this;
        }

        public Criteria andLockCountGreaterThan(Integer value) {
            addCriterion("lock_count >", value, "lockCount");
            return (Criteria) this;
        }

        public Criteria andLockCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("lock_count >=", value, "lockCount");
            return (Criteria) this;
        }

        public Criteria andLockCountLessThan(Integer value) {
            addCriterion("lock_count <", value, "lockCount");
            return (Criteria) this;
        }

        public Criteria andLockCountLessThanOrEqualTo(Integer value) {
            addCriterion("lock_count <=", value, "lockCount");
            return (Criteria) this;
        }

        public Criteria andLockCountIn(List<Integer> values) {
            addCriterion("lock_count in", values, "lockCount");
            return (Criteria) this;
        }

        public Criteria andLockCountNotIn(List<Integer> values) {
            addCriterion("lock_count not in", values, "lockCount");
            return (Criteria) this;
        }

        public Criteria andLockCountBetween(Integer value1, Integer value2) {
            addCriterion("lock_count between", value1, value2, "lockCount");
            return (Criteria) this;
        }

        public Criteria andLockCountNotBetween(Integer value1, Integer value2) {
            addCriterion("lock_count not between", value1, value2, "lockCount");
            return (Criteria) this;
        }

        public Criteria andLockTimeIsNull() {
            addCriterion("lock_time is null");
            return (Criteria) this;
        }

        public Criteria andLockTimeIsNotNull() {
            addCriterion("lock_time is not null");
            return (Criteria) this;
        }

        public Criteria andLockTimeEqualTo(Integer value) {
            addCriterion("lock_time =", value, "lockTime");
            return (Criteria) this;
        }

        public Criteria andLockTimeNotEqualTo(Integer value) {
            addCriterion("lock_time <>", value, "lockTime");
            return (Criteria) this;
        }

        public Criteria andLockTimeGreaterThan(Integer value) {
            addCriterion("lock_time >", value, "lockTime");
            return (Criteria) this;
        }

        public Criteria andLockTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("lock_time >=", value, "lockTime");
            return (Criteria) this;
        }

        public Criteria andLockTimeLessThan(Integer value) {
            addCriterion("lock_time <", value, "lockTime");
            return (Criteria) this;
        }

        public Criteria andLockTimeLessThanOrEqualTo(Integer value) {
            addCriterion("lock_time <=", value, "lockTime");
            return (Criteria) this;
        }

        public Criteria andLockTimeIn(List<Integer> values) {
            addCriterion("lock_time in", values, "lockTime");
            return (Criteria) this;
        }

        public Criteria andLockTimeNotIn(List<Integer> values) {
            addCriterion("lock_time not in", values, "lockTime");
            return (Criteria) this;
        }

        public Criteria andLockTimeBetween(Integer value1, Integer value2) {
            addCriterion("lock_time between", value1, value2, "lockTime");
            return (Criteria) this;
        }

        public Criteria andLockTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("lock_time not between", value1, value2, "lockTime");
            return (Criteria) this;
        }

        public Criteria andRootKeyIdIsNull() {
            addCriterion("root_key_id is null");
            return (Criteria) this;
        }

        public Criteria andRootKeyIdIsNotNull() {
            addCriterion("root_key_id is not null");
            return (Criteria) this;
        }

        public Criteria andRootKeyIdEqualTo(String value) {
            addCriterion("root_key_id =", value, "rootKeyId");
            return (Criteria) this;
        }

        public Criteria andRootKeyIdNotEqualTo(String value) {
            addCriterion("root_key_id <>", value, "rootKeyId");
            return (Criteria) this;
        }

        public Criteria andRootKeyIdGreaterThan(String value) {
            addCriterion("root_key_id >", value, "rootKeyId");
            return (Criteria) this;
        }

        public Criteria andRootKeyIdGreaterThanOrEqualTo(String value) {
            addCriterion("root_key_id >=", value, "rootKeyId");
            return (Criteria) this;
        }

        public Criteria andRootKeyIdLessThan(String value) {
            addCriterion("root_key_id <", value, "rootKeyId");
            return (Criteria) this;
        }

        public Criteria andRootKeyIdLessThanOrEqualTo(String value) {
            addCriterion("root_key_id <=", value, "rootKeyId");
            return (Criteria) this;
        }

        public Criteria andRootKeyIdLike(String value) {
            addCriterion("root_key_id like", value, "rootKeyId");
            return (Criteria) this;
        }

        public Criteria andRootKeyIdNotLike(String value) {
            addCriterion("root_key_id not like", value, "rootKeyId");
            return (Criteria) this;
        }

        public Criteria andRootKeyIdIn(List<String> values) {
            addCriterion("root_key_id in", values, "rootKeyId");
            return (Criteria) this;
        }

        public Criteria andRootKeyIdNotIn(List<String> values) {
            addCriterion("root_key_id not in", values, "rootKeyId");
            return (Criteria) this;
        }

        public Criteria andRootKeyIdBetween(String value1, String value2) {
            addCriterion("root_key_id between", value1, value2, "rootKeyId");
            return (Criteria) this;
        }

        public Criteria andRootKeyIdNotBetween(String value1, String value2) {
            addCriterion("root_key_id not between", value1, value2, "rootKeyId");
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