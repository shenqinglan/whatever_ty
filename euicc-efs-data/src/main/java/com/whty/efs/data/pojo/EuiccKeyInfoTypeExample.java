package com.whty.efs.data.pojo;

import java.util.ArrayList;
import java.util.List;

public class EuiccKeyInfoTypeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EuiccKeyInfoTypeExample() {
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

        public Criteria andKeyInfoIdIsNull() {
            addCriterion("key_info_id is null");
            return (Criteria) this;
        }

        public Criteria andKeyInfoIdIsNotNull() {
            addCriterion("key_info_id is not null");
            return (Criteria) this;
        }

        public Criteria andKeyInfoIdEqualTo(String value) {
            addCriterion("key_info_id =", value, "keyInfoId");
            return (Criteria) this;
        }

        public Criteria andKeyInfoIdNotEqualTo(String value) {
            addCriterion("key_info_id <>", value, "keyInfoId");
            return (Criteria) this;
        }

        public Criteria andKeyInfoIdGreaterThan(String value) {
            addCriterion("key_info_id >", value, "keyInfoId");
            return (Criteria) this;
        }

        public Criteria andKeyInfoIdGreaterThanOrEqualTo(String value) {
            addCriterion("key_info_id >=", value, "keyInfoId");
            return (Criteria) this;
        }

        public Criteria andKeyInfoIdLessThan(String value) {
            addCriterion("key_info_id <", value, "keyInfoId");
            return (Criteria) this;
        }

        public Criteria andKeyInfoIdLessThanOrEqualTo(String value) {
            addCriterion("key_info_id <=", value, "keyInfoId");
            return (Criteria) this;
        }

        public Criteria andKeyInfoIdLike(String value) {
            addCriterion("key_info_id like", value, "keyInfoId");
            return (Criteria) this;
        }

        public Criteria andKeyInfoIdNotLike(String value) {
            addCriterion("key_info_id not like", value, "keyInfoId");
            return (Criteria) this;
        }

        public Criteria andKeyInfoIdIn(List<String> values) {
            addCriterion("key_info_id in", values, "keyInfoId");
            return (Criteria) this;
        }

        public Criteria andKeyInfoIdNotIn(List<String> values) {
            addCriterion("key_info_id not in", values, "keyInfoId");
            return (Criteria) this;
        }

        public Criteria andKeyInfoIdBetween(String value1, String value2) {
            addCriterion("key_info_id between", value1, value2, "keyInfoId");
            return (Criteria) this;
        }

        public Criteria andKeyInfoIdNotBetween(String value1, String value2) {
            addCriterion("key_info_id not between", value1, value2, "keyInfoId");
            return (Criteria) this;
        }

        public Criteria andRetrievalIdIsNull() {
            addCriterion("retrieval_id is null");
            return (Criteria) this;
        }

        public Criteria andRetrievalIdIsNotNull() {
            addCriterion("retrieval_id is not null");
            return (Criteria) this;
        }

        public Criteria andRetrievalIdEqualTo(String value) {
            addCriterion("retrieval_id =", value, "retrievalId");
            return (Criteria) this;
        }

        public Criteria andRetrievalIdNotEqualTo(String value) {
            addCriterion("retrieval_id <>", value, "retrievalId");
            return (Criteria) this;
        }

        public Criteria andRetrievalIdGreaterThan(String value) {
            addCriterion("retrieval_id >", value, "retrievalId");
            return (Criteria) this;
        }

        public Criteria andRetrievalIdGreaterThanOrEqualTo(String value) {
            addCriterion("retrieval_id >=", value, "retrievalId");
            return (Criteria) this;
        }

        public Criteria andRetrievalIdLessThan(String value) {
            addCriterion("retrieval_id <", value, "retrievalId");
            return (Criteria) this;
        }

        public Criteria andRetrievalIdLessThanOrEqualTo(String value) {
            addCriterion("retrieval_id <=", value, "retrievalId");
            return (Criteria) this;
        }

        public Criteria andRetrievalIdLike(String value) {
            addCriterion("retrieval_id like", value, "retrievalId");
            return (Criteria) this;
        }

        public Criteria andRetrievalIdNotLike(String value) {
            addCriterion("retrieval_id not like", value, "retrievalId");
            return (Criteria) this;
        }

        public Criteria andRetrievalIdIn(List<String> values) {
            addCriterion("retrieval_id in", values, "retrievalId");
            return (Criteria) this;
        }

        public Criteria andRetrievalIdNotIn(List<String> values) {
            addCriterion("retrieval_id not in", values, "retrievalId");
            return (Criteria) this;
        }

        public Criteria andRetrievalIdBetween(String value1, String value2) {
            addCriterion("retrieval_id between", value1, value2, "retrievalId");
            return (Criteria) this;
        }

        public Criteria andRetrievalIdNotBetween(String value1, String value2) {
            addCriterion("retrieval_id not between", value1, value2, "retrievalId");
            return (Criteria) this;
        }

        public Criteria andKeyNameIsNull() {
            addCriterion("key_name is null");
            return (Criteria) this;
        }

        public Criteria andKeyNameIsNotNull() {
            addCriterion("key_name is not null");
            return (Criteria) this;
        }

        public Criteria andKeyNameEqualTo(String value) {
            addCriterion("key_name =", value, "keyName");
            return (Criteria) this;
        }

        public Criteria andKeyNameNotEqualTo(String value) {
            addCriterion("key_name <>", value, "keyName");
            return (Criteria) this;
        }

        public Criteria andKeyNameGreaterThan(String value) {
            addCriterion("key_name >", value, "keyName");
            return (Criteria) this;
        }

        public Criteria andKeyNameGreaterThanOrEqualTo(String value) {
            addCriterion("key_name >=", value, "keyName");
            return (Criteria) this;
        }

        public Criteria andKeyNameLessThan(String value) {
            addCriterion("key_name <", value, "keyName");
            return (Criteria) this;
        }

