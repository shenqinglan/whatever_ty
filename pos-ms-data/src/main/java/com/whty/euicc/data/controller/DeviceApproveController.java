package com.whty.euicc.data.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.base.common.Constant;
import com.whty.euicc.base.common.LogTables;
import com.whty.euicc.base.pojo.ApprovalInfo;
import com.whty.euicc.base.pojo.ApprovalInfoExample;
import com.whty.euicc.base.pojo.ApprovalUserInfo;
import com.whty.euicc.base.pojo.BaseUsers;
import com.whty.euicc.base.service.ApprovalInfoService;
import com.whty.euicc.base.service.BaseLogsService;
import com.whty.euicc.common.base.BaseController;
import com.whty.euicc.common.base.BaseResponseDto;
import com.whty.euicc.common.base.DataTableQuery;
import com.whty.euicc.common.utils.CheckEmpty;
import com.whty.euicc.common.utils.DateUtil;


/**
 * @author dzmsoft
 * @date 2016-05-30 15:37
 *
 * @version 1.0
 */
@Controller
@RequestMapping("/deviceApprove")
public class DeviceApproveController extends BaseController{

	@Autowired
	ApprovalInfoService approvalInfoService;
	
	@Autowired
	private BaseLogsService baseLogsService;
	
	private String userId;
	
	/**
	 * 显示主列表页面
	 * @dzmsoftgenerated 2016-05-30 15:37
	 * @return
	 */
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show() {
		return "modules/deviceApprove/deviceApproveUI";
	}
	
	/**
	 * 查询
	 * @dzmsoftgenerated 2016-05-30 15:37
	 * @return
	 */
	@RequestMapping(value = "/find")
	public void find(HttpServletRequest request,HttpServletResponse response)
			throws IOException {
		DataTableQuery dt = new DataTableQuery(request);
		Map<String,Object> result = buildTableData(dt);
		writeJSONResult(result, response,DateUtil.yyyy_MM_dd_HH_mm_EN);
	}
	
	/**
	 * 构建数据树
	 * @dzmsoftgenerated 2016-05-30 15:37
	 * @param length
	 * @param start
	 * @param draw
	 * @param modules
	 * @return
	 * @throws IOException
	 */
	private Map<String,Object> buildTableData(DataTableQuery dt) throws IOException {
		// 当前页数
		int currentNumber = dt.getPageStart() / dt.getPageLength() + 1;
		PageBounds pageBounds = new PageBounds(currentNumber, dt.getPageLength());
		ApprovalInfoExample example = buildExample(dt);
		PageList<ApprovalUserInfo> approvalUserInfos = approvalInfoService.selectByExample(example, pageBounds);
		Map<String,Object> records = new HashMap<String,Object>();
		records.put("data", approvalUserInfos);
		records.put("draw", dt.getPageDraw());
		records.put("recordsTotal", approvalUserInfos.getPaginator().getTotalCount());
		records.put("recordsFiltered", approvalUserInfos.getPaginator().getTotalCount());
		return records;
	}
	
	/**
	 * 构建列表查询条件
	 * @dzmsoftgenerated 2016-05-30 15:37
	 * @param 
	 * @return
	 */
	private ApprovalInfoExample buildExample(DataTableQuery dt) {
		ApprovalInfoExample approvalInfoExample = new ApprovalInfoExample();
		ApprovalInfoExample.Criteria criteria = approvalInfoExample.createCriteria();
		// 查询条件
		criteria.andApprovalAmountGreaterThan(0);
		// 排序条件
		if (CheckEmpty.isNotEmpty(dt.getOrderBy()) && CheckEmpty.isNotEmpty(dt.getOrderParam())){
			// 
			StringBuilder orderByClause = new StringBuilder("");
			orderByClause.append(dt.getOrderParam()).append(" ").append(dt.getOrderBy());
			approvalInfoExample.setOrderByClause(orderByClause.toString());
		}
		return approvalInfoExample;
	}
	
