// 页面信息
var deviceDisplayUI = function() {
	var grid = new Datatable();
	// 创建表格
	var createTable = function() {
		grid.init({
					src : $("#grid"),
					onSuccess : function(grid) {
						// execute some code after table records loaded
					},
					onError : function(grid) {
						// execute some code on network or other general error
					},

					loadingMessage : 'Loading...',
					dataTable : {
						'bStateSave' : true,
						'lengthMenu' : [ [ 10, 20, 30 ], [ 10, 20, 30 ] // change
																		// per
																		// page
																		// values
																		// here
						],
						'pageLength' : 10,
						'ajax' : {
							url : ctx + '/deviceDisplay/find'
						},
						'formSearch' : 'searchForm',
						"columnDefs" : [{'name':'SN','orderable' : true,'targets' : [0]}
						,{'name':'HARDWARE_ID','orderable' : false,'targets' : [1]}
						,{'name':'DEVICE_TYPE','orderable' : false,'targets' : [2]}
						,{'name':'CREATE_DATE','orderable' : false,'targets' : [3]}
						,{'name':'UPDATE_DATE','orderable' : false,'targets' : [4]}
						,{'name':'LOCK_STATE','orderable' : false,'targets' : [5]}],
						'columns' : [
								{
									'title' : 'SN号',
									'field' : 'sn'
								},
								{
									'title' : '硬件ID',
									'field' : 'hardwareId'
								},
								{
									'title' : '设备类型',
									'field' : 'deviceType'
								},
								{
									'title' : '录入时间',
									'field' : 'createDate'
								},
								{
									'title' : '操作时间',
									'field' : 'updateDate'
								},
								{
									'title' : '状态',
									'field' : 'lockState',
									'fieldRender' : "statusRender"
								}],
						'order' : [ [ 0, "asc" ] ] 
					}
				});
	};
	// 查询
	var search = function() {
		$("#search").click(function(e) {
			e.preventDefault();
			grid.getDataTable().ajax.reload();
			grid.clearAjaxParams();
		});
	};
	// 重置
	var reset = function() {
		grid.getDataTable().ajax.reload();
		grid.clearAjaxParams();
	};
	// 激活bootstrap控件相关功能
	var activeBootstrapControls = function() {
		// 提示控件配置
		var options = {
			"closeButton" : true,
			"debug" : false,
			"positionClass" : "toast-top-center",
			"onclick" : null,
			"showDuration" : "1000",
			"hideDuration" : "1000",
			"timeOut" : "3000",
			"extendedTimeOut" : "1000",
			"showEasing" : "swing",
			"hideEasing" : "linear",
			"showMethod" : "fadeIn",
			"hideMethod" : "fadeOut"
		}
		toastr.options = options;
	};
	return {
		init : function() {
			// 激活bootstrap控件相关功能
			activeBootstrapControls();
			// 创建表格
			createTable();
			//
			search();
		},
		callback : function() {
			reset();
		}
	};
}();

function statusRender(data) {
	if (data == 1) {
		return "已解锁";
	} else {
		return "未解锁";
	}
};