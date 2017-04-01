package com.whty.euicc.data.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.base.common.Constant;
import com.whty.euicc.base.common.LogTables;
import com.whty.euicc.base.dao.BaseLogsMapper;
import com.whty.euicc.base.pojo.BaseUsers;
import com.whty.euicc.common.utils.UUIDUtil;
import com.whty.euicc.data.dao.EuiccCardMapper;
import com.whty.euicc.data.dao.EuiccIsdRMapper;
import com.whty.euicc.data.pojo.CapabilitiesInfo;
import com.whty.euicc.data.pojo.CardInfo;
import com.whty.euicc.data.pojo.EcasdInfo;
import com.whty.euicc.data.pojo.EuiccCard;
import com.whty.euicc.data.pojo.EuiccCardExample;
import com.whty.euicc.data.pojo.EuiccCardExcelInfo;
import com.whty.euicc.data.pojo.EuiccIsdR;
import com.whty.euicc.data.pojo.IsdPInfo;
import com.whty.euicc.data.pojo.IsdRInfo;
import com.whty.euicc.data.pojo.Scp03Info;
import com.whty.euicc.data.pojo.Scp80Info;
import com.whty.euicc.data.pojo.Scp81Info;
import com.whty.euicc.data.service.EuiccCardService;
/**
 * @author dzmsoft
 * @date 2016-05-30 15:37
 *
 * @version 1.0
 */
@Service
public class EuiccCardServiceImpl implements EuiccCardService{

	@Autowired
	private EuiccCardMapper euiccCardMapper;
	
	@Autowired
	private EuiccIsdRMapper isdrMapper;
	
	@Autowired
	private BaseLogsMapper baseLogsMapper;

	/**
     * 根据条件插入记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public int insertEuiccCardSelective(CardInfo record){
		return euiccCardMapper.insertEuiccCardSelective(record);
	}
	
	/**
     * 根据条件插入记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public int insertEuiccCapabilitiesSelective(CapabilitiesInfo record){
		return euiccCardMapper.insertEuiccCapabilitiesSelective(record);
	}
	
	/**
     * 根据条件插入记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public int insertIsdRSelective(IsdRInfo record){
		return euiccCardMapper.insertIsdRSelective(record);
	}
	
	/**
     * 根据条件插入记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public int insertEcasdSelective(EcasdInfo record){
		return euiccCardMapper.insertEcasdSelective(record);
	}
	
	/**
     * 根据条件插入记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public int insertIsdPSelective(IsdPInfo record){
		return euiccCardMapper.insertIsdPSelective(record);
	}
	
	/**
     * 根据条件插入记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public int insertScp03Selective(Scp03Info record){
		return euiccCardMapper.insertScp03Selective(record);
	}
	
	/**
     * 根据条件插入记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public int insertScp80Selective(Scp80Info record){
		return euiccCardMapper.insertScp80Selective(record);
	}
	
	/**
     * 根据条件插入记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public int insertScp81Selective(Scp81Info record){
		return euiccCardMapper.insertScp81Selective(record);
	}
	
	/**
     * 根据主键查询记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public EuiccCard selectByPrimaryKey(String eid){
		return euiccCardMapper.selectByPrimaryKey(eid);
	}
	
	/**
     * 根据主键查询记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public Scp03Info selectScp03ByPrimaryKey(String scp03Id){
		return euiccCardMapper.selectScp03ByPrimaryKey(scp03Id);
	}
	
	/**
     * 根据主键查询记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public Scp80Info selectScp80ByPrimaryKey(String scp80Id){
		return euiccCardMapper.selectScp80ByPrimaryKey(scp80Id);
	}
	
	/**
     * 根据主键查询记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public Scp81Info selectScp81ByPrimaryKey(String scp81Id){
		return euiccCardMapper.selectScp81ByPrimaryKey(scp81Id);
	}
	
	/**
     * 根据主键查询记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public IsdPInfo selectIsdPByPrimaryKey(String pId){
		return euiccCardMapper.selectIsdPByPrimaryKey(pId);
	}
	
	/**
     * 根据主键查询记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public int selectCapabilitiesInfoCount(String eid){
		return euiccCardMapper.selectCapabilitiesInfoCount(eid);
	}
	
	/**
     * 根据主键查询记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public int selectCardInfoCount(String eid){
		return euiccCardMapper.selectCardInfoCount(eid);
	}
	
	/**
     * 根据主键查询记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public int selectIsdRInfoCount(String eid){
		return euiccCardMapper.selectIsdRInfoCount(eid);
	}
	
	/**
	 * 根据eid查询
	 */
	public EuiccIsdR selectIsdRByEid(String eid) {
		return isdrMapper.selectByEid(eid);
	}
	
