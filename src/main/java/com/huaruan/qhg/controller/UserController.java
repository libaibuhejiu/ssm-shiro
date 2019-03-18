package com.huaruan.qhg.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huaruan.qhg.bean.User;
import com.huaruan.qhg.dao.UserDao;
import com.huaruan.qhg.service.UserService;
import com.huaruan.qhg.util.MD5Utils;

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
	 * ע����
	 * @param username
	 * @param password
	 * @return ���״̬��map,1�ɹ���2���ڣ�0ʧ��
	 */
	@RequestMapping(value="/checkRegister",method=RequestMethod.POST)
	@ResponseBody
	public HashMap<String,String> checkRegister(@RequestParam("username") String username,@RequestParam("password") String password) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(MD5Utils.md5(password));//md5��������
		int sign = userService.register(user);
		HashMap<String,String> map = new HashMap<String, String>();
		if (sign == 1) {
			map.put("status","1");//ע��ɹ�
			return map;
		}
		if (sign == 2) {
			map.put("status","2");//�û����Ѵ���
			return map;
		}
		if (sign == 0) {
			map.put("status","0");//ע��ʧ��
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
		unknowuser.setPassword(md5password);//md5��������
		User user = userService.login(unknowuser.getUsername());
		HashMap<String,String> map = new HashMap<String, String>();
		if (user != null) {
			if (user.getPassword().equals(unknowuser.getPassword())) {
				map.put("status","1");//������ȷ����¼�ɹ�
			} else {
				map.put("status","2");//������󣬵�¼ʧ��
			}
		} else {
			map.put("status","0");//�û���������
		}
		return map;
	}
	
	@RequestMapping(value="/userManage")
	public String userManage(HttpServletRequest request) {
/*		PageBean pb = PBUtils.generatorPB(1, userDao);
		request.setAttribute("pageBean",pb);*/
		PageHelper.startPage(1,10);
		List<User> userList = userDao.findAllUser();
		PageInfo<User> userPageInfo = new PageInfo<User>(userList);
		request.setAttribute("pageInfo",userPageInfo);
		return "userManage";
	}
	
	//�û�������ģ����ѯ
	@RequestMapping(value="/findByUsernameCondition")
	public String findByUsernameCondition(HttpServletRequest request) {
		String usernameCondition = request.getParameter("username");
/*		List<User> userList = userDao.findByUsernameCondition(usernameCondition);
		PageBean pb = PBUtils.generatorPB(1, userDao);
		//��Ҫ����set PageBean�ĸ�������ֵ
		pb.setUserList(userList);
		pb.setAllCount(userList.size());
		//���¼������û�����set
		double allUserCount = userList.size();
		int totalPage = (int) Math.ceil(allUserCount / (pb.getPageSize()));
		pb.setTotalPage(totalPage);
		request.setAttribute("pageBean",pb);*/
		PageHelper.startPage(1,10);
		List<User> userList = userDao.findByUsernameCondition(usernameCondition);
		PageInfo<User> userPageInfo = new PageInfo<User>(userList);
		request.setAttribute("pageInfo",userPageInfo);
		return "userManage";
	}
	
	//�ض��������û�ҳ��
	@RequestMapping("/addWin")
	public String addWin() {
		return "addWin";
	}
	
	//ǰ���޸��û�ҳ��
	@RequestMapping(value="/updateWin")
	public String updateWin(HttpServletRequest request) {
		String strId = request.getParameter("id");
		int id = Integer.parseInt(strId);
		//����id�����û�
		User oldUser = userDao.findById(id);
		request.setAttribute("oldUser",oldUser);
		return "updateWin";
	}
	
	//�޸��û�
	@RequestMapping(value="/updateUser",method=RequestMethod.POST)
	@ResponseBody
	public HashMap<String,String> updateUser(@RequestParam("id") int id,@RequestParam("username") String username,@RequestParam("password") String password){
		User unknowuser = userDao.findExtraExistsByUsername(id,username);
		HashMap<String,String> map = new HashMap<String,String>();
		if (unknowuser != null) {
			map.put("status","0");//�û����Ѵ���
		} else {//�û���������ʼ�޸�
			User user = new User();
			user.setId(id);
			user.setUsername(username);
			user.setPassword(MD5Utils.md5(password));
			userDao.updateById(user);
			map.put("status","1");//�޸ĳɹ�
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
	
	//��ҳ
	@RequestMapping(value="/findUsersByPage")
	public String findUsersByPage(@Param("pageNum")int pageNum,HttpServletRequest request) {
/*		PageBean pb = PBUtils.generatorPB(currPage, userDao);
		request.setAttribute("pageBean",pb);*/
		PageHelper.startPage(pageNum,10);
		List<User> userList = userDao.findAllUser();
		PageInfo<User> userPageInfo = new PageInfo<User>(userList);
		request.setAttribute("pageInfo",userPageInfo);
		return "userManage";
	}
	

}
