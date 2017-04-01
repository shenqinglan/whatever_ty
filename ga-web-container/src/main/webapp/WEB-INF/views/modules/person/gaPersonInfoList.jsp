<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>个人信息管理</title>
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
		<li class="active"><a href="${ctx}/person/gaPersonInfo/">个人信息列表</a></li>
		<shiro:hasPermission name="person:gaPersonInfo:edit"><li><a href="${ctx}/person/gaPersonInfo/form">个人信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="gaPersonInfo" action="${ctx}/person/gaPersonInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>身份证号：</label>
				<form:input path="idCardNo" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>身份证号</th>
				<th>身份证有效期至</th>
				<th>发卡机关</th>
				<th>姓名</th>
				<th>性别</th>
				<th>民族</th>
				<th>出生年月日</th>
				<th>政治面貌</th>
				<th>教育程度</th>
				<th>身高</th>
				<th>婚姻状况</th>
				<th>配偶姓名</th>
				<th>配偶身份证号</th>
				<th>人口类型</th>
				<th>户籍地编码</th>
				<th>户籍地址</th>
				<th>居住地址</th>
				<th>曾住地址</th>
				<th>血型</th>
				<th>宗教信仰</th>
				<th>工作单位</th>
				<th>电话</th>
				<th>手机号码</th>
				<th>人脸信息</th>
				<th>备注</th>
				<shiro:hasPermission name="person:gaPersonInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="gaPersonInfo">
			<tr>
				<td><a href="${ctx}/person/gaPersonInfo/form?id=${gaPersonInfo.id}">
					${gaPersonInfo.idCardNo}
				</a></td>
				<td>
					<fmt:formatDate value="${gaPersonInfo.expiryTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${gaPersonInfo.issueOrgan}
				</td>
				<td>
					${gaPersonInfo.name}
				</td>
				<td>
					${fns:getDictLabel(gaPersonInfo.sex, 'ga_gender', '')}
				</td>
				<td>
					${gaPersonInfo.ethnic}
				</td>
				<td>
					<fmt:formatDate value="${gaPersonInfo.birthday}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(gaPersonInfo.politicalStatus, 'ga_political_status', '')}
				</td>
				<td>
					${fns:getDictLabel(gaPersonInfo.educationDegree, 'ga_education_degree', '')}
				</td>
				<td>
					${gaPersonInfo.height}
				</td>
				<td>
					${fns:getDictLabel(gaPersonInfo.maritalStatus, 'ga_marital_flag', '')}
				</td>
				<td>
					${gaPersonInfo.spouseName}
				</td>
				<td>
					${gaPersonInfo.spouseIdCardNo}
				</td>
				<td>
					${fns:getDictLabel(gaPersonInfo.personTypeCode, 'ga_person_type', '')}
				</td>
				<td>
					${gaPersonInfo.hukouAreaNo}
				</td>
				<td>
					${gaPersonInfo.hukouAddress}
				</td>
				<td>
					${gaPersonInfo.residenceAddress}
				</td>
				<td>
					${gaPersonInfo.formerAddress}
				</td>
				<td>
					${gaPersonInfo.bloodType}
				</td>
				<td>
					${gaPersonInfo.religion}
				</td>
				<td>
					${gaPersonInfo.company}
				</td>
				<td>
					${gaPersonInfo.tel}
				</td>
				<td>
					${gaPersonInfo.mobil}
				</td>
				<td>
					${gaPersonInfo.face}
				</td>
				<td>
					${gaPersonInfo.remarks}
				</td>
				<shiro:hasPermission name="person:gaPersonInfo:edit"><td>
    				<a href="${ctx}/person/gaPersonInfo/form?id=${gaPersonInfo.id}">修改</a>
					<a href="${ctx}/person/gaPersonInfo/delete?id=${gaPersonInfo.id}" onclick="return confirmx('确认要删除该个人信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>