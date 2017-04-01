jQuery(document).ready(function() {
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
						// bootbox.confirm("是否删除该角色信息？", function(result) {
						// if(!result){
						// return false;
						// }
						// });
						if (!confirm("是否删除该角色信息？")) {
							return false;
						}
						var roleId = $(this).attr("name");
						$.ajax({
									type : "POST",
									url : ctx + "/baseRoles/delete",
									data : {
										roleId : roleId
									},
									dataType : "json",
									success : function(data) {
										if (data.success){
											toastr['success']("删除成功", "系统提示");
											TableAjax.callback();
										} else{
											toastr['error'](data.msg,
													"系统提示");
										}
									}
								});
					});

					$(".update").click(function() {
						clearAddTable("update");
						var roleId = $(this).attr("name");
						$.ajax({
							type : "POST",
							url : ctx + "/baseRoles/view",
							data : {
								roleId : roleId
							},
							dataType : "json",
							success : function(data) {
								$.each(data, function(name, val) {
												$('#roleForm').find("[name='"
														+ name + "']").val(val);
											});
									Metronic.handleFixInputPlaceholderForIE();
									$('#createRole').modal('show');
							}
						});
					});

					$(".addRole").click(function() {
						var roleId = $(this).attr("name");
						var roleName = $(this).parent("td").parent("tr")
								.find("td:eq(0)").text();
						var zNodes = [];
						$.ajax({
									type : "POST",
									url : ctx + "/baseRoles/getZtreeData",
									data : {
										roleId : roleId
									},
									dataType : "json",
									success : function(data) {
										zNodes = data;
									},
									complete : function() {
										zTree.setZtreeNode(zNodes);
										zTree.init();
										$("#roleId").val(roleId);
										$("#title").html("角色授权：" + roleName);
										$("#moduleRole").modal('show');
									}
								});

					});
				},
				loadingMessage : 'Loading...',
				dataTable : {

					"bStateSave" : true, // save datatable
					// state(pagination, sort, etc)
					// in cookie.

					"lengthMenu" : [[10, 20, 30], [10, 20, 30] // change per
					// page values
					// here
					],
					"pageLength" : 10, // default record count per page
					"ajax" : {
						"url" : ctx + "/baseRoles/find"// ajax source
					},
					"formSearch" : "roleSearchForm",
					"columnDefs" : [{
								'name' : 'ROLE_NAME',
								'orderable' : true,
								'targets' : [0]
							}, {
								'orderable' : false,
								'targets' : [1]
							}, {
								'orderable' : false,
								'targets' : [2]
							}],
					"columns" : [{
								"title" : "角色名称",
								"field" : "roleName"
							}, {
								"title" : "角色描述",
								"field" : "roleDesc"
							}, {
								"title" : "操作",
								"field" : "roleId",
								'fieldRender' : "getPremission"
						}],
					"order" : [[0, "asc"]]
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

	TableAjax.init();

	$("#reset").click(function() {
				$("#roleNameSearch").val("");
			});

	$('#create').click(function() {
				clearAddTable("add");
				$('#createRole').modal('show');
			});

	$("#addMoudleRole").click(function() {
		var treeObj = $.fn.zTree.getZTreeObj("menuTree"), nodes = treeObj
				.getCheckedNodes(true);
		var moduleIds = "";
		for (var i = 0; i < nodes.length; i++) {
			moduleIds = moduleIds + nodes[i].id + ",";
		}
		$.ajax({
					type : "POST",
					url : ctx + "/baseRoles/saveRoleMoudle",
					data : {
						moduleId : moduleIds,
						roleId : $("#roleId").val()
					},
					dataType : "json",
					success : function(data) {
						if (data[0] > 0) {
							$('#moduleRole').modal('hide');
							toastr['success']("授权成功", "系统提示");
						} else {
							toastr['error']("授权失败", "系统提示");
						}
					}
				});

	});

	$('#addRole').click(function() {
				if (!validator.form())
					return false;
				$.ajax({
							type : "POST",
							url : ctx + "/baseRoles/save",
							data : {
								roleId : $("#roleEdit").val(),
								roleName : $("#roleNameEdit").val(),
								roleDesc : $("#roleDescEdit").val(),
								tag : $("#tag").val()
							},
							dataType : "json",
							success : function(data) {
								if (data[0] > 0) {
									$('#createRole').modal('hide');
									toastr['success'](data[1], "系统提示");
									TableAjax.callback();
								} else {
									toastr['error'](data[1], "系统提示");
								}
							}
						});
			});

	function clearAddTable(type) {
		// 清除验证css样式
		$('.form-group').removeClass('has-error').removeClass('has-success');
		$('.form-group i').removeClass().addClass('fa');
		if ("update" == type) {
			$("#myModalLabel").html("编辑角色");
			$("#tag").val("update");
		}
		if ("add" == type) {
			$("#myModalLabel").html("新建角色");
			$("#tag").val("add");
		}
		
		$("#roleForm")[0].reset();
	}

	// jquey validate验证
	var FormValidation = function() {
		// validation using icons
		var handleValidation2 = function() {
			// http://docs.jquery.com/Plugins/Validation
			var form2 = $('#roleForm');
			var error2 = $('.alert-danger', form2);
			var success2 = $('.alert-success', form2);

			return form2.validate({
						errorElement : 'span', // default input error message
						// container
						errorClass : 'help-block help-block-error', // default
						// input
						// error
						// message class
						focusInvalid : false, // do not focus the last invalid
						// input
						ignore : "", // validate all fields including form
						// hidden input
						rules : {
							roleName : {
								maxlength : 32,
								required : true
							},
							roleDesc : {
								maxlength : 64
							}
						},
						messages:{
						roleName:{required:"请输入角色名称",maxlength:$.validator.format('角色名称不能超过{0}个字符')}
						,roleDesc:{maxlength:$.validator.format('角色描述不能超过{0}个字符')}
						},

						invalidHandler : function(event, validator) { // display
							// error
							// alert on form
							// submit
							success2.hide();
							error2.show();
							Metronic.scrollTo(error2, -200);
						},

						errorPlacement : function(error, element) { // render
							// error
							// placement for each
							// input type
							var icon = Metronic.handValidStyle(element);
							icon.removeClass('fa-check').addClass("fa-warning");
							icon.attr("data-original-title", error.text())
									.tooltip({
												'container' : '#roleForm'
											});
						},

						highlight : function(element) { // hightlight error
							// inputs
							$(element).closest('.form-group')
									.removeClass("has-success")
									.addClass('has-error'); // set error class
							// to the
							// control group
						},

						unhighlight : function(element) { // revert the change
							// done by
							// hightlight

						},

						success : function(label, element) {
							var icon = Metronic.handValidStyle(element);
							$(element).closest('.form-group')
									.removeClass('has-error')
									.addClass('has-success'); // set success
							// class to the
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
});