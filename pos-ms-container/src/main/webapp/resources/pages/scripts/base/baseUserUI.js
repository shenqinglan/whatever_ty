$(function(){ 
	$('#userPassWordAdd').keyup(function () { 
		var strongRegex = new RegExp("^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$", "g"); 
		var mediumRegex = new RegExp("^(?=.{7,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))|((?=.*[a-z])(?=.*\\W))|((?=.*[A-Z])(?=.*\\W))|((?=.*[0-9])(?=.*\\W))).*$", "g"); 
		var enoughRegex = new RegExp("(?=.{6,}).*", "g"); 
	
		if (false == enoughRegex.test($(this).val())) { 
			$('#level').removeClass('pw-weak'); 
			$('#level').removeClass('pw-medium'); 
			$('#level').removeClass('pw-strong'); 
			$('#level').addClass(' pw-defule'); 
			 //密码小于六位的时候，密码强度图片都为灰色 
		} 
		else if (strongRegex.test($(this).val())) { 
			$('#level').removeClass('pw-weak'); 
			$('#level').removeClass('pw-medium'); 
			$('#level').removeClass('pw-strong'); 
			$('#level').addClass(' pw-strong'); 
			 //密码为八位及以上并且字母数字特殊字符三项都包括,强度最强 
		} 
		else if (mediumRegex.test($(this).val())) { 
			$('#level').removeClass('pw-weak'); 
			$('#level').removeClass('pw-medium'); 
			$('#level').removeClass('pw-strong'); 
			$('#level').addClass(' pw-medium'); 
			 //密码为七位及以上并且字母、数字、特殊字符三项中有两项，强度是中等 
		} 
		else { 
			$('#level').removeClass('pw-weak'); 
			$('#level').removeClass('pw-medium'); 
			$('#level').removeClass('pw-strong'); 
			$('#level').addClass('pw-weak'); 
			 //如果密码为6为及以下，就算字母、数字、特殊字符三项都包括，强度也是弱的 
		} 
		return true; 
	}); 
}) 

