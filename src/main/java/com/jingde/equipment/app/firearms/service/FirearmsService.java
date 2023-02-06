package com.jingde.equipment.app.firearms.service;

import com.github.pagehelper.PageInfo;
import com.jingde.equipment.model.Firearms;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface FirearmsService {

	/**
	 * 通过枪号获取枪支详情
	 * @param firearmNo
	 * @return
	 */
	Firearms getFirearmsByNo(String firearmNo);

	PageInfo<Firearms> list(Integer page, Integer size, Firearms condition);

	boolean add(Firearms firearms);

	int update(Firearms firearms);

	boolean delete(Firearms firearms);

	void exportQrCode(List<Map<String, Integer>> list, HttpServletResponse response);

	boolean qrCodeEntering(List<String> list);

	int updateFirearmsStatus(List<String> firearmsNo, Integer status);

	List<Firearms> selectApplyList(String time, Integer typeId);

	Firearms qrCodeToFirearms(String qrCode);

	Firearms findInfoByCode(String qrCode);
}