	/**
	 * 通过主键查询
	 * 
	 * @param request
	 * @param response
	 * @param moduleId
	 * @throws java.io.IOException
	 */
	@RequestMapping(value = "/view", method = RequestMethod.POST)
	public void view(HttpServletRequest request, HttpServletResponse response,
			String approvalUserId) throws IOException {
		userId = approvalUserId;
		ApprovalUserInfo approvalUserInfo = approvalInfoService.selectUserInfoByPrimaryKey(approvalUserId);
		writeJSONResult(approvalUserInfo, response);
	}
	
	/**
	 * 审批
	 */
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public void submit(ApprovalUserInfo approvalUserInfo, HttpServletResponse response) {
		Subject subjuct = SecurityUtils.getSubject();
		BaseUsers  currentUser= (BaseUsers) subjuct.getSession().getAttribute(Constant.BaseUsersConstant.CURRENTUSER);
		ApprovalInfo info = approvalInfoService.selectByPrimaryKey(userId);
		info.setApprovalAmount(0);
		info.setApprovalState("0");
		info.setTotalApprovalAmount(info.getTotalApprovalAmount() + approvalUserInfo.getApprovalAmount());
		info.setRealAmount(info.getRealAmount() + approvalUserInfo.getApprovalAmount());
		
		int flg = approvalInfoService.updateByPrimaryKeySelective(info);
		BaseResponseDto baseResponseDto = flg > 0 ? new BaseResponseDto(true, "申请成功！") : new BaseResponseDto(false, "申请失败！");
		baseLogsService.insertSelective(LogTables.getOperateLog(
                currentUser, "3", "POS_BASE_TYPE",
                "APPROVAL_INFO", getObjectData(approvalUserInfo), "", "设备审批",
                "审批"));
		writeJSONResult(baseResponseDto,response);
	}
	
	private String getObjectData(ApprovalUserInfo approvalUserInfo) {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("ApprovalInfo[");
		sBuffer.append("userAccount=");
		sBuffer.append(approvalUserInfo.getUserAccount());
		sBuffer.append(", ");
		sBuffer.append("approvalAmount=");
		sBuffer.append(approvalUserInfo.getApprovalAmount());
		sBuffer.append("]");
		return sBuffer.toString();
	}
	
	/**
	 * 审批
	 */
	@RequestMapping(value = "/reject", method = RequestMethod.POST)
	public void reject(String approvalUserId, HttpServletResponse response) {
		Subject subjuct = SecurityUtils.getSubject();
		BaseUsers  currentUser= (BaseUsers) subjuct.getSession().getAttribute(Constant.BaseUsersConstant.CURRENTUSER);
		ApprovalInfo approvalInfo = new ApprovalInfo();
		ApprovalInfo info = approvalInfoService.selectByPrimaryKey(approvalUserId);
		approvalInfo = info;
		info.setApprovalAmount(0);
		info.setApprovalState("0");
		
		int flg = approvalInfoService.updateByPrimaryKeySelective(info);
		BaseResponseDto baseResponseDto = flg > 0 ? new BaseResponseDto(true, "驳回成功！") : new BaseResponseDto(false, "驳回失败！");
		baseLogsService.insertSelective(LogTables.getOperateLog(
                currentUser, "4", "POS_BASE_TYPE",
                "APPROVAL_INFO", getRejectObjectData(approvalInfo), "", "申请驳回",
                "驳回"));
		writeJSONResult(baseResponseDto,response);
	}
	
	private String getRejectObjectData(ApprovalInfo approvalInfo) {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("ApprovalInfo[");
		sBuffer.append("approvalUserId(=");
		sBuffer.append(approvalInfo.getApprovalUserId());
		sBuffer.append(", ");
		sBuffer.append("approvalAmount=");
		sBuffer.append(approvalInfo.getApprovalAmount());
		sBuffer.append("]");
		return sBuffer.toString();
	}
}
