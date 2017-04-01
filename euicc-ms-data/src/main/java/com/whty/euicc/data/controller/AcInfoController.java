package com.whty.euicc.data.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.base.common.Constant;
import com.whty.euicc.base.common.LogTables;
import com.whty.euicc.base.pojo.BaseFields;
import com.whty.euicc.base.pojo.BaseFieldsExample;
import com.whty.euicc.base.pojo.BaseUsers;
import com.whty.euicc.base.pojo.ApInfoExample.Criteria;
import com.whty.euicc.base.service.BaseLogsService;
import com.whty.euicc.common.base.BaseController;
import com.whty.euicc.common.base.BaseResponseDto;
import com.whty.euicc.common.base.DataTableQuery;
import com.whty.euicc.common.utils.CheckEmpty;
import com.whty.euicc.common.utils.DateUtil;
import com.whty.euicc.data.pojo.AcInfo;
import com.whty.euicc.data.pojo.AcInfoExample;
import com.whty.euicc.data.service.AcInfoService;


/**
 * @author dzmsoft
 * @date 2016-05-30 15:37
 *
 * @version 1.0
 */
@Controller
@RequestMapping("/acInfo")
public class AcInfoController extends BaseController{

	@Autowired
	AcInfoService acInfoService;	
	
	@Autowired
	private BaseLogsService baseLogsService;
	
	/**
	 * 显示主列表页面
	 * @dzmsoftgenerated 2016-05-30 15:37
	 * @return
	 */
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show() {
		return "modules/acInfo/acInfoUI";
	}
	
	/**
	 * 查询
	 * @dzmsoftgenerated 2016-05-30 15:37
	 * @return
	 */
	@RequestMapping(value = "/find")
	public void find(HttpServletRequest request,HttpServletResponse response, AcInfo acInfo)
			throws IOException {
		DataTableQuery dt = new DataTableQuery(request);
		Map<String,Object> result = buildTableData(dt, acInfo);
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
	private Map<String,Object> buildTableData(DataTableQuery dt,AcInfo acInfo) throws IOException {
		// 当前页数
		int currentNumber = dt.getPageStart() / dt.getPageLength() + 1;
		PageBounds pageBounds = new PageBounds(currentNumber, dt.getPageLength());
		AcInfoExample example = buildExample(dt, acInfo);
		PageList<AcInfo> acInfos = acInfoService.selectByExample(example, pageBounds);
		Map<String,Object> records = new HashMap<String,Object>();
		records.put("data", acInfos);
		records.put("draw", dt.getPageDraw());
		records.put("recordsTotal", acInfos.getPaginator().getTotalCount());
		records.put("recordsFiltered", acInfos.getPaginator().getTotalCount());
		return records;
	}
	
	/**
	 * 构建列表查询条件
	 * @dzmsoftgenerated 2016-05-30 15:37
	 * @param 
	 * @return
	 */
	private AcInfoExample buildExample(DataTableQuery dt,AcInfo acInfo) {
		AcInfoExample acInfoExample = new AcInfoExample();
		AcInfoExample.Criteria criteria = acInfoExample.createCriteria();
		// 查询条件
		if (CheckEmpty.isNotEmpty(acInfo.getApdu())) {
			criteria.andApduLike("%" + acInfo.getApdu() + "%");
		}
		// 排序条件
		if (CheckEmpty.isNotEmpty(dt.getOrderBy()) && CheckEmpty.isNotEmpty(dt.getOrderParam())){
			// 
			StringBuilder orderByClause = new StringBuilder("");
			orderByClause.append(dt.getOrderParam()).append(" ").append(dt.getOrderBy());
			acInfoExample.setOrderByClause(orderByClause.toString());
		}
		return acInfoExample;
	}
	
	/**
	 * 通过主键查询
	 * 
	 * @param request
	 * @param response
	 * @param moduleId
	 * @throws java.io.IOException
	 */
	@RequestMapping(value = "/view", method = RequestMethod.POST)
	public void view(HttpServletRequest request, HttpServletResponse response,
			Integer id) throws IOException {
		AcInfo acInfo = acInfoService.selectByPrimaryKey(id);
		writeJSONResult(acInfo, response);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public void save(AcInfo acInfo, HttpServletResponse response,
			String tag) {
		Subject subjuct = SecurityUtils.getSubject();
		BaseUsers  currentUser= (BaseUsers) subjuct.getSession().getAttribute(Constant.BaseUsersConstant.CURRENTUSER);
		if (Constant.SAVESTATUSADD.equals(tag)) {
			acInfo.setId(new Random().nextInt());
			acInfoService.insertSelective(acInfo);
			baseLogsService.insertSelective(LogTables.getOperateLog(
                    currentUser, "0", Constant.EUICC_BASE_TYPE,
                    "BASE_FIELDS", getObjectData(acInfo), "", "示例",
                    "新建"));
			writeJSONResult(new BaseResponseDto(true, "新增示例成功"),response);
		} else if (Constant.SAVESTATUSUPDATE.equals(tag)) { 
			acInfoService.updateByPrimaryKeySelective(acInfo);
			baseLogsService.insertSelective(LogTables.getOperateLog(
                    currentUser, "0", Constant.EUICC_BASE_TYPE,
                    "AC_INFO", getObjectData(acInfoService.selectByPrimaryKey(acInfo.getId())), "", "示例",
                    "编辑"));
			writeJSONResult(new BaseResponseDto(true, "编辑示例成功"), response);
		}
	}
	
	/**
	 * 删除菜单信息
	 * 
	 * @param request
	 * @param response
	 * @param moduleId
	 *            模块id
	 * @throws java.io.IOException
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void delete(HttpServletRequest request,
			HttpServletResponse response, Integer id) throws IOException {
		BaseUsers currentUser = (BaseUsers) SecurityUtils.getSubject()
				.getSession()
				.getAttribute(Constant.BaseUsersConstant.CURRENTUSER);
		AcInfo acInfo = acInfoService.selectByPrimaryKey(id);
		BaseResponseDto baseResponseDto = null;
		// 删除
		int flag = acInfoService.deleteByPrimaryKey(id);
		baseResponseDto = flag > 0 ? new BaseResponseDto(true, "删除成功")
				: new BaseResponseDto(false, "删除失败");
		baseLogsService.insertSelective(LogTables.getOperateLog(currentUser,
				"2", Constant.EUICC_BASE_TYPE, "AC_INFO",
				getObjectData(acInfo), "", "示例", "删除"));
		writeJSONResult(baseResponseDto, response);
	}

	private String getObjectData(AcInfo acInfo) {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("AcInfo[");
		sBuffer.append("id=");
		sBuffer.append(acInfo.getId());
		sBuffer.append(", ");
		sBuffer.append("apdu=");
		sBuffer.append(acInfo.getApdu());
		sBuffer.append("]");
		return sBuffer.toString();
	}
}
