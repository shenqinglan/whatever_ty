<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="page-bar">
	<ul class="page-breadcrumb">
		<li><i class="fa fa-home"></i> eUICC管理 <i
			class="fa fa-angle-right"></i></li>
		<li>eUICC卡信息</li>
	</ul>
</div>
<div class="portlet box green-haze">
	<!-- 标题 开始-->
	<div class="portlet-title">
		<div class="caption">
			<i class="fa fa-globe"></i>eUICC卡信息
		</div>
	</div>
	<!-- 标题 结束-->
	<!-- 查询条件开始 -->
	<div class="portlet-body">
		<div class="row">
			<div class="col-md-12">
			<div class="portlet light">
				<form role="form" class="portlet-body form" id="searchForm" enctype="multipart/form-data">
					<div class="form-body">
						<div class="row">
							<div class="col-md-4">
								<div class="form-group">
									<label class="control-label col-md-4">EUICC卡ID</label>
									<div class="col-md-8">
										<input type="text" name="eid" id="eid" placeholder="EUICC卡ID" class="form-control form-filter">
									</div>
								</div>	
							</div>
							<div class="btn-set pull-right" style="margin-top: 10px;">
								<shiro:hasPermission name="euiccCard:create">
									<button class="btn blue" type="button" id="create">新建</button>
								</shiro:hasPermission>
								<shiro:hasPermission name="euiccCard:search">
									<button class="btn green"  type="button"  id="search">查询</button>
								</shiro:hasPermission>
			
							    <a href="${ctxResources}/excel/eUICC_CardInfo.xls" style="float:left;margin:5px 20px" >下载excel模板</a>
							    <input type="file" name="fileUpload" id="fileUpload" style="float:left">
								<shiro:hasPermission name="euiccCard:upload">
									<button class="btn green"  type="button"  id="import">导入excel</button>
								</shiro:hasPermission>
								
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
	<table  id="grid" class="table table-striped table-bordered table-hover">
		<tbody>
		</tbody>
	</table>
	<!-- 表格结束 -->