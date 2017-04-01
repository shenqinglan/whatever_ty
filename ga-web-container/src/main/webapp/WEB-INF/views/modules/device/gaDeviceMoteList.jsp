<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>中继设备管理</title>
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
		<li class="active"><a href="${ctx}/device/gaDeviceMote/">中继设备列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="gaDeviceMote" action="${ctx}/device/gaDeviceMote/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>基站号：</label>
				<form:input path="apNo" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>设备号：</label>
				<form:input path="deviceId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>状态：</label>
            <form:select path="status" class="input-xlarge required">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('ga_device_other_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>基站号</th>
				<th>设备号</th>
				<th>经度</th>
				<th>纬度</th>
				<th>地址</th>
				<th>状态</th>
				<th>备注</th>
				<shiro:hasPermission name="device:gaDeviceMote:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="gaDeviceMote">
			<tr>
				<td><a href="${ctx}/device/gaDeviceMote/form?id=${gaDeviceMote.id}">
					${gaDeviceMote.apNo}
				</a></td>
				<td>
					${gaDeviceMote.deviceId}
				</td>
				<td>
					${gaDeviceMote.longitude}
				</td>
				<td>
					${gaDeviceMote.latitude}
				</td>
				<td>
					${gaDeviceMote.address}
				</td>
				<td>
					${fns:getDictLabel(gaDeviceMote.status, 'ga_device_other_status', '')}
				</td>
				<td>
					${gaDeviceMote.remarks}
				</td>
				<shiro:hasPermission name="device:gaDeviceMote:edit"><td>
    				<a href="${ctx}/device/gaDeviceMote/form?id=${gaDeviceMote.id}">修改</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>