package com.whty.euicc.data.common.constant;

/**
 * tsm使用的常量
 * 
 * @author dengjun
 * @since 2014-9-29
 */
public abstract class EuiccConstant {
	
	public interface Rsa{
		public static final String MOBILE = "1";
		public static final String TELECOM = "3";
		public static final String UNICOM = "2";
		public static final String STANDARD = "4";
		public static final String DES = "5";
	}
	
	/**
	 * 安全域权限
	 * @author Administrator
	 *
	 */
	public interface SdPrivilege{
		
		public static final String DM_DAP = "E08000";
		
		public static final String Dm = "A00000";
		
		public static final String AM_DAP = "C04000";
		
		public static final String AM = "804000";
		
		public static final String DM_SAMSUNG = "808000";
		
		public static final String AM_SAMSUNG = "80E000";
	}
	
	/**
	 * 替换密钥状态
	 * @author dengzm
	 *
	 */
	public interface SdPutkeyStatus{
		/**
		 * 已创建
		 */
		public static final String HASREPLACE = "1";
		
		/**
		 * 未创建
		 */
		public static final String NOREPLACE = "0";
	}
	
	/**
	 *应用查询状态
	 */
	public interface AppQueryStatus{
		/**
		 * 未安装
		 */
		public static final String UNINSTALL = "00";
		/**
		 * 已安装
		 */
		public static final String INSTALL = "01";
		/**
		 * 已经下载未个人化
		 */
		public static final String UNPERSONALIZED = "02";
		/**
		 * 已经个人化未激活
		 */
		public static final String INACTIVE = "03";
	}
	
	/**
	 * 个人化状态
	 * @author Administrator
	 *
	 */
	public interface AppPersonalized{
		/**
		 * 未个人化
		 */
		public static final String UNPERSONALIZED = "0";
		/**
		 * 个人化
		 */
		public static final String PERSONALIZED = "1";
		/**
		 * 激活
		 */
		public static final String ACTIVE = "2";
	}
	
	/**
	 * 卡片状态
	 * @author Administrator
	 *
	 */
	public interface CardAppStatus{
		
		public static final String CardAppStatus = "CARD_APP_STATUS";
		public static final String CardSsdStatus = "CARD_SSD_STATUS";
		/**
		 * 已安装
		 */
		public static final String INSTASLL = "00";
		
		/**
		 * 删除
		 */
		public static final String WAIRTDELETE = "01";
	}
	
	/**
	 * 卡应用类型
	 * @author Administrator
	 *
	 */
	public interface CardAppType{
		public static final String CardAppType = "CARD_APP_TYPE";
		/**
		 * 辅助安全域
		 */
		public static final String SD = "0";
		
		public static final String APP = "1";
		
		public static final String LOADFILE = "2";
	}

	/**
	 * 卡类型
	 * 
	 * @author dengjun
	 * 
	 */
	public interface Cardtype {
		public static final String SWPSIM = "0";// swp-sim
		public static final String SD = "1";// SD
		public static final String TERMINAL= "2";// 终端
		public static final String ESE = "3";// eSe
		public static final String TCORE = "4";// t-core
		public static final String ANYPAY = "5";// 安付易
	}

	/**
	 * 卡片分类
	 * @author Administrator
	 *
	 */
	public interface CardCategory{
		/**
		 * 正式卡片
		 */
		public static final String FORMAL = "0";
		/**
		 * 测试卡片
		 */
		public static final String TEST = "1";
	}
	
	/**
	 * 卡状态 GP5.1.1，卡生命周期的五种状态 卡发行前阶段：OP_READY和INITIALIZED
	 * 卡发行后阶段：SECURED、CARD_LOCKED、TERMINATED TERMINATED可以发生在卡生命周期的任何阶段
	 * 
	 * @author dengjun
	 * 
	 */
	public interface CardStatus {
		/**
		 * OP_READY状态 ssd可以被卸载或安装
		 * 
		 */
		public static final String OP_READY = "01";
		/**
		 * OP_READY to INITIALIZED 不可逆 部分数据已初始化（isd密钥） 此状态卡还不能发给持卡者
		 */
		public static final String INITIALIZED = "07";
		/**
		 * INITIALIZED to SECURED 不可逆 isd包含了所有的密钥和安全因素
		 * 
		 */
		public static final String SECURED = "0F";
		/**
		 * SECURED to CARD_LOCKED 可逆
		 * 
		 */
		public static final String CARD_LOCKED = "7F";
		/**
		 * 任何其他状态 to TERMINATED 不可逆 此状态下，所有的APDU命令将被发送到isd，isd只对GET DATA响应
		 * 
		 */
		public static final String TERMINATED = "FF";
	}
	
