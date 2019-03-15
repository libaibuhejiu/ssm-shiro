package com.huaruan.qhg.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huaruan.qhg.bean.User;
import com.huaruan.qhg.dao.UserDao;
import com.huaruan.qhg.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userdao;
	
	@Override
	public int register(User unknowuser) {
		String username = unknowuser.getUsername();
		if (userdao.findByUsername(username) == null) {//查不到用户则可以进行注册
			userdao.insertUser(unknowuser);
			return 1;
		}
		if (userdao.findByUsername(username) != null) {
			return 2;
		}
		return 0;
	}

	@Override
	public User login(String unknowusername) {
		User user = userdao.findByUsername(unknowusername);
		return user;
	}

}
