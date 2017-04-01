package com.whty.rsp.data.pojo;

import java.util.ArrayList;
import java.util.List;

public class RspProfileExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RspProfileExample() {
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

        public Criteria andProfileStateIsNull() {
            addCriterion("profile_state is null");
            return (Criteria) this;
        }

        public Criteria andProfileStateIsNotNull() {
            addCriterion("profile_state is not null");
            return (Criteria) this;
        }

        public Criteria andProfileStateEqualTo(String value) {
            addCriterion("profile_state =", value, "profileState");
            return (Criteria) this;
        }

        public Criteria andProfileStateNotEqualTo(String value) {
            addCriterion("profile_state <>", value, "profileState");
            return (Criteria) this;
        }

        public Criteria andProfileStateGreaterThan(String value) {
            addCriterion("profile_state >", value, "profileState");
            return (Criteria) this;
        }

        public Criteria andProfileStateGreaterThanOrEqualTo(String value) {
            addCriterion("profile_state >=", value, "profileState");
            return (Criteria) this;
        }

        public Criteria andProfileStateLessThan(String value) {
            addCriterion("profile_state <", value, "profileState");
            return (Criteria) this;
        }

        public Criteria andProfileStateLessThanOrEqualTo(String value) {
            addCriterion("profile_state <=", value, "profileState");
            return (Criteria) this;
        }

        public Criteria andProfileStateLike(String value) {
            addCriterion("profile_state like", value, "profileState");
            return (Criteria) this;
        }

        public Criteria andProfileStateNotLike(String value) {
            addCriterion("profile_state not like", value, "profileState");
            return (Criteria) this;
        }

        public Criteria andProfileStateIn(List<String> values) {
            addCriterion("profile_state in", values, "profileState");
            return (Criteria) this;
        }

        public Criteria andProfileStateNotIn(List<String> values) {
            addCriterion("profile_state not in", values, "profileState");
            return (Criteria) this;
        }

        public Criteria andProfileStateBetween(String value1, String value2) {
            addCriterion("profile_state between", value1, value2, "profileState");
            return (Criteria) this;
        }

        public Criteria andProfileStateNotBetween(String value1, String value2) {
            addCriterion("profile_state not between", value1, value2, "profileState");
            return (Criteria) this;
        }

        public Criteria andProfileNickNameIsNull() {
            addCriterion("profile_nick_name is null");
            return (Criteria) this;
        }

        public Criteria andProfileNickNameIsNotNull() {
            addCriterion("profile_nick_name is not null");
            return (Criteria) this;
        }

        public Criteria andProfileNickNameEqualTo(String value) {
            addCriterion("profile_nick_name =", value, "profileNickName");
            return (Criteria) this;
        }

        public Criteria andProfileNickNameNotEqualTo(String value) {
            addCriterion("profile_nick_name <>", value, "profileNickName");
            return (Criteria) this;
        }

        public Criteria andProfileNickNameGreaterThan(String value) {
            addCriterion("profile_nick_name >", value, "profileNickName");
            return (Criteria) this;
        }

        public Criteria andProfileNickNameGreaterThanOrEqualTo(String value) {
            addCriterion("profile_nick_name >=", value, "profileNickName");
            return (Criteria) this;
        }

        public Criteria andProfileNickNameLessThan(String value) {
            addCriterion("profile_nick_name <", value, "profileNickName");
            return (Criteria) this;
        }

        public Criteria andProfileNickNameLessThanOrEqualTo(String value) {
            addCriterion("profile_nick_name <=", value, "profileNickName");
            return (Criteria) this;
        }

        public Criteria andProfileNickNameLike(String value) {
            addCriterion("profile_nick_name like", value, "profileNickName");
            return (Criteria) this;
        }

        public Criteria andProfileNickNameNotLike(String value) {
            addCriterion("profile_nick_name not like", value, "profileNickName");
            return (Criteria) this;
        }

        public Criteria andProfileNickNameIn(List<String> values) {
            addCriterion("profile_nick_name in", values, "profileNickName");
            return (Criteria) this;
        }

