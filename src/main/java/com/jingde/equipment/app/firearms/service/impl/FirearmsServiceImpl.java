package com.jingde.equipment.app.firearms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jingde.equipment.app.firearms.dao.FirearmsMapper;
import com.jingde.equipment.app.firearms.service.FirearmsService;
import com.jingde.equipment.app.firearms.service.FirearmsTypeService;
import com.jingde.equipment.core.exception.ServiceException;
import com.jingde.equipment.model.Firearms;
import com.jingde.equipment.model.FirearmsType;
import com.jingde.equipment.util.QrCodeUtils;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @auther XC
 * @create 2020-07-10 17:17
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FirearmsServiceImpl implements FirearmsService{

	private final FirearmsTypeService firearmsTypeService;

	private final FirearmsMapper firearmsMapper;


	@Override
	public Firearms getFirearmsByNo(String firearmNo) {
		Firearms firearms = new Firearms();
		QueryWrapper<Firearms> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("firearm_no",firearmNo);
		Firearms result = firearms.selectOne(queryWrapper);
		return result;
	}

	@Override
	public PageInfo<Firearms> list(Integer page, Integer size, Firearms condition) {
		PageHelper.startPage(page, size);
		Firearms firearms = new Firearms();
		QueryWrapper<Firearms> conveyWayWrapper = new QueryWrapper<>();
		if(null != condition.getFirearmTypeId()){
			conveyWayWrapper
					.eq("firearm_type_id",condition.getFirearmTypeId());
		}
		if( null != condition.getFirearmNo()){
			conveyWayWrapper
					.like("firearm_no", condition.getFirearmNo());
		}
		if(null != condition.getStatus()){
			conveyWayWrapper
					.eq("status", condition.getStatus());
		}
		List<Firearms> list = firearms.selectList(conveyWayWrapper);
		PageInfo<Firearms> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public boolean add(Firearms firearms) {
		boolean insert = firearms.insert();
		return insert;
	}

	@Override
	public int update(Firearms firearms) {
		// 检查枪炮编号是否存在
		QueryWrapper<Firearms> firearmsQueryWrapper = new QueryWrapper<>();
		firearmsQueryWrapper.eq("firearm_no",firearms.getFirearmNo());
		firearmsQueryWrapper.notIn("id", firearms.getId());
		Firearms result = firearms.selectOne(firearmsQueryWrapper);
		if(null != result){
			return 0;
		}
		boolean b = firearms.updateById();
		if(b){
			return 1;
		}
		return 2;
	}

	@Override
	public boolean delete(Firearms firearms) {
		return firearms.deleteById();
	}

	@Override
	public void exportQrCode(List<Map<String, Integer>> list, HttpServletResponse response) {
		try {
			//创建一个workbook
			XSSFWorkbook workbook = new XSSFWorkbook();
			for (int i = 0; i < list.size(); i++) {
				Map<String, Integer> condition = list.get(i);
				Integer firearmTypeId = condition.get("firearmTypeId");
				Integer count = condition.get("count");
				FirearmsType firearmsType = firearmsTypeService.selectById(firearmTypeId);
				if (null == firearmsType){
//					throw new Exception().
				}
				// 获取枪支类型对象
				//添加一个sheet
				XSSFSheet sheet = workbook.createSheet(firearmsType.getFirearmsType());
				//创建表头
				XSSFRow headerRow = sheet.createRow(0);
				XSSFCell headerRowCell1 = headerRow.createCell(0);
				XSSFCell headerRowCell2 = headerRow.createCell(1);
				XSSFCell headerRowCell3 = headerRow.createCell(2);
				XSSFCell headerRowCell4 = headerRow.createCell(3);
				headerRowCell1.setCellValue("分类");
				headerRowCell2.setCellValue("类型");
				headerRowCell3.setCellValue("二维码");
				// 枪号生成规则枪炮分类-枪炮类型-时间戳-传入参数list循环下标-数量下标 例：0-15-1594692498-0-1
				headerRowCell4.setCellValue("枪号");
				// 获取秒级别时间戳
				long time = System.currentTimeMillis() / 1000;
				// 设置列宽
				sheet.setColumnWidth(1, 15 * 256);
				sheet.setColumnWidth(2, 15 * 256);
				sheet.setColumnWidth(3, 23 * 256);
				for (int j = 0; j < count; j++) {
					XSSFRow row = sheet.createRow(j+1);
					//设置行高
					row.setHeightInPoints(70);
					XSSFCell cell0 = row.createCell(0);
					XSSFCell cell1 = row.createCell(1);
					XSSFCell cell3 = row.createCell(3);
					cell0.setCellValue(firearmsType.getType() == 0 ? "枪" : "炮");
					cell1.setCellValue(firearmsType.getFirearmsType());
					StringBuilder firearmNo = new StringBuilder();
					firearmNo.append(firearmsType.getType());
					firearmNo.append("-");
					firearmNo.append(firearmTypeId);
					firearmNo.append("-");
					firearmNo.append(time);
					firearmNo.append("-");
					firearmNo.append(i);
					firearmNo.append("-");
					firearmNo.append(j+1);
					String firearmNoResult = firearmNo.toString();
					cell3.setCellValue(firearmNoResult);
					// 插入二维码
					insertQrCode(firearmNoResult,workbook, sheet, (j+1),2);
				}
				// 输出文件
				try{
					String fileName = URLEncoder.encode("二维码打印模板.xlsx", "UTF-8");
					response.setContentType("application/octet-stream");
					response.setHeader("content-disposition", "attachment;filename=" + new String(fileName.getBytes("ISO8859-1")));
					response.setHeader("filename", fileName);
					workbook.write(response.getOutputStream());
				}catch (Exception e){

				}
			}

		}catch (Exception e){

		}
	}

	@Transactional
	@Override
	public boolean qrCodeEntering(List<String> list) {
		List<Firearms> firearmsList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			String qrCode = list.get(i).replace(" ","");
			Firearms firearms = new Firearms();
			// 检查枪炮编号是否存在
			QueryWrapper<Firearms> firearmsQueryWrapper = new QueryWrapper<>();
			firearmsQueryWrapper.eq("firearm_no",qrCode);
			Firearms result = firearms.selectOne(firearmsQueryWrapper);
			if(null != result){
				throw new  ServiceException("添加失败，编号：" + qrCode + "已经存在!");
			}
			// 例：0-15-1594692498-0-1
			String[] split = list.get(i).split("-");
			if(null == split || split.length < 5){
				throw new ServiceException("编码：" + qrCode + "格式有误!");
			}
			// 查询是否有对应的枪支类型
			FirearmsType firearmsType = firearmsTypeService.selectById(Integer.valueOf(split[1]));
			if(null == firearmsType){
				throw new ServiceException("编码：" + qrCode + "对应的枪支类型不存在!");
			}
			firearms.setFirearmTypeId(firearmsType.getId());
			firearms.setFirearmType(firearmsType.getFirearmsType());
			firearms.setFirearmNo(qrCode);
			firearms.setStatus(0);
			firearmsList.add(firearms);
		}
		if(!CollectionUtils.isEmpty(firearmsList)){
			firearmsMapper.insertFirearmsList(firearmsList);
			return true;
		}else {
			return false;
		}
	}

	@Override
	public int updateFirearmsStatus(List<String> list, Integer status) {
		if(!CollectionUtils.isEmpty(list)){
			Integer result = firearmsMapper.updateFirearmsStatus(list, status,null);
			return result;
		}else {
			return 0;
		}
	}

	@Override
	public List<Firearms> selectApplyList(String time, Integer typeId) {
		List<Firearms> list = firearmsMapper.selectApplyList(time,typeId);
		return list;

	}

	@Override
	public Firearms qrCodeToFirearms(String qrCode) {
		String firearmNo = qrCode.replace(" ","");
		String[] split = qrCode.split("-");
		if(null == split || split.length < 5){
			throw new ServiceException("此二维码格式不正确，请更换二维码！");
		}
		// 获取枪支类型信息
		FirearmsType firearmsType = firearmsTypeService.selectById(Integer.valueOf(split[1]));
		if(null == firearmsType){
			throw new ServiceException("此二维码没有对应的枪支类型!");
		}
		Firearms firearms = new Firearms();
		firearms.setFirearmTypeId(firearmsType.getId());
		firearms.setFirearmType(firearmsType.getFirearmsType());
		firearms.setFirearmNo(qrCode);
		return firearms;
	}

	@Override
	public Firearms findInfoByCode(String qrCode) {
		Firearms firearms = firearmsMapper.findInfoByCode(qrCode);
		return firearms;
	}

	private void insertQrCode(String context, XSSFWorkbook workbook, XSSFSheet sheet, int row, int coll) throws IOException {
		BufferedImage image = QrCodeUtils.generateQrCode(context);
		InputStream inputStream = bufferedImageToInputStream(image);
		byte[] bytes = IOUtils.toByteArray(inputStream);
		inputStream.read(bytes);
		int index = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
		XSSFCreationHelper helper = workbook.getCreationHelper();
		Drawing<?> patriarch = sheet.createDrawingPatriarch();
		XSSFClientAnchor anchor = helper.createClientAnchor();
		anchor.setRow1(row);
		anchor.setCol1(coll);
		Picture picture = patriarch.createPicture(anchor, index);
		picture.resize();
	}

	/**
	 * 将BufferedImage转换为InputStream
	 * @param image
	 * @return
	 */
	public InputStream bufferedImageToInputStream(BufferedImage image){
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "png", os);
			InputStream input = new ByteArrayInputStream(os.toByteArray());
			return input;
		} catch (IOException e) {
		}
		return null;
	}

}
