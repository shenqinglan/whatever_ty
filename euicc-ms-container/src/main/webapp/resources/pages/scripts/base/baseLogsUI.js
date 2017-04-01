/**
 *
 */



 function cutString(val){
 	 var valNewV=val.replaceAll("\u0020","&nbsp;");
	 if(val==null||val.length>=50){
	 	return "<div title="+valNewV+" style='text-decoration:none;cursor:hand;'>"+val.substr(0,50)+"......"+"</div>";
	 }else{
	 	return val;
	 }
}

// 页面信息
var baseLogsUI = function() {
	var grid = new Datatable();
	// 创建表格
	var createTable = function() {
		grid
				.init({
					src : $("#grid"),
					onSuccess : function(grid) {
						// execute some code after table records loaded
					},
					onError : function(grid) {
						// execute some code on network or other general error
					},
					onDataLoad : function(grid) {

					},
					loadingMessage : 'Loading...',
					dataTable : {
						'bStateSave' : true,
						'lengthMenu' : [ [ 10, 20, 30 ], [ 10, 20, 30 ] 																		// here
						],
						'pageLength' : 10,
						'ajax' : {
							url : ctx + '/baseLogs/find'
						},
						'formSearch' : 'searchForm',
						"columnDefs" : [
						 {'name':'USER_ID','orderable' : false,'targets' : [0]}
						,{'name':'OP_TYPE','orderable' : false,'targets' : [1]}
						,{'name':'OP_DATE','orderable' : false,'targets' : [2]}
						,{'name':'REMARK','orderable' : false,'targets' : [3]}],
						'columns' : [
								{
									'title' : '操作人',
									'field' : 'account'
								},
								{
									'title' : '操作',
									'field' : 'opType'
								},
								{
									'title' : '操作时间',
									'field' : 'opDate'
								},
								{
									'title' : '操作描述',
									'field' : 'remark'
								}],
						'order' : [ [ 2, "desc" ] ]
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
    var activeBootstrapControls = function(){

    	// 激活日期控件
        var dayStart = $('#dayStart').datepicker({
        	rtl: Metronic.isRTL(),
        	format: 'yyyy-mm-dd',
        	weekStart: 1,
        	autoclose: true,
        	todayBtn: 'linked',
        	language: 'zh-CN'
        });


        var dayEnd = $('#dayEnd').datepicker({
        	rtl: Metronic.isRTL(),
        	format: 'yyyy-mm-dd',
        	weekStart: 1,
        	autoclose: true,
        	todayBtn: 'linked',
        	language: 'zh-CN'
        });

        dayStart.on('changeDate',function(ev){
        	var startTime = ev.date.valueOf();
        	dayEnd.datepicker("setStartDate",new Date(startTime));

        });
        dayEnd.on('changeDate',function(ev){
        	var endTime = ev.date.valueOf();
        	dayStart.datepicker("setEndDate",new Date(endTime));

        });
    };


	return {
		init : function() {
			// 激活bootstrap控件相关功能
			activeBootstrapControls();
			// 创建表格
			createTable();
			// 查询
			search();
		},
		callback : function() {
			reset();
		}
	};
}();


// 重置form
$('#resetForm').click(function(){
	$('#searchForm')[0].reset();
	$('#dayStart').show();
	$('#dayEnd').show();
	$('#monthStart').hide();
	$('#monthEnd').hide();
	$('#yearStart').hide();
	$('#yearEnd').hide();
	$('#seasonValueStart').hide();
	$('#seasonValueEnd').hide();

	$('#dayStart').datepicker('setEndDate', null);
	$('#dayEnd').datepicker('setStartDate', null);
	$('#monthStart').datepicker('setEndDate', null);
	$('#monthEnd').datepicker('setStartDate', null);
	$('#yearStart').datepicker('setEndDate', null);
	$('#yearEnd').datepicker('setStartDate', null);
	baseLogsUI.callback();
});


$('#export').click(function(){
	if($("#grid_info").text()=="查询无记录"){
		toastr['error']("日志记录为空", "系统提示");
		return;
	}
	var pageLength = $("[name='grid_length']").val();
	var page  = $(".paginate_button.active a").text();
	window.location.href = ctx + '/baseLogs/export?pageLength='+pageLength+"&page="+page+"&"+$('#searchForm').serialize();
});
