package com.jingde.equipment.app.system.vo;

/**
 * @Description: com.jingde.equipment.app.system.vo
 * @Title: Permission
 * @Auther: CzSix
 * @create 2019/3/25 14:38
 */
public class PermissionVO {
    private String id;
    private String permissionId;
    private String permissionName;
    private String permissionUrl;
    private int sort;
    private String permissionNote;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
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
}
