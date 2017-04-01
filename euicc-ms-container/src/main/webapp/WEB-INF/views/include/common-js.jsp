<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!--[if lt IE 9]>
	<script src="${ctxResources}/global/plugins/respond.min.js"></script>
	<script src="${ctxResources}/global/plugins/excanvas.min.js"></script> 
<![endif]-->
<script src="${ctxResources}/global/scripts/metronic.js"
	type="text/javascript"></script>
<script src="${ctxResources}/layout/scripts/layout.js"
	type="text/javascript"></script>
<script src="${ctxResources}/layout/scripts/quick-sidebar.js"
	type="text/javascript"></script>
<script src="${ctxResources}/layout/scripts/layoutControl.js"
	type="text/javascript"></script>
<script src="${ctxResources}/pages/scripts/common/updatePassUI.js"
	type="text/javascript"></script>
<script src="${ctxResources}/pages/scripts/common/common.js"></script>
<script>
String.prototype.replaceAll = function(s1,s2){ 
	 return this.replace(new RegExp(s1,"gm"),s2); 
}

function cutString(val){
	var valNewV=val.replaceAll("\u0020","&nbsp;");
	 if(val==null||val.length>=20){
	 	return "<div title="+valNewV+" style='text-decoration:none;cursor:hand;'>"+val.substr(0,20)+"......"+"</div>";
	 }else{
	 	return val;
	 }
}


	$.ajaxSetup({
		global : false,
		type : "POST",
		complete : function(XMLHttpRequest, textStatus) {
			var data = XMLHttpRequest.responseText;
			if (data == "timeout") {
				alert("\u767B\u5F55\u8D85\u65F6");
				window.location.href=ctx+"/";
			}
		}
	});
	var updatePassValidator;
	
	$(function(){
		updatePassValidator=updatePassFormValidation.init();
	});
	
	function updatePass(){
		$("#oldPass").val("");
		$("#newPass").val("");
		$("#confirmPass").val("");
		$('.form-group').removeClass('has-error').removeClass('has-success');
		$('.form-group i').removeClass().addClass('fa');
		Metronic.handleFixInputPlaceholderForIE();
		$("#updatePassModal").modal("show");
	}
	
	function savePass(){
		if (!updatePassValidator.form())
			return false;
		
		var oldPass=$("#oldPass").val();
		var newPass=$("#newPass").val();
		$.ajax({
			type : "POST",
			url : ctx + "/baseUsers/updatePass",
			data : {
				oldPass : hex_md5(hex_md5(oldPass).toUpperCase()).toUpperCase(),
				newPass :  hex_md5(hex_md5(newPass).toUpperCase()).toUpperCase()
			},
			dataType : "json",
			success : function(data) {
				if (data.success) {
					$('#updatePassModal').modal('hide');
					alert("修改密码成功，请重新登录系统");
					window.location.href=ctx+"/";
				} else {
					toastr['error'](data.msg, "系统提示");
				}
			}
		});
	}
	
	
	 function hiddernmain(){
    	var span1=$('#span1');
    	var span2=$('#span2');
    	if(span1.css("visibility")=="visible")
		{
    		//alert("visible");
    		span1.css("visibility","hidden");          /* 设置主菜单隐藏 */
    		//span1.css("z-index","-1000");
    		//span1.css("display","none");          /* 设置主菜单隐藏 */
    		span1.html("x");
		}
    	else if(span1.css("visibility")=="hidden")
    	{
    		//alert("hidden");
    		span1.css("visibility","visible");        /*  设置主菜单显示  */
    		
    		//span1.css("z-index","1000");
     		span1.html("主菜单");
    	} 
    }  
</script>