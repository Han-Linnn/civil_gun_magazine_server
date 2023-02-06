package com.jingde.equipment.app.apply.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jingde.equipment.app.record.dto.LableDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
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
public class FirearmsReceiveBatchDTO {

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
     * (领枪)确认人id
     */
    private Integer confirmPersonId;

    /**
     * (领枪)确认人
     */
    private String confirmPerson;
    /**
     * (领枪)确认人密码
     */
    private String confirmPersonPassword;

    /**
     * 保管人(账号+密码验证,或者人脸的工号验证)
     */
    private List<LableDTO> lableList;

    /**
     * 领枪详情
     */
    private List<FirearmsApplyTypeInfoDTO> infos;

    /**
     * 子弹领取情况
     */
    private List<FirearmsApplyTypeLogDTO> types;
}
