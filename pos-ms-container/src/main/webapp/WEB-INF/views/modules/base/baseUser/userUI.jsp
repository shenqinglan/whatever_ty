<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->

<head>
<%@ include file="/WEB-INF/views/include/meta.jsp"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<jsp:include page="/WEB-INF/views/include/common-css.jsp"></jsp:include>
<jsp:include page="/WEB-INF/views/include/theme-style.jsp"></jsp:include>
<!-- BEGIN PAGE LEVEL PLUGIN STYLES -->
<link
	href="${ctxResources}/global/plugins/bootstrap-daterangepicker/daterangepicker-bs3.css"
	rel="stylesheet" type="text/css" />
<link
	href="${ctxResources}/global/plugins/fullcalendar/fullcalendar.min.css"
	rel="stylesheet" type="text/css" />
<link href="${ctxResources}/global/plugins/jqvmap/jqvmap/jqvmap.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css"
	href="${ctxResources}/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="${ctxResources}/global/plugins/bootstrap-toastr/toastr.min.css" />
<link href="${ctxResources}/layout/css/custom.css" rel="stylesheet" type="text/css"/>
<!-- END PAGE LEVEL PLUGIN STYLES -->
<style type="text/css"> 
/*密码强度*/
.pw-strength {clear: both;position: relative;top: 8px;width: 180px;}
.pw-bar{background: url(${ctxResources}/pages/img/pwd-1.png) no-repeat;height: 14px;overflow: hidden;width: 179px;}
.pw-bar-on{background:  url(${ctxResources}/pages/img/pwd-2.png) no-repeat; width:0px; height:14px;position: absolute;top: 1px;left: 2px;transition: width .5s ease-in;-moz-transition: width .5s ease-in;-webkit-transition: width .5s ease-in;-o-transition: width .5s ease-in;}
.pw-weak .pw-defule{ width:0px;}
.pw-weak .pw-bar-on {width: 60px;}
.pw-medium .pw-bar-on {width: 120px;}
.pw-strong .pw-bar-on {width: 179px;}
.pw-txt {padding-top: 2px;width: 180px;overflow: hidden;}
.pw-txt span {color: #707070;float: left;font-size: 12px;text-align: center;width: 58px;}</style>
<title>${requestScope.pageName}</title>
<!--add your css begin -->
<!--add your css end -->
</head>
<body class="page-header-fixed">
	<%@ include file="/WEB-INF/views/index/header.jsp"%>
	<!-- BEGIN CONTAINER -->
	<div class="page-container">
		<!-- BEGIN SIDEBAR -->
		<div class="page-sidebar-wrapper">
			<div class="page-sidebar navbar-collapse collapse">
				<%@ include file="/WEB-INF/views/index/menus.jsp"%>
			</div>
		</div>
		<!-- BEGIN CONTENT -->
		<div class="page-content-wrapper">
			<div class="page-content">

				<!-- your code here begin-->
				<%@ include file="userInfoList.jsp"%>
				<%@ include file="userInfoDetailsList.jsp"%>
				<!-- your code here end-->
			</div>
		</div>
		<%@ include file="userInfoEdit.jsp"%>
		<!-- END CONTENT -->
	</div>

	<jsp:include page="/WEB-INF/views/index/footer.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/views/include/core-plugins.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/views/include/common-js.jsp"></jsp:include>
	<!--add your js begin -->
	<script src="${ctxResources}/pages/scripts/index.js"
		type="text/javascript"></script>
	<script type="text/javascript"
		src="${ctxResources}/global/plugins/jquery-validation/js/jquery.validate.min.js"></script>
	<script type="text/javascript"
		src="${ctxResources}/global/plugins/jquery-validation/js/additional-methods.min.js"></script>
	<script type="text/javascript"
		src="${ctxResources}/global/plugins/jquery-validation/js/localization/messages_zh.min.js"></script>
	<script type="text/javascript"
		src="${ctxResources}/global/plugins/select2/select2.min.js"></script>
	<script type="text/javascript"
		src="${ctxResources}/global/plugins/datatables/media/js/jquery.dataTables.min.js"></script>
	<script type="text/javascript"
		src="${ctxResources}/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js"></script>
	<script type="text/javascript"
		src="${ctxResources}/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script src="${ctxResources}/global/scripts/datatable.js"></script>
	<script
		src="${ctxResources}/global/plugins/bootstrap-toastr/toastr.min.js"></script>
	<script
		src="${ctxResources}/pages/scripts/common/common-validation-addmethods.js"></script>
	<script src="${ctxResources}/pages/scripts/base/baseUserUI.js"></script>
	<!--add your js end -->
	<script type="text/javascript">
		jQuery(document).ready(function() {
			var ap = ctxResources + '/';
			Metronic.setAssetsPath(ap);
			Metronic.init();
			Layout.init();
			LayoutControl.init();
			var mid = '${param.menu_id}';
			LayoutControl.activeLink('click', mid);
			TableAjax.init();
		});
		
		function getPremission(data){
			return "<a class='btn default details btn-xs blue' name='"+data+"'><i class='fa fa-check-square'></i>详情 </a><shiro:hasPermission name='user:update'><a class='btn default update btn-xs blue' name='"+data+"'><i class='fa fa-edit'></i>编辑 </a></shiro:hasPermission><shiro:hasPermission name='user:delete'> <a name='"+data+"' class='btn default delete btn-xs red'><i class='fa fa-trash-o'></i> 删除 </a></shiro:hasPermission><shiro:hasPermission name='user:status'><a name='"+data+"' class='btn default start btn-xs blue'><i class='fa fa-save'></i> 启用</a><a name='"+data+"' class='btn default stop btn-xs red'><i class='fa fa-save'></i> 禁用 </a></shiro:hasPermission><shiro:hasPermission name='user:resetPass'><a name='"+data+"' class='btn default resetPass btn-xs blue'><i class='fa fa-save'></i>重置密码</a></shiro:hasPermission><span></span>";
		}
	</script>
</body>
</html>
