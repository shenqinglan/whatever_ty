<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="page-bar">
	<ul class="page-breadcrumb">
		<li><i class="fa fa-home"></i> 系统管理 <i
			class="fa fa-angle-right"></i></li>
		<li>用户管理</li>
	</ul>
</div>
<div class="portlet box green-haze">
	<div class="portlet-title">
		<div class="caption">
			<i class="fa fa-globe"></i>用户信息
		</div>
	</div>


	<div class="portlet-body">
		<div class="row">
		<div class="col-md-12">
					<div class="portlet light">
						<div class="portlet-body form">
						<!-- BEGIN FORM-->
								<div class="form-body">
									<div class="row" id="userSearchForm">
										<div class="col-md-4">
											<div class="form-group">
												<label class="control-label col-md-5">用户姓名</label>
												<div class="col-md-7">
													<input type="text" id="userNameSearch" name="userName" placeholder="用户姓名" class="form-control">
												</div>
											</div>
										</div>
										<!--/span-->
										<div class="col-md-4">
											<div class="form-group">
												<label class="control-label col-md-5">账号</label>
												<div class="col-md-7">
													<input type="text" id="userAccountSearch" name="userAccount" placeholder="账号" class="form-control">
												</div>
											</div>
										</div>
										<div class="btn-set pull-right" style="margin-top: 3px;">
									<shiro:hasPermission name="user:create">  
									<button class="btn blue" data-toggle="modal" id="create">新建</button>
									</shiro:hasPermission>
									<shiro:hasPermission name="user:search"> 
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
			</div>
			</div>
		<table class="table table-striped table-bordered table-hover"
			id="grid">
		<colgroup>
			<col >
			<col style="width:180px">
			<col style="width:100px">
			<col style="width:150px">
			<col style="width:120px">
			<col style="width:380px">
		</colgroup>
			<tbody id="tb" >
			</tbody>
		</table>
		
	

				