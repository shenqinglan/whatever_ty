<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- 新增用户窗口 -->
<div class='container'>
	<div class="modal fade" id="createUser" tabindex="-1"  data-backdrop="static" data-keyboard="false"  role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content" >
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title" id="myModalLabel">新建用户</h4>
				</div>
				<div class="modal-body">
					<div class="portlet-body form">
						<form class="form-horizontal" id="userForm" role="form">
							<div class="form-body">
								<input type="hidden" id="userIdAdd">
								<div class="form-group">
									<label class="col-md-3 control-label">账号<span class="required">*</span></label>
									<div class="col-md-9">
										<div class="input-icon right">
										<i class="fa"></i>
										<input type="text" id="userAccountAdd" name="userAccount" class="form-control"
											placeholder="用户账号">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">密码<span class="required">*</span></label>
									<div class="col-md-9">
									<div class="input-icon right">
										<i class="fa"></i>
										<input type="password" id="userPassWordAdd"  autocomplete="off"  name="userPassWord" class="form-control"
											placeholder="用户密码">
									<div id="level" class="pw-strength">           	
										<div class="pw-bar"></div>
										<div class="pw-bar-on"></div>
										<div class="pw-txt">
										<span>弱</span>
										<span>中</span>
										<span>强</span>
										</div>
									</div>	
									</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">用户姓名<span class="required">*</span></label>
									<div class="col-md-9">
									<div class="input-icon right">
										<i class="fa"></i>
										<input type="text" id="userNameAdd" name="userName" class="form-control"
											placeholder="用户姓名">
									</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">性别<span class="required">*</span></label>
									<div class="col-md-9">
									<div class="input-icon right">
										<i class="fa"></i>
										<select class="form-control" name="userSex" id="userSexAdd">
											<option value="0">男</option>
											<option value="1">女</option>
										</select>
									</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">电子邮件</label>
									<div class="col-md-9">
										<div class="input-icon right">
										<i class="fa"></i>
										<input type="text" id="userEmailAdd" name="userEmail" class="form-control"
											placeholder="电子邮件">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">手机</label>
									<div class="col-md-9">
									<div class="input-icon right">
										<i class="fa"></i>
										<input type="text" id="userPhoneAdd" name="userPhone" class="form-control"
											placeholder="手机">
									</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">办公电话</label>
									<div class="col-md-9">
									<div class="input-icon right">
										<i class="fa"></i>
										<input type="text" id="userOFAdd" name="userOF" class="form-control"
											placeholder="办公电话，例如：027-88888888">
									</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">用户角色<span class="required">*</span></label>
									<div class="col-md-9">
									<div class="input-icon right">
										<i class="fa"></i>
										<select class="form-control" name="userRole" id="userRoleAdd">
										</select>
									</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">备注</label>
									<div class="col-md-9">
									<div class="input-icon right">
										<i class="fa"></i>
										<input type="text" id="userRemarkAdd" name="userRemark" class="form-control"
											placeholder="备注">
									</div>
									</div>
								</div>
						</form>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" id="addUser">保存</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript"> 

</script>
