package com.whty.euicc.data.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.common.base.BaseController;
import com.whty.euicc.common.base.DataTableQuery;
import com.whty.euicc.common.utils.CheckEmpty;
import com.whty.euicc.common.utils.DateUtil;
import com.whty.euicc.data.pojo.EuiccPol2;
import com.whty.euicc.data.pojo.EuiccPol2Example;
import com.whty.euicc.data.service.EuiccPol2Service;


/**
 * @author dzmsoft
 * @date 2016-08-03 11:27
 *
 * @version 1.0
 */
@Controller
@RequestMapping("/pol2")
public class Pol2Controller extends BaseController{
	@Autowired
	EuiccPol2Service euiccPol2Service;
	
	/**
	 * 查询
	 * @dzmsoftgenerated 2016-09-29 14:57
	 * @return
	 */
	@RequestMapping(value = "/find")
	public void find(HttpServletRequest request,HttpServletResponse response, EuiccPol2 euiccPol2)
			throws IOException {
		DataTableQuery dt = new DataTableQuery(request);
		Map<String,Object> result = buildTableData(dt, euiccPol2);
		writeJSONResult(result, response,DateUtil.yyyy_MM_dd_HH_mm_EN);
	}
	
	/**
	 * 构建数据树
	 * @dzmsoftgenerated 2016-08-03 11:27
	 * @param length
	 * @param start
	 * @param draw
	 * @param modules
	 * @return
	 * @throws IOException
	 */
	private Map<String,Object> buildTableData(DataTableQuery dt,EuiccPol2 euiccPol2) throws IOException {
		// 当前页数
		int currentNumber = dt.getPageStart() / dt.getPageLength() + 1;
		PageBounds pageBounds = new PageBounds(currentNumber, dt.getPageLength());
		EuiccPol2Example example = buildExample(dt, euiccPol2);
		PageList<EuiccPol2> euiccPol2s = euiccPol2Service.selectByExample(example, pageBounds);
		Map<String,Object> records = new HashMap<String,Object>();
		records.put("data", euiccPol2s);
		records.put("draw", dt.getPageDraw());
		records.put("recordsTotal", euiccPol2s.getPaginator().getTotalCount());
		records.put("recordsFiltered", euiccPol2s.getPaginator().getTotalCount());
		return records;
	}
	
	/**
	 * 构建列表查询条件
	 * @dzmsoftgenerated 2016-09-29 15:08
	 * @param 
	 * @return
	 */
	private EuiccPol2Example buildExample(DataTableQuery dt,EuiccPol2 euiccPol2) {
		EuiccPol2Example euiccPol2Example = new EuiccPol2Example();
//		EuiccPol2Example.Criteria criteria = euiccPol2Example.createCriteria();
//		// 查询条件
//		if (CheckEmpty.isNotEmpty(profileMgr.getEid())) {
//			criteria.andEidEqualTo(profileMgr.getEid());
//		}
		// 排序条件
		if (CheckEmpty.isNotEmpty(dt.getOrderBy()) && CheckEmpty.isNotEmpty(dt.getOrderParam())){
			// 
			StringBuilder orderByClause = new StringBuilder("");
			orderByClause.append(dt.getOrderParam()).append(" ").append(dt.getOrderBy());
			euiccPol2Example.setOrderByClause(orderByClause.toString());
		}
		return euiccPol2Example;
	}

}
