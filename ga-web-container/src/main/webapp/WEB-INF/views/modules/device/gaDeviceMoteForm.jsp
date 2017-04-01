<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>中继设备管理</title>
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
		<li><a href="${ctx}/device/gaDeviceMote/">中继设备列表</a></li>
		<li class="active"><a href="${ctx}/device/gaDeviceMote/form?id=${gaDeviceMote.id}">中继设备<shiro:hasPermission name="device:gaDeviceMote:edit">${not empty gaDeviceMote.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="device:gaDeviceMote:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="gaDeviceMote" action="${ctx}/device/gaDeviceMote/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">基站号：</label>
			<div class="controls">
				<form:input path="apId" htmlEscape="false" maxlength="32" readOnly = "true" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">设备号：</label>
			<div class="controls">
				<form:input path="deviceId" htmlEscape="false" maxlength="64" readOnly = "true" class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">经度：</label>
			<div class="controls">
				<form:input path="longitude" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">纬度：</label>
			<div class="controls">
				<form:input path="latitude" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地址：</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="device:gaDeviceMote:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>