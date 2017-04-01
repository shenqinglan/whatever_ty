<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class='container'>
	<div class="modal fade" id="viewBaseFields" tabindex="-1" role="dialog" aria-labelledby="baseFieldsModalLabel" data-backdrop="static" data-keyboard="false" aria-hidden="true">
		<div class="modal-dialog" >
			<div class="modal-content" style="top:50px;">
				<!-- 对话框标题开始 -->
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
					<h4 class="modal-title" id="baseFieldsModalLabel">数据字典</h4>
				</div>
				<!-- 对话框标题结束-->
				<!-- 内容开始 -->
				<div class="modal-body">
					<div class="portlet-body form">
						<form class="form-horizontal" id="baseFieldsForm" role="form">
							<div class="form-body">
							<input type="hidden" name="tag" id="tag" value="add">
								<div class="form-group">
									<label class="col-md-4 control-label">字段名称<span class="required">*</span></label>
									<div class="col-md-8">
										<div class="input-icon right">
											<i class="fa"></i>
											<input type="text"  name="fieldName" placeholder="字段名称" class="form-control form-filter">
										</div>
										<input type="hidden" name="fieldId"  />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">字段<span class="required">*</span></label>
									<div class="col-md-8">
										<div class="input-icon right">
											<i class="fa"></i>
											<input type="text"  name="field" id="enName" placeholder="字段" class="form-control form-filter">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">字段值<span class="required">*</span></label>
									<div class="col-md-8">
										<div class="input-icon right">
											<i class="fa"></i>
											<input type="text"  name="valueField" placeholder="字段值" class="form-control form-filter">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">字段显示值<span class="required">*</span></label>
									<div class="col-md-8">
										<div class="input-icon right">
											<i class="fa"></i>
											<input type="text"  name="displayField" placeholder="字段显示值" class="form-control form-filter">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">显示顺序<span class="required">*</span></label>
									<div class="col-md-8">
									<div class="input-icon right">
											<i class="fa"></i>
										<input type="text"  name="sort" placeholder="排序" class="form-control form-filter" onkeyup="value=value.replace(/[^\d]/g, '')">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">是否启用<span class="required">*</span></label>
									<div class="col-md-8">
										<div class="input-icon right">
											<i class="fa"></i>
											<select  class="form-control" name="enabled">
												<option value="1">启用</option>
												<option value="0">停用</option>
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
					<button type="button" class="btn btn-primary" id="saveBaseFields">保存</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
				<!-- footer结束 -->
			</div>
		</div>
	</div>
</div>