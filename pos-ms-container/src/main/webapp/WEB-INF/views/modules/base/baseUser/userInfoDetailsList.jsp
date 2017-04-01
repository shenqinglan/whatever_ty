<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class='container'>
	<div class="modal fade" id="userInfoDetailsTable"
		data-backdrop="static" data-keyboard="false" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content" style="width: 800px;left: -100px; top:50px;">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title" id="detailsLabel">详情显示</h4>
				</div>

				<div class="modal-body">
					<div class="portlet-body">
						<div class="row">
							<div class="col-md-12">
								<div class="portlet light">
									<form role="form" class="portlet-body form" id="searchForm">
										<div class="form-body">
											<div class="row">
												<div class="col-md-6">
													<div class="form-group">
														<label class="control-label col-md-4">账号</label>
														<div class="col-md-8">
															<input type="text" readonly id="detailuserAccount"
																class="form-control form-filter">
														</div>
													</div>
												</div>
												<div class="col-md-6">
													<div class="form-group">
														<label class="control-label col-md-4">用户姓名</label>
														<div class="col-md-8">
															<input type="text" readonly id="detailuserName"
																class="form-control form-filter">
														</div>
													</div>
												</div>
												<div class="col-md-6">
													<div class="form-group">
														<label class="control-label col-md-4">状态</label>
														<div class="col-md-8">
															<input type="text" readonly id="detailuserStatus"
																class="form-control form-filter">
														</div>
													</div>
												</div>
												<div class="col-md-6">
													<div class="form-group">
														<label class="control-label col-md-4">性别</label>
														<div class="col-md-8">
															<input type="text" readonly id="detailuserSex"
																class="form-control form-filter">
														</div>
													</div>
												</div>
												<div class="col-md-6">
													<div class="form-group">
														<label class="control-label col-md-4">电子邮件</label>
														<div class="col-md-8">
															<input type="text" readonly id="detailuserEmail"
																class="form-control form-filter">
														</div>
													</div>
												</div>
												<div class="col-md-6">
													<div class="form-group">
														<label class="control-label col-md-4">手机</label>
														<div class="col-md-8">
															<input type="text" readonly id="detailuserMobile"
																class="form-control form-filter">
														</div>
													</div>
												</div>
												<div class="col-md-6">
													<div class="form-group">
														<label class="control-label col-md-4">办公电话</label>
														<div class="col-md-8">
															<input type="text" readonly id="detailuserOfficePhone"
																class="form-control form-filter">
														</div>
													</div>
												</div>
												<div class="col-md-6">
													<div class="form-group">
														<label class="control-label col-md-4">上次登录时间</label>
														<div class="col-md-8">
															<input type="text" readonly id="detailuserLastLoginTime"
																class="form-control form-filter">
														</div>
													</div>
												</div>
												<div class="col-md-6">
													<div class="form-group">
														<label class="control-label col-md-4">上次登录IP</label>
														<div class="col-md-8">
															<input type="text" readonly id="detailuserLastLoginIp"
																class="form-control form-filter">
														</div>
													</div>
												</div>
												<div class="col-md-6">
													<div class="form-group">
														<label class="control-label col-md-4">备注</label>
														<div class="col-md-8">
															<input type="text" readonly id="detailuserRemark"
																class="form-control form-filter">
														</div>
													</div>
												</div>

											</div>
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal" style="margin-top:-10px">关闭</button>
				</div>
			</div>
		</div>
	</div>
</div>
