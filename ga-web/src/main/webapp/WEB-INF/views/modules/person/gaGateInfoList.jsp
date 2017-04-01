<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>门禁信息管理</title>
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
		<li class="active"><a href="${ctx}/person/gaGateInfo/">门禁信息列表</a></li>
		<shiro:hasPermission name="person:gaGateInfo:edit"><li><a href="${ctx}/person/gaGateInfo/form">门禁信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="gaGateInfo" action="${ctx}/person/gaGateInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>门禁编码：</label>
				<form:input path="gateCode" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>所属小区：</label>
				<form:select path="areaId" class="input-xlarge ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getAreaInfoList()}" itemLabel="areaName" itemValue="id" htmlEscape="false"/>
                </form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>门禁编码</th>
				<th>门禁类型</th>
				<th>品牌型号</th>
				<th>所属小区</th>
				<th>所属楼栋</th>
				<th>所属单元</th>
				<th>安装时间</th>
				<th>上次维护时间</th>
				<th>备注</th>
				<shiro:hasPermission name="person:gaGateInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="gaGateInfo">
			<tr>
				<td><a href="${ctx}/person/gaGateInfo/form?id=${gaGateInfo.id}">
					${gaGateInfo.gateCode}
				</a></td>
				<td>
                    ${fns:getDictLabel(gaGateInfo.gateType, 'ga_gate_type', '')}
                </td>
				<td>
					${gaGateInfo.brand}
				</td>
				<td>
					${gaGateInfo.areaName}
				</td>
				<td>
					${gaGateInfo.buildingNo}
				</td>
				<td>
                    ${gaGateInfo.unitNo}
                </td>
				<td>
					<fmt:formatDate value="${gaGateInfo.installDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${gaGateInfo.lastMaintainDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${gaGateInfo.remarks}
				</td>
				<shiro:hasPermission name="person:gaGateInfo:edit"><td>
    				<a href="${ctx}/person/gaGateInfo/form?id=${gaGateInfo.id}">修改</a>
					<a href="${ctx}/person/gaGateInfo/delete?id=${gaGateInfo.id}" onclick="return confirmx('确认要删除该门禁信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>