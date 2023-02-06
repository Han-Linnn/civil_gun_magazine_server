package com.jingde.equipment.app.police.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jingde.equipment.annotation.LoginRequired;
import com.jingde.equipment.core.Result;
import com.jingde.equipment.core.ResultGenerator;
import com.jingde.equipment.app.police.dto.PoliceAnnualInspectionDTO;
import com.jingde.equipment.model.PoliceAnnualInspection;
import com.jingde.equipment.app.police.service.AnnualInspectionService;
import com.jingde.equipment.app.police.vo.PoliceAnnualInspectionVO;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 人员年检管理
 *
 * @author
 */
//@RestController
//@RequestMapping("/api/annual")
public class AnnualInspectionController {

    @Resource
    private AnnualInspectionService annualInspectionService;

    /**
     * 人员年检列表
     * annual/page
     *
     * @param
     * @param
     * @return
     */
    @LoginRequired
    @GetMapping("/page")
    public Result listByPage(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        PageHelper.startPage(page, size);
        List<PoliceAnnualInspectionVO> list = annualInspectionService.findByPage();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 人员年检添加
     * /annual/info
     *
     * @param
     * @param
     * @return
     */
    @LoginRequired
    @PostMapping("/info")
    public Result add(@RequestBody PoliceAnnualInspectionDTO form) {
        annualInspectionService.add(form);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 人员年检更新
     * /annual/info
     *
     * @param form
     * @return
     */
    @LoginRequired
    @PutMapping("/info")
    public Result update(@RequestBody PoliceAnnualInspectionDTO form) {
        PoliceAnnualInspection info = new PoliceAnnualInspection();
        BeanUtils.copyProperties(form, info);
        annualInspectionService.saveOrUpdate(info);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 年检详情
     * /annual/info
     *
     * @param id
     * @return
     */
    @LoginRequired
    @GetMapping("/info")
    public Result detail(Integer id) {

        PoliceAnnualInspectionVO info = annualInspectionService.findOneById(id);
        return ResultGenerator.genSuccessResult(info);
    }

    /**
     * 通过警员id获取警员年检信息
     * police/info?policeCode=0909909
     *
     * @param
     * @return
     */
    @LoginRequired
    @GetMapping("/info/code")
    public Result findByPoliceId(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size, String policeId,String status) {
        PageHelper.startPage(page, size);
        List<PoliceAnnualInspectionVO> list = annualInspectionService.findByPoliceId(policeId,status);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

}
