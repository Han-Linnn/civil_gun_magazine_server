package com.jingde.equipment.app.system.dto;

import java.util.List;

/**
 * Created by Goldrepo on 2019/2/28
 */
public class GroupUserDTO {
    private Integer id;
    private List<Integer> userIds;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Integer> userIds) {
        this.userIds = userIds;
    }
}
