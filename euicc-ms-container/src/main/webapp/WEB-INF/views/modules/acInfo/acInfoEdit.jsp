<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class='container'>
	<div class="modal fade" id="createModule" tabindex="-1" role="dialog" aria-labelledby="acInfoModalLabel" data-backdrop="static" data-keyboard="false" aria-hidden="true">
		<div class="modal-dialog" >
			<div class="modal-content">
				<!-- 对话框标题开始 -->
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
					<h4 class="modal-title" id="acInfoModalLabel"></h4>
				</div>
				<!-- 对话框标题结束-->
				<!-- 内容开始 -->
				<div class="modal-body">
					<div class="portlet-body form">
						<form class="form-horizontal" id="acInfoForm" role="form">
							<div class="form-body">
							<input type="hidden" name="tag" id="tag" value="add">
								<div class="form-group">
									<label class="col-md-4 control-label">HASH<span class="required">*</span></label>
									<div class="col-md-8">
										<div class="input-icon right">
											<i class="fa"></i>
											<input type="text"  name="hash" placeholder="HASH" class="form-control form-filter">
										</div>
										<input type="hidden" name="id"  />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">APDU<span class="required">*</span></label>
									<div class="col-md-8">
										<div class="input-icon right">
											<i class="fa"></i>
											<input type="text"  name="apdu" id="enName" placeholder="APDU指令" class="form-control form-filter">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">NFC<span class="required">*</span></label>
									<div class="col-md-8">
										<div class="input-icon right">
											<i class="fa"></i>
											<input type="text"  name="nfc" placeholder="NFC" class="form-control form-filter">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">ACAID<span class="required">*</span></label>
									<div class="col-md-8">
										<div class="input-icon right">
											<i class="fa"></i>
											<input type="text"  name="acAid" placeholder="ACAID" class="form-control form-filter">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">AC_INDEX<span class="required">*</span></label>
									<div class="col-md-8">
									<div class="input-icon right">
											<i class="fa"></i>
										<input type="text"  name="acIndex" placeholder="AC_INDEX" class="form-control form-filter" onkeyup="value=value.replace(/[^\d]/g, '')">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">APP_AID<span class="required">*</span></label>
									<div class="col-md-8">
									<div class="input-icon right">
											<i class="fa"></i>
										<input type="text"  name="appAid" placeholder="APP_AID" class="form-control form-filter" onkeyup="value=value.replace(/[^\d]/g, '')">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">状态<span class="required">*</span></label>
									<div class="col-md-8">
										<div class="input-icon right">
											<i class="fa"></i>
											<select  class="form-control" name="status">
												<option value="1">启用</option>
												<option value="2">停用</option>
											</select>
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
					<button type="button" class="btn btn-primary" id="addModule">保存</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
				<!-- footer结束 -->
			</div>
		</div>
	</div>
</div>