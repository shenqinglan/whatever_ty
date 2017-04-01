package com.whty.euicc.data.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
import com.whty.euicc.data.pojo.RootKeyInfo;
import com.whty.euicc.data.pojo.TerminalDeviceInfo;
import com.whty.euicc.data.service.TerminalDeviceInfoService;


/**
 * @author dzmsoft
 * @date 2016-05-30 15:37
 *
 * @version 1.0
 */
@Controller
@RequestMapping("/deviceUnlock")
public class DeviceUnlockController extends BaseController{

	@Autowired
	TerminalDeviceInfoService terminalDeviceInfoService;
	
	@Autowired
	ApprovalInfoService approvalInfoService;
	
	@Autowired
	private BaseLogsService baseLogsService;
	
	private TerminalDeviceInfo info = null;
	
	/**
	 * 显示主列表页面
	 * @dzmsoftgenerated 2016-05-30 15:37
	 * @return
	 */
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show() {
		return "modules/deviceUnlock/deviceUnlockUI";
	}
	
	/**
	 * 解锁
	 */
	@RequestMapping(value = "/unlock", method = RequestMethod.POST)
	@Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
	public void unlock(TerminalDeviceInfo terminalDeviceInfo, HttpServletResponse response) {
		Subject subjuct = SecurityUtils.getSubject();
		BaseUsers  currentUser= (BaseUsers) subjuct.getSession().getAttribute(Constant.BaseUsersConstant.CURRENTUSER);
		ApprovalInfo approvalInfo = approvalInfoService.selectByPrimaryKey(currentUser.getUserId());
		if ((approvalInfo != null && approvalInfo.getRealAmount() == 0) || approvalInfo == null) {
			writeJSONResult(new BaseResponseDto(false, "可用解锁次数不足，请申请！") ,response);
			return;
		}
		
		info = terminalDeviceInfoService.selectByPrimaryKey(terminalDeviceInfo.getSn());
		if ("1".equals(info.getLockState())) {
			if (info.getLockCount() >= 3) {
				if (!compareDate(Long.valueOf(info.getLockTime()), System.currentTimeMillis())) {
					writeJSONResult(new BaseResponseDto(false, "此设备已解锁三次，不能再解锁，请第二天再试！") ,response);
					return;
				} else {
					info.setLockCount(0);
				}
			}/* else {
				if (System.currentTimeMillis() - Long.valueOf(info.getLockTime()) < 5*60*1000) {
					writeJSONResult(new BaseResponseDto(false, "5分钟内不能连续解锁！") ,response);
					return;
				}
			}*/
		}
		Date date = new Date();
		info.setHardwareId(terminalDeviceInfo.getHardwareId());
		info.setUpdateDate(date);
		info.setLockState("1");
		info.setLockCount(info.getLockCount() + 1);
		info.setLockTime(String.valueOf(System.currentTimeMillis()));
		approvalInfo.setRealAmount(approvalInfo.getRealAmount() - 1);
		int flg1 = terminalDeviceInfoService.updateByPrimaryKeySelective(info);
		int flg2 = approvalInfoService.updateByPrimaryKeySelective(approvalInfo);
		BaseResponseDto baseResponseDto = flg1 > 0 && flg2 > 0 ? new BaseResponseDto(true, "解锁成功") : new BaseResponseDto(false, "解锁失败");
		baseLogsService.insertSelective(LogTables.getOperateLog(
                currentUser, "1", "POS_BASE_TYPE",
                "TERMINAL_DEVICE_INFO", getObjectData(terminalDeviceInfo), "", "设备解锁",
                "解锁"));
		writeJSONResult(baseResponseDto,response);
	}
	

	private String getObjectData(TerminalDeviceInfo terminalDeviceInfo) {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("TerminalDeviceInfo[");
		sBuffer.append("sn=");
		sBuffer.append(terminalDeviceInfo.getSn());
		sBuffer.append(", ");
		sBuffer.append("hardwareId=");
		sBuffer.append(terminalDeviceInfo.getHardwareId());
		sBuffer.append("]");
		return sBuffer.toString();
	}
	
	private boolean compareDate(long lastMillis, long currentMillis) {
		try {
			Date lastDate = new Date(lastMillis);
			Date currentDate = new Date(currentMillis);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String strLastDate = sdf.format(lastDate);
			String strCurrentDate = sdf.format(currentDate);
			if (sdf.parse(strLastDate).getTime() < sdf.parse(strCurrentDate).getTime()) {
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	/**
	 * 获取根密钥
	 */
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	@ResponseBody
	public void find(String sn, HttpServletResponse response) {
		RootKeyInfo rootKeyInfo = terminalDeviceInfoService.selectRootKey(sn);
		writeJSONResult(rootKeyInfo, response);
	}
	
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public void check(String sn, HttpServletResponse response) {
		info = terminalDeviceInfoService.selectByPrimaryKey(sn);
		writeJSONResult(info == null ? new BaseResponseDto(false, "系统中未录入该设备！") : new BaseResponseDto(true, "success"),response);
	}
}
