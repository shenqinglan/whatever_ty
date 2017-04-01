<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>卡信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/oauth/oauthCard/">卡信息列表</a></li>
		<shiro:hasPermission name="oauth:oauthCard:edit"><li><a href="${ctx}/oauth/oauthCard/form">卡信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="oauthCard" action="${ctx}/oauth/oauthCard/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label style="width:120px">国际移动用户标识：</label>
				<form:input path="imsi" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>手机号：</label>
				<form:input path="msisdn" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>EID：</label>
				<form:input path="eid" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>ICCID：</label>
                <form:input path="iccid" htmlEscape="false" maxlength="32" class="input-medium"/>
            </li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>国际移动用户标识</th>
				<th>手机号</th>
				<th>卡片计数器</th>
				<th>卡商标识</th>
				<th>EID</th>
				<th>ICCID</th>
				<th>卡片注册状态</th>
				<shiro:hasPermission name="oauth:oauthCard:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="oauthCard">
			<tr>
				<td><a href="${ctx}/oauth/oauthCard/form?id=${oauthCard.id}">
					${oauthCard.imsi}
				</a></td>
				<td>
					${oauthCard.msisdn}
				</td>
				<td>
					${oauthCard.count}
				</td>
				<td>
					${oauthCard.cardmanufacturerid}
				</td>
				<td>
					${oauthCard.eid}
				</td>
				<td>
                    ${oauthCard.iccid}
                </td>
				<td>
					${fns:getDictLabel(oauthCard.cardState, 'oauth_register_status', '')}
				</td>
				<shiro:hasPermission name="oauth:oauthCard:edit"><td>
    				<a href="${ctx}/oauth/oauthCard/form?id=${oauthCard.id}">修改</a>
					<a href="${ctx}/oauth/oauthCard/delete?id=${oauthCard.id}" onclick="return confirmx('确认要删除该卡信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>