	/**
	 * GP 9.1.1 表 9-4：应用的生命周期编码
	 * @author Administrator
	 *
	 */
	public interface AppLifeCycle{
		public static final String INSTALLED = "03";
		public static final String SELECTABLE = "07";
		public static final String PERSONALIZED = "0F";
		public static final String LOCKED = "83";
		/**
		 * 此项是自定义，不属于GP
		 */
		public static final String TERMINATED = "FF"; 
		public static final String ACTIVE = "EF"; 
	}
	
	/**
	 * GP 9.1.1 表 9-6：发行者安全域的生命周期编码
	 * @author dengzm
	 *
	 */
	public interface IsdLifeCycle{
		public static final String OP_READY = "01";
		public static final String INITIALIZED = "07";
		public static final String SECURED = "0F";
		public static final String CARD_LOCKED = "7F";
		public static final String TERMINATED = "FF";
	}
	
	/**
	 * GP 9.1.1 表 9-5：安全域的生命周期编码
	 * @author dengzm
	 *
	 */
	public interface SdLifeCycle{
		public static final String INSTALLED = "03";
		public static final String SELECTABLE = "07";
		public static final String PERSONALIZED = "0F";
		public static final String LOCKED = "83";
		/**
		 * 此项是自定义，不属于GP
		 */
		public static final String TERMINATED = "FF"; 
	}
	
	/**
	 * 
	 * 卡片命令返回的可执行的装载文件的生命周期:01 :LOADED
	 * 应用的生命周期:03:INSTALLED 07:SELECTABLE 0F: Application Specific State  83、87、8F: LOCKED
	 * 安全域的生命周期:03: INSTALLED 07: SELECTABLE 0F:PERSONALIZED 83、87、8F: LOCKED
	 * 
	 * @author xlping
	 * 
	 */
	public interface AppOrSdLifecycle {

		public static final String LOADED = "01";

		public static final String INSTALLED = "03";

		public static final String SELECTABLE = "07";

		public static final String SPECIFIC_STATE = "0F";

		public static final String LOCKED = "83";
	}

	/**
	 * 任务节点状态
	 * 
	 * @author dengjun
	 * 
	 */
	public interface TaskStatus {
		public static final String ZERO = "0";// 默认为节点异常状态
		public static final String ONE = "1";
		public static final String TWO = "2";
		public static final String THREE = "3";
		public static final String FOUR = "4";
		public static final String FIVE = "5";
		public static final String SIX = "6";
		public static final String SEVEN = "7";
		public static final String EIGHT = "8";
	}

	/**
	 * 应用类型 1为普通GP卡规范，2为GP-TEE规范 影响应用下载流程
	 * 
	 * @author dengjun
	 * 
	 */
	public interface AppType {
		/**
		 * 普通应用 ese,swp-sim为普通应用
		 */
		public static final String COMMANDAPP = "1";

		/**
		 * 可信应用
		 */
		public static final String TCOREAPP = "2";
		
		public static final String COM_APP = "01";
		
		public static final String FIN_APP = "02";
	}
	
	/**
	 * 第三方应用状态
	 * @author dengzm
	 *
	 */
	public interface AppRemoteStatus{
		/**
		 * 待审批
		 */
		public static final String WAITAUDIT = "1";
		/**
		 * 待上线
		 */
		public static final String WAITDONLINE = "2";
		/**
		 * 审核不通过
		 */
		public static final String NOPASE = "21";
		/**
		 * 上线
		 */
		public static final String ONLINE = "3";
		
		/**
		 * 下线
		 */
		public static final String OFFLINE = "4";
		
	}

	/**
	 * 应用状态
	 * 
	 * @author dengjun
	 *
	 */
	public interface AppStatus{
		/**
		 * 待提交
		 */
		public static final String APPLY = "0";
		/**
		 * 待审批
		 */
		public static final String WAITAUDIT = "1";
		/**
		 * 待上线
		 */
		public static final String WAITDONLINE = "2";
		/**
		 * 上线
		 */
		public static final String ONLINE = "3";
		
		/**
		 * 下线
		 */
		public static final String OFFLINE = "4";
		/**
		 * 注销
		 */
		public static final String LOGOUT = "5";
	}
	
	/**
	 * 是否显示
	 * @author Administrator
	 *
	 */
	public interface IsShow{
		/**
		 * 可见
		 */
		public static final String VISIBLE = "1";
		/**
		 * 不可见
		 */
		public static final String INVISIBLE = "0";
	}
	
