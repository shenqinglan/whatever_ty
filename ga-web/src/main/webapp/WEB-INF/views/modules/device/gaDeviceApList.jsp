<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>基站设备管理</title>
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
		<li class="active"><a href="${ctx}/device/gaDeviceAp/">基站设备列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="gaDeviceAp" action="${ctx}/device/gaDeviceAp/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>设备号：</label>
				<form:input path="deviceId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>状态：</label>
			<form:select path="status" class="input-xlarge required">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('ga_device_ap_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>设备号</th>
				<th>经度</th>
				<th>纬度</th>
				<th>地址</th>
				<th>状态</th>
				<th>备注</th>
				<shiro:hasPermission name="device:gaDeviceAp:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="gaDeviceAp">
			<tr>
				<td><a href="${ctx}/device/gaDeviceAp/form?id=${gaDeviceAp.id}">
					${gaDeviceAp.deviceId}
				</a></td>
				<td>
					${gaDeviceAp.longitude}
				</td>
				<td>
					${gaDeviceAp.latitude}
				</td>
				<td>
					${gaDeviceAp.address}
				</td>
				<td>
					${fns:getDictLabel(gaDeviceAp.status, 'ga_device_ap_status', '')}
				</td>
				<td>
					${gaDeviceAp.remarks}
				</td>
				<shiro:hasPermission name="device:gaDeviceAp:edit"><td>
    				<a href="${ctx}/device/gaDeviceAp/form?id=${gaDeviceAp.id}">修改</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>