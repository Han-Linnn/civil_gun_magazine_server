package com.jingde.equipment.app.department.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jingde.equipment.model.Department;
import com.jingde.equipment.app.system.vo.DepartmentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author
 */
public interface DepartmentMapper extends BaseMapper<Department> {

    List<Department> departmentPage(@Param("condition")DepartmentVO departmentVO);

    DepartmentVO findById(@Param("id") Integer id);

	String selectIdByName(@Param("name") String name);
}
