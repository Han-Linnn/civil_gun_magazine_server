package com.jingde.equipment.app.department.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.jingde.equipment.model.Department;
import com.jingde.equipment.app.system.vo.DepartmentVO;
import org.apache.ibatis.annotations.Param;

/**
 * @author
 */
public interface DepartmentService extends IService<Department> {

    PageInfo departmentPage(Integer page, Integer size, DepartmentVO departmentVO);

    DepartmentVO departmentInfo(Integer id);

	String selectIdByName(String replaceAll);
}
