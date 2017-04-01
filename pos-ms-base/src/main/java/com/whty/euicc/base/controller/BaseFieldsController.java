package com.whty.euicc.base.controller;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.base.common.Constant;
import com.whty.euicc.base.common.LogTables;
import com.whty.euicc.base.dto.BaseFieldsListCondDto;
import com.whty.euicc.base.pojo.BaseFields;
import com.whty.euicc.base.pojo.BaseFieldsExample;
import com.whty.euicc.base.pojo.BaseFieldsExample.Criteria;
import com.whty.euicc.base.pojo.BaseUsers;
import com.whty.euicc.base.service.BaseFieldsService;
import com.whty.euicc.base.service.BaseLogsService;
import com.whty.euicc.common.base.BaseResponseDto;
import com.whty.euicc.common.base.DataTableQuery;
import com.whty.euicc.common.utils.CheckEmpty;
import com.whty.euicc.common.utils.UUIDUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/baseFields")
public class BaseFieldsController extends BaseController {
	@Autowired
	BaseFieldsService baseFieldsService;

	@Autowired
	BaseLogsService bseLogServices;
	/**
	 * 显示主列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show() {
		return "/modules/base/baseFields/baseFieldsUI";
	}

	/**
	 * 查看APK详情 打开详情页面，无论是编辑还是查看详情，思路是一致的
	 * 
	 * @param response
	 * @param fieldId
	 */
	@RequestMapping(value = "/view", method = RequestMethod.POST)
	@ResponseBody
	public void view(HttpServletResponse response, String fieldId) {
		BaseFields baseFields = baseFieldsService.selectByPrimaryKey(fieldId);
		writeJSONResult(baseFields, response);
	}

	/**
	 * 查询
	 */
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	@ResponseBody
	public void find(HttpServletResponse response, HttpServletRequest request,BaseFieldsListCondDto baseFieldsListCondDto) {
		DataTableQuery dt = new DataTableQuery(request);
		Map<String,Object> result = buildTableData(dt, baseFieldsListCondDto);
		writeJSONResult(result, response);
	}

	/**
	 * 构建列表数据
	 * 
	 * @return
	 */
	private Map<String, Object> buildTableData(DataTableQuery dt,
			BaseFieldsListCondDto baseFieldsListCondDto) {
		int currentNumber = dt.getPageStart() / dt.getPageLength() + 1;
		PageBounds pageBounds = new PageBounds(currentNumber, dt.getPageLength());
		BaseFieldsExample example = buildExample(dt,baseFieldsListCondDto);
		PageList<BaseFields> baseFields = baseFieldsService.selectByExample(
				example, pageBounds);
		Map<String, Object> records = new HashMap<String, Object>();
		records.put("data", baseFields);
		records.put("recordsTotal", baseFields.getPaginator().getTotalCount());
		records.put("recordsFiltered", baseFields.getPaginator()
				.getTotalCount());
		//
		return records;
	}