var TableAjax = function() {
	var initPickers = function() {
		// init date pickers
		$('.date-picker').datepicker({
			rtl : Metronic.isRTL(),
			autoclose : true
		});
	}
	var grid = new Datatable();
	var handleRecords = function() {

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
						// 隐藏无需显示的列
						$("#grid thead tr").find("th:eq(3)").hide();
						$("#grid tbody tr").find("td:eq(3)").hide();
						$("#grid thead tr").find("th:eq(4)").hide();
						$("#grid tbody tr").find("td:eq(4)").hide();
						$("#grid thead tr").find("th:eq(5)").hide();
						$("#grid tbody tr").find("td:eq(5)").hide();
						$("#grid thead tr").find("th:eq(6)").hide();
						$("#grid tbody tr").find("td:eq(6)").hide();
						$("#grid thead tr").find("th:eq(9)").hide();
						$("#grid tbody tr").find("td:eq(9)").hide();
						
						$("#grid tbody").find("tr").each(function(){
							var userAcc=$(this).find("td").eq(0).text();
							if("admin"==userAcc){
								$(this).find(".update").attr("disabled",true);
//								$(this).find(".update").find("i").click(function (event) {
//									   return false;
//									});
								$(this).find(".delete").attr("disabled",true);
								$(this).find(".stop").attr("disabled",true);
								$(this).find(".start").attr("disabled",true);
								$(this).find(".resetPass").attr("disabled",true);
							}
						});
						
						$(".btn-xs").find("i").click(function (event) {
							if($(this).parent().attr("disabled") == "disabled"){
								return false;
							}
						});
						
						$(".details").click(function() {
							$("#detailuserAccount").val($(this).parent().parent().find("td").eq("0").text());
							$("#detailuserName").val($(this).parent().parent().find("td").eq("1").text());
							$("#detailuserStatus").val($(this).parent().parent().find("td").eq("2").text());
							$("#detailuserSex").val($(this).parent().parent().find("td").eq("3").text());
							$("#detailuserEmail").val($(this).parent().parent().find("td").eq("4").text());
							$("#detailuserMobile").val($(this).parent().parent().find("td").eq("5").text());
							$("#detailuserOfficePhone").val($(this).parent().parent().find("td").eq("6").text());
							$("#detailuserLastLoginTime").val($(this).parent().parent().find("td").eq("7").text());
							$("#detailuserLastLoginIp").val($(this).parent().parent().find("td").eq("8").text());
							$("#detailuserRemark").val($(this).parent().parent().find("td").eq("9").text());
							
							$("#userInfoDetailsTable").modal("show");
						});

						// execute some code on ajax data load
						$(".delete").click(function() {

							if (!confirm("是否删除该用户信息？")) {
								return false;
							}
							var userId = $(this).attr("name");
							$.ajax({
								type : "POST",
								url : ctx + "/baseUsers/delete",
								data : {
									userId : userId
								},
								dataType : "json",								
								success : function(data) {
									if (data[0] == 1) {
										toastr['success'](data[1], "系统提示");
										TableAjax.callback();
									}else{
										toastr['error'](data[1], "系统提示");
									}
								}
							});
						});

						$("#grid tbody tr").each(function() {
							var status = $(this).find("td").eq(2).text();
							if ("禁用" == status) {
								$(this).find(".stop").attr("disabled",true);
							} else {
								$(this).find(".start").attr("disabled",true);
							}
						});

						$(".stop").click(function() {
							
							if (!confirm("是否禁用该用户？")) {
								return false;
							}							
							var status = "0";
							var userId = $(this).attr("name");
							$.ajax({
								type : "POST",
								url : ctx + "/baseUsers/startOrstopUser",
								data : {
									userId : userId,
									status : status
								},
								dataType : "json",
//								success : function(data) {
//									if (data[0] == "0") {
//										toastr['error']("禁用失败，请联系管理员", "系统提示");
//									} else {
//										toastr['success']("禁用成功", "系统提示");
//										TableAjax.callback();
//										// $(this).parent("td").parent("tr").find("td").eq("2").text("禁用");
//									}								
								success : function(data) {
									if (data[0] == 1) {
										toastr['success'](data[1], "系统提示");
										TableAjax.callback();
									}else{
										toastr['error'](data[1], "系统提示");
									}
								}
							});
						});
						
						//重置密码
						$(".resetPass").click(function() {
							if (!confirm("是否重置该用户密码？")) {
								return false;
							}	
							var userId = $(this).attr("name");
							$.ajax({
								type : "POST",
								url : ctx + "/baseUsers/resetPass",
								data : {
									userId : userId
								},
								dataType : "json",								
								success : function(data) {
									if (data.success) {
										toastr['success']("重置密码成功", "系统提示");
										TableAjax.callback();
									}else{
										toastr['error']("重置密码失败，请联系管理员", "系统提示");
									}
								}
							});
						});

						//启用
						$(".start").click(function() {
							if (!confirm("是否启用该用户？")) {
								return false;
							}	
							var status = "1";
							var userId = $(this).attr("name");
							$.ajax({
								type : "POST",
								url : ctx + "/baseUsers/startOrstopUser",
								data : {
									userId : userId,
									status : status
								},
								dataType : "json",
//								success : function(data) {
//									if (data[0] == "0") {
//										toastr['error']("启用失败，请联系管理员", "系统提示");
//									} else {
//										toastr['success']("启用成功", "系统提示");
//										TableAjax.callback();
//										// $(this).parent("td").parent("tr").find("td").eq("2").text("启用");
//									}
//								}
								
								success : function(data) {
									if (data[0] == 1) {
										toastr['success'](data[1], "系统提示");
										TableAjax.callback();
									}else{
										toastr['error'](data[1], "系统提示");
									}
								}
							});
						});

						$(".update")
								.click(
										function() {
											var userId = $(this).attr("name");
											clearAddTable("update");
											$
													.ajax({
														type : "POST",
														url : ctx
																+ "/baseUsers/selectById",
														data : {
															userId : userId
														},
														dataType : "json",
														success : function(data) {
															if (data[0] == 0) {
																toastr['error']
																		(
																				"获取用户信息失败,请联系管理员！",
																				"系统提示");
															} else if (data[0] == 1) {
																$("#userIdAdd")
																		.val(
																				data[1].userId);
																$(
																		"#userAccountAdd")
																		.val(
																				data[1].userAccount);
																$(
																		"#userPassWordAdd")
																		.val(
																				"nodata");
																$(
																		"#userNameAdd")
																		.val(
																				data[1].userName);
																$("#userSexAdd")
																		.val(
																				data[1].userSex);
																$(
																		"#userRoleAdd")
																		.val(
																				data[2]);
																$(
																		"#userEmailAdd")
																		.val(
																				data[1].userEmail);
																$(
																		"#userPhoneAdd")
																		.val(
																				data[1].userMobile);
																$("#userOFAdd")
																		.val(
																				data[1].userOfficePhone);
																$(
																		"#userRemarkAdd")
																		.val(
																				data[1].userRemark);
																Metronic.handleFixInputPlaceholderForIE();
																$('#createUser')
																		.modal(
																				'show');
															}
														}
													});
										});

					},
					loadingMessage : 'Loading...',
					dataTable : { // here you can define a typical datatable
						// settings from
						// http://datatables.net/usage/options

						// Uncomment below line("dom" parameter) to fix the
						// dropdown overflow issue in the datatable cells. The
						// default datatable layout
						// setup uses scrollable div(table-scrollable) with
						// overflow:auto to enable vertical scroll(see:
						// assets/global/scripts/datatable.js).
						// So when dropdowns used the scrollable div should be
						// removed.
						// "dom": "<'row'<'col-md-8 col-sm-12'pli><'col-md-4
						// col-sm-12'<'table-group-actions
						// pull-right'>>r>t<'row'<'col-md-8
						// col-sm-12'pli><'col-md-4 col-sm-12'>>",

						"bStateSave" : true, // save datatable
						// state(pagination, sort, etc)
						// in cookie.

						"lengthMenu" : [ [ 10, 20, 30 ],
								[ 10, 20, 30 ] // change per
						// page values
						// here
						],
						"pageLength" : 10, // default record count per page
						"ajax" : {
							"url" : ctx + "/baseUsers/tableAjax", // ajax
						// source
						},
						"formSearch" : "userSearchForm",
						"columnDefs" : [{'name':'USER_ACCOUNT','orderable' : true,'targets' : [0]}
						,{'name':'USER_NAME','orderable' : true,'targets' : [1]}
						,{'name':'USER_STATUS','orderable' : true,'targets' : [2]}
						,{'name':'USER_SEX','orderable' : true,'targets' : [3]}
						,{'orderable' : false,'targets' : [4]}
						,{'orderable' : false,'targets' : [5]}
						,{'orderable' : false,'targets' : [6]}
						,{'orderable' : false,'targets' : [7]}
						,{'orderable' : false,'targets' : [8]}
						,{'orderable' : false,'targets' : [9]}
						,{'orderable' : false,'targets' : [10]}
						,{'orderable' : false,'targets' : [11]}],
						"columns" : [
								{
									"title" : "账号",
									"field" : "userAccount"
								},
								{
									"title" : "用户姓名",
									"field" : "userName"
								},
								{
									"title" : "状态",
									"field" : "userStatus",
									"fieldRender" : "getUserStatus"
								},
								{
									"title" : "性别",
									"field" : "userSex",
									"fieldRender" : "getUserSex"
								},
								{
									"title" : "电子邮件",
									"field" : "userEmail"
								},
								{
									"title" : "手机",
									"field" : "userMobile"
								},
								{
									"title" : "办公电话",
									"field" : "userOfficePhone"
								},
								{
									"title" : "上次登录时间",
									"field" : "userLastLoginTime",
								},
								{
									"title" : "上次登录IP",
									"field" : "userLastLoginIp"
								},
								{
									"title" : "备注",
									"field" : "userRemark"
								},
								{
									"title" : "用户KEY",
									"field" : "userKey"
								},
								{
									"title" : "操作",
									"field" : "userId",
									'fieldRender':"getPremission"
								// "wrapper" : "<button class='btn blue'
								// onclick='getUserInfoById($field$)'
								// >编辑</button><button class='btn red'
								// onclick='deleteUser($field$)' >删除</button>"
								}, ],
						"order" : [ [ 1, "asc" ] ]
					// set first column as a default sort by asc
					}
				});

	}

	var handleQuery = function() {
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

	return {

		// main function to initiate the module
		init : function() {

			initPickers();
			handleRecords();
			handleQuery();
		},
		callback : function() {
			reload();
		}
	};

}();

