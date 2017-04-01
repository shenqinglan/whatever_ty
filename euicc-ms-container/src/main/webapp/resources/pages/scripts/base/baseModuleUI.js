/**
 * @auther dengzm
 * @since 2015-4-23 菜单管理
 */
// jquey validate验证
var FormValidation = function() {
	// validation using icons
	var handleValidation2 = function() {
		// for more info visit the official plugin documentation:
		// http://docs.jquery.com/Plugins/Validation
		var form2 = $('#moduleForm');
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
						moduleId : {
							maxlength : 8,
							number:true,
							required : true
						},
						moduleName : {
							maxlength : 32,
							required : true
						},
						moduleUrl : {
							maxlength : 64,
							required : true
						},
						parentId : {
							required : true,
							number:true,
							maxlength : 8
						},
						displayIndex : {
							maxlength : 2,
							number : true,
							required : true
						},
						leafType : {
							required : true
						},
						iconCss : {
							maxlength : 64
						},
						information : {
							maxlength : 64
						}
					},
					messages:{
						moduleId:{required:"请输入菜单编码",maxlength:$.validator.format('菜单编码不能超过{0}个字符')}
						,moduleName:{required:"请输入菜单名称",maxlength:$.validator.format('菜单名称不能超过{0}个字符')}
						,moduleUrl:{required:"请输入菜单URL",maxlength:$.validator.format('菜单URL不能超过{0}个字符')}
						,parentId:{required:"请输入父级菜单",maxlength:$.validator.format('父级菜单不能超过{0}个字符')}
						,displayIndex:{required:"请输入显示顺序",maxlength:$.validator.format('显示顺序不能超过{0}个字符'),number:"只能填写数字"}
						,leafType:{required:"请输入节点类型"}
						,iconCss:{maxlength:$.validator.format('CSS样式不能超过{0}个字符')}
						,information:{maxlength:$.validator.format('菜单说明不能超过{0}个字符')}
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
						icon.attr("data-original-title", error.text()).tooltip(
								{
									'container' : '#moduleForm'
								});
					},

					highlight : function(element) { // hightlight error inputs
						$(element).closest('.form-group')
								.removeClass("has-success")
								.addClass('has-error'); // set error class to
						// the control group
					},

					unhighlight : function(element) { // revert the change
						// done by hightlight

					},

					success : function(label, element) {
						var icon = Metronic.handValidStyle(element);
						$(element).closest('.form-group')
								.removeClass('has-error')
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
// 
var baseModuleUI = function() {
	var grid = new Datatable();
	var validator = FormValidation.init();
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
				// execute some code on ajax data load
				$(".delete").click(function() {

					if (!confirm("是否确定删除该菜单信息？")) {
						return false;
					}
					var moduleId = $(this).attr("name");
					$.ajax({
								type : "POST",
								url : ctx + "/baseModules/delete",
								data : {
									moduleId : moduleId
								},
								dataType : "json",
								success : function(data) {
									if (data.success){
										toastr['success']("删除成功", "系统提示");
										baseModuleUI.callback();
									}else{
										toastr['error'](data.msg, "系统提示");
									}
								}
							});
				});

				$(".update").click(function() {
					clearAddTable("update");
					var moduleIds = $(this).attr("name");
					$.ajax({
								type : "POST",
								url : ctx + "/baseModules/view",
								data : {
									moduleId : moduleIds
								},
								dataType : "json",
								success : function(data) {
									changeLeafTypeByValue(data.leafType);
									$.each(data, function(name, val) {
												$('#moduleForm').find("[name='"
														+ name + "']").val(val);
											});
									Metronic.handleFixInputPlaceholderForIE();
									$('#createModule').modal('show');
								}
							});
				});
			},
			loadingMessage : 'Loading...',
			dataTable : {

				"bStateSave" : true, // save datatable state(pagination,
				// sort, etc) in cookie.

				"lengthMenu" : [[10, 20, 30], [10, 20, 30] // change per page
				// values here
				],
				"pageLength" : 10, // default record count per page
				"ajax" : {
					"url" : ctx + "/baseModules/find" // ajax source
				},
				"formSearch" : "modulesSearchForm",
				"columnDefs" : [{'name':'MODULE_ID','orderable' : true,'targets' : [0]}
								,{'name':'MODULE_NAME','orderable' : true,'targets' : [1]}
								,{'name':'MODULE_URL','orderable' : true,'targets' : [2]}
								,{'name':'PARENT_ID','orderable' : true,'targets' : [3]}
								,{'orderable' : false,'targets' : [4]}
								,{'orderable' : false,'targets' : [5]}
								,{'orderable' : false,'targets' : [6]}
								,{'orderable' : false,'targets' : [7]}],
				"columns" : [{
							"title" : "菜单编码",
							"field" : "moduleId"
						},{
							"title" : "菜单名称",
							"field" : "moduleName"
						}, {
							"title" : "菜单URL",
							"field" : "moduleUrl"
						}, {
							"title" : "父级菜单",
							"field" : "parentId"
						}, {
							"title" : "节点类型",
							"field" : "leafType",
							"fieldRender" : "getLeafType"
						}, {
							"title" : "展示顺序",
							"field" : "displayIndex"
						}, {
							"title" : "css样式",
							"field" : "iconCss"
						}, {
							"title" : "操作",
							"field" : "moduleId",
							'fieldRender' : "getPremission"
							// "wrapper" : "<button class='btn blue'
						// onclick='getModulesInfoById($field$)'
						// onclick='getUserInfoById($field$)'
						// >编辑</button><button class='btn red'
						// onclick='deleteUser($field$)' >删除</button>"
					}],
				"order" : [[1, "asc"]]
				// set first column as a default sort by asc
			}
		});
	};
	var search = function() {
		$("#search").click(function(e) {
					e.preventDefault();
					grid.getDataTable().ajax.reload();
					grid.clearAjaxParams();
				});
	}

	var reload = function() {
		grid.getDataTable().ajax.reload();
		grid.clearAjaxParams();
	}
	// 激活bootstrap控件相关功能
	var activeBootstrapControls = function() {
		// 激活日期控件
		$('.date-picker').datepicker({
					rtl : Metronic.isRTL(),
					autoclose : true
				});
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
					clearAddTable("add");
					Metronic.handleFixInputPlaceholderForIE();
					$('#createModule').modal('show');
				});
		// 菜单保存
		$('#addModule').click(function() {
					if (!validator.form())
						return false;
					$.ajax({
								type : "POST",
								url : ctx + "/baseModules/save",
								data :$('#moduleForm').serialize(),
								dataType : "json",
								success : function(data) {
									if (data.success){
										$('#createModule').modal('hide');
										toastr['success'](data.msg, "系统提示");
										baseModuleUI.callback();
									} else{
										toastr['error'](data.msg, "系统提示");
									}
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
			reload();
		}
	};
}();

//重置
$("#reset").click(function() {
	$("#modulesSearchForm").find("input[type='text']").each(function(){
		$(this).val("");
	});
	baseModuleUI.callback();
});

// 系统字段
function getLeafType(data) {
	if ("0" == data) {
		return "主菜单";
	} else if ("1" == data) {
		return "页面";
	} else if ("2" == data){
		return "动作";		
	}else {
		return "主菜单";
	}
}
function getEXStatus(data) {
	if ("0" == data) {
		return "收缩";
	} else if ("1" == data) {
		return "展开";
	} else {
		return "";
	}
}

function getIsDisplay(data) {
	if ("0" == data) {
		return "否";
	} else if ("1" == data) {
		return "是";
	} else {
		return "";
	}
}

function clearAddTable(type) {
	// 清除验证css样式
	$('.form-group').removeClass('has-error').removeClass('has-success');
	$('.form-group i').removeClass().addClass('fa');
	$("#moduleForm")[0].reset();
	if ("update" == type) {
		$("#myModalLabel").html("编辑菜单");
		$("#moduleIdEdit").attr("readOnly",true);
		$("#tag").val("update");
	}
	if ("add" == type) {
		$("#myModalLabel").html("新建菜单");
		$("#moduleIdEdit").attr("readOnly",false);
		$("#tag").val("add");
		// 初始化
		changeLeafTypeByValue('0');
	}
}
function changeLeafType(ele){
	changeLeafTypeByValue($(ele).val());
}
function changeLeafTypeByValue(data){
	if (data == '0'){
		// 主菜单
		$("#moduleForm_parentId").val('0');
		$("#moduleForm_parentId").attr('readonly',true);
		// 
		$('#parIdDiv').removeClass('has-error').removeClass('has-success');
		$('#parIdDiv i').removeClass().addClass('fa');
		
		$('#urlDiv').removeClass('has-error').removeClass('has-success');
		$('#urlDiv i').removeClass().addClass('fa');
		$('#moduleForm_moduleUrl').rules('remove');
		$('#moduleForm_moduleUrl_span').removeClass('required');
		$('#moduleForm_moduleUrl_span').html('');
		$('#moduleForm_moduleUrl').rules('add',{maxlength : 64,messages:{maxlength:$.validator.format('菜单URL不能超过{0}个字符')}});
		//
	} else if (data == '1' || data == '2'){
		//
		$("#moduleForm_parentId").val("");
		$("#moduleForm_parentId").attr('readonly',false);
		$('#parIdDiv').removeClass('has-error').removeClass('has-success');
		$('#parIdDiv i').removeClass().addClass('fa');
		
		//
		$('#moduleForm_moduleUrl_span').addClass('required');
		$('#moduleForm_moduleUrl_span').html('*');
		$('#moduleForm_moduleUrl').rules('remove');
		$('#moduleForm_moduleUrl').rules('add',{required:true,maxlength : 64,messages:{required:"请输入菜单URL",maxlength:$.validator.format('菜单URL不能超过{0}个字符')}});
	}
}
