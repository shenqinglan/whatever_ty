<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>历史结果查看</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			setInterval("refresh()", 1000)
		});
		
		function refresh() {
			 
            var urlHost = getRootHost();
            var urlStr = urlHost + "${ctx}/oauth/query/" + "refreshList"
                                 + "?userrID=" + document.getElementById("userrID").value;
            
            $.ajax({  
                url: urlStr, 
                type: "POST",   
                async : true,
                success: function (data) {  
                	
                    var re = jQuery.parseJSON(data);
                    window.document.getElementById("tbody").innerHTML = "";
                    var tbBody = '';
                    for (var i in re) {  
                        var jsonObj = re[i];
                        tbBody += "<tr>";
                        tbBody += "<td>" + jsonObj.msisdn + "</td>";
                        tbBody += "<td>" + jsonObj.operatorTime + "</td>";
                        tbBody += "<td>" + jsonObj.transactionId + "</td>";
                        tbBody += "<td>" + jsonObj.eid + "</td>";
                        tbBody += "<td>" + jsonObj.interfaceType + "</td>";
                        tbBody += "<td>" + jsonObj.retResult + "</td>";
                        tbBody += "<td>" + jsonObj.finishTime + "</td>";
                        tbBody += "</tr>";
                    }
                    $("tbody").html(tbBody);
                    
                },
                error: function () {  
                    
                }
            });     
            
        }
		function getRootHost(){
	        var pathName = window.location.pathname.substring(1);
	        var webName = (pathName == ''?'':pathName.substring(0,pathName.indexOf('/')));
	        return window.location.protocol + '//' + window.location.host + '/';
	    }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/oauth/query/">历史结果查看</a></li>
	</ul>
	<sys:message content="${message}"/>
	<input id="userrID" name="userrID" type="hidden"/ value="${userrID}">
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
			    <th>手机号码</th>
				<th>处理时间</th>
				<th>交易号</th>
				<th>EID</th>
				<th>接口类型</th>
				<th>卡状态</th>
				<th>结束时间</th>
			</tr>
		</thead>
		<tbody id = "tbody">
		<c:forEach items="${list}" var="oauthInterfaceInfo">
			<tr>
			    <td>
                    ${oauthInterfaceInfo.msisdn}
                </td>
				<td>
					${oauthInterfaceInfo.operatorTime}
				</td>
				<td>
                    ${oauthInterfaceInfo.transactionId}
                </td>
                <td>
                    ${oauthInterfaceInfo.eid}
                </td>
                <td>
                    ${oauthInterfaceInfo.interfaceType}
                </td>
                <td>
                    ${oauthInterfaceInfo.retResult}
                </td>
                <td>
                    ${oauthInterfaceInfo.finishTime}
                </td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="form-actions">
            <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
        </div>
</body>
</html>