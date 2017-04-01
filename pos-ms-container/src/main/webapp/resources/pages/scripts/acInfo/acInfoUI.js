/**
 * 
 */
// form校验
var FormValidation = function() {
	// validation using icons
	var handleValidation2 = function() {
		// for more info visit the official plugin documentation:
		// http://docs.jquery.com/Plugins/Validation
		var form2 = $('#acInfoForm');
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
				hash : {
					maxlength : 64,
					required : true
				},
				apdu : {
					maxlength : 32,
					required : true
				},
				nfc : {
					maxlength : 64,
					required : true
				},
				acAid : {
					maxlength : 64,
					required : true
				},
				acIndex : {
					required : true,
					maxlength : 16
				},
				appAid : {
					maxlength : 16,
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
					'container' : '#acInfoForm'
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
var acInfoUI = function() {
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
						$(".edit")
								.click(
										function() {
											setFormStatus("edit");
											var fieldId = $(this).attr("name");
											$
													.ajax({
														type : 'POST',
														url : ctx
																+ '/acInfo/view',
														data : {
															id : fieldId
														},
														dataType : 'json',
														success : function(data) {
															$
																	.each(
																			data,
																			function(
																					name,
																					val) {
																				$(
																						'#createModule')
																						.find(
																								"[name='"
																										+ name
																										+ "']")
																						.val(
																								val);
																			});
															Metronic
																	.handleFixInputPlaceholderForIE();
															$('#createModule')
																	.modal(
																			'show');
														},
														error : function(data) {
															toastr['error']
																	(
																			"获取信息失败,请联系管理员！",
																			"系统提示");
														}
													});
										});
						$(".delete").click(
								function() {
									var fieldId = $(this).attr("name");
									if (!confirm("是否确定删除该数据信息？")) {
										return false;
									}
									$.ajax({
										type : 'POST',
										url : ctx + '/acInfo/delete',
										data : {
											id : fieldId
										},
										dataType : 'json',
										success : function(data) {
											if(data.success){
												toastr['success']("删除成功",
													"系统提示");
												baseFieldsUI.callback();
											}
										},
										error : function(data) {
											toastr['error']("获取信息失败,请联系管理员！",
													"系统提示");
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
							url : ctx + '/acInfo/find'
						},
						'formSearch' : 'searchForm',
						"columnDefs" : [{'name':'HASH','orderable' : true,'targets' : [0]}
						,{'name':'APDU','orderable' : true,'targets' : [1]}
						,{'name':'NFC','orderable' : true,'targets' : [2]}
						,{'name':'AC_AID','orderable' : true,'targets' : [3]}
						,{'name':'STATUS','orderable' : false,'targets' : [4]}
						,{'name':'AC_INDEX','orderable' : false,'targets' : [5]}
						,{'name':'APP_AID','orderable' : false,'targets' : [6]}
						,{'name':'ID','orderable' : false,'targets' : [7]}],
						'columns' : [
								{
									'title' : 'hash',
									'field' : 'hash'
								},
								{
									'title' : 'apdu',
									'field' : 'apdu'
								},
								{
									'title' : 'nfc',
									'field' : 'nfc'
								},
								{
									'title' : 'acaid',
									'field' : 'acAid'
								},
								{
									'title' : 'status',
									'field' : 'status',
									'fieldRender' : "statusRender"
								},
								{
									'title' : 'acindex',
									'field' : 'acIndex'
								},
								{
									'title' : 'appaid',
									'field' : 'appAid'
								},
								{
									'title' : '操作',
									'field' : 'id',
									'fieldRender' : "getPremission"
								} ],
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
		// 新增
		$('#create').click(function() {
			setFormStatus("add");
			Metronic.handleFixInputPlaceholderForIE();
			$('#acInfoForm')[0].reset();
			$('#createModule').modal('show');
		});
		// 保存
		$('#addModule').click(function() {
			if (!validator.form())
				return false;
			$.ajax({
				type : 'POST',
				url : ctx + '/acInfo/save',
				data : $('#acInfoForm').serialize(),
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						toastr['success'](data.msg, "系统提示");
						$('#createModule').modal('hide');
						acInfoUI.callback();
					} else {
						toastr['error'](data.msg, "系统提示");
					}
				},
				error : function(data) {
					toastr['error'](data.msg, "系统提示");
				}
			});
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
		$("#acInfoModalLabel").html("编辑");//请完善编辑内容
	} else if ("add" == type) {
		$("#tag").val("add");
		$("#acInfoModalLabel").html("新建");//请完善新建内容
	}
};

$("#resetForm").click(function() {
	$('#searchForm')[0].reset();
	acInfoUI.callback();
});

function statusRender(data) {
	if (data == 1) {
		return "停用";
	} else {
		return "启用";
	}
};

function getPremission(data){
	var p = "<shiro:hasPermission name='acInfo:edit'><a class='btn default edit btn-xs blue' name='"+data+"'><i class='fa fa-edit'></i>编辑 </a></shiro:hasPermission>";
	p += "<shiro:hasPermission name='acInfo:delete'><a class='btn default delete btn-xs red' name='"+data+"'><i class='fa fa-trash-o'></i>删除</a></shiro:hasPermission>";
	return p;
}

