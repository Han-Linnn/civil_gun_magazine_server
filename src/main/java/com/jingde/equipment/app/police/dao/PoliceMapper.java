package com.jingde.equipment.app.police.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jingde.equipment.app.police.dto.PoliceFormDTO;
import com.jingde.equipment.app.system.vo.UserTypeVO;
import com.jingde.equipment.app.police.dto.PoliceDTO;
import com.jingde.equipment.model.Department;
import com.jingde.equipment.model.Police;
import com.jingde.equipment.app.system.vo.DepartmentPoliceVO;
import com.jingde.equipment.app.system.vo.PoliceInfoVO;
import com.jingde.equipment.app.system.vo.PolicePageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author
 */
public interface PoliceMapper extends BaseMapper<Police> {

    List<PoliceInfoVO> selectListByPage(@Param("condition") PoliceFormDTO condition);

    Police selectByPoliceCodeOrId(@Param("policeCode") String policeCode,@Param("id") String id);

    void add(@Param("condition") PoliceDTO condition);

    Police selectByPoliceCode(@Param("policeCode") String policeCode,@Param("policeName")String policeName);

    List<PolicePageVO> selectByDepartmentId(@Param("departId")String departId);

    List<Police> selectByNameOrCode(@Param("policeName")String policeName, @Param("policeCode")String policeCode);

    void updateUseGunStatus(@Param("id")String id,@Param("status")Integer status);

    List<UserTypeVO> selectUseGunPoliceBypoliceName(@Param("policeCode")String policeCode, @Param("policeName")String policeName);

    void updateUseGunStatusByPeuId(@Param("ids")String[] ids,@Param("status")Integer status,@Param("useStatus")Integer useStatus);

    void updateDepartmentByDeptId(@Param("dept")Department dept);

    List<DepartmentPoliceVO> findBydepartmentId(@Param("id") Integer id);

    PoliceInfoVO findInfoById(@Param("id") Integer id);
    
    void updatePolice(@Param("condition") PoliceDTO form);

}
