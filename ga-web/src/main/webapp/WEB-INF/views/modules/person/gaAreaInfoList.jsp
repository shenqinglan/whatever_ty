<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>小区信息管理</title>
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
		<li class="active"><a href="${ctx}/person/gaAreaInfo/">小区信息列表</a></li>
		<shiro:hasPermission name="person:gaAreaInfo:edit"><li><a href="${ctx}/person/gaAreaInfo/form">小区信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="gaAreaInfo" action="${ctx}/person/gaAreaInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>小区名称：</label>
				<form:input path="areaName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>小区编号：</label>
				<form:input path="areaNo" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>小区名称</th>
				<th>城市编号</th>
				<th>区域编号</th>
				<th>小区编号</th>
				<th>小区地址</th>
				<th>小区类型</th>
				<th>备注</th>
				<shiro:hasPermission name="person:gaAreaInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="gaAreaInfo">
			<tr>
				<td><a href="${ctx}/person/gaAreaInfo/form?id=${gaAreaInfo.id}">
					${gaAreaInfo.areaName}
				</a></td>
				<td>
					${gaAreaInfo.cityNo}
				</td>
				<td>
					${gaAreaInfo.districtNo}
				</td>
				<td>
					${gaAreaInfo.areaNo}
				</td>
				<td>
					${gaAreaInfo.areaAddress}
				</td>
				<td>
					${fns:getDictLabel(gaAreaInfo.areaTypeCode, 'ga_area_type', '')}
				</td>
				<td>
					${gaAreaInfo.remarks}
				</td>
				<shiro:hasPermission name="person:gaAreaInfo:edit"><td>
    				<a href="${ctx}/person/gaAreaInfo/form?id=${gaAreaInfo.id}">修改</a>
					<a href="${ctx}/person/gaAreaInfo/delete?id=${gaAreaInfo.id}" onclick="return confirmx('确认要删除该小区信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>