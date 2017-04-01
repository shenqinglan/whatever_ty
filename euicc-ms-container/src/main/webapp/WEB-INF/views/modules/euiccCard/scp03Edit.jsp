<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class='container'>
	<div class="modal fade" id="createScp03Module" tabindex="-1" role="dialog" aria-labelledby="scp03ModalLabel" data-backdrop="static" data-keyboard="false" aria-hidden="true">
		<div class="modal-dialog" >
			<div class="modal-content">
				<!-- 对话框标题开始 -->
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true" id="scp03Dissmissx">×</button>
					<h4 class="modal-title" id="scp03ModalLabel"></h4>
				</div>
				<!-- 对话框标题结束-->
				<!-- 内容开始 -->
				<div class="modal-body">
					<div class="portlet-body form">
						<form class="form-horizontal" id="scp03Form" role="form">
							<div class="form-body">
							<input type="hidden" name="scp03Tag" id="scp03Tag" value="add">
								<div class="form-group">
									<label class="col-md-4 control-label">SCP03_ID<span class="required">*</span></label>
									<div class="col-md-8">
										<div class="input-icon right">
											<i class="fa"></i>
											<input type="text"  name="scp03Id" placeholder="SCP03_ID" class="form-control form-filter">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">卡ID<span class="required">*</span></label>
									<div class="col-md-8">
										<div class="input-icon right">
											<i class="fa"></i>
											<input type="text"  name="eid" placeholder="卡ID" class="form-control form-filter" readonly = "readonly">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">ISD_P_AID<span class="required">*</span></label>
									<div class="col-md-8">
										<div class="input-icon right">
											<i class="fa"></i>
											<input type="text"  name="isdPAid" placeholder="ISD_P_AID" class="form-control form-filter">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">版本ID<span class="required">*</span></label>
									<div class="col-md-8">
										<div class="input-icon right">
											<i class="fa"></i>
											<input type="text"  name="id" placeholder="版本ID" class="form-control form-filter">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">版本<span class="required">*</span></label>
									<div class="col-md-8">
										<div class="input-icon right">
											<i class="fa"></i>
											<input type="text"  name="version" placeholder="版本" class="form-control form-filter">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">密钥数据<span class="required">*</span></label>
									<div class="col-md-8">
										<div class="input-icon right">
											<i class="fa"></i>
											<input type="text"  name="data" placeholder="密钥数据" class="form-control form-filter">
										</div>
									</div>
								</div>

							</div>
						</form>
					</div>
				</div>
				<!-- 内容结束 -->
				<!-- footer开始 -->
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" id="addScp03Module">保存</button>
					<button type="button" class="btn btn-default" data-dismiss="modal" id="scp03Dissmiss">关闭</button>
				</div>
				<!-- footer结束 -->
			</div>
		</div>
	</div>
</div>