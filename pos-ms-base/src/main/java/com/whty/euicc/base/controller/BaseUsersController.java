package com.whty.euicc.base.controller;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.base.common.Constant;
import com.whty.euicc.base.common.DBConstant;
import com.whty.euicc.base.common.LogTables;
import com.whty.euicc.base.dao.ApInfosMapper;
import com.whty.euicc.base.pojo.*;
import com.whty.euicc.base.pojo.BaseUsersExample.Criteria;
import com.whty.euicc.base.service.BaseLogsService;
import com.whty.euicc.base.service.BaseRolesService;
import com.whty.euicc.base.service.BaseUserRoleService;
import com.whty.euicc.base.service.BaseUsersService;
import com.whty.euicc.common.base.BaseController;
import com.whty.euicc.common.base.BaseResponseDto;
import com.whty.euicc.common.base.DataTableQuery;
import com.whty.euicc.common.utils.CheckEmpty;
import com.whty.euicc.common.utils.DateUtil;
import com.whty.euicc.common.utils.MD5Util;
import com.whty.euicc.common.utils.UUIDUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
//import com.whty.euicc.data.pojo.LogTable;

@Controller
@RequestMapping("/baseUsers")
public class BaseUsersController extends BaseController {

	@Autowired
	BaseUsersService baseUsersService;

	@Autowired
	private BaseRolesService baseRolesService;

	@Autowired
	private BaseUserRoleService baseUserRoleService;
	
	@Autowired
	private ApInfosMapper apInfosMapper;
	
	@Autowired
	private BaseLogsService baseLogsService;

	@Value("${init_user_pwd}")
	private String initUserPwd;
	

	@Value("${ap_apply_role}")
	private String ap_apply_role;
	
	@Value("${ap_role}")
	private String ap_role;
	
	/**
	 * 显示主列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show() {
		return "/modules/base/baseUser/userUI";
	}
	
	/**
	 * 表格请求
	 * 
	 * @param request
	 * @param response
	 * @throws java.io.IOException
	 */
	@RequestMapping(value = "/tableAjax")
	public void tableAjaxData(HttpServletRequest request,
			HttpServletResponse response, BaseUsers user) throws IOException {
		// 每页条数
		DataTableQuery dt = new DataTableQuery(request);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		Map<String, Object> result = buildTableData(dt, user);
		writeJSONResult(result, response, DateUtil.yyyy_MM_dd_HH_mm_ss_EN);
	}

	/**
	 * 构建数据树
	 * 
	 * @param dt
	 * @param user
	 * @return
	 * @throws java.io.IOException
	 */
	private Map<String, Object> buildTableData(DataTableQuery dt, BaseUsers user)
			throws IOException {
		// 当前页数
		int currentNumber = dt.getPageStart() / dt.getPageLength() + 1;
		PageBounds pageBounds = new PageBounds(currentNumber,
				dt.getPageLength());
		BaseUsersExample exmple = new BaseUsersExample();
		Criteria criteria = exmple.createCriteria();
		if (CheckEmpty.isNotEmpty(user.getUserAccount())) {
			criteria.andUserAccountLike("%" + user.getUserAccount() + "%");
		}
		if (CheckEmpty.isNotEmpty(user.getUserName())) {
			criteria.andUserNameLike("%" + user.getUserName() + "%");
		}
		// 排序条件
		if (CheckEmpty.isNotEmpty(dt.getOrderBy())
				&& CheckEmpty.isNotEmpty(dt.getOrderParam())) {
			//
			StringBuilder orderByClause = new StringBuilder("");
			orderByClause.append(dt.getOrderParam()).append(" ")
					.append(dt.getOrderBy());
			exmple.setOrderByClause(orderByClause.toString());
		}
		PageList<BaseUsers> baseUsers = baseUsersService.selectByExample(
				exmple, pageBounds);
		Map<String, Object> records = new HashMap<String, Object>();
		records.put("data", baseUsers);
		records.put("recordsTotal", baseUsers.getPaginator().getTotalCount());
		records.put("recordsFiltered", baseUsers.getPaginator().getTotalCount());
		return records;
	}

