package com.eve.eplusweibo.util;


public class AppMessage {
	private int taskType;
	private int rc;
	private int miLParam;
	private String mstrHParam;
	private String mstrLParam;
	private Object mData;
	
	
	public AppMessage(AppMessage msg)
	{
		this.taskType = msg.taskType;
		rc = msg.rc;
		miLParam = msg.miLParam;
		mstrHParam = msg.mstrHParam;
		mstrLParam = msg.mstrLParam;
		mData = msg.mData;
	}
	
	public AppMessage(int msg, int rc, int lparam, String shparam, String slparam, Object data)
	{
		taskType = msg;
		this.rc = rc ;
		miLParam = lparam;
		mstrHParam = shparam;
		mstrLParam = slparam;
		mData = data;
	}
	
	public void setMessageType(int msgtype)
	{
		taskType = msgtype;
	}
	
	public int getMessageType()
	{
		return taskType;
	}
	
	public void setRc(int rc)
	{
		this.rc = rc;
	}
	
	public int getRc()
	{
		return rc;
	}
	
	public void setLParam(int lparam)
	{
		miLParam = lparam;
	}
	
	public int getLParam()
	{
		return miLParam;
	}
	
	public void setStrHParam(String strHParam)
	{
		mstrHParam = strHParam;
	}
	
	public String getStrHParam()
	{
		return mstrHParam;
	}
	
	public void setStrLParam(String strLParam)
	{
		mstrLParam = strLParam;
	}
	
	public String getStrLParam()
	{
		return mstrLParam;
	}
	
	public void setData(Object data)
	{
		mData = data;
	}
	
	public Object getData()
	{
		return mData;
	}

	@Override
	public String toString()
	{
		return "AppMessage [mMessageType=" + taskType + ", rc="
				+ rc + ", miLParam=" + miLParam + ", mstrHParam="
				+ mstrHParam + ", mstrLParam=" + mstrLParam + ", mData="
				+ mData + "]";
	}
}
