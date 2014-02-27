package com.eve.eplusweibo.sns;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import com.eve.eplusweibo.Global;
import com.eve.eplusweibo.struct.ServerTask;
import com.eve.eplusweibo.util.AppMessage;
import com.eve.eplusweibo.util.Log;
import com.eve.eplusweibo.util.MessageDump;

/**
 * 添加任务到任务队列，处理，然后从队列中移除，在处理过程中添加任务，无效，拒绝
 * */
public class HttpManager {

	private final String TAG = "HttpManager";

	private static HttpManager instance = null;

	private Queue<ServerTask> mHttpQueue = null;
	private HTTPLoop mHttpTaskThread = null;

	private Object mLock = new Object();

	private HttpURLConnection connection;

	public static HttpManager getInstance() {
		if (instance == null) {
			instance = new HttpManager();
		}

		return instance;
	}

	private HttpManager() {
		mHttpQueue = new LinkedBlockingQueue<ServerTask>();
		mHttpTaskThread = new HTTPLoop();
		mHttpTaskThread.start();
	}

	public void destroy()
	{
		synchronized (mLock) {
			//			if(mHttpTaskThread != null)
			//			mHttpTaskThread.mbStop = true;
			//		mHttpTaskThread = null;
			if(mHttpQueue != null)
				mHttpQueue.clear();
			//		mHttpQueue = null;
		}
	}

	private ServerTask getTask()
	{
		synchronized (mLock) {
			while (mHttpQueue.size() == 0) {
				Log.i(TAG, "mHttpQueue size == 0");
				try {
					Log.v(TAG, "no task and waitting");
					mLock.wait();
					Log.v(TAG, "mHttpQueue notifyed");
				} catch (InterruptedException ie) {
					Log.v(TAG, "InterruptedException:"+ie.getMessage());
					return null;
				}
			}
			ServerTask task = mHttpQueue.peek();
			Log.i(TAG, "get task:"+task);
			return  task;
		}
	}

	private void addTask(ServerTask task)
	{
		synchronized (mLock) {
			if(!mHttpQueue.contains(task))
			{
				try {
					Log.v(TAG, "addTask:"+task);
					mHttpQueue.add(task);
					mLock.notifyAll();
				} catch (Exception e) {
					Log.e(TAG, ""+e.getMessage());
				}
			}else
			{
				Log.v(TAG, "mHttpQueue has contains this task->"+task.getTaskType());
			}
		}
	}