        public Criteria andProfileNickNameNotIn(List<String> values) {
            addCriterion("profile_nick_name not in", values, "profileNickName");
            return (Criteria) this;
        }

        public Criteria andProfileNickNameBetween(String value1, String value2) {
            addCriterion("profile_nick_name between", value1, value2, "profileNickName");
            return (Criteria) this;
        }

        public Criteria andProfileNickNameNotBetween(String value1, String value2) {
            addCriterion("profile_nick_name not between", value1, value2, "profileNickName");
            return (Criteria) this;
        }

        public Criteria andServiceProviderNameIsNull() {
            addCriterion("service_provider_name is null");
            return (Criteria) this;
        }

        public Criteria andServiceProviderNameIsNotNull() {
            addCriterion("service_provider_name is not null");
            return (Criteria) this;
        }

        public Criteria andServiceProviderNameEqualTo(String value) {
            addCriterion("service_provider_name =", value, "serviceProviderName");
            return (Criteria) this;
        }

        public Criteria andServiceProviderNameNotEqualTo(String value) {
            addCriterion("service_provider_name <>", value, "serviceProviderName");
            return (Criteria) this;
        }

        public Criteria andServiceProviderNameGreaterThan(String value) {
            addCriterion("service_provider_name >", value, "serviceProviderName");
            return (Criteria) this;
        }

        public Criteria andServiceProviderNameGreaterThanOrEqualTo(String value) {
            addCriterion("service_provider_name >=", value, "serviceProviderName");
            return (Criteria) this;
        }

        public Criteria andServiceProviderNameLessThan(String value) {
            addCriterion("service_provider_name <", value, "serviceProviderName");
            return (Criteria) this;
        }

        public Criteria andServiceProviderNameLessThanOrEqualTo(String value) {
            addCriterion("service_provider_name <=", value, "serviceProviderName");
            return (Criteria) this;
        }

        public Criteria andServiceProviderNameLike(String value) {
            addCriterion("service_provider_name like", value, "serviceProviderName");
            return (Criteria) this;
        }

        public Criteria andServiceProviderNameNotLike(String value) {
            addCriterion("service_provider_name not like", value, "serviceProviderName");
            return (Criteria) this;
        }

        public Criteria andServiceProviderNameIn(List<String> values) {
            addCriterion("service_provider_name in", values, "serviceProviderName");
            return (Criteria) this;
        }

        public Criteria andServiceProviderNameNotIn(List<String> values) {
            addCriterion("service_provider_name not in", values, "serviceProviderName");
            return (Criteria) this;
        }

        public Criteria andServiceProviderNameBetween(String value1, String value2) {
            addCriterion("service_provider_name between", value1, value2, "serviceProviderName");
            return (Criteria) this;
        }

        public Criteria andServiceProviderNameNotBetween(String value1, String value2) {
            addCriterion("service_provider_name not between", value1, value2, "serviceProviderName");
            return (Criteria) this;
        }

        public Criteria andProfileNameIsNull() {
            addCriterion("profile_name is null");
            return (Criteria) this;
        }

        public Criteria andProfileNameIsNotNull() {
            addCriterion("profile_name is not null");
            return (Criteria) this;
        }

        public Criteria andProfileNameEqualTo(String value) {
            addCriterion("profile_name =", value, "profileName");
            return (Criteria) this;
        }

        public Criteria andProfileNameNotEqualTo(String value) {
            addCriterion("profile_name <>", value, "profileName");
            return (Criteria) this;
        }

        public Criteria andProfileNameGreaterThan(String value) {
            addCriterion("profile_name >", value, "profileName");
            return (Criteria) this;
        }

        public Criteria andProfileNameGreaterThanOrEqualTo(String value) {
            addCriterion("profile_name >=", value, "profileName");
            return (Criteria) this;
        }

        public Criteria andProfileNameLessThan(String value) {
            addCriterion("profile_name <", value, "profileName");
            return (Criteria) this;
        }

        public Criteria andProfileNameLessThanOrEqualTo(String value) {
            addCriterion("profile_name <=", value, "profileName");
            return (Criteria) this;
        }

        public Criteria andProfileNameLike(String value) {
            addCriterion("profile_name like", value, "profileName");
            return (Criteria) this;
        }

