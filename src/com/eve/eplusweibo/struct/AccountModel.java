package com.eve.eplusweibo.struct;

/**
 * 账户表模型
 * @author ZhaoXuDong
 */
public class AccountModel {
	private String name;
	private String uid;
	private String accesstoken;
	private String expires;
	//到期的时间
	private String overtime;
	// index序号
	private String inx;
	//帐号类型：新浪、腾讯、人人网、微信朋友圈
	private String type;
	//头像地址
	private String profile;

	public String getName()  {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUid()  {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getType(){
		return type;
	}
	
	public void setType(String type){
		this.type = type;
	}
	
	public String getAccesstoken(){
		return accesstoken;
	}
	
	public void setAccesstoken(String accesstoken){
		this.accesstoken = accesstoken;
	}
	
	public String getExpires(){
		return expires;
	}
	
	public void setExpires(String expires){
		this.expires = expires;
	}
	
	public String getOvertime(){
		return overtime;
	}
	
	public void setOvertime(String overtime){
		this.overtime = overtime;
	}
	
	public String getInx(){
		return inx;
	}
	
	public void setInx(String inx){
		this.inx = inx;
	}
	
	public String getProfile()  {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

}
