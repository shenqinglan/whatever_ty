// Copyright (C) 2012 WHTY
package com.whty.euicc.base.common;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.base.dto.LogsUser;
import com.whty.euicc.base.pojo.BaseLogs;
import com.whty.euicc.base.pojo.BaseUsers;
import com.whty.euicc.common.utils.CheckEmpty;
import com.whty.euicc.common.utils.Encryptor;


@Component
public class LogTables {

    /**
     * 存放条件查询值
     */
	private static Map<String, Object> map = new HashMap<String, Object>();
    /** 操作人 */
    private BaseUsers baseUser;
    /** 操作类型 */
    private String operateType;
    /** 表名 */
    private String tableName;
    /** 数据 */
    private String dataObject;
    /**
     * 条件
     */
    private String condition;
    /**
     * 业务菜单
     */
    private String menu;
    /**
     * 业务功能
     */
    private String menuAction;
    /**
     * 操作时间
     */
    private Date opDate;
    
    private static String isEncryptLog;
    
    private static String logKey3DES;
    
    @Value("${isEncryptLog}")
	private void setIsEncryptLog(String isEncryptLog){
		this.isEncryptLog = isEncryptLog;
	}
	
    
    @Value("${logKey3DES}")
	private void setLogKey3DES(String logKey3DES){
		this.logKey3DES = logKey3DES;
	}
	
    
    

    /** TSM_OP_APP_TYPE 操作类型 0：提交；1：审批通过；2：审核不通过；3：发布；4：暂停；5：下线 */
    private static Map<String, String> appStatusMap = new
            HashMap<String, String>();
    static {
        appStatusMap.put("0", "提交");
        appStatusMap.put("1", "审批通过");
        appStatusMap.put("2", "审核不通过");
        appStatusMap.put("3", "发布");
        appStatusMap.put("4", "暂停");
        appStatusMap.put("5", "下线");
    }
    /** TSM_OP_AP_TYPE 操作类型0：注册；1：审核通过；2：审核不通过；3：注销 */
    private static Map<String, String> apStatusMap = new
            HashMap<String, String>();
    static {
        apStatusMap.put("0", "注册");
        apStatusMap.put("1", "审核通过");
        apStatusMap.put("2", "审核不通过");
        apStatusMap.put("3", "注销");
    }
    /** TSM_OP_APPVER_TYPE 操作类型0：提交；1：发布；2：暂停；3：下线 */
    private static Map<String, String> appleteStatusMap = new
            HashMap<String, String>();
    static {
        appleteStatusMap.put("0", "提交");
        appleteStatusMap.put("1", "发布");
        appleteStatusMap.put("2", "暂停");
        appleteStatusMap.put("3", "下线");
    }
    /** TSM_CARD_TEST_TYPE 操作类型0：测试通过；1：测试不通过 */
    private static Map<String, String> cardTestStatusMap = new
            HashMap<String, String>();
    static {
        cardTestStatusMap.put("0", "测试通过");
        cardTestStatusMap.put("1", "测试不通过");
    }
    /** TSM_BASE_TYPE 操作类型0：新增；1：编辑；2：删除 */
    private static Map<String, String> baseMap = new
            HashMap<String, String>();
    static {
        baseMap.put("0", "新增");
        baseMap.put("1", "编辑");
        baseMap.put("2", "删除");
    }

    public LogTables() {

    }

    public LogTables(BaseUsers baseUser, String operateType, String tableName,
            String dataObject, String condition,
            String menu, String menuAction) {
        this.setBaseUser(baseUser);
        this.setOperateType(operateType);
        this.setTableName(tableName);
        this.setDataObject(dataObject);
        this.setCondition(condition);
        this.setMenu(menu);
        this.setOpDate(new Date());
        this.setMenuAction(menuAction);
    }

