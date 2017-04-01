<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class='container'>
	<form role="form" class="portlet-body form" id="searchFormSelectPol2">
		<input type="hidden" name="select_iccid" id="select_iccid" class="form-control form-filter">
	</form>
	<div class="modal fade" id="selectPol2TableModule" tabindex="-1" role="dialog" aria-labelledby="selectPol2TableModalLabel" data-backdrop="static" data-keyboard="false" aria-hidden="true">
	
		<div class="modal-dialog" >
			<div class="modal-content">
				<!-- 对话框标题开始 -->
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true" id="selectPol2Dismissx">×</button>
					<h4 class="modal-title" id="selectPol2TableModalLabel">选择POL2列表</h4>
				</div>
				<!-- 对话框标题结束-->
				<!-- 内容开始 -->
				<table class="table table-striped table-bordered table-hover" id="selectPol2Grid">
					<tbody>
					</tbody>
				</table>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal" id="selectPol2Dismiss">关闭</button>
				</div>
			</div>
		</div>
	</div>
</div>