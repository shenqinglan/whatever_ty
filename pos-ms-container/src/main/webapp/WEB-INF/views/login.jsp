<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]> <html lang="en" class="no-js"> <![endif]-->
    <!-- BEGIN HEAD -->
<%@ include file="/WEB-INF/views/include/meta.jsp"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
    <head>
    	<jsp:include page="/WEB-INF/views/include/common-css.jsp" />
    	<jsp:include page="/WEB-INF/views/include/theme-style.jsp" />
		<!-- BEGIN PAGE LEVEL STYLES -->
		<link href="${ctxResources}/global/plugins/select2/select2.css" rel="stylesheet" type="text/css"/>
		<link href="${ctxResources}/pages/css/login-soft.css" rel="stylesheet" type="text/css"/>
		<!-- END PAGE LEVEL STYLES -->
        <title>登录</title>
    </head>
    <body class="login">
    	<object classid="clsid:6F73AEDF-E4E9-43D2-9152-62D11B07C29A" id="aaaa" name="aaaa"
		codeBase=/pos-ms-container/resources/cab/TyDevUnLockIoX.ocx#version=1,0,0,0
		data=data:application/x-oleobject;base64,VPpLUhUXNkSyudxeJIvBwwADAAAAAAAAAAAAAA==
        width="0" height="0">
    	</object>
    	<form id="searchForm"><input type="hidden" id="userKey" name="userKey"/></form>
    	<div class="logo" style="color:#EA0000;background-color:#FCFCFC;width:330px;height:395px;position:relative;top:100px">
    		<!-- <font size=5>
		    <strong>TSM可信服务管理品台V1.0</strong>
		    </font> -->
			<a href="#">
				<%-- <img style="height: 18px;width:50px;margin-top: 0px;" src="${ctxResources}/pages/media/bg/line.png" /> --%>
				<img src="${ctxResources}/pages/media/bg/logo_head.png" style="height: 26px;width:300px;margin-top: 0px;margin-left: 0px;" alt="logo"/>
				<img src="${ctxResources}/pages/media/bg/line.png" style="height: 18px;width:380px;margin-top: 10px;margin-left: -42px;" alt="logo"/>
			</a>
			<div class="content" style="margin-top:-5px;margin-left:20px;width:250px;height:250px">
						${errorMsg }
			<ul class="nav nav-tabs">
				<li id="nl"><a href="#normal" data-toggle="tab" style="line-height: 41px;height: 42px;width: 88px;padding: 0;">普通登录</a></li>
				<li id="ml"><a href="#mail" data-toggle="tab" style="line-height: 41px;height: 42px;width: 88px;padding: 0;">邮箱登录</a></li>
			</ul> 
		<div class="tab-content">	
		<div class="tab-pane fade in active" id="normal">	
			<form class="login-form" id = "loginForm" action="${ctx}/login" method="post" style="height:250px;width:270px;margin-left:-35px;margin-top: -30px">
				<!-- <div class="alert alert-danger display-hide" style="padding-bottom: 0px;margin-top: 15px">
					<button class="close" data-close="alert"></button>
					<span>
						请输入用户名、密码和验证码.
					</span>
				</div> -->
				<div class="form-group">
					<div class="input-icon left">
						<i class="fa fa-usermark" style="position:relative;top:10px;left:5px" ><label style="color:#E8E8E8;position:relative;left:20px;top:-22px;font-size: 31px">|</label></i>
						<input class="form-control" type="text" placeholder="用户名" autocomplete="off" id="userName" placeholder="用户名" name="username" style="position:relative;left:10px;padding-left:40px;width:250px;"/>
					</div>
				<!--
					<div class="row">
						<div class="col-md-3">
							<label class="control-label visible-ie8 visible-ie9" style="color:#666666;text-align:right">用户名</label>
						</div>
						<div class="col-md-9">
							
						</div>
					</div>
					ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
					
					
				</div>
				<div class="form-group">
					<!-- <label class="control-label visible-ie8 visible-ie9" style="color:#666666">密码</label> -->
					<div class="input-icon left">
						<i class="fa fa-pwdmark" style="position:relative;top:25px;left:6px"><label style="color:#E8E8E8;position:relative;left:20px;top:-22px;font-size: 31px">|</label></i>
						<input class="form-control" type="password" id="passId" placeholder="密码" autocomplete="off" placeholder="密码" name="password" style="position:relative;left:10px;padding-left:42px;width:250px;"/>
					</div>
				</div>
				
				<div class="form-group">
					<div class="input-icon left">
					    <i class ="fa fa-check-ma" style="position:relative;top:27px;left:6px"><label style="color:#E8E8E8;position:relative;left:20px;top:-22px;font-size: 31px">|</label></i>
						<input class="form-control"  style="width:50%;position:relative;left:10px;padding-left:40px" type="text" id="yzm" name="yzm" placeholder="验证码"/>
						<img src="${ctx}/randomImage" id="randomImage" width="90" height="30" style="position:relative;left:78px;top:-32px" />
					</div>
				</div>
				<div class="form-group">
					<div class="input-icon right" style="position:relative;top:-20px;left:-50px;color:#00b9a4">
						<input type="checkbox" id="saveAccount">记住账号 <input type="checkbox" id="savePass">记住密码
					</div>
				</div>
				<div class="form-actions">
					<button class="btn btn-primary btn-block uppercase" type="submit" style="width:255px;position: relative;left:11px;top:-20px">登录<i class="m-icon-swapright m-icon-white"></i></button>
				</div>
				<div style="margin-top:90px">
					<img src="${ctxResources}/pages/media/bg/logo_ty.png" style="height: 35px;width:105px;margin-top: 0px; " alt="logo"/>
				</div>
				<div style="color:#76a6ba;margin-top:8px">
					Copyright@2014-2020   Irc. All rights reserved.<br>
					     www.whty.com.cn 天喻信息版权所有
				</div>
			</form>
		</div>	
		<div class="tab-pane fade" id="mail">
			<form class="login-form"  style="height:250px;width:270px;margin-left:-35px;margin-top: -30px">
				<div class="form-group" >
						<div class="input-icon left">
							<i class="fa fa-usermark" style="position:relative;top:10px;left:5px" ><label style="color:#E8E8E8;position:relative;left:20px;top:-22px;font-size: 31px">|</label></i>
							<input class="form-control" type="text" placeholder="邮箱地址" autocomplete="off" id="emailAccount" placeholder="邮箱地址" name="emailAccount" style="position:relative;left:10px;padding-left:40px;width:250px;"/>
						</div>
				</div>
				<div class="form-group" id="mailBox">
						<div class="input-icon left" style="height: 65px;">
							<i class="fa fa-pwdmark" style="position:relative;top:25px;left:6px"><label style="color:#E8E8E8;position:relative;left:20px;top:-22px;font-size: 31px">|</label></i>
							<input class="form-control" id="emailCode" placeholder="邮箱验证码" value="" autocomplete="off" placeholder="邮箱验证码" name="emailCode" style="position:relative;left:10px;padding-left:42px;width:50%;"/>
							<button type="button" id="sendMail" style="position:relative;left:73px;top:-32px;height: 32px;width: 100px;font-size: 14px;" class="btn btn-primary btn-lg">发送验证码</button>
						</div>
					</div>
				
				<div class="form-group">
					<div class="input-icon left">
					    <i class ="fa fa-check-ma" style="position:relative;top:27px;left:6px"><label style="color:#E8E8E8;position:relative;left:20px;top:-22px;font-size: 31px">|</label></i>
						<input class="form-control" style="width:50%;position:relative;left:10px;padding-left:40px" type="text" id="yzm2" name="yzm2" placeholder="验证码"/>
						<img src="${ctx}/randomImage" id="randomImage2" width="90" height="30" style="position:relative;left:78px;top:-32px" />
					</div>
				</div> 
				
				<div class="form-actions">
					<button class="btn btn-primary btn-block uppercase" id="mailLogin" type="button" style="width:255px;position: relative;left:11px;top:-20px">登录<i class="m-icon-swapright m-icon-white"></i></button>
				</div>
				<div id="erroMsg" class="collapse">
					<span id="msg"></span>
				</div> 
				<div style="margin-top:115px">
					<img src="${ctxResources}/pages/media/bg/logo_ty.png" style="height: 35px;width:105px;margin-top: 0px; " alt="logo"/>
				</div>
				<div style="color:#76a6ba;margin-top:8px">
					Copyright@2014-2020   Irc. All rights reserved.<br>
					     www.whty.com.cn 天喻信息版权所有
				</div>
			</form>
		</div>	
		</div>	
		</div>
		</div>
	
		
		<jsp:include page="/WEB-INF/views/include/core-plugins.jsp"></jsp:include>
    	<jsp:include page="/WEB-INF/views/include/common-js.jsp"></jsp:include>
    	<!-- page plugins begin-->
    	<script src="${ctxResources}/global/plugins/jquery-validation/js/jquery.validate.min.js"  type="text/javascript"></script>
    	<script src="${ctxResources}/global/plugins/backstretch/jquery.backstretch.min.js" type="text/javascript"></script>
    	<script src="${ctxResources}/global/plugins/select2/select2.min.js" type="text/javascript"></script>
    	<script src="${ctxResources}/global/scripts/jquery.cookie-1.4.1.js" type="text/javascript"></script>
    	<!-- page plugins end-->
    	<script src="${ctxResources}/pages/scripts/login-soft.js" type="text/javascript"></script>
    	<script type="text/javascript">
	    	jQuery(document).ready(function() {
	    		//标签显示切换
	    		$('#nl a').click(function(e){
	    			e.preventDefault();
	    			$('#randomImage').trigger("click");
	    			$(this).tab('show');
	    		})
	    		$('#ml a').click(function(e){
	    			e.preventDefault();
	    			$('#randomImage2').trigger("click");
	    			$(this).tab('show');
	    		})
	    		
	    		$(function(){
					$('#randomImage2').click(function () {//生成验证码     
						$(this).hide().attr('src', '${ctx}/randomImage?' + Math.floor(Math.random()*100) ).fadeIn();    
					});    
				});
	    		//发送邮件按钮事件
       			var count = 60; // 倒数六十下
		        $('#sendMail').click(function(){
		       		var mail = $('#emailAccount').val();
		        	$.ajax({
		        		type:"post",
		        		url: '${ctx}/sendChkEMail',
		        		data:{emailAccount:mail},
		        		success:function(data){
		        			var rsp = $.parseJSON(data)
		        			if(!data.success){
		        				$('#msg').text(rsp.errorMsg);
		        				$('#erroMsg').collapse();
		        			}
		        		}
		        	}) 
		        	var start = setInterval(function(){
		        		// 启动按钮
				       	 if (count == 0) {
	    					$('#sendMail').removeAttr("disabled");
	    					$('#sendMail').html("发送验证码");
	    					count=60;
	    					clearInterval(start);        
			            }
			            else {
			                count--;
			                $('#sendMail').attr("disabled","disabled");
			                $('#sendMail').html("(" + count.toString() + ")秒");
			            }  
		        	},1000);
		        });
	    		$('#mailLogin').click(function(){
		    			var mail = $('#emailAccount').val();
		    			var code = $('#emailCode').val();
		    			var yzm2 = $('#yzm2').val();
		    			if(!mail){
		    				$('#msg').text("邮箱不能为空！");
	        				$('#erroMsg').collapse();
	        				return;
		    			}
		    			if(!code){
		    				$('#msg').text("邮箱验证码不能为空！");
	        				$('#erroMsg').collapse();
	        				return;
		    			}
		    			if(!yzm2){
		    				$('#msg').text("图形验证码不能为空！");
	        				$('#erroMsg').collapse();
	        				return;
		    			}
		    			$.ajax({
		    				type:"post",
		    				url: '${ctx}/verify',
			        		data:{
			        				emailAccount:mail,
			        				emailCode:code,
			        				yzm:yzm2
			        		},
			        		success:function(data){
			        			var rsp = $.parseJSON(data)
			        			if(!rsp.success){
			        				$('#msg').text(rsp.errorMsg);
			        				$('#erroMsg').collapse();
			        			}else{
			        				window.location.href="${ctx}/index";
			        			}
			        		}
		    			})
		    		});
	    		var ap = '${ctxResources}/';
	    		Metronic.setAssetsPath(ap);
			  	Metronic.init(); // init metronic core components
				Layout.init(); // init current layout
			  	Login.init();
			  	LayoutControl.init();
			    // init background slide images
			    $.backstretch([
			       // "${ctxResources}/pages/media/bg/1.jpg",
			       // "${ctxResources}/pages/media/bg/2.jpg",
			       // "${ctxResources}/pages/media/bg/3.jpg",
			       "${ctxResources}/pages/media/bg/bg.png"
			        ], {
			          fade: 1000,
			          duration: 8000
			    }
			    );
			    
			    $('#userName').val($.cookie('username',converter));
		        
		        $('#passId').val($.cookie('password',converter));
		        Metronic.handleFixInputPlaceholderForIE();
		        if($.cookie('cookie_username',converter)){
		        	 $('#saveAccount').parent("span").addClass("checked");
		        }
		        
		        if($.cookie('cookie_password',converter)){
		        	 $('#savePass').parent("span").addClass("checked");
		        }
		        
			    
			    $(function(){
					$('#randomImage').click(function () {//生成验证码     
						$(this).hide().attr('src', '${ctx}/randomImage?' + Math.floor(Math.random()*100) ).fadeIn();    
					});    
				});
			    
			    $.ajax({
					type : 'POST',
					url : ctx + '/clearSession',
					dataType : 'json',
					success : function(data) {
						
					}
				});
			    
			    
			    
			    function converter(val){
					if("null" === val || null === val){
						return undefined;
					}else if("false" === val){
						return false;
					}
					return val;
				}
			    
			});
    	</script>
    </body>
</html>
