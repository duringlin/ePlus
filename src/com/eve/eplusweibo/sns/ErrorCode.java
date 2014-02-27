package com.eve.eplusweibo.sns;

import com.eve.eplusweibo.util.Log;

public final class ErrorCode {

	//////////////////////////////////////
	// 1. 100~599 http保留
	// 2. 1000000~ api
	// 3. ~99 客户端自定义
	//////////////////////////////////////
    
	// client error code
	public static final int UNKNOWN = -1;		// unknown error
	public static final int HTTP_OK = 0;
	// to be removed
	public static final int NO_NETWORK = 1;		//一般在调用接口之前就判断了
	
	// to be removed
	public static final int HTTP_CONNECT_RESPONSE_ERROR = 103;
	
	public static final int HTTP_CONNECT_SOCKET_ERROR = 90;	 	// SocketException
	public static final int HTTP_CONNECT_TIME_OUT = 91;			// SocketTimeoutException
	public static final int HTTP_CONNECT_IO_ERROR = 92;			// IOException
	public static final int HTTP_URL_ERROR = 93;				// MalformedURLException
	
	/**
	 * return the error message id by errorCode
	 */
	public static int getHttpErrorStrResId(int errorCode)
	{
		int res = 123;
		switch(errorCode)
		{
			case NO_NETWORK:
//				res = R.string.kk_error_invalid_param;
				break;
			default:
				Log.e("ErrorCode", "unknow error=>"+errorCode);
//				res = R.string.kk_error_unknow;
				break;
		}
		return res;
	}
	
	
}
