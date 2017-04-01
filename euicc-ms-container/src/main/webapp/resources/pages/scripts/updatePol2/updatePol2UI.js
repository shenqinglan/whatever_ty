/**
 * 
 */
// form校验
var FormValidation = function() {
	// validation using icons
	var handleValidation2 = function() {
		// for more info visit the official plugin documentation:
		// http://docs.jquery.com/Plugins/Validation
		var form2 = $('#installProfileForm');
		var error2 = $('.alert-danger', form2);
		var success2 = $('.alert-success', form2);

		return form2.validate({
			errorElement : 'span', // default input error message
			// container
			errorClass : 'help-block help-block-error', // default input
			// error message
			// class
			focusInvalid : false, // do not focus the last invalid
			// input
			ignore : "", // validate all fields including form hidden
			// input
			rules : {
				fieldName : {
					maxlength : 64,
					required : true
				}
			},

			invalidHandler : function(event, validator) { // display
				// error
				// alert on
				// form
				// submit
				success2.hide();
				error2.show();
				Metronic.scrollTo(error2, -200);
			},

			errorPlacement : function(error, element) { // render error
				// placement for
				// each input
				// type
				var icon = Metronic.handValidStyle(element);
				icon.removeClass('fa-check').addClass("fa-warning");
				icon.attr("data-original-title", error.text()).tooltip({
					'container' : '#installProfileForm'
				});
			},

			highlight : function(element) { // hightlight error inputs
				$(element).closest('.form-group').removeClass("has-success")
						.addClass('has-error'); // set error class to
				// the control group
			},

			unhighlight : function(element) { // revert the change
				// done by hightlight

			},

			success : function(label, element) {
				var icon = Metronic.handValidStyle(element);
				$(element).closest('.form-group').removeClass('has-error')
						.addClass('has-success'); // set success class
				// to the control
				// group
				icon.removeClass("fa-warning").addClass("fa-check");
			},

			submitHandler : function(form) {
				success2.show();
				error2.hide();
				form[0].submit(); // submit the form
			}
		});
	}

	return {
		// main function to initiate the module
		init : function() {
			return handleValidation2();
		}
	};
}();



