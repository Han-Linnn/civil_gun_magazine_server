package com.jingde.equipment.app.police.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jingde.equipment.annotation.LoginRequired;
import com.jingde.equipment.app.department.service.DepartmentService;
import com.jingde.equipment.app.police.dto.PoliceDTO;
import com.jingde.equipment.app.police.dto.PoliceFormDTO;
import com.jingde.equipment.app.police.service.PoliceService;
import com.jingde.equipment.app.system.vo.DepartmentPoliceVO;
import com.jingde.equipment.app.system.vo.PoliceInfoVO;
import com.jingde.equipment.app.system.vo.UserTypeVO;
import com.jingde.equipment.app.user.dao.UserMapper;
import com.jingde.equipment.app.user.service.UserService;
import com.jingde.equipment.core.Result;
import com.jingde.equipment.core.ResultGenerator;
import com.jingde.equipment.model.Police;
import com.jingde.equipment.model.User;
import com.jingde.equipment.util.ExcelExportUtil;
import com.jingde.equipment.util.ExcelImportUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 人员管理
 *
 * @author
 */
@RestController
@RequestMapping("/api/police")
public class PoliceController {
    @Resource
    private PoliceService policeService;

    @Resource
    private DepartmentService departmentService;

    @Resource
    private UserService userService;

    @Resource
    private UserMapper userMapper;

	/**
	 * 人员信息批量导入
	 * @param file
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value = "/import",method = RequestMethod.POST)
    public Result importPolice(@RequestParam(name = "file")MultipartFile file) throws Exception{
		List<PoliceDTO> list = new ExcelImportUtil<PoliceDTO>(PoliceDTO.class).readExcel(file.getInputStream(),2,0);
		if(null == list || list.size()==0){
			return ResultGenerator.genSuccessResult();
		}
		policeService.importPolice(list);
		return ResultGenerator.genSuccessResult();
	}

	/**
	 * 人员信息批量导入模板下载
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/template/export",method = RequestMethod.GET)
	public void PoliceTemplateExport(HttpServletResponse response) throws Exception {
		InputStream fis = this.getClass().getResourceAsStream("/excel-template/人员批量导入模板.xlsx");
		new ExcelExportUtil(String.class,2,0).export(response,fis,new ArrayList<String>(),"人员批量导入模板.xlsx");

	}

    /**
     * 人员管理列表
     * police/page
     *
     * @param
     * @return
     */
    @LoginRequired
    @GetMapping("/page")
    public Result selectListByPage(PoliceFormDTO form) {
        form.setPage(form.getPage() == null ? 0 : form.getPage());
        form.setSize(form.getSize() == null ? 10 : form.getSize());
        PageHelper.startPage(form.getPage(), form.getSize());
        List<PoliceInfoVO> list = policeService.selectListByPage(form);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 部门人员管理列表
     * police/department/page
     *
     * @param page
     * @param size
     * @param id   部门id
     * @return
     */
    @LoginRequired
    @GetMapping("/department/page")
    public Result departmentpoliceListByPage(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size, Integer id) {
        PageHelper.startPage(page, size);
        if (null == id) {
            return ResultGenerator.genFailResult("请选择部门");
        }
        List<DepartmentPoliceVO> list = policeService.findBydepartmentId(id);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 人员筛选列表
     * police/select
     *
     * @param
     * @param
     * @return
     */
    @LoginRequired
    @GetMapping("/select")
    public Result select(String policeName, String policeCode) {
        List<Police> list = policeService.select(policeName, policeCode);
        return ResultGenerator.genSuccessResult(list);
    }

    /**
     * 人员类型筛选列表(审批人员列表/保养人员列表/用枪人员列表/被授权人列表)
     * police/selectType
     *
     * @param
     * @param
     * @return
     */
    @LoginRequired
    @GetMapping("/selectType")
    public Result selectType(String policeCode, String policeName, @RequestParam(defaultValue = "0") Integer type) {
        List<UserTypeVO> list = policeService.selectType(policeCode, policeName, type);
        return ResultGenerator.genSuccessResult(list);
    }

    /**
     * 人员添加
     * police/info
     *
     * @param form
     * @param
     * @return
     */
    @LoginRequired
    @PostMapping("/info")
    public Result add(@RequestBody PoliceDTO form) {

        policeService.add(form);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 人员删除
     * /police/info
     *
     * @param id
     * @return
     */
    @LoginRequired
    @DeleteMapping("/info")
    public Result delete(@RequestParam("id") String id) {
        if (StringUtils.isNotBlank(id)) {
            policeService.removeById(id);
            //删除该警员的用户
            userService.deleteByPoliceIds(id);
        } else {
            return ResultGenerator.genFailResult("id为空");
        }
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 人员更新
     * /police/info
     *
     * @param form
     * @return
     */
    @LoginRequired
    @PutMapping("/info")
    public Result update(@RequestBody PoliceDTO form) {
        policeService.updatePolice(form);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 人员详情
     * police/info
     *
     * @param id
     * @return
     */
    @LoginRequired
    @GetMapping("/info")
    public Result detail(Integer id) {
        PoliceInfoVO info = policeService.findInfoById(id);
        return ResultGenerator.genSuccessResult(info);
    }

    /**
     * 通过警号获取警员信息
     * police/info?policeCode=0909909
     *
     * @param
     * @return
     */
    @LoginRequired
    @GetMapping("/info/code")
    public Result findByPoliceCode(String policeCode,String policeName) {
        Police police = policeService.findByPoliceCode(policeCode,policeName);
        return ResultGenerator.genSuccessResult(police);
    }

    /**
     * 更新人员用枪状态
     */
    @LoginRequired
    @PostMapping("/info/updateStatus")
    public Result updateUseGunStatus(@RequestParam String id, Integer status) {
        policeService.updateUseGunStatus(id, status);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 获取所有有效的用户
     */
    @LoginRequired
    @GetMapping("/list")
    public Result getAllPoliceList(Integer type) {

        List<User> list = userService.listByCondition(type);
        return ResultGenerator.genSuccessResult(list);
    }
}
