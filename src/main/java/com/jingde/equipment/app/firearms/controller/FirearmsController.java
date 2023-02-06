package com.jingde.equipment.app.firearms.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jingde.equipment.annotation.LoginRequired;
import com.jingde.equipment.app.apply.service.FirearmsApplyLogService;
import com.jingde.equipment.app.apply.vo.FirearmsLogDetailVO;
import com.jingde.equipment.app.apply.vo.FirearmsLogVO;
import com.jingde.equipment.app.firearms.service.FirearmsService;
import com.jingde.equipment.core.Result;
import com.jingde.equipment.core.ResultGenerator;
import com.jingde.equipment.model.Firearms;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @auther XC
 * @create 2020-07-10 17:17
 */
@RestController
@RequestMapping("/api/firearms")
public class FirearmsController {

	@Resource
	private FirearmsService firearmsService;
	@Resource
	private FirearmsApplyLogService firearmsApplyLogService;

	/**
	 * 枪支列表
	 * @param page
	 * @param size
	 * @param condition
	 * @return
	 */
	@LoginRequired
	@GetMapping()
	public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size,Firearms condition){
		PageInfo<Firearms> result = firearmsService.list(page, size, condition);
		return ResultGenerator.genSuccessResult(result);
	}

	/**
	 * 添加枪支
	 * @param firearms
	 * @return
	 */
	@LoginRequired
	@PostMapping()
	public Result add(@RequestBody Firearms firearms){
		boolean result = firearmsService.add(firearms);
		if(result){
			return ResultGenerator.genSuccessResult();
		}else {
			return ResultGenerator.genFailResult("添加失败");
		}
	}

	/**
	 * 修改枪支
	 * @param firearms
	 * @return
	 */
	@LoginRequired
	@PutMapping()
	public Result update(@RequestBody Firearms firearms){
		int result = firearmsService.update(firearms);
		if(1 == result){
			return ResultGenerator.genSuccessResult();
		}else if(0 == result) {
			return ResultGenerator.genFailResult("更新失败，编号已存在！");
		}else {
			return ResultGenerator.genFailResult("更新失败");
		}
	}

	/**
	 * 删除枪支
	 * @param firearms
	 * @return
	 */
	@LoginRequired
	@DeleteMapping()
	public Result delete(Firearms firearms){
		boolean result = firearmsService.delete(firearms);
		if(result){
			return ResultGenerator.genSuccessResult();
		}else {
			return ResultGenerator.genFailResult("删除失败");
		}
	}

	/**
	 *  二维码导出
	 * @param list
	 * @param response
	 */
//	@LoginRequired
	@PostMapping("/export/QrCode")
	public void exportQrCode(@RequestBody List<Map<String, Integer>> list, HttpServletResponse response){
		firearmsService.exportQrCode(list, response);
	}
	
	/**
	 * 枪支扫描录入
	 * @param list
	 * @return
	 */
	@PostMapping(value = "/qrCode/entering")
	public Result qrCodeEntering(@RequestBody List<String> list){
		boolean result = firearmsService.qrCodeEntering(list);
		if(result){
			return ResultGenerator.genSuccessResult();
		}else {
			return ResultGenerator.genFailResult("该录入编号格式有误或已存在！");
		}
	}

	/**
	 * 扫描二维码获取枪支类型信息
	 * @param qrCode
	 * @return
	 */
	@GetMapping(value = "/qrCode/toFireamrs")
	public Result qrCodeToFirearms(@RequestParam(required = true) String qrCode){
		Firearms result = firearmsService.qrCodeToFirearms(qrCode);
		return ResultGenerator.genSuccessResult(result);
	}

	/**
	 * 领用记录列表
	 * @param page
	 * @param size
	 * @return
	 */
	@GetMapping(value = "/get/firearms/log")
	public Result<Object> getFirearmsLog(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size){
		PageHelper.startPage(page, size);
		List<FirearmsLogVO> list = firearmsApplyLogService.getFirearmsLog();
		PageInfo pageInfo = new PageInfo(list);
		return ResultGenerator.genSuccessResult(pageInfo);
	}

	/**
	 * 领用记录详情列表
	 * @param page
	 * @param size
	 * @return
	 */
	@GetMapping(value = "/get/firearms/logDetail/{id}")
	public Result<Object> getFirearmsLogDetail(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size, @PathVariable Integer id) {
		PageHelper.startPage(page, size);
		List<FirearmsLogDetailVO> list = firearmsApplyLogService.getFirearmsLogDetail(id);
		PageInfo pageInfo = new PageInfo(list);
		return ResultGenerator.genSuccessResult(pageInfo);
	}

	 /* *
	 *获取指定时间内的枪支可用列表
	 * url:/api/firearms/apply/list
	 */
	@GetMapping("/apply/list")
	public  Result selectApplyList(String time,Integer typeId){
		if (StringUtils.isBlank(time)) {
			return ResultGenerator.genFailResult("请选择时间");
		}
		List<Firearms> list = firearmsService.selectApplyList(time,typeId);
		return ResultGenerator.genSuccessResult(list);
	}

	/**
	 * 二维码获取枪支详情

	 * @return
	 */
	@LoginRequired
	@GetMapping("/info")
	public  Result findInfoByCode(@RequestParam(required = true)String qrCode){
		Firearms firearms =  firearmsService.findInfoByCode(qrCode);
		return ResultGenerator.genSuccessResult(firearms);
	}

}
