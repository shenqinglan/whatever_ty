$(document).ready(function(e) {
	var counter = 0;
	if (window.history && window.history.pushState) {
		$(window).on('popstate', function () {
			window.history.pushState('forward', '设备管理系统', '#');
			window.history.forward(1);
			//alert("click " + (++counter) + " backspace");
		});
	}else{
		if ((navigator.userAgent.indexOf('MSIE') >= 0) && (navigator.userAgent.indexOf('Opera') < 0)){ // IE
			var Historty = window.History;
			Historty.pushState('forward', '设备管理系统', '#');
			window.history.forward(1);
	    }
	}
	//use in IE browser
//    $(document).keydown(function (e) {
//        var doPrevent;
//        // for IE && Firefox
//        var varkey = (e.keyCode) || (e.which) || (e.charCode);
//        if (varkey == 8) {
//            var d = e.srcElement || e.target;
//            if (d.tagName.toUpperCase() == 'INPUT' || d.tagName.toUpperCase() == 'TEXTAREA') {
//                doPrevent = d.readOnly || d.disabled;
//                // for button,radio and checkbox
//                if (d.type.toUpperCase() == 'SUBMIT'
//                    || d.type.toUpperCase() == 'RADIO'
//                    || d.type.toUpperCase() == 'CHECKBOX'
//                    || d.type.toUpperCase() == 'BUTTON') {
//                    doPrevent = true;
//                }
//            }
//            else {
//                doPrevent = true;
//            }
//        }
//        else {
//            doPrevent = false;
//        }
//    if (doPrevent)
//
//        e.preventDefault();
//    });

});