$('#create').click(function() {
	clearAddTable("add");
	Metronic.handleFixInputPlaceholderForIE();
	$('#createUser').modal('show');
});

//
$("#reset").click(function() {
	$("#userNameSearch").val("");
	$("#userAccountSearch").val("");
	TableAjax.callback();
});

$('#addUser').click(function() {
	if (!validator.form())
		return false;
	$.ajax({
		type : "POST",
		url : ctx + "/baseUsers/add",
		data : {
			userId : $("#userIdAdd").val(),
			userAccount : $("#userAccountAdd").val(),
			userPassword : hex_md5(hex_md5($("#userPassWordAdd").val()).toUpperCase()).toUpperCase(),
			userName : $("#userNameAdd").val(),
			userSex : $("#userSexAdd").val(),
			userEmail : $("#userEmailAdd").val(),
			userMobile : $("#userPhoneAdd").val(),
			userOfficePhone : $("#userOFAdd").val(),
			userRole : $("#userRoleAdd").val(),
			userRemark : $("#userRemarkAdd").val()
		},
		dataType : "json",
		success : function(data) {
			if (data[0] == 1) {
				$('#createUser').modal('hide');
				toastr['success'](data[1], "系统提示");
				TableAjax.callback();
			} else {
				toastr['error'](data[1], "系统提示");
			}
		}
	});
});

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

function getUserSex(data) {
	if (data == "1") {
		return "女";
	} else if (data == "0") {
		return "男";
	} else {
		return "";
	}
}

