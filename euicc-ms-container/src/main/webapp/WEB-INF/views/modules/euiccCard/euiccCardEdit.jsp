<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class='container'>
	<div class="modal fade" id="createModule" tabindex="-1" role="dialog" aria-labelledby="euiccCardModalLabel" data-backdrop="static" data-keyboard="false" aria-hidden="true">
		<div class="modal-dialog" >
			<div class="modal-content">
				<!-- 对话框标题开始 -->
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
					<h4 class="modal-title" id="euiccCardModalLabel"></h4>
				</div>
				<!-- 对话框标题结束-->
				<!-- 内容开始 -->
				<div class="modal-body">
					<div class="portlet-body form">
						<form class="form-horizontal" id="euiccCardForm" role="form">
							<div class="form-body">
							<input type="hidden" name="tag" id="tag" value="add">
								<div class="form-group">
									<label class="col-md-4 control-label">卡ID<span class="required">*</span></label>
									<div class="col-md-8">
										<div class="input-icon right">
											<i class="fa"></i>
											<input type="text"  name="eid" placeholder="卡ID" class="form-control form-filter">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">生产日期<span class="required">*</span></label>
									<div class="col-md-8">
										<div class="input-icon right">
											<i class="fa"></i>
											 <!-- <input type="text" name="productiondate" placeholder="生产日期（如：2016-09-22 13:17:23）" class="form-control form-filter">  -->
											<input type="text" name="productiondate" id="d233" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"placeholder="生产日期" class="form-control form-filter"/>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">卡商ID<span class="required">*</span></label>
									<div class="col-md-8">
										<div class="input-icon right">
											<i class="fa"></i>
											<input type="text"  name="eumId" placeholder="卡商ID" class="form-control form-filter">
										</div>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-md-4 control-label">平台类型<span class="required">*</span></label>
									<div class="col-md-8">
										<div class="input-icon right">
											<i class="fa"></i>
											<input type="text"  name="platformtype" placeholder="平台类型" class="form-control form-filter">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">平台版本<span class="required">*</span></label>
									<div class="col-md-8">
									<div class="input-icon right">
											<i class="fa"></i>
										<input type="text"  name="platformversion" placeholder="平台版本" class="form-control form-filter"  >
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">剩余空间<span class="required">*</span></label>
									<div class="col-md-8">
									<div class="input-icon right">
											<i class="fa"></i>
										<input type="text"  name="remainingmemory" placeholder="剩余空间" class="form-control form-filter"  >
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">空闲空间<span class="required">*</span></label>
									<div class="col-md-8">
									<div class="input-icon right">
											<i class="fa"></i>
										<input type="text"  name="availablememoryforprofiles" placeholder="空闲空间" class="form-control form-filter"  >
										</div>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-md-4 control-label">SMSR_ID<span class="required">*</span></label>
									<div class="col-md-8">
									<div class="input-icon right">
											<i class="fa"></i>
										<input type="text"  name="smsrId" placeholder="SMSR_ID" class="form-control form-filter"  >
										</div>
									</div>
								</div>
								
								<input type="hidden"  name="ecasdId" >
								<input type="hidden"  name="capabilityId" >
								

								<div class="form-group">
									<label class="col-md-4 control-label">是否支持CAT_TP<span class="required">*</span></label>
									<div class="col-md-8">
										<div class="input-icon right">
											<i class="fa"></i>
											<select  class="form-control" name="catTpSupport">
												<option value="1">支持</option>
												<option value="2">不支持</option>
											</select>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">CAT_TP版本<span class="required">*</span></label>
									<div class="col-md-8">
									<div class="input-icon right">
											<i class="fa"></i>
										<input type="text"  name="catTpVersion" placeholder="CAT_TP版本" class="form-control form-filter"  >
										</div>
									</div>
								</div>

								<div class="form-group">
									<label class="col-md-4 control-label">是否支持HTTP<span class="required">*</span></label>
									<div class="col-md-8">
										<div class="input-icon right">
											<i class="fa"></i>
											<select  class="form-control" name="httpSupport">
												<option value="1">支持</option>
												<option value="2">不支持</option>
											</select>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">HTTPS版本<span class="required">*</span></label>
									<div class="col-md-8">
									<div class="input-icon right">
											<i class="fa"></i>
										<input type="text"  name="httpVersion" placeholder="HTTPS版本" class="form-control form-filter"  >
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">最高版本号<span class="required">*</span></label>
									<div class="col-md-8">
									<div class="input-icon right">
											<i class="fa"></i>
										<input type="text"  name="securePacketVersion" placeholder="最高版本号" class="form-control form-filter"  >
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">管理系统版本号<span class="required">*</span></label>
									<div class="col-md-8">
									<div class="input-icon right">
											<i class="fa"></i>
										<input type="text"  name="remoteProvisioningVersion" placeholder="管理系统版本号" class="form-control form-filter"  >
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">ISD_R_AID<span class="required">*</span></label>
									<div class="col-md-8">
									<div class="input-icon right">
											<i class="fa"></i>
										<input type="text"  name="isdRAid" placeholder="ISD_R_AID" class="form-control form-filter"  >
										<input type="hidden"  name="rId" >
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">POL1_ID<span class="required">*</span></label>
									<div class="col-md-8">
									<div class="input-icon right">
											<i class="fa"></i>
										<!--  <input type="text"  name="pol1Id" placeholder="POL1_ID" class="form-control form-filter"  >-->
										<select  class="form-control" name="pol1Id">
												<option value="00">00</option>
												<option value="01">01</option>
												<option value="02">02</option>
												<option value="03">03</option>
												<option value="04">04</option>
											</select>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">CERT_ECASD_ECKA<span class="required">*</span></label>
									<div class="col-md-8">
									<div class="input-icon right">
											<i class="fa"></i>
										<input type="text"  name="certEcasdEcka" placeholder="CERT_ECASD_ECKA" class="form-control form-filter"  >
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">手机号码<span class="required">*</span></label>
									<div class="col-md-8">
									<div class="input-icon right">
											<i class="fa"></i>
										<input type="text"  name="phoneNo" placeholder="手机号码" class="form-control form-filter"  >
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