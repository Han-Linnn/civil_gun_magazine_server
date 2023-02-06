package com.jingde.equipment.app.system.controller;

import com.jingde.equipment.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.jingde.equipment.annotation.LoginRequired;
import com.jingde.equipment.core.Result;
import com.jingde.equipment.core.ResultGenerator;
import com.jingde.equipment.app.system.dto.SysConfigDTO;

import com.jingde.equipment.model.SysConfig;
import com.jingde.equipment.app.system.service.SysConfigService;

import javax.annotation.Resource;

/**
 * @author
 */
@RestController
@RequestMapping("/api/system")
public class SysConfigController {

	@Resource
	private SysConfigService sysConfigService;
	
	//修改系统设置
	@LoginRequired
	@PostMapping("/sysUpdate")
    public Result update(@RequestAttribute("currentUser") User currentUser, @RequestBody SysConfigDTO form) {
 		SysConfig sysConfig = new SysConfig();
 		BeanUtils.copyProperties(form,sysConfig);
        sysConfigService.saveOrUpdate(sysConfig);
        return ResultGenerator.genSuccessResult();
    }

 	//系统设置查询
    @GetMapping("/sysList")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        PageHelper.startPage(page, size);
        SysConfig sysConfig = sysConfigService.getById(1);
        return ResultGenerator.genSuccessResult(sysConfig);
    }
	 	
}
