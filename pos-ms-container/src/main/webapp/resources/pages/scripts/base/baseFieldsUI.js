/**
 * 数据字典
 */
// form校验
var FormValidation = function() {
	// validation using icons
	var handleValidation2 = function() {
		// for more info visit the official plugin documentation:
		// http://docs.jquery.com/Plugins/Validation
		var form2 = $('#baseFieldsForm');
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
				},
				field : {
					maxlength : 32,
					required : true
				},
				valueField : {
					maxlength : 64,
					required : true
				},
				displayField : {
					maxlength : 64,
					required : true
				},
				sort : {
					required : true,
					maxlength : 2
				},
				enabled : {
					maxlength : 2,
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
					'container' : '#baseFieldsForm'
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
var baseFieldsUI = function() {
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
																+ '/baseFields/view',
														data : {
															fieldId : fieldId
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
																						'#baseFieldsForm')
																						.find(
																								"[name='"
																										+ name
																										+ "']")
																						.val(
																								val);
																			});
															Metronic
																	.handleFixInputPlaceholderForIE();
															$('#viewBaseFields')
																	.modal(
																			'show');
														},
														error : function(data) {
															toastr['error']
																	(
																			"获取用户信息失败,请联系管理员！",
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
										url : ctx + '/baseFields/delete',
										data : {
											fieldId : fieldId
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
											toastr['error']("获取用户信息失败,请联系管理员！",
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
							url : ctx + '/baseFields/find'
						},
						'formSearch' : 'searchForm',
						"columnDefs" : [{'name':'FIELD_NAME','orderable' : true,'targets' : [0]}
						,{'name':'FIELD','orderable' : true,'targets' : [1]}
						,{'name':'VALUE_FIELD','orderable' : true,'targets' : [2]}
						,{'name':'DISPLAY_FIELD','orderable' : true,'targets' : [3]}
						,{'orderable' : false,'targets' : [4]}
						,{'orderable' : false,'targets' : [5]}
						,{'orderable' : false,'targets' : [6]}],
						'columns' : [
								{
									'title' : '字段名称',
									'field' : 'fieldName'
								},
								{
									'title' : '字段',
									'field' : 'field'
								},
								{
									'title' : '字段值',
									'field' : 'valueField'
								},
								{
									'title' : '字段显示值',
									'field' : 'displayField'
								},
								{
									'title' : '是否启用',
									'field' : 'enabled',
									'fieldRender' : 'enabledStatusRender'
								},
								{
									'title' : '显示顺序',
									'field' : 'sort'
								},
								{
									'title' : '操作',
									'field' : 'fieldId',
									'fieldRender':"getPremission"
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
			$('#baseFieldsForm')[0].reset();
			Metronic.handleFixInputPlaceholderForIE();
			$('#viewBaseFields').modal('show');
		});
		// 保存
		$('#saveBaseFields').click(function() {
			if (!validator.form())
				return false;
			$.ajax({
				type : 'POST',
				url : ctx + '/baseFields/save',
				data : $('#baseFieldsForm').serialize(),
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						toastr['success'](data.msg, "系统提示");
						$('#viewBaseFields').modal('hide');
						baseFieldsUI.callback();
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
		$("#enName").attr("readOnly",true);
		$("#baseFieldsModalLabel").html("编辑数据字典");
	} else if ("add" == type) {
		$("#tag").val("add");
		$("#enName").attr("readOnly",false);
		$("#baseFieldsModalLabel").html("新建数据字典");
	}
};

$("#reset").click(function() {
	$("#fieldNameSearch").val("");
	baseFieldsUI.callback();
});

function enabledStatusRender(data) {
	if (data == 0) {
		return "停用";
	} else {
		return "启用";
	}
};