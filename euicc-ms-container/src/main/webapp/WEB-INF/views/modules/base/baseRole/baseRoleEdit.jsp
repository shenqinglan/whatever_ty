<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- 新增用户窗口 -->
<div class='container'>
	<div class="modal fade" id="createRole" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static" data-keyboard="false">
		<div class="modal-dialog">
			<div class="modal-content" style="top:100px">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title" id="myModalLabel">新建角色</h4>
				</div>
				<div class="modal-body">
					<div class="portlet-body form">
						<input type="hidden" id="roleEdit"/>
						<form class="form-horizontal" id="roleForm" role="form">
							<div class="form-body">
								<input type="hidden" id="tag" value="add"/>
								<div class="form-group">
									<label class="col-md-4 control-label">角色名称<span class="required">*</span></label>
									<div class="col-md-8">
									<div class="input-icon right">
										<i class="fa"></i>
										<input type="text" id="roleNameEdit" name="roleName" class="form-control"
											placeholder="角色名称"/>
									</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">角色描述</label>
									<div class="col-md-8">
									<div class="input-icon right">
										<i class="fa"></i>
										<input type="text" id="roleDescEdit" name="roleDesc" class="form-control"
											placeholder="角色描述"/>
									</div>
									</div>
								</div>
								</div>
						</form>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" id="addRole">保存</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
</div>

