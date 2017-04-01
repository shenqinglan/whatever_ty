package com.whty.euicc.data.common.constant;

public abstract class DBConstant {
	
	public interface Common{
		public final static String SD_AID = "sdAid";
		
		public final static String CARD_NO = "cardNO";
		
		/** 应用AID */
		public final static String APP_AID = "appAid";

		/** 应用版本号 */
		public final static String APP_VERSION = "appVersion";
		
		/**
		 * 应用提供商
		 */
		public final static String SP_CODE = "spCode";
		
		public final static String ORG_CODE = "orgCode";
		
		public final static String CARD_TYPE = "cardType";
		
		/**
		 * 状态
		 */
		public final static String STATUS = "status";
		/**
		 * 应用状态
		 */
		public final static String APP_STATUS = "appStatus";
	}
	
	
	public interface cardCapInfo{
		public final static String SD_AID = "sdAid";
		
		public final static String CARD_NO = "cardNO";
		
		/** 应用AID */
		public final static String APP_AID = "appAid";

		/** 应用版本号 */
		public final static String APP_VERSION = "appVersion";
	}
	
	/**
	 * 卡内安全域
	 * @author dengzm
	 *
	 */
	public interface cardSdInfo{
		
		public final static String SD_AID = "sdAid";
		
		public final static String CARD_NO = "cardNO";
		
		public final static String STATUS = "status";
		
		
	}

	public interface baseFields {

		/** 数据标识 */
		public final static String BASE_FIELDS_FIELD = "field";

		/** 数据值标识 */
		public final static String BASE_FIELDS_VALUEFIELD = "valueField";

		/** 接口标识 */
		public final static String BASE_FIELDS_COMMAND = "COMMAND";

		/** 任务标识 */
		public final static String BASE_FIELDS_TSMTASKLIST = "TSM_TASK_LIST";

		/** 服务标识 */
		public final static String BASE_FIELD_PLATFORMCODE = "PLATFORM_CODE";
	}

	public interface appInfo {

		/** 应用AID */
		public final static String APP_INFO_APPAID = "appAid";

		/** 应用类型 */
		public final static String APP_INFO_APPTYPE = "appType";

		/** 应用状态 */
		public final static String APP_INFO_APPSTATUS = "appStatus";
	}

	public interface appleteInfo {

		/** 应用AID */
		public final static String APPLETE_INFO_APPAID = "appAid";

		/** 应用版本号 */
		public final static String APPLETE_INFO_APPVERSION = "appVersion";

		/** 应用版本状态 */
		public final static String APPLETE_INFO_VERSIONSTATUS = "versionStatus";
	}

	public interface appInfoRemote {

		/** 应用AID */
		public final static String APP_INFO_REMOTE_APPAID = "appAid";

		/** 应用版本号 */
		public final static String APP_INFO_REMOTE_APPVERSION = "appVersion";

		/** 应用类型 */
		public final static String APP_INFO_REMOTE_APPTYPE = "appType";

		/** 应用状态 */
		public final static String APP_INFO_REMOTE_APPSTATUS = "appStatus";
		
		/**
		 * 是否可见
		 */
		public final static String IS_SHOW = "isShow";
	}

	public interface cardInfo {
		/** 卡片信息 */
		public final static String CARD_INFO = "CardInfo";
		
		/**
		 * 卡的唯一标识
		 */
		public final static String SE_ID = "seId";
		
		/**
		 *  卡状态
		 */
		public final static String STATUS = "status";
		
		/**
		 *  卡号
		 */
		public final static String CARD_NO = "cardNo";
		
	}	
	
	public interface cardAppInfo{
		/** 
		 * 卡的唯一标识
		 */
		public final static String CARD_NO = "cardNO";
		
		/**
		 * 卡的应用AID
		 */
		public final static String APP_AID = "appAid";
		
		/**
		 * 卡状态
		 */
		public final static String STATUS = "status";
		
		/**
		 * 应用类型
		 */
		public final static String APP_TYPE = "appType";
		/**
		 * 应用版本
		 */
		public final static String APP_VERSION = "appVersion";
		
		/**
		 * ISD
		 */
		public final static String ISD_AID = "isdAid";
		
		public final static String APP_PERSONALIZED = "appPersonalized";
		
		/**
		 * 非接默认应用
		 */
		public final static String CF_TYPE = "cfType";
	}
	
	/**
	 * 卡批次
	 * @author Administrator
	 *
	 */
	public interface cardBatch{
		/**
		 * 主键
		 */
		public final static String BATCH = "batch";
		/**
		 * 卡批次状态
		 */
		public final static String STATUS = "status";
	}
	
	/**
	 * 应用关系表
	 * @author Administrator
	 *
	 */
	public interface appRelation{
		/**
		 * 实例AID
		 */
		public final static String INSTANCE_AID = "instanceAid";
		/**
		 * 关联方式
		 * 0：依赖，1：绑定
		 */
		public final static String RELATION_TYPE = "relationType";
	}
	
	public interface appParameters{
		public final static String APP_AID = "appAid";
		public final static String APP_VERSION = "appVersion";
		public final static String SE_TYPE = "seType";
	}
	
	public interface loadFileInfo{
		public final static String LOAD_FILE_AID = "loadFileAid";
	}
}
