package com.huaruan.qhg.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaruan.qhg.bean.Permission;
import com.huaruan.qhg.bean.Role;
import com.huaruan.qhg.bean.User;
import com.huaruan.qhg.dao.UserDao;
import com.huaruan.qhg.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userdao;
	
	@Override
	public int register(User unknowuser) {
		String username = unknowuser.getUsername();
		if (userdao.findByUsername(username) == null) {
			userdao.insertUser(unknowuser);
			int userId = userdao.findByUsername(username).getId();
			//插入user_role表,角色为2，用户
			userdao.insertUserRole(Integer.toString(userId),"2");
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

	@Override
	public List<Permission> findPermissionList(int userId) {
		//根据id找到角色列表
		List<String> roleIdList = userdao.findRoleIdListByUserId(userId);
		List<Role> roleList = new ArrayList<>();
		for (String roleId : roleIdList) {
			Role role = userdao.findRoleByRoleId(roleId);
			roleList.add(role);
		}
		List<Permission> permissionList = new ArrayList<Permission>();
		//根据角色列表查找出权限列表
		for (Role role : roleList) {
			//根据角色id找到对应的权限id列表
			List<Integer> permissionIdList = userdao.findPermissionListByRoleId(role.getRoleId());
			for (Integer permissionId : permissionIdList) {
				//根据权限id找出权限
				Permission permission = userdao.findPermissionByPermissionId(permissionId);
				permissionList.add(permission);
			}
		}
		return permissionList;
	}

}
