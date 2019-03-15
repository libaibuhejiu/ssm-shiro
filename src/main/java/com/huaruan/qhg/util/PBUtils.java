package com.huaruan.qhg.util;

import java.util.List;

import com.huaruan.qhg.bean.PageBean;
import com.huaruan.qhg.bean.User;
import com.huaruan.qhg.dao.UserDao;

/**
 * 主要是用来生成PageBean
 * @author yangjian
 *@date 2019/3/15
 */
public class PBUtils {

	/**
	 * 生成PageBean
	 * @param currPage当前页
	 * @param userDao
	 * @return
	 */
	public static PageBean generatorPB(int currPage,UserDao userDao) {
		// currPage是当前的页数
		int pageSize = 10;// 每页显示的个数
		int allCount = userDao.findAllUserCount();
		// 转成double方便Math函数处理allCount
		double allUserCount = allCount;
		// 总页数
		int totalPage = (int) Math.ceil(allUserCount / pageSize);
		// mysql中 使用limit查询的第一个参数为 起始记录数; 第二个为 每页的数量
		int begin = (currPage - 1) * pageSize;// limit的第一个参数
		List<User> userList = userDao.findUsersByPage(begin, pageSize);
		// 封装pageBean
		PageBean pb = new PageBean();
		pb.setAllCount(allCount);
		pb.setCurrPage(currPage);
		pb.setUserList(userList);
		pb.setPageSize(pageSize);
		pb.setTotalPage(totalPage);
		return pb;
	}
}
