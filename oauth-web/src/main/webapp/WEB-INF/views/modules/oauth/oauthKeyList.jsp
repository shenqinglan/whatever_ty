<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>密钥信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnImport").click(function(){
                $.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true}, 
                    bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
            });
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
    <div id="importBox" class="hide">
        <form id="importForm" action="${ctx}/oauth/oauthKey/import" method="post" enctype="multipart/form-data"
            class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
            <input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
            <input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
            <a href="${ctx}/oauth/oauthKey/import/template">下载模板</a>
        </form>
    </div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/oauth/oauthKey/">密钥信息列表</a></li>
		<shiro:hasPermission name="oauth:oauthKey:edit"><li><a href="${ctx}/oauth/oauthKey/form">密钥信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="oauthKey" action="${ctx}/oauth/oauthKey/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label style = "width: 120px">手机号-EID-ICCID：</label>
                <form:select path="eid" class="input-xlarge ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getCardInfoList()}" itemLabel="remarks" itemValue="eid" htmlEscape="false"/>
                </form:select>
            </li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
			<input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>密钥序号</th>
				<th>VERSION</th>
				<th>MAC密钥</th>
				<th>EID</th>
				<th>ICCID</th>
				<th>手机号</th>
				<shiro:hasPermission name="oauth:oauthKey:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="oauthKey">
			<tr>
				<td><a href="${ctx}/oauth/oauthKey/form?id=${oauthKey.id}">
					${oauthKey.index}
				</a></td>
				<td>
					${oauthKey.version}
				</td>
				<td>
					${oauthKey.macKey}
				</td>
				<td>
					${oauthKey.eid}
				</td>
				<td>
                    ${oauthKey.iccid}
                </td>
				<td>
					${oauthKey.msisdn}
				</td>
				<shiro:hasPermission name="oauth:oauthKey:edit"><td>
    				<a href="${ctx}/oauth/oauthKey/form?id=${oauthKey.id}">修改</a>
					<a href="${ctx}/oauth/oauthKey/delete?id=${oauthKey.id}" onclick="return confirmx('确认要删除该密钥信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>