<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<%@ taglib prefix="shiro" uri="/WEB-INF/tlds/shiro.tld" %>
<title>欢迎登陆终端授权管理系统</title>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<c:set var="ctxResources" value="${pageContext.request.contextPath}/resources" scope="request"/>
<script>
	var ctx = '${ctx}';
	var ctxResources = '${ctxResources}';
</script>
