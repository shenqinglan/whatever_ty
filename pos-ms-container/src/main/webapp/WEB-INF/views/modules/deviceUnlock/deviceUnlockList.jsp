<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="page-bar">
	<ul class="page-breadcrumb">
		<li><i class="fa fa-home"></i> 授权管理<i
			class="fa fa-angle-right"></i></li>
		<li>设备解锁</li>
	</ul>
</div>
<div class="portlet box green-haze">
	<!-- 标题 开始-->
	<div class="portlet-title">
		<div class="caption">
			<i class="fa fa-globe"></i>设备解锁
		</div>
	</div>
	<!-- 标题 结束-->
	<!-- 查询条件开始 -->
	<div class="portlet-body">
		<div class="row">
			<div >
				<form role="form" class="portlet-body form" id="searchForm">
					<div class="form-body">
						<div class="row">
							<input type="hidden" id="rootKey" name="rootKey">
							<div class="col-md-4">
								<div class="form-group" >
									<label id="msg" class="control-label col-md-4" style="display: none; color: red; font: 300; margin-left: 500px;">设备未连接！</label>
								</div>	
							</div>
							<div id="div_reset" class="btn-set pull-right" style="margin-top: 10px;display: none">
								<button class="btn blue"  type="button"  id="reset">重新连接</button>
							</div>
						</div>
					</div>
				</form>

				<form class="form-horizontal" id="deviceUnlockForm" role="form">
					<div class="form-body" style="margin-left: 30px; margin-right: 100px">
						<div class="form-group" >
							<label class="col-md-4 control-label" style="font-size: 15px">SN</span></label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i class="fa"></i>
									<input type="text" id = "sn"  name="sn" class="form-control form-filter"  readonly = "readonly">
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label" style="font-size: 15px">硬件ID</span></label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i class="fa"></i>
									<input type="text"  id="hardwareId" name="hardwareId" class="form-control form-filter"  readonly = "readonly">
								</div>
							</div>
						</div>
						
						</div>
				</form>
				<div id="div_unlock" class="btn-set pull-right" style="margin-top: 10px; margin-right: 100px; display: none">
					<button class="btn blue"  type="button"  id="unlock">解锁</button>
				</div>
			</div>
			</div>
		</div>
	</div>
