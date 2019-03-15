<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>修改用户</title>
<script type="text/javascript" src="/ssm-project/js/jquery.3.3.1.js"></script>
<script>
window.onload = function(){
	var username = "${oldUser.username}";
	var password = "${oldUser.password}";
	var id = "${oldUser.id}";
	$("#username").val(username);	
	$("#password").val(password);
	$("#id").val(id);
}

function checkUpdate(){
	var strid = document.getElementById("id").value;
	var id = parseInt(strid);
	var username = document.getElementById("username").value;
	var password = document.getElementById("password").value;
	if (username == "") {
		alert("用户名不能为空！");
		return;
	}
	if (password == "") {
		alert("密码不能为空！");
		return;
	}
	$.ajax({
		type:"POST",
		url:"/ssm-project/user/updateUser",
		data:{
			"id":id,
			"username":username,
			"password":password
		},
		dataType:"json",
		success:function(data){
			var status = data.status;
			if (status == "1") {
				alert("修改成功！");
				window.location.href="/ssm-project/user/userManage";
			}
			if (status == "0") {
				$("#namestatus").html("用户名已存在！");
			}
		}
	}) 
	
}
</script>
</head>
<body>
	
	<form action="<%=this.getServletContext().getContextPath() %>/user/updateUser>" method="post">
		<table>
			<tr hidden="true">
				<td>id:</td>
				<td><input id="id" type="text"></td>
			</tr>
			<tr>
				<td>用户名:</td>
				<td><input id="username" name="username" type="text"></td>
				<td><font id="namestatus" color="red"></font></td>
			</tr>
			<tr>
				<td>密码:</td>
				<td><input id="password" name="password" type="text"></td>
			</tr>
			<tr>
				<td><input type="button" value="修改" onclick="checkUpdate()"></td>
				<td><font id="regstatus" color="red"></font></td>
			</tr>
		</table>
	</form>
</body>


</html>