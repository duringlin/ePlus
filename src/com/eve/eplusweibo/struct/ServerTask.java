package com.eve.eplusweibo.struct;


/**
 * 后台线程中的task对象
 * */
public class ServerTask {
	
	private int hparam;
	private int lparam;
	private String strHparam;
	private String strLparam;
	private Object data;
	private String callBack;
	
	public String getCallBack() {
		return callBack;
	}

	public void setCallBack(String callBack) {
		this.callBack = callBack;
	}

	public Object getData() {
		return data;
	}
	
	public void setData(Object data) {
		this.data = data;
	}

	public int getHparam() {
		return hparam;
	}

	public void setHparam(int hparam) {
		this.hparam = hparam;
	}

	public int getLparam() {
		return lparam;
	}

	public void setLparam(int lparam) {
		this.lparam = lparam;
	}

	public String getStrHparam() {
		return strHparam;
	}

	public void setStrHparam(String strHparam) {
		this.strHparam = strHparam;
	}

	public String getStrLparam() {
		return strLparam;
	}

	public void setStrLparam(String strLparam) {
		this.strLparam = strLparam;
	}

	private int taskType;
	
	public ServerTask(int taskType)
	{
		this.taskType = taskType;
	}
	
	public int getTaskType() {
		return taskType;
	}

	@Override
	public boolean equals(Object o) {
		
		if(o instanceof ServerTask)
		{
			ServerTask task = (ServerTask)o;
			if(task.getTaskType() == taskType && task.getHparam() == hparam && task.getLparam() == lparam)
				return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "ServerTask:[operation="+taskType+"]";
	}
}