    /**
     * 把pojo字段转为数据库字段
     *
     * @param field
     * @return
     */
    public static String toClumn(String field) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < field.length(); i++) {
            char c = field.charAt(i);
            if (Character.isUpperCase(c) && i > 0) {
                sb.append("_").append(Character.toUpperCase(c));
            }
            else {
                sb.append(Character.toUpperCase(c));
            }
        }
        return sb.toString();
    }

    /**
     * 得到日志对象
     *
     * @param user
     *            用户对象
     * @param opType
     *            操作(类似于测试通过/测试不通过/审核通过等等)
     * @param objectId
     *            被操作对象ID
     * @param desc
     *            描述
     * @return
     */
    public static BaseLogs getBaseLog(BaseUsers user, String opType,
            String objectId, String type, String desc) {
        BaseLogs log = new BaseLogs();
        log.setOpType(opType);
        log.setObjectId(objectId);
        log.setRemark(desc);
        log.setUserId(user.getUserAccount());
        log.setOpDate(new Date());
        log.setType(type);
        return log;
    }
    
    /**
     * 得到日志对象
     *
     * @param user
     *            用户对象
     * @param opType
     *            操作(类似于测试通过/测试不通过/审核通过等等)
     * @param objectId
     *            被操作对象ID
     * @param desc
     *            描述
     * @return
     */
    public static CheckLogs getCheckLog(BaseUsers user, String opType,
            String objectId, String type, String desc) {
    	CheckLogs log = new CheckLogs();
        log.setOpType(opType);
        log.setObjectId(objectId);
        log.setRemark(desc);
        log.setUserId(user.getUserAccount());
        log.setOpDate(new Date());
        log.setType(type);
        return log;
    }
    
    /**
     * 得到日志对象
     *
     * @param user
     *            用户对象
     * @param opType
     *            操作(类似于测试通过/测试不通过/审核通过等等)
     * @param type
     *            （对数据库的操作）操作类型TSM_OP_APP_TYPE/TSM_OP_AP_TYPE等等
     * @param appId
     *            应用版本ID
     * @param objectId
     *            被操作对象ID
     * @param tableName
     *            数据库表名
     * @param dataObject
     *            表数据，数据pojo对象字符
     * @param condition
     *            条件（根据条件操作的，需要记录相关条件，比如有些批量操作）比如Criteria对象
     * @param menu
     *            业务菜单
     * @param menuAction
     *            菜单下的业务功能，比如导入，导出，新增，删除等
     * @return
     */
    public static BaseLogs getOperateLog(BaseUsers user, String opType,
            Long appId, String objectId,
            String type, String tableName,
            String dataObject, String condition,
            String menu, String menuAction) {
        LogTables table = new LogTables(user, type, tableName, dataObject,
                condition, menu, menuAction);
        BaseLogs log = new BaseLogs();
        log.setOpType(opType);
        //log.setAppId(appId);
        log.setObjectId(objectId);
        log.setRemark(table.toString());
        log.setUserId(user.getUserAccount());
        log.setOpDate(new Date());
        log.setType(type);
        //log.setMenu(menu);
        return log;
    }

    /**
     * 得到日志对象
     *
     * @param user
     *            用户对象
     * @param opType
     *            操作(类似于测试通过/测试不通过/审核通过等等)
     * @param type
     *            （对数据库的操作）操作类型TSM_OP_APP_TYPE/TSM_OP_AP_TYPE等等
     * @param tableName
     *            数据库表名
     * @param dataObject
     *            表数据，数据pojo对象字符
     * @param condition
     *            条件（根据条件操作的，需要记录相关条件，比如有些批量操作）比如Criteria对象
     * @param menu
     *            业务菜单
     * @param menuAction
     *            菜单下的业务功能，比如导入，导出，新增，删除等
     * @return
     */
    public static BaseLogs getOperateLog(BaseUsers user, String opType,
            String type, String tableName, String dataObject, String condition,
            String menu, String menuAction) {
        LogTables table = new LogTables(user, type, tableName, dataObject,
                condition, menu, menuAction);
        BaseLogs log = new BaseLogs();
        log.setId(UUID.randomUUID().toString().replace("-", "").substring(0, 32));
        log.setOpType(opType);
        if("true".equals(isEncryptLog)){
        	try {
        		String remark = new String(new Encryptor().encrypt(logKey3DES.getBytes(), table.toString().getBytes("UTF8")));
        		log.setRemark(remark);
			} catch (Exception e) {
			}
        	
        }else{
        	log.setRemark(table.toString());
        }
        log.setUserId(user.getUserAccount());
        log.setOpDate(new Date());
        log.setType(type);
//        log.setMenu(menu);
        return log;
    }
    
    public static void decryptLogs(PageList<LogsUser> baseLogss) {
		if("true".equals(isEncryptLog)){
			if (baseLogss != null && baseLogss.size() > 0) {
				for (LogsUser logsUser : baseLogss) {
					String keyCV=logsUser.getRemark();
					try {
						String remark = new String(new Encryptor().decrypt(logKey3DES.getBytes(), keyCV.getBytes()),"UTF8");
						logsUser.setRemark(remark);
					} catch (Exception e) {
						logsUser.setRemark("日志被更改");
					}
					
				}
			}
		}
    }

    /**
     * 定制输出格式
     */
    public String toString() {
        StringBuffer desc = new StringBuffer();
        desc.append("菜单【" + menu + "】");
        desc.append("，功能【" + menuAction + "】");
        desc.append("，表【" + tableName + "】");
//        if (dataObject != null && !"".equals(dataObject)) {
        if (CheckEmpty.isNotEmpty(dataObject)) {
            desc.append("，数据【" + dataObject + "】");
        }
//        if (condition != null && !"".equals(condition)) {
        if (CheckEmpty.isNotEmpty(condition)) {
            desc.append("，条件【" + condition + "】");
        }
        return desc.toString();
    }

    public static String setShowValue(String value, String type) {
        if (Constant.EUICC_BASE_TYPE.equalsIgnoreCase(type)) {
            return baseMap.get(value);
        }
        else if (Constant.EUICC_CARD_TEST_TYPE.equalsIgnoreCase(type)) {
            return cardTestStatusMap.get(value);
        }
        else if (Constant.EUICC_OP_AP_TYPE.equalsIgnoreCase(type)) {
            return apStatusMap.get(value);
        }
        else if (Constant.EUICC_OP_APP_TYPE.equalsIgnoreCase(type)) {
            return appStatusMap.get(value);
        }
        else if (Constant.EUICC_OP_APPVER_TYPE.equalsIgnoreCase(value)) {
            return appleteStatusMap.get(value);
        }
        else {
            return "";
        }
    }

    public static Map<String, Object> getMap() {
        return map;
    }

    public static void setMap(Map<String, Object> map) {
        LogTables.map = map;
    }

    public BaseUsers getBaseUser() {
        return baseUser;
    }

    public void setBaseUser(BaseUsers baseUser) {
        this.baseUser = baseUser;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDataObject() {
        return dataObject;
    }

    public void setDataObject(String dataObject) {
        this.dataObject = dataObject;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getMenuAction() {
        return menuAction;
    }

    public void setMenuAction(String menuAction) {
        this.menuAction = menuAction;
    }

    public Date getOpDate() {
        return opDate;
    }

    public void setOpDate(Date opDate) {
        this.opDate = opDate;
    }

    public static Map<String, String> getAppStatusMap() {
        return appStatusMap;
    }

    public static void setAppStatusMap(Map<String, String> appStatusMap) {
        LogTables.appStatusMap = appStatusMap;
    }

    public static Map<String, String> getApStatusMap() {
        return apStatusMap;
    }

    public static void setApStatusMap(Map<String, String> apStatusMap) {
        LogTables.apStatusMap = apStatusMap;
    }

    public static Map<String, String> getCardTestStatusMap() {
        return cardTestStatusMap;
    }

    public static void setCardTestStatusMap(
            Map<String, String> cardTestStatusMap) {
        LogTables.cardTestStatusMap = cardTestStatusMap;
    }

    public static Map<String, String> getBaseMap() {
        return baseMap;
    }

    public static void setBaseMap(Map<String, String> baseMap) {
        LogTables.baseMap = baseMap;
    }

}
