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
		<li class="active"><a href="${ctx}/person/gaCardInfo/">卡信息列表</a></li>
		<shiro:hasPermission name="person:gaCardInfo:edit"><li><a href="${ctx}/person/gaCardInfo/form">卡信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="gaCardInfo" action="${ctx}/person/gaCardInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>卡片号：</label>
				<form:input path="cardNo" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>发卡至个人：</label>
                <form:select path="personId" class="input-xlarge ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getPersonList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
                </form:select>
            </li>
            <li><label>发卡至房屋：</label>
                <form:select path="houseId" class="input-xlarge ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getHouseList()}" itemLabel="standardAddress" itemValue="id" htmlEscape="false"/>
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
				<th>卡片号</th>
				<th>个人</th>
                <th>身份证号</th>
                <th>小区</th>
                <th>房屋</th>
				<th>卡类别</th>
				<th>有效期至</th>
				<th>是否在黑名单中</th>
				<th>发行单位</th>
				<th>发行时间</th>
				<th>与绑定人员（户主）关系</th>
				<th>备注</th>
				<shiro:hasPermission name="person:gaCardInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="gaCardInfo">
			<tr>
				<td><a href="${ctx}/person/gaCardInfo/form?id=${gaCardInfo.id}">
					${gaCardInfo.cardNo}
				</a></td>
                <td>
                    ${gaCardInfo.person}
                </td>
                <td>
                    ${gaCardInfo.idCardNo}
                </td>
                <td>
                    ${gaCardInfo.area}
                </td>
                <td>
                    ${gaCardInfo.house}
                </td>
				<td>
					${fns:getDictLabel(gaCardInfo.cardTypeCode, 'ga_card_type', '')}
				</td>
				<td>
					<fmt:formatDate value="${gaCardInfo.expiryTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(gaCardInfo.blackListFlag, 'ga_black_list_flag', '')}
				</td>
				<td>
					${gaCardInfo.issuer}
				</td>
				<td>
					<fmt:formatDate value="${gaCardInfo.issueDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(gaCardInfo.relation, 'ga_bind_person_relation', '')}
				</td>
				<td>
					${gaCardInfo.remarks}
				</td>
				<shiro:hasPermission name="person:gaCardInfo:edit"><td>
    				<a href="${ctx}/person/gaCardInfo/form?id=${gaCardInfo.id}">修改</a>
					<a href="${ctx}/person/gaCardInfo/delete?id=${gaCardInfo.id}" onclick="return confirmx('确认要删除该卡信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>