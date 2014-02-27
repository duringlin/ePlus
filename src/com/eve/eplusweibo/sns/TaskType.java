package com.eve.eplusweibo.sns;

/**
 * 任务类型，也是App消息类型
 * */
public final class TaskType {

	// server接口任务
	
	/** 注册 */
	public static final int HTTP_REGISTE = 10001002;
	/** 修改密码 */
	public static final int HTTP_RESET_PASSWORD = 10001005;
	/** 第三方注册 */
	public static final int HTTP_OPEN_PLATFORM_REGISTE = 10001006;
	// /** 第三方登录 */
	// public static final int HTTP_OPEN_PLATFORM_LOGIN = 10001007;
	/** 第三方更新个人信息 */
	public static final int HTTP_QQ_WEIBO_UPDATE = 10001008;

	/** 拿UserId */
	public static final int HTTP_GET_USERID = 10001011;
	/** 登录 */
	public static final int HTTP_LOGIN = 10001013;
	/** QQ绑定帐号 */
	public static final int HTTP_QQ_BIND = 10001014;
	/** 微博绑定帐号 */
	public static final int HTTP_WEIBO_BIND = 10001015;
	/** QQ解绑定帐号 */
	public static final int HTTP_QQ_UN_BIND = 10001016;
	/** 微博解绑定帐号 */
	public static final int HTTP_WIEBO_UN_BIND = 10001017;
	/** 获取已绑定帐号 */
	public static final int HTTP_GET_BIND_ACCOUNTS = 10001024;
	
	
}