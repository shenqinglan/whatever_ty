// 页面信息
var deviceApplyUI = function() {
	

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
			initData();
		}
	};
}();

//申请
$("#apply").click(function() {
	Metronic.handleFixInputPlaceholderForIE();
	$('#deviceApplyForm')[0].reset();
	$('#createModule').modal('show');
});

//提交
$("#submit").click(function() {
	var value = $("#approvalAmount").val();
	if (value == "") {
		toastr['error']("解锁次数不能为空！", "系统提示");
		return;
	}
	$.ajax({
		type : 'POST',
		url : ctx + '/deviceApply/apply',
		data : $('#deviceApplyForm').serialize(),
		dataType : 'json',
		success : function(data) {
			if (data.success) {
				toastr['success'](data.msg, "系统提示");
				$('#createModule').modal('hide');
				deviceApplyUI.init();
			} else {
				toastr['error'](data.msg, "系统提示");
			}
		},
		error : function(data) {
			toastr['error'](data.msg, "系统提示");
		}
	});
});

function initData() {
	$.ajax({
		type : 'POST',
		url : ctx + '/deviceApply/find',
		data : $('#deviceApplyListForm').serialize(),
		dataType : 'json',
		success : function(data) {
			$.each(data,function(name,val) {
				$('#deviceApplyListModule').find("[name='"+ name+ "']").val(val);
			});
			var status = $("#approvalState").val();
			//setStatus(status);
		},
		error : function(data) {
			toastr['error'](data.msg, "系统提示");
		}
	});
};

function setStatus(data) {
	if (data == 1) {
		$("#approvalState").val("已申请");
	} else {
		$("#approvalState").val("未申请");
	}
};