        public Criteria andKeyNameLessThanOrEqualTo(String value) {
            addCriterion("key_name <=", value, "keyName");
            return (Criteria) this;
        }

        public Criteria andKeyNameLike(String value) {
            addCriterion("key_name like", value, "keyName");
            return (Criteria) this;
        }

        public Criteria andKeyNameNotLike(String value) {
            addCriterion("key_name not like", value, "keyName");
            return (Criteria) this;
        }

        public Criteria andKeyNameIn(List<String> values) {
            addCriterion("key_name in", values, "keyName");
            return (Criteria) this;
        }

        public Criteria andKeyNameNotIn(List<String> values) {
            addCriterion("key_name not in", values, "keyName");
            return (Criteria) this;
        }

        public Criteria andKeyNameBetween(String value1, String value2) {
            addCriterion("key_name between", value1, value2, "keyName");
            return (Criteria) this;
        }

        public Criteria andKeyNameNotBetween(String value1, String value2) {
            addCriterion("key_name not between", value1, value2, "keyName");
            return (Criteria) this;
        }

        public Criteria andPIsNull() {
            addCriterion("p is null");
            return (Criteria) this;
        }

        public Criteria andPIsNotNull() {
            addCriterion("p is not null");
            return (Criteria) this;
        }

        public Criteria andPEqualTo(String value) {
            addCriterion("p =", value, "p");
            return (Criteria) this;
        }

        public Criteria andPNotEqualTo(String value) {
            addCriterion("p <>", value, "p");
            return (Criteria) this;
        }

        public Criteria andPGreaterThan(String value) {
            addCriterion("p >", value, "p");
            return (Criteria) this;
        }

        public Criteria andPGreaterThanOrEqualTo(String value) {
            addCriterion("p >=", value, "p");
            return (Criteria) this;
        }

        public Criteria andPLessThan(String value) {
            addCriterion("p <", value, "p");
            return (Criteria) this;
        }

        public Criteria andPLessThanOrEqualTo(String value) {
            addCriterion("p <=", value, "p");
            return (Criteria) this;
        }

        public Criteria andPLike(String value) {
            addCriterion("p like", value, "p");
            return (Criteria) this;
        }

        public Criteria andPNotLike(String value) {
            addCriterion("p not like", value, "p");
            return (Criteria) this;
        }

        public Criteria andPIn(List<String> values) {
            addCriterion("p in", values, "p");
            return (Criteria) this;
        }

        public Criteria andPNotIn(List<String> values) {
            addCriterion("p not in", values, "p");
            return (Criteria) this;
        }

        public Criteria andPBetween(String value1, String value2) {
            addCriterion("p between", value1, value2, "p");
            return (Criteria) this;
        }

        public Criteria andPNotBetween(String value1, String value2) {
            addCriterion("p not between", value1, value2, "p");
            return (Criteria) this;
        }

        public Criteria andQIsNull() {
            addCriterion("q is null");
            return (Criteria) this;
        }

        public Criteria andQIsNotNull() {
            addCriterion("q is not null");
            return (Criteria) this;
        }

        public Criteria andQEqualTo(String value) {
            addCriterion("q =", value, "q");
            return (Criteria) this;
        }

        public Criteria andQNotEqualTo(String value) {
            addCriterion("q <>", value, "q");
            return (Criteria) this;
        }

        public Criteria andQGreaterThan(String value) {
            addCriterion("q >", value, "q");
            return (Criteria) this;
        }

        public Criteria andQGreaterThanOrEqualTo(String value) {
            addCriterion("q >=", value, "q");
            return (Criteria) this;
        }

        public Criteria andQLessThan(String value) {
            addCriterion("q <", value, "q");
            return (Criteria) this;
        }

        public Criteria andQLessThanOrEqualTo(String value) {
            addCriterion("q <=", value, "q");
            return (Criteria) this;
        }

        public Criteria andQLike(String value) {
            addCriterion("q like", value, "q");
            return (Criteria) this;
        }

        public Criteria andQNotLike(String value) {
            addCriterion("q not like", value, "q");
            return (Criteria) this;
        }

        public Criteria andQIn(List<String> values) {
            addCriterion("q in", values, "q");
            return (Criteria) this;
        }

        public Criteria andQNotIn(List<String> values) {
            addCriterion("q not in", values, "q");
            return (Criteria) this;
        }

        public Criteria andQBetween(String value1, String value2) {
            addCriterion("q between", value1, value2, "q");
            return (Criteria) this;
        }

        public Criteria andQNotBetween(String value1, String value2) {
            addCriterion("q not between", value1, value2, "q");
            return (Criteria) this;
        }

        public Criteria andGIsNull() {
            addCriterion("g is null");
            return (Criteria) this;
        }

        public Criteria andGIsNotNull() {
            addCriterion("g is not null");
            return (Criteria) this;
        }

        public Criteria andGEqualTo(String value) {
            addCriterion("g =", value, "g");
            return (Criteria) this;
        }

        public Criteria andGNotEqualTo(String value) {
            addCriterion("g <>", value, "g");
            return (Criteria) this;
        }

        public Criteria andGGreaterThan(String value) {
            addCriterion("g >", value, "g");
            return (Criteria) this;
        }

        public Criteria andGGreaterThanOrEqualTo(String value) {
            addCriterion("g >=", value, "g");
            return (Criteria) this;
        }

        public Criteria andGLessThan(String value) {
            addCriterion("g <", value, "g");
            return (Criteria) this;
        }

        public Criteria andGLessThanOrEqualTo(String value) {
            addCriterion("g <=", value, "g");
            return (Criteria) this;
        }

        public Criteria andGLike(String value) {
            addCriterion("g like", value, "g");
            return (Criteria) this;
        }

        public Criteria andGNotLike(String value) {
            addCriterion("g not like", value, "g");
            return (Criteria) this;
        }

        public Criteria andGIn(List<String> values) {
            addCriterion("g in", values, "g");
            return (Criteria) this;
        }

