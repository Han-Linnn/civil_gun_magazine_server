package com.jingde.equipment.constant;

/**
 * Created by oceanover on 2019-05-31.
 * 应用业务、逻辑相关的常量
 *
 * @author
 */
public class ServiceConstant {
	// token 秘钥
	public static final String TOKEN_SECRET = "6f77c002656ee74b";

	// 擦拭保养状态
	// 待审核
	public static final String CLEAN_WAITING_AUDITING = "01";
	// 待保养
	public static final String CLEAN_WAITING_CLEAN = "05";
	//审批不通过
	public static final String CLEAN_UN_AUDITING = "10";
	// 保养中
	public static final String CLEAN_UNDER_MAINTENANCE = "15";
	//保养完成
	public static final String CLEAN_MAINTENANCE_COMPLETED = "25";
	//保养取消
	public static final String CLEAN_MAINTENANCE_CANCELLED = "30";

    /**
     * 领用申请常量
     *
     */
    //待审核
    public static final String APPLY_STATUS_EXAMINE = "01";
    //待领取
    public static final String APPLY_STATUS_RECEIVE = "02";
    //领取中
    public static final String APPLY_STATUS_RECEIVE_ING = "03";
    //部分领取待归还
    public static final String APPLY_STATUS_RECEIVE_NOT_RETURN = "04";
    //全部领取待归还
    public static final String APPLY_STATUS_ALL_RECEIVE_NOT_RETURN = "05";
    //部分归还
    public static final String APPLY_STATUS_PART_RETURN = "06";
    //审核不通过
    public static final String APPLY_STATUS_NO_EXAMINE = "08";
    //已取消
    public static final String APPLY_STATUS_CANCEL = "09";
    //已完成
    public static final String APPLY_STATUS_COMPLETE = "10";


}
