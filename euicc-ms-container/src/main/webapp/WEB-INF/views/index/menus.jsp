<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<ul class="page-sidebar-menu  page-sidebar-menu-light" style="background-color: #4b5453;" data-auto-scroll="true" data-slide-speed="200">
	<!-- BEGIN SIDEBAR MENU -->
	<li class="sidebar-toggler-wrapper">
		<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
		<!-- <div class="text" style="text-align:center">
		主菜单
		</div> -->
		<!-- <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp</span><font size=5>主菜单</font> -->
		<!-- <span class ="title" style="font-size:25px" overflow:hidden>&nbsp;&nbsp;&nbsp;&nbsp;主菜单</span> -->
		<span id="span1" style="color:#ffffff; word-wrap: break-word;word-break : break-all;width:500px;font-size:20px;white-space:nowrap;display:block;margin-top:10px;overflow-y:no-display !important;overflow: hidden !important;position:relative;left:45px;user-select:none;overflow-x:hidden;">主菜单</span>
		<span class="sidebar-toggler hidden-phone" style="display:block;margin-top:-32px;position:relative;" id="span2" onclick="hiddernmain()"></span>
		<!--  onclick="hiddernmain()" style="display:block;margin-top:-32px;position:relative;" id="span2"-->
	</li>
	<li style="margin-top:10px;"></li>
	
	${sessionScope.menustr}
	<%-- <c:forEach items="${sessionScope.menustr}" var="menus"></c:forEach> --%>
	
</ul>
<!-- END SIDEBAR MENU -->