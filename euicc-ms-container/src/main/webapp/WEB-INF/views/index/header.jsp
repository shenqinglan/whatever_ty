<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="page-header navbar navbar-fixed-top">
	<!-- BEGIN HEADER INNER -->
	<div class="page-header-inner">
		<!-- BEGIN LOGO -->
		<div class="page-logo" style="color:#ffffff">
		 <i class="fa fa-globe" style="font-size: 30px;position: absolute;left:10px;top:18px"></i> 
		<!-- font size=5>
		    <strong>TSM可信服务管理品台V1.0</strong>
		    </font> -->
			<a href="#" style="position: absolute;left:40px;top:-5px">
			 <img src="${ctxResources}/pages/media/bg/logo_head.png" alt="logo" class="logo-default"/> 
			</a>
			<div class="menu-toggler sidebar-toggler hide">
			<!--	 DOC: Remove the above "hide" to enable the sidebar toggler button on header -->
			</div>
		</div>
		<!-- END LOGO -->
		<!-- BEGIN RESPONSIVE MENU TOGGLER -->
		<a href="javascript:;" class="menu-toggler responsive-toggler" data-toggle="collapse" data-target=".navbar-collapse">
		</a>
		<!-- END RESPONSIVE MENU TOGGLER -->
		<!-- BEGIN TOP NAVIGATION MENU -->
		<div class="top-menu">
			<ul class="nav navbar-nav pull-right" >
				<li class="dropdown dropdown-user">
					<a href="#" class="dropdown-toggle"  style="color:#ffffff">
					<!-- 当前用户： -->
    					<shiro:authenticated>  
    							<shiro:principal/>
						</shiro:authenticated> 
						<label style="color:#969696;position:relative;left:15px">|</label>  
							
					</a>
					<ul class="dropdown-menu dropdown-menu-default">
						<!-- <li> -->
							<!--<a href="extra_profile.html">
								<i class="icon-user"></i> 个人信息
							</a>
						</li>-->
						<!--<li>
							<a href="page_calendar.html" tppabs="http://www.keenthemes.com/preview/metronic_admin/page_calendar.html">
								<i class="fa fa-calendar"></i> My Calendar
							</a>
						</li>
						<li>
							<a href="inbox.html" tppabs="http://www.keenthemes.com/preview/metronic_admin/inbox.html">
								<i class="fa fa-envelope"></i> My Inbox
								<span class="badge badge-danger">
									 3
								</span>
							</a>
						</li>
						<li>
							<a href="#">
								<i class="fa fa-tasks"></i> My Tasks
								<span class="badge badge-success">
									 7
								</span>
							</a>
						</li>-->
						<li class="divider">
						</li>
						<!--<li>
							<a href="extra_lock.html">
								<i class="fa fa-lock"></i> 锁屏
							</a>
						</li>-->
						<li>
							<a href="${ctx}/logout"  onclick="return confirm('是否确认退出系统？')">
								<i class="icon-lock"></i> 登出
							</a>
						</li>
					</ul>
				</li>
				<li class="dropdown dropdown-user">
					<a onclick="updatePass()" class="dropdown-toggle"  style="color:#ffffff"><img style="height: 12px;width: 10px;margin-top: 4px;margin-left:0px;" src="${ctxResources}/pages/media/bg/pwd.png" />修改密码<label style="color:#969696;position:relative;left:15px">|</label> </a>
				</li>
				<li class="dropdown dropdown-user" style="margin-left:0px;">
					<a href="${ctx}/logout"  onclick="return confirm('是否确认退出系统？')" class="dropdown-toggle"  style="color:#ffffff"><img style="height: 12px;width: 10px;margin-top: 4px;" src="${ctxResources}/pages/media/bg/out.png" />退出系统</a>
				</li>
				<!-- END USER LOGIN DROPDOWN -->
				<!-- BEGIN QUICK SIDEBAR TOGGLER -->
				<!-- DOC: Apply "dropdown-dark" class after below "dropdown-extended" to change the dropdown styte -->
				<!-- END QUICK SIDEBAR TOGGLER -->
			</ul>
		</div>
		<!-- END TOP NAVIGATION MENU -->
	</div>
	<!-- END HEADER INNER -->
</div>
<%@ include file="updatePass.jsp"%>

