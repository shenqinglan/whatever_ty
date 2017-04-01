package com.whty.euicc.data.controller;

import java.util.HashMap;
import java.util.Map;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.whty.tsm.common.base.BaseController;
import com.whty.tsm.common.base.DataTableQuery;
import com.whty.tsm.common.utils.CheckEmpty;
import com.whty.tsm.common.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.data.pojo.CheckLogs;
import com.whty.euicc.data.pojo.CheckLogsExample;
import com.whty.euicc.data.service.CheckLogsService;


/**
 * @author dzmsoft
 * @date 2015-07-15 15:35
 *
 * @version 1.0
 */
@Controller
@RequestMapping("/checkLogs")
public class CheckLogsController extends BaseController{

	@Autowired
	CheckLogsService checkLogsService;	
	
	/**
	 * 显示主列表页面
	 * @dzmsoftgenerated 2015-07-15 15:35
	 * @return
	 */
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show() {
		return "modules/checkLogs/checkLogsUI";
	}
	
	/**
	 * 查询
	 * @dzmsoftgenerated 2015-07-15 15:35
	 * @return
	 */
	@RequestMapping(value = "/find")
	public void find(HttpServletRequest request,HttpServletResponse response, CheckLogs checkLogs)
			throws IOException {
		DataTableQuery dt = new DataTableQuery(request);
		Map<String,Object> result = buildTableData(dt, checkLogs);
		writeJSONResult(result, response,DateUtil.yyyy_MM_dd_HH_mm_ss_EN);
	}
	
	/**
	 * 构建数据树
	 * @dzmsoftgenerated 2015-07-15 15:35
	 * @param length
	 * @param start
	 * @param draw
	 * @param modules
	 * @return
	 * @throws IOException
	 */
	private Map<String,Object> buildTableData(DataTableQuery dt,CheckLogs checkLogs) throws IOException {
		// 当前页数
		int currentNumber = dt.getPageStart() / dt.getPageLength() + 1;
		PageBounds pageBounds = new PageBounds(currentNumber, dt.getPageLength());
		CheckLogsExample example = buildExample(dt, checkLogs);
		PageList<CheckLogs> checkLogss = checkLogsService.selectByExample(example, pageBounds);
		Map<String,Object> records = new HashMap<String,Object>();
		records.put("data", checkLogss);
		records.put("draw", dt.getPageDraw());
		records.put("recordsTotal", checkLogss.getPaginator().getTotalCount());
		records.put("recordsFiltered", checkLogss.getPaginator().getTotalCount());
		return records;
	}
	
	/**
	 * 构建列表查询条件
	 * @dzmsoftgenerated 2015-07-15 15:35
	 * @param 
	 * @return
	 */
	private CheckLogsExample buildExample(DataTableQuery dt,CheckLogs checkLogs) {
		CheckLogsExample checkLogsExample = new CheckLogsExample();
		// 排序条件
		if (CheckEmpty.isNotEmpty(dt.getOrderBy()) && CheckEmpty.isNotEmpty(dt.getOrderParam())){
			// 
			StringBuilder orderByClause = new StringBuilder("");
			orderByClause.append(dt.getOrderParam()).append(" ").append(dt.getOrderBy());
			checkLogsExample.setOrderByClause(orderByClause.toString());
		}
		return checkLogsExample;
	}
}
