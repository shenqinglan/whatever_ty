package com.whty.euicc.tasks.common;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.whty.euicc.command.cmd.BaseCommand;
import com.whty.euicc.command.dto.AppBaseInfo;
import com.whty.euicc.command.dto.CardBatchInfoVo;
import com.whty.euicc.command.dto.CardDto;
import com.whty.euicc.command.dto.KeySessionInfo;
import com.whty.euicc.command.dto.SdInfoVo;
import com.whty.euicc.command.dto.SdKeys;
import com.whty.euicc.common.exception.InvalidParameterException;
import com.whty.euicc.common.utils.SecurityException;
import com.whty.euicc.packets.message.MsgBody;
import com.whty.euicc.packets.message.MsgBodyUtil;
import com.whty.euicc.packets.message.request.Rapdu;
import com.whty.euicc.packets.message.request.RequestMsgBody;
import com.whty.euicc.packets.message.response.Capdu;
import com.whty.euicc.packets.message.response.ResponseMsgBody;
import com.whty.euicc.tasks.constant.Constant;
import com.whty.euicc.tasks.exception.TaskException;

/**
 * 抽象任务类<br>
 * 
 * 
 * @author bjw@whty.com.cn
 * @date 2014年10月30日 下午2:00:38
 */
public abstract class Task implements Constant {

	private static final Logger logger = LoggerFactory.getLogger(Task.class);
	/**
	 * 步骤的参数对象
	 */
	private BaseCommand<RequestMsgBody, ResponseMsgBody> baseCommand;

	/**
	 * 设置命令，执行任务
	 * 
	 * @author dengjun
	 * @since 2014-9-27
	 * @param baseCommand
	 * @throws Exception
	 */
	public void setBaseCommand(
			BaseCommand<RequestMsgBody, ResponseMsgBody> baseCommand) {
		this.baseCommand = baseCommand;
	}

	protected BaseCommand<RequestMsgBody, ResponseMsgBody> getBaseCommand() {
		return baseCommand;
	}

	/**
	 * 获取请求数据体<快捷方法>
	 * 
	 * @return
	 */
	public MsgBody getMsgBody() {
		return this.baseCommand.getRequestBody();
	}

	

	/**
	 * 添加构造的响应APDU<快捷方法>
	 * 
	 * @param apdu
	 * @param needGpApdu
	 *            是否需要引入gp规范级别
	 * @throws TaskException
	 * @throws InvalidParameterException
	 */
	public void addApdu4TCore(String apdu, String compsta, boolean needGpApdu)
			throws TaskException, InvalidParameterException {
		if (needGpApdu) {
		}
		this.addCapdu(new Capdu(apdu, compsta));
	}

	

	/**
	 * 添加响应APDU<快捷方法>
	 * 
	 * @param capdu
	 */
	public void addCapdu(Capdu capdu) {
		this.getBaseCommand().addCapdu(capdu);
	}

	/**
	 * 获取请求的APDU<快捷方法>
	 * 
	 * @return
	 */
	public String getRapdu() {
		List<Rapdu> rapdus = MsgBodyUtil.getRapdu(this.baseCommand
				.getRequestBody());
		if (CollectionUtils.isEmpty(rapdus))
			return null;
		if (logger.isDebugEnabled()) {
			for (Rapdu rapdu : rapdus) {
				logger.info("rapdu:{}", rapdu.getApdu());
			}
		}
		String apdu = rapdus.get(rapdus.size() - 1).getApdu();
		return apdu;
	}

	/************************************************/
	/*** (1)请求数据快捷方式方法 *****/
	/************************************************/

	/**
	 * 获取交易参考号
	 * 
	 * @return
	 */
	public String getTradeRefNO() {
		return getBaseCommand().getMsgHeader().getTradeRefNO();
	}

	/**
	 * 获取请求的卡号
	 * 
	 * @return
	 */
	public String getCardNO() {
		return MsgBodyUtil.getCardNO(getBaseCommand().getRequestBody());
	}

	public String getSeID() {
		return MsgBodyUtil.getSeID(getBaseCommand().getRequestBody());
	}

	/**
	 * 获取请求的辅助安全域标识
	 * 
	 * @return
	 */
	public String getSsdAID() {
		return MsgBodyUtil.getSsdAID(getBaseCommand().getRequestBody());
	}

	public String getAppAID() {
		return MsgBodyUtil.getAppAID(getBaseCommand().getRequestBody());
	}

	public String getDeviceType() {
		return this.getBaseCommand().getMsgHeader().getDeviceType();
	}

	public String getAppVersion() {
		return MsgBodyUtil
				.getAppVersion(this.getBaseCommand().getRequestBody());
	}

	public String getDelRes() {
		return MsgBodyUtil.getDelRes(this.getBaseCommand().getRequestBody());
	}

	public String getInstRes() {
		return MsgBodyUtil.getInstRes(this.getBaseCommand().getRequestBody());
	}

	public String getAppInstalledTag() {
		return MsgBodyUtil.getAppInstalledTag(this.getBaseCommand()
				.getRequestBody());
	}

