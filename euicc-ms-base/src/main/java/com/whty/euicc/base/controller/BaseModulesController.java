package com.whty.euicc.base.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.base.common.Constant;
import com.whty.euicc.base.common.LogTables;
import com.whty.euicc.base.pojo.BaseModules;
import com.whty.euicc.base.pojo.BaseModulesExample;
import com.whty.euicc.base.pojo.BaseUsers;
import com.whty.euicc.base.pojo.BaseModulesExample.Criteria;
import com.whty.euicc.base.service.BaseLogsService;
import com.whty.euicc.base.service.BaseModulesService;

import com.whty.euicc.common.base.BaseResponseDto;
import com.whty.euicc.common.base.DataTableQuery;
import com.whty.euicc.common.utils.CheckEmpty;
import com.whty.euicc.common.utils.UUIDUtil;

/**
 * 菜单管理
 * 
 * @ClassName: BaseModulesController
 * @author liyang
 * @date 2015-4-15
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
@Controller
@RequestMapping("/baseModules")
public class BaseModulesController extends BaseController {

	@Autowired
	private BaseModulesService baseModulesService;

	@Autowired
	private BaseLogsService baseLogsService;

	@Autowired
	private BaseLogsService baseLogService;
	private static final Logger logger = LoggerFactory
			.getLogger(BaseModulesController.class);

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show() {
		return "/modules/base/baseModule/baseModuleUI";
	}

	/**
	 * 查询展示
	 * 
	 * @param request
	 * @param response
	 * @param modules
	 * @throws java.io.IOException
	 */
	@RequestMapping(value = "/find")
	public void find(HttpServletRequest request, HttpServletResponse response,
			BaseModules modules) throws IOException {
		DataTableQuery dt = new DataTableQuery(request);
		Map<String, Object> result = buildTableData(dt, modules);
		writeJSONResult(result, response);
	}

	private BaseModulesExample buildExample(DataTableQuery dt,
			BaseModules modules) {
		BaseModulesExample exmple = new BaseModulesExample();
		Criteria criteria = exmple.createCriteria();
		// 查询条件
		if (CheckEmpty.isNotEmpty(modules.getModuleName())) {
			criteria.andModuleNameLike("%" + modules.getModuleName() + "%");
		}
		if (CheckEmpty.isNotEmpty(modules.getModuleId())) {
			criteria.andModuleIdLike("%" + modules.getModuleId() + "%");
		}
		criteria.andLeafTypeNotEqualTo(Constant.LEAFTYPEBUTTON);
		// 排序条件
		if (CheckEmpty.isNotEmpty(dt.getOrderBy())
				&& CheckEmpty.isNotEmpty(dt.getOrderParam())) {
			//
			StringBuilder orderByClause = new StringBuilder("");
			orderByClause.append(dt.getOrderParam()).append(" ")
					.append(dt.getOrderBy());
			exmple.setOrderByClause(orderByClause.toString());
		}
		return exmple;
	}

	/**
	 * 构建数据树
	 * 
	 * @param length
	 * @param start
	 * @param draw
	 * @param modules
	 * @return
	 * @throws java.io.IOException
	 */
	private Map<String, Object> buildTableData(DataTableQuery dt,
			BaseModules modules) throws IOException {
		// 当前页数
		int currentNumber = dt.getPageStart() / dt.getPageLength() + 1;
		PageBounds pageBounds = new PageBounds(currentNumber,
				dt.getPageLength());
		BaseModulesExample exmple = buildExample(dt, modules);
		PageList<BaseModules> baseModules = baseModulesService.selectByExample(
				exmple, pageBounds);
		Map<String, Object> records = new HashMap<String, Object>();
		records.put("data", baseModules);
		records.put("draw", dt.getPageDraw());
		records.put("recordsTotal", baseModules.getPaginator().getTotalCount());
		records.put("recordsFiltered", baseModules.getPaginator()
				.getTotalCount());
		return records;
	}

	/**
	 * 新增/修改菜单信息
	 * 
	 * @param request
	 * @param response
	 * @param baseModules
	 * @param tag
	 *            判断是新增还是编辑
	 * @throws java.io.IOException
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(HttpServletRequest request, HttpServletResponse response,
			BaseModules baseModules, String tag) throws IOException {
		int flag = 0;
		BaseUsers currentUser = (BaseUsers) SecurityUtils.getSubject()
				.getSession()
				.getAttribute(Constant.BaseUsersConstant.CURRENTUSER);
		BaseResponseDto baseResponseDto = null;
		if ((Constant.SAVESTATUSADD).equals(tag)) {
			String moduleId = baseModules.getModuleId();
			// String
			PageBounds pageBounds = new PageBounds();
			BaseModulesExample example = new BaseModulesExample();
			Criteria criteria = example.createCriteria();
			criteria.andModuleIdEqualTo(moduleId);
			PageList<BaseModules> bmList = baseModulesService.selectByExample(
					example, pageBounds);
			if (bmList.size() > 0) {
				baseResponseDto = new BaseResponseDto(false, "菜单编码不能重复");
				writeJSONResult(baseResponseDto, response);
				return;
			}
			flag = baseModulesService.insertSelective(baseModules);
			baseResponseDto = flag > 0 ? new BaseResponseDto(true, "新建菜单成功")
					: new BaseResponseDto(false, "新建菜单失败，请联系管理员");
			List<String> list = new ArrayList<String>();
			list.add(moduleId);
			list.add(tag);
			baseLogsService.insertSelective(LogTables.getOperateLog(currentUser,
					"0", Constant.EUICC_BASE_TYPE, "BASE_MODULES",
					getObjectData(baseModules), "", "菜单管理", "新建"));
			// baseLogService.insertSelective(LogTable.getOperateLog());

		} else {
			flag = baseModulesService.updateSelectiveByPrimaryKey(baseModules);
			baseResponseDto = flag > 0 ? new BaseResponseDto(true, "编辑菜单成功")
					: new BaseResponseDto(false, "编辑菜单失败，请联系管理员");
			List<String> list = new ArrayList<String>();
			String moduleId = baseModules.getModuleId();
			list.add(moduleId);
			list.add(tag);
			baseLogsService.insertSelective(LogTables.getOperateLog(currentUser,
					"1", Constant.EUICC_BASE_TYPE, "BASE_MODULES",
					getObjectData(baseModules), "", "菜单管理", "编辑"));
		}
		writeJSONResult(baseResponseDto, response);
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
			HttpServletResponse response, String moduleId) throws IOException {
		BaseUsers currentUser = (BaseUsers) SecurityUtils.getSubject()
				.getSession()
				.getAttribute(Constant.BaseUsersConstant.CURRENTUSER);
		BaseModules modle = baseModulesService.selectByPrimaryKey(moduleId);
		BaseResponseDto baseResponseDto = null;
		// 删除
		int flag = baseModulesService.deleteByPrimaryKey(moduleId);
		baseResponseDto = flag > 0 ? new BaseResponseDto(true, "删除成功")
				: new BaseResponseDto(false, "删除失败");
		baseLogsService.insertSelective(LogTables.getOperateLog(currentUser,
				"2", Constant.EUICC_BASE_TYPE, "BASE_MODULES",
				getObjectData(modle), "", "菜单管理", "删除"));
		writeJSONResult(baseResponseDto, response);
	}

	/**
	 * 删除校验
	 * 
	 * @param moduleId
	 * @return
	 */
	private BaseResponseDto checkDelete(String moduleId) {
		// 判断当前删除的节点是否存在叶子节点
		BaseModulesExample example = new BaseModulesExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(moduleId);
		PageList<BaseModules> baseModules = baseModulesService.selectByExample(
				example, new PageBounds());
		if (!CollectionUtils.isEmpty(baseModules)) {
			return new BaseResponseDto(false, "当前菜单存在子节点，请核对后再删除");
		}
		return new BaseResponseDto(true);
	}

	/**
	 * 获取所有父节点列表
	 * 
	 * @param request
	 * @param response
	 * @param moduleId
	 */
	@RequestMapping(value = "/getParentList", method = RequestMethod.POST)
	public void getParentModuleList(HttpServletRequest request,
			HttpServletResponse response, String moduleId) {
		BaseModulesExample exmple = new BaseModulesExample();
		Criteria criteria = exmple.createCriteria();
		// 查询父级菜单 表示 "0"
		criteria.andLeafTypeEqualTo((short) 0);
		PageBounds pageBounds = new PageBounds();
		PageList<BaseModules> bms = baseModulesService.selectByExample(exmple,
				pageBounds);
		writeJSONArrayResult(bms, response);
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
			String moduleId) throws IOException {
		BaseModules baseModules = baseModulesService
				.selectByPrimaryKey(moduleId);
		writeJSONResult(baseModules, response);
	}

	public String getObjectData(BaseModules model) {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("BaseModules[");
		sBuffer.append("moduleId=");
		sBuffer.append(model.getModuleId());
		sBuffer.append(", ");
		sBuffer.append("moduleName=");
		sBuffer.append(model.getModuleName());
		sBuffer.append(", ");
		sBuffer.append("moduleUrl=");
		sBuffer.append(model.getModuleUrl());
		sBuffer.append(", ");
		sBuffer.append("parentId=");
		sBuffer.append(model.getParentId());
		sBuffer.append(", ");
		sBuffer.append("leafType=");
		sBuffer.append(model.getLeafType());
		sBuffer.append(", ");
		sBuffer.append("expanded=");
		sBuffer.append(model.getExpandedStatus());
		sBuffer.append(", ");
		sBuffer.append("displayIndex=");
		sBuffer.append(model.getDisplayIndex());
		sBuffer.append(", ");
		sBuffer.append("isDisplay=");
		sBuffer.append(model.getIsDisplay());
		sBuffer.append(", ");
		sBuffer.append("iconCss=");
		sBuffer.append(model.getIconCss());
		sBuffer.append(", ");
		sBuffer.append("information=");
		sBuffer.append(model.getInformation());
		sBuffer.append("]");
		return sBuffer.toString();
	}
}
