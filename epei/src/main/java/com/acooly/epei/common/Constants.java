package com.acooly.epei.common;

/**
 * 系统中使用到的常量
 * 修订记录：
 * liubin 2015-11-25 14:30 创建
 */
public class Constants {
	/**
	 * 注册短信验证码发送模板
	 */
	public static final String SMS_REG_TMP = "sms.template.reg";
	/**
	 * 线下充值成功短信通知
	 */
	public static final String SMS_RECHARGE_TMP = "sms.template.recharge";
	/**
	 * 重置密码短信模板
	 */
	public static final String SMS_RESET_PASSWORD_TMP = "sms.template.forgot.password";

	/**
	 * 陪护预约成功短信通知模板
	 */
	public static final String PH_ORDERED_SMS_TEMPLATE = "sms.template.ph.ordered";

	/**
	 * 预约成功短信模板
	 */
	public static final String PZ_ORDERED_SMS_TEMPLATE = "sms.template.pz.ordered";

	/**
	 * 陪诊预约成功后向医院负责人发送短信模版
	 */
	public static final String PZFZR_ORDERED_SMS_TEMPLATE = "sms.template.pzfzr.ordered";
	
	/**
	 * 陪护预约成功后向医院负责人发送短信模版
	 */
	public static final String PHFZR_ORDERED_SMS_TEMPLATE = "sms.template.phfzr.ordered";
	
	/**
	 * 陪诊预约成功后向医院负责人发送短信模版
	 */
	public static final String PZFZR_UPDATE_ORDERED_SMS_TEMPLATE = "sms.template.pzfzr.update.ordered";
	
	/**
	 * 医院管理员分配任务给服务人员 并发短信给服务人员和客户  短信模版
	 */
	public static final String PZ_ASSIGN_TASK_SMS_TEMPLATE = "sms.template.pz.assignTask";
	public static final String PH_ASSIGN_TASK_SMS_TEMPLATE = "sms.template.ph.assignTask";
	public static final String REMIND_SMS_TEMPLATE = "sms.template.remind";
	
	/**
	 * 验证码在session中的属性名
	 */
	public static final String SESSION_CAPTCHA_ID = "captchaId";

	/**
	 * 短信验证码在session中的属性名
	 */
	public static final String MOBILE_VALID_CODE = "mobileValidCode";

	/**
	 * 短信验证码超时时间在session中的属性名
	 */
	public  static final String EXPIRED_TIME = "expiredDateMilis";

	/**
	 * 服务电话
	 */
	public static final String SERVICE_TEL = "4008837622";
	
}
