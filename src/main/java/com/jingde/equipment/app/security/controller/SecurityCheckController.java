package com.jingde.equipment.app.security.controller;

import com.jingde.equipment.annotation.LoginRequired;
import com.jingde.equipment.app.security.service.SignLogService;
import com.jingde.equipment.app.security.vo.SignLogVO;
import com.jingde.equipment.core.Result;
import com.jingde.equipment.core.ResultGenerator;
import com.jingde.equipment.model.SignLog;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Goldrepo on 2019/2/28
 */
@RestController
@RequestMapping("/api/securityCheck")
public class SecurityCheckController {

    @Resource
    private SignLogService signLogService;

    /**
     * 安全检查列表/详情
     * securityCheck
     * @param page
     * @param size
     * @return
     */
    @GetMapping()
	@LoginRequired
    public Result logList(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size,@RequestParam(value = "id",required = false) Integer id) {
       if(null == id){
		   Result result = signLogService.findSignList(page,size);
		   return result;
	   }else {
		   SignLogVO info = signLogService.findSignById(id);
		   return ResultGenerator.genSuccessResult(info);
	   }
    }

    /**
     * 保存检查
     * securityCheck/save
     * @param form
     * @return
     */
    @PostMapping()
	@LoginRequired
    public Result save(@RequestBody SignLogVO form) {
    	//获取当前时间
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time= sdf.format( new Date());
        SignLog signLog = new SignLog();
        signLog.setCheckDepartment(form.getInspectUnit());
        signLog.setCheckName(form.getInspectors());
        if(null != form.getInspectDate()){
			signLog.setCheckDate(form.getInspectDate());
		}else {
			signLog.setCheckDate(time);
		}
		if(null != form.getRectificationDate()){
			signLog.setSubmitDate(form.getRectificationDate());
		}else {
			signLog.setSubmitDate(time);
		}
        signLog.setDepartment(form.getBeInspectUnit());
        signLog.setSignName(form.getPersonInCharge());
        signLog.setProblem(form.getDescription());
        signLog.setToDepartment(form.getReportUnit());
        signLog.setContent(form.getList());
		signLog.setProductDepartment(form.getProductUnit());
   		signLog.setId(null);
		signLogService.save(signLog);
        return ResultGenerator.genSuccessResult();
    }


	/**
	 * 修改检查
	 * securityCheck/save
	 * @param form
	 * @return
	 */
	@PutMapping()
	@LoginRequired
	public Result update(@RequestBody SignLogVO form) {
		if(null == form.getId()) return ResultGenerator.genNoContentResult("Id异常！！！");
		//获取当前时间
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time= sdf.format( new Date());
		SignLog signLog = new SignLog();
		signLog.setCheckDepartment(form.getInspectUnit());
		signLog.setCheckName(form.getInspectors());
		if(null == form.getInspectDate()){
			signLog.setCheckDate(form.getInspectDate());
		}else {
			signLog.setCheckDate(time);
		}
		if(null == form.getRectificationDate()){
			signLog.setSubmitDate(form.getRectificationDate());
		}else {
			signLog.setSubmitDate(time);
		}
		signLog.setDepartment(form.getBeInspectUnit());
		signLog.setSignName(form.getPersonInCharge());
		signLog.setProblem(form.getDescription());
		signLog.setToDepartment(form.getReportUnit());
		signLog.setContent(form.getList());
		signLog.setProductDepartment(form.getProductUnit());
		signLog.setId(form.getId());
		signLogService.saveOrUpdate(signLog);
		return ResultGenerator.genSuccessResult();
	}

	/**
	 * 安全检查删除（批量）
	 * @param ids
	 * @return
	 */
	@DeleteMapping()
	@LoginRequired
	public Result delete(@RequestParam(value = "ids") String ids) {
		if(StringUtils.isBlank(ids)) return ResultGenerator.genNoContentResult("至少选择一个对象！！！");
		signLogService.removeById(ids);
		return ResultGenerator.genSuccessResult();
	}
}
