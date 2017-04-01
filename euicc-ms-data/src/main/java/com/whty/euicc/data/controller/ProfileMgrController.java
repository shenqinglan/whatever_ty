package com.whty.euicc.data.controller;

import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.whty.euicc.base.service.BaseLogsService;
import com.whty.euicc.common.base.BaseController;
import com.whty.euicc.common.base.DataTableQuery;
import com.whty.euicc.common.utils.CheckEmpty;
import com.whty.euicc.common.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.data.pojo.ProfileMgr;
import com.whty.euicc.data.pojo.ProfileMgrExample;
import com.whty.euicc.data.service.EuiccCardService;
import com.whty.euicc.data.service.ProfileMgrService;


/**
 * @author dzmsoft
 * @date 2016-08-03 11:27
 *
 * @version 1.0
 */
@Controller
@RequestMapping("/profileMgr")
public class ProfileMgrController extends BaseController{
	@Autowired
	private BaseLogsService baseLogsService;
	
	@Autowired
	ProfileMgrService profileMgrService;
	
	@Autowired
	EuiccCardService cardService;
	
	/**
	 * 显示主列表页面
	 * @dzmsoftgenerated 2016-08-03 11:27
	 * @return
	 */
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show() {
		return "modules/profileMgr/profileMgrUI";
	}
	
	/**
	 * 查询
	 * @dzmsoftgenerated 2016-08-03 11:27
	 * @return
	 */
	@RequestMapping(value = "/find")
	public void find(HttpServletRequest request,HttpServletResponse response, ProfileMgr profileMgr)
			throws IOException {
		DataTableQuery dt = new DataTableQuery(request);
		Map<String,Object> result = buildTableData(dt, profileMgr);
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
	private Map<String,Object> buildTableData(DataTableQuery dt,ProfileMgr profileMgr) throws IOException {
		// 当前页数
		int currentNumber = dt.getPageStart() / dt.getPageLength() + 1;
		PageBounds pageBounds = new PageBounds(currentNumber, dt.getPageLength());
		ProfileMgrExample example = buildExample(dt, profileMgr);
		PageList<ProfileMgr> profileMgrs = profileMgrService.selectByExample(example, pageBounds);
		Map<String,Object> records = new HashMap<String,Object>();
		records.put("data", profileMgrs);
		records.put("draw", dt.getPageDraw());
		records.put("recordsTotal", profileMgrs.getPaginator().getTotalCount());
		records.put("recordsFiltered", profileMgrs.getPaginator().getTotalCount());
		return records;
	}
	
	/**
	 * 构建列表查询条件
	 * @dzmsoftgenerated 2016-08-03 11:27
	 * @param 
	 * @return
	 */
	private ProfileMgrExample buildExample(DataTableQuery dt,ProfileMgr profileMgr) {
		ProfileMgrExample profileMgrExample = new ProfileMgrExample();
		ProfileMgrExample.Criteria criteria = profileMgrExample.createCriteria();
		// 查询条件
		if (CheckEmpty.isNotEmpty(profileMgr.getEid())) {
			criteria.andEidLike(profileMgr.getEid());
		}
		// 排序条件
		if (CheckEmpty.isNotEmpty(dt.getOrderBy()) && CheckEmpty.isNotEmpty(dt.getOrderParam())){
			// 
			StringBuilder orderByClause = new StringBuilder("");
			orderByClause.append(dt.getOrderParam()).append(" ").append(dt.getOrderBy());
			profileMgrExample.setOrderByClause(orderByClause.toString());
		}
		return profileMgrExample;
	}

}
