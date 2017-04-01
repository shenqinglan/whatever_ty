package com.whty.euicc.data.pojo;

import java.util.ArrayList;
import java.util.List;

public class EuiccProfileExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EuiccProfileExample() {
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

        public Criteria andMnoIdIsNull() {
            addCriterion("mno_id is null");
            return (Criteria) this;
        }

        public Criteria andMnoIdIsNotNull() {
            addCriterion("mno_id is not null");
            return (Criteria) this;
        }

        public Criteria andMnoIdEqualTo(String value) {
            addCriterion("mno_id =", value, "mnoId");
            return (Criteria) this;
        }

        public Criteria andMnoIdNotEqualTo(String value) {
            addCriterion("mno_id <>", value, "mnoId");
            return (Criteria) this;
        }

        public Criteria andMnoIdGreaterThan(String value) {
            addCriterion("mno_id >", value, "mnoId");
            return (Criteria) this;
        }

        public Criteria andMnoIdGreaterThanOrEqualTo(String value) {
            addCriterion("mno_id >=", value, "mnoId");
            return (Criteria) this;
        }

        public Criteria andMnoIdLessThan(String value) {
            addCriterion("mno_id <", value, "mnoId");
            return (Criteria) this;
        }

        public Criteria andMnoIdLessThanOrEqualTo(String value) {
            addCriterion("mno_id <=", value, "mnoId");
            return (Criteria) this;
        }

        public Criteria andMnoIdLike(String value) {
            addCriterion("mno_id like", value, "mnoId");
            return (Criteria) this;
        }

        public Criteria andMnoIdNotLike(String value) {
            addCriterion("mno_id not like", value, "mnoId");
            return (Criteria) this;
        }

        public Criteria andMnoIdIn(List<String> values) {
            addCriterion("mno_id in", values, "mnoId");
            return (Criteria) this;
        }

        public Criteria andMnoIdNotIn(List<String> values) {
            addCriterion("mno_id not in", values, "mnoId");
            return (Criteria) this;
        }

        public Criteria andMnoIdBetween(String value1, String value2) {
            addCriterion("mno_id between", value1, value2, "mnoId");
            return (Criteria) this;
        }

        public Criteria andMnoIdNotBetween(String value1, String value2) {
            addCriterion("mno_id not between", value1, value2, "mnoId");
            return (Criteria) this;
        }

        public Criteria andFallbackAttributeIsNull() {
            addCriterion("fallback_attribute is null");
            return (Criteria) this;
        }

        public Criteria andFallbackAttributeIsNotNull() {
            addCriterion("fallback_attribute is not null");
            return (Criteria) this;
        }

        public Criteria andFallbackAttributeEqualTo(String value) {
            addCriterion("fallback_attribute =", value, "fallbackAttribute");
            return (Criteria) this;
        }

        public Criteria andFallbackAttributeNotEqualTo(String value) {
            addCriterion("fallback_attribute <>", value, "fallbackAttribute");
            return (Criteria) this;
        }

        public Criteria andFallbackAttributeGreaterThan(String value) {
            addCriterion("fallback_attribute >", value, "fallbackAttribute");
            return (Criteria) this;
        }

        public Criteria andFallbackAttributeGreaterThanOrEqualTo(String value) {
            addCriterion("fallback_attribute >=", value, "fallbackAttribute");
            return (Criteria) this;
        }

        public Criteria andFallbackAttributeLessThan(String value) {
            addCriterion("fallback_attribute <", value, "fallbackAttribute");
            return (Criteria) this;
        }

        public Criteria andFallbackAttributeLessThanOrEqualTo(String value) {
            addCriterion("fallback_attribute <=", value, "fallbackAttribute");
            return (Criteria) this;
        }

        public Criteria andFallbackAttributeLike(String value) {
            addCriterion("fallback_attribute like", value, "fallbackAttribute");
            return (Criteria) this;
        }

        public Criteria andFallbackAttributeNotLike(String value) {
            addCriterion("fallback_attribute not like", value, "fallbackAttribute");
            return (Criteria) this;
        }

        public Criteria andFallbackAttributeIn(List<String> values) {
            addCriterion("fallback_attribute in", values, "fallbackAttribute");
            return (Criteria) this;
        }

        public Criteria andFallbackAttributeNotIn(List<String> values) {
            addCriterion("fallback_attribute not in", values, "fallbackAttribute");
            return (Criteria) this;
        }

        public Criteria andFallbackAttributeBetween(String value1, String value2) {
            addCriterion("fallback_attribute between", value1, value2, "fallbackAttribute");
            return (Criteria) this;
        }

        public Criteria andFallbackAttributeNotBetween(String value1, String value2) {
            addCriterion("fallback_attribute not between", value1, value2, "fallbackAttribute");
            return (Criteria) this;
        }

