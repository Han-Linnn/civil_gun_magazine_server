package com.jingde.equipment.app.system.vo;

import java.util.List;

/**
 * @Description: com.jingde.equipment.app.system.vo
 * @Title: DepartmentVO
 * @Auther: CzSix
 * @create 2019/3/14 17:01
 */
public class DepartmentVO {
    private String id;
    private String departmentName;
    private List<PolicePageVO> list; //部门人员信息
    private String status;
    private String note;
    private String createTime;
    private String updateTime;
    private String parentId; //父级职务id
    private String departmentId; //部门id
    private String leaderLevelId; //级别id
    private String leaderLevel; //级别名称

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public List<PolicePageVO> getList() {
        return list;
    }

    public void setList(List<PolicePageVO> list) {
        this.list = list;
    }

    public String getStatus() {
        return status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getLeaderLevelId() {
        return leaderLevelId;
    }

    public void setLeaderLevelId(String leaderLevelId) {
        this.leaderLevelId = leaderLevelId;
    }

    public String getLeaderLevel() {
        return leaderLevel;
    }

    public void setLeaderLevel(String leaderLevel) {
        this.leaderLevel = leaderLevel;
    }
}