	/**
	 * 构建列表查询条件
	 * 
	 * @param baseFieldsListCondDto
	 * @return
	 */
	private BaseFieldsExample buildExample(DataTableQuery dt,
			BaseFieldsListCondDto baseFieldsListCondDto) {
		BaseFieldsExample baseFieldsExample = new BaseFieldsExample();
		Criteria criteria = baseFieldsExample.createCriteria();
		if (CheckEmpty.isNotEmpty(baseFieldsListCondDto.getFieldName())) {
			criteria.andFieldNameLike("%"
					+ baseFieldsListCondDto.getFieldName() + "%");
		}
		// 排序条件
		if (CheckEmpty.isNotEmpty(dt.getOrderBy()) && CheckEmpty.isNotEmpty(dt.getOrderParam())){
			// 
			StringBuilder orderByClause = new StringBuilder("");
			orderByClause.append(dt.getOrderParam()).append(" ").append(dt.getOrderBy());
			//orderByClause.append("convert(").append(dt.getOrderParam()).append(" USING gbk) COLLATE gbk_chinese_ci  ").append(dt.getOrderBy());
			baseFieldsExample.setOrderByClause(orderByClause.toString());
		}
		return baseFieldsExample;
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public void save(BaseFields baseFields, HttpServletResponse response,
			String tag) {
		BaseFieldsExample example = new BaseFieldsExample();
		Criteria criteria = example.createCriteria();
		criteria.andFieldEqualTo(baseFields.getField());
		criteria.andValueFieldEqualTo(baseFields.getValueField());
		// 查询数据库是否有 字段+字段值 相同的记录
		PageList<BaseFields> queryField = baseFieldsService.selectByExample(
				example, new PageBounds());
		// 新增 则直接判定是否有相同的记录
		if (Constant.SAVESTATUSADD.equals(tag)) {
			if (!queryField.isEmpty()) {
				writeJSONResult(new BaseResponseDto(false, "字段+字段值不能重复!"),
						response);
			} else {
				if (CheckEmpty.isEmpty(baseFields.getFieldId())) {
					baseFields.setFieldId(UUIDUtil.getUuidString());
					baseFieldsService.insertSelective(baseFields);
//					saveLog( com.whty.euicc.base.common.Constant.EUICC_BASE_TYPE,"0",baseFields.toString(),null);
					Subject subjuct = SecurityUtils.getSubject();
					BaseUsers  currentUser= (BaseUsers) subjuct.getSession().getAttribute(Constant.BaseUsersConstant.CURRENTUSER);
					bseLogServices.insertSelective(LogTables.getOperateLog(
		                    currentUser, "0", Constant.POS_BASE_TYPE,
		                    "BASE_FIELDS", getObjectData(baseFields), "", "数据字典",
		                    "新建"));
					writeJSONResult(new BaseResponseDto(true, "新增数据字典成功"),response);
				}
			}
		} else if (Constant.SAVESTATUSUPDATE.equals(tag)) { // 编辑时，如果字段+字段值没有变化
															// 编辑为成功；如果改动字段或者字段值后，与字段+字段值与其他记录相同
															// 则编辑失败
			if (!queryField.isEmpty()) {
				BaseFieldsExample example1 = new BaseFieldsExample();
				Criteria criteria1 = example1.createCriteria();
				criteria1.andFieldEqualTo(baseFields.getField());
				PageList<BaseFields> queryExist = baseFieldsService
						.selectByExample(example, new PageBounds());
				if (!CollectionUtils.isEmpty(queryExist)) {
					String fieldId = queryExist.get(0).getFieldId();
					if (!fieldId.equals(baseFields.getFieldId())) {
						writeJSONResult(new BaseResponseDto(false,
								"字段+字段值不能重复!"), response);
					} else {
						baseFieldsService
								.updateSelectiveByPrimaryKey(baseFields);
//						saveLog( com.whty.euicc.base.common.Constant.EUICC_BASE_TYPE,"1",baseFields.toString(),null);
						Subject subjuct = SecurityUtils.getSubject();
						BaseUsers  currentUser= (BaseUsers) subjuct.getSession().getAttribute(Constant.BaseUsersConstant.CURRENTUSER);
						bseLogServices.insertSelective(LogTables.getOperateLog(
			                    currentUser, "1", Constant.POS_BASE_TYPE,
			                    "BASE_FIELDS", getObjectData(baseFields), "", "数据字典",
			                    "编辑"));
						writeJSONResult(new BaseResponseDto(true, "编辑数据字典成功"),
								response);
					}
				}
			} else {
				baseFieldsService.updateSelectiveByPrimaryKey(baseFields);
//				saveLog( com.whty.euicc.base.common.Constant.EUICC_BASE_TYPE,"1",baseFields.toString(),null);
				Subject subjuct = SecurityUtils.getSubject();
				BaseUsers  currentUser= (BaseUsers) subjuct.getSession().getAttribute(Constant.BaseUsersConstant.CURRENTUSER);
				bseLogServices.insertSelective(LogTables.getOperateLog(
	                    currentUser, "0", Constant.POS_BASE_TYPE,
	                    "BASE_FIELDS", getObjectData(baseFieldsService.selectByPrimaryKey(baseFields.getFieldId())), "", "数据字典",
	                    "编辑"));
				writeJSONResult(new BaseResponseDto(true, "编辑数据字典成功"), response);
			}
		}
	}

	private String getObjectData(BaseFields basefield) {
		StringBuffer sBuffer = new StringBuffer();
        sBuffer.append("BaseField[");
        sBuffer.append("FieldName=");
        sBuffer.append(basefield.getFieldName());
        sBuffer.append(", ");
        sBuffer.append("DisplayField=");
        sBuffer.append(basefield.getDisplayField());
        sBuffer.append(", ");
        sBuffer.append("ValueField=");
        sBuffer.append(basefield.getValueField());
        sBuffer.append(",");
        sBuffer.append("Field=");
        sBuffer.append(basefield.getField());
        sBuffer.append(",");
        sBuffer.append("Sort=");
        sBuffer.append(basefield.getSort());
        sBuffer.append(",");
        sBuffer.append("Enabled=");
        sBuffer.append(basefield.getEnabled());
        sBuffer.append(",");
        sBuffer.append("]");
        return sBuffer.toString();
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public void delete(String fieldId, HttpServletResponse response) {
		BaseFields baseFields=baseFieldsService.selectByPrimaryKey(fieldId);
		baseFieldsService.deleteByPrimaryKey(fieldId);
//		saveLog( com.whty.euicc.base.common.Constant.EUICC_BASE_TYPE,"1","删除数据字典,数据字典【fieldId="+fieldId+"】",null);
		Subject subjuct = SecurityUtils.getSubject();
		BaseUsers  currentUser= (BaseUsers) subjuct.getSession().getAttribute(Constant.BaseUsersConstant.CURRENTUSER);
		bseLogServices.insertSelective(LogTables.getOperateLog(
                currentUser, "2", Constant.POS_BASE_TYPE,
                "BASE_FIELDS", getObjectData(baseFields), "", "数据字典",
                "删除"));
		writeJSONResult(new BaseResponseDto(true), response);
	}
	
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "/getFields", method = RequestMethod.POST)
	@ResponseBody
	public void getFields(String fieldType, HttpServletResponse response) {
		List<BaseFields> list=baseFieldsService.selectEnabledByEnFieldName(fieldType);
		writeJSONResult(list, response);
	}
	
}
