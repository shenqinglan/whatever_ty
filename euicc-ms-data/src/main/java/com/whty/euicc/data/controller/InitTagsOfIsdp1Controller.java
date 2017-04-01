package com.whty.euicc.data.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.google.gson.Gson;
import com.whty.euicc.base.common.Constant;
import com.whty.euicc.base.common.LogTables;
import com.whty.euicc.base.pojo.BaseUsers;
import com.whty.euicc.base.service.BaseLogsService;
import com.whty.euicc.common.base.BaseController;
import com.whty.euicc.common.base.BaseResponseDto;
import com.whty.euicc.common.base.DataTableQuery;
import com.whty.euicc.common.bean.RespMessage;
import com.whty.euicc.common.constant.ErrorCode;
import com.whty.euicc.common.exception.EuiccBusiException;
import com.whty.euicc.common.https.BaseHttp;
import com.whty.euicc.common.utils.CheckCallType;
import com.whty.euicc.common.utils.CheckEmpty;
import com.whty.euicc.common.utils.DateUtil;
import com.whty.euicc.data.message.EuiccMsg;
import com.whty.euicc.data.message.MsgHeader;
import com.whty.euicc.data.message.request.InstallProfileReqBody;
import com.whty.euicc.data.pojo.EuiccProfile;
import com.whty.euicc.data.pojo.EuiccProfileExample;
import com.whty.euicc.data.pojo.EuiccProfileWithBLOBs;
import com.whty.euicc.data.service.EuiccProfileService;
import com.whty.euicc.data.service.ProfileMgrService;
/**
 * 更新连接参数
 * @author Administrator
 *
 */
 
@Controller
@RequestMapping("/initTagsOfIsdp1")
public class InitTagsOfIsdp1Controller extends BaseController{
	
	@Autowired
	private BaseLogsService baseLogsService;
	
	@Autowired
	private EuiccProfileService euiccProfileService;
	

	@Autowired
	ProfileMgrService profileMgrService;

	/**
	 * 显示主列表页面
	 * @dzmsoftgenerated 2016-09-29 15:39
	 * @return
	 */

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show() {
		return "modules/initTagsOfIsdp1/initTagsOfIsdp1UI";
	}
	
	
	@RequestMapping(value = "/showProfiles", method = RequestMethod.POST)
	public void showScp80(HttpServletRequest request, HttpServletResponse response,
			String eid) throws IOException {
		writeJSONResult(eid, response);
	}
	
	/**
	 * 查询
	 * @dzmsoftgenerated 2016-08-03 16:15
	 * @return
	 */
	@RequestMapping(value = "/findProfiles")
	public void find(HttpServletRequest request,HttpServletResponse response,EuiccProfile profile)
			throws IOException {
		DataTableQuery dt = new DataTableQuery(request);
		Map<String,Object> result = buildTableData(dt, profile);
		writeJSONResult(result, response,DateUtil.yyyy_MM_dd_HH_mm_EN);
	}
	
	/**
	 * 构建数据树
	 * @dzmsoftgenerated 2016-08-03 16:15
	 * @param length
	 * @param start
	 * @param draw
	 * @param modules
	 * @return
	 * @throws IOException
	 */
	private Map<String,Object> buildTableData(DataTableQuery dt,EuiccProfile profile) throws IOException {
		// 当前页数
		int currentNumber = dt.getPageStart() / dt.getPageLength() + 1;
		PageBounds pageBounds = new PageBounds(currentNumber, dt.getPageLength());
		EuiccProfileExample example = buildExample(dt, profile);
		PageList<EuiccProfile> euiccProfiles = euiccProfileService.selectByExample(profile,example, pageBounds);
		Map<String,Object> records = new HashMap<String,Object>();
		records.put("data", euiccProfiles);
		records.put("draw", dt.getPageDraw());
		records.put("recordsTotal", euiccProfiles.getPaginator().getTotalCount());
		records.put("recordsFiltered", euiccProfiles.getPaginator().getTotalCount());
		return records;
	}
	
	/**
	 * 构建列表查询条件
	 * @dzmsoftgenerated 2016-08-03 16:15
	 * @param 
	 * @return
	 */
	private EuiccProfileExample buildExample(DataTableQuery dt,EuiccProfile profile) {
		EuiccProfileExample euiccProfileExample = new EuiccProfileExample();
		EuiccProfileExample.Criteria criteria = euiccProfileExample.createCriteria();
		// 查询条件
		String eid = profile.getCardEid();
		
		if (!StringUtils.equalsIgnoreCase(profile.getOptType(), "install") && CheckEmpty.isNotEmpty(eid)) {
			criteria.andEidEqualTo(eid);
		}
		
		// 排序条件
		if (CheckEmpty.isNotEmpty(dt.getOrderBy()) && CheckEmpty.isNotEmpty(dt.getOrderParam())){
			// 
			StringBuilder orderByClause = new StringBuilder("");
			orderByClause.append(dt.getOrderParam()).append(" ").append(dt.getOrderBy());
			euiccProfileExample.setOrderByClause(orderByClause.toString());
		}
		return euiccProfileExample;
	}
	
	@RequestMapping(value="/initTags",method=RequestMethod.POST)
	public void initTags(HttpServletRequest request,HttpServletResponse response,
			String id){
		BaseUsers currenUsers = (BaseUsers)SecurityUtils.getSubject()
				.getSession()
				.getAttribute(Constant.BaseUsersConstant.CURRENTUSER);
		BaseResponseDto baseResponseDto = null;
		EuiccProfileWithBLOBs record = euiccProfileService.selectByPrimaryKey(id);
		boolean flag = true; 
		String msg = "";
		try {
			if(CheckCallType.isLocalOperate()){
//				flag = euiccProfileService.updateByPrimaryKeySelective(record);
			}else{
				initTagsOfIsdp1(record);
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			if(e instanceof EuiccBusiException){
				EuiccBusiException busi = (EuiccBusiException) e;
				msg = busi.getMessage();
			}
		}
		baseResponseDto = flag ? new BaseResponseDto(true, "更新连接参数成功 ")
				: new BaseResponseDto(false, "更新连接参数失败");
		baseLogsService.insertSelective(LogTables.getOperateLog(currenUsers,
				"2", Constant.EUICC_BASE_TYPE, "EUICC_PROFILE",
				getObjectData(record), "", "更新连接参数", "更新"));
		writeJSONResult(baseResponseDto, response);
	}
	
	public void initTagsOfIsdp1(EuiccProfileWithBLOBs record)throws Exception{
		MsgHeader header = new MsgHeader("initTagsOfIsdp1ByDp");
		InstallProfileReqBody requestBody = new InstallProfileReqBody();
		requestBody.setEid(record.getEid());
		requestBody.setIccid(record.getIccid());
		requestBody.setIsdPAid(record.getIsdPAid());
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
		RespMessage respMessage = new Gson().fromJson(new String(msgBype), RespMessage.class);
        if(!StringUtils.equals(ErrorCode.SUCCESS, respMessage.getCode())){
        	throw new EuiccBusiException(respMessage.getCode(),respMessage.getMessage());
        }
	
	}
	private String getObjectData(EuiccProfile record) {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("EuiccCard[");
		sBuffer.append("id=");
		sBuffer.append(record.getEid());
		sBuffer.append("]");
		return sBuffer.toString();
	}


}
