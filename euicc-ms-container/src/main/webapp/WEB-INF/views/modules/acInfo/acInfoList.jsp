<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="page-bar">
	<ul class="page-breadcrumb">
		<li><i class="fa fa-home"></i> 天喻 <i
			class="fa fa-angle-right"></i></li>
		<li>示例</li>
	</ul>
</div>
<div class="portlet box green-haze">
	<!-- 标题 开始-->
	<div class="portlet-title">
		<div class="caption">
			<i class="fa fa-globe"></i>示例
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
									<label class="control-label col-md-4">apdu</label>
									<div class="col-md-8">
										<input type="text" name="apdu" id="apdu" placeholder="apdu" class="form-control form-filter">
									</div>
								</div>	
							</div>
							<div class="btn-set pull-right" style="margin-top: 10px;">
								<shiro:hasPermission name="baseFields:create">
								<button class="btn blue" type="button" id="create">新建</button>
								</shiro:hasPermission>
								<shiro:hasPermission name="baseFields:search">
								<button class="btn green"  type="button"  id="search">查询</button>
								</shiro:hasPermission>
								<button class="btn default"  type="button"  id="reset">重置</button>
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
		<colgroup>
			<col >
			<col style="width:200px">
			<col style="width:100px">
			<col style="width:150px">
			<col style="width:120px">
			<col style="width:120px">
			<col style="width:130px">
			<col style="width:130px">
			<col style="width:130px">
		</colgroup>
		<tbody>
		</tbody>
	</table>
	<!-- 表格结束 -->
