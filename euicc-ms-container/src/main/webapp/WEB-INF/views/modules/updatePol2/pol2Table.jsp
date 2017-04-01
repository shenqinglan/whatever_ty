<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class='container'>
	<form role="form" class="portlet-body form" id="searchFormProfile">
		<input type="hidden" name="cardEid" id="cardEid" class="form-control form-filter">
		<input type="hidden" name="optType" value="install">
	</form>
	<div class="modal fade" id="updatePol2TableModule" tabindex="-1" role="dialog" aria-labelledby="scp80TableModalLabel" data-backdrop="static" data-keyboard="false" aria-hidden="true">
	
		<div class="modal-dialog" >
			<div class="modal-content">
				<!-- 对话框标题开始 -->
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
					<h4 class="modal-title" id="scp80TableModalLabel">选择profile列表</h4>
				</div>
				<!-- 对话框标题结束-->
				<!-- 内容开始 -->
				<table class="table table-striped table-bordered table-hover" id="updatePol2Grid">
					<tbody>
					</tbody>
				</table>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
</div>