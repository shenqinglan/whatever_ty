package com.whty.euicc.base.service;

import java.util.List;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.base.pojo.BaseFields;
import com.whty.euicc.base.pojo.BaseFieldsExample;

public interface BaseFieldsService {
	
	/**
	 * @author dengzm
	 * @since 2015-4-18
	 * 根据数据字段的类型查询商数据字段
	 * @return
	 */
	List<BaseFields> selectEnabledByEnFieldName(String enFieldName);
	
	/**
	 * 保存字段信息
	 * @param baseFields
	 * @return
	 */
	int insertSelective(BaseFields baseFields);
	
	/**
	 * 根据主键查询字段信息
	 * @param fieldId 字段信息表主键
	 * @return
	 */
	BaseFields selectByPrimaryKey(String fieldId);
	
	/**
	 * 根据条件查询字段信息
	 * @param example 条件参数map
	 * @return
	 */
	PageList<BaseFields> selectByExample(BaseFieldsExample example, PageBounds pageBounds);
	
	/**
	 * 根据主键修改传入的字段信息
	 * @param baseFields
	 * @return 0：失败 ， 1：成功
	 */
	int updateSelectiveByPrimaryKey(BaseFields baseFields);
	
	/**
	 * 根据条件修改传入的字段信息
	 * @param record 
	 * @param example
	 * @return
	 */
	int updateSelectiveByRecord(BaseFields record,
                                BaseFieldsExample example);
	
	/**
	 * 根据主键删除字段信息
	 * @param fieldId
	 * @return 0：删除失败 ， 1：删除成功
	 */
	int deleteByPrimaryKey(String fieldId);
	
	/**
	 * 根据条件删除字段信息
	 * @param example
	 * @return
	 */
	int deleteByExample(BaseFieldsExample example);
	
	/**
	 * 根据flag选择发送短信内容
	 * @param flag 
	 * smsPre:短信验证码前缀           activateSuccess：审核通过激活成功
	 * checkfailPre：审核不通过前缀         checkfailPost：审核不通过后缀
	 * @return
	 */
	String selectSmsContent(String flag);
	
}
