<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>人员行踪查询</title>
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
		<li class="active"><a href="${ctx}/person/gaEntranceInfo2/">出入记录列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="gaEntranceInfo" action="${ctx}/person/gaEntranceInfo2/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>身份证号：</label>
				<form:input path="idCardNo" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>开始时间：</label>
			<input name="startTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                    value="<fmt:formatDate value="${entranceInfo.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                    onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
            </li>
            <li><label>结束时间：</label>
            <input name="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                    value="<fmt:formatDate value="${entranceInfo.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                    onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
            </li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>姓名</th>
                <th>身份证号</th>
                <th>刷卡时间</th>
                <th>出还是入</th>
				<th>小区</th>
				<th>单元</th>
				<th>房屋</th>
				<th>人脸信息</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="gaEntranceInfo">
			<tr>
			    <td>
                    ${gaEntranceInfo.name}
                </td>
                <td>
                    ${gaEntranceInfo.idCardNo}
                </td>
                <td>
                    <fmt:formatDate value="${gaEntranceInfo.swipeTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
                <td>
                    ${fns:getDictLabel(gaEntranceInfo.inOrOut, 'ga_in_out', '')}
                </td>
				<td>
					${gaEntranceInfo.areaName}
				</td>
				<td>
					${gaEntranceInfo.door}
				</td>
				<td>
                    ${gaEntranceInfo.house}
                </td>
				<td>
					${gaEntranceInfo.face}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>