<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>小区成分查询</title>
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
		<li class="active"><a href="${ctx}/person/gaAreaInfo2/">小区成分查询</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="gaAreaInfo2" action="${ctx}/person/gaAreaInfo2/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>所属小区：</label>
				<form:select path="areaId" class="input-xlarge ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getAreaInfoList()}" itemLabel="areaName" itemValue="id" htmlEscape="false"/>
                </form:select>
			</li>
			<li><label>人口类型：</label>
                <form:select path="personType" class="input-medium">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('ga_person_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>小区</th>
				<th>人员类型</th>
				<th>姓名</th>
				<th>身份证号</th>
				<th>所在房屋</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="gaAreaInfo2">
			<tr>
				<td>
					${gaAreaInfo2.area}
				</td>
				<td>
					${fns:getDictLabel(gaAreaInfo2.personType, 'ga_person_type', '')}
				</td>
				<td>
					${gaAreaInfo2.name}
				</td>
				<td>
					${gaAreaInfo2.idCardNo}
				</td>
				<td>
					${gaAreaInfo2.house}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>