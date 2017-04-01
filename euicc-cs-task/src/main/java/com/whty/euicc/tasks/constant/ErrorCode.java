// Copyright (C) 2012 WHTY
package com.whty.euicc.tasks.constant;

/**
 * 异常编号
 * 
 * 保留000000(成功)和(FFFFFF)失败
 * 
 * 总长为6位十六进制字符
 * 
 * @author Administrator
 * 
 */
public interface ErrorCode {

	// 1.系统级错误码
	public static final String ERROR_100000 = "100000"; // 无效的会话 ID；访问被拒绝
	// 2.终端错误码
	public static final String ERROR_200000 = "200000"; // 终端与服务器失去连接
	public static final String ERROR_200001 = "200001"; // 短信等待超时
	public static final String ERROR_200003 = "200003"; // 未收到响应
	// 3.原子任务错误码
	public static final String ERROR_300000 = "300000"; // 应用下载
	public static final String ERROR_300001 = "300001"; // 应用个人化
	public static final String ERROR_300002 = "300002"; // 应用删除
	public static final String ERROR_300003 = "300003"; // 应用锁定
	public static final String ERROR_300004 = "300004"; // 应用解锁
	public static final String ERROR_300005 = "300005"; // 应用迁移
	public static final String ERROR_300006 = "300006"; // 应用升级
	public static final String ERROR_300007 = "300007"; // 安全域创建
	public static final String ERROR_300008 = "300008"; // 安全域删除
	public static final String ERROR_300009 = "300009"; // 安全域锁定
	public static final String ERROR_300010 = "300010"; // 安全域解锁
	public static final String ERROR_300011 = "300011"; // 安全域个人化
	public static final String ERROR_300012 = "300012"; // 安全载体锁定
	public static final String ERROR_300013 = "300013"; // 安全载体解锁
	public static final String ERROR_300014 = "300014"; // 安全载体终止
	public static final String ERROR_300015 = "300015"; // 安全载体应用列表查询
	public static final String ERROR_300016 = "300016"; // 全局应用列表查询
	public static final String ERROR_300017 = "300017"; // 安全载体状态变更通知
	public static final String ERROR_300018 = "300018"; // 应用AID申请
	public static final String ERROR_300019 = "300019"; // 卡片信息同步
	public static final String ERROR_300020 = "300020"; // 安全域状态不满足要求或安全域上安装有应用
	public static final String ERROR_300021 = "300021"; // 安全载体状态不满足要求
	public static final String ERROR_300022 = "300022"; // 没有找到发布状态的应用版本
	public static final String ERROR_300023 = "300023"; // 关联的应用没有发布状态的应用版本
	public static final String ERROR_300024 = "300024"; // 关联的依赖包数据库中未找到
	public static final String ERROR_300025 = "300025"; // 终端响应数据为空
	public static final String ERROR_300026 = "300026"; // 外部认证错误
	public static final String ERROR_300027 = "300027"; // 安全通道协议为空
	public static final String ERROR_300028 = "300028"; // InitializeUpdate出错
	public static final String ERROR_300029 = "300029"; // 卡片空间同步错误
	public static final String ERROR_300030 = "300030"; // APDU指令执行结果
	public static final String ERROR_300031 = "300031"; // 卡上应用判断出错
	public static final String ERROR_300032 = "300032"; // 安全域权限判断
	public static final String ERROR_300033 = "300033"; // 卡片上没有安装该安全域
	public static final String ERROR_300034 = "300034"; // 卡片空间大小比较
	public static final String ERROR_300035 = "300035"; // 会话密钥生成
	public static final String ERROR_300036 = "300036"; // 卡片上没有安装该应用
	public static final String ERROR_300037 = "300037"; // token生成失败
	public static final String ERROR_300038 = "300038"; // APDU命令响应数据问题
	public static final String ERROR_300039 = "300039"; // 统一服务平台应用状态变更失败
	public static final String ERROR_300040 = "300040"; // 获取证书数据错误
	public static final String ERROR_300041 = "300041"; // 安全域不存在
	public static final String ERROR_300042 = "300042"; // 统一服务平台不存在该应用
	public static final String ERROR_300043 = "300043"; // 统一服务平台应用AID为空
	public static final String ERROR_300044 = "300044"; // 统一服务平台不存在该安全载体
	public static final String ERROR_300045 = "300045"; // CA未成功返回请求的数据
	public static final String ERROR_300046 = "300046"; // 安全载体已注册
	public static final String ERROR_300047 = "300047"; // select安全域
	public static final String ERROR_300048 = "300048"; // 统一服务平台已存在该应用
	public static final String ERROR_300049 = "300049"; // 应用授权失败
	public static final String ERROR_300050 = "300050"; // 安全载体已被激活过
	public static final String ERROR_300051 = "300051";// 未找到安装参数
	public static final String ERROR_300052 = "300052";// 补丁下载错误，补丁内容为空
	public static final String ERROR_300053 = "300053";// 获取COS和补丁版本错误
	// 4.其他异常（网络异常，数据库异常等)
	public static final String ERROR_400000 = "400000"; // 网络断开
	public static final String ERROR_400001 = "400001"; // 端口已被占用
	public static final String ERROR_400002 = "400002"; // 数据库异常
	public static final String ERROR_400003 = "400003"; // 数据库无SEID信息
	public static final String ERROR_400004 = "400004"; // 原子任务未定义
	public static final String ERROR_400005 = "400005"; // 数据库找不到相关批次信息
	public static final String ERROR_400006 = "400006"; // 数据库主安全域密钥信息缺失
	public static final String ERROR_400007 = "400007"; // 卡片信息注册表数据不存在或者不唯一
	public static final String ERROR_400008 = "400008"; // 卡片信息表数据不存在或不唯一
	public static final String ERROR_400009 = "400009"; // 业务模版表数据不存在或不唯一
	public static final String ERROR_400011 = "400011"; // 系统参数表数据不存在或不唯一
	public static final String ERROR_400012 = "400012"; // 短信发送失败
	public static final String ERROR_400013 = "400013"; // 平台卡片信息注册表数据不存在或者不唯一
	public static final String ERROR_400014 = "400014"; // IMSI为空
	public static final String ERROR_400015 = "400015"; // TSM地址未找到
	public static final String ERROR_400016 = "400016"; // 数据库应用信息缺失
	public static final String ERROR_400017 = "400017"; // 数据库应用所属安全域息缺失
	// 5.消息处理异常
	public static final String ERROR_500000 = "500000"; // 报文字段长度不足指定长度
	public static final String ERROR_500001 = "500001"; // 报文字段长度超过指定长度
	public static final String ERROR_500002 = "500002"; // 报文SE_ID为空
	public static final String ERROR_500003 = "500003"; // 接口命令未定义
	public static final String ERROR_500004 = "500004"; // 接口命令初始化失败
	public static final String ERROR_500005 = "500005"; // 平台代码未正确定义
	public static final String ERROR_500006 = "500006"; // 报文字段必填项校验失败
	public static final String ERROR_500007 = "500007"; // SE_ID和卡号均不存在
	public static final String ERROR_500008 = "500008"; // 会话标识为空
	public static final String ERROR_500009 = "500009"; // 报文格式不正确
	public static final String ERROR_500010 = "500010"; // 消息处理发生异常
	public static final String ERROR_500011 = "500011"; // 未能匹配到下一步流程
	public static final String ERROR_500012 = "500012"; // 服务器未响应
	public static final String ERROR_500013 = "500013"; // 重复请求
	public static final String ERROR_500014 = "500014"; // 业务流程未走完结束
	public static final String ERROR_500015 = "500015"; // 引擎未返回下一步执行步骤
	public static final String ERROR_500016 = "500016"; // 未找到本TSM的机构代码
	public static final String ERROR_500017 = "500017"; // 报文域长度不为偶数
	public static final String ERROR_500018 = "500018"; // 接口定义XML解析发生错误
	public static final String ERROR_500019 = "500019"; // 引擎发送消息后收到错误响应
	// 6.模板错误
	public static final String ERROR_600000 = "600000"; // 模板不存在或解析出错
	public static final String ERROR_600001 = "600001"; // 模板配置(from)有误:
														// command为空
	public static final String ERROR_600002 = "600002"; // 模板配置(from)有误:
														// 模版配置的命令和接收的请求命令不匹配
	public static final String ERROR_600003 = "600003"; // 模板配置(from)有误:
														// 请求的命令未配置
	public static final String ERROR_600004 = "600004"; // 模板配置(job)有误:
														// command为空
	public static final String ERROR_600005 = "600005"; // 模板配置(from)有误:
														// code值和消息来源TSM不匹配
	public static final String ERROR_600006 = "600006";// 没有找到应用
	// 7.平台标准化接入异常信息
	public static final String B0000 = "0000";// 业务处理正常
	public static final String B0001 = "B0001";// 业务处理异常
	public static final String B0002 = "B0002";// 指令执行失败
	public static final String B1001 = "B1001";// 机构不存在
	public static final String B1002 = "B1002";// 机构没有此权限
	public static final String B1003 = "B1003";// 机构没有登录
	public static final String B1004 = "B1004";// 机构系统不是启动状态
	public static final String B1005 = "B1005";// 机构没有公钥证书
	public static final String B1006 = "B1006";// 机构公钥证书无效
	public static final String B1007 = "B1007";// 机构会话不存在或会话状态无效
	public static final String B2001 = "B2001";// 应用已经存在
	public static final String B2002 = "B2002";// 应用状态已经是该状态
	public static final String B2003 = "B2003";// 应用不存在
	public static final String B2004 = "B2004";// 应用下载失败
	public static final String B2005 = "B2005";// 应用删除失败
	public static final String B2006 = "B2006";// 应用已下载
	public static final String B3001 = "B3001";// 辅助安全域信息不存在
	public static final String B3002 = "B3002";// 辅助安全域参数错误
	public static final String B3003 = "B3003";// 获取公钥失败
	public static final String B3004 = "B3004";// 辅助安全域初始密钥解密错误
	public static final String B3005 = "B3005";// 辅助安全域已安装
	public static final String B4001 = "B4001";// 安全载体数据异常
	public static final String B4002 = "B4002";// 安全载体不存在
	
	public static final String REJECTCODE_B2008   = "B2008"; // SE会话信息不存在或SE验证状态未通过
	public static final String REJECTINFO_B2008 = "SE会话信息不存在或SE验证状态未通过";
}
