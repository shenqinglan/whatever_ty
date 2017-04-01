package com.whty.euicc.data.controller;

import java.io.IOException;
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
import com.whty.euicc.data.pojo.Scp03Info;
import com.whty.euicc.data.service.EuiccCardService;
@Controller
@RequestMapping("/euiccCard")
public class Scp03Controller extends BaseController {
	@Autowired
	EuiccCardService euiccCardService;

	@Autowired
	private BaseLogsService baseLogsService;
	
	private String scp03Eid;
	/**
	 * 查询
	 * 
	 * @dzmsoftgenerated 2016-05-30 15:37
	 * @return
	 */
	@RequestMapping(value = "/findScp03")
	public void findScp03(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DataTableQuery dt = new DataTableQuery(request);
		Map<String, Object> result = buildScp03TableData(dt);
		writeJSONResult(result, response, DateUtil.yyyy_MM_dd_HH_mm_EN);
	}
	
	/**
	 * 构建数据树
	 * @param dt
	 * @return
	 * @throws IOException
	 */
	private Map<String, Object> buildScp03TableData(DataTableQuery dt) throws IOException {
		// 当前页数
		int currentNumber = dt.getPageStart() / dt.getPageLength() + 1;
		PageBounds pageBounds = new PageBounds(currentNumber,
				dt.getPageLength());
		EuiccCardExample example = buildScp03Example(dt);
		PageList<Scp03Info> scp03InfoList = euiccCardService.selectScp03ByExample(
				example, pageBounds);
		Map<String, Object> records = new HashMap<String, Object>();
		records.put("data", scp03InfoList);
		records.put("draw", dt.getPageDraw());
		records.put("recordsTotal", scp03InfoList.getPaginator().getTotalCount());
		records.put("recordsFiltered", scp03InfoList.getPaginator()
				.getTotalCount());
		return records;
	}
	
	/**
	 * 构建表查询条件
	 * @param dt
	 * @return
	 */
	private EuiccCardExample buildScp03Example(DataTableQuery dt) {
		EuiccCardExample euiccCardExample = new EuiccCardExample();
		EuiccCardExample.Criteria criteria = euiccCardExample.createCriteria();
		// 查询条件
		if (CheckEmpty.isNotEmpty(scp03Eid)) {
			criteria.andEidEqualTo(scp03Eid);
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
	 * 查询scp03数据
	 * @param request
	 * @param response
	 * @param scp03Id
	 * @throws IOException
	 */
	@RequestMapping(value = "/scp03View", method = RequestMethod.POST)
	public void scp03View(HttpServletRequest request, HttpServletResponse response,
			String scp03Id) throws IOException {
		Scp03Info scp03Info = euiccCardService.selectScp03ByPrimaryKey(scp03Id);
		writeJSONResult(scp03Info, response);
	}
	
	/**
	 * 返回scp03Eid
	 * @param request
	 * @param response
	 * @param eid
	 * @throws IOException
	 */
	@RequestMapping(value = "/showScp03", method = RequestMethod.POST)
	public void showScp03(HttpServletRequest request, HttpServletResponse response,
			String eid) throws IOException {
		scp03Eid = eid;
		writeJSONResult(scp03Eid, response);
	}
	
	/**
	 * 保存Scp81
	 */
	@RequestMapping(value = "/saveScp03", method = RequestMethod.POST)
	@ResponseBody
	public void saveScp03(Scp03Info scp03Info, HttpServletResponse response,
			String scp03Tag) {		
		if (Constant.SAVESTATUSADD.equals(scp03Tag)) {
			euiccCardService.insertScp03Selective(scp03Info);
			writeJSONResult(new BaseResponseDto(true, "新增Scp03成功"), response);
		} else if (Constant.SAVESTATUSUPDATE.equals(scp03Tag)) {
			euiccCardService.updateScp03Selective(scp03Info);			
			writeJSONResult(new BaseResponseDto(true, "编辑Scp03成功"), response);
		}
	}
	
}
