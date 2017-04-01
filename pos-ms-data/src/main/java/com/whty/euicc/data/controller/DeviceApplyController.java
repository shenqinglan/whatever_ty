package com.whty.euicc.data.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whty.euicc.base.common.Constant;
import com.whty.euicc.base.common.LogTables;
import com.whty.euicc.base.pojo.ApprovalInfo;
import com.whty.euicc.base.pojo.BaseUsers;
import com.whty.euicc.base.service.ApprovalInfoService;
import com.whty.euicc.base.service.BaseLogsService;
import com.whty.euicc.common.base.BaseController;
import com.whty.euicc.common.base.BaseResponseDto;


/**
 * @author dzmsoft
 * @date 2016-05-30 15:37
 *
 * @version 1.0
 */
@Controller
@RequestMapping("/deviceApply")
public class DeviceApplyController extends BaseController{

	@Autowired
	ApprovalInfoService approvalInfoService;	
	
	@Autowired
	private BaseLogsService baseLogsService;
	
	/**
	 * 显示主列表页面
	 * @dzmsoftgenerated 2016-09-20 09:19
	 * @return
	 */
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show() {
		return "modules/deviceApply/deviceApplyUI";
	}
	
	/**
	 * 申请
	 */
	@RequestMapping(value = "/apply", method = RequestMethod.POST)
	@ResponseBody
	public void apply(ApprovalInfo approvalInfo, HttpServletResponse response) {
		Subject subjuct = SecurityUtils.getSubject();
		BaseUsers  currentUser= (BaseUsers) subjuct.getSession().getAttribute(Constant.BaseUsersConstant.CURRENTUSER);
		String approvalUserId = currentUser.getUserId();
		ApprovalInfo info = approvalInfoService.selectByPrimaryKey(approvalUserId);
		info.setApprovalAmount(approvalInfo.getApprovalAmount());
		info.setApprovalState("1");
		
		int flg = approvalInfoService.updateByPrimaryKeySelective(info);
		BaseResponseDto baseResponseDto = flg > 0 ? new BaseResponseDto(true, "申请成功") : new BaseResponseDto(false, "申请失败");
		baseLogsService.insertSelective(LogTables.getOperateLog(
                currentUser, "2", "POS_BASE_TYPE",
                "APPROVAL_INFO", getObjectData(info), "", "设备申请",
                "申请"));
		writeJSONResult(baseResponseDto,response);
	}
	

	private String getObjectData(ApprovalInfo approvalInfo) {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("ApprovalInfo[");
		sBuffer.append("approvalUserId=");
		sBuffer.append(approvalInfo.getApprovalUserId());
		sBuffer.append(", ");
		sBuffer.append("approvalAmount=");
		sBuffer.append(approvalInfo.getApprovalAmount());
		sBuffer.append("]");
		return sBuffer.toString();
	}
	
	/**
	 * 获取申请次数信息
	 */
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	@ResponseBody
	public void find(HttpServletResponse response) {
		Subject subjuct = SecurityUtils.getSubject();
		BaseUsers  currentUser= (BaseUsers) subjuct.getSession().getAttribute(Constant.BaseUsersConstant.CURRENTUSER);
		String approvalUserId = currentUser.getUserId();
		ApprovalInfo approvalInfo = approvalInfoService.selectByPrimaryKey(approvalUserId);
		if (approvalInfo == null) {
			ApprovalInfo info = new ApprovalInfo();
			info.setApprovalUserId(approvalUserId);
			info.setTotalApprovalAmount(0);
			info.setApprovalAmount(0);
			info.setRealAmount(0);
			info.setApprovalState("0");
			approvalInfoService.insertSelective(info);
			writeJSONResult(info, response);
			return;
		}
		writeJSONResult(approvalInfo, response);
	}
}
