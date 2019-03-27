package com.huaruan.qhg.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import com.huaruan.qhg.bean.Permission;
import com.huaruan.qhg.bean.Role;
import com.huaruan.qhg.bean.User;

public interface UserDao {
	/**
	 * 通过用户名查找用户
	 * @param username
	 * @return 用户
	 */
	User findByUsername(String username);
	
	/**
	 * ����û�����
	 * @return �û�����
	 */
	int findAllUserCount();
	
	/**
	 * permission表里是否存在该permission
	 * @return 存在的个数
	 */
	int findPermissionCount(String permission);
	
	/**
	 * ������ʼ��¼����ÿҳ������ѯ���û��б�
	 * @param begin��ʼ�ļ�¼����Ҳ���Ǵӵڼ�����¼��ʼ
	 * @param pageSizeÿҳ��ʾ���û���
	 * @return
	 */
	List<User> findUsersByPage(@Param("begin") int begin,@Param("pageSize") int pageSize);
	
	/**
	 * �����û���ģ����ѯ
	 * @param usernameCondition
	 * @return �û��б�
	 */
	List<User> findByUsernameCondition(String usernameCondition);
	
	/**
	 * �����û�����id���ҳ�����id֮��������û�
	 * @param map������int id,String username
	 * @return
	 */
	User findExtraExistsByUsername(@Param("id")int id,@Param("username")String username);
	
	/**
	 * ����id�����û�
	 * @param id
	 * @return�û�����
	 */
	User findById(int id);
	
	/**
	 * 根据permission从permission表中查找permission的id
	 * @param permission
	 * @return
	 */
	int findByPerms(@Param("permission")String permission);
	
	/**
	 * 插入新用户
	 * @param user�û�
	 */
	void insertUser(User user);
	
	/**
	 * 插入user_role表
	 * @param userId
	 * @param roleId
	 */
	void insertUserRole(@Param("user_id")String userId,@Param("role_id")String roleId);
	
	/**
	 * 向permission表中新增权限
	 * @param permission
	 * @param description
	 */
	@Transactional
	public void insertIntoPermission(@Param("permission")String permission,@Param("description")String description);
	
	/**
	 * 向role_permission表插入
	 */
	void insertIntoRP(@Param("role_id")int roleId,@Param("permission_id")int permissionId,@Param("enable")int enable);
	
	/**
	 * ���������û�
	 * @return
	 */
	List<User> findAllUser();
	
	/**
	 * ����user��id�޸��û���Ϣ
	 * @param user
	 */
	void updateById(User user);
	
	/**
	 * ����idɾ���û�
	 * @param id
	 */
	int deleteById(int id);
	
	/**
	 * 根据用户id查找出角色列表
	 * @param userId
	 * @return
	 */
	List<Role> findRoleListByUserId(@Param("user_id")int userId);
	
	/**
	 * 根据role_id找出Role实例
	 * @param roleId
	 * @return
	 */
	Role findRoleByRoleId(@Param("role_id")String roleId);
	
	/**
	 * 根据用户id查找出role_id
	 * @param userId
	 * @return
	 */
	List<String> findRoleIdListByUserId(@Param("user_id")int userId);

	/**
	 * 根据角色id查找出权限id列表
	 * @param roleId
	 * @return
	 */
	List<Integer> findPermissionListByRoleId(@Param("role_id")String roleId);
	
	/**
	 * 根据权限id找出权限
	 * @param permissionId
	 * @return
	 */
	Permission findPermissionByPermissionId(@Param("id")int permissionId);
	
	/**
	 * 根据roleId和permissionId删除对应权限,其实是将enable置为0
	 * @param roleId
	 * @param permissionId
	 */
	int deletePermissionBy2Id(@Param("role_id")int roleId,@Param("permission_id")int permissionId);
	
	/**
	 * 根据id删除permission表的记录
	 * @param roleId
	 * @param permissionId
	 */
	void deletePermissionFormPermission(@Param("id")int id);
	
	/**
	 *  根据roleId和permissionId删除role_permission表的记录
	 * @param roleId
	 * @param permissionId
	 */
	void deletePermissionFormRP(@Param("role_id")int roleId,@Param("permission_id")int permissionId);

	
	/**
	 * 根据roleId和permissionId添加对应权限
	 * @param roleId
	 * @param permissionId
	 */
	int addPermissionBy2Id(@Param("role_id")int roleId,@Param("permission_id")int permissionId);
	
	
	/**
	 * 根据role_id确定roleName
	 * @param roleId
	 * @return
	 */
	String findRoleNameByRoleId(@Param("role_id")String roleId);
	
	/**
	 * 在user表中根据用户id修改role_id
	 * @param id
	 * @param roleId
	 */
	void updateRoleIdInUserTable(@Param("id")int id,@Param("role_id")int roleId);
	
	/**
	 * 在user_role表中根据用户id修改role_id
	 * @param userId
	 * @param roleId
	 */
	void updateRoleIdInUserRoleTable(@Param("user_id")int userId,@Param("role_id")int roleId);
	
	/**
	 * 根据permissionId修改Permission表
	 * @param permission
	 * @param decription
	 */
	void updatePermissionById(@Param("id")int id,@Param("permission")String permission,@Param("description")String description);
	
	/**
	 * 根据permission在permission表中查找有几个同名权限
	 * @param permission
	 * @return
	 */
	int findPermissionByPermission(@Param("permission")String permission);
	
	/**
	 * 根据角色Id找出权限id列表
	 * @param roleId
	 * @return
	 */
	List<Integer> findPermissionIdListByRoleId(@Param("role_id")int roleId);
	
	/**
	 * 根据角色Id找出没有的权限id列表
	 * @param roleId
	 * @return
	 */
	List<Integer> findUnPermissionIdListByRoleId(@Param("role_id")int roleId);
}
