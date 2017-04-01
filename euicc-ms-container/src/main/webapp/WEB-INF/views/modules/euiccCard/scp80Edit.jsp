<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class='container'>
	<div class="modal fade" id="createScp80Module" tabindex="-1" role="dialog" aria-labelledby="scp80ModalLabel" data-backdrop="static" data-keyboard="false" aria-hidden="true">
		<div class="modal-dialog" >
			<div class="modal-content">
				<!-- 对话框标题开始 -->
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true" id="scp80Dissmissx">×</button>
					<h4 class="modal-title" id="scp80ModalLabel"></h4>
				</div>
				<!-- 对话框标题结束-->
				<!-- 内容开始 -->
				<div class="modal-body">
					<div class="portlet-body form">
						<form class="form-horizontal" id="scp80Form" role="form">
							<div class="form-body">
							<input type="hidden" name="scp80Tag" id="scp80Tag" value="add">
								<div class="form-group">
									<label class="col-md-4 control-label">SCP80_ID<span class="required">*</span></label>
									<div class="col-md-8">
										<div class="input-icon right">
											<i class="fa"></i>
											<input type="text"  name="scp80Id" placeholder="SCP80_ID" class="form-control form-filter">
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
					<button type="button" class="btn btn-primary" id="addScp80Module">保存</button>
					<button type="button" class="btn btn-default" data-dismiss="modal" id="scp80Dissmiss">关闭</button>
				</div>
			</div>
		</div>
	</div>
</div>