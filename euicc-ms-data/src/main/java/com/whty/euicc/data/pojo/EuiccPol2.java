package com.whty.euicc.data.pojo;

public class EuiccPol2 {
    private String pol2Id;

    private String subject;

    private String action;

    private String qualification;

    public String getPol2Id() {
        return pol2Id;
    }

    public void setPol2Id(String pol2Id) {
        this.pol2Id = pol2Id == null ? null : pol2Id.trim();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject == null ? null : subject.trim();
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action == null ? null : action.trim();
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification == null ? null : qualification.trim();
    }
}