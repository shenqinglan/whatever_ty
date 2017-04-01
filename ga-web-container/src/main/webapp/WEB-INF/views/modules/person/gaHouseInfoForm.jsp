<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>房屋信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/person/gaHouseInfo/">房屋信息列表</a></li>
		<li class="active"><a href="${ctx}/person/gaHouseInfo/form?id=${gaHouseInfo.id}">房屋信息<shiro:hasPermission name="person:gaHouseInfo:edit">${not empty gaHouseInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="person:gaHouseInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="gaHouseInfo" action="${ctx}/person/gaHouseInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">所属小区：</label>
            <div class="controls">
                <form:select path="areaId" class="input-xlarge ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getAreaInfoList()}" itemLabel="areaName" itemValue="id" htmlEscape="false"/>
                </form:select>
            </div>
		</div>
		<div class="control-group">
            <label class="control-label">所属楼栋  + 单元号：</label>
            <div class="controls">
                <form:select path="gateId" class="input-xlarge ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getGateInfoList()}" itemLabel="remarks" itemValue="id" htmlEscape="false"/>
                </form:select>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">房间号：</label>
            <div class="controls">
                <form:input path="roomNo" htmlEscape="false" maxlength="32" class="input-xlarge "/>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">标准地址：</label>
			<div class="controls">
				<form:input path="standardAddress" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">通俗地址：</label>
			<div class="controls">
				<form:input path="commonAddress" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">房屋类型：</label>
			<div class="controls">
				<form:select path="houseTypeCode" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('ga_house_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">面积：</label>
			<div class="controls">
				<form:input path="size" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交付时间：</label>
			<div class="controls">
				<input name="issueDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${gaHouseInfo.issueDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="person:gaHouseInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>