        public Criteria andProfileNameNotLike(String value) {
            addCriterion("profile_name not like", value, "profileName");
            return (Criteria) this;
        }

        public Criteria andProfileNameIn(List<String> values) {
            addCriterion("profile_name in", values, "profileName");
            return (Criteria) this;
        }

        public Criteria andProfileNameNotIn(List<String> values) {
            addCriterion("profile_name not in", values, "profileName");
            return (Criteria) this;
        }

        public Criteria andProfileNameBetween(String value1, String value2) {
            addCriterion("profile_name between", value1, value2, "profileName");
            return (Criteria) this;
        }

        public Criteria andProfileNameNotBetween(String value1, String value2) {
            addCriterion("profile_name not between", value1, value2, "profileName");
            return (Criteria) this;
        }

        public Criteria andIconTypeIsNull() {
            addCriterion("icon_type is null");
            return (Criteria) this;
        }

        public Criteria andIconTypeIsNotNull() {
            addCriterion("icon_type is not null");
            return (Criteria) this;
        }

        public Criteria andIconTypeEqualTo(String value) {
            addCriterion("icon_type =", value, "iconType");
            return (Criteria) this;
        }

        public Criteria andIconTypeNotEqualTo(String value) {
            addCriterion("icon_type <>", value, "iconType");
            return (Criteria) this;
        }

        public Criteria andIconTypeGreaterThan(String value) {
            addCriterion("icon_type >", value, "iconType");
            return (Criteria) this;
        }

        public Criteria andIconTypeGreaterThanOrEqualTo(String value) {
            addCriterion("icon_type >=", value, "iconType");
            return (Criteria) this;
        }

        public Criteria andIconTypeLessThan(String value) {
            addCriterion("icon_type <", value, "iconType");
            return (Criteria) this;
        }

        public Criteria andIconTypeLessThanOrEqualTo(String value) {
            addCriterion("icon_type <=", value, "iconType");
            return (Criteria) this;
        }

        public Criteria andIconTypeLike(String value) {
            addCriterion("icon_type like", value, "iconType");
            return (Criteria) this;
        }

        public Criteria andIconTypeNotLike(String value) {
            addCriterion("icon_type not like", value, "iconType");
            return (Criteria) this;
        }

        public Criteria andIconTypeIn(List<String> values) {
            addCriterion("icon_type in", values, "iconType");
            return (Criteria) this;
        }

        public Criteria andIconTypeNotIn(List<String> values) {
            addCriterion("icon_type not in", values, "iconType");
            return (Criteria) this;
        }

        public Criteria andIconTypeBetween(String value1, String value2) {
            addCriterion("icon_type between", value1, value2, "iconType");
            return (Criteria) this;
        }

        public Criteria andIconTypeNotBetween(String value1, String value2) {
            addCriterion("icon_type not between", value1, value2, "iconType");
            return (Criteria) this;
        }

        public Criteria andProfileClassIsNull() {
            addCriterion("profile_class is null");
            return (Criteria) this;
        }

        public Criteria andProfileClassIsNotNull() {
            addCriterion("profile_class is not null");
            return (Criteria) this;
        }

        public Criteria andProfileClassEqualTo(String value) {
            addCriterion("profile_class =", value, "profileClass");
            return (Criteria) this;
        }

        public Criteria andProfileClassNotEqualTo(String value) {
            addCriterion("profile_class <>", value, "profileClass");
            return (Criteria) this;
        }

        public Criteria andProfileClassGreaterThan(String value) {
            addCriterion("profile_class >", value, "profileClass");
            return (Criteria) this;
        }

        public Criteria andProfileClassGreaterThanOrEqualTo(String value) {
            addCriterion("profile_class >=", value, "profileClass");
            return (Criteria) this;
        }

        public Criteria andProfileClassLessThan(String value) {
            addCriterion("profile_class <", value, "profileClass");
            return (Criteria) this;
        }

        public Criteria andProfileClassLessThanOrEqualTo(String value) {
            addCriterion("profile_class <=", value, "profileClass");
            return (Criteria) this;
        }

