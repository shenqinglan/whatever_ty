<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- 新增用户窗口 -->
<div class='container'>
	<div class="modal fade" id="moduleRole" tabindex="-1" role="dialog" data-backdrop="static" data-keyboard="false"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title" id="myModalLabel">角色授权</h4>
				</div>
				<div class="modal-body">
					<div class="row">
	<div class="col-md-12 ">
		<div class="portlet box blue">
			<div class="portlet-title">
				<div class="caption">
					<i class="fa fa-gift"></i><label id="title">标题</label>
					<input type="hidden" id="roleId"/>
				</div>
				<div class="tools">
				</div>
			</div>
			<div class="portlet-body form">
				<form role="form" class="form-horizontal">
					<div class="form-body">
						<div class="form-group">
							<div class="col-md-12">
								<div id="menuTree" class="ztree">
								</div>
								<div id="context-menu">
		                            <ul class="dropdown-menu" role="menu">
		                                <li><a tabindex="-1">Action</a></li>
		                                <li><a tabindex="-1">Another action</a></li>
		                                <li><a tabindex="-1">Something else here</a></li>
		                                <li class="divider"></li>
		                                <li><a tabindex="-1">Separated link</a></li>
		                            </ul>
		                        </div>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" id="addMoudleRole">保存</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
</div>

