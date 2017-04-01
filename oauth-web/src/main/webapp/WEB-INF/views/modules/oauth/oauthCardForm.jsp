<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>卡信息管理</title>
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
		<li><a href="${ctx}/oauth/oauthCard/">卡信息列表</a></li>
		<li class="active"><a href="${ctx}/oauth/oauthCard/form?id=${oauthCard.id}">卡信息<shiro:hasPermission name="oauth:oauthCard:edit">${not empty oauthCard.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="oauth:oauthCard:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="oauthCard" action="${ctx}/oauth/oauthCard/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">国际移动用户标识：</label>
			<div class="controls">
				<form:input path="imsi" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机号：</label>
			<div class="controls">
				<form:input path="msisdn" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
            <label class="control-label">EID：</label>
            <div class="controls">
                <form:input path="eid" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">ICCID：</label>
            <div class="controls">
                <form:input path="iccid" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">卡片计数器：</label>
			<div class="controls">
				<form:input path="count" htmlEscape="false" maxlength="11" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">卡商标识：</label>
			<div class="controls">
				<form:input path="cardmanufacturerid" htmlEscape="false" readOnly = "true" maxlength="2" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
            <label class="control-label">卡片注册状态：</label>
            <div class="controls">
                <form:select path="cardState" class="input-xlarge ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('oauth_register_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </div>
        </div>
		<div class="form-actions">
			<shiro:hasPermission name="oauth:oauthCard:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>