        public Criteria andProfileClassLike(String value) {
            addCriterion("profile_class like", value, "profileClass");
            return (Criteria) this;
        }

        public Criteria andProfileClassNotLike(String value) {
            addCriterion("profile_class not like", value, "profileClass");
            return (Criteria) this;
        }

        public Criteria andProfileClassIn(List<String> values) {
            addCriterion("profile_class in", values, "profileClass");
            return (Criteria) this;
        }

        public Criteria andProfileClassNotIn(List<String> values) {
            addCriterion("profile_class not in", values, "profileClass");
            return (Criteria) this;
        }

        public Criteria andProfileClassBetween(String value1, String value2) {
            addCriterion("profile_class between", value1, value2, "profileClass");
            return (Criteria) this;
        }

        public Criteria andProfileClassNotBetween(String value1, String value2) {
            addCriterion("profile_class not between", value1, value2, "profileClass");
            return (Criteria) this;
        }

        public Criteria andNotifEventIsNull() {
            addCriterion("notif_event is null");
            return (Criteria) this;
        }

        public Criteria andNotifEventIsNotNull() {
            addCriterion("notif_event is not null");
            return (Criteria) this;
        }

        public Criteria andNotifEventEqualTo(String value) {
            addCriterion("notif_event =", value, "notifEvent");
            return (Criteria) this;
        }

        public Criteria andNotifEventNotEqualTo(String value) {
            addCriterion("notif_event <>", value, "notifEvent");
            return (Criteria) this;
        }

        public Criteria andNotifEventGreaterThan(String value) {
            addCriterion("notif_event >", value, "notifEvent");
            return (Criteria) this;
        }

        public Criteria andNotifEventGreaterThanOrEqualTo(String value) {
            addCriterion("notif_event >=", value, "notifEvent");
            return (Criteria) this;
        }

        public Criteria andNotifEventLessThan(String value) {
            addCriterion("notif_event <", value, "notifEvent");
            return (Criteria) this;
        }

        public Criteria andNotifEventLessThanOrEqualTo(String value) {
            addCriterion("notif_event <=", value, "notifEvent");
            return (Criteria) this;
        }

        public Criteria andNotifEventLike(String value) {
            addCriterion("notif_event like", value, "notifEvent");
            return (Criteria) this;
        }

        public Criteria andNotifEventNotLike(String value) {
            addCriterion("notif_event not like", value, "notifEvent");
            return (Criteria) this;
        }

        public Criteria andNotifEventIn(List<String> values) {
            addCriterion("notif_event in", values, "notifEvent");
            return (Criteria) this;
        }

        public Criteria andNotifEventNotIn(List<String> values) {
            addCriterion("notif_event not in", values, "notifEvent");
            return (Criteria) this;
        }

        public Criteria andNotifEventBetween(String value1, String value2) {
            addCriterion("notif_event between", value1, value2, "notifEvent");
            return (Criteria) this;
        }

        public Criteria andNotifEventNotBetween(String value1, String value2) {
            addCriterion("notif_event not between", value1, value2, "notifEvent");
            return (Criteria) this;
        }

        public Criteria andNotifAddressIsNull() {
            addCriterion("notif_address is null");
            return (Criteria) this;
        }

        public Criteria andNotifAddressIsNotNull() {
            addCriterion("notif_address is not null");
            return (Criteria) this;
        }

        public Criteria andNotifAddressEqualTo(String value) {
            addCriterion("notif_address =", value, "notifAddress");
            return (Criteria) this;
        }

        public Criteria andNotifAddressNotEqualTo(String value) {
            addCriterion("notif_address <>", value, "notifAddress");
            return (Criteria) this;
        }

        public Criteria andNotifAddressGreaterThan(String value) {
            addCriterion("notif_address >", value, "notifAddress");
            return (Criteria) this;
        }

        public Criteria andNotifAddressGreaterThanOrEqualTo(String value) {
            addCriterion("notif_address >=", value, "notifAddress");
            return (Criteria) this;
        }

        public Criteria andNotifAddressLessThan(String value) {
            addCriterion("notif_address <", value, "notifAddress");
            return (Criteria) this;
        }

