<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>修改用户角色</title>
<script type="text/javascript" src="/ssm-project/js/jquery.3.3.1.js"></script>
<script>
window.onload = function(){
	var username = "${user.username}";
	var id = "${user.id}";
	var roleId = "${user.roleId}";
	$("#username").val(username);	
	$("#id").val(id);
	$("#roleId").val(roleId);
}

function checkUpdate(){
	var strid = document.getElementById("id").value;

}
</script>
</head>
<body>
	
	<form action="<%=this.getServletContext().getContextPath() %>/user/updateUserRole" method="post">
		<table>
			<tr hidden="true">
				<td>id:</td>
				<td><input id="id" name="id" type="text"></td>
			</tr>
			<tr>
				<td>用户名:</td>
				<td><input id="username" name="username" type="text" readonly="readonly"></td>
			</tr>
			<tr>
				<td>角色:</td>
				<td>
					<select id="role_select" name="roleId">
						<option value="2">用户</option>
						<option value="1">管理员</option>
					</select>
				</td>
			</tr>
			<tr>
				<td><input type="submit" value="修改"></td>
			</tr>
		</table>
	</form>
</body>


</html>