	/**
     * 根据主键查询记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public int selectEcasdInfoCount(String ecasdId){
		return euiccCardMapper.selectEcasdInfoCount(ecasdId);
	}
	
	/**
     * 根据主键查询记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public int selectIsdPInfoCount(String eid){
		return euiccCardMapper.selectIsdPInfoCount(eid);
	}
	
	/**
     * 根据主键查询记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public int selectScp03InfoCount(String eid){
		return euiccCardMapper.selectScp03InfoCount(eid);
	}
	
	/**
     * 根据主键查询记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public int selectScp80InfoCount(String eid){
		return euiccCardMapper.selectScp80InfoCount(eid);
	}
	
	/**
     * 根据主键查询记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public int selectScp81InfoCount(String eid){
		return euiccCardMapper.selectScp81InfoCount(eid);
	}
	
	/**
     * 分页查询记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public PageList<EuiccCard> selectByExample(EuiccCardExample example,PageBounds pageBounds){
		return euiccCardMapper.selectByExample(example, pageBounds);
	}
	
	/**
     * scp03分页查询记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public PageList<Scp03Info> selectScp03ByExample(EuiccCardExample example,PageBounds pageBounds){
		return euiccCardMapper.selectScp03ByExample(example, pageBounds);
	}
	
	/**
     * scp80分页查询记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public PageList<Scp80Info> selectScp80ByExample(EuiccCardExample example,PageBounds pageBounds){
		return euiccCardMapper.selectScp80ByExample(example, pageBounds);
	}
	
	/**
     * scp81分页查询记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public PageList<Scp81Info> selectScp81ByExample(EuiccCardExample example,PageBounds pageBounds){
		return euiccCardMapper.selectScp81ByExample(example, pageBounds);
	}
	
	/**
     * scp81分页查询记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public PageList<IsdPInfo> selectIsdPByExample(EuiccCardExample example,PageBounds pageBounds){
		return euiccCardMapper.selectIsdPByExample(example, pageBounds);
	}
	
	/**
     * scp03查询记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public PageList<Scp03Info> selectScp03ByEid(String eid){
		return euiccCardMapper.selectScp03ByEid(eid);
	}
	
	/**
     * scp80查询记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public PageList<Scp80Info> selectScp80ByEid(String eid){
		return euiccCardMapper.selectScp80ByEid(eid);
	}
	
	/**
     * scp81查询记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public PageList<Scp81Info> selectScp81ByEid(String eid){
		return euiccCardMapper.selectScp81ByEid(eid);
	}
	
	/**
     * 根据主键更新记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public int updateEuiccCardSelective(CardInfo record){
		return euiccCardMapper.updateEuiccCardSelective(record);
	}
	
	/**
     * 根据查询条件更新记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public int updateEuiccCapabilitiesSelective(CapabilitiesInfo record){
		return euiccCardMapper.updateEuiccCapabilitiesSelective(record);
	}
	
	/**
     * 根据查询条件更新记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public int updateIsdRSelective(IsdRInfo record){
		return euiccCardMapper.updateIsdRSelective(record);
	}
	
	/**
     * 根据查询条件更新记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public int updateEcasdSelective(EcasdInfo record){
		return euiccCardMapper.updateEcasdSelective(record);
	}
	
	/**
     * 根据查询条件更新记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public int updateIsdPSelective(IsdPInfo record){
		return euiccCardMapper.updateIsdPSelective(record);
	}
	
	/**
     * 根据查询条件更新记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public int updateScp03Selective(Scp03Info record){
		return euiccCardMapper.updateScp03Selective(record);
	}
	
	/**
     * 根据查询条件更新记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public int updateScp80Selective(Scp80Info record){
		return euiccCardMapper.updateScp80Selective(record);
	}
	
	/**
     * 根据查询条件更新记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public int updateScp81Selective(Scp81Info record){
		return euiccCardMapper.updateScp81Selective(record);
	}
	
	
	/**
     * 根据主键生成记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public int deleteCardInfoByPrimaryKey(String eid){
		return euiccCardMapper.deleteCardInfoByPrimaryKey(eid);
	}
	
	/**
     * 根据主键生成记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public int deleteCapabilitiesInfoByPrimaryKey(String eid){
		return euiccCardMapper.deleteCapabilitiesInfoByPrimaryKey(eid);
	}
	
	/**
     * 根据主键生成记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public int deleteEcasdInfoByPrimaryKey(String ecasdId){
		return euiccCardMapper.deleteEcasdInfoByPrimaryKey(ecasdId);
	}
	
	/**
	 * 根据条件删除字段信息
	 * @dzmsoftgenerated 2016-05-30 15:37
	 * @param example
	 * @return
	 */
	public int deleteByExample(EuiccCardExample example){
		return euiccCardMapper.deleteByExample(example);
	}
	
