<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>修改角色权限</title>
<script type="text/javascript" src="/ssm-project/js/jquery.3.3.1.js"></script>
<script>
window.onload = function(){
	var roleId = "${roleId}";
	var permissionId = "${permission.id}";
	var permission = "${permission.permission}";
	var description = "${permission.description}";
	$("#roleId").val(roleId);	
	$("#permission").val(permission);
	$("#description").val(description);
	$("#permissionId").val(permissionId);
}

function updatePermission(){
	var permissionId = $("#permissionId").val();
	var permission = $("#permission").val();
	var description = $("#description").val();
	$.ajax({
		type:"GET",
		url:"/ssm-project/user/updatePermission?permissionId="+permissionId+"&permission="+permission+"&description="+description,
		data:{
			
		},
		dataType:"json",
		success:function(data){
			var status = data.status;
			if (status == "1") {
				alert("修改权限成功！");
				window.location.href="/ssm-project/user/rolePermissionManage";
			}
			if (status == "0") {
				alert("修改失败，请重试！");
			}
			if (status == "2") {
				alert("权限已存在！");
			}
		},
		error:function (e) {
			alert("error");
		}
	})
}
</script>
</head>
<body>
	
	<form action="<%=this.getServletContext().getContextPath() %>/user/updateUserRole" method="post">
		<table>
			<tr>
				<td>角色id:</td>
				<td><input id="roleId" name="roleId" type="text" readonly="readonly"></td>
			</tr>
			<tr>
				<td>权限id:</td>
				<td><input id="permissionId" name="permissionId" type="text" readonly="readonly"></td>
			</tr>
			<tr>
				<td>权限：:</td>
				<td><input id="permission" name="permission" type="text"></td>
			</tr>
			<tr>
				<td>权限描述：</td>
				<td><input id="description" name="description" type="text"></td>
				
			</tr>
			<tr>
				<td><input type="button" onclick="updatePermission()" value="修改"></td>
			</tr>
		</table>
	</form>
	<a href="/ssm-project/user/rolePermissionManage">返回</a>
</body>


</html>