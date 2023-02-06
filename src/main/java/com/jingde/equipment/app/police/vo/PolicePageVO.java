package com.jingde.equipment.app.police.vo;

import lombok.Data;

/**
 * 配枪人员列表信息表(持枪人员列表/禁枪人员列表)
 * @Description: com.jingde.equipment.app.system.vo
 * @Title: PoliceVo
 * @Auther: CzSix
 * @create 2019/3/7 16:33
 */
@Data
public class PolicePageVO {

    private String id; //配枪人员id
    private String policeName; //姓名
    private String sex; //性别
    private String age; //年龄
    private String nation; //民族
    private String politicalStatus; //政治面貌
    private String leaderLevel; //职位(级别)
    private String departmentName; //部门
    private String policeCode; //警号
    private String address; //家庭地址
    private String mobile; ///联系方式
    private String firearmCode; //枪证号
    private String firearmCodePublish; //发证日期
    private String annualVerification; //年审情况
    private String reason; //禁枪原因/配枪不良记录
    private String date; //不良记录时间
    private String status;


}