	/**
	 * 新增/编辑用戶
	 * 
	 * @param request
	 * @param response
	 * @param user
	 * @throws java.io.IOException
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void addUser(HttpServletRequest request,
			HttpServletResponse response, BaseUsers user, String userRole)
			throws IOException {
		response.setCharacterEncoding("utf-8");
		int flag = 0;
		String msg = "";
		List<Object> list = new ArrayList<Object>();
		PageBounds pageBounds = new PageBounds();
		Subject subjuct = SecurityUtils.getSubject();
		BaseUsers  currentUser= (BaseUsers) subjuct.getSession().getAttribute(Constant.BaseUsersConstant.CURRENTUSER);
		BaseUsersExample bsExample = new BaseUsersExample();
		Criteria criteriaBs = bsExample.createCriteria();
		criteriaBs.andUserAccountEqualTo(user.getUserAccount());
		if (CheckEmpty.isNotEmpty(user.getUserId())) {
			criteriaBs.andUserIdNotEqualTo(user.getUserId());
		}
		PageList<BaseUsers> bsUser = baseUsersService.selectByExample(
				bsExample, pageBounds);
		if (bsUser.size() > 0) {
			flag = 2;
			msg = "账号已存在";
			list.add(flag);
			list.add(msg);
			writeJSONArrayResult(list, response);
			return;
		}
		if (CheckEmpty.isEmpty(user.getUserId())) {
			String userId = UUIDUtil.getUuidString();
			user.setUserId(userId);
			user.setUserStatus("0");
			// 未选择角色
			if (!"-1".equals(userRole)) {
				BaseUserRoleKey bs = new BaseUserRoleKey();
				bs.setRoleId(userRole);
				bs.setUserId(user.getUserId());
				baseUserRoleService.insertSelective(bs);
			}
			user.setUserKey(UUID.randomUUID().toString().replace("-", ""));
			ApprovalInfo approvalInfo = new ApprovalInfo();
			approvalInfo.setApprovalUserId(user.getUserId());
			approvalInfo.setTotalApprovalAmount(0);
			approvalInfo.setApprovalAmount(0);
			approvalInfo.setRealAmount(0);
			approvalInfo.setApprovalState("0");
			flag = baseUsersService.insertSelective(user, approvalInfo);
			if(ap_apply_role.equals(userRole)){
				ApInfo apInfo=new ApInfo();
				apInfo.setUserId(user.getUserId());
				apInfo.setStatus("0");
				apInfo.setRegCodeDate(new Date());
				apInfosMapper.insertSelective(apInfo);
			}
			if (flag > 0) {
				msg = "添加用户成功";
//				saveLog(com.whty.euicc.base.common.Constant.EUICC_BASE_TYPE,"添加用户",user.toString(),"新建");
				baseLogsService.insertSelective(LogTables.getOperateLog(
	                    currentUser, "5", Constant.POS_BASE_TYPE,
	                    "BASE_USERS", getObjectData(user), "", "用户管理",
	                    "新建"));
			} else {
				msg = "添加用户失败，请联系管理员";
			}
		} else {
			if(isAPOrAPAPPLY(user.getUserId())){
				list.add("0");
				list.add("不允许编辑角色为应用提供方或者应用提供方注册的用户");
				writeJSONArrayResult(list, response);
				return;
			}
			user.setUserPassword(null);
			flag = baseUsersService.updateSelectiveByPrimaryKey(user);
			BaseUserRoleExample example = new BaseUserRoleExample();
			BaseUserRoleExample.Criteria criteria = example
					.createCriteria();
			criteria.andUserIdEqualTo(user.getUserId());
			baseUserRoleService.deleteByExample(example);
			if (!"-1".equals(userRole)) {
				BaseUserRoleKey bs = new BaseUserRoleKey();
				bs.setRoleId(userRole);
				bs.setUserId(user.getUserId());
				baseUserRoleService.insertSelective(bs);
			}
			if (flag > 0) {
				msg = "修改用户成功";
				//List<String> userlist=new ArrayList<String>();
				//saveLog(com.whty.euicc.base.common.Constant.EUICC_BASE_TYPE,"编辑用户",user.toString(),"编辑");
				baseLogsService.insertSelective(LogTables.getOperateLog(
	                    currentUser, "6", Constant.POS_BASE_TYPE,
	                    "BASE_USERS", getObjectData(user), "", "用户管理",
	                    "编辑"));
			} else {
				msg = "修改用户失败，请联系管理员";
			}
		}
		list.add(flag);
		list.add(msg);
		writeJSONArrayResult(list, response);
	}
	 private String getObjectData(BaseUsers user) {
	        StringBuffer sBuffer = new StringBuffer();
	        sBuffer.append("BaseUser[");
	        sBuffer.append("userName=");
	        sBuffer.append(user.getUserName());
	        sBuffer.append(", ");
	        sBuffer.append("UserOfficePhone=");
	        sBuffer.append(user.getUserOfficePhone());
	        sBuffer.append(", ");
	        sBuffer.append("status=");
	        sBuffer.append(user.getUserStatus());
	        sBuffer.append(",");
	        sBuffer.append("userEmail=");
	        sBuffer.append(user.getUserEmail());
	        sBuffer.append(",");
	        sBuffer.append("userSex=");
	        sBuffer.append(user.getUserSex());
	        sBuffer.append(",");
	        sBuffer.append("userRemak=");
	        sBuffer.append(user.getUserRemark());
	        sBuffer.append(",");
	        sBuffer.append("]");
	        return sBuffer.toString();
	    }
//	private void saveLog(String EUICCBaseType, String string, String string2, String opType) {
//		// TODO Auto-generated method stub
//		//logger.debug("保存日志");
//		Subject subject=SecurityUtils.getSubject();
//		String userId = (String)subject.getSession().getAttribute(Constant.BaseUsersConstant.USERID);
//		BaseLogs baseLogs = new BaseLogs();
//		// ID
//		baseLogs.setId(UUIDUtil.getUuidString());
//		// 操作人
//		baseLogs.setUserId(userId);
//		// 操作类型
//		baseLogs.setOpType("");
//		// 操作日期
//		baseLogs.setOpDate(new Date());
//        StringBuffer remark = new StringBuffer();
////        if(userlist!=null)
////        {
//        	remark.append("菜单" + "【系统设置 】");
//        	remark.append("功能"+"【"+opType+"】");
////        	if(Constant.SAVESTATUSADD.equals(list.get(1)))
////        	{
////        		remark.append("功能"+"【新建】");
////        	}
////        	else if(Constant.SAVESTATUSUPDATE.equals(list.get(1)))
////        	{
////        		remark.append("功能"+"【编辑】");
////        	}
////        	else
////        	{
////        		remark.append("功能"+"【删除】");
////        	}
//        	remark.append("表"+"【BASE_USERS】"); 
////        }
//        remark.append("数据"+"【"+string2+"】");
//       	//remark.append(dataObject);
//		// 描述
//        if(remark.length()>2000) {
//        	baseLogs.setRemark(remark.substring(0, 2000));
//        } else {
//        	baseLogs.setRemark(remark.toString());
//        }
//		// 操作所属大类别
//		baseLogs.setType(EUICCBaseType);
//		baseLogsService.insertSelective(baseLogs);
//	}

	/**
	 * 根据主键删除用户
	 * 
	 * @param request
	 * @param response
	 * @param userId
	 * @throws java.io.IOException
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void deleteUser(HttpServletRequest request,
			HttpServletResponse response, String userId) throws IOException {
		response.setCharacterEncoding("utf-8");
		int flag = 0;
		String msg = "";

		// 系统管理员信息不允许删除
		Subject subjuct = SecurityUtils.getSubject();
		BaseUsers  currentUser= (BaseUsers) subjuct.getSession().getAttribute(Constant.BaseUsersConstant.CURRENTUSER);
		Boolean isAdmin = isAdmin(userId);
		Boolean isAP = isAP(userId);
		BaseUsers user=baseUsersService.selectByPrimaryKey(userId);
		if (isAdmin) {
			flag = 2;
		} else if(isAP){
			flag = 3;
		}else {
			flag = baseUsersService.deleteByPrimaryKey(userId);
			apInfosMapper.deleteByPrimaryKey(userId);
			BaseUserRoleExample example=new BaseUserRoleExample();
			example.createCriteria().andUserIdEqualTo(userId);
			baseUserRoleService.deleteByExample(example);
		}

		if (flag == 0) {
			msg = "删除失败，请联系管理员";
		} else if (flag == 2) {
			msg = "系统管理员用户不允许删除！";
		}  else if (flag == 3) {
			msg = "应用提供方不允许删除！";
		} else {
			baseLogsService.insertSelective(LogTables.getOperateLog(
                    currentUser, "2", Constant.POS_BASE_TYPE,
                    "BASE_USERS", getObjectData(user), "", "用户管理",
                    "删除"));
			msg = "删除成功";
		}

		List<Object> list = new ArrayList<Object>();
		list.add(flag);
		list.add(msg);
		writeJSONArrayResult(list, response);
	}

	/**
	 * 根据主键查询用户信息,包括角色信息
	 * 
	 * @param request
	 * @param response
	 * @param userId
	 * @throws java.io.IOException
	 */
	@RequestMapping(value = "/selectById", method = RequestMethod.POST)
	public void selectUserById(HttpServletRequest request,
			HttpServletResponse response, String userId) throws IOException {
		response.setCharacterEncoding("utf-8");
		BaseUsers baseUser = baseUsersService.selectByPrimaryKey(userId);
		BaseUserRoleExample baseUserRoleExample = new BaseUserRoleExample();
		PageBounds pageBounds = new PageBounds();
		baseUserRoleExample.createCriteria().andUserIdEqualTo(userId);
		PageList<BaseUserRoleKey> baseUserRole = baseUserRoleService
				.selectByExample(baseUserRoleExample, pageBounds);
		int flag = 0;
		if (baseUser == null) {
			flag = 0;
		} else {
			flag = 1;
		}
		List<Object> list = new ArrayList<Object>();
		list.add(flag);
		list.add(baseUser);
		if (baseUserRole.size() > 0) {
			list.add(baseUserRole.get(0).getRoleId());
		} else {
			// 用户无权限的时候传入 -1
			list.add("-1");
		}
		list.add(baseUserRole);
		writeJSONArrayResult(list, response);
	}

