<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- BEGIN STYLE CUSTOMIZER -->
<div class="theme-panel hidden-xs hidden-sm">
	<div class="theme-options">
		<div class="theme-option theme-colors clearfix">
			<span>
			颜色主题设置</span>
			<ul>
				<li class="color-default tooltips" data-style="default"   data-container="body" data-original-title="黑色">
				</li>
				<li class="color-darkblue tooltips" data-style="darkblue" data-container="body" data-original-title="深蓝">
				</li>
				<li class="color-blue tooltips" data-style="blue" data-container="body" data-original-title="纯蓝">
				</li>
				<li class="color-grey tooltips" data-style="grey" data-container="body" data-original-title="暗灰">
				</li>
				<li class="color-light tooltips" data-style="light" data-container="body" data-original-title="明亮">
				</li>
				<li class="color-light2 tooltips" data-style="light2"   data-container="body" data-original-title="亮白">
				</li>
			</ul>
		</div>
		<div class="theme-option">
			<span>
			主题风格</span>
			<select class="layout-style-option form-control input-sm">
				<option value="square" >方角边风格</option>
				<option value="rounded" selected="selected">圆角边风格</option>
			</select>
		</div>
		<div class="theme-option">
			<span>
			Layout </span>
			<select class="layout-option form-control input-sm">
				<option value="fluid" selected="selected">Fluid</option>
				<option value="boxed">Boxed</option>
			</select>
		</div>
		<div class="theme-option">
			<span>
			Header </span>
			<select class="page-header-option form-control input-sm">
				<option value="fixed" selected="selected">Fixed</option>
				<option value="default">Default</option>
			</select>
		</div>
		<div class="theme-option">
			<span>
			Top Menu Dropdown</span>
			<select class="page-header-top-dropdown-style-option form-control input-sm">
				<option value="light" selected="selected">Light</option>
				<option value="dark">Dark</option>
			</select>
		</div>
		<div class="theme-option">
			<span>
			侧边栏停靠</span>
			<select class="sidebar-option form-control input-sm">
				<option value="fixed">Fixed</option>
				<option value="default" selected="selected">Default</option>
			</select>
		</div>
		<div class="theme-option">
			<span>
			菜单展现方式 </span>
			<select class="sidebar-menu-option form-control input-sm">
				<option value="accordion" selected="selected">Accordion</option>
				<option value="hover">Hover</option>
			</select>
		</div>
		<div class="theme-option">
			<span>
			Sidebar Style </span>
			<select class="sidebar-style-option form-control input-sm">
				<option value="default" selected="selected">Default</option>
				<option value="light">Light</option>
			</select>
		</div>
		<div class="theme-option">
			<span>
			侧边栏位置 </span>
			<select class="sidebar-pos-option form-control input-sm">
				<option value="left" selected="selected">Left</option>
				<option value="right">Right</option>
			</select>
		</div>
		<div class="theme-option">
			<span>
			页脚 </span>
			<select class="page-footer-option form-control input-sm">
				<option value="fixed">Fixed</option>
				<option value="default" selected="selected">Default</option>
			</select>
		</div>
	</div>
</div>
<!-- END STYLE CUSTOMIZER -->
<!-- BEGIN PAGE HEADER-->
<!-- BEGIN PAGE TITLE & BREADCRUMB-->
<h3 id="page-title" class="page-title">
${requestScope.pageName} <small>${requestScope.pageMemo} </small>
</h3>
<!-- END PAGE TITLE & BREADCRUMB-->
<!-- END PAGE HEADER-->