        public Criteria andNotifAddressLessThanOrEqualTo(String value) {
            addCriterion("notif_address <=", value, "notifAddress");
            return (Criteria) this;
        }

        public Criteria andNotifAddressLike(String value) {
            addCriterion("notif_address like", value, "notifAddress");
            return (Criteria) this;
        }

        public Criteria andNotifAddressNotLike(String value) {
            addCriterion("notif_address not like", value, "notifAddress");
            return (Criteria) this;
        }

        public Criteria andNotifAddressIn(List<String> values) {
            addCriterion("notif_address in", values, "notifAddress");
            return (Criteria) this;
        }

        public Criteria andNotifAddressNotIn(List<String> values) {
            addCriterion("notif_address not in", values, "notifAddress");
            return (Criteria) this;
        }

        public Criteria andNotifAddressBetween(String value1, String value2) {
            addCriterion("notif_address between", value1, value2, "notifAddress");
            return (Criteria) this;
        }

        public Criteria andNotifAddressNotBetween(String value1, String value2) {
            addCriterion("notif_address not between", value1, value2, "notifAddress");
            return (Criteria) this;
        }

        public Criteria andProfileOwnerIsNull() {
            addCriterion("profile_owner is null");
            return (Criteria) this;
        }

        public Criteria andProfileOwnerIsNotNull() {
            addCriterion("profile_owner is not null");
            return (Criteria) this;
        }

        public Criteria andProfileOwnerEqualTo(String value) {
            addCriterion("profile_owner =", value, "profileOwner");
            return (Criteria) this;
        }

        public Criteria andProfileOwnerNotEqualTo(String value) {
            addCriterion("profile_owner <>", value, "profileOwner");
            return (Criteria) this;
        }

        public Criteria andProfileOwnerGreaterThan(String value) {
            addCriterion("profile_owner >", value, "profileOwner");
            return (Criteria) this;
        }

        public Criteria andProfileOwnerGreaterThanOrEqualTo(String value) {
            addCriterion("profile_owner >=", value, "profileOwner");
            return (Criteria) this;
        }

        public Criteria andProfileOwnerLessThan(String value) {
            addCriterion("profile_owner <", value, "profileOwner");
            return (Criteria) this;
        }

        public Criteria andProfileOwnerLessThanOrEqualTo(String value) {
            addCriterion("profile_owner <=", value, "profileOwner");
            return (Criteria) this;
        }

        public Criteria andProfileOwnerLike(String value) {
            addCriterion("profile_owner like", value, "profileOwner");
            return (Criteria) this;
        }

        public Criteria andProfileOwnerNotLike(String value) {
            addCriterion("profile_owner not like", value, "profileOwner");
            return (Criteria) this;
        }

        public Criteria andProfileOwnerIn(List<String> values) {
            addCriterion("profile_owner in", values, "profileOwner");
            return (Criteria) this;
        }

        public Criteria andProfileOwnerNotIn(List<String> values) {
            addCriterion("profile_owner not in", values, "profileOwner");
            return (Criteria) this;
        }

        public Criteria andProfileOwnerBetween(String value1, String value2) {
            addCriterion("profile_owner between", value1, value2, "profileOwner");
            return (Criteria) this;
        }

        public Criteria andProfileOwnerNotBetween(String value1, String value2) {
            addCriterion("profile_owner not between", value1, value2, "profileOwner");
            return (Criteria) this;
        }

        public Criteria andSmdpProprietaryDataIsNull() {
            addCriterion("smdp_proprietary_data is null");
            return (Criteria) this;
        }

        public Criteria andSmdpProprietaryDataIsNotNull() {
            addCriterion("smdp_proprietary_data is not null");
            return (Criteria) this;
        }

        public Criteria andSmdpProprietaryDataEqualTo(String value) {
            addCriterion("smdp_proprietary_data =", value, "smdpProprietaryData");
            return (Criteria) this;
        }

        public Criteria andSmdpProprietaryDataNotEqualTo(String value) {
            addCriterion("smdp_proprietary_data <>", value, "smdpProprietaryData");
            return (Criteria) this;
        }

