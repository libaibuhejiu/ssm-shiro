<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
<script type="text/javascript" src="/ssm-project/js/jquery.3.3.1.js"></script>
<script>
function checkRegister(){
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
		url:"/ssm-project/user/checkRegister",
		data:{
			"username":username,
			"password":password
		},
		dataType:"json",
		success:function(data){
			var status = data.status;
			if (status == "1") {
				window.location.href="/ssm-project/user/registerSuccess";
			}
			if (status == "2") {
				$("#namestatus").html("用户名已存在！");
			}
			if (status == "0") {
				
			}
		}
	}) 
	
}
</script>
</head>
<body>
	
	<form action="<%=this.getServletContext().getContextPath() %>/user/checkRegister>" method="post">
		<table>
			<tr>
				<td>用户名:</td>
				<td><input id="username" name="username" type="text"></td>
				<td><font id="namestatus" color="red"></font></td>
			</tr>
			<tr>
				<td>密码:</td>
				<td><input id="password" name="password" type="password"></td>
			</tr>
			<tr>
				<td><input type="button" value="注册" onclick="checkRegister()"></td>
				<td><font id="regstatus" color="red"></font></td>
			</tr>
		</table>
	</form>
</body>


</html>