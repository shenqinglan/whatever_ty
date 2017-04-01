package com.whty.efs.data.pojo;

import java.util.ArrayList;
import java.util.List;

public class EuiccCapabilitiesExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EuiccCapabilitiesExample() {
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

        public Criteria andCapabilitieIdIsNull() {
            addCriterion("capabilitie_id is null");
            return (Criteria) this;
        }

        public Criteria andCapabilitieIdIsNotNull() {
            addCriterion("capabilitie_id is not null");
            return (Criteria) this;
        }

        public Criteria andCapabilitieIdEqualTo(String value) {
            addCriterion("capabilitie_id =", value, "capabilitieId");
            return (Criteria) this;
        }

        public Criteria andCapabilitieIdNotEqualTo(String value) {
            addCriterion("capabilitie_id <>", value, "capabilitieId");
            return (Criteria) this;
        }

        public Criteria andCapabilitieIdGreaterThan(String value) {
            addCriterion("capabilitie_id >", value, "capabilitieId");
            return (Criteria) this;
        }

        public Criteria andCapabilitieIdGreaterThanOrEqualTo(String value) {
            addCriterion("capabilitie_id >=", value, "capabilitieId");
            return (Criteria) this;
        }

        public Criteria andCapabilitieIdLessThan(String value) {
            addCriterion("capabilitie_id <", value, "capabilitieId");
            return (Criteria) this;
        }

        public Criteria andCapabilitieIdLessThanOrEqualTo(String value) {
            addCriterion("capabilitie_id <=", value, "capabilitieId");
            return (Criteria) this;
        }

        public Criteria andCapabilitieIdLike(String value) {
            addCriterion("capabilitie_id like", value, "capabilitieId");
            return (Criteria) this;
        }

        public Criteria andCapabilitieIdNotLike(String value) {
            addCriterion("capabilitie_id not like", value, "capabilitieId");
            return (Criteria) this;
        }

        public Criteria andCapabilitieIdIn(List<String> values) {
            addCriterion("capabilitie_id in", values, "capabilitieId");
            return (Criteria) this;
        }

        public Criteria andCapabilitieIdNotIn(List<String> values) {
            addCriterion("capabilitie_id not in", values, "capabilitieId");
            return (Criteria) this;
        }

        public Criteria andCapabilitieIdBetween(String value1, String value2) {
            addCriterion("capabilitie_id between", value1, value2, "capabilitieId");
            return (Criteria) this;
        }

