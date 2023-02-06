package com.jingde.equipment.app.firearms.dto;

/**
 * Created by Goldrepo on 2019/2/28
 */
public class FirearmsEquipmentDTO {

    private String id;
    private String issueDate; //配发时间
    private String firearmsType; //枪支类型
    private String firearmsNo; //枪支号码(跟枪一对一)
    private String firearmsCode; //枪证号码
    private String managerStatus; //管理状态(登记状态):新建登记状态是0---0(待审核),1(审核通过),2(审核未通过),3()
    private String usedLog; //痕迹建档
    private String changeResult; //变动情况
    private String note; //备注
    private String managerPerson; //登记人(当前登录用户)
    private String auditingPerson; //审核人
    private String auditingPersonId; //审核人id(当前登录的用户)
    private String auditingDate; //审核时间
    private String status; //审核状态
	private String[] record; //配备记录id列表
    private String password; //审核人密码
	private String account;  //审核人账号

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getFirearmsType() {
        return firearmsType;
    }

    public void setFirearmsType(String firearmsType) {
        this.firearmsType = firearmsType;
    }

    public String getFirearmsNo() {
        return firearmsNo;
    }

    public void setFirearmsNo(String firearmsNo) {
        this.firearmsNo = firearmsNo;
    }

    public String getFirearmsCode() {
        return firearmsCode;
    }

    public void setFirearmsCode(String firearmsCode) {
        this.firearmsCode = firearmsCode;
    }

    public String getManagerStatus() {
        return managerStatus;
    }

    public void setManagerStatus(String managerStatus) {
        this.managerStatus = managerStatus;
    }

    public String getUsedLog() {
        return usedLog;
    }

    public void setUsedLog(String usedLog) {
        this.usedLog = usedLog;
    }

    public String getChangeResult() {
        return changeResult;
    }

    public void setChangeResult(String changeResult) {
        this.changeResult = changeResult;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getManagerPerson() {
        return managerPerson;
    }

    public void setManagerPerson(String managerPerson) {
        this.managerPerson = managerPerson;
    }

    public String getAuditingPerson() {
        return auditingPerson;
    }

    public void setAuditingPerson(String auditingPerson) {
        this.auditingPerson = auditingPerson;
    }

    public String getAuditingDate() {
        return auditingDate;
    }

    public void setAuditingDate(String auditingDate) {
        this.auditingDate = auditingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	public String[] getRecord() {
		return record;
	}

	public void setRecord(String[] record) {
		this.record = record;
	}

	public String getAuditingPersonId() {
        return auditingPersonId;
    }

    public void setAuditingPersonId(String auditingPersonId) {
        this.auditingPersonId = auditingPersonId;
    }
}