	public String getAppStatus() {
		return MsgBodyUtil.getAppStatus(this.getBaseCommand().getRequestBody());
	}

	public String getPartnerOrgCode() {
		return MsgBodyUtil.getPartnerOrgCode(this.getBaseCommand()
				.getRequestBody());
	}

	public List<Rapdu> getRapduList() {
		return MsgBodyUtil.getRapdu(this.getBaseCommand().getRequestBody());
	}

	/************************************************/
	/*** (2)中间变量快捷方式方法 *****/
	/************************************************/
	/**
	 * 设置卡端会话信息<快捷方法>
	 * 
	 * @param keySessionInfo
	 */
	public void setKeySessionInfo(KeySessionInfo keySessionInfo) {
		this.getBaseCommand().getContext().setKeySessionInfo(keySessionInfo);
	}

	/**
	 * 获取卡端会话信息<快捷方法>
	 * 
	 * @return
	 */
	public KeySessionInfo getKeySessionInfo() {
		return this.getBaseCommand().getContext().getKeySessionInfo();
	}

	/**
	 * 设置本地应用信息<快捷方法>
	 * 
	 * @param localeAppInfo
	 */
	public void setLocaleAppInfo(AppBaseInfo localeAppInfo) {
		this.getBaseCommand().getContext().setLocaleAppInfo(localeAppInfo);
	}

	/**
	 * 设置共享应用信息<快捷方法>
	 * 
	 * @param sharedAppInfo
	 */
	public void setSharedAppInfo(AppBaseInfo sharedAppInfo) {
		this.getBaseCommand().getContext().setSharedAppInfo(sharedAppInfo);
	}

	/**
	 * 设置sd密钥信息
	 * 
	 * @param sdKeys
	 */
	public void setSdKeys(SdKeys sdKeys) {
		this.getBaseCommand().getContext().setSdKeys(sdKeys);
	}

	/**
	 * 获取sd密钥信息
	 * 
	 * @return
	 */
	public SdKeys getSdKeys() {
		return this.getBaseCommand().getContext().getSdKeys();
	}

	/**
	 * 获取安全通道
	 * 
	 * @return
	 */
	public String getScp() {
		return this.getBaseCommand().getContext().getScp();
	}

	/**
	 * 设置安全通道
	 * 
	 * @param scp
	 */
	public void setScp(String scp) {
		this.getBaseCommand().getContext().setScp(scp);
	}

	/**
	 * 设置卡信息<快捷方法>
	 * 
	 * @param cardDto
	 */
	public void setCardDto(CardDto cardDto) {
		this.getBaseCommand().getContext().setCardDto(cardDto);
	}

	/**
	 * 获取卡信息<快捷方法>
	 * 
	 * @return
	 */
	public CardDto getCardDto() {
		return this.getBaseCommand().getContext().getCardDto();
	}

	/**
	 * 获取安全域信息<快捷方法>
	 * 
	 * @return
	 */
	public SdInfoVo getSdInfo() {
		return this.getBaseCommand().getContext().getSdInfo();
	}

	/**
	 * 设置安全域信息<快捷方法>
	 * 
	 * @param sdInfo
	 */
	/*public void setSdInfo(SdInfo sdInfo) {

		this.getBaseCommand().getContext().setSdInfo(po2vo(sdInfo));
	}

	private SdInfoVo po2vo(SdInfo sdInfo) {
		SdInfoVo sdVo = new SdInfoVo();
		sdVo.setAcAid(sdInfo.getAcAid());
		sdVo.setCardType(sdInfo.getCardType());
		sdVo.setDomainType(sdInfo.getDomainType());
		sdVo.setLoadFileAid(sdInfo.getLoadFileAid());
		sdVo.setModuleAid(sdInfo.getModuleAid());
		sdVo.setParameter(sdInfo.getParameter());
		sdVo.setPrivilege(sdInfo.getPrivilege());
		sdVo.setRsaAlg(sdInfo.getRsaAlg());
		sdVo.setSdAid(sdInfo.getSdAid());
		sdVo.setsDapId(sdInfo.getsDapId());
		sdVo.setsDapVer(sdInfo.getsDapVer());
		sdVo.setsDekId(sdInfo.getsDekId());
		sdVo.setsDekVer(sdInfo.getsDekVer());
		sdVo.setsEncId(sdInfo.getsEncId());
		sdVo.setsEncVer(sdInfo.getsEncVer());
		sdVo.setsMacId(sdInfo.getsMacId());
		sdVo.setsMacVer(sdInfo.getsMacVer());
		sdVo.setSpCode(sdInfo.getSpCode());
		sdVo.setsReceiptId(sdInfo.getsReceiptId());
		sdVo.setsReceiptVer(sdInfo.getsReceiptVer());
		sdVo.setStatus(sdInfo.getStatus());
		sdVo.setsTokenId(sdInfo.getsTokenId());
		sdVo.setsTokenVer(sdInfo.getsTokenVer());
		return sdVo;
	}*/

	/**
	 * 获取所属安全域
	 * 
	 * @return
	 */
	public String getSdAid() {
		return this.getBaseCommand().getContext().getSdAid();
	}