function getUserStatus(data) {
	if (data == "1") {
		return "禁用";
	} else if (data == "0") {
		return "启用";
	} else {
		return "";
	}
}

// 设置页面时间格式显示
function getDateValue(value) {
	if (value == null || "" == value || "null" == value) {
		return "";
	}
	var now = new Date();
	now.setTime(value);
	var months = now.getMonth() + 1;
	if (months < 10) {
		months = '0' + months;
	}
	var day;
	if (now.getDate() < 10) {
		day = '0' + now.getDate();
	} else {
		day = now.getDate();
	}
	var hours = now.getHours();
	if (hours < 10) {
		hours = '0' + hours;
	}
	var minutes = now.getMinutes();
	if (minutes < 10) {
		minutes = '0' + minutes;
	}
	var seconds = now.getSeconds();
	if (seconds < 10) {
		seconds = '0' + seconds;
	}
	var dateValue = now.getFullYear() + '-' + months + '-' + day + ' ' + hours
			+ ':' + minutes + ':' + seconds;
	return dateValue;

}

function clearAddTable(type) {
	// 清除验证css样式
	$('.form-group').removeClass('has-error').removeClass('has-success');
	$('.form-group i').removeClass().addClass('fa');
	$('#level').removeClass('pw-weak'); 
	$('#level').removeClass('pw-medium'); 
	$('#level').removeClass('pw-strong'); 
	$('#level').addClass('pw-defule'); 
	if ("update" == type) {
		$("#myModalLabel").html("编辑用户");
		$("#userAccountAdd").attr("readonly", true);
		$("#userPassWordAdd").attr("readonly", true);
	}
	if ("add" == type) {		
		$("#myModalLabel").html("新建用户");
		$("#userAccountAdd").attr("readOnly",false);
		$("#userPassWordAdd").attr("readonly", false);
	}
	$.ajax({
		type : "POST",
		url : ctx + "/baseUsers/getRoleList",
		dataType : "json",
		success : function(data) {
			$("#userRoleAdd").html("");
			for ( var i = 0; i < data.length; i++) {
				if(data[i].roleId!="SYSTEM_MANAGER_ROLE"&&data[i].roleId!="AP_ROLE"){
					$("#userRoleAdd").append(
						"<option value='" + data[i].roleId + "'>"
								+ data[i].roleName + "</option>");
				}
			}
		}
	});
	$("#userIdAdd").val("");
	$("#userAccountAdd").val("");
	$("#userPassWordAdd").val("");
	$("#userNameAdd").val("");
	$("#userSexAdd").val("");
	$("#userEmailAdd").val("");
	$("#userPhoneAdd").val("");
	$("#userOFAdd").val("");
	$("#userRemarkAdd").val("");
}

// jquey validate验证
var FormValidation = function() {
	// validation using icons
	var handleValidation2 = function() {
		// for more info visit the official plugin documentation:
		// http://docs.jquery.com/Plugins/Validation
		var form2 = $('#userForm');
		var error2 = $('.alert-danger', form2);
		var success2 = $('.alert-success', form2);

		return form2.validate({
			errorElement : 'span', // default input error message container
			errorClass : 'help-block help-block-error', // default input error
														// message class
			focusInvalid : false, // do not focus the last invalid input
			ignore : "", // validate all fields including form hidden input
			rules : {
				userAccount : {
					userAccount:true,
					required : true
				},
				userPassWord : {
					minlength : 6,
					maxlength : 16,
					userPass: true,
					required : true
				},
				userName : {
					maxlength : 32,
					required : true
				},
				userSex : {
					required : true
				},
				userEmail : {
					maxlength : 32,
					email : true
				},
				userPhone : {
					telephone : true
				},
				userRemark : {
					maxlength : 64
				},
				userOF : {
					maxlength : 13,
					userOF : true
				},
				userRole: {
					required : true
				},
			},

			invalidHandler : function(event, validator) { // display error
															// alert on form
															// submit
				success2.hide();
				error2.show();
				Metronic.scrollTo(error2, -200);
			},

			errorPlacement : function(error, element) { // render error
														// placement for each
														// input type
				var icon = Metronic.handValidStyle(element);
				icon.removeClass('fa-check').addClass("fa-warning");
				icon.attr("data-original-title", error.text()).tooltip({
					'container' : '#userForm'
				});
			},

			highlight : function(element) { // hightlight error inputs
				$(element).closest('.form-group').removeClass("has-success")
						.addClass('has-error'); // set error class to the
												// control group
			},

			unhighlight : function(element) { // revert the change done by
												// hightlight

			},

			success : function(label, element) {
				var icon = Metronic.handValidStyle(element);
				$(element).closest('.form-group').removeClass('has-error')
						.addClass('has-success'); // set success class to the
													// control group
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
var validator = FormValidation.init();
