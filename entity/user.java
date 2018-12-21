package com.example.taoxiao.entity;

import java.sql.Blob;

public class user {
	private String userid;
	private String userpsw;
	private String nickname;
	private Blob headpicture;
	private String college;
	private String gradle;
	private String tel;
	private String ali;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUserpsw() {
		return userpsw;
	}
	public void setUserpsw(String userpsw) {
		this.userpsw = userpsw;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Blob getHeadpicture() {
		return headpicture;
	}
	public void setHeadpicture(Blob headpicture) {
		this.headpicture = headpicture;
	}
	public String getCollege() {
		return college;
	}
	public void setCollege(String college) {
		this.college = college;
	}
	public String getGradle() {
		return gradle;
	}
	public void setGradle(String gradle) {
		this.gradle = gradle;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getAli() {
		return ali;
	}
	public void setAli(String ali) {
		this.ali = ali;
	}
	
}
/*
insert into `user` values('a','abc','0316',null,null);
insert into `user` values('b','abc','0316',null,null);
insert into `user` values('01','01','0316',null,null);
insert into `user` values('abc123','01','0316',null,null);
*/
