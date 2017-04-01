(function(factory) {
	if (typeof define === "function" && define.amd) {
		define(["jquery", "./jquery.validate"], factory);
	} else {
		factory(jQuery);
	}
}(function($) {

	$.validator.addMethod("ipPort", function(value, element) {
		return this.optional(element)
				|| /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-4])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-4])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-4])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-4])\:[0-9]{2,5}$/
						.test($.trim( value ));
	}, "格式错误，标准格式为：IP:端口，例如127.0.0.1:8888且ip地址需要小于255");

	$.validator.addMethod("zipcode", function(value, element) {
				return this.optional(element) || /^[0-9\ -]+$/.test($.trim( value ));
			}, "请输入正确的 邮政编码");

	$.validator.addMethod("telephone", function(value, element) {
				return this.optional(element)
						|| /^1[3|4|5|8|][0-9]{9}$/.test(value);
			}, "请输入正确的手机号");

	$.validator.addMethod("aid", function(value, element) {
				return this.optional(element)
						|| /^[a-fA-F0-9\- ]+$/.test($.trim( value ));
			}, "请输入有效的AID(a-f,A-F或者0-9之间的字符)");
	
	$.validator.addMethod("parameter", function(value, element) {
				return this.optional(element)
						|| /^[a-fA-F0-9\- ]+$/.test($.trim( value ));
			}, "请输入有效的安装参数(a-f,A-F或者0-9之间的字符)");
	
	$.validator.addMethod("sdSpace", function(value, element) {
				return this.optional(element)
						|| /^[1-9][0-9]{0,7}$/.test($.trim( value ));
			}, "请输入1到99999999之间的数字");

	$.validator.addMethod("TwoNo", function(value, element) {
				return this.optional(element) || /^\d{2}$/.test($.trim( value ));
			}, "请输入2位数字");

	$.validator.addMethod("rsaAlg", function(value, element) {
				return this.optional(element) || /^[1-5]{1}$/.test($.trim( value ));
			}, "请输入1-5之间的数值");

	$.validator.addMethod("userAccount", function(value, element) {
				return this.optional(element)
						|| /^[a-zA-Z0-9]{5,16}$/.test($.trim( value ));
			}, "请输入有效的用户账号（5-16位数字或字母）");
	
	$.validator.addMethod("moduleAid", function(value, element) {
		return this.optional(element)
				|| /^[a-fA-F0-9\-]{0,32}$/.test($.trim( value ));
		///^[a-fA-F0-9\- ]+$/.test(value)
	}, "请输入有效的模块Aid（a-f,A-F或者0-9之间的字符）");
	
	$.validator.addMethod("userPass", function(value, element) {
				return this.optional(element)
						|| /^[a-zA-Z0-9|!|@|#|$|%|^|&|*|.|_]{6,64}$/
								.test(value);
			}, "密码长度至少为6位,只能包含数字字母和特殊字符(!@#$%^&*.)");

	$.validator.addMethod("idCode", function(value, element) {
				return this.optional(element)
						|| /^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/.test($.trim( value ));
			}, "请输入正确的身份证号");

	$.validator.addMethod("userOF", function(value, element) {
				return this.optional(element)
						|| /^0\d{2}-\d{8}|0\d{2}-\d{7}|0\d{3}-\d{7}|0\d{3}-\d{8}$/
								.test(value);
			}, "请输入正确的办公电话");
			
	$.validator.addMethod("numberAndABC", function(value, element) {
				return this.optional(element)
						|| /^[a-fA-F0-9]+$/
								.test($.trim( value ));
			}, "只能输入a-f，A-F或者0-9之间的字符");

	$.validator.addMethod("orgCode", function(value, element) {
				return this.optional(element)
						|| /^[0-9a-fA-F]{10}$/.test(value);
			}, "只能输入10位16进制代码");

	$.validator.addMethod("ip", function(value, element) {
		return this.optional(element)
				|| /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-4])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-4])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-4])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-4])$/
						.test($.trim( value ));
	}, "格式错误，标准格式为：127.0.0.1且ip地址需要小于255");

	$.validator.addMethod("port", function(value, element) {
		return this.optional(element)
				|| /^([0-9]|[1-9]\d|[1-9]\d{2}|[1-9]\d{3}|[1-5]\d{4}|6[0-4]\d{3}|65[0-4]\d{2}|655[0-2]\d|6553[0-5])$/
						.test($.trim( value ));
	}, "端口请输入0到65535的整数");
	$.validator.addMethod("filetype", function(value, element, param) {
				var fileType = value.substring(value.lastIndexOf(".") + 1)
						.toLowerCase();
				return this.optional(element)
						|| $.inArray(fileType, param) != -1;
			}, $.validator.format("invalid file type"));

	$.validator.addMethod("productName", function(value, element) {
		var tag=$("#tag").val();
		var productId=$("#productIdString").val();
		var flag = 1;  
      $.ajax({  
          type:"POST",  
          url:ctx + '/productInfo/checkProductName',  
          async:false,                                             //同步方法，如果用异步的话，flag永远为1  
          data:{'tag':tag,
          'productName':$.trim( value ),
          		'productId':productId
          		
          }, 
          dataType : 'json',
          success: function(data){  
               if(!data.success){  
                   flag = 0;  
               } else{
               	  flag=1;
               }
          }  
      });  
      if(flag == 0){  
          return false;  
      }else{  
          return true;  
      }  
	}, "产品名称不能重复");
}));
