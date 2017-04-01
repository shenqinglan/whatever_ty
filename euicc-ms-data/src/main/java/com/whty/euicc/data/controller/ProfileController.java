package com.whty.euicc.data.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.base.common.Constant;
import com.whty.euicc.base.common.LogTables;
import com.whty.euicc.base.controller.BaseController;
import com.whty.euicc.base.pojo.BaseUsers;
import com.whty.euicc.base.service.BaseLogsService;
import com.whty.euicc.common.base.BaseResponseDto;
import com.whty.euicc.common.base.DataTableQuery;
import com.whty.euicc.common.utils.CheckEmpty;
import com.whty.euicc.data.pojo.EuiccProfile;
import com.whty.euicc.data.pojo.EuiccProfileExample;
import com.whty.euicc.data.pojo.EuiccProfileExample.Criteria;
import com.whty.euicc.data.pojo.EuiccProfileWithBLOBs;
import com.whty.euicc.data.service.EuiccProfileService;
import com.whty.euicc.profile.basehandler.BaseHandler;
/**
 * profile 录入
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/profile")
public class ProfileController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(ProfileController.class); 
	@Autowired
	private EuiccProfileService profileService;

	@Autowired
	private BaseLogsService baseLogsService;

	/**
	 * 其实doGet和doPost的功能都是一样，就是获取前端传过来的数据。
	 * 但是通过method=“get”传过来的数据，会在请求的URL里面显示。
	 * 而method=“post”的时候，数据不会显示在请求的URL里面显示。
	 * @return
	 */
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show() {
		return "/modules/profile/profileUI";
	}

	@RequestMapping("/findAll")
	public void find(HttpServletRequest request, HttpServletResponse response,
			EuiccProfile profile) throws IOException {
		DataTableQuery dt = new DataTableQuery(request);
		Map<String, Object> result = buildTableData(dt, profile);
		writeJSONResult(result, response);
	}

	private Map<String, Object> buildTableData(DataTableQuery dt,
			EuiccProfile profile) throws IOException {
		// 当前页数
		int currentNumber = dt.getPageStart() / dt.getPageLength() + 1;
		PageBounds pageBounds = new PageBounds(currentNumber, dt
				.getPageLength());
		EuiccProfileExample example = buildExample(dt, profile);
		PageList<EuiccProfileWithBLOBs> pro = profileService
				.selectByExampleWithBLOBs(example, pageBounds);
		Map<String, Object> records = new HashMap<String, Object>();
		records.put("data", pro);
		records.put("draw", dt.getPageDraw());
		records.put("recordsTotal", pro.getPaginator().getTotalCount());
		records.put("recordsFiltered", pro.getPaginator().getTotalCount());
		return records;
	}

	private EuiccProfileExample buildExample(DataTableQuery dt, EuiccProfile profile) {
		EuiccProfileExample exmple = new EuiccProfileExample();
		Criteria criteria = exmple.createCriteria();
		// 查询条件
		if (CheckEmpty.isNotEmpty(profile.getIccid())) {
			criteria.andIccidLike("%" + profile.getIccid() + "%");
		}

		// criteria.andLeafTypeNotEqualTo(Constant.LEAFTYPEBUTTON);
		// 排序条件
		if (CheckEmpty.isNotEmpty(dt.getOrderBy())
				&& CheckEmpty.isNotEmpty(dt.getOrderParam())) {
			//
			StringBuilder orderByClause = new StringBuilder("");
			orderByClause.append(dt.getOrderParam()).append(" ").append(
					dt.getOrderBy());
			exmple.setOrderByClause(orderByClause.toString());
		}
		return exmple;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/view", method = RequestMethod.POST)
	private void view(HttpServletResponse response, String iccid) {
		EuiccProfileWithBLOBs profile = profileService.selectByPrimaryKey(iccid);
		writeJSONResult(profile, response);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void delete(HttpServletRequest request,
			HttpServletResponse response, String iccid) throws IOException {
		BaseUsers currentUser = (BaseUsers) SecurityUtils.getSubject()
				.getSession().getAttribute(
						Constant.BaseUsersConstant.CURRENTUSER);

		EuiccProfileWithBLOBs profile = profileService.selectByPrimaryKey(iccid);
		BaseResponseDto baseResponseDto = null;
		int flag = profileService.deleteByPrimaryKey(iccid);
		baseResponseDto = flag > 0 ? new BaseResponseDto(true, "删除成功")
				: new BaseResponseDto(false, "删除失败");
		baseLogsService.insertSelective(LogTables.getOperateLog(currentUser,
				"2", Constant.EUICC_BASE_TYPE, "BASE_MODULES",
				getObjectData(profile), "", "菜单管理", "删除"));
		writeJSONResult(baseResponseDto, response);

	}

	@RequestMapping(value = "/import/{iccid}", method = RequestMethod.POST)
	public void importExl(@PathVariable String iccid,HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// if (!file.getOriginalFilename().contains(".asn")) {
		// writeJSONResult(new BaseResponseDto(true, "请选择Excel文档"), response);
		// return;
		// }
		int flag = 1;
		logger.info("import asn begin");
		BaseUsers currentUser = (BaseUsers) SecurityUtils.getSubject()
		.getSession().getAttribute(
				Constant.BaseUsersConstant.CURRENTUSER);
		BufferedReader reader = null;
		BaseResponseDto baseResponseDto = null;
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		    MultipartFile multipartFile = multipartRequest.getFile("fileUpload"+iccid); // 这个file要与fileElementId一致
		    String fileName = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
		    logger.info("文件后缀名为  >>>" + fileName);
		    if (!".asn".equals(fileName)) {
		    	flag = 2;
		    }else {
		    	InputStream inputStream = multipartFile.getInputStream();
		    	reader = new BufferedReader(new InputStreamReader(inputStream));
		    	String der = new BaseHandler().handler(reader);
		    	String asn = IOUtils.toString(multipartFile.getInputStream(), "UTF-8");
		    	logger.info("asn file value  >>" + asn);
		    	logger.info("der file value  >>" + der);

		    	EuiccProfileWithBLOBs record = new EuiccProfileWithBLOBs();
		    	record.setIccid(iccid);
		    	record.setAsnFile(asn);
		    	record.setDerFile(der);
		    	logger.info("record value >>" + record);

		    	flag = profileService.updateByPrimaryKeySelective(record);
		    	logger.info("flag value >>" + flag);
		    	baseLogsService.insertSelective(LogTables.getOperateLog(
		    			currentUser, "1", Constant.EUICC_BASE_TYPE, "BASE_MODULES",
		    			getObjectData(record), "", "菜单管理", "编辑"));
		    	
		    }
		} catch (Exception e2) {
			flag = 0;
			if(reader != null)reader.close();
			e2.printStackTrace();
		} 
		if(flag == 0){
			baseResponseDto = new BaseResponseDto(false, "上传失败");
		}else if (flag == 1) {
			baseResponseDto = new BaseResponseDto(true, "上传成功");
		}else if (flag == 2) {
			baseResponseDto = new BaseResponseDto(false, "请选择后缀名为asn的文件");
		}
        logger.info("import asn end");
        writeJSONResult(baseResponseDto, response);
		
	}


	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(HttpServletRequest request, HttpServletResponse response,
			EuiccProfileWithBLOBs profile, String tag) throws IOException {
		int flag = 0;
		BaseUsers currentUser = (BaseUsers) SecurityUtils.getSubject()
				.getSession().getAttribute(
						Constant.BaseUsersConstant.CURRENTUSER);
		BaseResponseDto baseResponseDto = null;
		if ((Constant.SAVESTATUSADD).equals(tag)) {
			String iccid = profile.getIccid();
			PageBounds pageBounds = new PageBounds();
			EuiccProfileExample example = new EuiccProfileExample();
			Criteria criteria = example.createCriteria();
			criteria.andIccidEqualTo(iccid);
			PageList<EuiccProfileWithBLOBs> PWBList = profileService
					.selectByExampleWithBLOBs(example, pageBounds);
			if (PWBList.size() > 0) {
				baseResponseDto = new BaseResponseDto(false, "profileID不能重复");
				writeJSONResult(baseResponseDto, response);
				return;
			}
			String fallBackStr = profile.getFallbackAttribute();
			if (StringUtils.equals(fallBackStr, "00")) {
				if (checkFallBack(fallBackStr)) {
					baseResponseDto = new BaseResponseDto(false, "只能有一个profile具备fallback属性");
					writeJSONResult(baseResponseDto, response);
					return;
				}
			}
			
			flag = profileService.insertSelective(profile);
			baseResponseDto = flag > 0 ? new BaseResponseDto(true, "新建profile成功")
					: new BaseResponseDto(false, "新建profile失败，请联系管理员");
			baseLogsService.insertSelective(LogTables.getOperateLog(
					currentUser, "0", Constant.EUICC_BASE_TYPE, "BASE_MODULES",
					getObjectData(profile), "", "profile管理", "新建"));

		} else {
			flag = profileService.updateByPrimaryKeySelective(profile);
			baseResponseDto = flag > 0 ? new BaseResponseDto(true, "编辑profile成功")
					: new BaseResponseDto(false, "编辑profile失败，请联系管理员");
			List<String> list = new ArrayList<String>();
			baseLogsService.insertSelective(LogTables.getOperateLog(
					currentUser, "1", Constant.EUICC_BASE_TYPE, "BASE_MODULES",
					getObjectData(profile), "", "菜单管理", "编辑"));
		}
		writeJSONResult(baseResponseDto, response);

	}
	private boolean checkFallBack(String fallbackStr) {
		boolean exeCode = false;
		PageBounds pageBounds = new PageBounds();
		EuiccProfileExample example = new EuiccProfileExample();
		Criteria criteria = example.createCriteria();
		criteria.andIccidEqualTo(fallbackStr);
		PageList<EuiccProfileWithBLOBs> PWBList = profileService
				.selectByExampleWithBLOBs(example, pageBounds);
		if (PWBList.size() > 0) {
			exeCode = true;
		}
		return exeCode;
	}

	private String getObjectData(EuiccProfileWithBLOBs profile) {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("ProfileWithBLOBs[");
		sBuffer.append("iccid=");
		sBuffer.append(profile.getIccid());
		sBuffer.append(", ");
		sBuffer.append("isdPAid=");
		sBuffer.append(profile.getIsdPAid());
		sBuffer.append(", ");
		sBuffer.append("mnoId=");
		sBuffer.append(profile.getMnoId());
		sBuffer.append(", ");
		sBuffer.append("fallbackAttribute=");
		sBuffer.append(profile.getFallbackAttribute());
		sBuffer.append(", ");
		sBuffer.append(", ");
		sBuffer.append("state=");
		sBuffer.append(profile.getState());
		sBuffer.append(", ");
		sBuffer.append("smdpId=");
		sBuffer.append(profile.getSmdpId());
		sBuffer.append(", ");
		sBuffer.append("profileType=");
		sBuffer.append(profile.getProfileType());
		sBuffer.append(", ");
		sBuffer.append("allocatedMemory=");
		sBuffer.append(profile.getAllocatedMemory());
		sBuffer.append(", ");
		sBuffer.append("freeMemory=");
		sBuffer.append(profile.getFreeMemory());
		sBuffer.append(", ");
		sBuffer.append("pol2Id=");
		sBuffer.append(profile.getPol2Id());
		sBuffer.append("]");
		return sBuffer.toString();
	}

}
