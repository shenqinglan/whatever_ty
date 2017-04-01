<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class='container'>
	<div class="modal fade" id="createModule" tabindex="-1" role="dialog" aria-labelledby="deviceApproveModalLabel" data-backdrop="static" data-keyboard="false" aria-hidden="true">
		<div class="modal-dialog" >
			<div class="modal-content">
				<!-- 对话框标题开始 -->
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
					<h4 class="modal-title" id="deviceApproveModalLabel">审批</h4>
				</div>
				<!-- 对话框标题结束-->
				<!-- 内容开始 -->
				<div class="modal-body">
					<div class="portlet-body form">
						<form class="form-horizontal" id="deviceApproveForm" role="form">
							<div class="form-body">
								<div class="form-group">
									<div style="margin-bottom: 5px">
									<label class="col-md-4 control-label">申请用户</label>
									</div>
									<div class="col-md-8">
										<div class="input-icon right">
											<i class="fa"></i>
											<input type="text"  id="userAccount" name="userAccount" class="form-control form-filter" readonly = "readonly">
										</div>
									</div>
								</div>
								<div class="form-group">
									<div style="margin-bottom: 5px">
									<label class="col-md-4 control-label">累计申请</label>
									</div>
									<div class="col-md-8">
										<div class="input-icon right">
											<i class="fa"></i>
											<input type="text"  id="totalApprovalAmount" name="totalApprovalAmount" class="form-control form-filter" readonly = "readonly">
										</div>
									</div>
								</div>
								<div class="form-group">
									<div style="margin-bottom: 5px">
									<label class="col-md-4 control-label">本次申请<span class="required">*</span></label>
									</div>
									<div class="col-md-8">
										<div class="input-icon right">
											<i class="fa"></i>
											<input type="text"  id="approvalAmount" name="approvalAmount" class="form-control form-filter" onkeyup="value=value.replace(/[^\d]/g,'')">
										</div>
									</div>
								</div>
								<div class="form-group">
									<div style="margin-bottom: 5px">
									<label class="col-md-4 control-label">剩余</label>
									</div>
									<div class="col-md-8">
										<div class="input-icon right">
											<i class="fa"></i>
											<input type="text"  id="realAmount" name="realAmount" class="form-control form-filter" readonly = "readonly">
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
					<button type="button" class="btn btn-primary" id="submit">提交</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
				<!-- footer结束 -->
			</div>
		</div>
	</div>
</div>