        public Criteria andCapabilitieIdNotBetween(String value1, String value2) {
            addCriterion("capabilitie_id not between", value1, value2, "capabilitieId");
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

        public Criteria andCatTpSupportIsNull() {
            addCriterion("cat_tp_support is null");
            return (Criteria) this;
        }

        public Criteria andCatTpSupportIsNotNull() {
            addCriterion("cat_tp_support is not null");
            return (Criteria) this;
        }

        public Criteria andCatTpSupportEqualTo(String value) {
            addCriterion("cat_tp_support =", value, "catTpSupport");
            return (Criteria) this;
        }

        public Criteria andCatTpSupportNotEqualTo(String value) {
            addCriterion("cat_tp_support <>", value, "catTpSupport");
            return (Criteria) this;
        }

        public Criteria andCatTpSupportGreaterThan(String value) {
            addCriterion("cat_tp_support >", value, "catTpSupport");
            return (Criteria) this;
        }

        public Criteria andCatTpSupportGreaterThanOrEqualTo(String value) {
            addCriterion("cat_tp_support >=", value, "catTpSupport");
            return (Criteria) this;
        }

        public Criteria andCatTpSupportLessThan(String value) {
            addCriterion("cat_tp_support <", value, "catTpSupport");
            return (Criteria) this;
        }

        public Criteria andCatTpSupportLessThanOrEqualTo(String value) {
            addCriterion("cat_tp_support <=", value, "catTpSupport");
            return (Criteria) this;
        }

        public Criteria andCatTpSupportLike(String value) {
            addCriterion("cat_tp_support like", value, "catTpSupport");
            return (Criteria) this;
        }

        public Criteria andCatTpSupportNotLike(String value) {
            addCriterion("cat_tp_support not like", value, "catTpSupport");
            return (Criteria) this;
        }

        public Criteria andCatTpSupportIn(List<String> values) {
            addCriterion("cat_tp_support in", values, "catTpSupport");
            return (Criteria) this;
        }

        public Criteria andCatTpSupportNotIn(List<String> values) {
            addCriterion("cat_tp_support not in", values, "catTpSupport");
            return (Criteria) this;
        }

        public Criteria andCatTpSupportBetween(String value1, String value2) {
            addCriterion("cat_tp_support between", value1, value2, "catTpSupport");
            return (Criteria) this;
        }

        public Criteria andCatTpSupportNotBetween(String value1, String value2) {
            addCriterion("cat_tp_support not between", value1, value2, "catTpSupport");
            return (Criteria) this;
        }

        public Criteria andCatTpVersionIsNull() {
            addCriterion("cat_tp_version is null");
            return (Criteria) this;
        }

        public Criteria andCatTpVersionIsNotNull() {
            addCriterion("cat_tp_version is not null");
            return (Criteria) this;
        }

        public Criteria andCatTpVersionEqualTo(String value) {
            addCriterion("cat_tp_version =", value, "catTpVersion");
            return (Criteria) this;
        }

        public Criteria andCatTpVersionNotEqualTo(String value) {
            addCriterion("cat_tp_version <>", value, "catTpVersion");
            return (Criteria) this;
        }

        public Criteria andCatTpVersionGreaterThan(String value) {
            addCriterion("cat_tp_version >", value, "catTpVersion");
            return (Criteria) this;
        }

        public Criteria andCatTpVersionGreaterThanOrEqualTo(String value) {
            addCriterion("cat_tp_version >=", value, "catTpVersion");
            return (Criteria) this;
        }

        public Criteria andCatTpVersionLessThan(String value) {
            addCriterion("cat_tp_version <", value, "catTpVersion");
            return (Criteria) this;
        }

        public Criteria andCatTpVersionLessThanOrEqualTo(String value) {
            addCriterion("cat_tp_version <=", value, "catTpVersion");
            return (Criteria) this;
        }

        public Criteria andCatTpVersionLike(String value) {
            addCriterion("cat_tp_version like", value, "catTpVersion");
            return (Criteria) this;
        }

        public Criteria andCatTpVersionNotLike(String value) {
            addCriterion("cat_tp_version not like", value, "catTpVersion");
            return (Criteria) this;
        }

        public Criteria andCatTpVersionIn(List<String> values) {
            addCriterion("cat_tp_version in", values, "catTpVersion");
            return (Criteria) this;
        }

        public Criteria andCatTpVersionNotIn(List<String> values) {
            addCriterion("cat_tp_version not in", values, "catTpVersion");
            return (Criteria) this;
        }

        public Criteria andCatTpVersionBetween(String value1, String value2) {
            addCriterion("cat_tp_version between", value1, value2, "catTpVersion");
            return (Criteria) this;
        }

        public Criteria andCatTpVersionNotBetween(String value1, String value2) {
            addCriterion("cat_tp_version not between", value1, value2, "catTpVersion");
            return (Criteria) this;
        }

        public Criteria andHttpSupportIsNull() {
            addCriterion("http_support is null");
            return (Criteria) this;
        }

        public Criteria andHttpSupportIsNotNull() {
            addCriterion("http_support is not null");
            return (Criteria) this;
        }

        public Criteria andHttpSupportEqualTo(String value) {
            addCriterion("http_support =", value, "httpSupport");
            return (Criteria) this;
        }

        public Criteria andHttpSupportNotEqualTo(String value) {
            addCriterion("http_support <>", value, "httpSupport");
            return (Criteria) this;
        }

        public Criteria andHttpSupportGreaterThan(String value) {
            addCriterion("http_support >", value, "httpSupport");
            return (Criteria) this;
        }

        public Criteria andHttpSupportGreaterThanOrEqualTo(String value) {
            addCriterion("http_support >=", value, "httpSupport");
            return (Criteria) this;
        }

        public Criteria andHttpSupportLessThan(String value) {
            addCriterion("http_support <", value, "httpSupport");
            return (Criteria) this;
        }

        public Criteria andHttpSupportLessThanOrEqualTo(String value) {
            addCriterion("http_support <=", value, "httpSupport");
            return (Criteria) this;
        }

        public Criteria andHttpSupportLike(String value) {
            addCriterion("http_support like", value, "httpSupport");
            return (Criteria) this;
        }

        public Criteria andHttpSupportNotLike(String value) {
            addCriterion("http_support not like", value, "httpSupport");
            return (Criteria) this;
        }

        public Criteria andHttpSupportIn(List<String> values) {
            addCriterion("http_support in", values, "httpSupport");
            return (Criteria) this;
        }

        public Criteria andHttpSupportNotIn(List<String> values) {
            addCriterion("http_support not in", values, "httpSupport");
            return (Criteria) this;
        }

        public Criteria andHttpSupportBetween(String value1, String value2) {
            addCriterion("http_support between", value1, value2, "httpSupport");
            return (Criteria) this;
        }

        public Criteria andHttpSupportNotBetween(String value1, String value2) {
            addCriterion("http_support not between", value1, value2, "httpSupport");
            return (Criteria) this;
        }

        public Criteria andHttpVersionIsNull() {
            addCriterion("http_version is null");
            return (Criteria) this;
        }

        public Criteria andHttpVersionIsNotNull() {
            addCriterion("http_version is not null");
            return (Criteria) this;
        }

        public Criteria andHttpVersionEqualTo(String value) {
            addCriterion("http_version =", value, "httpVersion");
            return (Criteria) this;
        }

        public Criteria andHttpVersionNotEqualTo(String value) {
            addCriterion("http_version <>", value, "httpVersion");
            return (Criteria) this;
        }

        public Criteria andHttpVersionGreaterThan(String value) {
            addCriterion("http_version >", value, "httpVersion");
            return (Criteria) this;
        }

        public Criteria andHttpVersionGreaterThanOrEqualTo(String value) {
            addCriterion("http_version >=", value, "httpVersion");
            return (Criteria) this;
        }

        public Criteria andHttpVersionLessThan(String value) {
            addCriterion("http_version <", value, "httpVersion");
            return (Criteria) this;
        }

        public Criteria andHttpVersionLessThanOrEqualTo(String value) {
            addCriterion("http_version <=", value, "httpVersion");
            return (Criteria) this;
        }

        public Criteria andHttpVersionLike(String value) {
            addCriterion("http_version like", value, "httpVersion");
            return (Criteria) this;
        }

        public Criteria andHttpVersionNotLike(String value) {
            addCriterion("http_version not like", value, "httpVersion");
            return (Criteria) this;
        }

        public Criteria andHttpVersionIn(List<String> values) {
            addCriterion("http_version in", values, "httpVersion");
            return (Criteria) this;
        }

        public Criteria andHttpVersionNotIn(List<String> values) {
            addCriterion("http_version not in", values, "httpVersion");
            return (Criteria) this;
        }

        public Criteria andHttpVersionBetween(String value1, String value2) {
            addCriterion("http_version between", value1, value2, "httpVersion");
            return (Criteria) this;
        }

        public Criteria andHttpVersionNotBetween(String value1, String value2) {
            addCriterion("http_version not between", value1, value2, "httpVersion");
            return (Criteria) this;
        }

        public Criteria andSecurePacketVersionIsNull() {
            addCriterion("secure_packet_version is null");
            return (Criteria) this;
        }

        public Criteria andSecurePacketVersionIsNotNull() {
            addCriterion("secure_packet_version is not null");
            return (Criteria) this;
        }

        public Criteria andSecurePacketVersionEqualTo(String value) {
            addCriterion("secure_packet_version =", value, "securePacketVersion");
            return (Criteria) this;
        }

        public Criteria andSecurePacketVersionNotEqualTo(String value) {
            addCriterion("secure_packet_version <>", value, "securePacketVersion");
            return (Criteria) this;
        }

        public Criteria andSecurePacketVersionGreaterThan(String value) {
            addCriterion("secure_packet_version >", value, "securePacketVersion");
            return (Criteria) this;
        }

        public Criteria andSecurePacketVersionGreaterThanOrEqualTo(String value) {
            addCriterion("secure_packet_version >=", value, "securePacketVersion");
            return (Criteria) this;
        }

        public Criteria andSecurePacketVersionLessThan(String value) {
            addCriterion("secure_packet_version <", value, "securePacketVersion");
            return (Criteria) this;
        }

        public Criteria andSecurePacketVersionLessThanOrEqualTo(String value) {
            addCriterion("secure_packet_version <=", value, "securePacketVersion");
            return (Criteria) this;
        }

        public Criteria andSecurePacketVersionLike(String value) {
            addCriterion("secure_packet_version like", value, "securePacketVersion");
            return (Criteria) this;
        }

        public Criteria andSecurePacketVersionNotLike(String value) {
            addCriterion("secure_packet_version not like", value, "securePacketVersion");
            return (Criteria) this;
        }

        public Criteria andSecurePacketVersionIn(List<String> values) {
            addCriterion("secure_packet_version in", values, "securePacketVersion");
            return (Criteria) this;
        }

        public Criteria andSecurePacketVersionNotIn(List<String> values) {
            addCriterion("secure_packet_version not in", values, "securePacketVersion");
            return (Criteria) this;
        }

        public Criteria andSecurePacketVersionBetween(String value1, String value2) {
            addCriterion("secure_packet_version between", value1, value2, "securePacketVersion");
            return (Criteria) this;
        }

        public Criteria andSecurePacketVersionNotBetween(String value1, String value2) {
            addCriterion("secure_packet_version not between", value1, value2, "securePacketVersion");
            return (Criteria) this;
        }

        public Criteria andRemoteProvisioningVersionIsNull() {
            addCriterion("remote_provisioning_version is null");
            return (Criteria) this;
        }

        public Criteria andRemoteProvisioningVersionIsNotNull() {
            addCriterion("remote_provisioning_version is not null");
            return (Criteria) this;
        }

        public Criteria andRemoteProvisioningVersionEqualTo(String value) {
            addCriterion("remote_provisioning_version =", value, "remoteProvisioningVersion");
            return (Criteria) this;
        }

        public Criteria andRemoteProvisioningVersionNotEqualTo(String value) {
            addCriterion("remote_provisioning_version <>", value, "remoteProvisioningVersion");
            return (Criteria) this;
        }

        public Criteria andRemoteProvisioningVersionGreaterThan(String value) {
            addCriterion("remote_provisioning_version >", value, "remoteProvisioningVersion");
            return (Criteria) this;
        }

        public Criteria andRemoteProvisioningVersionGreaterThanOrEqualTo(String value) {
            addCriterion("remote_provisioning_version >=", value, "remoteProvisioningVersion");
            return (Criteria) this;
        }

        public Criteria andRemoteProvisioningVersionLessThan(String value) {
            addCriterion("remote_provisioning_version <", value, "remoteProvisioningVersion");
            return (Criteria) this;
        }

        public Criteria andRemoteProvisioningVersionLessThanOrEqualTo(String value) {
            addCriterion("remote_provisioning_version <=", value, "remoteProvisioningVersion");
            return (Criteria) this;
        }

        public Criteria andRemoteProvisioningVersionLike(String value) {
            addCriterion("remote_provisioning_version like", value, "remoteProvisioningVersion");
            return (Criteria) this;
        }

        public Criteria andRemoteProvisioningVersionNotLike(String value) {
            addCriterion("remote_provisioning_version not like", value, "remoteProvisioningVersion");
            return (Criteria) this;
        }

        public Criteria andRemoteProvisioningVersionIn(List<String> values) {
            addCriterion("remote_provisioning_version in", values, "remoteProvisioningVersion");
            return (Criteria) this;
        }

        public Criteria andRemoteProvisioningVersionNotIn(List<String> values) {
            addCriterion("remote_provisioning_version not in", values, "remoteProvisioningVersion");
            return (Criteria) this;
        }

        public Criteria andRemoteProvisioningVersionBetween(String value1, String value2) {
            addCriterion("remote_provisioning_version between", value1, value2, "remoteProvisioningVersion");
            return (Criteria) this;
        }

        public Criteria andRemoteProvisioningVersionNotBetween(String value1, String value2) {
            addCriterion("remote_provisioning_version not between", value1, value2, "remoteProvisioningVersion");
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