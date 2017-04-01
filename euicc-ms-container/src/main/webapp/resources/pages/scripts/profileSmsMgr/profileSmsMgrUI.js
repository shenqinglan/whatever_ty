/**
 * 
 */
// form校验
var FormValidation = function() {
	// validation using icons
	var handleValidation2 = function() {
		// for more info visit the official plugin documentation:
		// http://docs.jquery.com/Plugins/Validation
		var form2 = $('#profileSmsMgrForm');
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
					'container' : '#profileSmsMgrForm'
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
var Scp80FormValidation = function() {
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
var profileSmsMgrUI = function() {
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
									if (isScp80TableCreated == 1) {
										scp80grid.getDataTable().ajax.reload();
										scp80grid.clearAjaxParams();
									} else {
										createScp80Table();
									}
									
									Metronic.handleFixInputPlaceholderForIE();
									$('#createScp80TableModule').modal('show');
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
							url : ctx + '/profileSmsMgr/find'
						},
						'formSearch' : 'searchForm',
						"columnDefs" : [{'name':'EID','orderable' : true,'targets' : [0]}
						,{'name':'PRODUCTION_DATE','orderable' : true,'targets' : [1]}
						,{'name':'PLATFORM_TYPE','orderable' : true,'targets' : [2]}
						,{'name':'PLATFORM_VERSION','orderable' : true,'targets' : [3]}
						,{'name':'REMAINING_MEMORY','orderable' : false,'targets' : [4]}
						,{'name':'AVAILABLEMEMORYFORPROFILES','orderable' : false,'targets' : [5]}
						,{'name':'PHONE_NO','orderable' : false,'targets' : [6]}
						,{'name':'EID','orderable' : false,'targets' : [7]}],
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
									'title' : '手机号码',
									'field' : 'phoneNo'
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
	
	var scp80grid = new Datatable();
	var isScp80TableCreated = 0;
	var scp80validator = Scp80FormValidation.init();
	// 创建表格
	var createScp80Table = function() {
		scp80grid
				.init({
					src : $("#scp80grid"),
					onSuccess : function(scp80grid) {
						// execute some code after table records loaded
					},
					onError : function(scp80grid) {
						// execute some code on network or other general error
					},
					onDataLoad : function(scp80grid) {
						$("#scp80grid tbody tr").each(function() {
							var status = $(this).find("td").eq(2).text();
							if ("安装成功" == status) {
								$(this).find(".stop").attr("disabled",true);
								$(this).find(".delete").attr("disabled",true);
							} 
							
							if ("启用" == status) {
								$(this).find(".start").attr("disabled",true);
								$(this).find(".delete").attr("disabled",true);
							} 

							if ("禁用" == status) {
								//$(this).find(".start").attr("disabled",true);
								$(this).find(".stop").attr("disabled",true);
							} 
							
							if ("删除" == status) {
								$(this).find(".fallBackAttr").attr("disabled",true);
							}
							
							if ("初始" == status || "删除" == status || "安装下载中" == status || "创建isd-p成功" == status 
									|| "认证成功" == status  || "创建isd-p失败" == status 
									|| "认证失败" == status || "安装失败" == status || "其它" == status) {
								$(this).find(".start").attr("disabled",true);
								$(this).find(".stop").attr("disabled",true);
								$(this).find(".delete").attr("disabled",true);
								$(this).find(".masterDelete").attr("disabled",true);
							}
						});
						
						$(".start").click(
								function() {
									$('#createScp80TableModule').modal('hide');
									var fieldId = $(this).attr("name");
									if (!confirm("是否确定启用该profile？")) {
										return false;
									}
									$.ajax({
										type : 'POST',
										url : ctx + '/euiccProfile/enableProfileBySms',
										timeout : 120000,
										data : {
											id : fieldId
										},
										dataType : 'json',
										success : function(data) {
											if(data.success){
												toastr['success'](data.msg,
													"系统提示");
												$('#createScp80TableModule').modal('show');
												scp80grid.getDataTable().ajax.reload();
												scp80grid.clearAjaxParams();
											}else{
												toastr['error'](data.msg,
												"系统提示");
											}
										},
										error : function(data) {
											toastr['error'](data.msg,
													"系统提示");
										},
										complete : function(XMLHttpRequest,status){
											if(status == 'timeout'){
												toastr['error']("系统调用超时，请重新再试",
												"系统提示");
											}
										}
									});
								});
						
						$(".stop").click(
								function() {
									$('#createScp80TableModule').modal('hide');
									var fieldId = $(this).attr("name");
									if (!confirm("是否确定禁用该profile？")) {
										return false;
									}
									$.ajax({
										type : 'POST',
										url : ctx + '/euiccProfile/disableProfileBySms',
										timeout : 120000,
										data : {
											id : fieldId
										},
										dataType : 'json',
										success : function(data) {
											if(data.success){
												toastr['success'](data.msg,
													"系统提示");
												$('#createScp80TableModule').modal('show');
												scp80grid.getDataTable().ajax.reload();
												scp80grid.clearAjaxParams();
											}else{
												toastr['error'](data.msg,
												"系统提示");
											}
										},
										error : function(data) {
											toastr['error'](data.msg,
													"系统提示");
										},
										complete : function(XMLHttpRequest,status){
											if(status == 'timeout'){
												toastr['error']("系统调用超时，请重新再试",
												"系统提示");
											}
										}
									});
								});
						
						$(".delete").click(
								function() {
									$('#createScp80TableModule').modal('hide');
									var fieldId = $(this).attr("name");
									if (!confirm("是否确定删除该数据信息？")) {
										return false;
									}
									$.ajax({
										type : 'POST',
										url : ctx + '/euiccProfile/deleteProfileBySms',
										timeout : 120000,
										data : {
											id : fieldId
										},
										dataType : 'json',
										success : function(data) {
											if(data.success){
												toastr['success'](data.msg,
													"系统提示");
												$('#createScp80TableModule').modal('show');
												scp80grid.getDataTable().ajax.reload();
												scp80grid.clearAjaxParams();
											}else{
												toastr['error'](data.msg,
												"系统提示");
											}
										},
										error : function(data) {
											toastr['error'](data.msg,
													"系统提示");
										},
										complete : function(XMLHttpRequest,status){
											if(status == 'timeout'){
												toastr['error']("系统调用超时，请重新再试",
												"系统提示");
											}
										}
									});
								});
						$(".masterDelete").click(
								function() {
									$('#createScp80TableModule').modal('hide');
									var fieldId = $(this).attr("name");
									if (!confirm("是否确定主删除该数据信息？")) {
										return false;
									}
									$.ajax({
										type : 'POST',
										url : ctx + '/euiccProfile/masterDeleteProfileBySms',
										timeout : 120000,
										data : {
											id : fieldId
										},
										dataType : 'json',
										success : function(data) {
											if(data.success){
												toastr['success'](data.msg,
													"系统提示");
												$('#createScp80TableModule').modal('show');
												scp80grid.getDataTable().ajax.reload();
												scp80grid.clearAjaxParams();
											}else{
												toastr['error'](data.msg,
												"系统提示");
											}
										},
										error : function(data) {
											toastr['error'](data.msg,
													"系统提示");
										},
										complete : function(XMLHttpRequest,status){
											if(status == 'timeout'){
												toastr['error']("系统调用超时，请重新再试",
												"系统提示");
											}
										}
									});
								});
						$(".fallBackAttr").click(
								function() {
									$('#createScp80TableModule').modal('hide');
									var fieldId = $(this).attr("name");
									$.ajax({
										type : 'POST',
										url : ctx + '/euiccProfile/setFallBackAttrBySms',
										timeout : 120000,
										data : {
											id : fieldId
										},
										dataType : 'json',
										success : function(data) {
											if(data.success){
												toastr['success'](data.msg,
													"系统提示");
												$('#createScp80TableModule').modal('show');
												scp80grid.getDataTable().ajax.reload();
												scp80grid.clearAjaxParams();
											}else{
												toastr['error'](data.msg,
												"系统提示");
											}
										},
										error : function(data) {
											toastr['error'](data.msg,
													"系统提示");
										},
										complete : function(XMLHttpRequest,status){
											if(status == 'timeout'){
												toastr['error']("系统调用超时，请重新再试",
												"系统提示");
											}
										}
									});
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
						,{'name':'ISD-P-AID','orderable' : false,'targets' : [1]}
						,{'name':'STATE','orderable' : false,'targets' : [2]}
						,{'name':'ICCID','orderable' : false,'targets' : [3]}],
						'columns' : [
								{
									'title' : 'ICCID',
									'field' : 'iccid'
								},
								{
									'title' : 'ISD-P-AID',
									'field' : 'isdPAid'
								},
								{
									'title' : '状态',
									'field' : 'state',
									'fieldRender' : "statusRender"
								},
								{
									'title' : '操作',
									'field' : 'iccid',
									'fieldRender' : "getScpPremission"
								} ],
						'order' : [ [ 0, "asc" ] ] 
					}
					
				});
		isScp80TableCreated = 1;
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
	
		// 打开新增窗口
		$('#create').click(function() {
			setFormStatus("add");
			Metronic.handleFixInputPlaceholderForIE();
			$('#profileMgrForm')[0].reset();
			$('#createModule').modal('show');
		});
		
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
	profileMgrUI.callback();
});

function getPremission(data){
	var p = "<shiro:hasPermission name='profileSmsMgr:details'><a class='btn default details btn-xs blue' name='"+data+"'><i class='fa fa-edit'></i>查看profile </a></shiro:hasPermission>";
	return p;
}

function getScpPremission(data){
	var p = "<shiro:hasPermission name='euiccProfile:enable'><a class='btn default start btn-xs blue' name='"+data+"'><i class='fa fa-save'></i>启用 </a></shiro:hasPermission>";
	p += "<shiro:hasPermission name='euiccProfile:disable'><a class='btn default stop btn-xs blue' name='"+data+"'><i class='fa fa-save'></i>禁用 </a></shiro:hasPermission>";
	p += "<shiro:hasPermission name='euiccProfile:delete'><a class='btn default delete btn-xs blue' name='"+data+"'><i class='fa fa-trash-o'></i>删除 </a></shiro:hasPermission>";
	p += "<shiro:hasPermission name='euiccProfile:masterDelete'><a class='btn default masterDelete btn-xs blue' name='"+data+"'><i class='fa fa-trash-o'></i>主删除 </a></shiro:hasPermission>";
	p += "<shiro:hasPermission name='euiccProfile:fallBackAttr'><a class='btn default fallBackAttr btn-xs blue' name='"+data+"'><i class='fa fa-save'></i>回滚设置 </a></shiro:hasPermission>";
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


