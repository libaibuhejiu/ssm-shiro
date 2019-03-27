package com.huaruan.qhg.service;

import com.huaruan.qhg.bean.User;

import java.util.List;

import com.huaruan.qhg.bean.Permission;

public interface UserService {
	/**
	 * 注册
	 * @param user
	 * @return 1成功0失败2用户名已存在
	 */
	public int register(User user);
	
	/**
	 * 登录
	 * @param 用户名
	 * @return 用户
	 */
	public User login(String unknowusername);
	
	/**
	 * 根据用户id查找权限列表
	 * @param userId
	 * @return
	 */
	public List<Permission> findPermissionList(int userId);

}
