package com.whty.euicc.data.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.base.common.Constant;
import com.whty.euicc.base.service.BaseLogsService;
import com.whty.euicc.common.base.BaseController;
import com.whty.euicc.common.base.BaseResponseDto;
import com.whty.euicc.common.base.DataTableQuery;
import com.whty.euicc.common.utils.CheckEmpty;
import com.whty.euicc.common.utils.DateUtil;
import com.whty.euicc.data.pojo.EuiccCardExample;
import com.whty.euicc.data.pojo.IsdPInfo;
import com.whty.euicc.data.service.EuiccCardService;
@Controller
@RequestMapping("/euiccCard")
public class IsdpController extends BaseController {
	@Autowired
	EuiccCardService euiccCardService;

	@Autowired
	private BaseLogsService baseLogsService;
	
	private String isdpEid;
	/**
	 * 查询
	 * 
	 * @dzmsoftgenerated 2016-08-11
	 * @return
	 */
	@RequestMapping(value = "/findIsdp")
	public void findIsdp(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DataTableQuery dt = new DataTableQuery(request);
		Map<String, Object> result = buildIsdpTableData(dt);
		writeJSONResult(result, response, DateUtil.yyyy_MM_dd_HH_mm_EN);
	}
	
	/**
	 * 构建数据树
	 * @param dt
	 * @return
	 * @throws IOException
	 */
	private Map<String, Object> buildIsdpTableData(DataTableQuery dt) throws IOException {
		// 当前页数
		int currentNumber = dt.getPageStart() / dt.getPageLength() + 1;
		PageBounds pageBounds = new PageBounds(currentNumber,
				dt.getPageLength());
		EuiccCardExample example = buildIsdpExample(dt);
		PageList<IsdPInfo> isdpInfoList = euiccCardService.selectIsdPByExample(
				example, pageBounds);
		Map<String, Object> records = new HashMap<String, Object>();
		records.put("data", isdpInfoList);
		records.put("draw", dt.getPageDraw());
		records.put("recordsTotal", isdpInfoList.getPaginator().getTotalCount());
		records.put("recordsFiltered", isdpInfoList.getPaginator()
				.getTotalCount());
		return records;
	}
	
	/**
	 * 构建表查询条件
	 * @param dt
	 * @return
	 */
	private EuiccCardExample buildIsdpExample(DataTableQuery dt) {
		EuiccCardExample euiccCardExample = new EuiccCardExample();
		EuiccCardExample.Criteria criteria = euiccCardExample.createCriteria();
		// 查询条件
		if (CheckEmpty.isNotEmpty(isdpEid)) {
			criteria.andEidEqualTo(isdpEid);
		}
		// // 排序条件
		// if (CheckEmpty.isNotEmpty(dt.getOrderBy()) &&
		// CheckEmpty.isNotEmpty(dt.getOrderParam())){
		// //
		// StringBuilder orderByClause = new StringBuilder("");
		// orderByClause.append(dt.getOrderParam()).append(" ").append(dt.getOrderBy());
		// euiccCardExample.setOrderByClause(orderByClause.toString());
		// }
		return euiccCardExample;
	}
	
	/**
	 * 查询scp81信息
	 * @param request
	 * @param response
	 * @param pId
	 * @throws IOException
	 */
	@RequestMapping(value = "/isdpView", method = RequestMethod.POST)
	public void isdpView(HttpServletRequest request, HttpServletResponse response,
			String pId) throws IOException {
		IsdPInfo isdpInfo = euiccCardService.selectIsdPByPrimaryKey(pId);
		writeJSONResult(isdpInfo, response);
	}
	
	/**
	 * 返回isdpEid
	 * @param request
	 * @param response
	 * @param eid
	 * @throws IOException
	 */
	@RequestMapping(value = "/showIsdp", method = RequestMethod.POST)
	public void showIsdp(HttpServletRequest request, HttpServletResponse response,
			String eid) throws IOException {
		isdpEid = eid;
		writeJSONResult(isdpEid, response);
	}
	
	/**
	 * 保存ISDP
	 */
	@RequestMapping(value = "/saveIsdp", method = RequestMethod.POST)
	@ResponseBody
	public void saveIsdp(IsdPInfo isdpInfo, HttpServletResponse response,
			String isdpTag) {		
		if (Constant.SAVESTATUSADD.equals(isdpTag)) {
			isdpInfo.setCreateDt(new Date());
			euiccCardService.insertIsdPSelective(isdpInfo);
			writeJSONResult(new BaseResponseDto(true, "新增Isdp成功"), response);
		} else if (Constant.SAVESTATUSUPDATE.equals(isdpTag)) {
			isdpInfo.setUpdateDt(new Date());
			euiccCardService.updateIsdPSelective(isdpInfo);			
			writeJSONResult(new BaseResponseDto(true, "编辑Isdp成功"), response);
		}
	}
	
}