        public Criteria andSubscritionAddressIsNull() {
            addCriterion("subscrition_address is null");
            return (Criteria) this;
        }

        public Criteria andSubscritionAddressIsNotNull() {
            addCriterion("subscrition_address is not null");
            return (Criteria) this;
        }

        public Criteria andSubscritionAddressEqualTo(String value) {
            addCriterion("subscrition_address =", value, "subscritionAddress");
            return (Criteria) this;
        }

        public Criteria andSubscritionAddressNotEqualTo(String value) {
            addCriterion("subscrition_address <>", value, "subscritionAddress");
            return (Criteria) this;
        }

        public Criteria andSubscritionAddressGreaterThan(String value) {
            addCriterion("subscrition_address >", value, "subscritionAddress");
            return (Criteria) this;
        }

        public Criteria andSubscritionAddressGreaterThanOrEqualTo(String value) {
            addCriterion("subscrition_address >=", value, "subscritionAddress");
            return (Criteria) this;
        }

        public Criteria andSubscritionAddressLessThan(String value) {
            addCriterion("subscrition_address <", value, "subscritionAddress");
            return (Criteria) this;
        }

        public Criteria andSubscritionAddressLessThanOrEqualTo(String value) {
            addCriterion("subscrition_address <=", value, "subscritionAddress");
            return (Criteria) this;
        }

        public Criteria andSubscritionAddressLike(String value) {
            addCriterion("subscrition_address like", value, "subscritionAddress");
            return (Criteria) this;
        }

        public Criteria andSubscritionAddressNotLike(String value) {
            addCriterion("subscrition_address not like", value, "subscritionAddress");
            return (Criteria) this;
        }

        public Criteria andSubscritionAddressIn(List<String> values) {
            addCriterion("subscrition_address in", values, "subscritionAddress");
            return (Criteria) this;
        }

        public Criteria andSubscritionAddressNotIn(List<String> values) {
            addCriterion("subscrition_address not in", values, "subscritionAddress");
            return (Criteria) this;
        }

        public Criteria andSubscritionAddressBetween(String value1, String value2) {
            addCriterion("subscrition_address between", value1, value2, "subscritionAddress");
            return (Criteria) this;
        }

        public Criteria andSubscritionAddressNotBetween(String value1, String value2) {
            addCriterion("subscrition_address not between", value1, value2, "subscritionAddress");
            return (Criteria) this;
        }

