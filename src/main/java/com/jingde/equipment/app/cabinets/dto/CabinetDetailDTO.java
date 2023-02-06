package com.jingde.equipment.app.cabinets.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.*;

/**
 * @author 
 */
@Data
public class CabinetDetailDTO {
	@JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue)
	private Integer id;
	@JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue)
	private String cabinetCode;
	@JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue)
	private Integer seq;
	@JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue)
	private String seatCode;
	@JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue)
	private String cardCode;
	@JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue)
	private String inStatus;
	@JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue)
	private String usedStatus;
	@JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue)
	private String imgUrl;
	@JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue)
	private String status;
	@JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue)
	private Integer firearmsTypeId;
	@JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue)
	private String firearmsType;
	@JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue)
	private String seatType;
	 //门id
    private  String doorId;
   
	 //门序号
    private  Integer doorIndex;
   
	 //门排列方式
    private  String doorLayout;
   
	 //门名字
    private  String doorName;
   
	 //门高度
    private  Integer doorHeight;
   
	 //门宽度
    private  Integer doorWidth;
   
	 //区域id
    private  String areaId;
   
	 //区域序号
    private  Integer areaIndex;
   
	 //区域排列方式
    private  String areaLayout;
   
	 //区域名字
    private  String areaName;
   
	 //区域高度
    private  Integer areaHeight;
   
	 //区域宽度
    private  Integer areaWidth;
   
	 //枪座/弹座id
    private  String seatId;
   
	 //枪座/弹座排列方式
    private  String seatLayout;
   
	 //枪座/弹座名字
    private  String seatName;
   
	 //枪座/弹座高度
    private  Integer seatHeight;
   
	 //枪座/弹座宽度
    private  Integer seatWidth;

}
