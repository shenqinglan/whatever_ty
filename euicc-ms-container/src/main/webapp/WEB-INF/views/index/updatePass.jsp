<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class='container'>
	<div class="modal fade" id="updatePassModal" tabindex="-1" role="dialog" aria-labelledby="apkInfoModalLabel" data-backdrop="static" data-keyboard="false" aria-hidden="true">
		<div class="modal-dialog" >
			<div class="modal-content">
				<!-- 对话框标题开始 -->
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
					<h4 class="modal-title">修改密码</h4>
				</div>
				<!-- 对话框标题结束-->
				<!-- 内容开始 -->
				<div class="modal-body">
					<div class="portlet-body form">
						<form class="form-horizontal" id="updatePassForm" role="form" method="post" action="${ctx}/apkInfo/save"  role="form" enctype="multipart/form-data" target="hidden_frame">
							<div class="form-body">
								<div class="form-group">
									<label class="col-md-4 control-label">原密码<span class="required">*</span></label>
									<div class="col-md-8">
										<div class="input-icon right">
											<i class="fa"></i>
											<input type="password" name="oldPass"  autocomplete="off" id="oldPass" placeholder="原密码" class="form-control form-filter">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">新密码<span class="required">*</span></label>
									<div class="col-md-8">
										<div class="input-icon right">
											<i class="fa"></i>
											<input type="password" name="newPass"  autocomplete="off" id="newPass" placeholder="新密码" class="form-control form-filter">
										</div>
									</div>
								</div>
								<div class="form-group">
							      	<label class="col-md-4 control-label">确认密码<span class="required">*</span></label>
									<div class="col-md-8">
										<div class="input-icon right">
											<i class="fa"></i>
											<input type="password" id="confirmPass" autocomplete="off" name="confirmPass" placeholder="确认密码" class="form-control form-filter">
										</div>
									</div>
							    </div>
							</div>
							<!-- 内容结束 -->
				<!-- footer开始 -->
				<div class="modal-footer">
					<button type="button" onclick="savePass()" class="btn btn-primary" >保存</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
				<!-- footer结束 -->
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>