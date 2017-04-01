package com.whty.efs.data.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EuiccCardExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EuiccCardExample() {
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

        public Criteria andEumIdIsNull() {
            addCriterion("eum_id is null");
            return (Criteria) this;
        }

        public Criteria andEumIdIsNotNull() {
            addCriterion("eum_id is not null");
            return (Criteria) this;
        }

        public Criteria andEumIdEqualTo(String value) {
            addCriterion("eum_id =", value, "eumId");
            return (Criteria) this;
        }

        public Criteria andEumIdNotEqualTo(String value) {
            addCriterion("eum_id <>", value, "eumId");
            return (Criteria) this;
        }

        public Criteria andEumIdGreaterThan(String value) {
            addCriterion("eum_id >", value, "eumId");
            return (Criteria) this;
        }

        public Criteria andEumIdGreaterThanOrEqualTo(String value) {
            addCriterion("eum_id >=", value, "eumId");
            return (Criteria) this;
        }

        public Criteria andEumIdLessThan(String value) {
            addCriterion("eum_id <", value, "eumId");
            return (Criteria) this;
        }

        public Criteria andEumIdLessThanOrEqualTo(String value) {
            addCriterion("eum_id <=", value, "eumId");
            return (Criteria) this;
        }

        public Criteria andEumIdLike(String value) {
            addCriterion("eum_id like", value, "eumId");
            return (Criteria) this;
        }

        public Criteria andEumIdNotLike(String value) {
            addCriterion("eum_id not like", value, "eumId");
            return (Criteria) this;
        }

        public Criteria andEumIdIn(List<String> values) {
            addCriterion("eum_id in", values, "eumId");
            return (Criteria) this;
        }

        public Criteria andEumIdNotIn(List<String> values) {
            addCriterion("eum_id not in", values, "eumId");
            return (Criteria) this;
        }

        public Criteria andEumIdBetween(String value1, String value2) {
            addCriterion("eum_id between", value1, value2, "eumId");
            return (Criteria) this;
        }

        public Criteria andEumIdNotBetween(String value1, String value2) {
            addCriterion("eum_id not between", value1, value2, "eumId");
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

        public Criteria andSignatureIdIsNull() {
            addCriterion("signature_id is null");
            return (Criteria) this;
        }

        public Criteria andSignatureIdIsNotNull() {
            addCriterion("signature_id is not null");
            return (Criteria) this;
        }

        public Criteria andSignatureIdEqualTo(String value) {
            addCriterion("signature_id =", value, "signatureId");
            return (Criteria) this;
        }

        public Criteria andSignatureIdNotEqualTo(String value) {
            addCriterion("signature_id <>", value, "signatureId");
            return (Criteria) this;
        }

        public Criteria andSignatureIdGreaterThan(String value) {
            addCriterion("signature_id >", value, "signatureId");
            return (Criteria) this;
        }

        public Criteria andSignatureIdGreaterThanOrEqualTo(String value) {
            addCriterion("signature_id >=", value, "signatureId");
            return (Criteria) this;
        }

        public Criteria andSignatureIdLessThan(String value) {
            addCriterion("signature_id <", value, "signatureId");
            return (Criteria) this;
        }

        public Criteria andSignatureIdLessThanOrEqualTo(String value) {
            addCriterion("signature_id <=", value, "signatureId");
            return (Criteria) this;
        }

        public Criteria andSignatureIdLike(String value) {
            addCriterion("signature_id like", value, "signatureId");
            return (Criteria) this;
        }

        public Criteria andSignatureIdNotLike(String value) {
            addCriterion("signature_id not like", value, "signatureId");
            return (Criteria) this;
        }

        public Criteria andSignatureIdIn(List<String> values) {
            addCriterion("signature_id in", values, "signatureId");
            return (Criteria) this;
        }

        public Criteria andSignatureIdNotIn(List<String> values) {
            addCriterion("signature_id not in", values, "signatureId");
            return (Criteria) this;
        }

        public Criteria andSignatureIdBetween(String value1, String value2) {
            addCriterion("signature_id between", value1, value2, "signatureId");
            return (Criteria) this;
        }

        public Criteria andSignatureIdNotBetween(String value1, String value2) {
            addCriterion("signature_id not between", value1, value2, "signatureId");
            return (Criteria) this;
        }

        public Criteria andProductionDateIsNull() {
            addCriterion("production_date is null");
            return (Criteria) this;
        }

        public Criteria andProductionDateIsNotNull() {
            addCriterion("production_date is not null");
            return (Criteria) this;
        }

        public Criteria andProductionDateEqualTo(Date value) {
            addCriterion("production_date =", value, "productionDate");
            return (Criteria) this;
        }

        public Criteria andProductionDateNotEqualTo(Date value) {
            addCriterion("production_date <>", value, "productionDate");
            return (Criteria) this;
        }

        public Criteria andProductionDateGreaterThan(Date value) {
            addCriterion("production_date >", value, "productionDate");
            return (Criteria) this;
        }

        public Criteria andProductionDateGreaterThanOrEqualTo(Date value) {
            addCriterion("production_date >=", value, "productionDate");
            return (Criteria) this;
        }

        public Criteria andProductionDateLessThan(Date value) {
            addCriterion("production_date <", value, "productionDate");
            return (Criteria) this;
        }

        public Criteria andProductionDateLessThanOrEqualTo(Date value) {
            addCriterion("production_date <=", value, "productionDate");
            return (Criteria) this;
        }

        public Criteria andProductionDateIn(List<Date> values) {
            addCriterion("production_date in", values, "productionDate");
            return (Criteria) this;
        }

        public Criteria andProductionDateNotIn(List<Date> values) {
            addCriterion("production_date not in", values, "productionDate");
            return (Criteria) this;
        }

        public Criteria andProductionDateBetween(Date value1, Date value2) {
            addCriterion("production_date between", value1, value2, "productionDate");
            return (Criteria) this;
        }

        public Criteria andProductionDateNotBetween(Date value1, Date value2) {
            addCriterion("production_date not between", value1, value2, "productionDate");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeIsNull() {
            addCriterion("platform_type is null");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeIsNotNull() {
            addCriterion("platform_type is not null");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeEqualTo(String value) {
            addCriterion("platform_type =", value, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeNotEqualTo(String value) {
            addCriterion("platform_type <>", value, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeGreaterThan(String value) {
            addCriterion("platform_type >", value, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeGreaterThanOrEqualTo(String value) {
            addCriterion("platform_type >=", value, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeLessThan(String value) {
            addCriterion("platform_type <", value, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeLessThanOrEqualTo(String value) {
            addCriterion("platform_type <=", value, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeLike(String value) {
            addCriterion("platform_type like", value, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeNotLike(String value) {
            addCriterion("platform_type not like", value, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeIn(List<String> values) {
            addCriterion("platform_type in", values, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeNotIn(List<String> values) {
            addCriterion("platform_type not in", values, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeBetween(String value1, String value2) {
            addCriterion("platform_type between", value1, value2, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeNotBetween(String value1, String value2) {
            addCriterion("platform_type not between", value1, value2, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformVersionIsNull() {
            addCriterion("platform_version is null");
            return (Criteria) this;
        }

        public Criteria andPlatformVersionIsNotNull() {
            addCriterion("platform_version is not null");
            return (Criteria) this;
        }

        public Criteria andPlatformVersionEqualTo(String value) {
            addCriterion("platform_version =", value, "platformVersion");
            return (Criteria) this;
        }

        public Criteria andPlatformVersionNotEqualTo(String value) {
            addCriterion("platform_version <>", value, "platformVersion");
            return (Criteria) this;
        }

        public Criteria andPlatformVersionGreaterThan(String value) {
            addCriterion("platform_version >", value, "platformVersion");
            return (Criteria) this;
        }

        public Criteria andPlatformVersionGreaterThanOrEqualTo(String value) {
            addCriterion("platform_version >=", value, "platformVersion");
            return (Criteria) this;
        }

        public Criteria andPlatformVersionLessThan(String value) {
            addCriterion("platform_version <", value, "platformVersion");
            return (Criteria) this;
        }

        public Criteria andPlatformVersionLessThanOrEqualTo(String value) {
            addCriterion("platform_version <=", value, "platformVersion");
            return (Criteria) this;
        }

        public Criteria andPlatformVersionLike(String value) {
            addCriterion("platform_version like", value, "platformVersion");
            return (Criteria) this;
        }

        public Criteria andPlatformVersionNotLike(String value) {
            addCriterion("platform_version not like", value, "platformVersion");
            return (Criteria) this;
        }

        public Criteria andPlatformVersionIn(List<String> values) {
            addCriterion("platform_version in", values, "platformVersion");
            return (Criteria) this;
        }

        public Criteria andPlatformVersionNotIn(List<String> values) {
            addCriterion("platform_version not in", values, "platformVersion");
            return (Criteria) this;
        }

        public Criteria andPlatformVersionBetween(String value1, String value2) {
            addCriterion("platform_version between", value1, value2, "platformVersion");
            return (Criteria) this;
        }

        public Criteria andPlatformVersionNotBetween(String value1, String value2) {
            addCriterion("platform_version not between", value1, value2, "platformVersion");
            return (Criteria) this;
        }

        public Criteria andRemainingMemoryIsNull() {
            addCriterion("remaining_memory is null");
            return (Criteria) this;
        }

        public Criteria andRemainingMemoryIsNotNull() {
            addCriterion("remaining_memory is not null");
            return (Criteria) this;
        }

        public Criteria andRemainingMemoryEqualTo(String value) {
            addCriterion("remaining_memory =", value, "remainingMemory");
            return (Criteria) this;
        }

        public Criteria andRemainingMemoryNotEqualTo(String value) {
            addCriterion("remaining_memory <>", value, "remainingMemory");
            return (Criteria) this;
        }

        public Criteria andRemainingMemoryGreaterThan(String value) {
            addCriterion("remaining_memory >", value, "remainingMemory");
            return (Criteria) this;
        }

        public Criteria andRemainingMemoryGreaterThanOrEqualTo(String value) {
            addCriterion("remaining_memory >=", value, "remainingMemory");
            return (Criteria) this;
        }

        public Criteria andRemainingMemoryLessThan(String value) {
            addCriterion("remaining_memory <", value, "remainingMemory");
            return (Criteria) this;
        }

        public Criteria andRemainingMemoryLessThanOrEqualTo(String value) {
            addCriterion("remaining_memory <=", value, "remainingMemory");
            return (Criteria) this;
        }

        public Criteria andRemainingMemoryLike(String value) {
            addCriterion("remaining_memory like", value, "remainingMemory");
            return (Criteria) this;
        }

        public Criteria andRemainingMemoryNotLike(String value) {
            addCriterion("remaining_memory not like", value, "remainingMemory");
            return (Criteria) this;
        }

        public Criteria andRemainingMemoryIn(List<String> values) {
            addCriterion("remaining_memory in", values, "remainingMemory");
            return (Criteria) this;
        }

        public Criteria andRemainingMemoryNotIn(List<String> values) {
            addCriterion("remaining_memory not in", values, "remainingMemory");
            return (Criteria) this;
        }

        public Criteria andRemainingMemoryBetween(String value1, String value2) {
            addCriterion("remaining_memory between", value1, value2, "remainingMemory");
            return (Criteria) this;
        }

        public Criteria andRemainingMemoryNotBetween(String value1, String value2) {
            addCriterion("remaining_memory not between", value1, value2, "remainingMemory");
            return (Criteria) this;
        }

        public Criteria andAvailablememoryforprofilesIsNull() {
            addCriterion("availablememoryforprofiles is null");
            return (Criteria) this;
        }

        public Criteria andAvailablememoryforprofilesIsNotNull() {
            addCriterion("availablememoryforprofiles is not null");
            return (Criteria) this;
        }

        public Criteria andAvailablememoryforprofilesEqualTo(String value) {
            addCriterion("availablememoryforprofiles =", value, "availablememoryforprofiles");
            return (Criteria) this;
        }

        public Criteria andAvailablememoryforprofilesNotEqualTo(String value) {
            addCriterion("availablememoryforprofiles <>", value, "availablememoryforprofiles");
            return (Criteria) this;
        }

        public Criteria andAvailablememoryforprofilesGreaterThan(String value) {
            addCriterion("availablememoryforprofiles >", value, "availablememoryforprofiles");
            return (Criteria) this;
        }

        public Criteria andAvailablememoryforprofilesGreaterThanOrEqualTo(String value) {
            addCriterion("availablememoryforprofiles >=", value, "availablememoryforprofiles");
            return (Criteria) this;
        }

        public Criteria andAvailablememoryforprofilesLessThan(String value) {
            addCriterion("availablememoryforprofiles <", value, "availablememoryforprofiles");
            return (Criteria) this;
        }

        public Criteria andAvailablememoryforprofilesLessThanOrEqualTo(String value) {
            addCriterion("availablememoryforprofiles <=", value, "availablememoryforprofiles");
            return (Criteria) this;
        }

        public Criteria andAvailablememoryforprofilesLike(String value) {
            addCriterion("availablememoryforprofiles like", value, "availablememoryforprofiles");
            return (Criteria) this;
        }

        public Criteria andAvailablememoryforprofilesNotLike(String value) {
            addCriterion("availablememoryforprofiles not like", value, "availablememoryforprofiles");
            return (Criteria) this;
        }

        public Criteria andAvailablememoryforprofilesIn(List<String> values) {
            addCriterion("availablememoryforprofiles in", values, "availablememoryforprofiles");
            return (Criteria) this;
        }

        public Criteria andAvailablememoryforprofilesNotIn(List<String> values) {
            addCriterion("availablememoryforprofiles not in", values, "availablememoryforprofiles");
            return (Criteria) this;
        }

        public Criteria andAvailablememoryforprofilesBetween(String value1, String value2) {
            addCriterion("availablememoryforprofiles between", value1, value2, "availablememoryforprofiles");
            return (Criteria) this;
        }

        public Criteria andAvailablememoryforprofilesNotBetween(String value1, String value2) {
            addCriterion("availablememoryforprofiles not between", value1, value2, "availablememoryforprofiles");
            return (Criteria) this;
        }

        public Criteria andCountIsNull() {
            addCriterion("count is null");
            return (Criteria) this;
        }

        public Criteria andCountIsNotNull() {
            addCriterion("count is not null");
            return (Criteria) this;
        }

        public Criteria andCountEqualTo(Integer value) {
            addCriterion("count =", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountNotEqualTo(Integer value) {
            addCriterion("count <>", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountGreaterThan(Integer value) {
            addCriterion("count >", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("count >=", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountLessThan(Integer value) {
            addCriterion("count <", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountLessThanOrEqualTo(Integer value) {
            addCriterion("count <=", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountIn(List<Integer> values) {
            addCriterion("count in", values, "count");
            return (Criteria) this;
        }

        public Criteria andCountNotIn(List<Integer> values) {
            addCriterion("count not in", values, "count");
            return (Criteria) this;
        }

        public Criteria andCountBetween(Integer value1, Integer value2) {
            addCriterion("count between", value1, value2, "count");
            return (Criteria) this;
        }

        public Criteria andCountNotBetween(Integer value1, Integer value2) {
            addCriterion("count not between", value1, value2, "count");
            return (Criteria) this;
        }

        public Criteria andPhoneNoIsNull() {
            addCriterion("phone_no is null");
            return (Criteria) this;
        }

        public Criteria andPhoneNoIsNotNull() {
            addCriterion("phone_no is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneNoEqualTo(String value) {
            addCriterion("phone_no =", value, "phoneNo");
            return (Criteria) this;
        }

        public Criteria andPhoneNoNotEqualTo(String value) {
            addCriterion("phone_no <>", value, "phoneNo");
            return (Criteria) this;
        }

        public Criteria andPhoneNoGreaterThan(String value) {
            addCriterion("phone_no >", value, "phoneNo");
            return (Criteria) this;
        }

        public Criteria andPhoneNoGreaterThanOrEqualTo(String value) {
            addCriterion("phone_no >=", value, "phoneNo");
            return (Criteria) this;
        }

        public Criteria andPhoneNoLessThan(String value) {
            addCriterion("phone_no <", value, "phoneNo");
            return (Criteria) this;
        }

        public Criteria andPhoneNoLessThanOrEqualTo(String value) {
            addCriterion("phone_no <=", value, "phoneNo");
            return (Criteria) this;
        }

        public Criteria andPhoneNoLike(String value) {
            addCriterion("phone_no like", value, "phoneNo");
            return (Criteria) this;
        }

        public Criteria andPhoneNoNotLike(String value) {
            addCriterion("phone_no not like", value, "phoneNo");
            return (Criteria) this;
        }

        public Criteria andPhoneNoIn(List<String> values) {
            addCriterion("phone_no in", values, "phoneNo");
            return (Criteria) this;
        }

        public Criteria andPhoneNoNotIn(List<String> values) {
            addCriterion("phone_no not in", values, "phoneNo");
            return (Criteria) this;
        }

        public Criteria andPhoneNoBetween(String value1, String value2) {
            addCriterion("phone_no between", value1, value2, "phoneNo");
            return (Criteria) this;
        }

        public Criteria andPhoneNoNotBetween(String value1, String value2) {
            addCriterion("phone_no not between", value1, value2, "phoneNo");
            return (Criteria) this;
        }

        public Criteria andLastAuditDateIsNull() {
            addCriterion("last_audit_date is null");
            return (Criteria) this;
        }

        public Criteria andLastAuditDateIsNotNull() {
            addCriterion("last_audit_date is not null");
            return (Criteria) this;
        }

        public Criteria andLastAuditDateEqualTo(Date value) {
            addCriterion("last_audit_date =", value, "lastAuditDate");
            return (Criteria) this;
        }

        public Criteria andLastAuditDateNotEqualTo(Date value) {
            addCriterion("last_audit_date <>", value, "lastAuditDate");
            return (Criteria) this;
        }

        public Criteria andLastAuditDateGreaterThan(Date value) {
            addCriterion("last_audit_date >", value, "lastAuditDate");
            return (Criteria) this;
        }

        public Criteria andLastAuditDateGreaterThanOrEqualTo(Date value) {
            addCriterion("last_audit_date >=", value, "lastAuditDate");
            return (Criteria) this;
        }

        public Criteria andLastAuditDateLessThan(Date value) {
            addCriterion("last_audit_date <", value, "lastAuditDate");
            return (Criteria) this;
        }

        public Criteria andLastAuditDateLessThanOrEqualTo(Date value) {
            addCriterion("last_audit_date <=", value, "lastAuditDate");
            return (Criteria) this;
        }

        public Criteria andLastAuditDateIn(List<Date> values) {
            addCriterion("last_audit_date in", values, "lastAuditDate");
            return (Criteria) this;
        }

        public Criteria andLastAuditDateNotIn(List<Date> values) {
            addCriterion("last_audit_date not in", values, "lastAuditDate");
            return (Criteria) this;
        }

        public Criteria andLastAuditDateBetween(Date value1, Date value2) {
            addCriterion("last_audit_date between", value1, value2, "lastAuditDate");
            return (Criteria) this;
        }

        public Criteria andLastAuditDateNotBetween(Date value1, Date value2) {
            addCriterion("last_audit_date not between", value1, value2, "lastAuditDate");
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