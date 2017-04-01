<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="page-bar">
	<ul class="page-breadcrumb">
		<li><i class="fa fa-home"></i> 授权管理<i
			class="fa fa-angle-right"></i></li>
		<li>设备申请</li>
	</ul>
</div>
<div class="portlet box green-haze">
	<!-- 标题 开始-->
	<div class="portlet-title">
		<div class="caption">
			<i class="fa fa-globe"></i>设备申请
		</div>
	</div>
	<!-- 标题 结束-->
	<!-- 查询条件开始 -->
	<div class="portlet-body">
		<div class="row">
			<div id="deviceApplyListModule">

				<form class="form-horizontal" id="deviceApplyListForm" role="form">
					<div class="form-body" style="margin-left: 30px; margin-right: 100px; margin-top: 20px">
						<div class="form-group" >
							<label class="col-md-4 control-label" style="font-size: 15px">累计申请解锁次数</span></label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i class="fa"></i>
									<input type="text" id = "totalApprovalAmount"  name="totalApprovalAmount" class="form-control form-filter"  readonly = "readonly">
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label" style="font-size: 15px">剩余解锁次数</span></label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i class="fa"></i>
									<input type="text"  id="realAmount" name="realAmount" class="form-control form-filter"  readonly = "readonly">
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label" style="font-size: 15px">正在申请解锁次数</span></label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i class="fa"></i>
									<input type="text"  id="approvalAmount" name="approvalAmount" class="form-control form-filter"  readonly = "readonly">
								</div>
							</div>
						</div>
						<!--<div class="form-group">
							<label class="col-md-4 control-label" style="font-size: 15px">申请状态</span></label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i class="fa"></i>
									<input type="text"  id="approvalState" name="approvalState" class="form-control form-filter"  readonly = "readonly">
								</div>
							</div>
						</div> -->
						
						</div>
				</form>
				<div id="div_apply" class="btn-set pull-right" style="margin-top: 10px; margin-right: 100px">
					<button class="btn blue"  type="button"  id="apply">申请</button>
				</div>
			</div>
			</div>
		</div>
	</div>