	/**
     * 根据eid删除记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public int deleteIsdRInfoByEid(String eid){
		return euiccCardMapper.deleteIsdRInfoByEid(eid);
	}
	
	/**
     * 根据eid删除记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public int deleteIsdPInfoByEid(String eid){
		return euiccCardMapper.deleteIsdPInfoByEid(eid);
	}
	
	/**
     * 根据eid删除记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public int deleteScp80InfoByEid(String eid){
		return euiccCardMapper.deleteScp80InfoByEid(eid);
	}
	
	/**
     * 根据eid删除记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public int deleteScp81InfoByEid(String eid){
		return euiccCardMapper.deleteScp81InfoByEid(eid);
	}
	
	/**
     * 根据eid删除记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public int deleteScp03InfoByEid(String eid){
		return euiccCardMapper.deleteScp03InfoByEid(eid);
	}

	public int deleteAllByEid(String eid) {
		return euiccCardMapper.deleteAllByEid(eid);
	}
	
	/**
	 * 获取卡信息
	 * @param euiccCard
	 * @return
	 */
	public CardInfo getCardInfo(EuiccCard euiccCard) {
		CardInfo cardInfo = new CardInfo();
		cardInfo.setEid(euiccCard.getEid());
		cardInfo.setEumId(euiccCard.getEumId());
		cardInfo.setProductiondate(euiccCard.getProductiondate());
		cardInfo.setPlatformtype(euiccCard.getPlatformtype());
		cardInfo.setPlatformversion(euiccCard.getPlatformversion());
		cardInfo.setRemainingmemory(euiccCard.getRemainingmemory());
		cardInfo.setAvailablememoryforprofiles(euiccCard.getAvailablememoryforprofiles());
		cardInfo.setSmsrId(euiccCard.getSmsrId());
		cardInfo.setEcasdId(StringUtils.defaultIfEmpty(euiccCard.getEcasdId(),UUIDUtil.getUuidString()));
		cardInfo.setPhoneNo(euiccCard.getPhoneNo());
//		ecasdId = cardInfo.getEcasdId();
		return cardInfo;
	}
	
