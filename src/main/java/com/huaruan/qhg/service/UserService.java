package com.huaruan.qhg.service;

import com.huaruan.qhg.bean.User;
/**
 * 用户的接口类
 * @author Administrator
 *
 */
public interface UserService {
	/**
	 * 注册
	 * @param user
	 * @return 1注册成功，0注册失败,2用户已存在
	 */
	public int register(User user);
	
	/**
	 * 登录时根据用户名查找用户
	 * @param 输入的用户名
	 * @return 成功返回该用户,失败返回null
	 */
	public User login(String unknowusername);

}
