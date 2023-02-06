package com.jingde.equipment.app.base.dto;

import lombok.*;

/**
 * Created by oceanover on 2019-03-18.
 * @author
 */
@Data
public class PageDTO {
    /**
     * 页码
     */
    private Integer page;
    /**
     * 每页大小
     */
    private Integer size;

}
