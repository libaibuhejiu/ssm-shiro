<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script type="text/javascript" src="/ssm-project/js/jquery.3.3.1.js"></script>
<script>
function checkLogin(){
	var username = document.getElementById("username").value;
	var password = document.getElementById("password").value;
	var rememberMe = document.getElementById("rememberMe").checked;
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
		url:"/ssm-project/user/checkLogin",
		data:{
			"username":username,
			"password":password,
			"rememberMe":rememberMe
		},
		dataType:"json",
		success:function(data){
			var status = data.status;
			if (status == "1") {
				window.location.href="/ssm-project/user/loginSuccess";
			} else {
				$("#loginstatus_u").html(status);
			}
			/* if (status == "0") {
				$("#loginstatus_p").html("");
				$("#loginstatus_u").html("用户名不存在！");
			}
			if (status == "2") {
				$("#loginstatus_u").html("");
				$("#loginstatus_p").html("密码错误！");
			} */
			
		}
	}) 
}
</script>
</head>
<body>

	<form action="<%=this.getServletContext().getContextPath() %>/user/checkLogin>" method="post">
		<table>
			<tr>
				<td>用户名:</td>
				<td><input id="username" name="username" type="text"></td>
				<td><font id="loginstatus_u" color="red"></font></td>
			</tr>
			<tr>
				<td>密码:</td>
				<td><input id="password" name="password" type="password"></td>
				<td><font id="loginstatus_p" color="red"></font></td>
			</tr>
			<tr>
				<td>
					<input type="checkbox" id="rememberMe" name="rememberMe" value="true">
					记住我
				</td>
				
			</tr>
			<tr>
				<td><input type="button" value="登录" onclick="checkLogin()"></td>
				
			</tr>
		</table>
		<a id="register" href="<%=this.getServletContext().getContextPath() %>/user/register">注册</a>
	</form>
	
</body>
</html>