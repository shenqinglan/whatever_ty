<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="page-bar">
	<ul class="page-breadcrumb">
		<li><i class="fa fa-home"></i> eUICC管理 <i
			class="fa fa-angle-right"></i></li>
		<li>更新POL2</li>
	</ul>
</div>
<div class="portlet box green-haze">
	<!-- 标题 开始-->
	<div class="portlet-title">
		<div class="caption">
			<i class="fa fa-globe"></i>更新POL2
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
										<label class="control-label col-md-4">eUICC卡主键EID</label>
										<div class="col-md-8">
											<input type="text" name="eid" id="eid" placeholder="EID"
												class="form-control form-filter">
										</div>
									</div>
								</div>
								<div class="btn-set pull-right">
								<shiro:hasPermission name="updatePol2:search">
									<button class="btn green" type="button" id="search">查询</button>
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
