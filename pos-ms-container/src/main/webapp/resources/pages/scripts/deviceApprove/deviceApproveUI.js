// form校验
var FormValidation = function() {
	// validation using icons
	var handleValidation2 = function() {
		// for more info visit the official plugin documentation:
		// http://docs.jquery.com/Plugins/Validation
		var form2 = $('#deviceApproveForm');
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
				approvalAmount : {
					maxlength : 32,
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
var deviceApproveUI = function() {
	var grid = new Datatable();
	var validator = FormValidation.init();
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
					onDataLoad : function(grid) {
						$(".edit")
						.click(
								function() {
									var fieldId = $(this).attr("name");
									$.ajax({
												type : 'POST',
												url : ctx
														+ '/deviceApprove/view',
												data : {
													approvalUserId : fieldId
												},
												dataType : 'json',
												success : function(data) {
													$.each(data,function(name,val) {
														$('#createModule').find("[name='"+ name+ "']").val(val);
													});
													Metronic.handleFixInputPlaceholderForIE();
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
						$(".delete")
						.click(							
								function() {
									if (!confirm('确定驳回此申请？')) {
										return;
									}
									var fieldId = $(this).attr("name");
									$.ajax({
												type : 'POST',
												url : ctx
														+ '/deviceApprove/reject',
												data : {
													approvalUserId : fieldId
												},
												dataType : 'json',
												success : function(data) {
													if (data.success) {
														toastr['success'](data.msg, "系统提示");
														deviceApproveUI.callback();
													} else {
														toastr['error'](data.msg, "系统提示");
													}
												},
												error : function(data) {
													toastr['error']
															(
																	"获取信息失败,请联系管理员！",
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
							url : ctx + '/deviceApprove/find'
						},
						'formSearch' : 'searchForm',
						"columnDefs" : [{'name':'USER_ACCOUNT','orderable' : true,'targets' : [0]}
						,{'name':'TOTAL_APPROVAL_AMOUNT','orderable' : false,'targets' : [1]}
						,{'name':'APPROVAL_AMOUNT','orderable' : false,'targets' : [2]}
						,{'name':'REAL_AMOUNT','orderable' : false,'targets' : [3]}
						,{'name':'APPROVAL_USER_ID','orderable' : false,'targets' : [4]}],
						'columns' : [
								{
									'title' : '申请用户',
									'field' : 'userAccount'
								},
								{
									'title' : '累计申请',
									'field' : 'totalApprovalAmount'
								},
								{
									'title' : '本次申请',
									'field' : 'approvalAmount'
								},
								{
									'title' : '剩余',
									'field' : 'realAmount'
								},
								{
									'title' : '操作',
									'field' : 'approvalUserId',
									'fieldRender' : "getPremission"
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

//提交
$("#submit").click(function() {
	var value = $("#approvalAmount").val();
	if (value == "") {
		toastr['error']("申请次数不能为空！", "系统提示");
		return;
	}
	$.ajax({
		type : 'POST',
		url : ctx + '/deviceApprove/submit',
		data : $('#deviceApproveForm').serialize(),
		dataType : 'json',
		success : function(data) {
			if (data.success) {
				toastr['success'](data.msg, "系统提示");
				$('#createModule').modal('hide');
				deviceApproveUI.callback();
			} else {
				toastr['error'](data.msg, "系统提示");
			}
		},
		error : function(data) {
			toastr['error'](data.msg, "系统提示");
		}
	});
});

function statusRender(data) {
	if (data == 1) {
		return "已解锁";
	} else {
		return "未解锁";
	}
};

function getPremission(data){
	var p = "<shiro:hasPermission name='deviceApprove:approve'><a class='btn default edit btn-xs blue' name='"+data+"'><i class='fa fa-edit'></i>审批</a></shiro:hasPermission>";
	p += "<shiro:hasPermission name='deviceApprove:reject'><a class='btn default delete btn-xs blue' name='"+data+"'><i class='fa fa-edit'></i>驳回</a></shiro:hasPermission>";
	return p;
}