	/**
	 * 设置所属安全域
	 * 
	 * @param sdAid
	 */
	public void setSdAid(String sdAid) {
		this.getBaseCommand().getContext().setSdAid(sdAid);
	}

	/**
	 * 获取卡批次信息
	 * 
	 * @return
	 */
	public CardBatchInfoVo getCardBatchInfo() {
		return this.getBaseCommand().getContext().getCardBatchInfo();
	}

	/**
	 * 设置卡批次信息
	 * 
	 * @param cardBatchInfo
	 */
	public void setCardBatchInfo(CardBatchInfoVo cardBatchInfo) {
		this.getBaseCommand().getContext().setCardBatchInfo(cardBatchInfo);
	}

	/**
	 * 获取辅助安全域状态<快捷方法>
	 * 
	 * @return
	 */
	public String getCardSssdStatus() {
		return this.getBaseCommand().getContext().getCardSssdStatus();
	}

	/**
	 * 设置辅助安全域状态<快捷方法>
	 * 
	 * @param ssdID_status
	 */
	public void setCardSssdStatus(String ssdID_status) {
		this.getBaseCommand().getContext().setCardSssdStatus(ssdID_status);
	}

	public void setCardAppStatus(String cardAppStatus) {
		this.getBaseCommand().getContext().setCardAppStatus(cardAppStatus);
	}

	public String getCardAppStatus() {
		return this.getBaseCommand().getContext().getCardAppStatus();
	}

	public String getLastCmd() {
		return this.getBaseCommand().getContext().getLastCmd();
	}

	public String getLastCmdCode() {
		return this.getBaseCommand().getContext().getLastCmdCode();
	}

	public void setLastCmd(String lastCmd, String lastCmdCode) {
		this.getBaseCommand().getContext().setLastCmd(lastCmd, lastCmdCode);
	}

	public String getExternalAuthenticateRandom() {
		return this.getBaseCommand().getContext()
				.getExternalAuthenticateRandom();
	}

	public void setExternalAuthenticateRandom(String externalAuthenticateRandom) {
		this.getBaseCommand().getContext()
				.setExternalAuthenticateRandom(externalAuthenticateRandom);
	}

	public String getInitializeData() {
		return this.getBaseCommand().getContext().getInitializeData();
	}

	public void setInitializeData(String initializeData) {
		this.getBaseCommand().getContext().setInitializeData(initializeData);
	}

	/**
	 * 获取应用信息
	 * 
	 * @return
	 */
	public AppBaseInfo getAppBaseInfo() {
		AppBaseInfo localeAppInfo = this.getBaseCommand().getContext()
				.getLocaleAppInfo();

		return (null == localeAppInfo) ? this.getBaseCommand().getContext()
				.getSharedAppInfo() : localeAppInfo;
	}

	public void setAppStatusMap(Map<String, String> paids) {
		this.getBaseCommand().getContext().setAppStatusMap(paids);
	}

	public void putAppStatusMap(String key, String value) {
		this.getBaseCommand().getContext().getAppStatusMap().put(key, value);
	}

	public Map<String, String> getAppStatusMap() {
		return this.getBaseCommand().getContext().getAppStatusMap();
	}

	public String getSkid() {
		return this.getBaseCommand().getContext().getSkid();
	}

	public void setSkid(String skid) {
		this.getBaseCommand().getContext().setSkid(skid);
	}

	public int getOperNum() {
		return this.getBaseCommand().getContext().getOperNum();
	}

	public void setOperNum(int operNum) {
		this.getBaseCommand().getContext().setOperNum(operNum);
	}

	public void addOperNum() {
		this.getBaseCommand().getContext().addOperNum();
	}

	public String getApduIndex() {
		return this.getBaseCommand().getContext().getApduIndex();
	}

	public void setApduIndex(String apduIndex) {
		this.getBaseCommand().getContext().setApduIndex(apduIndex);
	}

	public String getSkidkey() {
		return this.getBaseCommand().getContext().getSkidkey();
	}

	public void setSkidkey(String skidkey) {
		this.getBaseCommand().getContext().setSkidkey(skidkey);
	}

	public String getPerProecss() {
		return this.getBaseCommand().getContext().getPerProecss();
	}

	public void setPerProecss(String perProecss) {
		this.getBaseCommand().getContext().setPerProecss(perProecss);
	}

	public String getTradeNo() {
		return this.getBaseCommand().getContext().getTradeNo();
	}

	public void setTradeNo(String tradeNo) {
		this.getBaseCommand().getContext().setTradeNo(tradeNo);
	}

	public void setDataList(List<String> dataList) {
		this.getBaseCommand().getContext().setDataList(dataList);
	}

	public List<String> getDataList() {
		return this.getBaseCommand().getContext().getDataList();
	}

	public boolean isPutkey() {
		return this.getBaseCommand().getContext().isPutkey();
	}

	public void setPutkey(boolean isPutkey) {
		this.getBaseCommand().getContext().setPutkey(isPutkey);
	}

	/**********************/
	/**********************/
	/**********************/

}