        public Criteria andGNotIn(List<String> values) {
            addCriterion("g not in", values, "g");
            return (Criteria) this;
        }

        public Criteria andGBetween(String value1, String value2) {
            addCriterion("g between", value1, value2, "g");
            return (Criteria) this;
        }

        public Criteria andGNotBetween(String value1, String value2) {
            addCriterion("g not between", value1, value2, "g");
            return (Criteria) this;
        }

        public Criteria andYIsNull() {
            addCriterion("y is null");
            return (Criteria) this;
        }

        public Criteria andYIsNotNull() {
            addCriterion("y is not null");
            return (Criteria) this;
        }

        public Criteria andYEqualTo(String value) {
            addCriterion("y =", value, "y");
            return (Criteria) this;
        }

        public Criteria andYNotEqualTo(String value) {
            addCriterion("y <>", value, "y");
            return (Criteria) this;
        }

        public Criteria andYGreaterThan(String value) {
            addCriterion("y >", value, "y");
            return (Criteria) this;
        }

        public Criteria andYGreaterThanOrEqualTo(String value) {
            addCriterion("y >=", value, "y");
            return (Criteria) this;
        }

        public Criteria andYLessThan(String value) {
            addCriterion("y <", value, "y");
            return (Criteria) this;
        }

        public Criteria andYLessThanOrEqualTo(String value) {
            addCriterion("y <=", value, "y");
            return (Criteria) this;
        }

        public Criteria andYLike(String value) {
            addCriterion("y like", value, "y");
            return (Criteria) this;
        }

        public Criteria andYNotLike(String value) {
            addCriterion("y not like", value, "y");
            return (Criteria) this;
        }

        public Criteria andYIn(List<String> values) {
            addCriterion("y in", values, "y");
            return (Criteria) this;
        }

        public Criteria andYNotIn(List<String> values) {
            addCriterion("y not in", values, "y");
            return (Criteria) this;
        }

        public Criteria andYBetween(String value1, String value2) {
            addCriterion("y between", value1, value2, "y");
            return (Criteria) this;
        }

        public Criteria andYNotBetween(String value1, String value2) {
            addCriterion("y not between", value1, value2, "y");
            return (Criteria) this;
        }

        public Criteria andJIsNull() {
            addCriterion("j is null");
            return (Criteria) this;
        }

        public Criteria andJIsNotNull() {
            addCriterion("j is not null");
            return (Criteria) this;
        }

        public Criteria andJEqualTo(String value) {
            addCriterion("j =", value, "j");
            return (Criteria) this;
        }

        public Criteria andJNotEqualTo(String value) {
            addCriterion("j <>", value, "j");
            return (Criteria) this;
        }

        public Criteria andJGreaterThan(String value) {
            addCriterion("j >", value, "j");
            return (Criteria) this;
        }

        public Criteria andJGreaterThanOrEqualTo(String value) {
            addCriterion("j >=", value, "j");
            return (Criteria) this;
        }

        public Criteria andJLessThan(String value) {
            addCriterion("j <", value, "j");
            return (Criteria) this;
        }

        public Criteria andJLessThanOrEqualTo(String value) {
            addCriterion("j <=", value, "j");
            return (Criteria) this;
        }

        public Criteria andJLike(String value) {
            addCriterion("j like", value, "j");
            return (Criteria) this;
        }

        public Criteria andJNotLike(String value) {
            addCriterion("j not like", value, "j");
            return (Criteria) this;
        }

        public Criteria andJIn(List<String> values) {
            addCriterion("j in", values, "j");
            return (Criteria) this;
        }

        public Criteria andJNotIn(List<String> values) {
            addCriterion("j not in", values, "j");
            return (Criteria) this;
        }

        public Criteria andJBetween(String value1, String value2) {
            addCriterion("j between", value1, value2, "j");
            return (Criteria) this;
        }

        public Criteria andJNotBetween(String value1, String value2) {
            addCriterion("j not between", value1, value2, "j");
            return (Criteria) this;
        }

        public Criteria andSeedIsNull() {
            addCriterion("seed is null");
            return (Criteria) this;
        }

        public Criteria andSeedIsNotNull() {
            addCriterion("seed is not null");
            return (Criteria) this;
        }

        public Criteria andSeedEqualTo(String value) {
            addCriterion("seed =", value, "seed");
            return (Criteria) this;
        }

        public Criteria andSeedNotEqualTo(String value) {
            addCriterion("seed <>", value, "seed");
            return (Criteria) this;
        }

        public Criteria andSeedGreaterThan(String value) {
            addCriterion("seed >", value, "seed");
            return (Criteria) this;
        }

        public Criteria andSeedGreaterThanOrEqualTo(String value) {
            addCriterion("seed >=", value, "seed");
            return (Criteria) this;
        }

        public Criteria andSeedLessThan(String value) {
            addCriterion("seed <", value, "seed");
            return (Criteria) this;
        }

        public Criteria andSeedLessThanOrEqualTo(String value) {
            addCriterion("seed <=", value, "seed");
            return (Criteria) this;
        }

        public Criteria andSeedLike(String value) {
            addCriterion("seed like", value, "seed");
            return (Criteria) this;
        }

        public Criteria andSeedNotLike(String value) {
            addCriterion("seed not like", value, "seed");
            return (Criteria) this;
        }

        public Criteria andSeedIn(List<String> values) {
            addCriterion("seed in", values, "seed");
            return (Criteria) this;
        }

        public Criteria andSeedNotIn(List<String> values) {
            addCriterion("seed not in", values, "seed");
            return (Criteria) this;
        }

        public Criteria andSeedBetween(String value1, String value2) {
            addCriterion("seed between", value1, value2, "seed");
            return (Criteria) this;
        }

        public Criteria andSeedNotBetween(String value1, String value2) {
            addCriterion("seed not between", value1, value2, "seed");
            return (Criteria) this;
        }

        public Criteria andPgenCounterIsNull() {
            addCriterion("pgen_counter is null");
            return (Criteria) this;
        }

