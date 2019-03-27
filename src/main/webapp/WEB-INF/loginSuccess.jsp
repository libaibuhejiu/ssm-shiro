<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<%@ taglib prefix="mytag" uri="/mytag" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>欢迎</title>
</head>
<body>
	<div id="welcome">
		<h3>欢迎您，
			<shiro:principal property="userName"/>
		</h3>
	</div>
	<div style="float:right;margin-right:80%;margin-top:-40px">
		<a id="logout" href="<%=this.getServletContext().getContextPath() %>/user/logout" >注销</a>
	</div>
	<div id="main">
		<shiro:hasRole name="user"><h6>user</h6></shiro:hasRole>
		<shiro:hasRole name="admin"><h6>admin</h6></shiro:hasRole>
		<shiro:hasPermission name="admin:modify,user:modify_myself">
			<a id="mymessage" href="<%=this.getServletContext().getContextPath() %>/user/myMessage">我的信息</a>
		</shiro:hasPermission>
		<shiro:hasRole name="admin">

			<h3>用户管理>></h3>
			<a href="/ssm-project/user/userMessageManage">用户信息管理</a>
			<a href="/ssm-project/user/userRoleManage">用户角色管理</a>
			<a href="/ssm-project/user/rolePermissionManage">角色权限管理</a>
		</shiro:hasRole>
		<mytag:hasAnyPermission name="user:create,user:delete,user:modify,user:query">
			<h3>用户管理>></h3>
			<a href="/ssm-project/user/userMessageManage">用户信息管理</a>
		</mytag:hasAnyPermission>

	</div>
</body>
</html>