	/**
	 * 应用版本状态
	 * @author dengjun
	 * 
	 */
	public interface AppVersionStatus {
		/**
		 * 申请
		 */
		public static final String APPLY = "0";
		/**
		 * 待审批
		 */
		public static final String WAITAUDIT = "1";
		/**
		 * 调测
		 */
		public static final String DEBUG = "2";
		/**
		 * 发布
		 */
		public static final String DEPLOY = "3";
		/**
		 * 暂停
		 */
		public static final String PAUSE = "4";
		/**
		 * 下线
		 */
		public static final String OFFLINE = "5";
	}

	/**
	 * 应用所在地 本地应用和异地应用
	 * 
	 * @author dengjun
	 * 
	 */
	public interface AppSite {
		/**
		 * 
		 */
		public static final String LOCAL = "1";
		/**
		 * 
		 */
		public static final String REMOTE = "2";
	}

	/**
	 * 安全域类型
	 * 
	 * @author dengjun
	 * 
	 */
	public interface DomainType {
		/**
		 * ISD,Issuer Security Domain 主安全域
		 */
		public static final String ISD = "00";
		/**
		 * Supplementary Security Domain 辅助安全域
		 */
		public static final String SSD = "01";
		/**
		 * 
		 */
		public static final String SUBISD = "04";
		/**
		 * 
		 */
		public static final String SUBSSD = "05";
	}

	/**
	 * 设备类型
	 * 
	 * @author Ocea
	 */
	public interface DeviceType {
		/**
		 * Remote Server
		 */
		public static final String SERVER = "0";
		/**
		 * Mobile Client
		 */
		public static final String CLIENT = "1";
	}

	/**
	 * 应用信息
	 * 
	 * @author Ocea
	 * 
	 */
//	public interface AppBaseInfo {
//		public static final String APP_BASE_INFO0 = "AppBaseInfo";
//	}

	/**
	 * 关联AID类型
	 * @author Administrator
	 *
	 */
	public interface AidType{
		/**
		 * 应用实例
		 */
		public static final String INSTANCE = "0";
		/**
		 * 应用包
		 */
		public static final String CAP = "1";
	}
	
	/**
	 * 关联方式
	 * 0：依赖，1：绑定
	 * @author Administrator
	 *
	 */
	public interface AppRelationType{
		/**
		 * 依赖
		 */
		public static final String DEPENDENCE = "0";
		/**
		 * 绑定
		 */
		public static final String BINDING = "1";
	}
	
	/**
     * 报文的类型
     * 00001001    个人化操作请求
     * 00001002    个人化指令处理
     * 00001003    个人化结束指令，个人化发起
     * 00001004    个人化结束指令，tsm发起
     */
	public interface cmdType{
		//个人化操作请求
		public static final String ONE = "00001001";
		//个人化指令处理
		public static final String TWO = "00001002";
		//个人化结束指令，个人化发起
		public static final String THREE = "00001003";
		//个人化结束指令，tsm发起
		public static final String FOUR = "00001004";
	}
	
	public interface sdType{
		public static final String SUB_ISD = "00";
		
		public static final String SSD = "01";
		
		public static final String SUB_SSD = "02";
	}
	
	/**
	 * 行业前置识别的receiver标识
	 * @author Administrator
	 *
	 */
	public interface Receiver{
		//厦门个人化系统
		public static final String XM = "XM";
		//洪城个人化系统
		public static final String HC = "HC";
	}
	
	/**
	 * 是否需要个人化标识
	 */
	public interface  PersonalFlag{
		//需要个人化
		public static final String need = "1";
		//不用个人化
		public static final String noNeed = "0";
	}
	
	
	/**
	 *  app_seq_info 下载相关标识
	 */
	public interface AppDownloadFlag{
		//下载成功
		public static final String download_success = "0";
		//下载失败
		public static final String download_fail = "1";
		//下载标识 1
		public static final String download_type = "1";
	}
		
	/**
	 * app_seq_info个人化相关标识
	 */
	public interface AppPersonalizeFlag{
		//个人化成功
		public static final String personalize_success = "0";	
		//个人化标识 5
		public static final String personalize_type = "5";
	}
	
	/**
	 * app删除相关标识
	 */
	public interface AppDeleteFlag{
		//删除成功
		public static final String delete_success = "0";	
		//删除标识 2
		public static final String delete_type = "2";
	}
	
	
	/**
	 * app切换相关标识
	 */
	public interface AppSwitchFlag{
		//默认非接应用
		public static final String default_non_contact = "CF0180";	
		//不是默认非接应用
		public static final String un_default_non_contact = "CF0100";
	}
	
	/**
	 * 合作方状态
	 */
	public interface PartnerStatus {
		// 有效
		public static final String partner_status_on = "1";
		// 无效
		public static final String partner_status_off = "0";
	}
	
}