	/**
	 * 获取角色列表
	 * 
	 * @param request
	 * @param response
	 * @param userId
	 * @throws java.io.IOException
	 */
	@RequestMapping(value = "/getRoleList", method = RequestMethod.POST)
	public void getRoleList(HttpServletRequest request,
			HttpServletResponse response, String userId) throws IOException {
		response.setCharacterEncoding("utf-8");
		BaseRolesExample example = new BaseRolesExample();
		PageBounds pageBounds = new PageBounds();
		PageList<BaseRoles> baseRoles = baseRolesService.selectByExample(
				example, pageBounds);
		writeJSONArrayResult(baseRoles, response);
	}

	/**
	 * 启用/禁用商户
	 * 
	 * @param request
	 * @param response
	 * @param userId
	 * @param status
	 */
	@RequestMapping(value = "/startOrstopUser", method = RequestMethod.POST)
	public void startOrstopUser(HttpServletRequest request,
			HttpServletResponse response, String userId, String status) {
		int flag = 0;
		String msg = "";
		// 系统管理员信息不允许启用禁用
		Boolean isAdmin = isAdmin(userId);
		if (isAdmin) {
			flag = 2;
		} else {
			// 更改用户状态
			BaseUsers baseUsers = new BaseUsers();
			baseUsers.setUserId(userId);
			if (DBConstant.BaseUserStatus.START.equals(status)) {
				baseUsers.setUserStatus(DBConstant.BaseUserStatus.STOP);
			} else {
				baseUsers.setUserStatus(DBConstant.BaseUserStatus.START);
			}
			flag = baseUsersService.upateUserStatus(baseUsers);
		}

		// 封装显示信息
		if (flag == 0) {
			msg = "操作失败，请联系管理员";
		} else if (flag == 2) {
			msg = "系统管理员用户不允许启用禁用！";
		} else {
			Subject subjuct = SecurityUtils.getSubject();
			BaseUsers  currentUser= (BaseUsers) subjuct.getSession().getAttribute(Constant.BaseUsersConstant.CURRENTUSER);
			baseLogsService.insertSelective(LogTables.getOperateLog(
                    currentUser, "1", Constant.POS_BASE_TYPE,
                    "BASE_USERS", getObjectData(baseUsersService.selectByPrimaryKey(userId)), "", "用户管理",
                    "更改用户状态"));
			msg = "操作成功";
		}

		List<Object> list = new ArrayList<Object>();
		list.add(flag);
		list.add(msg);
		writeJSONArrayResult(list, response);
	}
	
