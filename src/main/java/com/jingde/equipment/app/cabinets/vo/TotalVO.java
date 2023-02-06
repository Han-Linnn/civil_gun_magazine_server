package com.jingde.equipment.app.cabinets.vo;

import lombok.Data;

import java.util.List;

/**
 * @Description: com.jingde.equipment.app.system.vo
 * @Title: LableVO
 * @Auther: CzSix
 * @create 2019/3/12 10:03
 */
@Data
public class TotalVO {
    private String id;
    private String type;
    private int count;
    private List<LableVO> list;


}
