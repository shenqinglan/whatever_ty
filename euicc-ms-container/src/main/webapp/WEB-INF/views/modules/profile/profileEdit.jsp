<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class='container'>
	<div class="modal fade" id="createProfile" tabindex="-1" role="dialog" aria-labelledby="acInfoModalLabel" data-backdrop="static" data-keyboard="false" aria-hidden="true">
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
						<form class="form-horizontal" id="profileForm" role="form">
							<div class="form-body">
							<input type="hidden" name="tag" id="tag" value="add">
								<div class="form-group">
									<label class="col-md-4 control-label">iccid<span class="required">*</span></label>
									<div class="col-md-8">
										<div class="input-icon right">
											<i class="fa"></i>
											<input type="text" id="read" name="iccid" readonly="readonly" placeholder="iccid" class="form-control form-filter">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">isdPAid<span class="required">*</span></label>
									<div class="col-md-8">
										<div class="input-icon right">
											<i class="fa"></i>
											<input type="text"  name="isdPAid" placeholder="isdPAid" class="form-control form-filter">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">mnoId<span class="required">*</span></label>
									<div class="col-md-8">
										<div class="input-icon right">
											<i class="fa"></i>
											<input type="text"  name="mnoId" placeholder="mnoId" class="form-control form-filter">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">fallbackAttribute<span class="required">*</span></label>
									<div class="col-md-8">
										<div class="input-icon right">
											<i class="fa"></i>
											<!-- <input type="text"  name="fallbackAttribute" placeholder="回退属性" class="form-control form-filter"> -->
											<select  class="form-control" name="fallbackAttribute">
												<option value="00">00</option>
												<option value="01">01</option>
											</select>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">订阅地址imsi<span class="required">*</span></label>
									<div class="col-md-8">
										<div class="input-icon right">
											<i class="fa"></i>
											<input type="text"  name="imsi" id="enName" placeholder="imsi" class="form-control form-filter">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">订阅地址msisdn<span class="required">*</span></label>
									<div class="col-md-8">
										<div class="input-icon right">
											<i class="fa"></i>
											<input type="text"  name="msisdn" id="enName" placeholder="msisdn" class="form-control form-filter">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">状态<span class="required">*</span></label>
									<div class="col-md-8">
										<div class="input-icon right">
											<i class="fa"></i>
											<input type="text"  name="state" value="00" placeholder="STATE" class="form-control form-filter" readonly="readonly"> 
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">SMDP_ID<span class="required">*</span></label>
									<div class="col-md-8">
										<div class="input-icon right">
											<i class="fa"></i>
											<input type="text"  name="smdpId" placeholder="SMDP_ID" class="form-control form-filter">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">profile类型<span class="required">*</span></label>
									<div class="col-md-8">
									<div class="input-icon right">
											<i class="fa"></i>
										<input type="text"  name="profileType" placeholder="PROFILE_TYPE" class="form-control form-filter">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">总内存<span class="required">*</span></label>
									<div class="col-md-8">
									<div class="input-icon right">
											<i class="fa"></i>
										<input type="text"  name="allocatedMemory" placeholder="ALLOCATED_MEMORY" class="form-control form-filter" onkeyup="value=value.replace(/[^\d]/g, '')">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">空闲内存<span class="required">*</span></label>
									<div class="col-md-8">
									<div class="input-icon right">
											<i class="fa"></i>
										<input type="text"  name="freeMemory" placeholder="FREE_MEMORY" class="form-control form-filter" onkeyup="value=value.replace(/[^\d]/g, '')">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">POL2_ID<span class="required">*</span></label>
									<div class="col-md-8">
									<div class="input-icon right">
											<i class="fa"></i>
										<!--  <input type="text"  name="pol2Id" placeholder="策略规则主键id" class="form-control form-filter" onkeyup="value=value.replace(/[^\d]/g, '')">-->
										<select  class="form-control" name="pol2Id">
										                <option value="">无pol2规则</option>
												<option value="01">01</option>
												<option value="02">02</option>
												<option value="03">03</option>
											</select>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">手机号码<span class="required">*</span></label>
									<div class="col-md-8">
									<div class="input-icon right">
											<i class="fa"></i>
										<input type="text"  name="phoneNo" placeholder="手机号码" class="form-control form-filter" onkeyup="value=value.replace(/[^\d]/g, '')">
										</div>
									</div>
								</div>
								<%--<div class="form-group">
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
								</div>--%>
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