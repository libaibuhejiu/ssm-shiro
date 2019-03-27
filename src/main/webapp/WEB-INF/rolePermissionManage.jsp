<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>角色权限修改</title>
<script type="text/javascript" src="/ssm-project/js/jquery.3.3.1.js"></script>
<script type="text/javascript">
	function deletePermission(roleId,permissionId) {
		if (confirm("确认删除该权限吗？")) {
			$.ajax({
				type:"GET",
				url:"/ssm-project/user/deletePermission?roleId="+roleId+"&permissionId="+permissionId,
				data:{
					
				},
				dataType:"json",
				success:function(data){
					var status = data.status;
					if (status == "1") {
						alert("删除成功！");
						window.location.href="/ssm-project/user/rolePermissionManage";
					}
					if (status == "0") {
						alert("删除失败，请重试！");
					}
				},
				error:function (e) {
					alert("error");
				}
			})
		}
		return;
	}
	
	function addPermission(roleId,permissionId){
		if (confirm("确认添加该权限吗？")) {
			$.ajax({
				type:"GET",
				url:"/ssm-project/user/addPermission?roleId="+roleId+"&permissionId="+permissionId,
				data:{
					
				},
				dataType:"json",
				success:function(data){
					var status = data.status;
					if (status == "1") {
						alert("添加成功！");
						window.location.href="/ssm-project/user/rolePermissionManage";
					}
					if (status == "0") {
						alert("添加失败，请重试！");
					}
				},
				error:function (e) {
					alert("error");
				}
			})
		}
		return;
		
	}
	
	function createPermission(roleId){
		var permission = $("#permission_name").val();
		var description = $("#permission_description").val();
		if (permission == "") {
			alert("权限名不能为空！");
			return;
		}
		if (description == "") {
			alert("权限描述不能为空！");
			return;
		}
		if (confirm("确认新增该权限吗？")) {
			$.ajax({
				type:"GET",
				url:"/ssm-project/user/createPermission?roleId="+roleId+"&permission="+permission+"&description="+description,
				data:{
					
				},
				dataType:"json",
				success:function(data){
					var status = data.status;
					if (status == "1") {
						alert("新增权限成功！");
						window.location.href="/ssm-project/user/rolePermissionManage";
					}
					if (status == "0") {
						alert("权限已存在！");
					}
					if (status == "2") {
						alert("新增失败，请重试！");
					}
				},
				error:function (e) {
					alert("error");
				}
			})
		}
		return;
	}
	
	function updatePermission(roleId,permissionId,permission,description){
		window.location.href="/ssm-project/user/updatePermissionWin?roleId="+roleId+"&permissionId="+permissionId+"&permission="+permission+"&description="+description;
	}
	
	function deletePermissionTotally(roleId,permissionId){
		if (confirm("确认完全删除该权限吗？")) {
			$.ajax({
				type:"GET",
				url:"/ssm-project/user/deletePermissionTotally?roleId="+roleId+"&permissionId="+permissionId,
				data:{
					
				},
				dataType:"json",
				success:function(data){
					var status = data.status;
					if (status == "1") {
						alert("删除权限成功！");
						window.location.href="/ssm-project/user/rolePermissionManage";
					}
					
					if (status == "0") {
						alert("删除失败，请重试！");
					}
				},
				error:function (e) {
					alert("error");
				}
			})
		}
	}
	
</script>
</head>
<body>
	<div id="main">
		<div id="class">
			<select name="roleId">
				<option value="2">用户</option>
				<!-- <option value="1">管理员</option> -->
			</select>
		</div>
		<div id="permission">
			<h3>已有权限>></h3>
			<table id="table_rolePermission" border="1">
				<thead>
					<tr>
						<td>角色id</td>
						<td>角色</td>
						<td>权限id</td>
						<td>权限</td>
						<td>权限描述</td>
						<td>操作</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${permissionList }" var="permission">
						<tr>
							<td>2</td>
							<td>用户</td>
							<td>${permission.id }</td>
							<td>${permission.permission }</td>
							<td>${permission.description }</td>
							<td>
								<a href="#" onclick="updatePermission(2,${permission.id},'${permission.permission }','${permission.description }')">修改</a>
								<a href="#" onclick="deletePermission(2,${permission.id})">删除</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<h3>暂无权限>></h3>
			
			<table id="table_addRolePermission" border="1">
				<thead>
					<tr>
						<td>角色id</td>
						<td>角色</td>
						<td>权限id</td>
						<td>权限</td>
						<td>权限描述</td>
						<td>操作</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${unPermissionList }" var="unPermission">
						<tr>
							<td>2</td>
							<td>用户</td>
							<td>${unPermission.id }</td>
							<td>${unPermission.permission }</td>
							<td>${unPermission.description }</td>
							<td>
								<a href="#" onclick="addPermission(2,${unPermission.id})">添加到已有</a>
								<a href="#" onclick="updatePermission(2,${unPermission.id},'${unPermission.permission}','${unPermission.description}')">修改</a>
								<a href="#" onclick="deletePermissionTotally(2,${unPermission.id})">彻底删除</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<form id="createPermission_form">
				<table>
					<tr>
						<td>权限名：</td>
						<td><input id="permission_name" name="permission" type="text"></td>
					</tr>
					<tr>
						<td>权限描述</td>
						<td><input id="permission_description" name="description" type="text"></td>
					</tr>
					<tr>
						<td><input type="button" value="新增权限" onclick="createPermission(2)"></td>
					</tr>
				</table>
			</form>
		</div>
		<div>
			<a href="/ssm-project/user/loginSuccess">返回</a>
		</div>
	</div>
</body>
</html>