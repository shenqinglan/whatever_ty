package com.whty.euicc.base.controller;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.base.common.Constant;
import com.whty.euicc.base.common.LogTables;
import com.whty.euicc.base.pojo.*;
import com.whty.euicc.base.service.BaseLogsService;
import com.whty.euicc.base.service.BaseModulesService;
import com.whty.euicc.base.service.BaseUserRoleService;
import com.whty.euicc.base.service.BaseUsersService;
import com.whty.euicc.common.base.BaseController;
import com.whty.euicc.common.base.BaseResponseDto;
import com.whty.euicc.common.base.Menu;
import com.whty.euicc.common.mail.MailSender;
import com.whty.euicc.common.utils.CheckEmpty;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class LoginController extends BaseController implements HttpSessionListener{
	 private static final Logger logger = LoggerFactory
	            .getLogger(LoginController.class);

	@Autowired
	private BaseModulesService baseModulesService;

	@Autowired
	private BaseUsersService baseUsersService;

	@Autowired
	private BaseUserRoleService baseUserRoleService;

	@Value("${ctxPath}")
	private String ctx;

	@Value("${smtp_hostname}")
	private String smtpHostName;

	@Value("${mail_username}")
	private String mailUsername;

	@Value("${mail_password}")
	private String mailPassword;
	
	@Autowired
	private BaseLogsService baseLogsService;

	private static Map<String, HttpSession> loginedUser = new HashMap<String, HttpSession>();

	public static String getPwdMailContent(String userCode) {
		StringBuilder sb = new StringBuilder("");
		sb.append("<p style=\"text-align: left;\">亲爱的用户：</p>");
		sb.append("<p style=\"text-align: left; text-indent: 2em;\">欢迎使用找回密码功能。</p>");
		sb.append("<p style=\"text-align: left; text-indent: 2em;\">您的验证码为：</p>");
		sb.append("<p style=\"text-align: left; text-indent: 2em;\">");
		sb.append(userCode);
		sb.append("</p>");
		sb.append("<p style=\"text-align: left; text-indent: 2em;\">该验证码有效期为10分钟，逾期请重新获取；</p>");
		sb.append("<p style=\"text-align: left; text-indent: 2em;\">如果您并未发过此请求，则可能是因为其他用户在尝试重设密码时误输入了您的电子邮件地址而使您收到这封邮件，那么您可以放心的忽略此邮件，无需进一步采取任何操作。</p>");
		sb.append("<p style=\"text-align: right;\">用户中心</p>");
		DateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		String dateStr = df.format(new Date());
		sb.append("<p style=\"text-align: right;\">");
		sb.append(dateStr);
		sb.append("</p>");
		return sb.toString();
	}

	@RequestMapping(value = "${ctxPath}", method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response) {
		String msg=(String) request.getParameter("errorMsg");
		request.setAttribute("errorMsg", msg);
		return "login";
	}

	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response) {
		return "index/index";
	}
	
	@RequestMapping(value = "getUserKey", method = RequestMethod.POST)
	public void getUserKey(HttpServletRequest request, HttpServletResponse response,
			String userName) {
		BaseUsers baseUsers = baseUsersService.selectByUserAccount(userName);
		writeJSONResult(baseUsers, response);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String doLogin(HttpServletRequest request,
			HttpServletResponse response,Model model) {
		logger.debug("login....");
		String errorMsg = "";
		String userName = request.getParameter("username");
		String passWord = request.getParameter("password");
		String yzm = request.getParameter("yzm");
		String sessionYzm=(String) request.getSession().getAttribute(Constant.RANDOM_CODE);
		if(CheckEmpty.isEmpty(sessionYzm)){
			model.addAttribute("errorMsg", "验证码超时");
			return "login";
		}
		if(!sessionYzm.equalsIgnoreCase(yzm)){
			model.addAttribute("errorMsg", "验证码不正确");
			return "login";
		}
        BaseUsers user1=baseUsersService.selectByPrimaryKey("1");
		UsernamePasswordToken userToken = new UsernamePasswordToken(userName,
				passWord);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(userToken);
		} catch (UnknownAccountException e) {
			errorMsg = "账户/密码不正确";
			model.addAttribute("errorMsg", errorMsg);
			return "login";
		} catch (IncorrectCredentialsException e) {
			errorMsg = "用户名/密码错误";
			model.addAttribute("errorMsg", errorMsg);
			return "login";
		}  catch (LockedAccountException e) {
			errorMsg = "账户锁定";
			model.addAttribute("errorMsg", errorMsg);
			return "login";
		} catch (AuthenticationException e) {
            e.printStackTrace();
			// 其他错误，比如锁定，如果想单独处理请单独catch处理
			errorMsg = "其他错误：" + e.getMessage();
			model.addAttribute("errorMsg", errorMsg);
			return "login";
		}
		if (subject.isAuthenticated()) {
			String userId = (String) subject.getSession()
					.getAttribute("userId");
			BaseUsers user=baseUsersService.selectByPrimaryKey(userId);
			BaseUserRoleExample userRoleExample =new BaseUserRoleExample();
			BaseUserRoleExample.Criteria cra=userRoleExample.createCriteria();
			cra.andUserIdEqualTo(userId);
			cra.andRoleIdEqualTo("AP_APPLY_ROLE");
			PageList<BaseUserRoleKey> list=baseUserRoleService.selectByExample(userRoleExample, new PageBounds());
			if(user.getUserLastLoginTime()==null&&list.size()>0)
			{
				request.getSession().setAttribute("warring", "您是第一次登录，请修改您的密码");
			}
			else
			{
				request.getSession().setAttribute("warring", null);
			}
			BaseUsers baseUsers=new BaseUsers();
			baseUsers.setUserId(userId);
			baseUsers.setUserLastLoginIp(this.getIpAddr(request));
			baseUsers.setUserLastLoginTime(new Date());
			baseUsersService.updateSelectiveByPrimaryKey(baseUsers);
			String menuJsonStr = buildMenuStr(getMenuTrees(userId), request);
			HttpSession httpSession = request.getSession();
			userNewSession(userName, httpSession);
			httpSession.setAttribute("menustr", menuJsonStr);
			httpSession.setAttribute("userName",userName);
			httpSession.setMaxInactiveInterval(1800);//设置超时时间
			baseLogsService.insertSelective(LogTables.getOperateLog(
					user, "0", Constant.POS_BASE_TYPE,
					 "BASE_USER", getObjectData(user),
					 "", "登陆","登陆"));
			return  "index/index";
		} else {
			model.addAttribute("errorMsg", errorMsg);
			return "login";
		}
	}
	
	private String getObjectData(BaseUsers baseUsers) {
		 StringBuffer sBuffer = new StringBuffer();
		 sBuffer.append("BaseUser[");
		 sBuffer.append("userAccount=");
		 sBuffer.append(baseUsers.getUserAccount());
		 sBuffer.append(", ");
		 sBuffer.append("userLastLoginIp=");
		 sBuffer.append(baseUsers.getUserLastLoginIp());
		 sBuffer.append(", ");
		 sBuffer.append("userLastLoginTime=");
		 sBuffer.append(baseUsers.getUserLastLoginTime());
		 sBuffer.append("]");
		 return sBuffer.toString();
	 }


	@RequestMapping(value = "/sendChkEMail")
	public void sendChkEMail(HttpServletRequest request, HttpServletResponse response, String emailAccount,
			HttpSession session) {
		BaseUsersExample exmple = new BaseUsersExample();
		BaseUsersExample.Criteria criteria = exmple.createCriteria();
		criteria.andUserEmailEqualTo(emailAccount);
		//查询邮箱对应的用户
		PageList<BaseUsers> list = baseUsersService.selectByExample(exmple, new PageBounds());
		HashMap<String, Object> res = new HashMap<String, Object>();
		if(list.size()==0) {
			res.put("success", false);
			res.put("errorMsg", "未绑定该邮箱");
			writeJSONResult(res, response);
			return;
		}
		// 发送验证邮件
		MailSender sender = new MailSender(smtpHostName, mailUsername, mailPassword);
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateStr = df.format(new Date());
		String chkCode = getRandomString(6);
		// 将验证码存入session
		session.setAttribute("emailAccount", emailAccount);
		session.setAttribute("chkCode", chkCode);
		session.setAttribute("dateStr", dateStr);
		session.setAttribute("user", list.get(0));
		session.setAttribute("userId", list.get(0).getUserId());
		try {
			sender.send(emailAccount, "登录验证码",getPwdMailContent(chkCode));
			res.put("success", true);
			writeJSONResult(res, response);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			res.put("success", false);
			res.put("errorMsg", "邮件发送失败，请确认邮箱地址！");
			writeJSONResult(res, response);
			return;
		}
	}

	@RequestMapping(value = "/verify", method = RequestMethod.POST)
	public void verify(HttpServletRequest request, HttpServletResponse response, String yzm, String emailAccount,String emailCode, HttpSession session) {
		HashMap<String, Object> res = new HashMap<String, Object>();
		//验证图形验证码
		String sessionYzm=(String) request.getSession().getAttribute(Constant.RANDOM_CODE);
		if(!yzm.equalsIgnoreCase(sessionYzm)){
			res.put("success", false);
			res.put("errorMsg", "图形验证码错误");
			writeJSONResult(res, response);
			return;
		}
		// 校验验证码是否输入正确
		Object chk = session.getAttribute("chkCode");
		Object date = session.getAttribute("dateStr");
		Object userObj = session.getAttribute("user");
		if(chk != null&&date!=null&&userObj!=null){
			String chkCode = chk.toString();
			String dateStr = date.toString();
			BaseUsers baseUser = (BaseUsers)userObj;
			if (chkCode.equalsIgnoreCase(emailCode)) {
				DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
				try {
					Date applyTime = df.parse(dateStr);
					Date nowTime = new Date();
					long difTime = nowTime.getTime() - applyTime.getTime();
					long mins = difTime / (1000 * 60);
					// 校验验证码是否过期
					if(mins < 10){
						res.put("success", true);


						UsernamePasswordToken userToken = new UsernamePasswordToken(baseUser.getUserName(),baseUser.getUserPassword());
						Subject subject = SecurityUtils.getSubject();
						try {
							subject.login(userToken);
						}catch (Exception e){

						}
						BaseUsers baseUsers=new BaseUsers();
						baseUsers.setUserId(baseUser.getUserId());
						baseUsers.setUserLastLoginIp(this.getIpAddr(request));
						baseUsers.setUserLastLoginTime(new Date());
						baseUsersService.updateSelectiveByPrimaryKey(baseUsers);
						String menuJsonStr = buildMenuStr(getMenuTrees(baseUser.getUserId()), request);
						session.setAttribute("menustr", menuJsonStr);
						session.setAttribute("userName",baseUser.getUserAccount());
						session.setMaxInactiveInterval(1800);//设置超时时间
						userNewSession(baseUser.getUserAccount(), session);
						writeJSONResult(res, response);
					}else{
						res.put("success", false);
						res.put("errorMsg", "验证码已过期，请重新获取！");
						writeJSONResult(res, response);
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				res.put("success", false);
				res.put("errorMsg", "验证码错误，请重新输入！");
				writeJSONResult(res, response);
			}
		}else{
			res.put("success", false);
			res.put("errorMsg", "请输入验证码！");
			writeJSONResult(res, response);
		}
	}


	/**
     * 取得客户端真实ip
     *
     * @param request
     * @return 客户端真实ip
     */
    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        logger.debug("1- X-Forwarded-For ip={}", ip);
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            logger.debug("2- Proxy-Client-IP ip={}", ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            logger.debug("3- WL-Proxy-Client-IP ip={}", ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            logger.debug("4- HTTP_CLIENT_IP ip={}", ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            logger.debug("5- HTTP_X_FORWARDED_FOR ip={}", ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            logger.debug("6- getRemoteAddr ip={}", ip);
        }
        logger.debug("finally ip={}", ip);
        return ip;
    }



	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request,
			HttpServletResponse response){
		Subject subject = SecurityUtils.getSubject();
		String userName =(String)request.getSession().getAttribute("userName");
		request.getSession().removeAttribute("userName");
		subject.logout();
		loginedUser.remove(userName);
		return "login";
	}

	@RequestMapping(value = "/clearSession")
	public String clearSession(HttpServletRequest request,
			HttpServletResponse response){
		request.getSession().removeAttribute("userName");
		return "login";
	}

	@RequestMapping(value = "/getUserName")
	public void getUserName(HttpServletRequest request,
			HttpServletResponse response){
		String userNanme=(String) request.getSession().getAttribute("userName");
		if(CheckEmpty.isEmpty(userNanme)){
			writeJSONResult(new BaseResponseDto(false,""), response);
		}else{
			writeJSONResult(new BaseResponseDto(true,""), response);
		}
	}


//	@RequestMapping(value = "/{catalog}/{catalog1}/{page}", method = RequestMethod.GET)
//	public String page(@RequestParam String menu_id,@PathVariable String catalog,@PathVariable String catalog1,@PathVariable String page,
//			HttpSession session,
//			HttpServletRequest request,HttpServletResponse response) throws IOException {
//		StringBuilder url = new StringBuilder("");
//		url.append("/modules");
//		if (CheckEmpty.isNotEmpty(catalog)){
//			url.append("/").append(catalog);
//		}
//		if (CheckEmpty.isNotEmpty(catalog1)){
//			url.append("/").append(catalog1);
//		}
//		if (CheckEmpty.isNotEmpty(page)){
//			url.append("/").append(page);
//		}
//		return  url.toString();
//	}


//	@RequestMapping(value = "/{catalog}/{catalog1}", method = RequestMethod.GET)
//	public String page(@RequestParam String menu_id,@PathVariable String catalog,@PathVariable String catalog1,
//			HttpSession session,
//			HttpServletRequest request,HttpServletResponse response) throws IOException {
//		StringBuilder url = new StringBuilder("");
//		url.append("/modules");
//		if (CheckEmpty.isNotEmpty(catalog)){
//			url.append("/").append(catalog);
//		}
//		if (CheckEmpty.isNotEmpty(catalog1)){
//			url.append("/").append(catalog1);
//		}
//		return  url.toString();
//	}

	private static Menu covertModuleToMenu(BaseModules baseModules) {
		Menu menu = new Menu();
		menu.setId(baseModules.getModuleId());
		menu.setIcon(baseModules.getIconCss());
		menu.setOrder(baseModules.getDisplayIndex());
		menu.setParentId(baseModules.getParentId());
		menu.setUrl(baseModules.getModuleUrl());
		menu.setName(baseModules.getModuleName());
		return menu;

	}

	private List<Menu> getMenuTrees(String userId) {
		List<BaseModules> listMoudle = baseModulesService
				.selectMyModules(userId);
		List<Menu> list = new ArrayList<Menu>();
		for (int i = 0; i < listMoudle.size(); i++) {
			BaseModules bm = listMoudle.get(i);
			if ("0".equals(bm.getParentId())) {
				Menu menu=new Menu();
				menu=covertModuleToMenu(bm);
				List<Menu> chList=new ArrayList<Menu>();
				for (int j = 0; j < listMoudle.size(); j++) {
					if (listMoudle.get(j).getParentId()
							.equals(bm.getModuleId())) {
						chList.add(covertModuleToMenu(listMoudle.get(j)));
					}
				}
				menu.setSubMenus(chList);
				list.add(menu);
			}
		}

		return list;
	}

	private String buildMenuStr(List<Menu> menus, HttpServletRequest request) {
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + request.getContextPath();
		StringBuffer targetStrBuffer = new StringBuffer();
		for (int i = 0; i < menus.size(); i++) {
			Menu menu = menus.get(i);
			boolean hasMemo = "".equals(menu.getMemo());
			boolean hasUrl = "".equals(menu.getUrl());
			String tooltip = " class='tooltips' data-container='body' data-placement='right' data-html='true' data-original-title='%s'";
			// 处理默认打开首页
			// if(menu.getId().equals("menu_1")){
			// tooltip =
			// " class='start active tooltips' data-container='body' data-placement='right' data-html='true' data-original-title='%s'";
			// }
			String menuStrFormatWithSingle = "<li id='%s' "
					+ (hasMemo ? "%s" : tooltip) + " > <a href='"
					+ (hasUrl ? "javascript:;%s" : "%s")
					+ "'> <i class='%s'></i> "
					+ "<span class='title'>%s</span><span></span></a></li>";
			String menuStrFormatWithChildren = "<li id='%s' title='%s'> <a href='"
					+ (hasUrl ? "javascript:;%s" : "%s")
					+ "'  > <i class='%s'></i> "
					+ "<span class='title'>%s</span><span class='arrow'>"
					+ "</span></a>"
					+ "<ul class='sub-menu'>"
					+ "%s"
					+ "</ul></li>";
			targetStrBuffer.append(String.format(
					menu.isHasChildren() ? menuStrFormatWithChildren
							: menuStrFormatWithSingle, menu.getId(), menu
							.getMemo(), basePath + menu.getUrl(), menu
							.getIcon(), menu.getName(),
					buildMenuStr(menu.getSubMenus(), request)));
		}

		return targetStrBuffer.toString();
	}

	public void sessionCreated(HttpSessionEvent se) {
		// TODO Auto-generated method stub

	}

	public void sessionDestroyed(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		String userName = (String)session.getAttribute("userName");
		loginedUser.remove(userName);
	}

	/**
	 * 根据用户名删除会话，使用新会话
	 * @param userName
	 * @param newSession
	 */
	private void userNewSession(String userName, HttpSession newSession) {
		HttpSession oldSession = loginedUser.get(userName);
		if (oldSession != null) {
			try {
				oldSession.removeAttribute("userName");
			} catch (IllegalStateException e) {

			}
		}
		if (userName != null && newSession != null) {
			loginedUser.put(userName, newSession);
		}
	}

	private String getRandomString(int num) {
		String temp = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuffer buffer = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < num; i++) {///// 字母
			int index = random.nextInt(temp.length());
			String rand = temp.substring(index, index + 1);
			buffer.append(rand);
		}
		return buffer.toString();
	}
}
