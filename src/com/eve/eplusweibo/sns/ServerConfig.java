package com.eve.eplusweibo.sns;

public final class ServerConfig {
	
	public static final int PLATFORM = 2;
	
	
//	// HTTP 服务器
	public static final String HTTP_SERVER = "http://api.kktv1.com:8080/meShow/entrance?parameter=";
	public static final String HTTP_SERVER_WEB = "http://www.kktv1.com";
	// 新支付平台：paypal, 移动, 联通, qvod房间外, payeco
	public static final String HTTP_CHARGE_API = "http://api8.kktv1.com:8080/api8/s0?params=";
	// 老支付平台：支付宝 神州付 银联手机
	public static final String HTTP_CHARGE_API_OLD = "http://api8.kktv1.com:8080/api8/entrance?parameter=";
	// Socket 服务器
    public static final String ROOM_HTTP_SERVER = "http://into1.kktv8.com/?roomId=";
    //安全帐号
    public static final String HTTP_SAFE_LOGIN_SERVER = "http://api.kktv1.com:8080/meShow/entrance?parameter=";
	//房间买票地址
	public static final String BUY_TICKET_URL = "http://www.kktv1.com/Extra/ticket";
	
	/**测试服*/
//	public static final String HTTP_SERVER = "http://10.0.0.23:9090/meShow/entrance?parameter=";
//	public static final String HTTP_SERVER_WEB = "http://10.0.0.2:100";
//	public static final String HTTP_CHARGE_API = "http://10.0.0.23:9292/api8/s0?params=";
//	public static final String HTTP_CHARGE_API_OLD = "http://10.0.0.23:9292/api8/entrance?parameter=";
//	public static final String ROOM_HTTP_SERVER = "http://10.0.0.23:81/?roomId=";
//	public static final String HTTP_SAFE_LOGIN_SERVER = "http://10.0.0.23:9090/meShow/entrance?parameter=";
//	public static final String BUY_TICKET_URL = "http://10.0.0.2:100/Extra/ticket";
	
    
	/**阮成的电脑*/
//	public static final String HTTP_SERVER = "http://10.0.0.2:9090/meShow/entrance?parameter=";
//	public static final String HTTP_SERVER_WEB = "http://10.0.0.2:100";
//	public static final String HTTP_CHARGE_API = "http://10.0.0.2:9292/api8/s0?params=";
//	public static final String HTTP_CHARGE_API_OLD = "http://10.0.0.2:9090/meShow/entrance?parameter=";
//	public static final String ROOM_HTTP_SERVER = "http://10.0.0.2:81/?roomId=";

//	/**内网*/
//	public static final String HTTP_SERVER = "http://10.0.0.193:8080/meShow/entrance?parameter=";
//	public static final String HTTP_SERVER_WEB = "http://10.0.0.106:83";
//  public static final String HTTP_CHARGE_API = "http://cc.kktv1.com:100";
//	public static final String ROOM_HTTP_SERVER = "http://10.0.0.2:8000/?roomId=";
    
}
