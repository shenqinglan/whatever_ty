<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- 新增用户窗口 -->
<div class='container'>
	<div class="modal fade" id="createModule" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"  data-backdrop="static" data-keyboard="false">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title" id="myModalLabel">新建菜单</h4>
				</div>
				<div class="modal-body">
					<div class="portlet-body form">
						<form class="form-horizontal" id="moduleForm" role="form">
							<div class="form-body">
								<input type="hidden" name="tag" value="add">
								<div class="form-group">
									<label class="col-md-3 control-label">节点类型<span class="required">*</span></label>
									<div class="col-md-9">
									<div class="input-icon right">
										<i class="fa"></i>
										<select class="form-control" name="leafType"  id="leafTypeEdit" onchange="changeLeafType(this)">
											<option value="0">主菜单</option>
											<option value="1">页面</option>
											<option value="2">动作</option>
										</select>
									</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">菜单编码<span class="required">*</span></label>
									<div class="col-md-9">
									<div class="input-icon right">
										<i class="fa"></i>
										<input type="text" id="moduleIdEdit" name="moduleId" class="form-control"
											placeholder="菜单编码">
									</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">菜单名称<span class="required">*</span></label>
									<div class="col-md-9">
									<div class="input-icon right">
										<i class="fa"></i>
										<input type="text" id="moduleNameEdit" name="moduleName" class="form-control"
											placeholder="菜单名称">
									</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">菜单URL<span id="moduleForm_moduleUrl_span" class="required">*</span></label>
									<div class="col-md-9">
									<div class="input-icon right">
										<i class="fa"></i>
										<input type="text" id="moduleForm_moduleUrl"  name="moduleUrl" class="form-control"
											placeholder="菜单URL">
									</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">父级菜单<span class="required">*</span></label>
									<div class="col-md-9">
									<div class="input-icon right">
										<i class="fa"></i>
										<input type="text" id="moduleForm_parentId" name="parentId" class="form-control" 
											placeholder="父级菜单">
									</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">显示顺序<span class="required">*</span></label>
									<div class="col-md-9">
									<div class="input-icon right">
										<i class="fa"></i>
										<input type="text" id="displayIndexEdit"  name="displayIndex" class="form-control"
											placeholder="显示顺序">
									</div>
									</div>
								</div>
								<!-- <div class="form-group">
									<label class="col-md-3 control-label">展开状态</label>
									<div class="col-md-9">
									<div class="input-icon right">
										<i class="fa"></i>
										<select class="form-control" name="expandedStatus"  id="expandedStatusEdit">
											<option value="0">收缩</option>
											<option value="1">展开</option>
										</select>
									</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">是否展示</label>
									<div class="col-md-9">
									<div class="input-icon right">
										<i class="fa"></i>
										<select class="form-control" name="isDisplay" id="isDisplayEdit">
											<option value="1">是</option>
											<option value="0">否</option>
										</select>
									</div>
									</div>
								</div>  -->
								<div class="form-group">
									<label class="col-md-3 control-label">CSS样式</label>
									<div class="col-md-9">
									<div class="input-icon right">
										<i class="fa"></i>
										<input type="text" id="iconCssEdit" name="iconCss"  class="form-control"
											placeholder="CSS样式">
									</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">菜单说明</label>
									<div class="col-md-9">
									<div class="input-icon right">
										<i class="fa"></i>
										<input type="text" id="informationEdit" name="information" class="form-control"
											placeholder="菜单说明">
									</div>
									</div>
								</div>
						</form>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" id="addModule">保存</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
</div>

