<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="page-bar">
	<ul class="page-breadcrumb">
		<li><i class="fa fa-home"></i> eUICC管理 <i
			class="fa fa-angle-right"></i></li>
		<li>profile录入</li>
	</ul>
</div>
<div class="portlet box green-haze">
	<!-- 标题 开始-->
	<div class="portlet-title">
		<div class="caption">
			<i class="fa fa-globe"></i>显示
		</div>
	</div>
	<!-- 标题 结束-->
	<!-- 查询条件开始 -->
	<div class="portlet-body">
		<div class="row">
			<div class="col-md-12">
			<div class="portlet light">
				<form role="form" class="portlet-body form" id="searchForm">
					<div class="form-body">
						<div class="row">
							<div class="col-md-4">
								<div class="form-group">
									<label class="control-label col-md-4">iccid</label>
									<div class="col-md-8">
										<input type="text" name="iccid" id="iccid" placeholder="iccid" class="form-control form-filter">
									</div>
								</div>	
							</div>
							
							
							<div class="btn-set pull-right" style="margin-top: 10px;">
								<shiro:hasPermission name="profile:create">
								<button class="btn blue" type="button" id="create">新建</button>
								</shiro:hasPermission>
								<shiro:hasPermission name="profile:search">
								<button class="btn green"  type="button"  id="search">查询</button>
								</shiro:hasPermission>
							</div>
						</div>
					</div>
				</form>
			</div>
			</div>
		</div>
	</div>
	</div>
	<!-- 查询条件结束 -->
	<!-- 表格开始 -->
	<table class="table table-striped table-bordered table-hover" id="grid">
		<tbody>
		</tbody>
	</table>
	<!-- 表格结束 -->
