<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>用户管理</title>
<script type="text/javascript" src="/ssm-project/js/jquery.3.3.1.js"></script>
<script type="text/javascript">
	window.onload = function(){
		var isUpdate = "${isUpdate}";
		$("#isUpdate").val(isUpdate);
		if ($("#isUpdate").val() == "0") {
			return;
		}
		if ($("#isUpdate").val() == "1") {
			$("#isUpdate").val("0");
			alert("用户角色修改成功！");
			
		}
	} 
	
	function findUser(){
		var usernameCondition = $("#findCondition").val();
		window.location.href="/ssm-project/user/findByUsernameCondition?username="+usernameCondition;
	}
	
	
	
</script>
</head>
<body>
	<div>
		<!-- 用来看是否更新用户角色，0未更新，1已更新 -->
		<input id="isUpdate" type="text" value="0" hidden="true">
	</div>
	<div>
		<input id="findCondition" type="text" style="margin-left:100px">
		<input id="find_button" type="button" onclick="findUser()" value="查找用户">
	</div>
	<div id="userList">
		<div id="table_area">
			<table id="table_uesr" border="1">
				<thead>
					<tr>
						<td>用户id</td>
						<td>用户名</td>
						<td>角色</td>
						<td>操作</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${pageInfo.list }" var="user">
						<tr>
							<td>${user.id }</td>
							<td>${user.username }</td>
							<td>
								<c:if test="${user.roleId == '1' }">
									<font>管理员</font>
								</c:if>
								<c:if test="${user.roleId == '2' }">
									<font>用户</font>
								</c:if>
							</td>
							<td>
								<a href="/ssm-project/user/updateRoleWin?id=${user.id }&roleId=${user.roleId}">修改</a>
							</td>
						</tr>
					</c:forEach>
						<tr align="center">
						   <td colspan="9">
							第<font style="color: blue">${pageInfo.pageNum }</font>页   
							每页显示<font style="color: blue">${pageInfo.pageSize }</font>条   
							总记录数<font style="color: blue">${pageInfo.total }</font>条  
							<c:if test="${pageInfo.pageNum != 1 }">  <!-- 判断当前页是否第一页,若不是第一页,就显示首页和下一页,反之不显示 -->
							   <a href="${pageContext.request.contextPath }/user/findUsersByPage?pageNum=1" style="text-decoration: none">首页</a>
							   <a href="${pageContext.request.contextPath }/user/findUsersByPage?pageNum=${pageInfo.pageNum - 1}"  style="text-decoration: none">前一页 </a>
							</c:if>
							<c:if test="${pageInfo.pages <= 9 }"> <!-- 分页显示页码这里选择的是9,若是总页数小于等于9,则全部显示 -->
							   <c:forEach begin="1" var="i" end="${pageInfo.pages }">
								<c:if test="${pageInfo.pageNum == i }">
								   <font style="color: red">${i }</font> 
								</c:if>
								<c:if test="${pageInfo.pageNum != i }"> <!-- 选择你要去的页数,假如当前页是4,然后点击6,此时i=6,点击后跳转第6页 -->
									<a href="${pageContext.request.contextPath }/user/findUsersByPage?pageNum=${i }" style="text-decoration: none">${i } </a>
								</c:if>
							   </c:forEach>	
							</c:if>
							<c:if test="${pageInfo.pages > 9 }"><!-- 当总页数大于9的时候 -->
								<c:if test="${pageInfo.pageNum <= 5 }"> <!-- 若是当前页小于5,则显示9个页码后用...代替,表示后面还有 -->
									<c:forEach var="i" begin="1" end="9">
										<c:if test="${pageInfo.pageNum == i }">
											<font style="color: red">${i }</font> 
										</c:if>
										<c:if test="${pageInfo.pageNum != i }">
											<a href="${pageContext.request.contextPath }/user/findUsersByPage?pageNum=${i }" style="text-decoration: none">${i } </a>
										</c:if>
									</c:forEach>
												...
								</c:if>
								<c:if test="${pageInfo.pageNum > 5 }"> <!-- 若是当前页大于5,则需要进行下面的判断 -->
									<c:if test="${pageInfo.pageNum + 4 < pageInfo.pages }">  <!-- 若是这种情况,则为中间显示9个页码,前后均为... -->
										...
										<c:forEach var="i" begin="${pageInfo.pageNum - 4 }" end="${pageInfo.pageNum + 4  }">
											<c:if test="${pageInfo.pageNum == i }">
												<font style="color: red">${i }</font> 
											</c:if>
											<c:if test="${pageInfo.pageNum != i }">
												<a href="${pageContext.request.contextPath }/user/findUsersByPage?pageNum=${i }" style="text-decoration: none">${i } </a>
											</c:if>
										</c:forEach>
										...
									</c:if>
									<c:if test="${pageInfo.pageNum + 4 >= pageInfo.pages }"><!-- 若是这种情况,只需前面加上...即可 -->
										...
										<c:forEach var="i" begin="${pageInfo.pages - 8 }" end="${pageInfo.pages }">
											<c:if test="${pageInfo.pageNum == i }">
												<font style="color: red">${i }</font> 
											</c:if>
											<c:if test="${pageInfo.pageNum != i }">
												<a href="${pageContext.request.contextPath }/user/findUsersByPage?pageNum=${i }" style="text-decoration: none">${i } </a>
											</c:if>
										</c:forEach>
									</c:if>
								</c:if>
							</c:if>
							<c:if test="${pageInfo.pageNum != pageInfo.pages }"> <!-- 此情况和显示首页前一页类似,不再赘述 -->
								<a href="${pageContext.request.contextPath }/user/findUsersByPage?pageNum=${pageInfo.pageNum + 1 }" style="text-decoration: none">后一页</a>
								<a href="${pageContext.request.contextPath }/user/findUsersByPage?pageNum=${pageInfo.pages }" style="text-decoration: none">末页</a>
							</c:if> 
						   </td>
						</tr>
				</tbody>
			</table>
		</div>
	</div>
	<div>
		<a id="return" href="<%=this.getServletContext().getContextPath() %>/user/loginSuccess">返回</a>
	</div>
</body>
</html>