        public Criteria andPgenCounterIsNotNull() {
            addCriterion("pgen_counter is not null");
            return (Criteria) this;
        }

        public Criteria andPgenCounterEqualTo(String value) {
            addCriterion("pgen_counter =", value, "pgenCounter");
            return (Criteria) this;
        }

        public Criteria andPgenCounterNotEqualTo(String value) {
            addCriterion("pgen_counter <>", value, "pgenCounter");
            return (Criteria) this;
        }

        public Criteria andPgenCounterGreaterThan(String value) {
            addCriterion("pgen_counter >", value, "pgenCounter");
            return (Criteria) this;
        }

        public Criteria andPgenCounterGreaterThanOrEqualTo(String value) {
            addCriterion("pgen_counter >=", value, "pgenCounter");
            return (Criteria) this;
        }

        public Criteria andPgenCounterLessThan(String value) {
            addCriterion("pgen_counter <", value, "pgenCounter");
            return (Criteria) this;
        }

        public Criteria andPgenCounterLessThanOrEqualTo(String value) {
            addCriterion("pgen_counter <=", value, "pgenCounter");
            return (Criteria) this;
        }

        public Criteria andPgenCounterLike(String value) {
            addCriterion("pgen_counter like", value, "pgenCounter");
            return (Criteria) this;
        }

        public Criteria andPgenCounterNotLike(String value) {
            addCriterion("pgen_counter not like", value, "pgenCounter");
            return (Criteria) this;
        }

        public Criteria andPgenCounterIn(List<String> values) {
            addCriterion("pgen_counter in", values, "pgenCounter");
            return (Criteria) this;
        }

        public Criteria andPgenCounterNotIn(List<String> values) {
            addCriterion("pgen_counter not in", values, "pgenCounter");
            return (Criteria) this;
        }

        public Criteria andPgenCounterBetween(String value1, String value2) {
            addCriterion("pgen_counter between", value1, value2, "pgenCounter");
            return (Criteria) this;
        }

        public Criteria andPgenCounterNotBetween(String value1, String value2) {
            addCriterion("pgen_counter not between", value1, value2, "pgenCounter");
            return (Criteria) this;
        }

        public Criteria andModulusIsNull() {
            addCriterion("modulus is null");
            return (Criteria) this;
        }

        public Criteria andModulusIsNotNull() {
            addCriterion("modulus is not null");
            return (Criteria) this;
        }

        public Criteria andModulusEqualTo(String value) {
            addCriterion("modulus =", value, "modulus");
            return (Criteria) this;
        }

        public Criteria andModulusNotEqualTo(String value) {
            addCriterion("modulus <>", value, "modulus");
            return (Criteria) this;
        }

        public Criteria andModulusGreaterThan(String value) {
            addCriterion("modulus >", value, "modulus");
            return (Criteria) this;
        }

        public Criteria andModulusGreaterThanOrEqualTo(String value) {
            addCriterion("modulus >=", value, "modulus");
            return (Criteria) this;
        }

        public Criteria andModulusLessThan(String value) {
            addCriterion("modulus <", value, "modulus");
            return (Criteria) this;
        }

        public Criteria andModulusLessThanOrEqualTo(String value) {
            addCriterion("modulus <=", value, "modulus");
            return (Criteria) this;
        }

        public Criteria andModulusLike(String value) {
            addCriterion("modulus like", value, "modulus");
            return (Criteria) this;
        }

        public Criteria andModulusNotLike(String value) {
            addCriterion("modulus not like", value, "modulus");
            return (Criteria) this;
        }

        public Criteria andModulusIn(List<String> values) {
            addCriterion("modulus in", values, "modulus");
            return (Criteria) this;
        }

        public Criteria andModulusNotIn(List<String> values) {
            addCriterion("modulus not in", values, "modulus");
            return (Criteria) this;
        }

        public Criteria andModulusBetween(String value1, String value2) {
            addCriterion("modulus between", value1, value2, "modulus");
            return (Criteria) this;
        }

        public Criteria andModulusNotBetween(String value1, String value2) {
            addCriterion("modulus not between", value1, value2, "modulus");
            return (Criteria) this;
        }

        public Criteria andExponentIsNull() {
            addCriterion("exponent is null");
            return (Criteria) this;
        }

        public Criteria andExponentIsNotNull() {
            addCriterion("exponent is not null");
            return (Criteria) this;
        }

        public Criteria andExponentEqualTo(String value) {
            addCriterion("exponent =", value, "exponent");
            return (Criteria) this;
        }

        public Criteria andExponentNotEqualTo(String value) {
            addCriterion("exponent <>", value, "exponent");
            return (Criteria) this;
        }

        public Criteria andExponentGreaterThan(String value) {
            addCriterion("exponent >", value, "exponent");
            return (Criteria) this;
        }

        public Criteria andExponentGreaterThanOrEqualTo(String value) {
            addCriterion("exponent >=", value, "exponent");
            return (Criteria) this;
        }

        public Criteria andExponentLessThan(String value) {
            addCriterion("exponent <", value, "exponent");
            return (Criteria) this;
        }

        public Criteria andExponentLessThanOrEqualTo(String value) {
            addCriterion("exponent <=", value, "exponent");
            return (Criteria) this;
        }

        public Criteria andExponentLike(String value) {
            addCriterion("exponent like", value, "exponent");
            return (Criteria) this;
        }

        public Criteria andExponentNotLike(String value) {
            addCriterion("exponent not like", value, "exponent");
            return (Criteria) this;
        }

        public Criteria andExponentIn(List<String> values) {
            addCriterion("exponent in", values, "exponent");
            return (Criteria) this;
        }

        public Criteria andExponentNotIn(List<String> values) {
            addCriterion("exponent not in", values, "exponent");
            return (Criteria) this;
        }

        public Criteria andExponentBetween(String value1, String value2) {
            addCriterion("exponent between", value1, value2, "exponent");
            return (Criteria) this;
        }

