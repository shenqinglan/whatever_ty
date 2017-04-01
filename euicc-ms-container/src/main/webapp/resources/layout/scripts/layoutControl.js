/**
Demo script to handle the theme demo
**/
var LayoutControl = function() {

    // Handle Theme Settings
    var handleTheme = this.handleTheme = function() {

        var panel = $('.theme-panel');

        if ($('body').hasClass('page-boxed') === false) {
            $('.layout-option', panel).val("fluid");
        }

        $('.sidebar-option', panel).val("default");
        $('.page-header-option', panel).val("fixed");
        $('.page-footer-option', panel).val("default");
        if ($('.sidebar-pos-option').attr("disabled") === false) {
            $('.sidebar-pos-option', panel).val(Metronic.isRTL() ? 'right' : 'left');
        }

        //handle theme layout
        var resetLayout = function() {
            $("body").
            removeClass("page-boxed").
            removeClass("page-footer-fixed").
            removeClass("page-sidebar-fixed").
            removeClass("page-header-fixed").
            removeClass("page-sidebar-reversed");

            $('.page-header > .page-header-inner').removeClass("container");

            if ($('.page-container').parent(".container").size() === 1) {
                $('.page-container').insertAfter('body > .clearfix');
            }

            if ($('.page-footer > .container').size() === 1) {
                $('.page-footer').html($('.page-footer > .container').html());
            } else if ($('.page-footer').parent(".container").size() === 1) {
                $('.page-footer').insertAfter('.page-container');
                $('.scroll-to-top').insertAfter('.page-footer');
            }

             $(".top-menu > .navbar-nav > li.dropdown").removeClass("dropdown-dark");

            $('body > .container').remove();
        };

        var lastSelectedLayout = '';

        var setLayout = function() {

            var layoutOption = $('.layout-option', panel).val();
            var sidebarOption = $('.sidebar-option', panel).val();
            var headerOption = $('.page-header-option', panel).val();
            var footerOption = $('.page-footer-option', panel).val();
            var sidebarPosOption = $('.sidebar-pos-option', panel).val();
            var sidebarStyleOption = $('.sidebar-style-option', panel).val();
            var sidebarMenuOption = $('.sidebar-menu-option', panel).val();
            var headerTopDropdownStyle = $('.page-header-top-dropdown-style-option', panel).val();

            if (sidebarOption == "fixed" && headerOption == "default") {
                alert('不支持在滚动的页眉设置下固定侧边栏，请固定页眉才可以设置侧边栏');
                $('.page-header-option', panel).val("fixed");
                $('.sidebar-option', panel).val("fixed");
                sidebarOption = 'fixed';
                headerOption = 'fixed';
            }

            resetLayout(); // reset layout to default state

            if (layoutOption === "boxed") {
                $("body").addClass("page-boxed");

                // set header
                $('.page-header > .page-header-inner').addClass("container");
                var cont = $('body > .clearfix').after('<div class="container"></div>');

                // set content
                $('.page-container').appendTo('body > .container');

                // set footer
                if (footerOption === 'fixed') {
                    $('.page-footer').html('<div class="container">' + $('.page-footer').html() + '</div>');
                } else {
                    $('.page-footer').appendTo('body > .container');
                }
            }

            if (lastSelectedLayout != layoutOption) {
                //layout changed, run responsive handler:
                Metronic.runResizeHandlers();
            }
            lastSelectedLayout = layoutOption;

            //header
            if (headerOption === 'fixed') {
                $("body").addClass("page-header-fixed");
                $(".page-header").removeClass("navbar-static-top").addClass("navbar-fixed-top");
            } else {
                $("body").removeClass("page-header-fixed");
                $(".page-header").removeClass("navbar-fixed-top").addClass("navbar-static-top");
            }

            //sidebar
            if ($('body').hasClass('page-full-width') === false) {
                if (sidebarOption === 'fixed') {
                    $("body").addClass("page-sidebar-fixed");
                    $("page-sidebar-menu").addClass("page-sidebar-menu-fixed");
                    $("page-sidebar-menu").removeClass("page-sidebar-menu-default");
                    Layout.initFixedSidebarHoverEffect();
                } else {
                    $("body").removeClass("page-sidebar-fixed");
                    $("page-sidebar-menu").addClass("page-sidebar-menu-default");
                    $("page-sidebar-menu").removeClass("page-sidebar-menu-fixed");
                    $('.page-sidebar-menu').unbind('mouseenter').unbind('mouseleave');
                }
            }

            // top dropdown style
            if (headerTopDropdownStyle === 'dark') {
                $(".top-menu > .navbar-nav > li.dropdown").addClass("dropdown-dark");
            } else {
                $(".top-menu > .navbar-nav > li.dropdown").removeClass("dropdown-dark");
            }

            //footer
            if (footerOption === 'fixed') {
                $("body").addClass("page-footer-fixed");
            } else {
                $("body").removeClass("page-footer-fixed");
            }

            //sidebar style
            if (sidebarStyleOption === 'light') {
                $(".page-sidebar-menu").addClass("page-sidebar-menu-light");
            } else {
                $(".page-sidebar-menu").removeClass("page-sidebar-menu-light");
            }

            //sidebar menu
            if (sidebarMenuOption === 'hover') {
                if (sidebarOption == 'fixed') {
                    $('.sidebar-menu-option', panel).val("accordion");
                    alert("Hover Sidebar Menu is not compatible with Fixed Sidebar Mode. Select Default Sidebar Mode Instead.");
                } else {
                    $(".page-sidebar-menu").addClass("page-sidebar-menu-hover-submenu");
                }
            } else {
                $(".page-sidebar-menu").removeClass("page-sidebar-menu-hover-submenu");
            }

            //sidebar position
            if (Metronic.isRTL()) {
                if (sidebarPosOption === 'left') {
                    $("body").addClass("page-sidebar-reversed");
                    $('#frontend-link').tooltip('destroy').tooltip({
                        placement: 'right'
                    });
                } else {
                    $("body").removeClass("page-sidebar-reversed");
                    $('#frontend-link').tooltip('destroy').tooltip({
                        placement: 'left'
                    });
                }
            } else {
                if (sidebarPosOption === 'right') {
                    $("body").addClass("page-sidebar-reversed");
                    $('#frontend-link').tooltip('destroy').tooltip({
                        placement: 'left'
                    });
                } else {
                    $("body").removeClass("page-sidebar-reversed");
                    $('#frontend-link').tooltip('destroy').tooltip({
                        placement: 'right'
                    });
                }
            }

            Layout.fixContentHeight(); // fix content height
            Layout.initFixedSidebar(); // reinitialize fixed sidebar
        };

        // handle theme colors
        var setColor = this.setColor = function(color) {
            var color_ = (Metronic.isRTL() ? color + '-rtl' : color);
            $('#style_color').attr("href", Layout.getLayoutCssPath() + 'themes/' + color_ + ".css");
            if (color == 'light2') {
                $('.page-logo img').attr('src', Layout.getLayoutImgPath() + 'logo-invert.png');
            } else {
                $('.page-logo img').attr('src', Layout.getLayoutImgPath() + 'logo.png');
            }
            $.cookie('layout-style-option',color, { expires: 1000*60*60*24*30, path: '/' });
        };

        $('.toggler', panel).click(function() {
            $('.toggler').hide();
            $('.toggler-close').show();
            $('.theme-panel > .theme-options').show();
        });

        $('.toggler-close', panel).click(function() {
            $('.toggler').show();
            $('.toggler-close').hide();
            $('.theme-panel > .theme-options').hide();
        });

        $('.theme-colors > ul > li', panel).click(function() {
            var color = $(this).attr("data-style");
            setColor(color);
            $('ul > li', panel).removeClass("current");
            $(this).addClass("current");
        });

        // set default theme options:

        if ($("body").hasClass("page-boxed")) {
            $('.layout-option', panel).val("boxed");
        }

        if ($("body").hasClass("page-sidebar-fixed")) {
            $('.sidebar-option', panel).val("fixed");
        }

        if ($("body").hasClass("page-header-fixed")) {
            $('.page-header-option', panel).val("fixed");
        }

        if ($("body").hasClass("page-footer-fixed")) {
            $('.page-footer-option', panel).val("fixed");
        }

        if ($("body").hasClass("page-sidebar-reversed")) {
            $('.sidebar-pos-option', panel).val("right");
        }

        if ($(".page-sidebar-menu").hasClass("page-sidebar-menu-light")) {
            $('.sidebar-style-option', panel).val("light");
        }

        if ($(".page-sidebar-menu").hasClass("page-sidebar-menu-hover-submenu")) {
            $('.sidebar-menu-option', panel).val("hover");
        }

        var sidebarOption = $('.sidebar-option', panel).val();
        var headerOption = $('.page-header-option', panel).val();
        var footerOption = $('.page-footer-option', panel).val();
        var sidebarPosOption = $('.sidebar-pos-option', panel).val();
        var sidebarStyleOption = $('.sidebar-style-option', panel).val();
        var sidebarMenuOption = $('.sidebar-menu-option', panel).val();

        $('.layout-option, .page-header-option, .page-header-top-dropdown-style-option, .sidebar-option, .page-footer-option, .sidebar-pos-option, .sidebar-style-option, .sidebar-menu-option', panel).change(setLayout);
    };

    // handle theme style
    var setThemeStyle = function(style) {
        var file = (style === 'rounded' ? 'components-rounded' : 'components');
        file = (Metronic.isRTL() ? file + '-rtl' : file);
        $('#style_components').attr("href", Metronic.getGlobalCssPath() + file + ".css");
        if ($.cookie) {
            //$.cookie('layout-style-option', style);
        	$.cookie('layout-style-option',style, { expires: 1000*60*60*24*30, path: '/' });
        }
    };

    // Handle sidebar menu links
    var handleSidebarMenuActiveLink = function(mode, el) {
    	alert(mode + "   "  + el);
        var url = location.hash.toLowerCase();

        var menu = $('.page-sidebar-menu');

        if (mode === 'click' || mode === 'set') {
            el = $(el);
        } else if (mode === 'match') {
            menu.find("li > a").each(function() {
                var path = $(this).attr("href").toLowerCase();
                // url match condition
                if (path.length > 1 && url.substr(1, path.length - 1) == path.substr(1)) {
                    el = $(this);
                    return;
                }
            });
        }

        if (!el || el.size() == 0) {
            return;
        }

        if (el.attr('href').toLowerCase() === 'javascript:;' || el.attr('href').toLowerCase() === '#') {
            return;
        }

        var slideSpeed = parseInt(menu.data("slide-speed"));
        var keepExpand = menu.data("keep-expanded");

        // disable active states
        menu.find('li.active').removeClass('active');
        menu.find('li > a > .selected').remove();

        if (menu.hasClass('page-sidebar-menu-hover-submenu') === false) {
            menu.find('li.open').each(function(){
                if ($(this).children('.sub-menu').size() === 0) {
                    $(this).removeClass('open');
                    $(this).find('> a > .arrow.open').removeClass('open');
                }
            });
        } else {
             menu.find('li.open').removeClass('open');
        }

        el.parents('li').each(function () {
            $(this).addClass('active');
            $(this).find('> a > span.arrow').addClass('open');

            if ($(this).parent('ul.page-sidebar-menu').size() === 1) {
                $(this).find('> a').append('<span class="selected"></span>');
            }

            if ($(this).children('ul.sub-menu').size() === 1) {
                $(this).addClass('open');
            }
        });

        if (mode === 'click') {
            if (Metronic.getViewPort().width < 992 && $('.page-sidebar').hasClass("in")) { // close the menu on mobile view while laoding a page
                $('.page-header .responsive-toggler').click();
            }
        }
    };

    var initMessage = this.initMessage = function () {
    	$.ajax( {
            type : "get",
            url : "/boots-web/ajaxMessage",
            dataType:"json",
            success : function(msg) {
//                alert("msg: " + msg.length);
//                alert(msg[0].msgId);
               $("#header_inbox_bar").find(".badge").html(msg.length);
               $("#header_inbox_bar > .dropdown-menu").find("li:first > p").html("您有"+msg.length+"条新的消息");
//               $("#header_inbox_bar > .dropdown-menu").find(".dropdown-menu-list").html("");
               var inboxHtml ='';
               for(var i=0 ;i < msg.length; i++){
            	   inboxHtml += '<li>';
            	   inboxHtml += '<a href="/comn-boot/inboxView?msgId='+msg[i].msgId+'">';
            	   inboxHtml += '<span class="photo">';
            	   inboxHtml += ' <img src="../../resources/layout/img/avatar2.jpg" class="img-circle" alt="">';
            	   inboxHtml += '</span>';
            	   inboxHtml += '<span class="subject">';
            	   inboxHtml += '<span class="from">';
            	   inboxHtml +=  msg[i].from;
            	   inboxHtml += '</span>';
            	   inboxHtml += '<span class="time">';
            	   inboxHtml += msg[i].createTime;
            	   inboxHtml += '</span>';
            	   inboxHtml += '<span class="message">';
            	   inboxHtml += msg[i].msgContent;
            	   inboxHtml += '</span>';
            	   inboxHtml += '</a>';
            	   inboxHtml += '</li>';
               }

               $("#header_inbox_bar > .dropdown-menu").find(".dropdown-menu-list").html(inboxHtml);
            }
        });
    }

    return {

        //main function to initiate the theme
        init: function() {
            // handles style customer tool
           var thems  = handleTheme();
	       this.setColor = function(color) {
	            var color_ = (Metronic.isRTL() ? color + '-rtl' : color);
	            $('#style_color').attr("href", Layout.getLayoutCssPath() + 'themes/' + color_ + ".css");
	            if (color == 'light2') {
	                $('.page-logo img').attr('src', Layout.getLayoutImgPath() + 'logo-invert.png');
	            } else {
	                $('.page-logo img').attr('src', Layout.getLayoutImgPath() + 'logo.png');
	            }

	        };
            // handle layout style change
            $('.theme-panel .layout-style-option').change(function() {
                 setThemeStyle($(this).val());
            });

            // set layout style from cookie
            if ($.cookie && $.cookie('layout-style-option') === 'rounded') {
                setThemeStyle($.cookie('layout-style-option'));
                $('.theme-panel .layout-style-option').val($.cookie('layout-style-option'));
            }
            if(!$.cookie('layout-style-option')){
	        	$.cookie('layout-style-option',"light2",{expires:1000*60*60*24*30, path: '/' })
	    	}
    		var theme = $.cookie('layout-style-option');
        	this.setColor(theme);
        	//initMessage();
        },
        activeLink : function (mode,el){
            var url = location.hash.toLowerCase();

            var menu = $('.page-sidebar-menu');

            if (mode === 'click' || mode === 'set') {
                el = $('#'+el + ' > a');
            } else if (mode === 'match') {
                menu.find("li > a").each(function() {
                    var path = $(this).attr("href").toLowerCase();
                    // url match condition
                    if (path.length > 1 && url.substr(1, path.length - 1) == path.substr(1)) {
                        el = $(this);
                        return;
                    }
                });
            }

            if (!el || el.size() == 0) {
                return;
            }

            if (el.attr('href').toLowerCase() === 'javascript:;' || el.attr('href').toLowerCase() === '#') {
                return;
            }

            var slideSpeed = parseInt(menu.data("slide-speed"));
            var keepExpand = menu.data("keep-expanded");

            // disable active states
            menu.find('li.active').removeClass('active');
            menu.find('li > a > .selected').remove();

            if (menu.hasClass('page-sidebar-menu-hover-submenu') === false) {
                menu.find('li.open').each(function(){
                    if ($(this).children('.sub-menu').size() === 0) {
                        $(this).removeClass('open');
                        $(this).find('> a > .arrow.open').removeClass('open');
                    }
                });
            } else {
                 menu.find('li.open').removeClass('open');
            }

            el.parents('li').each(function () {
                $(this).addClass('active');
                $(this).find('> a > span.arrow').addClass('open');

                if ($(this).parent('ul.page-sidebar-menu').size() === 1) {
                    $(this).find('> a').append('<span class="selected"></span>');
                }

                if ($(this).children('ul.sub-menu').size() === 1) {
                    $(this).addClass('open');
                }
            });

            if (mode === 'click') {
                if (Metronic.getViewPort().width < 992 && $('.page-sidebar').hasClass("in")) { // close the menu on mobile view while laoding a page
                    $('.page-header .responsive-toggler').click();
                }
            }
        }
    };

}();