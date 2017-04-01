<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class='container'>
	<div class="modal fade" id="createScp80TableModule" tabindex="-1" role="dialog" aria-labelledby="scp80TableModalLabel" data-backdrop="static" data-keyboard="false" aria-hidden="true">
		<div class="modal-dialog" >
			<div class="modal-content">
				<!-- 对话框标题开始 -->
				<div class="modal-header">
					<input type="hidden" name="scp80TableEid" id="scp80TableEid">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
					<h4 class="modal-title" id="scp80TableModalLabel">SCP80密钥信息</h4>
				</div>
				<!-- 对话框标题结束-->
				<!-- 内容开始 -->
				<table class="table table-striped table-bordered table-hover" id="scp80grid">
					<colgroup>
						<col width="10%">
						<col width="10%">
						<col width="10%">
						<col width="10%">
						<col width="10%">
						<col width="10%">
					</colgroup>
					<tbody>
					</tbody>
				</table>
				<div class="modal-footer">
				    <button type="button" class="btn btn-primary" id="createScp80">新建</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
</div>