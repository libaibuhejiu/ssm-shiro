package com.huaruan.qhg.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huaruan.qhg.bean.PageBean;
import com.huaruan.qhg.bean.User;
import com.huaruan.qhg.dao.UserDao;
import com.huaruan.qhg.service.UserService;
import com.huaruan.qhg.util.MD5Utils;
import com.huaruan.qhg.util.PBUtils;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private UserDao userDao;
	
	@RequestMapping("/login")
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
	
	/**
	 * 注册检查
	 * @param username
	 * @param password
	 * @return 检查状态的map,1成功，2存在，0失败
	 */
	@RequestMapping(value="/checkRegister",method=RequestMethod.POST)
	@ResponseBody
	public HashMap<String,String> checkRegister(@RequestParam("username") String username,@RequestParam("password") String password) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(MD5Utils.md5(password));//md5加密密码
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
			map.put("status","0");//注册失败
			return map;
		}
		return map;
	}
	
	@RequestMapping(value="/checkLogin",method=RequestMethod.POST)
	@ResponseBody
	public HashMap<String,String> checkLogin(@RequestParam("username") String username,@RequestParam("password") String password) {
		User unknowuser = new User();
		String md5password = MD5Utils.md5(password);
		unknowuser.setUsername(username);
		unknowuser.setPassword(md5password);//md5加密密码
		User user = userService.login(unknowuser.getUsername());
		HashMap<String,String> map = new HashMap<String, String>();
		if (user != null) {
			if (user.getPassword().equals(unknowuser.getPassword())) {
				map.put("status","1");//密码正确，登录成功
			} else {
				map.put("status","2");//密码错误，登录失败
			}
		} else {
			map.put("status","0");//用户名不存在
		}
		return map;
	}
	
	@RequestMapping(value="/userManage")
	public String userManage(HttpServletRequest request) {
		PageBean pb = PBUtils.generatorPB(1, userDao);
		request.setAttribute("pageBean",pb);
		return "userManage";
	}
	
	//用户名条件模糊查询
	@RequestMapping(value="/findByUsernameCondition")
	public String findByUsernameCondition(HttpServletRequest request) {
		String usernameCondition = request.getParameter("username");
		List<User> userList = userDao.findByUsernameCondition(usernameCondition);
		PageBean pb = PBUtils.generatorPB(1, userDao);
		//需要重新set PageBean的各项属性值
		pb.setUserList(userList);
		pb.setAllCount(userList.size());
		//重新计算总用户数并set
		double allUserCount = userList.size();
		int totalPage = (int) Math.ceil(allUserCount / (pb.getPageSize()));
		pb.setTotalPage(totalPage);
		request.setAttribute("pageBean",pb);
		return "userManage";
	}
	
	//重定向到新增用户页面
	@RequestMapping("/addWin")
	public String addWin() {
		return "addWin";
	}
	
	//前往修改用户页面
	@RequestMapping(value="/updateWin")
	public String updateWin(HttpServletRequest request) {
		String strId = request.getParameter("id");
		int id = Integer.parseInt(strId);
		//根据id查找用户
		User oldUser = userDao.findById(id);
		request.setAttribute("oldUser",oldUser);
		return "updateWin";
	}
	
	//修改用户
	@RequestMapping(value="/updateUser",method=RequestMethod.POST)
	@ResponseBody
	public HashMap<String,String> updateUser(@RequestParam("id") int id,@RequestParam("username") String username,@RequestParam("password") String password){
		User unknowuser = userDao.findExtraExistsByUsername(id,username);
		HashMap<String,String> map = new HashMap<String,String>();
		if (unknowuser != null) {
			map.put("status","0");//用户名已存在
		} else {//用户名可用则开始修改
			User user = new User();
			user.setId(id);
			user.setUsername(username);
			user.setPassword(MD5Utils.md5(password));
			userDao.updateById(user);
			map.put("status","1");//修改成功
		}
		return map;
	}
	
	@RequestMapping("/deleteUserById")
	@ResponseBody
	public Map<String,String> deleteUserById(int id) {
		int rows = userDao.deleteById(id);
		Map<String,String> map = new HashMap<String,String>();
		if (rows > 0) {
			map.put("status","1");//删除成功
		} else {
			map.put("status","0");//删除失败
		}
		return map;
	}
	
	//分页
	@RequestMapping(value="/findUsersByPage")
	public String findUsersByPage(int currPage,HttpServletRequest request) {
		PageBean pb = PBUtils.generatorPB(currPage, userDao);
		request.setAttribute("pageBean",pb);
		return "userManage";
	}
	

}