        public Criteria andSmdpProprietaryDataGreaterThan(String value) {
            addCriterion("smdp_proprietary_data >", value, "smdpProprietaryData");
            return (Criteria) this;
        }

        public Criteria andSmdpProprietaryDataGreaterThanOrEqualTo(String value) {
            addCriterion("smdp_proprietary_data >=", value, "smdpProprietaryData");
            return (Criteria) this;
        }

        public Criteria andSmdpProprietaryDataLessThan(String value) {
            addCriterion("smdp_proprietary_data <", value, "smdpProprietaryData");
            return (Criteria) this;
        }

        public Criteria andSmdpProprietaryDataLessThanOrEqualTo(String value) {
            addCriterion("smdp_proprietary_data <=", value, "smdpProprietaryData");
            return (Criteria) this;
        }

        public Criteria andSmdpProprietaryDataLike(String value) {
            addCriterion("smdp_proprietary_data like", value, "smdpProprietaryData");
            return (Criteria) this;
        }

        public Criteria andSmdpProprietaryDataNotLike(String value) {
            addCriterion("smdp_proprietary_data not like", value, "smdpProprietaryData");
            return (Criteria) this;
        }

        public Criteria andSmdpProprietaryDataIn(List<String> values) {
            addCriterion("smdp_proprietary_data in", values, "smdpProprietaryData");
            return (Criteria) this;
        }

        public Criteria andSmdpProprietaryDataNotIn(List<String> values) {
            addCriterion("smdp_proprietary_data not in", values, "smdpProprietaryData");
            return (Criteria) this;
        }

        public Criteria andSmdpProprietaryDataBetween(String value1, String value2) {
            addCriterion("smdp_proprietary_data between", value1, value2, "smdpProprietaryData");
            return (Criteria) this;
        }

        public Criteria andSmdpProprietaryDataNotBetween(String value1, String value2) {
            addCriterion("smdp_proprietary_data not between", value1, value2, "smdpProprietaryData");
            return (Criteria) this;
        }

        public Criteria andPprIsNull() {
            addCriterion("PPR is null");
            return (Criteria) this;
        }

        public Criteria andPprIsNotNull() {
            addCriterion("PPR is not null");
            return (Criteria) this;
        }

        public Criteria andPprEqualTo(String value) {
            addCriterion("PPR =", value, "ppr");
            return (Criteria) this;
        }

        public Criteria andPprNotEqualTo(String value) {
            addCriterion("PPR <>", value, "ppr");
            return (Criteria) this;
        }

        public Criteria andPprGreaterThan(String value) {
            addCriterion("PPR >", value, "ppr");
            return (Criteria) this;
        }

        public Criteria andPprGreaterThanOrEqualTo(String value) {
            addCriterion("PPR >=", value, "ppr");
            return (Criteria) this;
        }

        public Criteria andPprLessThan(String value) {
            addCriterion("PPR <", value, "ppr");
            return (Criteria) this;
        }

        public Criteria andPprLessThanOrEqualTo(String value) {
            addCriterion("PPR <=", value, "ppr");
            return (Criteria) this;
        }

        public Criteria andPprLike(String value) {
            addCriterion("PPR like", value, "ppr");
            return (Criteria) this;
        }

        public Criteria andPprNotLike(String value) {
            addCriterion("PPR not like", value, "ppr");
            return (Criteria) this;
        }

        public Criteria andPprIn(List<String> values) {
            addCriterion("PPR in", values, "ppr");
            return (Criteria) this;
        }

        public Criteria andPprNotIn(List<String> values) {
            addCriterion("PPR not in", values, "ppr");
            return (Criteria) this;
        }

        public Criteria andPprBetween(String value1, String value2) {
            addCriterion("PPR between", value1, value2, "ppr");
            return (Criteria) this;
        }