	/**
	 * 获取卡能力信息
	 * @param euiccCard
	 * @return
	 */
	public CapabilitiesInfo getCapabilitiesInfo(EuiccCard euiccCard) {
		CapabilitiesInfo capabilitiesInfo = new CapabilitiesInfo();
		capabilitiesInfo.setCapabilityId(StringUtils.defaultIfEmpty(euiccCard.getCapabilityId(),UUIDUtil.getUuidString()));
		capabilitiesInfo.setEid(euiccCard.getEid());
		capabilitiesInfo.setCatTpSupport(euiccCard.getCatTpSupport());
		capabilitiesInfo.setCatTpVersion(euiccCard.getCatTpVersion());
		capabilitiesInfo.setHttpSupport(euiccCard.getHttpSupport());
		capabilitiesInfo.setHttpVersion(euiccCard.getHttpVersion());
		capabilitiesInfo.setSecurePacketVersion(euiccCard
				.getSecurePacketVersion());
		capabilitiesInfo.setRemoteProvisioningVersion(euiccCard
				.getRemoteProvisioningVersion());
		return capabilitiesInfo;
	}
	
	/**
	 * 获取ISDR信息
	 * @param euiccCard
	 * @param tag
	 * @return
	 */
	public IsdRInfo getIsdRInfo(EuiccCard euiccCard,String tag) {
		IsdRInfo isdRInfo = new IsdRInfo();
		if(Constant.SAVESTATUSADD.equals(tag)){
			isdRInfo.setrId(UUIDUtil.getUuidString());
		}else{
			isdRInfo.setrId(isdrMapper.selectByEid(euiccCard.getEid()).getrId());//原版，mark,找原因euiccCard.getEid()).getrId()
		}
		
		isdRInfo.setIsdRAid(euiccCard.getIsdRAid());
		isdRInfo.setEid(euiccCard.getEid());
		isdRInfo.setPol1Id(euiccCard.getPol1Id());
		return isdRInfo;
	}
	
	/**
	 * 获取ECASD信息
	 * @param euiccCard
	 * @return
	 */
	public EcasdInfo getEcasdInfo(EuiccCard euiccCard,String ecasdId) {
		EcasdInfo ecasdInfo = new EcasdInfo();
		if (StringUtils.isBlank(ecasdId)) {
			ecasdInfo.setEcasdId(euiccCard.getEcasdId());
//			System.out.println("ecasdid in import register >>> "+euiccCard.getEcasdId());
		}else {
			ecasdInfo.setEcasdId(ecasdId);
			System.out.println("ecasdid >>> "+ecasdId);
		}
		//ecasdInfo.setEcasdId(UUID.randomUUID().toString().replace("-", ""));
		
		ecasdInfo.setCertEcasdEcka(euiccCard.getCertEcasdEcka());
		return ecasdInfo;
	}
	
