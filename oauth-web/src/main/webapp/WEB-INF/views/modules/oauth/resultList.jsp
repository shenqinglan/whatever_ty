<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>测试结果</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			setInterval("refresh()", 1000)
		});
		
		function refresh() {
			 
            var urlHost = getRootHost();
            var urlStr = urlHost + "${ctx}/oauth/testCase/" + "refreshList?msisdn=" + document.getElementById("contentTable").rows[1].cells[0].innerHTML 
            		             + "&respcode=" + document.getElementById("contentTable").rows[1].cells[1].innerHTML
                                 + "&respdesc=" + document.getElementById("contentTable").rows[1].cells[2].innerHTML 
                                 + "&accepttime=" + document.getElementById("contentTable").rows[1].cells[3].innerHTML 
                                 + "&transactionId=" + document.getElementById("contentTable").rows[1].cells[4].innerHTML
                                 + "&userrID=" + document.getElementById("userrID").value;
            var c11 = document.getElementById("contentTable").rows[1].cells[1].innerHTML;
            c11 = c11.replace(/(^\s*)|(\s*$)/g, "");
            if (c11 == "0000") {
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
                        tbBody += "<td>" + jsonObj.respcode + "</td>";
                        tbBody += "<td>" + jsonObj.respdesc + "</td>";
                        tbBody += "<td>" + jsonObj.accepttime + "</td>";
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
		<li class="active"><a href="${ctx}/oauth/testCase/">测试结果</a></li>
	</ul>
	<sys:message content="${message}"/>
	<input id="userrID" name="userrID" type="hidden"/ value="${userrID}">
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
			    <th>手机号码</th>
				<th>应答编码</th>
				<th>应答描述</th>
				<th>处理时间</th>
				<th>交易号</th>
				<th>EID</th>
				<th>接口类型</th>
				<th>卡状态</th>
				<th>结束时间</th>
			</tr>
		</thead>
		<tbody id = "tbody">
		<c:forEach items="${list}" var="outputEntity">
			<tr>
			    <td>
                    ${outputEntity.msisdn}
                </td>
				<td>
					${outputEntity.respcode}
				</td>
				<td>
					${outputEntity.respdesc}
				</td>
				<td>
					${outputEntity.accepttime}
				</td>
				<td>
                    ${outputEntity.transactionId}
                </td>
                <td>
                    ${outputEntity.eid}
                </td>
                <td>
                    ${outputEntity.interfaceType}
                </td>
                <td>
                    ${outputEntity.retResult}
                </td>
                <td>
                    ${outputEntity.finishTime}
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