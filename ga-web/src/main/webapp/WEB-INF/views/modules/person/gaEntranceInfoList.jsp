<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>出入信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#door").empty();
            resetOrderStatusOptions("${gaEntranceInfo.areaId}");
            $("#areaId").change(function(){
                $("#door").empty();
                resetOrderStatusOptions($("#areaId").find(":selected").val());
            });
            setInterval("refresh()", 1000);
		});
		function refresh() {
			var urlHost = getRootHost();
            var urlStr = urlHost + "${ctx}/person/gaEntranceInfo/" + "refreshList?areaId=" + $("#areaId").find(":selected").val() + "&gateId=" + $("#gateId").find(":selected").val()
            		             + "&startTime=" + document.getElementsByName("startTime")[0].value + "&endTime=" + document.getElementsByName("endTime")[0].value + "&pageNo=1&pageSize=100000";
            
            //alert(urlStr);
            $.ajax({  
                url: urlStr, 
                type: "POST",   
                async : true,
                success: function (data) {  
                    var re = jQuery.parseJSON(data);
                    $("#contentTable  tr:not(:first)").html("");
                    var tbBody = '';
                    for (var i in re) {  
                        var jsonObj = re[i];
                        tbBody += "<tr>";
                        tbBody += "<td>" + jsonObj.areaName + "</td>";
                        tbBody += "<td>" + jsonObj.building + "</td>";
                        tbBody += "<td>" + jsonObj.door + "</td>";
                        tbBody += "<td>" + jsonObj.name + "</td>";
                        tbBody += "<td>" + jsonObj.idCardNo + "</td>";
                        tbBody += "<td>" + jsonObj.swipeTime + "</td>";
                        tbBody += "<td>" + "</td>";
                        tbBody += "<td>" + "</td>";
                        tbBody += "<td>" + "</td>";
                        tbBody += "</tr>";
                    }
                    $("#contentTable").append(tbBody);
                }  
            });     
		}
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function resetOrderStatusOptions(stateType){
            $("#door").append("<option value=''>请选择</option>");
            if(stateType != "") {//根据传入的数据字典类型，初始化下拉框 
                var urlHost = getRootHost();
                var urlStr = urlHost + "${ctx}/person/gaEntranceInfo/" + "getDoorByAreaId?areaId=" + stateType;
                $.ajax({  
                    url: urlStr, 
                    type: "POST",   
                    async : true,
                    success: function (data) {  
                        var re = jQuery.parseJSON(data);
                        var optionstring = "";
                        for (var i in re) {  
                            var jsonObj = re[i];
                            optionstring += "<option value=\"" + jsonObj.id + "\" >" + jsonObj.door + "</option>";
                        } 
                        $("#door").append(optionstring);
                        var initVal = "${gaEntranceInfo.door}";
                        if (initVal != null && initVal != "") {
                            $("#door").find("option[value='"+ initVal + "']").attr("selected","selected");
                            $("#s2id_door").find("span.select2-chosen").text($("#door").find(":selected").text());
                        } else {
                            $("#door option").each(function(){
                                var thisOption = $(this);
                                if(!thisOption.val()) {
                                    thisOption.attr("selected","selected");
                                    return false;//跳出循环
                                }                   
                            }); 
                            $("#s2id_door").find("span.select2-chosen").text($("#door").find(":selected").text());
                        }
                    }  
                });                 
            } else {
                $("#door option").each(function(){
                    var thisOption = $(this);
                    if(!thisOption.val()) {
                        thisOption.attr("selected","selected");
                        return false;//跳出循环
                    }                   
                }); 
                $("#s2id_door").find("span.select2-chosen").text($("#door").find(":selected").text());
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
		<li class="active"><a href="${ctx}/person/gaEntranceInfo/">出入信息列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="gaEntranceInfo" action="${ctx}/person/gaEntranceInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="1"/>
		<input id="pageSize" name="pageSize" type="hidden" value="100000"/>
		<ul class="ul-form">
			<li><label>小区：</label>
                <form:select path="areaId" class="input-xlarge ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getAreaInfoList()}" itemLabel="areaName" itemValue="id" htmlEscape="false"/>
                </form:select>
            </li>
            <li><label>楼栋单元号：</label>
                <form:select path="gateId" class="input-xlarge ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getGateInfoList()}" itemLabel="remarks" itemValue="id" htmlEscape="false"/>
                </form:select>
            </li>
			<li><label>开始时间：</label>
            <input name="startTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                    value="<fmt:formatDate value="${gaEntranceInfo.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                    onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
            </li>
            <li><label>结束时间：</label>
            <input name="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                    value="<fmt:formatDate value="${gaEntranceInfo.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                    onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
            </li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-bordered table-condensed">
		<thead>
			<tr>
				<th>小区</th>
				<th>楼栋</th>
                <th>单元</th>
                <th>刷卡人姓名</th>
                <th>刷卡人身份证号</th>
				<th>刷卡时间</th>
				<th>出还是入</th>
				<th>人脸信息</th>
				<th>备注</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="gaEntranceInfo">
			<tr>
				<td>
                    ${gaEntranceInfo.areaName}
                </td>
                <td>
                    ${gaEntranceInfo.building}
                </td>
                <td>
                    ${gaEntranceInfo.door}
                </td>
                <td>
                    ${gaEntranceInfo.name}
                </td>
                <td>
                    ${gaEntranceInfo.idCardNo}
                </td>
				<td>
					<fmt:formatDate value="${gaEntranceInfo.swipeTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(gaEntranceInfo.inOrOut, 'ga_in_out', '')}
				</td>
				<td>
					${gaEntranceInfo.face}
				</td>
				<td>
					${gaEntranceInfo.remarks}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
</body>
</html>