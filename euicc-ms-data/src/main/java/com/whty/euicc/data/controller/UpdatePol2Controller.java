package com.whty.euicc.data.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.whty.euicc.base.common.Constant;
import com.whty.euicc.base.common.LogTables;
import com.whty.euicc.base.pojo.BaseUsers;
import com.whty.euicc.base.service.BaseLogsService;
import com.whty.euicc.common.base.BaseController;
import com.whty.euicc.common.base.BaseResponseDto;
import com.whty.euicc.data.pojo.EuiccProfile;
import com.whty.euicc.data.service.EuiccProfileService;
/**
 * profile下载安装
 * @author Administrator
 *
 */
@Controller
@RequestMapping("updatePol2")
public class UpdatePol2Controller  extends BaseController{
	
	@Autowired
	private BaseLogsService baseLogsService;
	
	@Autowired
	private EuiccProfileService euiccProfileService;
	
	/**
	 * 显示主列表页面
	 * @dzmsoftgenerated 2016-09-29 15:39
	 * @return
	 */
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show() {
		return "modules/updatePol2/updatePol2UI";
	}
	
	/**
	 * 更新POL2
	 * @param request
	 * @param response
	 * @param iccid
	 * @param pol2Id
	 * @throws IOException
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void updateProfile(HttpServletRequest request,
			HttpServletResponse response, String iccid, String pol2Id) throws IOException {
		BaseUsers currentUser = (BaseUsers) SecurityUtils.getSubject()
				.getSession()
				.getAttribute(Constant.BaseUsersConstant.CURRENTUSER);
		EuiccProfile euiccProfile = new EuiccProfile();
		euiccProfile.setIccid(iccid);
		euiccProfile.setPol2Id(pol2Id);
		BaseResponseDto baseResponseDto = null;
		
		int flag = euiccProfileService.updatePol2ByPrimaryKey(euiccProfile);


		baseResponseDto = flag > 0 ? new BaseResponseDto(true, "更新成功")
				: new BaseResponseDto(false, "更新失败");
		baseLogsService.insertSelective(LogTables.getOperateLog(currentUser,
				"2", Constant.EUICC_BASE_TYPE, "EUICC_PROFILE",
				getObjectData(euiccProfile), "", "更新POL2", "更新"));
		writeJSONResult(baseResponseDto, response);
	}

	/**
	 * 构建数据树
	 * @param profile
	 * @return
	 */
	private String getObjectData(EuiccProfile profile) {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("EuiccProfile[");
		sBuffer.append("iccid=");
		sBuffer.append(profile.getIccid());
		sBuffer.append("]");
		return sBuffer.toString();
	}
}
