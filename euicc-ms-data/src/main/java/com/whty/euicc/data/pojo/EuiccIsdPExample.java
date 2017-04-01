package com.whty.euicc.data.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EuiccIsdPExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EuiccIsdPExample() {
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

        public Criteria andPIdIsNull() {
            addCriterion("p_id is null");
            return (Criteria) this;
        }

        public Criteria andPIdIsNotNull() {
            addCriterion("p_id is not null");
            return (Criteria) this;
        }

        public Criteria andPIdEqualTo(String value) {
            addCriterion("p_id =", value, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdNotEqualTo(String value) {
            addCriterion("p_id <>", value, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdGreaterThan(String value) {
            addCriterion("p_id >", value, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdGreaterThanOrEqualTo(String value) {
            addCriterion("p_id >=", value, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdLessThan(String value) {
            addCriterion("p_id <", value, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdLessThanOrEqualTo(String value) {
            addCriterion("p_id <=", value, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdLike(String value) {
            addCriterion("p_id like", value, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdNotLike(String value) {
            addCriterion("p_id not like", value, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdIn(List<String> values) {
            addCriterion("p_id in", values, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdNotIn(List<String> values) {
            addCriterion("p_id not in", values, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdBetween(String value1, String value2) {
            addCriterion("p_id between", value1, value2, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdNotBetween(String value1, String value2) {
            addCriterion("p_id not between", value1, value2, "pId");
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

        public Criteria andIsdPStateIsNull() {
            addCriterion("isd_p_state is null");
            return (Criteria) this;
        }

        public Criteria andIsdPStateIsNotNull() {
            addCriterion("isd_p_state is not null");
            return (Criteria) this;
        }

        public Criteria andIsdPStateEqualTo(String value) {
            addCriterion("isd_p_state =", value, "isdPState");
            return (Criteria) this;
        }

        public Criteria andIsdPStateNotEqualTo(String value) {
            addCriterion("isd_p_state <>", value, "isdPState");
            return (Criteria) this;
        }

        public Criteria andIsdPStateGreaterThan(String value) {
            addCriterion("isd_p_state >", value, "isdPState");
            return (Criteria) this;
        }

        public Criteria andIsdPStateGreaterThanOrEqualTo(String value) {
            addCriterion("isd_p_state >=", value, "isdPState");
            return (Criteria) this;
        }

        public Criteria andIsdPStateLessThan(String value) {
            addCriterion("isd_p_state <", value, "isdPState");
            return (Criteria) this;
        }

        public Criteria andIsdPStateLessThanOrEqualTo(String value) {
            addCriterion("isd_p_state <=", value, "isdPState");
            return (Criteria) this;
        }

        public Criteria andIsdPStateLike(String value) {
            addCriterion("isd_p_state like", value, "isdPState");
            return (Criteria) this;
        }

        public Criteria andIsdPStateNotLike(String value) {
            addCriterion("isd_p_state not like", value, "isdPState");
            return (Criteria) this;
        }

        public Criteria andIsdPStateIn(List<String> values) {
            addCriterion("isd_p_state in", values, "isdPState");
            return (Criteria) this;
        }

        public Criteria andIsdPStateNotIn(List<String> values) {
            addCriterion("isd_p_state not in", values, "isdPState");
            return (Criteria) this;
        }

        public Criteria andIsdPStateBetween(String value1, String value2) {
            addCriterion("isd_p_state between", value1, value2, "isdPState");
            return (Criteria) this;
        }

        public Criteria andIsdPStateNotBetween(String value1, String value2) {
            addCriterion("isd_p_state not between", value1, value2, "isdPState");
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

        public Criteria andIsdPLoadfileAidIsNull() {
            addCriterion("isd_p_loadfile_aid is null");
            return (Criteria) this;
        }

        public Criteria andIsdPLoadfileAidIsNotNull() {
            addCriterion("isd_p_loadfile_aid is not null");
            return (Criteria) this;
        }

        public Criteria andIsdPLoadfileAidEqualTo(String value) {
            addCriterion("isd_p_loadfile_aid =", value, "isdPLoadfileAid");
            return (Criteria) this;
        }

        public Criteria andIsdPLoadfileAidNotEqualTo(String value) {
            addCriterion("isd_p_loadfile_aid <>", value, "isdPLoadfileAid");
            return (Criteria) this;
        }

        public Criteria andIsdPLoadfileAidGreaterThan(String value) {
            addCriterion("isd_p_loadfile_aid >", value, "isdPLoadfileAid");
            return (Criteria) this;
        }

        public Criteria andIsdPLoadfileAidGreaterThanOrEqualTo(String value) {
            addCriterion("isd_p_loadfile_aid >=", value, "isdPLoadfileAid");
            return (Criteria) this;
        }

        public Criteria andIsdPLoadfileAidLessThan(String value) {
            addCriterion("isd_p_loadfile_aid <", value, "isdPLoadfileAid");
            return (Criteria) this;
        }

        public Criteria andIsdPLoadfileAidLessThanOrEqualTo(String value) {
            addCriterion("isd_p_loadfile_aid <=", value, "isdPLoadfileAid");
            return (Criteria) this;
        }

        public Criteria andIsdPLoadfileAidLike(String value) {
            addCriterion("isd_p_loadfile_aid like", value, "isdPLoadfileAid");
            return (Criteria) this;
        }

        public Criteria andIsdPLoadfileAidNotLike(String value) {
            addCriterion("isd_p_loadfile_aid not like", value, "isdPLoadfileAid");
            return (Criteria) this;
        }

        public Criteria andIsdPLoadfileAidIn(List<String> values) {
            addCriterion("isd_p_loadfile_aid in", values, "isdPLoadfileAid");
            return (Criteria) this;
        }

        public Criteria andIsdPLoadfileAidNotIn(List<String> values) {
            addCriterion("isd_p_loadfile_aid not in", values, "isdPLoadfileAid");
            return (Criteria) this;
        }

        public Criteria andIsdPLoadfileAidBetween(String value1, String value2) {
            addCriterion("isd_p_loadfile_aid between", value1, value2, "isdPLoadfileAid");
            return (Criteria) this;
        }

        public Criteria andIsdPLoadfileAidNotBetween(String value1, String value2) {
            addCriterion("isd_p_loadfile_aid not between", value1, value2, "isdPLoadfileAid");
            return (Criteria) this;
        }

        public Criteria andIsdPModuleAidIsNull() {
            addCriterion("isd_p_module_aid is null");
            return (Criteria) this;
        }

        public Criteria andIsdPModuleAidIsNotNull() {
            addCriterion("isd_p_module_aid is not null");
            return (Criteria) this;
        }

        public Criteria andIsdPModuleAidEqualTo(String value) {
            addCriterion("isd_p_module_aid =", value, "isdPModuleAid");
            return (Criteria) this;
        }

        public Criteria andIsdPModuleAidNotEqualTo(String value) {
            addCriterion("isd_p_module_aid <>", value, "isdPModuleAid");
            return (Criteria) this;
        }

        public Criteria andIsdPModuleAidGreaterThan(String value) {
            addCriterion("isd_p_module_aid >", value, "isdPModuleAid");
            return (Criteria) this;
        }

        public Criteria andIsdPModuleAidGreaterThanOrEqualTo(String value) {
            addCriterion("isd_p_module_aid >=", value, "isdPModuleAid");
            return (Criteria) this;
        }

        public Criteria andIsdPModuleAidLessThan(String value) {
            addCriterion("isd_p_module_aid <", value, "isdPModuleAid");
            return (Criteria) this;
        }

        public Criteria andIsdPModuleAidLessThanOrEqualTo(String value) {
            addCriterion("isd_p_module_aid <=", value, "isdPModuleAid");
            return (Criteria) this;
        }

        public Criteria andIsdPModuleAidLike(String value) {
            addCriterion("isd_p_module_aid like", value, "isdPModuleAid");
            return (Criteria) this;
        }

        public Criteria andIsdPModuleAidNotLike(String value) {
            addCriterion("isd_p_module_aid not like", value, "isdPModuleAid");
            return (Criteria) this;
        }

        public Criteria andIsdPModuleAidIn(List<String> values) {
            addCriterion("isd_p_module_aid in", values, "isdPModuleAid");
            return (Criteria) this;
        }

        public Criteria andIsdPModuleAidNotIn(List<String> values) {
            addCriterion("isd_p_module_aid not in", values, "isdPModuleAid");
            return (Criteria) this;
        }

        public Criteria andIsdPModuleAidBetween(String value1, String value2) {
            addCriterion("isd_p_module_aid between", value1, value2, "isdPModuleAid");
            return (Criteria) this;
        }

        public Criteria andIsdPModuleAidNotBetween(String value1, String value2) {
            addCriterion("isd_p_module_aid not between", value1, value2, "isdPModuleAid");
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

        public Criteria andConnectivityParamsIsNull() {
            addCriterion("connectivity_params is null");
            return (Criteria) this;
        }

        public Criteria andConnectivityParamsIsNotNull() {
            addCriterion("connectivity_params is not null");
            return (Criteria) this;
        }

        public Criteria andConnectivityParamsEqualTo(String value) {
            addCriterion("connectivity_params =", value, "connectivityParams");
            return (Criteria) this;
        }

        public Criteria andConnectivityParamsNotEqualTo(String value) {
            addCriterion("connectivity_params <>", value, "connectivityParams");
            return (Criteria) this;
        }

        public Criteria andConnectivityParamsGreaterThan(String value) {
            addCriterion("connectivity_params >", value, "connectivityParams");
            return (Criteria) this;
        }

        public Criteria andConnectivityParamsGreaterThanOrEqualTo(String value) {
            addCriterion("connectivity_params >=", value, "connectivityParams");
            return (Criteria) this;
        }

        public Criteria andConnectivityParamsLessThan(String value) {
            addCriterion("connectivity_params <", value, "connectivityParams");
            return (Criteria) this;
        }

        public Criteria andConnectivityParamsLessThanOrEqualTo(String value) {
            addCriterion("connectivity_params <=", value, "connectivityParams");
            return (Criteria) this;
        }

        public Criteria andConnectivityParamsLike(String value) {
            addCriterion("connectivity_params like", value, "connectivityParams");
            return (Criteria) this;
        }

        public Criteria andConnectivityParamsNotLike(String value) {
            addCriterion("connectivity_params not like", value, "connectivityParams");
            return (Criteria) this;
        }

        public Criteria andConnectivityParamsIn(List<String> values) {
            addCriterion("connectivity_params in", values, "connectivityParams");
            return (Criteria) this;
        }

        public Criteria andConnectivityParamsNotIn(List<String> values) {
            addCriterion("connectivity_params not in", values, "connectivityParams");
            return (Criteria) this;
        }

        public Criteria andConnectivityParamsBetween(String value1, String value2) {
            addCriterion("connectivity_params between", value1, value2, "connectivityParams");
            return (Criteria) this;
        }

        public Criteria andConnectivityParamsNotBetween(String value1, String value2) {
            addCriterion("connectivity_params not between", value1, value2, "connectivityParams");
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