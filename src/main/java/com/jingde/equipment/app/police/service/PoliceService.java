package com.jingde.equipment.app.police.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jingde.equipment.app.police.dto.PoliceFormDTO;
import com.jingde.equipment.app.user.vo.UserSonVO;
import com.jingde.equipment.core.Result;
import com.jingde.equipment.app.police.dto.PoliceDTO;
import com.jingde.equipment.model.Department;
import com.jingde.equipment.model.Police;
import com.jingde.equipment.app.system.vo.DepartmentPoliceVO;
import com.jingde.equipment.app.system.vo.PoliceInfoVO;
import com.jingde.equipment.app.system.vo.PolicePageVO;
import com.jingde.equipment.app.system.vo.UserTypeVO;

import java.util.List;

/**
 * @author
 */
public interface PoliceService extends IService<Police> {

    List<PoliceInfoVO> selectListByPage(PoliceFormDTO form);

    List<DepartmentPoliceVO> findBydepartmentId(Integer id);

    Result add(PoliceDTO form);

    Result delete(Integer id);

    Police findByPoliceCode(String policeCode,String policeName);

    List<PolicePageVO> findBydeptId(String deptId);

    List<Police> select(String policeName,String policeCode);

    void updateUseGunStatus(String id,Integer status);

    List<UserTypeVO> selectType(String policeCode, String policeName, Integer type);

    void updateUseGunStatusByPeuId(String ids, Integer status);

    void updateDepartmentByDeptId(Department dept);

    PoliceInfoVO findInfoById(Integer id);

    UserSonVO selectSonAccount(String policeCode);

    Result updatePolice(PoliceDTO form);

	void importPolice(List<PoliceDTO> list);
}
