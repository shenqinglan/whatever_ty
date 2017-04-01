<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>房屋信息管理</title>
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
		<li class="active"><a href="${ctx}/person/gaHouseInfo/">房屋信息列表</a></li>
		<shiro:hasPermission name="person:gaHouseInfo:edit"><li><a href="${ctx}/person/gaHouseInfo/form">房屋信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="gaHouseInfo" action="${ctx}/person/gaHouseInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>所属小区：</label>
                <form:select path="areaId" class="input-xlarge ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getAreaInfoList()}" itemLabel="areaName" itemValue="id" htmlEscape="false"/>
                </form:select>
            </li>
			<li><label>楼栋号：</label>
				<form:input path="buildingNo" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>单元号：</label>
				<form:input path="unitNo" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>房间号：</label>
				<form:input path="roomNo" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>小区</th>
				<th>标准地址</th>
				<th>通俗地址</th>
				<th>楼栋号</th>
				<th>单元号</th>
				<th>房间号</th>
				<th>房屋类型</th>
				<th>面积</th>
				<th>备注</th>
				<shiro:hasPermission name="person:gaHouseInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="gaHouseInfo">
			<tr>
				<td>
					${gaHouseInfo.areaName}
				</td>
				<td>
					${gaHouseInfo.standardAddress}
				</td>
				<td>
					${gaHouseInfo.commonAddress}
				</td>
				<td>
					${gaHouseInfo.buildingNo}
				</td>
				<td>
					${gaHouseInfo.unitNo}
				</td>
				<td>
					${gaHouseInfo.roomNo}
				</td>
				<td>
					${fns:getDictLabel(gaHouseInfo.houseTypeCode, 'ga_house_type', '')}
				</td>
				<td>
					${gaHouseInfo.size}
				</td>
				<td>
					${gaHouseInfo.remarks}
				</td>
				<shiro:hasPermission name="person:gaHouseInfo:edit"><td>
    				<a href="${ctx}/person/gaHouseInfo/form?id=${gaHouseInfo.id}">修改</a>
					<a href="${ctx}/person/gaHouseInfo/delete?id=${gaHouseInfo.id}" onclick="return confirmx('确认要删除该房屋信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>