<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>测试命令发送</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	$(document).ready(function() {
		resetRewardId();
		$("#type").change(function(){
			
            document.getElementById('format').value = "";
            document.getElementById('authType').value = "";
            document.getElementById('authData').value = "";
           
            resetRewardId();
        });
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
        
        function resetRewardId() {
        	var ty = $("#type").find(":selected").val();
        	
            if (ty == "02" || ty == "03") {
                document.getElementById('formatdiv').style.display = "";
                document.getElementById('authTypediv').style.display = "";
                document.getElementById('authDatadiv').style.display = "";
            } else {
            	document.getElementById('formatdiv').style.display = "none";
                document.getElementById('authTypediv').style.display = "none";
                document.getElementById('authDatadiv').style.display = "none";
            }
        }
		
	/*获取Host*/
    function getRootHost(){
        var pathName = window.location.pathname.substring(1);
        var webName = (pathName == ''?'':pathName.substring(0,pathName.indexOf('/')));
        return window.location.protocol + '//' + window.location.host + '/';
    }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/oauth/testCase/">测试命令发送</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="inputEntity" action="${ctx}/oauth/testCase/form" method="post" class="form-horizontal" enctype="multipart/form-data">
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">测试类型：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge required">
				<form:options items="${fns:getDictList('oauth_test_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/></form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
            <label class="control-label">手机号码：</label>
            <div class="controls">
                <form:select path="phoneNo" class="input-xlarge required">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getCardInfoList()}" itemLabel="remarks" itemValue="msisdn" htmlEscape="false"/>
                </form:select>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-group" id = "formatdiv">
            <label class="control-label">编码格式：</label>
            <div class="controls">
                <form:input path="format" htmlEscape="false" maxlength="2" class="input-xlarge required"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-group" id = "authTypediv">
            <label class="control-label">认证类型：</label>
            <div class="controls">
                <form:input path="authType" htmlEscape="false" maxlength="2" class="input-xlarge required"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-group" id = "authDatadiv">
            <label class="control-label">认证数据：</label>
            <div class="controls">
                <form:input path="authData" htmlEscape="false" maxlength="240" class="input-xlarge required"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="发送"/>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>