        public Criteria andPprNotBetween(String value1, String value2) {
            addCriterion("PPR not between", value1, value2, "ppr");
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

        public Criteria andHashCcIsNull() {
            addCriterion("hash_cc is null");
            return (Criteria) this;
        }

        public Criteria andHashCcIsNotNull() {
            addCriterion("hash_cc is not null");
            return (Criteria) this;
        }

        public Criteria andHashCcEqualTo(String value) {
            addCriterion("hash_cc =", value, "hashCc");
            return (Criteria) this;
        }

        public Criteria andHashCcNotEqualTo(String value) {
            addCriterion("hash_cc <>", value, "hashCc");
            return (Criteria) this;
        }

        public Criteria andHashCcGreaterThan(String value) {
            addCriterion("hash_cc >", value, "hashCc");
            return (Criteria) this;
        }

        public Criteria andHashCcGreaterThanOrEqualTo(String value) {
            addCriterion("hash_cc >=", value, "hashCc");
            return (Criteria) this;
        }

        public Criteria andHashCcLessThan(String value) {
            addCriterion("hash_cc <", value, "hashCc");
            return (Criteria) this;
        }

        public Criteria andHashCcLessThanOrEqualTo(String value) {
            addCriterion("hash_cc <=", value, "hashCc");
            return (Criteria) this;
        }

        public Criteria andHashCcLike(String value) {
            addCriterion("hash_cc like", value, "hashCc");
            return (Criteria) this;
        }

        public Criteria andHashCcNotLike(String value) {
            addCriterion("hash_cc not like", value, "hashCc");
            return (Criteria) this;
        }

        public Criteria andHashCcIn(List<String> values) {
            addCriterion("hash_cc in", values, "hashCc");
            return (Criteria) this;
        }

        public Criteria andHashCcNotIn(List<String> values) {
            addCriterion("hash_cc not in", values, "hashCc");
            return (Criteria) this;
        }

        public Criteria andHashCcBetween(String value1, String value2) {
            addCriterion("hash_cc between", value1, value2, "hashCc");
            return (Criteria) this;
        }

        public Criteria andHashCcNotBetween(String value1, String value2) {
            addCriterion("hash_cc not between", value1, value2, "hashCc");
            return (Criteria) this;
        }

        public Criteria andMatchingIdIsNull() {
            addCriterion("matching_id is null");
            return (Criteria) this;
        }

        public Criteria andMatchingIdIsNotNull() {
            addCriterion("matching_id is not null");
            return (Criteria) this;
        }

        public Criteria andMatchingIdEqualTo(String value) {
            addCriterion("matching_id =", value, "matchingId");
            return (Criteria) this;
        }

        public Criteria andMatchingIdNotEqualTo(String value) {
            addCriterion("matching_id <>", value, "matchingId");
            return (Criteria) this;
        }

        public Criteria andMatchingIdGreaterThan(String value) {
            addCriterion("matching_id >", value, "matchingId");
            return (Criteria) this;
        }

        public Criteria andMatchingIdGreaterThanOrEqualTo(String value) {
            addCriterion("matching_id >=", value, "matchingId");
            return (Criteria) this;
        }

        public Criteria andMatchingIdLessThan(String value) {
            addCriterion("matching_id <", value, "matchingId");
            return (Criteria) this;
        }

        public Criteria andMatchingIdLessThanOrEqualTo(String value) {
            addCriterion("matching_id <=", value, "matchingId");
            return (Criteria) this;
        }

        public Criteria andMatchingIdLike(String value) {
            addCriterion("matching_id like", value, "matchingId");
            return (Criteria) this;
        }

        public Criteria andMatchingIdNotLike(String value) {
            addCriterion("matching_id not like", value, "matchingId");
            return (Criteria) this;
        }

        public Criteria andMatchingIdIn(List<String> values) {
            addCriterion("matching_id in", values, "matchingId");
            return (Criteria) this;
        }

        public Criteria andMatchingIdNotIn(List<String> values) {
            addCriterion("matching_id not in", values, "matchingId");
            return (Criteria) this;
        }

        public Criteria andMatchingIdBetween(String value1, String value2) {
            addCriterion("matching_id between", value1, value2, "matchingId");
            return (Criteria) this;
        }

        public Criteria andMatchingIdNotBetween(String value1, String value2) {
            addCriterion("matching_id not between", value1, value2, "matchingId");
            return (Criteria) this;
        }

        public Criteria andMsisdnIsNull() {
            addCriterion("msisdn is null");
            return (Criteria) this;
        }

        public Criteria andMsisdnIsNotNull() {
            addCriterion("msisdn is not null");
            return (Criteria) this;
        }

        public Criteria andMsisdnEqualTo(String value) {
            addCriterion("msisdn =", value, "msisdn");
            return (Criteria) this;
        }

        public Criteria andMsisdnNotEqualTo(String value) {
            addCriterion("msisdn <>", value, "msisdn");
            return (Criteria) this;
        }

        public Criteria andMsisdnGreaterThan(String value) {
            addCriterion("msisdn >", value, "msisdn");
            return (Criteria) this;
        }

        public Criteria andMsisdnGreaterThanOrEqualTo(String value) {
            addCriterion("msisdn >=", value, "msisdn");
            return (Criteria) this;
        }

        public Criteria andMsisdnLessThan(String value) {
            addCriterion("msisdn <", value, "msisdn");
            return (Criteria) this;
        }

        public Criteria andMsisdnLessThanOrEqualTo(String value) {
            addCriterion("msisdn <=", value, "msisdn");
            return (Criteria) this;
        }

        public Criteria andMsisdnLike(String value) {
            addCriterion("msisdn like", value, "msisdn");
            return (Criteria) this;
        }

        public Criteria andMsisdnNotLike(String value) {
            addCriterion("msisdn not like", value, "msisdn");
            return (Criteria) this;
        }

        public Criteria andMsisdnIn(List<String> values) {
            addCriterion("msisdn in", values, "msisdn");
            return (Criteria) this;
        }

        public Criteria andMsisdnNotIn(List<String> values) {
            addCriterion("msisdn not in", values, "msisdn");
            return (Criteria) this;
        }

        public Criteria andMsisdnBetween(String value1, String value2) {
            addCriterion("msisdn between", value1, value2, "msisdn");
            return (Criteria) this;
        }

        public Criteria andMsisdnNotBetween(String value1, String value2) {
            addCriterion("msisdn not between", value1, value2, "msisdn");
            return (Criteria) this;
        }

        public Criteria andLockVersionIsNull() {
            addCriterion("lock_version is null");
            return (Criteria) this;
        }

        public Criteria andLockVersionIsNotNull() {
            addCriterion("lock_version is not null");
            return (Criteria) this;
        }

        public Criteria andLockVersionEqualTo(String value) {
            addCriterion("lock_version =", value, "lockVersion");
            return (Criteria) this;
        }

        public Criteria andLockVersionNotEqualTo(String value) {
            addCriterion("lock_version <>", value, "lockVersion");
            return (Criteria) this;
        }

        public Criteria andLockVersionGreaterThan(String value) {
            addCriterion("lock_version >", value, "lockVersion");
            return (Criteria) this;
        }

        public Criteria andLockVersionGreaterThanOrEqualTo(String value) {
            addCriterion("lock_version >=", value, "lockVersion");
            return (Criteria) this;
        }

        public Criteria andLockVersionLessThan(String value) {
            addCriterion("lock_version <", value, "lockVersion");
            return (Criteria) this;
        }

        public Criteria andLockVersionLessThanOrEqualTo(String value) {
            addCriterion("lock_version <=", value, "lockVersion");
            return (Criteria) this;
        }

        public Criteria andLockVersionLike(String value) {
            addCriterion("lock_version like", value, "lockVersion");
            return (Criteria) this;
        }

        public Criteria andLockVersionNotLike(String value) {
            addCriterion("lock_version not like", value, "lockVersion");
            return (Criteria) this;
        }

        public Criteria andLockVersionIn(List<String> values) {
            addCriterion("lock_version in", values, "lockVersion");
            return (Criteria) this;
        }

        public Criteria andLockVersionNotIn(List<String> values) {
            addCriterion("lock_version not in", values, "lockVersion");
            return (Criteria) this;
        }

        public Criteria andLockVersionBetween(String value1, String value2) {
            addCriterion("lock_version between", value1, value2, "lockVersion");
            return (Criteria) this;
        }

        public Criteria andLockVersionNotBetween(String value1, String value2) {
            addCriterion("lock_version not between", value1, value2, "lockVersion");
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