package com.jingde.equipment.app.apply.vo;

import com.jingde.equipment.model.AmmoReturnBatchInfo;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 申请类型记录表
 * </p>
 *
 * @author jingde
 * @since 2020-07-07
 */
@Data
public class FirearmsReturnBatchVo {


    private Integer id;

    /**
     * 申请记录id
     */
    private Integer applyId;

    /**
     * 保管人 id
     */
    private Integer keepingPersonId;

    /**
     * 保管人
     */
    private String keepingPerson;

    /**
     * 保管人2 id
     */
    private Integer keepingPerson2Id;

    /**
     * 保管人
     */
    private String keepingPerson2;

    /**
     * (还枪)确认人id
     */
    private Integer confirmPersonId;

    /**
     * (还枪)确认人
     */
    private String confirmPerson;

    /**
     * 创建时间
     */
    private String createTime;

    private List<FirearmsApplyTypeInfoVo> firearmsList;

    private List<AmmoReturnBatchInfo> ammoList;

}
