package com.eve.eplusweibo;

import android.os.Environment;


/**
 * 系统配置
 * Global
 * @author vytas
 * @create 12-11-28
 * 
 */
public class Global {
	
    /** LOG开关  (发布前 务必 关闭)*/
    public static boolean DEBUG = true;

	public static final String APP_SD_PATH = Environment.getExternalStorageDirectory().toString() + "/eplus/";
	public static final String APP_PIC_CACHE_PATH = APP_SD_PATH + "cache/picture/";
	
	/**屏幕密度*/
	public static float density = 0;
	/**屏幕宽度 */
	public static int windowsWidth;
	/**屏幕高度 */
	public static int windowsHeight;
	/**状态栏高度 */
	public static int statusBarHeight;
	

	/** 网络链接时限 （毫秒）*/
	public static final int HTTP_CONNECT_TIMEOUT = 20000;
	public static final int HTTP_READ_TIMEOUT = 20000;		
	
	 /** 社交帐号类型 新浪微博 **/
    public static final String SNSTYPE_SINA = "sina";
    /** 社交帐号类型 腾讯微博博 **/
    public static final String SNSTYPE_TENCENT = "tencent";
	
    /** 新浪微博 APPKEY **/
    public static final String SINA_CONSUMER_KEY = "3100739515";
    /** 新浪微博 认证回调地址 **/
    public static final String SINA_REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";

}
