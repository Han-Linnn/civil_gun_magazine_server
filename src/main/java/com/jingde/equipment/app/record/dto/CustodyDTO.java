package com.jingde.equipment.app.record.dto;

import com.jingde.equipment.app.cabinets.vo.LableVO;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * Created by oceanover on 2019-03-10.
 * 发枪
 *
 * @author
 */
@Data
public class CustodyDTO {
    
    private Integer id;
    private List<Integer> ids;
    // 保管人
    private String keepingPerson;
    // 保管人 id
    private Integer keepingPersonId;
    //确认人id
    private Integer confirmPersonId;
    // 密码
    private String password;
    // 枪型
    private String gunType;
    // 枪号
    private String gunNo;
    // 申领子弹
    private String bullet;
    //保管人签名集合
    private List<LableDTO> list;
}
