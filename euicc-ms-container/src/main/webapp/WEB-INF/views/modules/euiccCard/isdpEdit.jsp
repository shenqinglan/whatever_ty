<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class='container'>
	<div class="modal fade" id="createIsdpModule" tabindex="-1" role="dialog" aria-labelledby="isdpModalLabel" data-backdrop="static" data-keyboard="false" aria-hidden="true">
		<div class="modal-dialog" >
			<div class="modal-content">
				<!-- 对话框标题开始 -->
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true" id="isdpDissmissx">×</button>
					<h4 class="modal-title" id="isdpModalLabel"></h4>
				</div>
				<!-- 对话框标题结束-->
				<!-- 内容开始 -->
				<div class="modal-body">
					<div class="portlet-body form">
						<form class="form-horizontal" id="isdpForm" role="form">
							<div class="form-body">
							<input type="hidden" name="isdpTag" id="isdpTag" value="add">
								<div class="form-group">
									<label class="col-md-4 control-label">P_ID<span class="required">*</span></label>
									<div class="col-md-8">
										<div class="input-icon right">
											<i class="fa"></i>
											<input type="text"  name="pId" placeholder="P_ID" class="form-control form-filter">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">卡ID<span class="required">*</span></label>
									<div class="col-md-8">
										<div class="input-icon right">
											<i class="fa"></i>
											<input type="text"  name="eid" placeholder="卡ID" class="form-control form-filter" readonly="readonly">
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
									<label class="col-md-4 control-label">ISDP状态<span class="required">*</span></label>
									<div class="col-md-8">
										<div class="input-icon right">
											<i class="fa"></i>
											<select  class="form-control" name="isdPState">
												<option value="1">启用</option>
												<option value="2">关闭</option>
											</select>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">下载文件AID<span class="required">*</span></label>
									<div class="col-md-8">
										<div class="input-icon right">
											<i class="fa"></i>
											<input type="text"  name="isdPLoadfileAid" placeholder="下载文件AID" class="form-control form-filter">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">模块AID<span class="required">*</span></label>
									<div class="col-md-8">
										<div class="input-icon right">
											<i class="fa"></i>
											<input type="text"  name="isdPModuleAid" placeholder="模块AID" class="form-control form-filter">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">内存空间<span class="required">*</span></label>
									<div class="col-md-8">
										<div class="input-icon right">
											<i class="fa"></i>
											<input type="text"  name="allocatedMemory" placeholder="内存空间" class="form-control form-filter">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">连接参数<span class="required">*</span></label>
									<div class="col-md-8">
										<div class="input-icon right">
											<i class="fa"></i>
											<input type="text"  name="connectivityParam" placeholder="连接参数" class="form-control form-filter">
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
					<button type="button" class="btn btn-primary" id="addIsdpModule">保存</button>
					<button type="button" class="btn btn-default" data-dismiss="modal" id="isdpDissmiss">关闭</button>
				</div>
				<!-- footer结束 -->
			</div>
		</div>
	</div>
</div>