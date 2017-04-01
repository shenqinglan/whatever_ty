package com.whty.euicc.base.common;

public abstract class DBConstant {
	
	/**
	 * 数据字典
	 * @author dengzm
	 *
	 */
	public interface BaseFieldsDirectory{
		/**
		 * 
		 */
		public static final String MERSTATUS = "merStatus";
		/**
		 * 交易状态
		 */
		public static final String TRADESTATUS = "tradeStatus";
		/**
		 * 交易类型
		 */
		public static final String TRADETYPE = "tradeType";
		/**
		 * 设备类型
		 */
		public static final String SNTYPES = "snTypes";
		/**
		 * 统计方式
		 */
		public static final String STATISTYPE = "statisType";
		/**
		 * 季节值
		 */
	}
	
	/**
	 * 数据字典的启用、停用状态
	 * @author dengzm
	 *
	 */
	public interface BaseFieldsEnabledStatus{
		/**
		 * 启用
		 */
		public static final short ENABLED = 1;
		
		/**
		 * 停用
		 */
		public static final short DISABLED = 0;
	}
	
	/**
	 * 数据字典短信相关
	 * @author Administrator
	 *
	 */
	public interface BaseFieldsSMS{
		/**
		 * 短信内容字段
		 */
		public static final String ENFIELDNAME = "sms";
		/**
		 * 短信验证码前缀
		 */
		public static final String SMSPRE = "smsPre";
		/**
		 * 审核通过激活成功
		 */
		public static final String ACTIVATESUCCESS = "activateSuccess";
		/**
		 * 审核不通过前缀
		 */
		public static final String CHECKFAILPRE = "checkFailPre";
		/**
		 * 审核不通过后缀
		 */
		public static final String CHECKFAILPOST = "checkFailPost";
	}

	/**
	 * 商户状态
	 * @author Administrator
	 *
	 */
	public interface MerStatus{
		
		
		/** 初始状态，商户注册账号*/
		public static final String INITIAL = "0";
		
		/** 登记*/
		public static final String REGISTER = "00";
		
		/** 待审核，商户开通*/
		public static final String OPEN = "01";
		
		/** 启用*/
		public static final String START = "02";
				
		/** 审核不通过*/
		public static final String NOTAPPR = "03";
		
		/** 停用*/
		public static final String STOP = "04";
		
		/** 激活*/
		public static final String ACTIVATE = "05";

		
	}
	
	/**
	 * 商户卡片绑定状态
	 * @author Administrator
	 *
	 */
	public interface MerCardStatus{
		
		/** 绑定*/
		public static final String BIND = "00";
		
		/** 解绑*/
		public static final String UNBIND = "01";
	}
	
	/**
	 * APK状态
	 * @author Administrator
	 *
	 */
	public interface ProductApkStatus{
		/** 上线*/
		public static final String ONLINE = "01";
	}
	/**
	 * 固件版本状态
	 * @author Administrator
	 *
	 */
	public interface ProductFirmwareStatus{
		/** 上线*/
		public static final String ONLINE = "01";
	}
	/**
	 * 交易结果
	 */
	public interface OrderStatus{
		/**交易成功 */
		public static final String SUCCESS = "00";
		/**交易无效 */
		public static final String INVALID = "01";
	}
	
	/**
	 * 商户设备绑定状态
	 * @author Administrator
	 *
	 */
	public interface MerDeviStatus{
		
		/** 绑定*/
		public static final String BIND = "00";
		
		/** 解绑*/
		public static final String UNBIND = "01";
	}
	
	/**
	 * 绑定方式
	 * @author Administrator
	 *
	 */
	public interface BINDTYPE{
		
		/** 平台绑定*/
		public static final String PLATFORM = "00";
		
		/** 手机绑定*/
		public static final String PHONE = "01";
	}
	
	public interface BaseUserStatus{
		
		/** 平台用户启用*/
		public static final String START = "0";
		
		/** 平台用户停用*/
		public static final String STOP = "1";
	}
	
	
}
