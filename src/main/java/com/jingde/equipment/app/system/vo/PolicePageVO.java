package com.jingde.equipment.app.system.vo;

/**
 * 配枪人员列表信息表(持枪人员列表/禁枪人员列表)
 * @Description: com.jingde.equipment.app.system.vo
 * @Title: PoliceVo
 * @Auther: CzSix
 * @create 2019/3/7 16:33
 */
public class PolicePageVO {

    private String id; //配枪人员id
    private String policeName; //姓名
    private String sex; //性别
    private String age; //年龄
    private String nation; //民族
    private String politicalStatus; //政治面貌
    private String leaderLevel; //职位(级别)
    private String departmentName; //部门
    private String policeCode; //警号
    private String address; //家庭地址
    private String mobile; ///联系方式
    private String firearmCode; //枪证号
    private String firearmCodePublish; //发证日期
    private String annualVerification; //年审情况
    private String reason; //禁枪原因/配枪不良记录
    private String date; //不良记录时间
    private String groupName; //人员所属的组

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPoliceName() {
        return policeName;
    }

    public void setPoliceName(String policeName) {
        this.policeName = policeName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getPoliticalStatus() {
        return politicalStatus;
    }

    public void setPoliticalStatus(String politicalStatus) {
        this.politicalStatus = politicalStatus;
    }

    public String getPoliceCode() {
        return policeCode;
    }

    public void setPoliceCode(String policeCode) {
        this.policeCode = policeCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFirearmCode() {
        return firearmCode;
    }

    public void setFirearmCode(String firearmCode) {
        this.firearmCode = firearmCode;
    }

    public String getFirearmCodePublish() {
        return firearmCodePublish;
    }

    public void setFirearmCodePublish(String firearmCodePublish) {
        this.firearmCodePublish = firearmCodePublish;
    }

    public String getAnnualVerification() {
        return annualVerification;
    }

    public void setAnnualVerification(String annualVerification) {
        this.annualVerification = annualVerification;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLeaderLevel() {
        return leaderLevel;
    }

    public void setLeaderLevel(String leaderLevel) {
        this.leaderLevel = leaderLevel;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
