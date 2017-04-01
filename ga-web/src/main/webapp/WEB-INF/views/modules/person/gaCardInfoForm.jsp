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
		<li><a href="${ctx}/person/gaCardInfo/">卡信息列表</a></li>
		<li class="active"><a href="${ctx}/person/gaCardInfo/form?id=${gaCardInfo.id}">卡信息<shiro:hasPermission name="person:gaCardInfo:edit">${not empty gaCardInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="person:gaCardInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="gaCardInfo" action="${ctx}/person/gaCardInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">卡片号：</label>
			<div class="controls">
				<form:input path="cardNo" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
            <label class="control-label">发卡至个人：</label>
            <div class="controls">
                <form:select path="personId" class="input-xlarge ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getPersonList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
                </form:select>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">发卡至房屋：</label>
            <div class="controls">
                <form:select path="houseId" class="input-xlarge ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getHouseList()}" itemLabel="standardAddress" itemValue="id" htmlEscape="false"/>
                </form:select>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">卡类别：</label>
			<div class="controls">
				<form:select path="cardTypeCode" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('ga_card_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">有效期至：</label>
			<div class="controls">
				<input name="expiryTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${gaCardInfo.expiryTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否在黑名单中：</label>
			<div class="controls">
				<form:select path="blackListFlag" class="input-xlarge">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('ga_black_list_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否需要人脸信息：</label>
			<div class="controls">
				<form:select path="faceInfoFlag" class="input-xlarge">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('ga_face_info_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发行单位：</label>
			<div class="controls">
				<form:input path="issuer" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发行时间：</label>
			<div class="controls">
				<input name="issueDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${gaCardInfo.issueDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进入次数限制：</label>
			<div class="controls">
				<form:input path="inLimitCount" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出去次数限制：</label>
			<div class="controls">
				<form:input path="outLimitCount" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">与绑定人员（户主）关系：</label>
			<div class="controls">
				<form:select path="relation" class="input-xlarge required">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('ga_bind_person_relation')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="person:gaCardInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>