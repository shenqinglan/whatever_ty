<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="page-bar">
	<ul class="page-breadcrumb">
		<li><i class="fa fa-home"></i> 系统管理 <i
			class="fa fa-angle-right"></i></li>
		<li>角色管理</li>
	</ul>
	<div class="page-toolbar">
		<div id="dashboard-report-range"
			class="pull-right tooltips btn btn-fit-height grey-salt"
			data-placement="top" data-original-title="更改主面板数据统计时间段">
			<i class="icon-calendar"></i>&nbsp; <span
				class="thin uppercase visible-lg-inline-block">&nbsp;</span>&nbsp; <i
				class="fa fa-angle-down"></i>
		</div>
	</div>
</div>
<div class="portlet box green-haze">
	<div class="portlet-title">
		<div class="caption">
			<i class="fa fa-globe"></i>角色信息
		</div>
	</div>


	<div class="portlet-body">
		<div class="row">
		<div class="col-md-12">
					<div class="portlet light">
					<div class="caption">
						<i class="icon-equalizer font-green-haze"></i>
						<span class="caption-subject font-green-haze bold uppercase">查询条件</span>
						<!-- <span class="caption-helper">some info...</span> -->
					</div>
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
								<div class="form-body">
									<div class="row" id="roleSearchForm">
										<div class="col-md-4">
											<div class="form-group">
												<label class="control-label col-md-3">角色名称</label>
												<div class="col-md-9">
													<input type="text" id="roleNameSearch" name="roleName" placeholder="角色名称" class="form-control">
												</div>
											</div>
										</div>
										<div class="btn-set pull-right">
									<shiro:hasPermission name="role:create">  
									<button class="btn blue" data-toggle="modal" id="create">新建</button>
									</shiro:hasPermission>
									<shiro:hasPermission name="role:search">  
									<button class="btn green" id="search">查询</button>
									</shiro:hasPermission>
									<button class="btn default" type="button" id="reset">重置</button>
									</div>
										<!--/span-->
									</div>
								</div>
							<!-- END FORM-->
						</div>
					</div>
					</div>
				</div>
		<table class="table table-striped table-bordered table-hover"
			id="grid">
			<tbody >
			</tbody>
		</table>
		</div>
	</div>

				