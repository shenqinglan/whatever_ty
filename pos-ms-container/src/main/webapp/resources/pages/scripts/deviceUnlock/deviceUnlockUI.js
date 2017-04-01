// 页面信息
var deviceUnlockUI = function() {
	

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
			Connect();
			GetSN();
			GetHardId();
			getRootKey();
		}
	};
}();

function getRootKey(){
	var fieldId = $("#sn").val();
	$.ajax({
		type : 'POST',
		url : ctx + '/deviceUnlock/find',
		data :  {
			sn : fieldId
		},
		dataType : 'json',
		success : function(data) {
			$.each(data,function(name,val) {
				$('#searchForm').find("[name='"+ name+ "']").val(val);
			});
		},
		error : function(data) {
			toastr['error'](data.msg, "系统提示");
		}
	});
}

$("#reset").click(function() {
	deviceUnlockUI.init();
});

$("#unlock").click(function() {
	var fieldId = $("#sn").val();
	$.ajax({
		type : 'POST',
		url : ctx + '/deviceUnlock/check',
		data : {sn : fieldId},
		dataType : 'json',
		success : function(data) {
			if (data.success) {
				UnLock();
			} else {
				toastr['error'](data.msg, "系统提示");
			}
		},
		error : function(data) {
			toastr['error'](data.msg, "系统提示");
		}
	});
	deviceUnlockUI.init();
});
//连接
function Connect()
{
    try {
        var vOut = aaaa.Connect();
        if (!vOut) {
        	document.getElementById("msg").style.display = "block";
        	document.getElementById("div_reset").style.display = "block";
        	document.getElementById("div_unlock").style.display = "none";
        } else {
        	document.getElementById("msg").style.display = "none";
        	document.getElementById("div_reset").style.display = "none";
        	document.getElementById("div_unlock").style.display = "block";
        }
    }
    catch (e)
    {
	    alert("错误: " + e );
    }  
    return false;
}

//关闭连接
function Close()
{
    alert("Close");
    try
    {
        var vOut=aaaa.disconnect();
        alert("result:"+vOut);
    }
    catch(e)
    {
        alert("错误："+e);
    }
    return false
}

//获取设备SN号
function GetSN()
{
    try
    {
        var vOut=aaaa.GetSN();
        $("#sn").val(vOut);
    }
    catch(e)
    {
        alert("错误："+e);
    }
    return false;
}

//获取设备硬件ID
function GetHardId() 
{
    try
    {
        var vOut=aaaa.GetHardId();
        $("#hardwareId").val(vOut);
    }
    catch(e)
    {
        alert("错误："+e);
    }
    return false;
}

//设备解锁
function UnLock()
{
    try
    {
    	var rootKey = $("#rootKey").val();
    	if (rootKey == ""){
    		toastr['error']("获取根密钥异常！", "系统提示");
    		return;
    	}
        var vOut = aaaa.UnLock(rootKey);
        if (vOut) {
        	$.ajax({
				type : 'POST',
				url : ctx + '/deviceUnlock/unlock',
				data : $('#deviceUnlockForm').serialize(),
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						toastr['success'](data.msg, "系统提示");
					} else {
						toastr['error'](data.msg, "系统提示");
					}
				},
				error : function(data) {
					toastr['error'](data.msg, "系统提示");
				}
			});
        } else {
        	toastr['error']("解锁失败！", "系统提示");
        }
    }
    catch(e)
    {
        alert("错误："+e);
    }
}