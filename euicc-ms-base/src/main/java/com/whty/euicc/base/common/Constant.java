package com.whty.euicc.base.common;

/**
 *  系统常量
 * @ClassName: Constant  
 * @author liyang
 * @date 2015-4-18
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class Constant {
	
	public static final String   UTF8    = "UTF-8";
	
	public interface BaseModulesConstant{
		/**
		 * 根节点
		 */
		public final static String ROOT = "0";
	}
	
	/**
	 * 菜单类型
	 * @author dzm
	 *
	 */
	public interface LeafTypeConstant{
		/**
		 * 菜单父节点
		 */
		public static final short LEAFTYPEPARENT=0;
		
		/**
		 * 菜单子节点
		 */
		public static final short LEAFTYPESON=1;
		
		/**
		 * 菜单按钮节点
		 */
		public static final short LEAFTYPEBUTTON=2;
	}
	
	/**
	 * 用户信息
	 * @author dzm
	 *
	 */
	public interface BaseUsersConstant{
		public final static String USERID = "userId";
		public final static String USERNAME = "username";
		
		public final static String CURRENTUSER = "CURRENT_USER";
		/**
		 * 锁定
		 */
		public final static String LOCKED = "1";
	}
	
	
	public  static final String SAVESTATUSADD="add";
	
	public static final String SAVESTATUSUPDATE="update";
	
	//菜单父节点
	public static final short LEAFTYPEPARENT=0;
	
	//菜单子节点
	public static final short LEAFTYPESON=1;
	
	//菜单按钮节点
	public static final short LEAFTYPEBUTTON=2;
	
	//固件版本预上线
	public static final String  PREONLINE = "00";
	//固件版本上线
	public static final String ONLINE = "01";
	//固件版本下线
	public static final String OFFLINE = "02";
	
	/**
	 * 按天统计
	 * */
	public static final String BYDAY = "1";
	
	/**
	 * 按月统计
	 */
	public static final String BYMONTH = "2";
	
	/**
	 * 按季统计
	 */
	public static final String BYSEASON = "3";
	
	/**
	 * 按年统计
	 */
	public static final String BYYEAR = "4";
	
	
	// 应用提供方附件文件夹
    public static final String   AP_ATTACH    = "aps";
    
    
    public final static String   EUICC_OP_APP_TYPE         = "EUICC_OP_APP_TYPE";

    public final static String   EUICC_OP_AP_TYPE          = "EUICC_OP_AP_TYPE";

    public final static String   EUICC_OP_APPVER_TYPE      = "EUICC_OP_APPVER_TYPE";

    public final static String   EUICC_CARD_TEST_TYPE      = "EUICC_CARD_TEST_TYPE";

    public final static String   EUICC_BASE_TYPE           = "EUICC_BASE_TYPE";

    public final static String   Ap_ROLE_ID              = "AP_ROLE";

    public final static String   EUICC_APP_PUSH_TYPE       = "EUICC_APP_PUSH_TYPE";

    public final static String   EUICC_PRODUCT_STATUS      ="EUICC_PRODUCT_STATUS";
    
    public final static String   EUICC_AP_STATUS           ="EUICC_AP_STATUS";
    
    public final static String   SD_TYPE                 ="SD_TYPE";
    
    public final static String   EUICC_CARD_TYPE           ="EUICC_CARD_TYPE";
    
    public final static String   EUICC_CARD_STATUS         ="EUICC_CARD_STATUS";
    
    public final static String EUICC_APP_OP_TYPE = "EUICC_APP_OP_TYPE";
    
    //产品操作类型
    public final static String EUICC_PRODUCT_OP_TYPE = "EUICC_PRODUCT_OP_TYPE";
    /** 保存session中的验证码key */
    public static final String RANDOM_CODE = "sRand";
}
