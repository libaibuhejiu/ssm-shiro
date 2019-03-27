package com.huaruan.qhg.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huaruan.qhg.bean.ActiveUser;
import com.huaruan.qhg.bean.Permission;
import com.huaruan.qhg.bean.User;
import com.huaruan.qhg.dao.UserDao;
import com.huaruan.qhg.service.UserService;
import com.huaruan.qhg.util.ShiroUtils;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private UserDao userDao;
	
	@RequestMapping(value= {"/login","/",""})
	public String login() {
		return "login";
	}
	
	@RequestMapping("/loginSuccess")
	public String loginSuccess() {
		return "loginSuccess";
	}
	
	@RequestMapping("/register")
	public String register() {
		return "register";
	}
	
	@RequestMapping("/registerSuccess")
	public String registerSuccess() {
		return "registerSuccess";
	}
	
	@RequestMapping("/noPermission")
	public String noPermission() {
		return "noPermission";
	}
	
	@RequestMapping("/myMessage")
	public String myMessage(HttpServletRequest request) {	
		ActiveUser activeUser = ShiroUtils.getActiveUser();
		int userId = activeUser.getId();
		User user = userDao.findById(userId);
		request.setAttribute("oldUser",user);
		return "myMessage";
	}
	
	/**
	 * 检查注册
	 * @param username
	 * @param password
	 * @return 
	 */
	@RequestMapping(value="/checkRegister",method=RequestMethod.POST)
	@ResponseBody
	public HashMap<String,String> checkRegister(@RequestParam("username") String username,@RequestParam("password") String password) {
		User user = new User();
		user.setUsername(username);
		//使用SimpleHash加密
		String salt = new SecureRandomNumberGenerator().nextBytes().toString();	// 盐
		int times = 2;	// 加盐次数
		String algorithmName = "md5";	// MD5 加密
		// 加密 密码
		String encodedPassword = new SimpleHash(algorithmName, password, salt, times).toString();
		user.setPassword(encodedPassword);
		user.setSalt(salt);
		user.setRoleId("2");//role_id 2 角色：用户
		int sign = userService.register(user);
		HashMap<String,String> map = new HashMap<String, String>();
		if (sign == 1) {
			map.put("status","1");//注册成功
			return map;
		}
		if (sign == 2) {
			map.put("status","2");//用户名已存在
			return map;
		}
		if (sign == 0) {
			map.put("status","0");//失败
			return map;
		}
		return map;
	}
	
	//登录验证
	@RequestMapping(value="/checkLogin",method=RequestMethod.POST)
	@ResponseBody
	public HashMap<String,String> checkLogin(@RequestParam("username") String username,@RequestParam("password") String password,@RequestParam("rememberMe") boolean rememberMe) {
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		token.setRememberMe(rememberMe);
		HashMap<String,String> map = new HashMap<String, String>();
		try {
			subject.login(token);
		} catch (IncorrectCredentialsException ice) {
			map.put("status","密码错误！");
			return map;
		} catch (UnknownAccountException uae) {
			map.put("status","用户名不存在！");
			return map;
		}
		map.put("status","1");
		return map;
	}
	
	//用户信息管理页
	@RequestMapping(value="/userMessageManage")
	public String userMessageManage(HttpServletRequest request) {
		//分页
		PageHelper.startPage(1,10);
		List<User> userList = userDao.findAllUser();
		PageInfo<User> userPageInfo = new PageInfo<User>(userList);
		request.setAttribute("pageInfo",userPageInfo);
		//获取身份信息
		ActiveUser activeUser = ShiroUtils.getActiveUser();
		request.setAttribute("activeUser",activeUser);
		return "userMessageManage";
	}

	//用户角色管理页
	@RequestMapping(value="/userRoleManage")
	public String userRoleManage(HttpServletRequest request) {
		// 分页
		PageHelper.startPage(1, 10);
		List<User> userList = userDao.findAllUser();
		PageInfo<User> userPageInfo = new PageInfo<User>(userList);
		request.setAttribute("pageInfo", userPageInfo);
		// 获取身份信息
		ActiveUser activeUser = ShiroUtils.getActiveUser();
		request.setAttribute("activeUser", activeUser);
		return "userRoleManage";
	}
	
	@RequestMapping(value="/findByUsernameCondition")
	public String findByUsernameCondition(HttpServletRequest request) {
		String usernameCondition = request.getParameter("username");
		PageHelper.startPage(1,10);
		List<User> userList = userDao.findByUsernameCondition(usernameCondition);
		PageInfo<User> userPageInfo = new PageInfo<User>(userList);
		request.setAttribute("pageInfo",userPageInfo);
		return "userMessageManage";
	}
	
	@RequestMapping("/addWin")
	public String addWin() {
		return "addWin";
	}
	
	
	//用户信息修改页
	@RequestMapping(value="/updateWin")
	public String updateWin(HttpServletRequest request) {
		String strId = request.getParameter("id");
		int id = Integer.parseInt(strId);
		User oldUser = userDao.findById(id);
		request.setAttribute("oldUser",oldUser);
		return "updateWin";
	}
	
	//用户角色修改页
	@RequestMapping(value="/updateRoleWin")
	public String updateRoleWin(HttpServletRequest request) {
		String strId = request.getParameter("id");
		int id = Integer.parseInt(strId);
		User user = userDao.findById(id);
		request.setAttribute("user",user);
		return "updateRoleWin";
	} 
	
	//用户角色修改提交
	@RequestMapping(value=("/updateUserRole"),method=RequestMethod.POST)
	public String updateUserRole(HttpServletRequest request,HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		int id = Integer.parseInt(request.getParameter("id"));
		int roleId = Integer.parseInt(request.getParameter("roleId"));
		userDao.updateRoleIdInUserTable(id, roleId);
		userDao.updateRoleIdInUserRoleTable(id, roleId);
		// 返回用户角色管理页
		// 分页
		PageHelper.startPage(1, 10);
		List<User> userList = userDao.findAllUser();
		PageInfo<User> userPageInfo = new PageInfo<User>(userList);
		request.setAttribute("pageInfo", userPageInfo);
		// 获取身份信息
		ActiveUser activeUser = ShiroUtils.getActiveUser();
		request.setAttribute("activeUser", activeUser);
		request.setAttribute("isUpdate","1");
		return "userRoleManage";
	}
	
	//角色权限管理页
	@RequestMapping("/rolePermissionManage")
	public String rolePermissionManage(HttpServletRequest request) {
		//1.查询出用户角色权限列表（暂时先只改用户权限）
		int roleId = 2;//用户角色的role_id为2
		List<Integer> permissionIdList = new ArrayList<Integer>();
		permissionIdList = userDao.findPermissionIdListByRoleId(roleId);
		List<Permission> permissionList = new ArrayList<Permission>();
		//根据权限id列表查出权限列表
		for (Integer permissionId : permissionIdList) {
			//根据权限id查出对应的权限
			Permission permission = userDao.findPermissionByPermissionId(permissionId);
			permissionList.add(permission);
		}
		request.setAttribute("permissionList",permissionList);
		//2.查询出用户角色没有的权限列表
		List<Integer> unPermissionIdList = new ArrayList<Integer>();
		unPermissionIdList = userDao.findUnPermissionIdListByRoleId(roleId);
		List<Permission> unPermissionList = new ArrayList<Permission>();
		for (Integer unPermissionId : unPermissionIdList) {
			Permission unPermission = userDao.findPermissionByPermissionId(unPermissionId);
			unPermissionList.add(unPermission);
		}
		request.setAttribute("unPermissionList",unPermissionList);
		return "rolePermissionManage";
	}
	
	//根据roleId和permisisonId删除对应权限
	@RequestMapping("/deletePermission")
	@ResponseBody
	public HashMap<String,String> deletePermission(@RequestParam("roleId")int roleId,@RequestParam("permissionId")int permissionId) {
		HashMap<String,String> map = new HashMap<String,String>();
		int status = 0;
		try {
			status = userDao.deletePermissionBy2Id(roleId, permissionId);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status","0");//删除权限失败
			return map;
		}
		if (status == 0) {
			map.put("status","0");//失败
			return map;
		}
		map.put("status","1");//删除成功
		return map;
	}
	
	//根据roleId和permisisonId添加对应权限
	@RequestMapping("/addPermission")
	@ResponseBody
	public HashMap<String,String> addPermission(@RequestParam("roleId")int roleId,@RequestParam("permissionId")int permissionId) {
		HashMap<String,String> map = new HashMap<String,String>();
		int status = 0;
		try {
			status = userDao.addPermissionBy2Id(roleId, permissionId);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status","0");//添加权限失败
			return map;
		}
		if (status == 0) {
			map.put("status","0");//失败
			return map;
		}
		map.put("status","1");//添加成功
		return map;
	}
	
	/*根据roleId和permisison,description新增权限
	 * 如果permission在permission表里不存在，则需向permission表和role_permission表添加,并将enable置为0
	 * 如果permission在permission表里已存在，则提示权限已存在*/
	@RequestMapping("/createPermission")
	@ResponseBody
	public HashMap<String,String> createPermission(@RequestParam("roleId")int roleId,@RequestParam("permission")String permission,@RequestParam("description")String description){
		int isExists = userDao.findPermissionCount(permission);
		HashMap<String,String> map = new HashMap<String,String>();
		if (isExists>0) {
			map.put("status","0");//权限已存在
			return map;
		}
		if (isExists==0) {
			try {
				userDao.insertIntoPermission(permission, description);
				int permissionId = userDao.findByPerms(permission);
				userDao.insertIntoRP(roleId, permissionId,0);
			} catch (Exception e) {
				map.put("status","2");//插入失败，请重试
				e.printStackTrace();
			}
			map.put("status","1");//插入成功
		}
		return map;
	}
	
	//完全删除权限
	@RequestMapping("/deletePermissionTotally")
	@ResponseBody
	public HashMap<String,String> deletePermissionTotally(@RequestParam("roleId")int roleId,@RequestParam("permissionId")int permissionId){
		HashMap<String,String> map = new HashMap<String,String>();
		try {
			userDao.deletePermissionFormRP(roleId, permissionId);
			userDao.deletePermissionFormPermission(permissionId);
		} catch (Exception e) {
			map.put("status","0");//删除失败
			e.printStackTrace();
			return map;
		}
		map.put("status","1");//删除成功
		return map;
	}
	
	//更新权限窗口
	@RequestMapping("/updatePermissionWin")
	public String updatePermission(HttpServletRequest request, @RequestParam("roleId")int roleId,@RequestParam("permission")String permission,@RequestParam("permissionId")int permissionId,@RequestParam("description")String description) {
		Permission permissionBean = new Permission();
		permissionBean.setId(permissionId);
		permissionBean.setPermission(permission);
		permissionBean.setDescription(description);
		request.setAttribute("permission",permissionBean);
		request.setAttribute("roleId",roleId);
		return "updatePermissionWin";
	}
	
	//更新权限
	@RequestMapping("/updatePermission")
	@ResponseBody
	public HashMap<String,String> updatePermission(@RequestParam("permissionId")int permissionId,@RequestParam("permission")String permission,@RequestParam("description")String description){
		HashMap<String,String> map = new HashMap<String,String>();
		int count = userDao.findPermissionByPermission(permission);//是否有重名权限
		if (count>0) {
			map.put("status","2");//权限名已存在
			return map;
		}
		try {
			userDao.updatePermissionById(permissionId, permission, description);
		} catch (Exception e) {
			map.put("status","0");//修改失败
			e.printStackTrace();
			return map;
		}
		map.put("status","1");//成功
		return map;
	}
	
	//更新用户信息
	@RequestMapping(value="/updateUser",method=RequestMethod.POST)
	@ResponseBody
	public HashMap<String,String> updateUser(@RequestParam("id") int id,@RequestParam("username") String username,@RequestParam("password") String password){
		User unknowuser = userDao.findExtraExistsByUsername(id,username);
		HashMap<String,String> map = new HashMap<String,String>();
		if (unknowuser != null) {
			map.put("status","0");//用户名已存在
		} else {//用户名不存在则更新
			User user = new User();
			user.setId(id);
			user.setUsername(username);
			//使用SimpleHash加密
			String salt = new SecureRandomNumberGenerator().nextBytes().toString();	// 盐
			int times = 2;	// 加盐次数
			String algorithmName = "md5";	// MD5 加密
			// 加密 密码
			String encodedPassword = new SimpleHash(algorithmName, password, salt, times).toString();
			user.setPassword(encodedPassword);
			user.setSalt(salt);
			userDao.updateById(user);
			map.put("status","1");//成功
		}
		return map;
	}
	
	@RequestMapping("/deleteUserById")
	@ResponseBody
	public Map<String,String> deleteUserById(int id) {
		int rows = userDao.deleteById(id);
		Map<String,String> map = new HashMap<String,String>();
		if (rows > 0) {
			map.put("status","1");//ɾ���ɹ�
		} else {
			map.put("status","0");//ɾ��ʧ��
		}
		return map;
	}
	
	//分页
	@RequestMapping(value="/findUsersByPage")
	public String findUsersByPage(@Param("pageNum")int pageNum,HttpServletRequest request) {
		PageHelper.startPage(pageNum,10);
		List<User> userList = userDao.findAllUser();
		PageInfo<User> userPageInfo = new PageInfo<User>(userList);
		request.setAttribute("pageInfo",userPageInfo);
		return "userMessageManage";
	}
	
	//注销
	@RequestMapping(value="/logout")
	public String logout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "login";
	}
	

}