        public Criteria andStateIsNull() {
            addCriterion("state is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("state is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(String value) {
            addCriterion("state =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(String value) {
            addCriterion("state <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(String value) {
            addCriterion("state >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(String value) {
            addCriterion("state >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(String value) {
            addCriterion("state <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(String value) {
            addCriterion("state <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLike(String value) {
            addCriterion("state like", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotLike(String value) {
            addCriterion("state not like", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<String> values) {
            addCriterion("state in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<String> values) {
            addCriterion("state not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(String value1, String value2) {
            addCriterion("state between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(String value1, String value2) {
            addCriterion("state not between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andSmdpIdIsNull() {
            addCriterion("smdp_id is null");
            return (Criteria) this;
        }

        public Criteria andSmdpIdIsNotNull() {
            addCriterion("smdp_id is not null");
            return (Criteria) this;
        }

        public Criteria andSmdpIdEqualTo(String value) {
            addCriterion("smdp_id =", value, "smdpId");
            return (Criteria) this;
        }

        public Criteria andSmdpIdNotEqualTo(String value) {
            addCriterion("smdp_id <>", value, "smdpId");
            return (Criteria) this;
        }

        public Criteria andSmdpIdGreaterThan(String value) {
            addCriterion("smdp_id >", value, "smdpId");
            return (Criteria) this;
        }

        public Criteria andSmdpIdGreaterThanOrEqualTo(String value) {
            addCriterion("smdp_id >=", value, "smdpId");
            return (Criteria) this;
        }

        public Criteria andSmdpIdLessThan(String value) {
            addCriterion("smdp_id <", value, "smdpId");
            return (Criteria) this;
        }

        public Criteria andSmdpIdLessThanOrEqualTo(String value) {
            addCriterion("smdp_id <=", value, "smdpId");
            return (Criteria) this;
        }

        public Criteria andSmdpIdLike(String value) {
            addCriterion("smdp_id like", value, "smdpId");
            return (Criteria) this;
        }

        public Criteria andSmdpIdNotLike(String value) {
            addCriterion("smdp_id not like", value, "smdpId");
            return (Criteria) this;
        }

        public Criteria andSmdpIdIn(List<String> values) {
            addCriterion("smdp_id in", values, "smdpId");
            return (Criteria) this;
        }

        public Criteria andSmdpIdNotIn(List<String> values) {
            addCriterion("smdp_id not in", values, "smdpId");
            return (Criteria) this;
        }

        public Criteria andSmdpIdBetween(String value1, String value2) {
            addCriterion("smdp_id between", value1, value2, "smdpId");
            return (Criteria) this;
        }

        public Criteria andSmdpIdNotBetween(String value1, String value2) {
            addCriterion("smdp_id not between", value1, value2, "smdpId");
            return (Criteria) this;
        }

        public Criteria andProfileTypeIsNull() {
            addCriterion("profile_type is null");
            return (Criteria) this;
        }

        public Criteria andProfileTypeIsNotNull() {
            addCriterion("profile_type is not null");
            return (Criteria) this;
        }

        public Criteria andProfileTypeEqualTo(String value) {
            addCriterion("profile_type =", value, "profileType");
            return (Criteria) this;
        }

        public Criteria andProfileTypeNotEqualTo(String value) {
            addCriterion("profile_type <>", value, "profileType");
            return (Criteria) this;
        }

        public Criteria andProfileTypeGreaterThan(String value) {
            addCriterion("profile_type >", value, "profileType");
            return (Criteria) this;
        }

        public Criteria andProfileTypeGreaterThanOrEqualTo(String value) {
            addCriterion("profile_type >=", value, "profileType");
            return (Criteria) this;
        }

        public Criteria andProfileTypeLessThan(String value) {
            addCriterion("profile_type <", value, "profileType");
            return (Criteria) this;
        }

        public Criteria andProfileTypeLessThanOrEqualTo(String value) {
            addCriterion("profile_type <=", value, "profileType");
            return (Criteria) this;
        }

        public Criteria andProfileTypeLike(String value) {
            addCriterion("profile_type like", value, "profileType");
            return (Criteria) this;
        }

        public Criteria andProfileTypeNotLike(String value) {
            addCriterion("profile_type not like", value, "profileType");
            return (Criteria) this;
        }

        public Criteria andProfileTypeIn(List<String> values) {
            addCriterion("profile_type in", values, "profileType");
            return (Criteria) this;
        }

        public Criteria andProfileTypeNotIn(List<String> values) {
            addCriterion("profile_type not in", values, "profileType");
            return (Criteria) this;
        }

        public Criteria andProfileTypeBetween(String value1, String value2) {
            addCriterion("profile_type between", value1, value2, "profileType");
            return (Criteria) this;
        }

        public Criteria andProfileTypeNotBetween(String value1, String value2) {
            addCriterion("profile_type not between", value1, value2, "profileType");
            return (Criteria) this;
        }

        public Criteria andAllocatedMemoryIsNull() {
            addCriterion("allocated_memory is null");
            return (Criteria) this;
        }

        public Criteria andAllocatedMemoryIsNotNull() {
            addCriterion("allocated_memory is not null");
            return (Criteria) this;
        }

        public Criteria andAllocatedMemoryEqualTo(String value) {
            addCriterion("allocated_memory =", value, "allocatedMemory");
            return (Criteria) this;
        }

        public Criteria andAllocatedMemoryNotEqualTo(String value) {
            addCriterion("allocated_memory <>", value, "allocatedMemory");
            return (Criteria) this;
        }

        public Criteria andAllocatedMemoryGreaterThan(String value) {
            addCriterion("allocated_memory >", value, "allocatedMemory");
            return (Criteria) this;
        }

        public Criteria andAllocatedMemoryGreaterThanOrEqualTo(String value) {
            addCriterion("allocated_memory >=", value, "allocatedMemory");
            return (Criteria) this;
        }

        public Criteria andAllocatedMemoryLessThan(String value) {
            addCriterion("allocated_memory <", value, "allocatedMemory");
            return (Criteria) this;
        }

        public Criteria andAllocatedMemoryLessThanOrEqualTo(String value) {
            addCriterion("allocated_memory <=", value, "allocatedMemory");
            return (Criteria) this;
        }

        public Criteria andAllocatedMemoryLike(String value) {
            addCriterion("allocated_memory like", value, "allocatedMemory");
            return (Criteria) this;
        }

        public Criteria andAllocatedMemoryNotLike(String value) {
            addCriterion("allocated_memory not like", value, "allocatedMemory");
            return (Criteria) this;
        }

        public Criteria andAllocatedMemoryIn(List<String> values) {
            addCriterion("allocated_memory in", values, "allocatedMemory");
            return (Criteria) this;
        }

        public Criteria andAllocatedMemoryNotIn(List<String> values) {
            addCriterion("allocated_memory not in", values, "allocatedMemory");
            return (Criteria) this;
        }

        public Criteria andAllocatedMemoryBetween(String value1, String value2) {
            addCriterion("allocated_memory between", value1, value2, "allocatedMemory");
            return (Criteria) this;
        }

        public Criteria andAllocatedMemoryNotBetween(String value1, String value2) {
            addCriterion("allocated_memory not between", value1, value2, "allocatedMemory");
            return (Criteria) this;
        }

        public Criteria andFreeMemoryIsNull() {
            addCriterion("free_memory is null");
            return (Criteria) this;
        }

        public Criteria andFreeMemoryIsNotNull() {
            addCriterion("free_memory is not null");
            return (Criteria) this;
        }

        public Criteria andFreeMemoryEqualTo(String value) {
            addCriterion("free_memory =", value, "freeMemory");
            return (Criteria) this;
        }

        public Criteria andFreeMemoryNotEqualTo(String value) {
            addCriterion("free_memory <>", value, "freeMemory");
            return (Criteria) this;
        }

        public Criteria andFreeMemoryGreaterThan(String value) {
            addCriterion("free_memory >", value, "freeMemory");
            return (Criteria) this;
        }

        public Criteria andFreeMemoryGreaterThanOrEqualTo(String value) {
            addCriterion("free_memory >=", value, "freeMemory");
            return (Criteria) this;
        }

        public Criteria andFreeMemoryLessThan(String value) {
            addCriterion("free_memory <", value, "freeMemory");
            return (Criteria) this;
        }

        public Criteria andFreeMemoryLessThanOrEqualTo(String value) {
            addCriterion("free_memory <=", value, "freeMemory");
            return (Criteria) this;
        }

        public Criteria andFreeMemoryLike(String value) {
            addCriterion("free_memory like", value, "freeMemory");
            return (Criteria) this;
        }

        public Criteria andFreeMemoryNotLike(String value) {
            addCriterion("free_memory not like", value, "freeMemory");
            return (Criteria) this;
        }

        public Criteria andFreeMemoryIn(List<String> values) {
            addCriterion("free_memory in", values, "freeMemory");
            return (Criteria) this;
        }

        public Criteria andFreeMemoryNotIn(List<String> values) {
            addCriterion("free_memory not in", values, "freeMemory");
            return (Criteria) this;
        }

        public Criteria andFreeMemoryBetween(String value1, String value2) {
            addCriterion("free_memory between", value1, value2, "freeMemory");
            return (Criteria) this;
        }

        public Criteria andFreeMemoryNotBetween(String value1, String value2) {
            addCriterion("free_memory not between", value1, value2, "freeMemory");
            return (Criteria) this;
        }

        public Criteria andPol2IdIsNull() {
            addCriterion("pol2_id is null");
            return (Criteria) this;
        }

        public Criteria andPol2IdIsNotNull() {
            addCriterion("pol2_id is not null");
            return (Criteria) this;
        }

        public Criteria andPol2IdEqualTo(String value) {
            addCriterion("pol2_id =", value, "pol2Id");
            return (Criteria) this;
        }

        public Criteria andPol2IdNotEqualTo(String value) {
            addCriterion("pol2_id <>", value, "pol2Id");
            return (Criteria) this;
        }

        public Criteria andPol2IdGreaterThan(String value) {
            addCriterion("pol2_id >", value, "pol2Id");
            return (Criteria) this;
        }

        public Criteria andPol2IdGreaterThanOrEqualTo(String value) {
            addCriterion("pol2_id >=", value, "pol2Id");
            return (Criteria) this;
        }

        public Criteria andPol2IdLessThan(String value) {
            addCriterion("pol2_id <", value, "pol2Id");
            return (Criteria) this;
        }

        public Criteria andPol2IdLessThanOrEqualTo(String value) {
            addCriterion("pol2_id <=", value, "pol2Id");
            return (Criteria) this;
        }

        public Criteria andPol2IdLike(String value) {
            addCriterion("pol2_id like", value, "pol2Id");
            return (Criteria) this;
        }

        public Criteria andPol2IdNotLike(String value) {
            addCriterion("pol2_id not like", value, "pol2Id");
            return (Criteria) this;
        }

        public Criteria andPol2IdIn(List<String> values) {
            addCriterion("pol2_id in", values, "pol2Id");
            return (Criteria) this;
        }

        public Criteria andPol2IdNotIn(List<String> values) {
            addCriterion("pol2_id not in", values, "pol2Id");
            return (Criteria) this;
        }

        public Criteria andPol2IdBetween(String value1, String value2) {
            addCriterion("pol2_id between", value1, value2, "pol2Id");
            return (Criteria) this;
        }

        public Criteria andPol2IdNotBetween(String value1, String value2) {
            addCriterion("pol2_id not between", value1, value2, "pol2Id");
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