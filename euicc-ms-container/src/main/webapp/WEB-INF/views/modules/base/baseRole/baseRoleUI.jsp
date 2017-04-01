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
<link rel="stylesheet" type="text/css"
	href="${ctxResources}/global/plugins/zTree/css/metroStyle/metroStyle.css" />
<!-- END PAGE LEVEL PLUGIN STYLES -->
<link href="${ctxResources}/layout/css/custom.css" rel="stylesheet" type="text/css"/>
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
				<%@ include file="baseRoleList.jsp"%>
				<!-- your code here end-->
			</div>
		</div>
		<%@ include file="baseRoleEdit.jsp"%>
		<%@ include file="baseRoleModule.jsp"%>
		<!-- END CONTENT -->
	</div>

	<jsp:include page="/WEB-INF/views/index/footer.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/views/include/core-plugins.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/views/include/common-js.jsp"></jsp:include>
	<!--add your js begin -->
	<script type="text/javascript"
		src="${ctxResources}/global/plugins/jquery-validation/js/jquery.validate.min.js"></script>
	<script type="text/javascript"
		src="${ctxResources}/global/plugins/jquery-validation/js/additional-methods.min.js"></script>
	<script type="text/javascript"
		src="${ctxResources}/global/plugins/jquery-validation/js/localization/messages_zh.min.js"></script>
	<script src="${ctxResources}/pages/scripts/index.js"
		type="text/javascript"></script>
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
		src="${ctxResources}/global/plugins/zTree/js/jquery.ztree.core-3.5.js"></script>
	<script
		src="${ctxResources}/global/plugins/zTree/js/jquery.ztree.excheck-3.5.js"></script>
	<script
		src="${ctxResources}/global/plugins/zTree/js/jquery.ztree.exedit-3.5.js"></script>
	<script src="${ctxResources}/pages/scripts/zTree.js"></script>
	<script src="${ctxResources}/pages/scripts/base/baseRoleUI.js"></script>

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
		});
		function getPremission(data){
			return "<shiro:hasPermission name='role:update'><a class='btn default btn-xs update blue' name='"+data+"' ><i class='fa fa-edit'></i> 编辑 </a></shiro:hasPermission><shiro:hasPermission name='role:delete'><a name='"+data+"' class='btn default delete btn-xs red'><i class='fa fa-trash-o'></i> 删除 </a></shiro:hasPermission><shiro:hasPermission name='role:permisson'><a name='"+data+"' class='btn default addRole btn-xs red'><i class='fa fa-save'></i>授权 </a></shiro:hasPermission><span></span>";
		}
	</script>
	
</body>
</html>