//Scp80Form
var UpdatePol2FormValidation = function() {
	// validation using icons
	var handleValidation2 = function() {
		// for more info visit the official plugin documentation:
		// http://docs.jquery.com/Plugins/Validation
		var form2 = $('#scp80Form');
		var error2 = $('.alert-danger', form2);
		var success2 = $('.alert-success', form2);

		return form2.validate({
			errorElement : 'span', // default input error message
			// container
			errorClass : 'help-block help-block-error', // default input
			// error message
			// class
			focusInvalid : false, // do not focus the last invalid
			// input
			ignore : "", // validate all fields including form hidden
			// input
			rules : {
				fieldName : {
					maxlength : 64,
					required : true
				}
			},

			invalidHandler : function(event, validator) { // display
				// error
				// alert on
				// form
				// submit
				success2.hide();
				error2.show();
				Metronic.scrollTo(error2, -200);
			},

			errorPlacement : function(error, element) { // render error
				// placement for
				// each input
				// type
				var icon = Metronic.handValidStyle(element);
				icon.removeClass('fa-check').addClass("fa-warning");
				icon.attr("data-original-title", error.text()).tooltip({
					'container' : '#scp80Form'
				});
			},

			highlight : function(element) { // hightlight error inputs
				$(element).closest('.form-group').removeClass("has-success")
						.addClass('has-error'); // set error class to
				// the control group
			},

			unhighlight : function(element) { // revert the change
				// done by hightlight

			},

			success : function(label, element) {
				var icon = Metronic.handValidStyle(element);
				$(element).closest('.form-group').removeClass('has-error')
						.addClass('has-success'); // set success class
				// to the control
				// group
				icon.removeClass("fa-warning").addClass("fa-check");
			},

			submitHandler : function(form) {
				success2.show();
				error2.hide();
				form[0].submit(); // submit the form
			}
		});
	}

	return {
		// main function to initiate the module
		init : function() {
			return handleValidation2();
		}
	};
}();
// 页面信息
var updatePol2UI = function() {
	var eid;
	var grid = new Datatable();
	var validator = FormValidation.init();
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
						$(".details")
						.click(
								function() {
									eid = $(this).attr("name");
									$('#cardEid').val(eid);
									if (isUpdatePol2TableCreated == 1) {
										updatePol2Grid.getDataTable().ajax.reload();
										updatePol2Grid.clearAjaxParams();
									} else {
										createUpdatePol2Table();
									}
									
									Metronic.handleFixInputPlaceholderForIE();
									$('#updatePol2TableModule').modal('show');
								});
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
							url : ctx + '/profileMgr/find'
						},
						'formSearch' : 'searchForm',
						"columnDefs" : [{'name':'EID','orderable' : true,'targets' : [0]}
						,{'name':'PRODUCTION_DATE','orderable' : true,'targets' : [1]}
						,{'name':'PLATFORM_TYPE','orderable' : true,'targets' : [2]}
						,{'name':'PLATFORM_VERSION','orderable' : true,'targets' : [3]}
						,{'name':'REMAINING_MEMORY','orderable' : false,'targets' : [4]}
						,{'name':'AVAILABLEMEMORYFORPROFILES','orderable' : false,'targets' : [5]}
						,{'name':'EID','orderable' : false,'targets' : [6]}],
						'columns' : [
								{
									'title' : '卡ID',
									'field' : 'eid'
								},
								{
									'title' : '生产日期',
									'field' : 'productionDate'
								},
								{
									'title' : '平台类型',
									'field' : 'platformType'
								},
								{
									'title' : '平台版本',
									'field' : 'platformVersion'
								},
								{
									'title' : '可用内存',
									'field' : 'remainingMemory'
								},
								{
									'title' : '属性',
									'field' : 'availablememoryforprofiles'
								},
								{
									'title' : '操作',
									'field' : 'eid',
									'fieldRender' : "getPremission"
								}
								
								],
						'order' : [ [ 0, "asc" ] ] 
					}
				});
	};
	
	var updatePol2Grid = new Datatable();
	var iccid;
	var isUpdatePol2TableCreated = 0;
	//var scp80validator = UpdatePol2FormValidation.init();
	// 创建表格
	var createUpdatePol2Table = function() {
		updatePol2Grid
				.init({
					src : $("#updatePol2Grid"),
					onSuccess : function(updatePol2Grid) {
						// execute some code after table records loaded
					},
					onError : function(updatePol2Grid) {
						// execute some code on network or other general error
					},
					onDataLoad : function(updatePol2Grid) {
						
						$(".edit").click(
								function() {
									iccid = $(this).attr("name");
									$('#select_iccid').val(iccid);
									if (isSelectPol2TableCreated == 1) {
										selectPol2Grid.getDataTable().ajax.reload();
										selectPol2Grid.clearAjaxParams();
									} else {
										createSelectPol2Table();
									}
									$('#updatePol2TableModule').modal('hide');
									Metronic.handleFixInputPlaceholderForIE();
									$('#selectPol2TableModule').modal('show');
								});
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
							url : ctx + '/euiccProfile/findProfiles'
						},
						'formSearch' : 'searchFormProfile',
						"columnDefs" : [{'name':'ICCID','orderable' : true,'targets' : [0]}
						,{'name':'PROFILE_TYPE','orderable' : false,'targets' : [1]}
						,{'name':'STATE','orderable' : false,'targets' : [2]}
						,{'name':'ICCID','orderable' : false,'targets' : [3]}],
						'columns' : [
								{
									'title' : 'ICCID',
									'field' : 'iccid'
								},
								{
									'title' : 'profile类型',
									'field' : 'profileType'
								},
								{
									'title' : '状态',
									'field' : 'state',
									'fieldRender' : "statusRender"
								},
								{
									'title' : '操作',
									'field' : 'iccid',
									'fieldRender' : "getPol2Premission"
								} ],
						'order' : [ [ 0, "asc" ] ] 
					}
					
				});
		isUpdatePol2TableCreated = 1;
	};
	
	var selectPol2Grid = new Datatable();
	var isSelectPol2TableCreated = 0;
	// 创建表格
	var createSelectPol2Table = function() {
		selectPol2Grid
				.init({
					src : $("#selectPol2Grid"),
					onSuccess : function(selectPol2Grid) {
						// execute some code after table records loaded
					},
					onError : function(selectPol2Grid) {
						// execute some code on network or other general error
					},
					onDataLoad : function(selectPol2Grid) {
						
						$(".update").click(
								function() {
									if (!confirm("是否确定选择此POL2并且更新？")) {
										return false;
									}
									var pol2Id = $(this).attr("name");
									$.ajax({
										type : 'POST',
										url : ctx
												+ '/updatePol2/update',
										data : {
											iccid : iccid,
											pol2Id : pol2Id
										},
										dataType : 'json',
										success : function(data) {
											if (data.success) {
												toastr['success'](data.msg, "系统提示");
											} else {
												toastr['error'](data.msg, "系统提示");
											}
										},
										error : function(data) {
											toastr['error']("获取信息失败,请联系管理员！","系统提示");
										}
									});
									$('#selectPol2TableModule').modal('hide');
									
								});
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
							url : ctx + '/pol2/find'
						},
						'formSearch' : 'searchFormSelectPol2',
						"columnDefs" : [{'name':'POL2_ID','orderable' : true,'targets' : [0]}
						,{'name':'SUBJECT','orderable' : false,'targets' : [1]}
						,{'name':'ACTION','orderable' : false,'targets' : [2]}
						,{'name':'QUALIFICATION','orderable' : false,'targets' : [3]}
						,{'name':'POL2_ID','orderable' : false,'targets' : [4]}],
						'columns' : [
								{
									'title' : 'POL2_ID',
									'field' : 'pol2Id'
								},
								{
									'title' : 'SUBJECT',
									'field' : 'subject'
								},
								{
									'title' : 'ACTION',
									'field' : 'action',
								},
								{
									'title' : 'QUALIFICATION',
									'field' : 'qualification',
								},
								{
									'title' : '操作',
									'field' : 'pol2Id',
									'fieldRender' : "getUpdtePol2Premission"
								} ],
						'order' : [ [ 0, "asc" ] ] 
					}
					
				});
		isSelectPol2TableCreated = 1;
	};
	//关闭
	$("#selectPol2Dismiss").click(function(){
		selectPol2TableDismiss();
	});
	$("#selectPol2Dismissx").click(function(){
		selectPol2TableDismiss();
	});
	function selectPol2TableDismiss() {
		if (isUpdatePol2TableCreated == 1) {
			updatePol2Grid.getDataTable().ajax.reload();
			updatePol2Grid.clearAjaxParams();
		} else {
			createUpdatePol2Table();
		}
		Metronic.handleFixInputPlaceholderForIE();
		$('#updatePol2TableModule').modal('show');
	}
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
function setFormStatus(type) {
	// 清除验证css样式
	$('.form-group').removeClass('has-error').removeClass('has-success');
	$('.form-group i').removeClass().addClass('fa');
	if ("edit" == type) {
		$("#tag").val("update");
		$("#profileMgrModalLabel").html("编辑");//请完善编辑内容
	} else if ("add" == type) {
		$("#tag").val("add");
		$("#profileMgrModalLabel").html("新建");//请完善新建内容
	}
};

