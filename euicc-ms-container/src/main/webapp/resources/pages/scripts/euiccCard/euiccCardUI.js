/**
 * 
 */
// form校验
var FormValidation = function() {
	// validation using icons
	var handleValidation2 = function() {
		// for more info visit the official plugin documentation:
		// http://docs.jquery.com/Plugins/Validation
		var form2 = $('#euiccCardForm');
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
				eid : {
					maxlength : 32,
					required : true
				},
				eumId : {
					maxlength : 32,
					required : true
				},
				productiondate : {
					maxlength : 32,
					required : true
				},
				platformtype : {
					maxlength : 2,
					//required : true
				},
				platformversion : {
					//required : true,
					maxlength : 2
				},
				remainingmemory : {
					maxlength : 10,
					required : true
				},
				availablememoryforprofiles : {
					maxlength : 10,
					required : true
				},
				smsrId : {
					//maxlength : 2,
					required : true
				},
				catTpSupport : {
					maxlength : 2,
					//required : true
				},
				catTpVersion : {
					maxlength : 6,
					required : true
				},
				httpSupport : {
					maxlength : 2,
					//required : true
				},
				httpVersion : {
					maxlength : 6,
					required : true
				},
				securePacketVersion : {
					maxlength : 6,
					required : true
				},
				remoteProvisioningVersion : {
					maxlength : 6,
					required : true
				},
				isdRAid : {
					maxlength : 32,
					required : true
				},
				pol1Id : {
					maxlength : 2,
					//required : true
				},
				certEcasdEcka : {
					//maxlength : 32,
					//required : true
				},
				phoneNo : {
					maxlength : 32,
					minlength : 11,
					digits : true,
					required : true
				}
			},
			messages:{
				eid:{required:"请输入卡ID",maxlength:$.validator.format('卡ID不能超过{0}个字符')} 
				,eumId:{required:"请输入卡商ID",maxlength:$.validator.format('卡商ID不能超过{0}个字符')}
				,productiondate:{required:"请输入生产日期",maxlength:$.validator.format('生产日期不能超过{0}个字符')} 
				,platformtype:{required:"请输入平台类型",maxlength:$.validator.format('平台类型不能超过{0}个字符')} 
				,platformversion:{required:"请输入平台版本",maxlength:$.validator.format('平台版本不能超过{0}个字符')} 
				,remainingmemory:{required:"请输入剩余空间",maxlength:$.validator.format('剩余空间不能超过{0}个字符')}
				,availablememoryforprofiles:{required:"请输入空闲空间",maxlength:$.validator.format('空闲空间不能超过{0}个字符')}
				,smsrId:{required:"请输入SMSR_ID",maxlength:$.validator.format('SMSR_ID不能超过{0}个字符')} 
				,catTpVersion:{required:"请输入CAT_TP版本",maxlength:$.validator.format('CAT_TP版本不能超过{0}个字符')}
				,httpVersion:{required:"请输入HTTPS版本号",maxlength:$.validator.format('HTTPS版本号不能超过{0}个字符')}
				,securePacketVersion:{required:"请输入最高版本号",maxlength:$.validator.format('最高版本号不能超过{0}个字符')} 
				,remoteProvisioningVersion:{required:"请输入管理系统版本号",maxlength:$.validator.format('管理系统版本号不能超过{0}个字符')} 
				,isdRAid:{required:"请输入ISD_R_AID",maxlength:$.validator.format('ISD_R_AID不能超过{0}个字符')}
				,certEcasdEcka:{required:"请输入CERT_ECASD_ECKA",maxlength:$.validator.format('CERT_ECASD_ECKA不能超过{0}个字符')}
				,phoneNo:{required:"请输入手机号码",maxlength:$.validator.format('手机号码不能超过{0}个字符')}
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
					'container' : '#euiccCardForm'
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

//Scp03Form
var Scp03FormValidation = function() {
	// validation using icons
	var handleValidation2 = function() {
		// for more info visit the official plugin documentation:
		// http://docs.jquery.com/Plugins/Validation
		var form2 = $('#scp03Form');
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

				scp03Id : {
					maxlength : 32,
					required : true
				},
				eid : {
					maxlength : 32,
					required : true
				},
				isdPAid : {
					maxlength : 32,
					required : true
				},
				id : {
					maxlength : 32,
					required : true
				},
				version : {
					required : true,
					maxlength : 32
				},
				data : {
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
					'container' : '#scp03Form'
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

				scp80Id : {
					maxlength : 32,
					required : true
				},
				eid : {
					maxlength : 32,
					required : true
				},
				id : {
					maxlength : 32,
					required : true
				},
				version : {
					required : true,
					maxlength : 32
				},
				data : {
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

//Scp81Form
var Scp81FormValidation = function() {
	// validation using icons
	var handleValidation2 = function() {
		// for more info visit the official plugin documentation:
		// http://docs.jquery.com/Plugins/Validation
		var form2 = $('#scp81Form');
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

				scp81Id : {
					maxlength : 32,
					required : true
				},
				eid : {
					maxlength : 32,
					required : true
				},
				id : {
					maxlength : 32,
					required : true
				},
				version : {
					required : true,
					maxlength : 32
				},
				data : {
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
					'container' : '#scp81Form'
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

//IsdpForm
var IsdpFormValidation = function() {
	// validation using icons
	var handleValidation2 = function() {
		// for more info visit the official plugin documentation:
		// http://docs.jquery.com/Plugins/Validation
		var form2 = $('#isdpForm');
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

				pId : {
					maxlength : 32,
					required : true
				},
				eid : {
					maxlength : 32,
					required : true
				},
				isdPAid : {
					maxlength : 32,
					required : true
				},
				isdPState : {
					required : true,
					maxlength : 32
				},
				createDt : {
					maxlength : 32,
					required : true
				},
				updateDt : {
					maxlength : 32,
					required : true
				},
				isdPLoadfileAid : {
					maxlength : 32,
					required : true
				},
				isdPModuleAid : {
					maxlength : 32,
					required : true
				},
				allocatedMemory : {
					maxlength : 32,
					required : true
				},
				connectivityParam : {
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
					'container' : '#scp81Form'
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
var euiccCardUI = function() {
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
																+ '/euiccCard/view',
														data : {
															eid : fieldId
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
										url : ctx + '/euiccCard/delete',
										data : {
											eid : fieldId
										},
										dataType : 'json',
										success : function(data) {
											if(data.success){
												toastr['success']("删除成功",
													"系统提示");
												euiccCardUI.callback();
											}else{
												toastr['error'](data.msg,
												"系统提示");
											}
										},
										error : function(data) {
											toastr['error']("获取信息失败,请联系管理员！",
													"系统提示");
										}
									});
								});
						$(".scp80")
						.click(
								function() {
									var fieldId = $(this).attr("name");
									$
											.ajax({
												type : 'POST',
												url : ctx
														+ '/euiccCard/showScp80',
												data : {
													eid : fieldId
												},
												dataType : 'json',
												success : function(data) {
													$("#scp80TableEid").val(fieldId);
													if (isScp80TableCreated == 1) {
														scp80grid.getDataTable().ajax.reload();
														scp80grid.clearAjaxParams();
													} else {
														createScp80Table();
													}
													
													Metronic
															.handleFixInputPlaceholderForIE();
													$('#createScp80TableModule')
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
						$(".scp81")
						.click(
								function() {
									var fieldId = $(this).attr("name");
									$
											.ajax({
												type : 'POST',
												url : ctx
														+ '/euiccCard/showScp81',
												data : {
													eid : fieldId
												},
												dataType : 'json',
												success : function(data) {
													$("#scp81TableEid").val(fieldId);
													if (isScp81TableCreated == 1) {
														scp81grid.getDataTable().ajax.reload();
														scp81grid.clearAjaxParams();
													} else {
														createScp81Table();
													}
													Metronic
															.handleFixInputPlaceholderForIE();
													$('#createScp81TableModule')
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
						$(".scp03")
						.click(
								function() {
									var fieldId = $(this).attr("name");
									$
											.ajax({
												type : 'POST',
												url : ctx
														+ '/euiccCard/showScp03',
												data : {
													eid : fieldId
												},
												dataType : 'json',
												success : function(data) {
													$("#scp03TableEid").val(fieldId);
													if (isScp03TableCreated == 1) {
														scp03grid.getDataTable().ajax.reload();
														scp03grid.clearAjaxParams();
													} else {
														createScp03Table();
													}
													Metronic
															.handleFixInputPlaceholderForIE();
													$('#createScp03TableModule')
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
						$(".isdp")
						.click(
								function() {
									var fieldId = $(this).attr("name");
									$
											.ajax({
												type : 'POST',
												url : ctx
														+ '/euiccCard/showIsdp',
												data : {
													eid : fieldId
												},
												dataType : 'json',
												success : function(data) {
													$("#isdpTableEid").val(fieldId);
													if (isIsdpTableCreated == 1) {
														isdpgrid.getDataTable().ajax.reload();
														isdpgrid.clearAjaxParams();
													} else {
														createIsdpTable();
													}
													Metronic
															.handleFixInputPlaceholderForIE();
													$('#createIsdpTableModule')
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
					},
					loadingMessage : 'Loading...',
					dataTable : {
						'sScrollX' : '120%',
						'bStateSave' : true,
						'lengthMenu' : [ [ 10, 20, 30 ], [ 10, 20, 30 ] // change
																		// per
																		// page
																		// values
																		// here
						],
						'pageLength' : 20,
						'ajax' : {
							url : ctx + '/euiccCard/find'
						},
						'formSearch' : 'searchForm',
						"columnDefs" : [{'name':'EID','orderable' : true,'targets' : [0]}
						,{'name':'EUM_ID','orderable' : true,'targets' : [1]}
						,{'name':'PRODUCTIONDATE','orderable' : false,'targets' : [2]}
						,{'name':'PLATFORMTYPE','orderable' : false,'targets' : [3]}
						,{'name':'PLATFORMVERSION','orderable' : false,'targets' : [4]}
						,{'name':'REMAININGMEMORY','orderable' : false,'targets' : [5]}
						,{'name':'AVAILABLEMEMORYFORPROFILES','orderable' : false,'targets' : [6]}
						,{'name':'SMSR_ID','orderable' : false,'targets' : [7]}
						,{'name':'ECASD_ID','orderable' : false,'targets' : [8]}
						,{'name':'CAPABILITY_ID','orderable' : false,'targets' : [9]}
						/*,{'name':'CAT_TP_SUPPORT','orderable' : false,'targets' : [10]}
						,{'name':'CAT_TP_VERSION','orderable' : false,'targets' : [11]}
						,{'name':'HTTP_SUPPORT','orderable' : false,'targets' : [12]}
						,{'name':'HTTP_VERSION','orderable' : false,'targets' : [13]}
						,{'name':'SECURE_PACKET_VERSION','orderable' : false,'targets' : [14]}
						,{'name':'REMOTE_PROVISIONING_VERSION','orderable' : false,'targets' : [15]}*/
						,{'name':'ISD_R_AID','orderable' : false,'targets' : [10]}
						/*,{'name':'POL1_ID','orderable' : false,'targets' : [17]}*/
						,{'name':'CERT_ECASD_ECKA','orderable' : false,'targets' : [11]}
						,{'name':'PHONE_NO','orderable' : false,'targets' : [12]}
						,{'name':'EID','orderable' : false,'targets' : [13]}],
						'columns' : [
								{
									'title' : '卡ID',
									'field' : 'eid'
								},
								{
									'title' : '卡商ID',
									'field' : 'eumId'
								},
								{
									'title' : '生产日期',
									'field' : 'productiondate'
								},
								{
									'title' : '平台类型',
									'field' : 'platformtype'
								},
								{
									'title' : '平台版本',
									'field' : 'platformversion'
								},
								{
									'title' : '剩余空间',
									'field' : 'remainingmemory'
								},
								{
									'title' : '空闲空间',
									'field' : 'availablememoryforprofiles'
								},
								{
									'title' : 'SMSR_ID',
									'field' : 'smsrId'
								},
								{
									'title' : 'ECASD_ID',
									'field' : 'ecasdId'
								},
								{
									'title' : 'CAPABILITY_ID',
									'field' : 'capabilityId'
								},
								/*{
									'title' : '是否支持CAT_TP',
									'field' : 'catTpSupport',
									'fieldRender' : 'statusRender'
								},
								{
									'title' : 'CAT_TP版本',
									'field' : 'catTpVersion'
								},
								{
									'title' : '是否支持HTTPS',
									'field' : 'httpSupport',
									'fieldRender' : 'statusRender'
								},*/
								/*{
									'title' : 'HTTPS版本',
									'field' : 'httpVersion'
								},
								{
									'title' : '最高版本号',
									'field' : 'securePacketVersion'
								},
								{
									'title' : '管理系统版本号',
									'field' : 'remoteProvisioningVersion'
								},*/
								{
									'title' : 'ISD_R_AID',
									'field' : 'isdRAid'
								},
								/*{
									'title' : 'POL1_ID',
									'field' : 'pol1Id'
								},*/
								{
									'title' : 'CERT_ECASD_ECKA',
									'field' : 'certEcasdEcka'
								},
								{
									'title' : '手机号码',
									'field' : 'phoneNo'
								},
								{
									'title' : '操作',
									'field' : 'eid',
									'fieldRender' : "getPremission"
								} ],
						'order' : [ [ 0, "asc" ] ] 
					}
				});
	};

	var scp03grid = new Datatable();
	var isScp03TableCreated = 0;
	var scp03validator = Scp03FormValidation.init();
	// 创建表格
	var createScp03Table = function() {
		scp03grid
				.init({
					src : $("#scp03grid"),
					onSuccess : function(scp03grid) {
						// execute some code after table records loaded
					},
					onError : function(scp03grid) {
						// execute some code on network or other general error
					},
					onDataLoad : function(scp03grid) {
						$(".edit")
								.click(
										function() {
											setScp03FormStatus("edit");
											var fieldId = $(this).attr("name");
											$
													.ajax({
														type : 'POST',
														url : ctx
																+ '/euiccCard/scp03View',
														data : {
															scp03Id : fieldId
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
																						'#createScp03Module')
																						.find(
																								"[name='"
																										+ name
																										+ "']")
																						.val(
																								val);
																			});
															Metronic
																	.handleFixInputPlaceholderForIE();
															$('#createScp03Module')
																	.modal(
																			'show');
															$('#createScp03TableModule').modal('hide');
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
						'pageLength' : 20,
						'ajax' : {
							url : ctx + '/euiccCard/findScp03'
						},
						"columnDefs" : [{'name':'SCP03_ID','orderable' : true,'targets' : [0]}
						,{'name':'EID','orderable' : true,'targets' : [1]}
						,{'name':'ISD_P_AID','orderable' : false,'targets' : [2]}
						,{'name':'ID','orderable' : false,'targets' : [3]}
						,{'name':'VERSION','orderable' : false,'targets' : [4]}
						,{'name':'DATA','orderable' : false,'targets' : [5]}
						,{'name':'SCP03_ID','orderable' : false,'targets' : [6]}],
						'columns' : [
								{
									'title' : 'SCP03_ID',
									'field' : 'scp03Id'
								},
								{
									'title' : '卡ID',
									'field' : 'eid'
								},
								{
									'title' : 'ISD_P_AID',
									'field' : 'isdPAid'
								},
								{
									'title' : '版本ID',
									'field' : 'id'
								},
								{
									'title' : '版本',
									'field' : 'version'
								},
								{
									'title' : '密钥数据',
									'field' : 'data'
								},
								{
									'title' : '操作',
									'field' : 'scp03Id',
									'fieldRender' : "getScpPremission"
								} ],
						'order' : [ [ 0, "asc" ] ] 
					}
				});
		isScp03TableCreated = 1;
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
						$(".edit")
								.click(
										function() {
											setScp80FormStatus("edit");
											var fieldId = $(this).attr("name");
											$
													.ajax({
														type : 'POST',
														url : ctx
																+ '/euiccCard/scp80View',
														data : {
															scp80Id : fieldId
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
																						'#createScp80Module')
																						.find(
																								"[name='"
																										+ name
																										+ "']")
																						.val(
																								val);
																			});
															Metronic
																	.handleFixInputPlaceholderForIE();
															$('#createScp80Module')
																	.modal(
																			'show');
															$('#createScp80TableModule').modal('hide');
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
						'pageLength' : 20,
						'ajax' : {
							url : ctx + '/euiccCard/findScp80'
						},
						"columnDefs" : [{'name':'SCP80_ID','orderable' : true,'targets' : [0]}
						,{'name':'EID','orderable' : true,'targets' : [1]}
						,{'name':'ID','orderable' : false,'targets' : [2]}
						,{'name':'VERSION','orderable' : false,'targets' : [3]}
						,{'name':'DATA','orderable' : false,'targets' : [4]}
						,{'name':'SCP80_ID','orderable' : false,'targets' : [5]}],
						'columns' : [
								{
									'title' : 'SCP80_ID',
									'field' : 'scp80Id'
								},
								{
									'title' : '卡ID',
									'field' : 'eid'
								},
								{
									'title' : '版本ID',
									'field' : 'id'
								},
								{
									'title' : '版本',
									'field' : 'version'
								},
								{
									'title' : '密钥数据',
									'field' : 'data'
								},
								{
									'title' : '操作',
									'field' : 'scp80Id',
									'fieldRender' : "getScpPremission"
								} ],
						'order' : [ [ 0, "asc" ] ] 
					}
					
				});
		isScp80TableCreated = 1;
	};
	
	var scp81grid = new Datatable();
	var isScp81TableCreated = 0;
	var scp81validator = Scp81FormValidation.init();
	// 创建表格
	var createScp81Table = function() {
		scp81grid
				.init({
					src : $("#scp81grid"),
					onSuccess : function(scp81grid) {
						// execute some code after table records loaded
					},
					onError : function(scp81grid) {
						// execute some code on network or other general error
					},
					onDataLoad : function(scp81grid) {
						$(".edit")
								.click(
										function() {
											setScp81FormStatus("edit");
											var fieldId = $(this).attr("name");
											$
													.ajax({
														type : 'POST',
														url : ctx
																+ '/euiccCard/scp81View',
														data : {
															scp81Id : fieldId
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
																						'#createScp81Module')
																						.find(
																								"[name='"
																										+ name
																										+ "']")
																						.val(
																								val);
																			});
															Metronic
																	.handleFixInputPlaceholderForIE();
															$('#createScp81Module')
																	.modal(
																			'show');
															$('#createScp81TableModule').modal('hide');
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
						'pageLength' : 20,
						'ajax' : {
							url : ctx + '/euiccCard/findScp81'
						},
						"columnDefs" : [{'name':'SCP81_ID','orderable' : true,'targets' : [0]}
						,{'name':'EID','orderable' : true,'targets' : [1]}
						,{'name':'ID','orderable' : false,'targets' : [2]}
						,{'name':'VERSION','orderable' : false,'targets' : [3]}
						,{'name':'DATA','orderable' : false,'targets' : [4]}
						,{'name':'SCP81_ID','orderable' : false,'targets' : [5]}],
						'columns' : [
								{
									'title' : 'SCP81_ID',
									'field' : 'scp81Id'
								},
								{
									'title' : '卡ID',
									'field' : 'eid'
								},
								{
									'title' : '版本ID',
									'field' : 'id'
								},
								{
									'title' : '版本',
									'field' : 'version'
								},
								{
									'title' : '密钥数据',
									'field' : 'data'
								},
								{
									'title' : '操作',
									'field' : 'scp81Id',
									'fieldRender' : "getScpPremission"
								} ],
						'order' : [ [ 0, "asc" ] ] 
					}
				});
		isScp81TableCreated = 1;
	};
	
	var isdpgrid = new Datatable();
	var isIsdpTableCreated = 0;
	var isdpvalidator = IsdpFormValidation.init();
	// 创建表格
	var createIsdpTable = function() {
		isdpgrid
				.init({
					src : $("#isdpgrid"),
					onSuccess : function(isdpgrid) {
						// execute some code after table records loaded
					},
					onError : function(isdpgrid) {
						// execute some code on network or other general error
					},
					onDataLoad : function(isdpgrid) {
						$(".edit")
								.click(
										function() {
											setIsdpFormStatus("edit");
											var fieldId = $(this).attr("name");
											$
													.ajax({
														type : 'POST',
														url : ctx
																+ '/euiccCard/isdpView',
														data : {
															pId : fieldId
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
																						'#createIsdpModule')
																						.find(
																								"[name='"
																										+ name
																										+ "']")
																						.val(
																								val);
																			});
															Metronic
																	.handleFixInputPlaceholderForIE();
															$('#createIsdpModule')
																	.modal(
																			'show');
															$('#createIsdpTableModule').modal('hide');
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
						'pageLength' : 20,
						'ajax' : {
							url : ctx + '/euiccCard/findIsdp'
						},
						"columnDefs" : [{'name':'P_ID','orderable' : true,'targets' : [0]}
						,{'name':'EID','orderable' : true,'targets' : [1]}
						,{'name':'ISD_P_AID','orderable' : false,'targets' : [2]}
						,{'name':'ISD_P_STATE','orderable' : false,'targets' : [3]}
						,{'name':'CREATE_DATE','orderable' : false,'targets' : [4]}
						,{'name':'UPDATE_DATE','orderable' : false,'targets' : [5]}
						,{'name':'ISD_P_LOADFILE_AID','orderable' : false,'targets' : [6]}
						,{'name':'ISD_P_MODULE_AID','orderable' : false,'targets' : [7]}
						,{'name':'ALLOCATED_MEMORY','orderable' : false,'targets' : [8]}
						,{'name':'CONNECTIVITY_PARAMS','orderable' : false,'targets' : [9]}],
						'columns' : [
								{
									'title' : 'P_ID',
									'field' : 'pId'
								},
								{
									'title' : '卡ID',
									'field' : 'eid'
								},
								{
									'title' : 'ISD_P_AID',
									'field' : 'isdPAid'
								},
								{
									'title' : 'ISDP状态',
									'field' : 'isdPState',
									'fieldRender' : "isdpStatusRender"
								},
								{
									'title' : '创建时间',
									'field' : 'createDt'
								},
								{
									'title' : '更新时间',
									'field' : 'updateDt'
								},
								{
									'title' : '下载文件AID',
									'field' : 'isdPLoadfileAid'
								},
								{
									'title' : '模块AID',
									'field' : 'isdPModuleAid'
								},
								{
									'title' : '内存空间',
									'field' : 'allocatedMemory'
								},
								{
									'title' : '连接参数',
									'field' : 'connectivityParam'
								},
								{
									'title' : '操作',
									'field' : 'pId',
									'fieldRender' : "getScpPremission"
								} ],
						'order' : [ [ 0, "asc" ] ] 
					}
				});
		isIsdpTableCreated = 1;
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
		scp03grid.getDataTable().ajax.reload();
		scp03grid.clearAjaxParams();
		scp80grid.getDataTable().ajax.reload();
		scp80grid.clearAjaxParams();
		scp81grid.getDataTable().ajax.reload();
		scp81grid.clearAjaxParams();
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
			$('#euiccCardForm')[0].reset();
			$('#createModule').modal('show');
		});
		
		//新建Scp80
		$('#createScp80').click(function(){
			setScp80FormStatus("add");
			Metronic.handleFixInputPlaceholderForIE();
			$('#scp80Form')[0].reset();
			$('#scp80Form').find(':input[name="eid"]').val($("#scp80TableEid").val());
			$('#createScp80Module').modal('show');
			$('#createScp80TableModule').modal('hide');
		});
		
		//新建Scp81
		$('#createScp81').click(function(){
			setScp81FormStatus("add");
			Metronic.handleFixInputPlaceholderForIE();
			$('#scp81Form')[0].reset();
			$('#scp81Form').find(':input[name="eid"]').val($("#scp81TableEid").val());
			$('#createScp81Module').modal('show');
			$('#createScp81TableModule').modal('hide');
		});
		
		//新建Scp03
		$('#createScp03').click(function(){
			setScp03FormStatus("add");
			Metronic.handleFixInputPlaceholderForIE();
			$('#scp03Form')[0].reset();
			$('#scp03Form').find(':input[name="eid"]').val($("#scp03TableEid").val());
			$('#createScp03Module').modal('show');
			$('#createScp03TableModule').modal('hide');
		});
		
		//新建ISDP
		$('#createIsdp').click(function(){
			setIsdpFormStatus("add");
			Metronic.handleFixInputPlaceholderForIE();
			$('#isdpForm')[0].reset();
			$('#isdpForm').find(':input[name="eid"]').val($("#isdpTableEid").val());
			$('#createIsdpModule').modal('show');
			$('#createIsdpTableModule').modal('hide');
		});

		// 导入
		$('#import').click(function(){
			toastr.clear();
			 if ($('#fileUpload').val().indexOf(".xls") < 0) {
					toastr['error']("请选择Excel文档", "系统提示");
					return;
				}
			$.ajaxFileUpload({
				type : 'POST',
				url : ctx + '/euiccCard/importExl',
				secureuri:false,  
				fileElementId:'fileUpload',
				dataType : 'json',
//				timeout : 180000,
				success : function(data) {
					if (data.success) {
						toastr['success'](data.msg, "系统提示");
						euiccCardUI.callback();		
					} else {
						toastr['error'](data.msg, "系统提示");
					}
				},
				error : function(data) {
					toastr['error'](data.msg, "系统提示");
				},
//				complete : function(XMLHttpRequest,status){
//					if(status == 'timeout'){
//						toastr['error']("系统调用超时",
//						"系统提示");
//					}
//				}
			});
		});
		// 保存
		$('#addModule').click(function() {
			//清除之前显示的弹窗
			toastr.clear();
			if (!validator.form())
				return false;
			$.ajax({
				type : 'POST',
				url : ctx + '/euiccCard/save',
				data : $('#euiccCardForm').serialize(),
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						toastr['success'](data.msg, "系统提示");
						$('#createModule').modal('hide');
						euiccCardUI.callback();
					} else {
						toastr['error'](data.msg, "系统提示");
					}
				},
				error : function(data) {
					toastr['error'](data.msg, "系统提示");
				}
			});
		});
		
		// 保存scp80
		$('#addScp80Module').click(function() {
			if (!scp80validator.form())
				return false;
			$.ajax({
				type : 'POST',
				url : ctx + '/euiccCard/saveScp80',
				data : $('#scp80Form').serialize(),
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						toastr['success'](data.msg, "系统提示");
						$('#createScp80Module').modal('hide');
						$('#createScp80TableModule').modal('show');
						scp80grid.getDataTable().ajax.reload();
						scp80grid.clearAjaxParams();
					} else {
						toastr['error'](data.msg, "系统提示");
					}
				},
				error : function(data) {
					toastr['error'](data.msg, "系统提示");
				}
			});
		});
		
		// 保存scp81
		$('#addScp81Module').click(function() {
			if (!scp81validator.form())
				return false;
			$.ajax({
				type : 'POST',
				url : ctx + '/euiccCard/saveScp81',
				data : $('#scp81Form').serialize(),
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						toastr['success'](data.msg, "系统提示");
						$('#createScp81Module').modal('hide');
						$('#createScp81TableModule').modal('show');
						scp81grid.getDataTable().ajax.reload();
						scp81grid.clearAjaxParams();
					} else {
						toastr['error'](data.msg, "系统提示");
					}
				},
				error : function(data) {
					toastr['error'](data.msg, "系统提示");
				}
			});
		});
		
		// 保存scp03
		$('#addScp03Module').click(function() {
			if (!scp03validator.form())
				return false;
			$.ajax({
				type : 'POST',
				url : ctx + '/euiccCard/saveScp03',
				data : $('#scp03Form').serialize(),
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						toastr['success'](data.msg, "系统提示");
						$('#createScp03Module').modal('hide');
						$('#createScp03TableModule').modal('show');
						scp03grid.getDataTable().ajax.reload();
						scp03grid.clearAjaxParams();
					} else {
						toastr['error'](data.msg, "系统提示");
					}
				},
				error : function(data) {
					toastr['error'](data.msg, "系统提示");
				}
			});
		});
		
		// 保存ISDP
		$('#addIsdpModule').click(function() {
			if (!isdpvalidator.form())
				return false;
			$.ajax({
				type : 'POST',
				url : ctx + '/euiccCard/saveIsdp',
				data : $('#isdpForm').serialize(),
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						toastr['success'](data.msg, "系统提示");
						$('#createIsdpModule').modal('hide');
						$('#createIsdpTableModule').modal('show');
						isdpgrid.getDataTable().ajax.reload();
						isdpgrid.clearAjaxParams();
					} else {
						toastr['error'](data.msg, "系统提示");
					}
				},
				error : function(data) {
					toastr['error'](data.msg, "系统提示");
				}
			});
		});
		
		// 关闭scp80编辑框
		$('#scp80Dissmissx').click(function() {
			$('#createScp80TableModule').modal('show');
		});
		$('#scp80Dissmiss').click(function() {
			$('#createScp80TableModule').modal('show');
		});
		
		// 关闭scp81编辑框
		$('#scp81Dissmissx').click(function() {
			$('#createScp81TableModule').modal('show');
		});
		$('#scp81Dissmiss').click(function() {
			$('#createScp81TableModule').modal('show');
		});
		
		// 关闭scp03编辑框
		$('#scp03Dissmissx').click(function() {
			$('#createScp03TableModule').modal('show');
		});
		$('#scp03Dissmiss').click(function() {
			$('#createScp03TableModule').modal('show');
		});
		
		// 关闭isdp编辑框
		$('#isdpDissmissx').click(function() {
			$('#createIsdpTableModule').modal('show');
		});
		$('#isdpDissmiss').click(function() {
			$('#createIsdpTableModule').modal('show');
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
		$("#euiccCardModalLabel").html("编辑");//请完善编辑内容
	} else if ("add" == type) {
		$("#tag").val("add");
		$("#euiccCardModalLabel").html("新建");//请完善新建内容
	}
};

function setScp80FormStatus(type) {
	// 清除验证css样式
	$('.form-group').removeClass('has-error').removeClass('has-success');
	$('.form-group i').removeClass().addClass('fa');
	if ("edit" == type) {
		$("#scp80Tag").val("update");
		$("#scp80ModalLabel").html("编辑");//请完善编辑内容
	} else if ("add" == type) {
		$("#scp80Tag").val("add");
		$("#scp80ModalLabel").html("新建");//请完善新建内容
	}
};

function setScp81FormStatus(type) {
	// 清除验证css样式
	$('.form-group').removeClass('has-error').removeClass('has-success');
	$('.form-group i').removeClass().addClass('fa');
	if ("edit" == type) {
		$("#scp81Tag").val("update");
		$("#scp81ModalLabel").html("编辑");//请完善编辑内容
	} else if ("add" == type) {
		$("#scp81Tag").val("add");
		$("#scp81ModalLabel").html("新建");//请完善新建内容
	}
};

function setScp03FormStatus(type) {
	// 清除验证css样式
	$('.form-group').removeClass('has-error').removeClass('has-success');
	$('.form-group i').removeClass().addClass('fa');
	if ("edit" == type) {
		$("#scp03Tag").val("update");
		$("#scp03ModalLabel").html("编辑");//请完善编辑内容
	} else if ("add" == type) {
		$("#scp03Tag").val("add");
		$("#scp03ModalLabel").html("新建");//请完善新建内容
	}
};

function setIsdpFormStatus(type) {
	// 清除验证css样式
	$('.form-group').removeClass('has-error').removeClass('has-success');
	$('.form-group i').removeClass().addClass('fa');
	if ("edit" == type) {
		$("#isdpTag").val("update");
		$("#isdpModalLabel").html("编辑");//请完善编辑内容
	} else if ("add" == type) {
		$("#isdpTag").val("add");
		$("#isdpModalLabel").html("新建");//请完善新建内容
	}
};

$("#resetForm").click(function() {
	$('#searchForm')[0].reset();
	euiccCardUI.callback();
});

function statusRender(data) {
	if (data == 1) {
		return "支持";
	} else {
		return "不支持";
	}
};

function isdpStatusRender(data) {
	if (data == 1) {
		return "启用";
	} else {
		return "关闭";
	}
};

function getPremission(data){
	var p = "<shiro:hasPermission name='euiccCard:edit'><a class='btn default edit btn-xs blue' name='"+data+"'><i class='fa fa-edit'></i>编辑 </a></shiro:hasPermission>";
	p += "<shiro:hasPermission name='euiccCard:delete'><a class='btn default delete btn-xs red' name='"+data+"'><i class='fa fa-trash-o'></i>删除</a></shiro:hasPermission>";
	p += "<shiro:hasPermission name='euiccCard:showScp80'><a class='btn default scp80 btn-xs blue' name='"+data+"'>显示SCP80</a></shiro:hasPermission>";
	p += "<shiro:hasPermission name='euiccCard:showScp81'><a class='btn default scp81 btn-xs blue' name='"+data+"'>显示SCP81</a></shiro:hasPermission>";
	p += "<shiro:hasPermission name='euiccCard:showScp03'><a class='btn default scp03 btn-xs blue' name='"+data+"'>显示SCP03</a></shiro:hasPermission>";
	p += "<shiro:hasPermission name='euiccCard:showIsdp'><a class='btn default isdp btn-xs blue' name='"+data+"'>显示ISDP</a></shiro:hasPermission>";
	return p;
}

function getScpPremission(data){
	var p = "<shiro:hasPermission name='euiccCard:edit'><a class='btn default edit btn-xs blue' name='"+data+"'><i class='fa fa-edit'></i>编辑 </a></shiro:hasPermission>";
	return p;
}
