package com.huaruan.qhg.bean;

import java.io.Serializable;

/**
 * 权限类
 * @author Administrator
 *
 */
public class Permission implements Serializable{
	
	private int id;
	private String permission;
	private String description;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	

}