	class HTTPLoop extends Thread {
		private final boolean mbStop = false;
		public void run() {
			Log.v(TAG, "==>HTTP thread start");
			while (!mbStop) {
				Log.i(TAG, "HTTPLoop run,isEmpty=>"+mHttpQueue.isEmpty());
				/**
				 * 无需登录的链接
				 * */
				android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
				ServerTask task = getTask();
				if(task == null)
					continue;
				Log.i(TAG, "getTask===>"+task);
				switch (task.getTaskType()) {
				case TaskType.HTTP_GET_USERID:
//					getHttpUserId(task);
					mHttpQueue.remove(task);
					break;
				case TaskType.HTTP_REGISTE:
//					registeEx(task);
					mHttpQueue.remove(task);
					break;
				default:
					break;
				}
			}
		}
	}


//	public void getIsOnline(ServerTask task) {
//		int userid = task.getHparam();
//		Log.i(TAG,"get online->"+userid);
//
//		String url = HtmlRequestFormer.getOnLine(userid);
//		Log.i(TAG, "get online url="+url);
//		InputStream stream = getConnectStream(url, task.getTaskType());
//		if(stream != null)
//		{
//			MessageDump.getInstance().dispatch(new AppMessage(task.getTaskType(), 0, task.getLparam(), Util.convertStreamToString(stream), task.getStrHparam(), null));
//			if(connection != null)
//				connection.disconnect();
//			connection = null;
//			try {
//				stream.close();
//				stream = null;
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	/**获取用户动态*/
//	private void getUserDynamic(ServerTask task){
//		int pageIndex = task.getLparam();
//		int countPerPage = Integer.parseInt(task.getStrHparam());
//		int userid  = task.getHparam();
//		String url = HtmlRequestFormer.getUserDynameicUrl(userid,pageIndex,countPerPage);
//		Log.i(TAG, "getUserDynamic url==" + url);
//		InputStream stream = getConnectStream(url, task.getTaskType());
//		if(stream != null)
//		{
//			UserDynamicParser parser = new UserDynamicParser();
//			int rc = HtmlParser.parse(stream, parser);
//
//			MessageDump.getInstance().dispatch(new AppMessage(task.getTaskType(), rc, parser.getPageTota(), null, null, parser.getUserDynamicList()));
//			parser.release();
//			parser = null;
//			if(connection != null)
//				connection.disconnect();
//			connection = null;
//			try {
//				stream.close();
//				stream = null;
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	/**获取去用户动态列表评论列表*/
//	private void getUserDynamicList(ServerTask task){
//		int newsid = task.getLparam();
//		int pageIndex = task.getHparam();
//		String pageNumber = task.getStrHparam();
//		String url = HtmlRequestFormer.getUserDynameicListUrl(newsid, pageIndex, pageNumber);
//		Log.i(TAG, "getUserDynamic commentList url=" + url);
//		Log.i(TAG, "getUserDynamicList url==" + url);
//		InputStream stream = getConnectStream(url, task.getTaskType());
//		if(stream != null)
//		{
//			UserDynamicCommentParser parser = new UserDynamicCommentParser();
//			int rc = HtmlParser.parse(stream, parser);
//
//			MessageDump.getInstance().dispatch(new AppMessage(task.getTaskType(), rc, parser.getTotalNumber(), null, null, parser.getUserDynamicCommentList()));
//			parser.release();
//			parser = null;
//			if(connection != null)
//				connection.disconnect();
//			connection = null;
//			try {
//				stream.close();
//				stream = null;
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}


	
	private InputStream getConnectStream(String urlStr, int msgtype,
			String hpa, int connectTime, int readTime)
	{
		int errCode = -1;
		int status = -1;
		InputStream stream = null;
		try{
			URL url = new URL(urlStr);
			HttpURLConnection con = null;
			con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(connectTime);
			con.setReadTimeout(readTime);
			con.setRequestProperty("Charset", "utf-8"); // 设置编码
			Log.i(TAG, "connect...");
			long time = System.currentTimeMillis();
			con.connect();
			Log.i(TAG, "connect complete->"+(System.currentTimeMillis() - time));
			status = con.getResponseCode();
			if (status == HttpURLConnection.HTTP_OK) {
				connection = con;
				stream = con.getInputStream();
				Log.i(TAG, "getInputStream ->"+(System.currentTimeMillis() - time));
				errCode = ErrorCode.HTTP_OK;
			}else
			{
				Log.e(TAG, "status error----->"+status);
				errCode = ErrorCode.HTTP_CONNECT_RESPONSE_ERROR;
			}
		} catch (SocketException e) {
			errCode = ErrorCode.HTTP_CONNECT_SOCKET_ERROR;
			e.printStackTrace();
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			errCode = ErrorCode.HTTP_CONNECT_TIME_OUT;
		} catch (IOException e) {
			e.printStackTrace();
			errCode = ErrorCode.HTTP_CONNECT_IO_ERROR;
		}
		if (errCode != ErrorCode.HTTP_OK && msgtype > 0) {
			MessageDump.getInstance().dispatch(new AppMessage(msgtype,errCode, status, hpa, null, null));
		}
		return stream;
	}

	private InputStream getConnectStream(String urlStr,int msgtype,String hpa,int connectTime)
	{
		return getConnectStream(urlStr, msgtype, hpa, connectTime, Global.HTTP_READ_TIMEOUT);
	}

	private InputStream getConnectStream(String urlStr,int msgtype)
	{
		return getConnectStream(urlStr, msgtype, null,Global.HTTP_CONNECT_TIMEOUT);
	}

	@SuppressWarnings("unused")
	private InputStream getConnectStream(String urlStr,int msgtype, int connectTime)
	{
		return getConnectStream(urlStr, msgtype, null, connectTime);
	}

	private InputStream getConnectStream(String urlStr,int msgtype,String hpa)
	{
		return getConnectStream(urlStr, msgtype, hpa,Global.HTTP_CONNECT_TIMEOUT);
	}
	


}