	/**
	 * 更新数据库
	 * @param currentUser
	 * @param info
	 * @throws Exception 
	 */
	@Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
	public String importEuicc(BaseUsers currentUser, EuiccCardExcelInfo info)throws Exception{
		try {
			List<CardInfo>cardList = new ArrayList<CardInfo>();
			List<CapabilitiesInfo>capabilitiesList = new ArrayList<CapabilitiesInfo>();
			List<IsdRInfo>isdrList = new ArrayList<IsdRInfo>();
			List<EcasdInfo>ecasdList = new ArrayList<EcasdInfo>();
			List<String> eidList = new ArrayList<String>();
			List<String> ecasdIDList = new ArrayList<String>();
			List<Scp80Info>scp80InfoList = new ArrayList<Scp80Info>();
			List<Scp03Info>scp03InfoList = new ArrayList<Scp03Info>();
			List<Scp81Info>scp81InfoList = new ArrayList<Scp81Info>();
			List<IsdPInfo>isdPInfoList = new ArrayList<IsdPInfo>();
			//更新卡基本数据
			for(int i = 0;i<info.getEuiccCardList().size();i++) {
				EuiccCard euicccard = info.getEuiccCardList().get(i);
				String eid = euicccard.getEid();
				/*euiccCardMapper.deleteScp03InfoByEid(eid);
				euiccCardMapper.deleteScp81InfoByEid(eid);
				euiccCardMapper.deleteScp80InfoByEid(eid);
				euiccCardMapper.deleteIsdPInfoByEid(eid);
				euiccCardMapper.deleteIsdRInfoByEid(eid);
				euiccCardMapper.deleteCapabilitiesInfoByPrimaryKey(eid);
				euiccCardMapper.deleteCardInfoByPrimaryKey(eid);
				euiccCardMapper.deleteEcasdInfoByPrimaryKey(euicccard.getEcasdId());
				 */
				eidList.add(eid);
				ecasdIDList.add(euicccard.getEcasdId());

				cardList.add(getCardInfo(euicccard));
				capabilitiesList.add(getCapabilitiesInfo(euicccard));
				isdrList.add(getIsdRInfo(euicccard,Constant.SAVESTATUSADD));
				ecasdList.add(getEcasdInfo(euicccard,null));
				/*    euiccCardMapper.insertEuiccCardSelective(getCardInfo(euicccard));
				  euiccCardMapper.insertEuiccCapabilitiesSelective(getCapabilitiesInfo(euicccard));
				  euiccCardMapper.insertIsdRSelective(getIsdRInfo(euicccard,Constant.SAVESTATUSADD));
				  euiccCardMapper.insertEcasdSelective(getEcasdInfo(euicccard,null));
				 */

				if ((i != 0 && i%6000 == 0) || ((i == info.getEuiccCardList().size()-1) && info.getEuiccCardList().size()%6000 >= 1)) {
					euiccCardMapper.batchDeleteSCP80(eidList);
					euiccCardMapper.batchDeleteSCP81(eidList);
					euiccCardMapper.batchDeleteSCP03(eidList);
					euiccCardMapper.batchDeleteIsdP(eidList);
					euiccCardMapper.batchDeleteCard(eidList);
					euiccCardMapper.batchDeleteCapabilities(eidList);
					euiccCardMapper.batchDeleteIsdR(eidList);
					euiccCardMapper.batchDeleteEcasd(ecasdIDList);

					euiccCardMapper.insertCardByBatch(cardList);
					euiccCardMapper.insertCapabilitiesByBatch(capabilitiesList);
					euiccCardMapper.insertIsdRByBatch(isdrList);
					euiccCardMapper.insertEcasdByBatch(ecasdList);
					eidList.clear();
					ecasdIDList.clear();
					cardList.clear();
					capabilitiesList.clear();
					isdrList.clear();
					ecasdList.clear();
				}
			}
			//更新Scp80
			for(int i = 0;i < info.getScp80InfoList().size();i++) {
				//euiccCardMapper.insertScp80Selective(scp80Info);
				Scp80Info scp80Info = info.getScp80InfoList().get(i);
				scp80InfoList.add(scp80Info);
				if ((i != 0 && i%6000 == 0) || ((i == info.getScp80InfoList().size()-1) && info.getScp80InfoList().size()%6000 >= 1)) {
					euiccCardMapper.insertScp80ByBatch(scp80InfoList);
					scp80InfoList.clear();
				}

			}

			//更新Scp81
			for(int i = 0;i < info.getScp81InfoList().size();i++) {
				//euiccCardMapper.insertScp81Selective(scp81Info);
				Scp81Info scp81Info = info.getScp81InfoList().get(i);
				scp81InfoList.add(scp81Info);
				if ((i != 0 && i%6000 == 0) || ((i == info.getScp81InfoList().size()-1) && info.getScp81InfoList().size()%6000 >= 1)) {
					euiccCardMapper.insertScp81ByBatch(scp81InfoList);
					scp81InfoList.clear();
				}
			}

			//更新Scp03
			for(int i = 0;i < info.getScp03InfoList().size();i++) {
				//euiccCardMapper.insertScp03Selective(scp03Info);
				Scp03Info scp03Info = info.getScp03InfoList().get(i);
				scp03InfoList.add(scp03Info);
				if ((i != 0 && i%6000 == 0) || ((i == info.getScp03InfoList().size()-1) && info.getScp03InfoList().size()%6000 >= 1)) {
					euiccCardMapper.insertScp03ByBatch(scp03InfoList);
					scp03InfoList.clear();
				}
			}

			//更新isd-p
			for(int i = 0;i < info.getIsdPInfoList().size();i++) {
				//euiccCardMapper.insertIsdPSelective(indPInfo);
				IsdPInfo isdPInfo = info.getIsdPInfoList().get(i);
				isdPInfoList.add(isdPInfo);
				if ((i != 0 && i%6000 == 0) || ((i == info.getIsdPInfoList().size()-1) && info.getIsdPInfoList().size()%6000 >= 1)) {
					euiccCardMapper.insertIsdPByBatch(isdPInfoList);
					isdPInfoList.clear();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("database operate fail,data already rollback!");
		}
		return "0000";
	}
	
	
	/**
	 * 新增主界面一览表记录
	 * @param cardInfo
	 * @param capabilitiesInfo
	 * @param isdRInfo
	 * @param ecasdInfo
	 */
	@Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
	public void addEuicc(CardInfo cardInfo, CapabilitiesInfo capabilitiesInfo,
			IsdRInfo isdRInfo, EcasdInfo ecasdInfo) {
		euiccCardMapper.insertEuiccCardSelective(cardInfo);
		euiccCardMapper.insertEuiccCapabilitiesSelective(capabilitiesInfo);
		euiccCardMapper.insertIsdRSelective(isdRInfo);
		euiccCardMapper.insertEcasdSelective(ecasdInfo);
	}
	
	/**
	 * 修改主界面一览表记录
	 * @param cardInfo
	 * @param eid
	 * @param capabilitiesInfo
	 * @param isdRInfo
	 * @param ecasdInfo
	 */
	@Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
	public void editEuicc(CardInfo cardInfo, String eid,
			CapabilitiesInfo capabilitiesInfo, IsdRInfo isdRInfo,
			EcasdInfo ecasdInfo) {
		euiccCardMapper.updateEuiccCardSelective(cardInfo);
		if(euiccCardMapper.selectCapabilitiesInfoCount(eid) > 0){
			euiccCardMapper.updateEuiccCapabilitiesSelective(capabilitiesInfo);
		} else {
			euiccCardMapper.insertEuiccCapabilitiesSelective(capabilitiesInfo);
		}
		
		if(euiccCardMapper.selectIsdRInfoCount(eid) > 0){
			euiccCardMapper.updateIsdRSelective(isdRInfo);
		} else {
			euiccCardMapper.insertIsdRSelective(isdRInfo);
		}
		
		if(euiccCardMapper.selectEcasdInfoCount(cardInfo.getEcasdId()) > 0){
			euiccCardMapper.updateEcasdSelective(ecasdInfo);
		} else {
			euiccCardMapper.insertEcasdSelective(ecasdInfo);
		}
	}

	/**
	 * 删除所有关联表数据
	 * @param eid
	 * @param ecasdId
	 */
	@Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
	public void deleteEuicc(String eid, String ecasdId) {
		euiccCardMapper.deleteScp03InfoByEid(eid);
		euiccCardMapper.deleteScp81InfoByEid(eid);
		euiccCardMapper.deleteScp80InfoByEid(eid);
		euiccCardMapper.deleteIsdPInfoByEid(eid);
		euiccCardMapper.deleteEcasdInfoByPrimaryKey(ecasdId);
		euiccCardMapper.deleteIsdRInfoByEid(eid);
		euiccCardMapper.deleteCapabilitiesInfoByPrimaryKey(eid);
		euiccCardMapper.deleteCardInfoByPrimaryKey(eid);
		//euiccCardService.deleteAllByEid(eid);
	}
	
	
}