        public Criteria andExponentNotBetween(String value1, String value2) {
            addCriterion("exponent not between", value1, value2, "exponent");
            return (Criteria) this;
        }

        public Criteria andX509SubjectNameIsNull() {
            addCriterion("x509_subject_name is null");
            return (Criteria) this;
        }

        public Criteria andX509SubjectNameIsNotNull() {
            addCriterion("x509_subject_name is not null");
            return (Criteria) this;
        }

        public Criteria andX509SubjectNameEqualTo(String value) {
            addCriterion("x509_subject_name =", value, "x509SubjectName");
            return (Criteria) this;
        }

        public Criteria andX509SubjectNameNotEqualTo(String value) {
            addCriterion("x509_subject_name <>", value, "x509SubjectName");
            return (Criteria) this;
        }

        public Criteria andX509SubjectNameGreaterThan(String value) {
            addCriterion("x509_subject_name >", value, "x509SubjectName");
            return (Criteria) this;
        }

        public Criteria andX509SubjectNameGreaterThanOrEqualTo(String value) {
            addCriterion("x509_subject_name >=", value, "x509SubjectName");
            return (Criteria) this;
        }

        public Criteria andX509SubjectNameLessThan(String value) {
            addCriterion("x509_subject_name <", value, "x509SubjectName");
            return (Criteria) this;
        }

        public Criteria andX509SubjectNameLessThanOrEqualTo(String value) {
            addCriterion("x509_subject_name <=", value, "x509SubjectName");
            return (Criteria) this;
        }

        public Criteria andX509SubjectNameLike(String value) {
            addCriterion("x509_subject_name like", value, "x509SubjectName");
            return (Criteria) this;
        }

        public Criteria andX509SubjectNameNotLike(String value) {
            addCriterion("x509_subject_name not like", value, "x509SubjectName");
            return (Criteria) this;
        }

        public Criteria andX509SubjectNameIn(List<String> values) {
            addCriterion("x509_subject_name in", values, "x509SubjectName");
            return (Criteria) this;
        }

        public Criteria andX509SubjectNameNotIn(List<String> values) {
            addCriterion("x509_subject_name not in", values, "x509SubjectName");
            return (Criteria) this;
        }

        public Criteria andX509SubjectNameBetween(String value1, String value2) {
            addCriterion("x509_subject_name between", value1, value2, "x509SubjectName");
            return (Criteria) this;
        }

        public Criteria andX509SubjectNameNotBetween(String value1, String value2) {
            addCriterion("x509_subject_name not between", value1, value2, "x509SubjectName");
            return (Criteria) this;
        }

        public Criteria andX509CertificateIsNull() {
            addCriterion("x509_certificate is null");
            return (Criteria) this;
        }

        public Criteria andX509CertificateIsNotNull() {
            addCriterion("x509_certificate is not null");
            return (Criteria) this;
        }

        public Criteria andX509CertificateEqualTo(String value) {
            addCriterion("x509_certificate =", value, "x509Certificate");
            return (Criteria) this;
        }

        public Criteria andX509CertificateNotEqualTo(String value) {
            addCriterion("x509_certificate <>", value, "x509Certificate");
            return (Criteria) this;
        }

        public Criteria andX509CertificateGreaterThan(String value) {
            addCriterion("x509_certificate >", value, "x509Certificate");
            return (Criteria) this;
        }

        public Criteria andX509CertificateGreaterThanOrEqualTo(String value) {
            addCriterion("x509_certificate >=", value, "x509Certificate");
            return (Criteria) this;
        }

        public Criteria andX509CertificateLessThan(String value) {
            addCriterion("x509_certificate <", value, "x509Certificate");
            return (Criteria) this;
        }

        public Criteria andX509CertificateLessThanOrEqualTo(String value) {
            addCriterion("x509_certificate <=", value, "x509Certificate");
            return (Criteria) this;
        }

        public Criteria andX509CertificateLike(String value) {
            addCriterion("x509_certificate like", value, "x509Certificate");
            return (Criteria) this;
        }

        public Criteria andX509CertificateNotLike(String value) {
            addCriterion("x509_certificate not like", value, "x509Certificate");
            return (Criteria) this;
        }

        public Criteria andX509CertificateIn(List<String> values) {
            addCriterion("x509_certificate in", values, "x509Certificate");
            return (Criteria) this;
        }

        public Criteria andX509CertificateNotIn(List<String> values) {
            addCriterion("x509_certificate not in", values, "x509Certificate");
            return (Criteria) this;
        }

        public Criteria andX509CertificateBetween(String value1, String value2) {
            addCriterion("x509_certificate between", value1, value2, "x509Certificate");
            return (Criteria) this;
        }

        public Criteria andX509CertificateNotBetween(String value1, String value2) {
            addCriterion("x509_certificate not between", value1, value2, "x509Certificate");
            return (Criteria) this;
        }

        public Criteria andX509IssuerNameIsNull() {
            addCriterion("x509_issuer_name is null");
            return (Criteria) this;
        }

        public Criteria andX509IssuerNameIsNotNull() {
            addCriterion("x509_issuer_name is not null");
            return (Criteria) this;
        }

        public Criteria andX509IssuerNameEqualTo(String value) {
            addCriterion("x509_issuer_name =", value, "x509IssuerName");
            return (Criteria) this;
        }

        public Criteria andX509IssuerNameNotEqualTo(String value) {
            addCriterion("x509_issuer_name <>", value, "x509IssuerName");
            return (Criteria) this;
        }

        public Criteria andX509IssuerNameGreaterThan(String value) {
            addCriterion("x509_issuer_name >", value, "x509IssuerName");
            return (Criteria) this;
        }

        public Criteria andX509IssuerNameGreaterThanOrEqualTo(String value) {
            addCriterion("x509_issuer_name >=", value, "x509IssuerName");
            return (Criteria) this;
        }

