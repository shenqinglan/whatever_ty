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

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.common.base.BaseController;
import com.whty.euicc.common.base.DataTableQuery;
import com.whty.euicc.common.utils.CheckEmpty;
import com.whty.euicc.common.utils.DateUtil;
import com.whty.euicc.data.pojo.TerminalDeviceInfo;
import com.whty.euicc.data.pojo.TerminalDeviceInfoExample;
import com.whty.euicc.data.service.TerminalDeviceInfoService;


/**
 * @author dzmsoft
 * @date 2016-05-30 15:37
 *
 * @version 1.0
 */
@Controller
@RequestMapping("/deviceDisplay")
public class DeviceDisplayController extends BaseController{

	@Autowired
	TerminalDeviceInfoService terminalDeviceInfoService;
	
	/**
	 * 显示主列表页面
	 * @dzmsoftgenerated 2016-05-30 15:37
	 * @return
	 */
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show() {
		return "modules/deviceDisplay/deviceDisplayUI";
	}
	
	/**
	 * 查询
	 * @dzmsoftgenerated 2016-05-30 15:37
	 * @return
	 */
	@RequestMapping(value = "/find")
	public void find(HttpServletRequest request,HttpServletResponse response, TerminalDeviceInfo terminalDeviceInfo)
			throws IOException {
		DataTableQuery dt = new DataTableQuery(request);
		Map<String,Object> result = buildTableData(dt, terminalDeviceInfo);
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
	private Map<String,Object> buildTableData(DataTableQuery dt,TerminalDeviceInfo terminalDeviceInfo) throws IOException {
		// 当前页数
		int currentNumber = dt.getPageStart() / dt.getPageLength() + 1;
		PageBounds pageBounds = new PageBounds(currentNumber, dt.getPageLength());
		TerminalDeviceInfoExample example = buildExample(dt, terminalDeviceInfo);
		PageList<TerminalDeviceInfo> terminalDeviceInfos = terminalDeviceInfoService.selectByExample(example, pageBounds);
		Map<String,Object> records = new HashMap<String,Object>();
		records.put("data", terminalDeviceInfos);
		records.put("draw", dt.getPageDraw());
		records.put("recordsTotal", terminalDeviceInfos.getPaginator().getTotalCount());
		records.put("recordsFiltered", terminalDeviceInfos.getPaginator().getTotalCount());
		return records;
	}
	
	/**
	 * 构建列表查询条件
	 * @dzmsoftgenerated 2016-05-30 15:37
	 * @param 
	 * @return
	 */
	private TerminalDeviceInfoExample buildExample(DataTableQuery dt,TerminalDeviceInfo terminalDeviceInfo) {
		TerminalDeviceInfoExample terminalDeviceInfoExample = new TerminalDeviceInfoExample();
		TerminalDeviceInfoExample.Criteria criteria = terminalDeviceInfoExample.createCriteria();
		// 查询条件
		if (CheckEmpty.isNotEmpty(terminalDeviceInfo.getSn())) {
			criteria.andSnLike("%" + terminalDeviceInfo.getSn() + "%");
		}
		// 排序条件
		if (CheckEmpty.isNotEmpty(dt.getOrderBy()) && CheckEmpty.isNotEmpty(dt.getOrderParam())){
			// 
			StringBuilder orderByClause = new StringBuilder("");
			orderByClause.append(dt.getOrderParam()).append(" ").append(dt.getOrderBy());
			terminalDeviceInfoExample.setOrderByClause(orderByClause.toString());
		}
		return terminalDeviceInfoExample;
	}
}
