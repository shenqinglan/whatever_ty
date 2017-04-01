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
import com.whty.euicc.data.pojo.Scp80Info;
import com.whty.euicc.data.service.EuiccCardService;
@Controller
@RequestMapping("/euiccCard")
public class Scp80Controller extends BaseController {
	
	@Autowired
	EuiccCardService euiccCardService;

	@Autowired
	private BaseLogsService baseLogsService;
	
	private String scp80Eid;
	
	/**
	 * 查询
	 * 
	 * @dzmsoftgenerated 2016-05-30 15:37
	 * @return
	 */
	@RequestMapping(value = "/findScp80")
	public void findScp80(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DataTableQuery dt = new DataTableQuery(request);
		Map<String, Object> result = buildScp80TableData(dt);
		writeJSONResult(result, response, DateUtil.yyyy_MM_dd_HH_mm_EN);
	}
	
	/**
	 * 构建数据树
	 * @param dt
	 * @return
	 * @throws IOException
	 */
	private Map<String, Object> buildScp80TableData(DataTableQuery dt) throws IOException {
		// 当前页数
		int currentNumber = dt.getPageStart() / dt.getPageLength() + 1;
		PageBounds pageBounds = new PageBounds(currentNumber,
				dt.getPageLength());
		EuiccCardExample example = buildScp80Example(dt);
		PageList<Scp80Info> scp80InfoList = euiccCardService.selectScp80ByExample(
				example, pageBounds);
		Map<String, Object> records = new HashMap<String, Object>();
		records.put("data", scp80InfoList);
		records.put("draw", dt.getPageDraw());
		records.put("recordsTotal", scp80InfoList.getPaginator().getTotalCount());
		records.put("recordsFiltered", scp80InfoList.getPaginator()
				.getTotalCount());
		return records;
	}
	
	/**
	 * 构建表查询条件
	 * @param dt
	 * @return
	 */
	private EuiccCardExample buildScp80Example(DataTableQuery dt) {
		EuiccCardExample euiccCardExample = new EuiccCardExample();
		EuiccCardExample.Criteria criteria = euiccCardExample.createCriteria();
		// 查询条件
		if (CheckEmpty.isNotEmpty(scp80Eid)) {
			criteria.andEidEqualTo(scp80Eid);
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
	 * 查询scp80信息
	 * @param request
	 * @param response
	 * @param scp80Id
	 * @throws IOException
	 */
	@RequestMapping(value = "/scp80View", method = RequestMethod.POST)
	public void scp80View(HttpServletRequest request, HttpServletResponse response,
			String scp80Id) throws IOException {
		Scp80Info scp80Info = euiccCardService.selectScp80ByPrimaryKey(scp80Id);
		writeJSONResult(scp80Info, response);
	}
	
	/**
	 * 返回scp80Eid
	 * @param request
	 * @param response
	 * @param eid
	 * @throws IOException
	 */
	@RequestMapping(value = "/showScp80", method = RequestMethod.POST)
	public void showScp80(HttpServletRequest request, HttpServletResponse response,
			String eid) throws IOException {
		scp80Eid = eid;
		writeJSONResult(scp80Eid, response);
	}
	
	/**
	 * 保存Scp80
	 */
	@RequestMapping(value = "/saveScp80", method = RequestMethod.POST)
	@ResponseBody
	public void saveScp80(Scp80Info scp80Info, HttpServletResponse response,
			String scp80Tag) {		
		if (Constant.SAVESTATUSADD.equals(scp80Tag)) {
			euiccCardService.insertScp80Selective(scp80Info);
			writeJSONResult(new BaseResponseDto(true, "新增Scp80成功"), response);
		} else if (Constant.SAVESTATUSUPDATE.equals(scp80Tag)) {
			euiccCardService.updateScp80Selective(scp80Info);			
			writeJSONResult(new BaseResponseDto(true, "编辑Scp80成功"), response);
		}
	}
	

}