        public Criteria andX509IssuerNameLessThan(String value) {
            addCriterion("x509_issuer_name <", value, "x509IssuerName");
            return (Criteria) this;
        }

        public Criteria andX509IssuerNameLessThanOrEqualTo(String value) {
            addCriterion("x509_issuer_name <=", value, "x509IssuerName");
            return (Criteria) this;
        }

        public Criteria andX509IssuerNameLike(String value) {
            addCriterion("x509_issuer_name like", value, "x509IssuerName");
            return (Criteria) this;
        }

        public Criteria andX509IssuerNameNotLike(String value) {
            addCriterion("x509_issuer_name not like", value, "x509IssuerName");
            return (Criteria) this;
        }

        public Criteria andX509IssuerNameIn(List<String> values) {
            addCriterion("x509_issuer_name in", values, "x509IssuerName");
            return (Criteria) this;
        }

        public Criteria andX509IssuerNameNotIn(List<String> values) {
            addCriterion("x509_issuer_name not in", values, "x509IssuerName");
            return (Criteria) this;
        }

        public Criteria andX509IssuerNameBetween(String value1, String value2) {
            addCriterion("x509_issuer_name between", value1, value2, "x509IssuerName");
            return (Criteria) this;
        }

        public Criteria andX509IssuerNameNotBetween(String value1, String value2) {
            addCriterion("x509_issuer_name not between", value1, value2, "x509IssuerName");
            return (Criteria) this;
        }

        public Criteria andX509SerialNumberIsNull() {
            addCriterion("x509_serial_number is null");
            return (Criteria) this;
        }

        public Criteria andX509SerialNumberIsNotNull() {
            addCriterion("x509_serial_number is not null");
            return (Criteria) this;
        }

        public Criteria andX509SerialNumberEqualTo(String value) {
            addCriterion("x509_serial_number =", value, "x509SerialNumber");
            return (Criteria) this;
        }

        public Criteria andX509SerialNumberNotEqualTo(String value) {
            addCriterion("x509_serial_number <>", value, "x509SerialNumber");
            return (Criteria) this;
        }

        public Criteria andX509SerialNumberGreaterThan(String value) {
            addCriterion("x509_serial_number >", value, "x509SerialNumber");
            return (Criteria) this;
        }

        public Criteria andX509SerialNumberGreaterThanOrEqualTo(String value) {
            addCriterion("x509_serial_number >=", value, "x509SerialNumber");
            return (Criteria) this;
        }

        public Criteria andX509SerialNumberLessThan(String value) {
            addCriterion("x509_serial_number <", value, "x509SerialNumber");
            return (Criteria) this;
        }

        public Criteria andX509SerialNumberLessThanOrEqualTo(String value) {
            addCriterion("x509_serial_number <=", value, "x509SerialNumber");
            return (Criteria) this;
        }

        public Criteria andX509SerialNumberLike(String value) {
            addCriterion("x509_serial_number like", value, "x509SerialNumber");
            return (Criteria) this;
        }

        public Criteria andX509SerialNumberNotLike(String value) {
            addCriterion("x509_serial_number not like", value, "x509SerialNumber");
            return (Criteria) this;
        }

        public Criteria andX509SerialNumberIn(List<String> values) {
            addCriterion("x509_serial_number in", values, "x509SerialNumber");
            return (Criteria) this;
        }

        public Criteria andX509SerialNumberNotIn(List<String> values) {
            addCriterion("x509_serial_number not in", values, "x509SerialNumber");
            return (Criteria) this;
        }

        public Criteria andX509SerialNumberBetween(String value1, String value2) {
            addCriterion("x509_serial_number between", value1, value2, "x509SerialNumber");
            return (Criteria) this;
        }

        public Criteria andX509SerialNumberNotBetween(String value1, String value2) {
            addCriterion("x509_serial_number not between", value1, value2, "x509SerialNumber");
            return (Criteria) this;
        }

        public Criteria andX509SkiIsNull() {
            addCriterion("x509_ski is null");
            return (Criteria) this;
        }

        public Criteria andX509SkiIsNotNull() {
            addCriterion("x509_ski is not null");
            return (Criteria) this;
        }

        public Criteria andX509SkiEqualTo(String value) {
            addCriterion("x509_ski =", value, "x509Ski");
            return (Criteria) this;
        }

        public Criteria andX509SkiNotEqualTo(String value) {
            addCriterion("x509_ski <>", value, "x509Ski");
            return (Criteria) this;
        }

        public Criteria andX509SkiGreaterThan(String value) {
            addCriterion("x509_ski >", value, "x509Ski");
            return (Criteria) this;
        }

        public Criteria andX509SkiGreaterThanOrEqualTo(String value) {
            addCriterion("x509_ski >=", value, "x509Ski");
            return (Criteria) this;
        }

        public Criteria andX509SkiLessThan(String value) {
            addCriterion("x509_ski <", value, "x509Ski");
            return (Criteria) this;
        }

        public Criteria andX509SkiLessThanOrEqualTo(String value) {
            addCriterion("x509_ski <=", value, "x509Ski");
            return (Criteria) this;
        }

        public Criteria andX509SkiLike(String value) {
            addCriterion("x509_ski like", value, "x509Ski");
            return (Criteria) this;
        }

        public Criteria andX509SkiNotLike(String value) {
            addCriterion("x509_ski not like", value, "x509Ski");
            return (Criteria) this;
        }

        public Criteria andX509SkiIn(List<String> values) {
            addCriterion("x509_ski in", values, "x509Ski");
            return (Criteria) this;
        }

        public Criteria andX509SkiNotIn(List<String> values) {
            addCriterion("x509_ski not in", values, "x509Ski");
            return (Criteria) this;
        }

        public Criteria andX509SkiBetween(String value1, String value2) {
            addCriterion("x509_ski between", value1, value2, "x509Ski");
            return (Criteria) this;
        }

        public Criteria andX509SkiNotBetween(String value1, String value2) {
            addCriterion("x509_ski not between", value1, value2, "x509Ski");
            return (Criteria) this;
        }

