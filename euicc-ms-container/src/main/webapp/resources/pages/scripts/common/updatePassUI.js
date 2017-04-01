// jquey validate验证
var updatePassFormValidation = function() {
	// validation using icons
	var handleValidation2 = function() {
		// for more info visit the official plugin documentation:
		// http://docs.jquery.com/Plugins/Validation
		var form2 = $('#updatePassForm');
		var error2 = $('.alert-danger', form2);
		var success2 = $('.alert-success', form2);

		return form2.validate({
			errorElement : 'span', // default input error message container
			errorClass : 'help-block help-block-error', // default input error
														// message class
			focusInvalid : false, // do not focus the last invalid input
			ignore : "", // validate all fields including form hidden input
			rules : {
				oldPass : {
					required : true
				},
				newPass:{
					minlength : 6,
					maxlength : 16,
					userPass: true,
					required : true
				},
				confirmPass:{
					equalTo:"#newPass",
					required : true
				}
			},

			  messages: {
	                oldPass: {
	                    required: "请输入原密码."
	                },
	                password: {
	                    required: "请填入新密码"
	                },
	                confirmPass: {
	                	required: "确定密码不能为空",
	                    equalTo: "密码两次输入不一致"
	                }
	            },
	            
			invalidHandler : function(event, validator) { // display error
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
					'container' : '#updatePassForm'
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
