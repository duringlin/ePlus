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

}
