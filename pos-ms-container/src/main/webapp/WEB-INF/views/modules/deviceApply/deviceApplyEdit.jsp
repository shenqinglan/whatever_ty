<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class='container'>
	<div class="modal fade" id="createModule" tabindex="-1" role="dialog" aria-labelledby="deviceApplyModalLabel" data-backdrop="static" data-keyboard="false" aria-hidden="true">
		<div class="modal-dialog" >
			<div class="modal-content">
				<!-- 对话框标题开始 -->
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
					<h4 class="modal-title" id="deviceApplyModalLabel"></h4>
				</div>
				<!-- 对话框标题结束-->
				<!-- 内容开始 -->
				<div class="modal-body">
					<div class="portlet-body form">
						<form class="form-horizontal" id="deviceApplyForm" role="form">
							<div class="form-body">
								<div class="form-group">
									<div style="margin-bottom: 5px">
									<label class="col-md-4 control-label">解锁次数<span class="required">*</span></label>
									</div>
									<div class="col-md-8">
										<div class="input-icon right">
											<i class="fa"></i>
											<input type="text"  id="approvalAmount" name="approvalAmount" placeholder="解锁次数" class="form-control form-filter" onkeyup="value=value.replace(/[^\d]/g,'')">
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