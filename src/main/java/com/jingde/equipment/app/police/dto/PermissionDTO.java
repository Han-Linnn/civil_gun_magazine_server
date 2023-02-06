package com.jingde.equipment.app.police.dto;

import java.util.List;

/**
 * @Description: com.jingde.equipment.app.system.vo
 * @Title: Permission
 * @Auther: CzSix
 * @create 2019/3/25 14:38
 */
public class PermissionDTO {
    private String id;
    private String permissionName;
    private String permissionUrl;
    private int sort;
    private String permissionNote;
    private List<String> permissionUrls;
    private String policeName;
    private String policeCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionUrl() {
        return permissionUrl;
    }

    public void setPermissionUrl(String permissionUrl) {
        this.permissionUrl = permissionUrl;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getPermissionNote() {
        return permissionNote;
    }

    public void setPermissionNote(String permissionNote) {
        this.permissionNote = permissionNote;
    }

    public List<String> getPermissionUrls() {
        return permissionUrls;
    }

    public void setPermissionUrls(List<String> permissionUrls) {
        this.permissionUrls = permissionUrls;
    }

    public String getPoliceName() {
        return policeName;
    }

    public void setPoliceName(String policeName) {
        this.policeName = policeName;
    }

    public String getPoliceCode() {
        return policeCode;
    }

    public void setPoliceCode(String policeCode) {
        this.policeCode = policeCode;
    }
}
