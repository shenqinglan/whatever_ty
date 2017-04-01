package com.whty.euicc.base.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ApInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ApInfoExample() {
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

        public Criteria andUserIdIsNull() {
            addCriterion("USER_ID is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("USER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(String value) {
            addCriterion("USER_ID =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(String value) {
            addCriterion("USER_ID <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(String value) {
            addCriterion("USER_ID >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("USER_ID >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(String value) {
            addCriterion("USER_ID <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(String value) {
            addCriterion("USER_ID <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLike(String value) {
            addCriterion("USER_ID like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotLike(String value) {
            addCriterion("USER_ID not like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<String> values) {
            addCriterion("USER_ID in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<String> values) {
            addCriterion("USER_ID not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(String value1, String value2) {
            addCriterion("USER_ID between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(String value1, String value2) {
            addCriterion("USER_ID not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andSpNameIsNull() {
            addCriterion("SP_NAME is null");
            return (Criteria) this;
        }

        public Criteria andSpNameIsNotNull() {
            addCriterion("SP_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andSpNameEqualTo(String value) {
            addCriterion("SP_NAME =", value, "spName");
            return (Criteria) this;
        }

        public Criteria andSpNameNotEqualTo(String value) {
            addCriterion("SP_NAME <>", value, "spName");
            return (Criteria) this;
        }

        public Criteria andSpNameGreaterThan(String value) {
            addCriterion("SP_NAME >", value, "spName");
            return (Criteria) this;
        }

        public Criteria andSpNameGreaterThanOrEqualTo(String value) {
            addCriterion("SP_NAME >=", value, "spName");
            return (Criteria) this;
        }

        public Criteria andSpNameLessThan(String value) {
            addCriterion("SP_NAME <", value, "spName");
            return (Criteria) this;
        }

        public Criteria andSpNameLessThanOrEqualTo(String value) {
            addCriterion("SP_NAME <=", value, "spName");
            return (Criteria) this;
        }

        public Criteria andSpNameLike(String value) {
            addCriterion("SP_NAME like", value, "spName");
            return (Criteria) this;
        }

        public Criteria andSpNameNotLike(String value) {
            addCriterion("SP_NAME not like", value, "spName");
            return (Criteria) this;
        }

        public Criteria andSpNameIn(List<String> values) {
            addCriterion("SP_NAME in", values, "spName");
            return (Criteria) this;
        }

        public Criteria andSpNameNotIn(List<String> values) {
            addCriterion("SP_NAME not in", values, "spName");
            return (Criteria) this;
        }

        public Criteria andSpNameBetween(String value1, String value2) {
            addCriterion("SP_NAME between", value1, value2, "spName");
            return (Criteria) this;
        }

        public Criteria andSpNameNotBetween(String value1, String value2) {
            addCriterion("SP_NAME not between", value1, value2, "spName");
            return (Criteria) this;
        }

        public Criteria andSpCodeIsNull() {
            addCriterion("SP_CODE is null");
            return (Criteria) this;
        }

        public Criteria andSpCodeIsNotNull() {
            addCriterion("SP_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andSpCodeEqualTo(String value) {
            addCriterion("SP_CODE =", value, "spCode");
            return (Criteria) this;
        }

        public Criteria andSpCodeNotEqualTo(String value) {
            addCriterion("SP_CODE <>", value, "spCode");
            return (Criteria) this;
        }

        public Criteria andSpCodeGreaterThan(String value) {
            addCriterion("SP_CODE >", value, "spCode");
            return (Criteria) this;
        }

        public Criteria andSpCodeGreaterThanOrEqualTo(String value) {
            addCriterion("SP_CODE >=", value, "spCode");
            return (Criteria) this;
        }

        public Criteria andSpCodeLessThan(String value) {
            addCriterion("SP_CODE <", value, "spCode");
            return (Criteria) this;
        }

        public Criteria andSpCodeLessThanOrEqualTo(String value) {
            addCriterion("SP_CODE <=", value, "spCode");
            return (Criteria) this;
        }

        public Criteria andSpCodeLike(String value) {
            addCriterion("SP_CODE like", value, "spCode");
            return (Criteria) this;
        }

        public Criteria andSpCodeNotLike(String value) {
            addCriterion("SP_CODE not like", value, "spCode");
            return (Criteria) this;
        }

        public Criteria andSpCodeIn(List<String> values) {
            addCriterion("SP_CODE in", values, "spCode");
            return (Criteria) this;
        }

        public Criteria andSpCodeNotIn(List<String> values) {
            addCriterion("SP_CODE not in", values, "spCode");
            return (Criteria) this;
        }

        public Criteria andSpCodeBetween(String value1, String value2) {
            addCriterion("SP_CODE between", value1, value2, "spCode");
            return (Criteria) this;
        }

        public Criteria andSpCodeNotBetween(String value1, String value2) {
            addCriterion("SP_CODE not between", value1, value2, "spCode");
            return (Criteria) this;
        }

        public Criteria andLinkerIsNull() {
            addCriterion("LINKER is null");
            return (Criteria) this;
        }

        public Criteria andLinkerIsNotNull() {
            addCriterion("LINKER is not null");
            return (Criteria) this;
        }

        public Criteria andLinkerEqualTo(String value) {
            addCriterion("LINKER =", value, "linker");
            return (Criteria) this;
        }

        public Criteria andLinkerNotEqualTo(String value) {
            addCriterion("LINKER <>", value, "linker");
            return (Criteria) this;
        }

        public Criteria andLinkerGreaterThan(String value) {
            addCriterion("LINKER >", value, "linker");
            return (Criteria) this;
        }

        public Criteria andLinkerGreaterThanOrEqualTo(String value) {
            addCriterion("LINKER >=", value, "linker");
            return (Criteria) this;
        }

        public Criteria andLinkerLessThan(String value) {
            addCriterion("LINKER <", value, "linker");
            return (Criteria) this;
        }

        public Criteria andLinkerLessThanOrEqualTo(String value) {
            addCriterion("LINKER <=", value, "linker");
            return (Criteria) this;
        }

        public Criteria andLinkerLike(String value) {
            addCriterion("LINKER like", value, "linker");
            return (Criteria) this;
        }

        public Criteria andLinkerNotLike(String value) {
            addCriterion("LINKER not like", value, "linker");
            return (Criteria) this;
        }

        public Criteria andLinkerIn(List<String> values) {
            addCriterion("LINKER in", values, "linker");
            return (Criteria) this;
        }

        public Criteria andLinkerNotIn(List<String> values) {
            addCriterion("LINKER not in", values, "linker");
            return (Criteria) this;
        }

        public Criteria andLinkerBetween(String value1, String value2) {
            addCriterion("LINKER between", value1, value2, "linker");
            return (Criteria) this;
        }

        public Criteria andLinkerNotBetween(String value1, String value2) {
            addCriterion("LINKER not between", value1, value2, "linker");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNull() {
            addCriterion("PHONE is null");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNotNull() {
            addCriterion("PHONE is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneEqualTo(String value) {
            addCriterion("PHONE =", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotEqualTo(String value) {
            addCriterion("PHONE <>", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThan(String value) {
            addCriterion("PHONE >", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("PHONE >=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThan(String value) {
            addCriterion("PHONE <", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThanOrEqualTo(String value) {
            addCriterion("PHONE <=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLike(String value) {
            addCriterion("PHONE like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotLike(String value) {
            addCriterion("PHONE not like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneIn(List<String> values) {
            addCriterion("PHONE in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotIn(List<String> values) {
            addCriterion("PHONE not in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneBetween(String value1, String value2) {
            addCriterion("PHONE between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotBetween(String value1, String value2) {
            addCriterion("PHONE not between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andAddressIsNull() {
            addCriterion("ADDRESS is null");
            return (Criteria) this;
        }

        public Criteria andAddressIsNotNull() {
            addCriterion("ADDRESS is not null");
            return (Criteria) this;
        }

        public Criteria andAddressEqualTo(String value) {
            addCriterion("ADDRESS =", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotEqualTo(String value) {
            addCriterion("ADDRESS <>", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThan(String value) {
            addCriterion("ADDRESS >", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanOrEqualTo(String value) {
            addCriterion("ADDRESS >=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThan(String value) {
            addCriterion("ADDRESS <", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThanOrEqualTo(String value) {
            addCriterion("ADDRESS <=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLike(String value) {
            addCriterion("ADDRESS like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotLike(String value) {
            addCriterion("ADDRESS not like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressIn(List<String> values) {
            addCriterion("ADDRESS in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotIn(List<String> values) {
            addCriterion("ADDRESS not in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressBetween(String value1, String value2) {
            addCriterion("ADDRESS between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotBetween(String value1, String value2) {
            addCriterion("ADDRESS not between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andZipcodeIsNull() {
            addCriterion("ZIPCODE is null");
            return (Criteria) this;
        }

        public Criteria andZipcodeIsNotNull() {
            addCriterion("ZIPCODE is not null");
            return (Criteria) this;
        }

        public Criteria andZipcodeEqualTo(String value) {
            addCriterion("ZIPCODE =", value, "zipcode");
            return (Criteria) this;
        }

        public Criteria andZipcodeNotEqualTo(String value) {
            addCriterion("ZIPCODE <>", value, "zipcode");
            return (Criteria) this;
        }

        public Criteria andZipcodeGreaterThan(String value) {
            addCriterion("ZIPCODE >", value, "zipcode");
            return (Criteria) this;
        }

        public Criteria andZipcodeGreaterThanOrEqualTo(String value) {
            addCriterion("ZIPCODE >=", value, "zipcode");
            return (Criteria) this;
        }

        public Criteria andZipcodeLessThan(String value) {
            addCriterion("ZIPCODE <", value, "zipcode");
            return (Criteria) this;
        }

        public Criteria andZipcodeLessThanOrEqualTo(String value) {
            addCriterion("ZIPCODE <=", value, "zipcode");
            return (Criteria) this;
        }

        public Criteria andZipcodeLike(String value) {
            addCriterion("ZIPCODE like", value, "zipcode");
            return (Criteria) this;
        }

        public Criteria andZipcodeNotLike(String value) {
            addCriterion("ZIPCODE not like", value, "zipcode");
            return (Criteria) this;
        }

        public Criteria andZipcodeIn(List<String> values) {
            addCriterion("ZIPCODE in", values, "zipcode");
            return (Criteria) this;
        }

        public Criteria andZipcodeNotIn(List<String> values) {
            addCriterion("ZIPCODE not in", values, "zipcode");
            return (Criteria) this;
        }

        public Criteria andZipcodeBetween(String value1, String value2) {
            addCriterion("ZIPCODE between", value1, value2, "zipcode");
            return (Criteria) this;
        }

        public Criteria andZipcodeNotBetween(String value1, String value2) {
            addCriterion("ZIPCODE not between", value1, value2, "zipcode");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("STATUS is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("STATUS =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("STATUS <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("STATUS >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("STATUS >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("STATUS <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("STATUS <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("STATUS like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("STATUS not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("STATUS in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("STATUS not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("STATUS between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("STATUS not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andRegCodeIsNull() {
            addCriterion("REG_CODE is null");
            return (Criteria) this;
        }

        public Criteria andRegCodeIsNotNull() {
            addCriterion("REG_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andRegCodeEqualTo(String value) {
            addCriterion("REG_CODE =", value, "regCode");
            return (Criteria) this;
        }

        public Criteria andRegCodeNotEqualTo(String value) {
            addCriterion("REG_CODE <>", value, "regCode");
            return (Criteria) this;
        }

        public Criteria andRegCodeGreaterThan(String value) {
            addCriterion("REG_CODE >", value, "regCode");
            return (Criteria) this;
        }

        public Criteria andRegCodeGreaterThanOrEqualTo(String value) {
            addCriterion("REG_CODE >=", value, "regCode");
            return (Criteria) this;
        }

        public Criteria andRegCodeLessThan(String value) {
            addCriterion("REG_CODE <", value, "regCode");
            return (Criteria) this;
        }

        public Criteria andRegCodeLessThanOrEqualTo(String value) {
            addCriterion("REG_CODE <=", value, "regCode");
            return (Criteria) this;
        }

        public Criteria andRegCodeLike(String value) {
            addCriterion("REG_CODE like", value, "regCode");
            return (Criteria) this;
        }

        public Criteria andRegCodeNotLike(String value) {
            addCriterion("REG_CODE not like", value, "regCode");
            return (Criteria) this;
        }

        public Criteria andRegCodeIn(List<String> values) {
            addCriterion("REG_CODE in", values, "regCode");
            return (Criteria) this;
        }

        public Criteria andRegCodeNotIn(List<String> values) {
            addCriterion("REG_CODE not in", values, "regCode");
            return (Criteria) this;
        }

        public Criteria andRegCodeBetween(String value1, String value2) {
            addCriterion("REG_CODE between", value1, value2, "regCode");
            return (Criteria) this;
        }

        public Criteria andRegCodeNotBetween(String value1, String value2) {
            addCriterion("REG_CODE not between", value1, value2, "regCode");
            return (Criteria) this;
        }

        public Criteria andRegCodeDateIsNull() {
            addCriterion("REG_CODE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andRegCodeDateIsNotNull() {
            addCriterion("REG_CODE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andRegCodeDateEqualTo(Date value) {
            addCriterion("REG_CODE_DATE =", value, "regCodeDate");
            return (Criteria) this;
        }

        public Criteria andRegCodeDateNotEqualTo(Date value) {
            addCriterion("REG_CODE_DATE <>", value, "regCodeDate");
            return (Criteria) this;
        }

        public Criteria andRegCodeDateGreaterThan(Date value) {
            addCriterion("REG_CODE_DATE >", value, "regCodeDate");
            return (Criteria) this;
        }

        public Criteria andRegCodeDateGreaterThanOrEqualTo(Date value) {
            addCriterion("REG_CODE_DATE >=", value, "regCodeDate");
            return (Criteria) this;
        }

        public Criteria andRegCodeDateLessThan(Date value) {
            addCriterion("REG_CODE_DATE <", value, "regCodeDate");
            return (Criteria) this;
        }

        public Criteria andRegCodeDateLessThanOrEqualTo(Date value) {
            addCriterion("REG_CODE_DATE <=", value, "regCodeDate");
            return (Criteria) this;
        }

        public Criteria andRegCodeDateIn(List<Date> values) {
            addCriterion("REG_CODE_DATE in", values, "regCodeDate");
            return (Criteria) this;
        }

        public Criteria andRegCodeDateNotIn(List<Date> values) {
            addCriterion("REG_CODE_DATE not in", values, "regCodeDate");
            return (Criteria) this;
        }

        public Criteria andRegCodeDateBetween(Date value1, Date value2) {
            addCriterion("REG_CODE_DATE between", value1, value2, "regCodeDate");
            return (Criteria) this;
        }

        public Criteria andRegCodeDateNotBetween(Date value1, Date value2) {
            addCriterion("REG_CODE_DATE not between", value1, value2, "regCodeDate");
            return (Criteria) this;
        }

        public Criteria andFilePathIsNull() {
            addCriterion("FILE_PATH is null");
            return (Criteria) this;
        }

        public Criteria andFilePathIsNotNull() {
            addCriterion("FILE_PATH is not null");
            return (Criteria) this;
        }

        public Criteria andFilePathEqualTo(String value) {
            addCriterion("FILE_PATH =", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotEqualTo(String value) {
            addCriterion("FILE_PATH <>", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathGreaterThan(String value) {
            addCriterion("FILE_PATH >", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathGreaterThanOrEqualTo(String value) {
            addCriterion("FILE_PATH >=", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathLessThan(String value) {
            addCriterion("FILE_PATH <", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathLessThanOrEqualTo(String value) {
            addCriterion("FILE_PATH <=", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathLike(String value) {
            addCriterion("FILE_PATH like", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotLike(String value) {
            addCriterion("FILE_PATH not like", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathIn(List<String> values) {
            addCriterion("FILE_PATH in", values, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotIn(List<String> values) {
            addCriterion("FILE_PATH not in", values, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathBetween(String value1, String value2) {
            addCriterion("FILE_PATH between", value1, value2, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotBetween(String value1, String value2) {
            addCriterion("FILE_PATH not between", value1, value2, "filePath");
            return (Criteria) this;
        }

        public Criteria andCenterAddrIsNull() {
            addCriterion("CENTER_ADDR is null");
            return (Criteria) this;
        }

        public Criteria andCenterAddrIsNotNull() {
            addCriterion("CENTER_ADDR is not null");
            return (Criteria) this;
        }

        public Criteria andCenterAddrEqualTo(String value) {
            addCriterion("CENTER_ADDR =", value, "centerAddr");
            return (Criteria) this;
        }

        public Criteria andCenterAddrNotEqualTo(String value) {
            addCriterion("CENTER_ADDR <>", value, "centerAddr");
            return (Criteria) this;
        }

        public Criteria andCenterAddrGreaterThan(String value) {
            addCriterion("CENTER_ADDR >", value, "centerAddr");
            return (Criteria) this;
        }

        public Criteria andCenterAddrGreaterThanOrEqualTo(String value) {
            addCriterion("CENTER_ADDR >=", value, "centerAddr");
            return (Criteria) this;
        }

        public Criteria andCenterAddrLessThan(String value) {
            addCriterion("CENTER_ADDR <", value, "centerAddr");
            return (Criteria) this;
        }

        public Criteria andCenterAddrLessThanOrEqualTo(String value) {
            addCriterion("CENTER_ADDR <=", value, "centerAddr");
            return (Criteria) this;
        }

        public Criteria andCenterAddrLike(String value) {
            addCriterion("CENTER_ADDR like", value, "centerAddr");
            return (Criteria) this;
        }

        public Criteria andCenterAddrNotLike(String value) {
            addCriterion("CENTER_ADDR not like", value, "centerAddr");
            return (Criteria) this;
        }

        public Criteria andCenterAddrIn(List<String> values) {
            addCriterion("CENTER_ADDR in", values, "centerAddr");
            return (Criteria) this;
        }

        public Criteria andCenterAddrNotIn(List<String> values) {
            addCriterion("CENTER_ADDR not in", values, "centerAddr");
            return (Criteria) this;
        }

        public Criteria andCenterAddrBetween(String value1, String value2) {
            addCriterion("CENTER_ADDR between", value1, value2, "centerAddr");
            return (Criteria) this;
        }

        public Criteria andCenterAddrNotBetween(String value1, String value2) {
            addCriterion("CENTER_ADDR not between", value1, value2, "centerAddr");
            return (Criteria) this;
        }

        public Criteria andFileNameIsNull() {
            addCriterion("FILE_NAME is null");
            return (Criteria) this;
        }

        public Criteria andFileNameIsNotNull() {
            addCriterion("FILE_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andFileNameEqualTo(String value) {
            addCriterion("FILE_NAME =", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotEqualTo(String value) {
            addCriterion("FILE_NAME <>", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameGreaterThan(String value) {
            addCriterion("FILE_NAME >", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameGreaterThanOrEqualTo(String value) {
            addCriterion("FILE_NAME >=", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameLessThan(String value) {
            addCriterion("FILE_NAME <", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameLessThanOrEqualTo(String value) {
            addCriterion("FILE_NAME <=", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameLike(String value) {
            addCriterion("FILE_NAME like", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotLike(String value) {
            addCriterion("FILE_NAME not like", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameIn(List<String> values) {
            addCriterion("FILE_NAME in", values, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotIn(List<String> values) {
            addCriterion("FILE_NAME not in", values, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameBetween(String value1, String value2) {
            addCriterion("FILE_NAME between", value1, value2, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotBetween(String value1, String value2) {
            addCriterion("FILE_NAME not between", value1, value2, "fileName");
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