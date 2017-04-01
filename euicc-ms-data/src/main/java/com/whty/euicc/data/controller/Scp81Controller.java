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
import com.whty.euicc.data.pojo.Scp81Info;
import com.whty.euicc.data.service.EuiccCardService;
@Controller
@RequestMapping("/euiccCard")
public class Scp81Controller extends BaseController{
	@Autowired
	EuiccCardService euiccCardService;

	@Autowired
	private BaseLogsService baseLogsService;
	
	private String scp81Eid;
	
	/**
	 * 查询
	 * 
	 * @dzmsoftgenerated 2016-05-30 15:37
	 * @return
	 */
	@RequestMapping(value = "/findScp81")
	public void findScp81(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DataTableQuery dt = new DataTableQuery(request);
		Map<String, Object> result = buildScp81TableData(dt);
		writeJSONResult(result, response, DateUtil.yyyy_MM_dd_HH_mm_EN);
	}
	
	/**
	 * 构建数据树
	 * @param dt
	 * @return
	 * @throws IOException
	 */
	private Map<String, Object> buildScp81TableData(DataTableQuery dt) throws IOException {
		// 当前页数
		int currentNumber = dt.getPageStart() / dt.getPageLength() + 1;
		PageBounds pageBounds = new PageBounds(currentNumber,
				dt.getPageLength());
		EuiccCardExample example = buildScp81Example(dt);
		PageList<Scp81Info> scp81InfoList = euiccCardService.selectScp81ByExample(
				example, pageBounds);
		Map<String, Object> records = new HashMap<String, Object>();
		records.put("data", scp81InfoList);
		records.put("draw", dt.getPageDraw());
		records.put("recordsTotal", scp81InfoList.getPaginator().getTotalCount());
		records.put("recordsFiltered", scp81InfoList.getPaginator()
				.getTotalCount());
		return records;
	}
	
	/**
	 * 构建表查询条件
	 * @param dt
	 * @return
	 */
	private EuiccCardExample buildScp81Example(DataTableQuery dt) {
		EuiccCardExample euiccCardExample = new EuiccCardExample();
		EuiccCardExample.Criteria criteria = euiccCardExample.createCriteria();
		// 查询条件
		if (CheckEmpty.isNotEmpty(scp81Eid)) {
			criteria.andEidEqualTo(scp81Eid);
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
	 * @param scp81Id
	 * @throws IOException
	 */
	@RequestMapping(value = "/scp81View", method = RequestMethod.POST)
	public void scp81View(HttpServletRequest request, HttpServletResponse response,
			String scp81Id) throws IOException {
		Scp81Info scp81Info = euiccCardService.selectScp81ByPrimaryKey(scp81Id);
		writeJSONResult(scp81Info, response);
	}
	
	/**
	 * 返回scp81Eid
	 * @param request
	 * @param response
	 * @param eid
	 * @throws IOException
	 */
	@RequestMapping(value = "/showScp81", method = RequestMethod.POST)
	public void showScp81(HttpServletRequest request, HttpServletResponse response,
			String eid) throws IOException {
		scp81Eid = eid;
		writeJSONResult(scp81Eid, response);
	}
	
	/**
	 * 保存Scp81
	 */
	@RequestMapping(value = "/saveScp81", method = RequestMethod.POST)
	@ResponseBody
	public void saveScp81(Scp81Info scp81Info, HttpServletResponse response,
			String scp81Tag) {		
		if (Constant.SAVESTATUSADD.equals(scp81Tag)) {
			euiccCardService.insertScp81Selective(scp81Info);
			writeJSONResult(new BaseResponseDto(true, "新增Scp81成功"), response);
		} else if (Constant.SAVESTATUSUPDATE.equals(scp81Tag)) {
			euiccCardService.updateScp81Selective(scp81Info);			
			writeJSONResult(new BaseResponseDto(true, "编辑Scp81成功"), response);
		}
	}
	
	

}
