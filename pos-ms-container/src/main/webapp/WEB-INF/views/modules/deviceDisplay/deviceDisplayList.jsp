<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="page-bar">
	<ul class="page-breadcrumb">
		<li><i class="fa fa-home"></i> 授权管理 <i
			class="fa fa-angle-right"></i></li>
		<li>设备信息</li>
	</ul>
</div>
<div class="portlet box green-haze">
	<!-- 标题 开始-->
	<div class="portlet-title">
		<div class="caption">
			<i class="fa fa-globe"></i>设备信息
		</div>
	</div>
	<!-- 标题 结束-->
	<!-- 查询条件开始 -->
	<div class="portlet-body">
		<div class="row">
			<div class="col-md-12">
			<div class="portlet light">
				<form role="form" class="portlet-body form" id="searchForm">
					<div class="form-body">
						<div class="row">
							<div class="col-md-4">
								<div class="form-group">
									<label class="control-label col-md-4">SN号</label>
									<div class="col-md-8">
										<input type="text" name="sn" id="sn" placeholder="SN号" class="form-control form-filter">
									</div>
								</div>	
							</div>
							<div class="btn-set pull-right" style="margin-top: 10px;">
								<button class="btn green"  type="button"  id="search">查询</button>
							</div>
						</div>
					</div>
				</form>
			</div>
			</div>
		</div>
	</div>
	</div>
	<!-- 查询条件结束 -->
	<!-- 表格开始 -->
	<table class="table table-striped table-bordered table-hover" id="grid">
		<colgroup>

		</colgroup>
		<tbody>
		</tbody>
	</table>
	<!-- 表格结束 -->