        public Criteria andX509CrlIsNull() {
            addCriterion("x509_crl is null");
            return (Criteria) this;
        }

        public Criteria andX509CrlIsNotNull() {
            addCriterion("x509_crl is not null");
            return (Criteria) this;
        }

        public Criteria andX509CrlEqualTo(String value) {
            addCriterion("x509_crl =", value, "x509Crl");
            return (Criteria) this;
        }

        public Criteria andX509CrlNotEqualTo(String value) {
            addCriterion("x509_crl <>", value, "x509Crl");
            return (Criteria) this;
        }

        public Criteria andX509CrlGreaterThan(String value) {
            addCriterion("x509_crl >", value, "x509Crl");
            return (Criteria) this;
        }

        public Criteria andX509CrlGreaterThanOrEqualTo(String value) {
            addCriterion("x509_crl >=", value, "x509Crl");
            return (Criteria) this;
        }

        public Criteria andX509CrlLessThan(String value) {
            addCriterion("x509_crl <", value, "x509Crl");
            return (Criteria) this;
        }

        public Criteria andX509CrlLessThanOrEqualTo(String value) {
            addCriterion("x509_crl <=", value, "x509Crl");
            return (Criteria) this;
        }

        public Criteria andX509CrlLike(String value) {
            addCriterion("x509_crl like", value, "x509Crl");
            return (Criteria) this;
        }

        public Criteria andX509CrlNotLike(String value) {
            addCriterion("x509_crl not like", value, "x509Crl");
            return (Criteria) this;
        }

        public Criteria andX509CrlIn(List<String> values) {
            addCriterion("x509_crl in", values, "x509Crl");
            return (Criteria) this;
        }

        public Criteria andX509CrlNotIn(List<String> values) {
            addCriterion("x509_crl not in", values, "x509Crl");
            return (Criteria) this;
        }

        public Criteria andX509CrlBetween(String value1, String value2) {
            addCriterion("x509_crl between", value1, value2, "x509Crl");
            return (Criteria) this;
        }

