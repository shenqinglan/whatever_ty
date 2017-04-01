/**
 * 
 */
// form校验
var FormValidation = function() {
	// validation using icons
	var handleValidation2 = function() {
		// for more info visit the official plugin documentation:
		// http://docs.jquery.com/Plugins/Validation
		var form2 = $('#businessPartnerForm');
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
				orgCode : {
					maxlength : 32,
					required : true,
					orgCode:true
				},
				orgName : {
					maxlength : 64,
					required : true
				},
				url : {
					maxlength : 18,
					required : true,
					ip:true
				},
				port : {
					maxlength : 6,
					required : true,
					port:true
				},
				remark : {
					maxlength : 2000
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
					'container' : '#businessPartnerForm'
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

//			submitHandler : function(form) {
//				success2.show();
//				error2.hide();
//				form2[0].submit(); // submit the form
//				$('#createModule').modal('hide');
//				businessPartnerUI.callback();
//			}
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
var businessPartnerUI = function() {
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
						$(".update").click(function() {
							setFormStatus("edit");
							var orgCode = $(this).attr("name");
							$.ajax({
								type : "POST",
								url : ctx + "/businessPartner/view",
								data : {
									orgCode : orgCode
								},
								dataType : "json",
								success : function(data) {
									$.each(data, function(name, val) {
													$('#businessPartnerForm').find("[name='"
															+ name + "']").val(val);
												});
										Metronic.handleFixInputPlaceholderForIE();
										$('#createModule').modal('show');
										resetFileDiv();
								}
							});
						});
						
						$(".delete").click(function() {
							if (!confirm("删除之后数据无法恢复，是否确认？")) {
								return false;
							}
							var orgCode = $(this).attr("name");
							$.ajax({
										type : "POST",
										url : ctx + "/businessPartner/delete",
										data : {
											orgCode : orgCode
										},
										dataType : "json",
										success : function(data) {
											if (data.success){
												toastr['success']("删除成功", "系统提示");
												businessPartnerUI.callback();
											} else{
												toastr['error'](data.msg,
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
							url : ctx + '/businessPartner/find'
						},
						'formSearch' : 'searchForm',
						"columnDefs" : [{'name':'ORG_CODE','orderable' : true,'targets' : [0]}
						,{'name':'ORG_NAME','orderable' : true,'targets' : [1]}
						,{'name':'STATUS','orderable' : true,'targets' : [2]}
						,{'name':'TYPE','orderable' : true,'targets' : [3]}
						,{'name':'PATTERN','orderable' : true,'targets' : [4]}
						,{'name':'URL','orderable' : true,'targets' : [5]}
						,{'name':'PORT','orderable' : true,'targets' : [6]}
						,{'orderable' : false,'targets' : [7]}
						,{'orderable' : false,'targets' : [8]}],
						'columns' : [
								{
									'title' : '合作方机构代码',
									'field' : 'orgCode'
								},
								{
									'title' : '合作方机构名称',
									'field' : 'orgName'
								},
								{
									'title' : '合作状态',
									'field' : 'status',
									'fieldRender' : "getStatus"
										
								},
								{
									'title' : '合作类型',
									'field' : 'type',
									'fieldRender' : "getType"
								},
								{
									'title' : '接入方式',
									'field' : 'pattern'
								},
								{
									'title' : 'IP',
									'field' : 'url'
								},
								{
									'title' : '端口号',
									'field' : 'port'
								},
								{
									'title' : '描述',
									'field' : 'remark',
									'fieldRender' : "cutString"
								},
								{
									'title' : '操作',
									'field' : 'orgCode',
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
	
	
		// 打开新增窗口
		$('#create').click(function() {
			setFormStatus("add");
			$('#businessPartnerForm')[0].reset();
			Metronic.handleFixInputPlaceholderForIE();
			$('#createModule').modal('show');
			resetFileDiv();
		});
		
		$('#patternId').change(function() {
			resetFileDiv();
		});
		
		function resetFileDiv() {
			if($('#patternId').val()=="socket"||$('#patternId').val()=="http") 
			{
				$('#fileId').hide();
				$('#file').rules("remove");
			} else {
				$('#fileId').show();
				$('#file').rules("add", { required: true,
				    extension:"crt", messages: { extension: "接入凭证必须为.crt文件"} });
			}
		}
		
		// 保存
//		$('#addModule').click(function() {
//			if (!validator.form())
//				return false;
//			$.ajax({
//				type : 'POST',
//				url : ctx + '/businessPartner/save',
//				data : $('#certificate').serialize(),
//				dataType : 'json',
//				success : function(data) {
//					if (data.success) {
//						toastr['success'](data.msg, "系统提示");
//						$('#createModule').modal('hide');
//						businessPartnerUI.callback();
//					} else {
//						toastr['error'](data.msg, "系统提示");
//					}
//				},
//				error : function(data) {
//					toastr['error'](data.msg, "系统提示");
//				}
//			});
//		});
		
	return {
		init : function() {
			
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

function getFieldDisplay(options,value){
	var txt = ""
	options.each(function(){
        var val = $(this).val();
        if(val == value)
        {
        	txt = $(this).text();
        	return;
        }
    });
	return txt;
}

function getType(value) {
	var options = $("#typeId option");
	return getFieldDisplay(options, value);
}

function getStatus(value) {
	var options = $("#statusId option");
	return getFieldDisplay(options, value);
}

function setFormStatus(type) {
	// 清除验证css样式
	$('.form-group').removeClass('has-error').removeClass('has-success');
	$('.form-group i').removeClass().addClass('fa');
	if ("edit" == type) {
		$("#tag").val("update");
		$("#businessPartnerModalLabel").html("编辑合作机构");//请完善编辑内容
		$("#orgCodeId").attr("readonly","readonly");
	} else if ("add" == type) {
		$("#tag").val("add");
		$("#businessPartnerModalLabel").html("新建合作机构");//请完善新建内容
		$("#orgCodeId").removeAttr("readonly");;
	}
};



$("#resetForm").click(function() {
	$('#searchForm')[0].reset();
	businessPartnerUI.callback();
});

function afterEdit(flag, msg) {
	if (flag == 0) {
		toastr['error'](msg, "系统提示");
	} else {
		$('#createModule').modal('hide');
		toastr['success'](msg, "系统提示");
		businessPartnerUI.callback();
	}
}
