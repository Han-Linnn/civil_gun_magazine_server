package com.jingde.equipment.app.department.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jingde.equipment.app.department.dao.DepartmentMapper;
import com.jingde.equipment.app.department.service.DepartmentService;
import com.jingde.equipment.app.police.dao.PoliceMapper;
import com.jingde.equipment.app.system.vo.DepartmentVO;
import com.jingde.equipment.app.system.vo.PolicePageVO;
import com.jingde.equipment.model.Department;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Description: com.jingde.equipment.app.system.service.impl
 * @Title: DepartmentServiceImpl
 * @Auther: CzSix
 * @create 2019/3/14 16:47
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

    private final DepartmentMapper departmentMapper;

    private final PoliceMapper policeMapper;

    @Override
    public PageInfo departmentPage(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size, DepartmentVO departmentVO) {
        PageHelper.startPage(page, size);
        List<Department> list = departmentMapper.departmentPage(departmentVO);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public DepartmentVO departmentInfo(Integer id) {
        DepartmentVO vo;
        vo = departmentMapper.findById(id);
        if (null != vo) {
            //获取部门的中的人员信息
            List<PolicePageVO> list = policeMapper.selectByDepartmentId(vo.getId());
            vo.setList(list);
        } else {
            vo = new DepartmentVO();
        }
        return vo;
    }

	@Override
	public String selectIdByName(String name) {
		return departmentMapper.selectIdByName(name);
	}

}
