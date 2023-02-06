package com.jingde.equipment.app.department.controller;

import com.github.pagehelper.PageInfo;
import com.jingde.equipment.annotation.LoginRequired;
import com.jingde.equipment.app.department.dto.DepartmentDTO;
import com.jingde.equipment.app.department.service.DepartmentService;
import com.jingde.equipment.app.police.service.PoliceService;
import com.jingde.equipment.app.system.vo.DepartmentVO;
import com.jingde.equipment.core.Result;
import com.jingde.equipment.core.ResultGenerator;
import com.jingde.equipment.model.Department;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 人员管理
 * @Description: com.jingde.equipment.app.system.controller
 * @Title: PoliceController
 * @Auther: CzSix
 * @create 2019/3/8 17:51
 */

@RestController
@RequestMapping("/api/department")
public class DepartmentController {

    @Resource
    private DepartmentService departmentService;

    @Resource
    private PoliceService policeService;
    /**
     * 部门列表
     * /department/page
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/page")
    public Result listByPage(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size,DepartmentVO departmentVO) {
        PageInfo pageInfo = departmentService.departmentPage(page,size,departmentVO);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 部门筛选
     * /department/select
     *
     * @param
     * @param
     * @return
     */
    @GetMapping("/select")
    public Result select() {
        List<Department> list = departmentService.list();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 部门添加
     * /department/info
     *
     * @param form
     * @return
     */
    @PostMapping("/info")
    @LoginRequired
    public Result add(@RequestBody @Validated DepartmentDTO form) {
        Department d = new Department();
        d.setDepartmentName(form.getDepartmentName());
        d.setNote(form.getNote());
        departmentService.save(d);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 部门删除
     * /department/info
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/info")
    @LoginRequired
    public Result delete( @RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            departmentService.removeById(ids);
        } else {
            return ResultGenerator.genFailResult("ids为空");
        }

        return ResultGenerator.genSuccessResult();
    }

    /**
     * 部门更新
     * /department/info
     *
     * @param form
     * @return
     */
    @PutMapping("/info")
    @LoginRequired
    public Result update(@RequestBody DepartmentDTO form) {
        if (null == form.getId() || 0 == form.getId()) {
            return ResultGenerator.genFailResult("id为空");
        }
        Department dept = new Department();
        dept.setId(form.getId());
        dept.setDepartmentName(form.getDepartmentName());
        dept.setNote(form.getNote());

        departmentService.saveOrUpdate(dept);
        //修改人员表中的部门名称
        policeService.updateDepartmentByDeptId(dept);

        return ResultGenerator.genSuccessResult();
    }

    /**
     * 部门详情
     * /department/info/{id}
     *
     * @param id
     * @return
     */
    @GetMapping("/info")
    @LoginRequired
    public Result detail(Integer id) {
        DepartmentVO info = departmentService.departmentInfo(id);
        return ResultGenerator.genSuccessResult(info);
    }

}
