<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="page-bar">
	<ul class="page-breadcrumb">
		<li><i class="fa fa-home"></i> 日志管理 <i
			class="fa fa-angle-right"></i></li>
		<li>操作日志</li>
	</ul>
</div>
<div class="portlet box green-haze">
	<!-- 标题 开始-->
	<div class="portlet-title">
		<div class="caption">
			<i class="fa fa-globe"></i>操作日志
		</div>
	</div>
	<!-- 标题 结束-->
	<!-- 查询条件开始 -->
	<div class="portlet-body">
		<div class="row">
			<div class="col-md-12">
				<div class="portlet light">
<!-- 					<div class="caption"> -->
<!-- 						<i class="icon-equalizer font-green-haze"></i> <span -->
<!-- 							class="caption-subject font-green-haze bold uppercase">查询条件</span> -->
<!-- 					</div> -->
					<form role="form" class="portlet-body form" id="searchForm">
						<div class="form-body">
							<div class="row">
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4">操作人</label>
										<div class="col-md-8">
										<input type="text" name="userId" id="userAccount" placeholder="操作人" class="form-control form-filter">
										<!-- <select class="form-control id="userAccount" name="userId">
											<option value="">全部</option>
											<c:forEach items="${userAccount}" var="item">
												<option value="${item.userId}">${item.userAccount}</option>
											</c:forEach>
										</select> -->
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4">起始时间</label>
										<div class="col-md-8">
											<div>
												<input type="text" class="form-control form-filter input-md"  id="dayStart" name="dayStart" placeholder=" "/>
											</div>
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4">结束时间</label>
										<div class="col-md-8">
											<div>
												<input type="text" class="form-control form-filter input-md"  id="dayEnd" name="dayEnd" placeholder=" "/>
											</div>
										</div>
									</div>
								</div>
								<div class="btn-set pull-right" style="margin-top: 15px;">
									<shiro:hasPermission name="baseLogs:search">
									<button class="btn green" type="button" id="search">查询</button>
									</shiro:hasPermission>
									<shiro:hasPermission name="baseLogs:export">
									<button class="btn green" type="button" id="export">导出</button>
									</shiro:hasPermission>
									<button class="btn default" type="button" id="resetForm">重置</button>

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
			<col style="width:10%">
			<col style="width:10%">
			<col style="width:15%">
			<col style="width:65%">
		</colgroup>
		<tbody>
		</tbody>
	</table>
	<!-- 表格结束 -->