$("#resetForm").click(function() {
	$('#searchForm')[0].reset();
	installProfileUI.callback();
});

function getPremission(data){
	var p = "<shiro:hasPermission name='profileMgr:details'><a class='btn default details btn-xs blue' name='"+data+"'><i class='fa fa-edit'></i>选择profile </a></shiro:hasPermission>";
	return p;
}

function getPol2Premission(data){
	 var p = "<shiro:hasPermission name='updatePol2:updatePol2'><a class='btn default edit btn-xs blue' name='"+data+"'><i class='fa fa-edit'></i>更新 POL2</a></shiro:hasPermission>";
	return p;
}

function getUpdtePol2Premission(data){
	 var p = "<shiro:hasPermission name='updatePol2:update'><a class='btn default update btn-xs blue' name='"+data+"'><i class='fa fa-edit'></i>更新</a></shiro:hasPermission>";
	return p;
}

function statusRender(data) {
	if (data == '00' || data == '') {
		return "初始";
	} else if(data == '01'){
		return "启用";
	} else if(data == '02'){
		return "禁用";
	} else if(data == '03'){
		return "删除";
	} else if(data == '10'){
		return "安装下载中";
	} else if(data == '11'){
		return "创建isd-p成功";
	} else if(data == '12'){
		return "认证成功";
	} else if(data == '13'){
		return "安装成功";
	} else if(data == '21'){
		return "创建isd-p失败";
	} else if(data == '22'){
		return "认证失败";
	} else if(data == '23'){
		return "安装失败";
	} else{
		return "其它";
	}
}