	//重置密码
	@RequestMapping(value = "/resetPass", method = RequestMethod.POST)
	public void resetPass(HttpServletRequest request,
			HttpServletResponse response, String userId) {
		BaseUsers baseUsers=new BaseUsers();
		baseUsers.setUserId(userId);
		baseUsers.setUserPassword(MD5Util.MD5(MD5Util.MD5(initUserPwd)));
		BaseResponseDto baseResponseDto=new BaseResponseDto(true);
		int flag=baseUsersService.updateSelectiveByPrimaryKey(baseUsers);
		if (flag>0){
			baseResponseDto.setSuccess(true);
		}else{
			baseResponseDto.setSuccess(false);
		}
		writeJSONResult(baseResponseDto, response);
	}
	
	/**
	 * 判断是否是系统管理员用户
	 * 
	 * @param userId
	 * @return
	 */
	private boolean isAdmin(String userId) {
		BaseUsers baseUsers = this.baseUsersService.selectByPrimaryKey(userId);
		if ("admin".equals(baseUsers.getUserAccount())) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断是否是应用提供方
	 * 
	 * @param userId
	 * @return
	 */
	private boolean isAP(String userId) {
		BaseUserRoleExample baex=new BaseUserRoleExample();
		baex.createCriteria().andUserIdEqualTo(userId);
		PageList<BaseUserRoleKey> baseUserRoleKey= baseUserRoleService.selectByExample(baex, new PageBounds());
		for (int i = 0; i < baseUserRoleKey.size(); i++) {
			BaseUserRoleKey bu=baseUserRoleKey.get(i);
			if(ap_role.equals(bu.getRoleId())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断是否是应用提供方
	 * 
	 * @param userId
	 * @return
	 */
	private boolean isAPOrAPAPPLY(String userId) {
		BaseUserRoleExample baex=new BaseUserRoleExample();
		baex.createCriteria().andUserIdEqualTo(userId);
		PageList<BaseUserRoleKey> baseUserRoleKey= baseUserRoleService.selectByExample(baex, new PageBounds());
		for (int i = 0; i < baseUserRoleKey.size(); i++) {
			BaseUserRoleKey bu=baseUserRoleKey.get(i);
			if(ap_role.equals(bu.getRoleId())||ap_apply_role.equals(bu.getRoleId())){
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * 判断是否是应用提供方
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updatePass", method = RequestMethod.POST)
	public void updatePass(HttpServletRequest request,
			HttpServletResponse response,String oldPass,String newPass) {
		Subject subject=SecurityUtils.getSubject();
		BaseUsers user=(BaseUsers) subject.getSession().getAttribute(Constant.BaseUsersConstant.CURRENTUSER);
		if(user==null){
			writeJSONResult(new BaseResponseDto(false, "当前未登录"), response);
			return;
		}
		//原始密码错误
		if(!user.getUserPassword().equals(oldPass)){
			writeJSONResult(new BaseResponseDto(false, "原始密码错误"), response);
			return;
		}
		if(CheckEmpty.isEmpty(oldPass)){
			writeJSONResult(new BaseResponseDto(false, "原始密码不能为空"), response);
			return;
		}
		if(oldPass.equals(newPass)){
			writeJSONResult(new BaseResponseDto(false, "新密码不能和原密码相同"), response);
			return;
		}
		//修改密码
		BaseUsers newUser=new BaseUsers();
		newUser.setUserId(user.getUserId());
		newUser.setUserPassword(newPass);
		baseUsersService.updateSelectiveByPrimaryKey(newUser);
		
		writeJSONResult(new BaseResponseDto(true, "密码修改成功"), response);
	}
}
