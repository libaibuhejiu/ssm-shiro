package com.huaruan.qhg.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.huaruan.qhg.bean.User;

public interface UserDao {
	/**
	 * 根据用户名查找用户
	 * @param username用户名
	 * @return 用户
	 */
	User findByUsername(String username);
	
	/**
	 * 查出用户总数
	 * @return 用户总数
	 */
	int findAllUserCount();
	
	/**
	 * 根据起始记录数和每页数量查询出用户列表
	 * @param begin起始的记录数，也就是从第几条记录开始
	 * @param pageSize每页显示的用户数
	 * @return
	 */
	List<User> findUsersByPage(@Param("begin") int begin,@Param("pageSize") int pageSize);
	
	/**
	 * 根据用户名模糊查询
	 * @param usernameCondition
	 * @return 用户列表
	 */
	List<User> findByUsernameCondition(String usernameCondition);
	
	/**
	 * 根据用户名和id查找出除此id之外的其他用户
	 * @param map包含了int id,String username
	 * @return
	 */
	User findExtraExistsByUsername(@Param("id")int id,@Param("username")String username);
	
	/**
	 * 根据id查找用户
	 * @param id
	 * @return用户对象
	 */
	User findById(int id);
	
	/**
	 * 插入新用户
	 * @param user用户
	 */
	void insertUser(User user);
	
	/**
	 * 查找所有用户
	 * @return
	 */
	List<User> findAllUser();
	
	/**
	 * 根据user的id修改用户信息
	 * @param user
	 */
	void updateById(User user);
	
	/**
	 * 根据id删除用户
	 * @param id
	 */
	int deleteById(int id);

}