        public Criteria andX509CrlNotBetween(String value1, String value2) {
            addCriterion("x509_crl not between", value1, value2, "x509Crl");
            return (Criteria) this;
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andPGpKeyIdIsNull() {
            addCriterion("p_gp_key_id is null");
            return (Criteria) this;
        }

        public Criteria andPGpKeyIdIsNotNull() {
            addCriterion("p_gp_key_id is not null");
            return (Criteria) this;
        }

        public Criteria andPGpKeyIdEqualTo(String value) {
            addCriterion("p_gp_key_id =", value, "pGpKeyId");
            return (Criteria) this;
        }

        public Criteria andPGpKeyIdNotEqualTo(String value) {
            addCriterion("p_gp_key_id <>", value, "pGpKeyId");
            return (Criteria) this;
        }

        public Criteria andPGpKeyIdGreaterThan(String value) {
            addCriterion("p_gp_key_id >", value, "pGpKeyId");
            return (Criteria) this;
        }

        public Criteria andPGpKeyIdGreaterThanOrEqualTo(String value) {
            addCriterion("p_gp_key_id >=", value, "pGpKeyId");
            return (Criteria) this;
        }

        public Criteria andPGpKeyIdLessThan(String value) {
            addCriterion("p_gp_key_id <", value, "pGpKeyId");
            return (Criteria) this;
        }

        public Criteria andPGpKeyIdLessThanOrEqualTo(String value) {
            addCriterion("p_gp_key_id <=", value, "pGpKeyId");
            return (Criteria) this;
        }

        public Criteria andPGpKeyIdLike(String value) {
            addCriterion("p_gp_key_id like", value, "pGpKeyId");
            return (Criteria) this;
        }

        public Criteria andPGpKeyIdNotLike(String value) {
            addCriterion("p_gp_key_id not like", value, "pGpKeyId");
            return (Criteria) this;
        }

        public Criteria andPGpKeyIdIn(List<String> values) {
            addCriterion("p_gp_key_id in", values, "pGpKeyId");
            return (Criteria) this;
        }

        public Criteria andPGpKeyIdNotIn(List<String> values) {
            addCriterion("p_gp_key_id not in", values, "pGpKeyId");
            return (Criteria) this;
        }

        public Criteria andPGpKeyIdBetween(String value1, String value2) {
            addCriterion("p_gp_key_id between", value1, value2, "pGpKeyId");
            return (Criteria) this;
        }

        public Criteria andPGpKeyIdNotBetween(String value1, String value2) {
            addCriterion("p_gp_key_id not between", value1, value2, "pGpKeyId");
            return (Criteria) this;
        }

        public Criteria andSPkiSexpIsNull() {
            addCriterion("s_pki_sexp is null");
            return (Criteria) this;
        }

        public Criteria andSPkiSexpIsNotNull() {
            addCriterion("s_pki_sexp is not null");
            return (Criteria) this;
        }

        public Criteria andSPkiSexpEqualTo(String value) {
            addCriterion("s_pki_sexp =", value, "sPkiSexp");
            return (Criteria) this;
        }

        public Criteria andSPkiSexpNotEqualTo(String value) {
            addCriterion("s_pki_sexp <>", value, "sPkiSexp");
            return (Criteria) this;
        }

        public Criteria andSPkiSexpGreaterThan(String value) {
            addCriterion("s_pki_sexp >", value, "sPkiSexp");
            return (Criteria) this;
        }

        public Criteria andSPkiSexpGreaterThanOrEqualTo(String value) {
            addCriterion("s_pki_sexp >=", value, "sPkiSexp");
            return (Criteria) this;
        }

        public Criteria andSPkiSexpLessThan(String value) {
            addCriterion("s_pki_sexp <", value, "sPkiSexp");
            return (Criteria) this;
        }

        public Criteria andSPkiSexpLessThanOrEqualTo(String value) {
            addCriterion("s_pki_sexp <=", value, "sPkiSexp");
            return (Criteria) this;
        }

        public Criteria andSPkiSexpLike(String value) {
            addCriterion("s_pki_sexp like", value, "sPkiSexp");
            return (Criteria) this;
        }

        public Criteria andSPkiSexpNotLike(String value) {
            addCriterion("s_pki_sexp not like", value, "sPkiSexp");
            return (Criteria) this;
        }

        public Criteria andSPkiSexpIn(List<String> values) {
            addCriterion("s_pki_sexp in", values, "sPkiSexp");
            return (Criteria) this;
        }

        public Criteria andSPkiSexpNotIn(List<String> values) {
            addCriterion("s_pki_sexp not in", values, "sPkiSexp");
            return (Criteria) this;
        }

        public Criteria andSPkiSexpBetween(String value1, String value2) {
            addCriterion("s_pki_sexp between", value1, value2, "sPkiSexp");
            return (Criteria) this;
        }

        public Criteria andSPkiSexpNotBetween(String value1, String value2) {
            addCriterion("s_pki_sexp not between", value1, value2, "sPkiSexp");
            return (Criteria) this;
        }

        public Criteria andMgmtDataIsNull() {
            addCriterion("mgmt_data is null");
            return (Criteria) this;
        }

        public Criteria andMgmtDataIsNotNull() {
            addCriterion("mgmt_data is not null");
            return (Criteria) this;
        }

        public Criteria andMgmtDataEqualTo(String value) {
            addCriterion("mgmt_data =", value, "mgmtData");
            return (Criteria) this;
        }

        public Criteria andMgmtDataNotEqualTo(String value) {
            addCriterion("mgmt_data <>", value, "mgmtData");
            return (Criteria) this;
        }

        public Criteria andMgmtDataGreaterThan(String value) {
            addCriterion("mgmt_data >", value, "mgmtData");
            return (Criteria) this;
        }

        public Criteria andMgmtDataGreaterThanOrEqualTo(String value) {
            addCriterion("mgmt_data >=", value, "mgmtData");
            return (Criteria) this;
        }

        public Criteria andMgmtDataLessThan(String value) {
            addCriterion("mgmt_data <", value, "mgmtData");
            return (Criteria) this;
        }

        public Criteria andMgmtDataLessThanOrEqualTo(String value) {
            addCriterion("mgmt_data <=", value, "mgmtData");
            return (Criteria) this;
        }

        public Criteria andMgmtDataLike(String value) {
            addCriterion("mgmt_data like", value, "mgmtData");
            return (Criteria) this;
        }

        public Criteria andMgmtDataNotLike(String value) {
            addCriterion("mgmt_data not like", value, "mgmtData");
            return (Criteria) this;
        }

        public Criteria andMgmtDataIn(List<String> values) {
            addCriterion("mgmt_data in", values, "mgmtData");
            return (Criteria) this;
        }

        public Criteria andMgmtDataNotIn(List<String> values) {
            addCriterion("mgmt_data not in", values, "mgmtData");
            return (Criteria) this;
        }

        public Criteria andMgmtDataBetween(String value1, String value2) {
            addCriterion("mgmt_data between", value1, value2, "mgmtData");
            return (Criteria) this;
        }

        public Criteria andMgmtDataNotBetween(String value1, String value2) {
            addCriterion("mgmt_data not between", value1, value2, "mgmtData");
            return (Criteria) this;
        }

        public Criteria andPGpKeyPacketIsNull() {
            addCriterion("p_gp_key_packet is null");
            return (Criteria) this;
        }

        public Criteria andPGpKeyPacketIsNotNull() {
            addCriterion("p_gp_key_packet is not null");
            return (Criteria) this;
        }

        public Criteria andPGpKeyPacketEqualTo(String value) {
            addCriterion("p_gp_key_packet =", value, "pGpKeyPacket");
            return (Criteria) this;
        }

        public Criteria andPGpKeyPacketNotEqualTo(String value) {
            addCriterion("p_gp_key_packet <>", value, "pGpKeyPacket");
            return (Criteria) this;
        }

        public Criteria andPGpKeyPacketGreaterThan(String value) {
            addCriterion("p_gp_key_packet >", value, "pGpKeyPacket");
            return (Criteria) this;
        }

        public Criteria andPGpKeyPacketGreaterThanOrEqualTo(String value) {
            addCriterion("p_gp_key_packet >=", value, "pGpKeyPacket");
            return (Criteria) this;
        }

        public Criteria andPGpKeyPacketLessThan(String value) {
            addCriterion("p_gp_key_packet <", value, "pGpKeyPacket");
            return (Criteria) this;
        }

        public Criteria andPGpKeyPacketLessThanOrEqualTo(String value) {
            addCriterion("p_gp_key_packet <=", value, "pGpKeyPacket");
            return (Criteria) this;
        }

        public Criteria andPGpKeyPacketLike(String value) {
            addCriterion("p_gp_key_packet like", value, "pGpKeyPacket");
            return (Criteria) this;
        }

        public Criteria andPGpKeyPacketNotLike(String value) {
            addCriterion("p_gp_key_packet not like", value, "pGpKeyPacket");
            return (Criteria) this;
        }

        public Criteria andPGpKeyPacketIn(List<String> values) {
            addCriterion("p_gp_key_packet in", values, "pGpKeyPacket");
            return (Criteria) this;
        }

        public Criteria andPGpKeyPacketNotIn(List<String> values) {
            addCriterion("p_gp_key_packet not in", values, "pGpKeyPacket");
            return (Criteria) this;
        }

        public Criteria andPGpKeyPacketBetween(String value1, String value2) {
            addCriterion("p_gp_key_packet between", value1, value2, "pGpKeyPacket");
            return (Criteria) this;
        }

        public Criteria andPGpKeyPacketNotBetween(String value1, String value2) {
            addCriterion("p_gp_key_packet not between", value1, value2, "pGpKeyPacket");
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