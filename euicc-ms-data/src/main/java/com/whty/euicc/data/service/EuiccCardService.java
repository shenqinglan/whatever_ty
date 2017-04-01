package com.whty.euicc.data.service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.base.pojo.BaseUsers;
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

/**
 * @author dzmsoft
 * @date 2016-05-30 15:37
 *
 * @version 1.0
 */
public interface EuiccCardService {
	
	/**
     * 根据条件插入记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	int insertEuiccCardSelective(CardInfo record);
	
	/**
     * 根据条件插入记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	int insertEuiccCapabilitiesSelective(CapabilitiesInfo record);
	
	/**
     * 根据条件插入记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	int insertIsdRSelective(IsdRInfo record);
	
	/**
     * 根据条件插入记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	int insertEcasdSelective(EcasdInfo record);
	
	/**
     * 根据条件插入记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	int insertIsdPSelective(IsdPInfo record);
	
	/**
     * 根据条件插入记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	int insertScp03Selective(Scp03Info record);
	
	/**
     * 根据条件插入记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	int insertScp80Selective(Scp80Info record);
	
	/**
     * 根据条件插入记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	int insertScp81Selective(Scp81Info record);
	
	/**
     * 根据主键查询记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	EuiccCard selectByPrimaryKey(String eid);
	
	/**
     * 根据主键查询记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	Scp03Info selectScp03ByPrimaryKey(String scp03Id);
	
	/**
     * 根据主键查询记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	Scp80Info selectScp80ByPrimaryKey(String scp80Id);
	
	/**
     * 根据主键查询记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	Scp81Info selectScp81ByPrimaryKey(String scp81Id);
	
	/**
     * 根据主键查询记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	IsdPInfo selectIsdPByPrimaryKey(String pId);
	
	/**
     * 根据主键查询记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	int selectCardInfoCount(String eid);
	
	/**
     * 根据主键查询记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	int selectCapabilitiesInfoCount(String eid);
	
	/**
     * 根据主键查询记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	int selectIsdRInfoCount(String eid);
	
	/**
	 * 根据eid查询
	 * @param eid
	 * @return
	 */
	EuiccIsdR selectIsdRByEid(String eid);
	
	/**
     * 根据主键查询记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	int selectEcasdInfoCount(String ecasdId);
	
	/**
     * 根据主键查询记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	int selectIsdPInfoCount(String isdPAid);
	
	/**
     * 根据主键查询记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	int selectScp03InfoCount(String scp03Id);
	
	/**
     * 根据主键查询记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	int selectScp80InfoCount(String scp80Id);
	
	/**
     * 根据主键查询记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	int selectScp81InfoCount(String scp81Id);
	
	/**
     * 分页查询记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	PageList<EuiccCard> selectByExample(EuiccCardExample example,PageBounds pageBounds);
	
	/**
     * scp03分页查询记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	PageList<Scp03Info> selectScp03ByExample(EuiccCardExample example,PageBounds pageBounds);
	
	/**
     * scp80分页查询记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	PageList<Scp80Info> selectScp80ByExample(EuiccCardExample example,PageBounds pageBounds);
	
	/**
     * scp81分页查询记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	PageList<Scp81Info> selectScp81ByExample(EuiccCardExample example,PageBounds pageBounds);
	
	/**
     * scp81分页查询记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	PageList<IsdPInfo> selectIsdPByExample(EuiccCardExample example,PageBounds pageBounds);
	
	/**
     * 根据主键更新记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	int updateEuiccCardSelective(CardInfo record);
	
	/**
     * 根据查询条件更新记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	int updateEuiccCapabilitiesSelective(CapabilitiesInfo record);
	
	/**
     * 根据查询条件更新记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	int updateIsdRSelective(IsdRInfo record);
	
	/**
     * 根据查询条件更新记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	int updateEcasdSelective(EcasdInfo record);
	
	/**
     * 根据查询条件更新记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	int updateIsdPSelective(IsdPInfo record);
	
	/**
     * 根据查询条件更新记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	int updateScp03Selective(Scp03Info record);
	
	/**
     * 根据查询条件更新记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	int updateScp80Selective(Scp80Info record);
	
	/**
     * 根据查询条件更新记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	int updateScp81Selective(Scp81Info record);
	
	/**
     * 根据主键生成记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	int deleteCardInfoByPrimaryKey(String eid);
	
	/**
     * 根据主键生成记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	int deleteCapabilitiesInfoByPrimaryKey(String eid);
	
	/**
     * 根据主键生成记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	int deleteEcasdInfoByPrimaryKey(String ecasdId);
	
	/**
	 * 根据条件删除字段信息
	 * @dzmsoftgenerated 2016-05-30 15:37
	 * @param example
	 * @return
	 */
	int deleteByExample(EuiccCardExample example);
	
	/**
     * 根据eid删除记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	int deleteIsdRInfoByEid(String eid);
	
	/**
     * 根据eid删除记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	int deleteIsdPInfoByEid(String eid);
	
	/**
     * 根据eid删除记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	int deleteScp80InfoByEid(String eid);
	
	/**
     * 根据eid删除记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	int deleteScp81InfoByEid(String eid);
	
	/**
     * 根据eid删除记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	int deleteScp03InfoByEid(String eid);
	
	/**
     * 根据eid删除记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	int deleteAllByEid(String eid);
	/**
	 * 获取卡信息
	 * @param euiccCard
	 * @return
	 */
	CardInfo getCardInfo(EuiccCard euiccCard);
	
	CapabilitiesInfo getCapabilitiesInfo(EuiccCard euiccCard);
	
	IsdRInfo getIsdRInfo(EuiccCard euiccCard,String tag);
	
	EcasdInfo getEcasdInfo(EuiccCard euiccCard,String ecasdId);
	
	String importEuicc(BaseUsers currentUser, EuiccCardExcelInfo info) throws Exception;
	
	void deleteEuicc(String eid, String ecasdId);
	void editEuicc(CardInfo cardInfo, String eid,
			CapabilitiesInfo capabilitiesInfo, IsdRInfo isdRInfo,
			EcasdInfo ecasdInfo);
	
	void addEuicc(CardInfo cardInfo, CapabilitiesInfo capabilitiesInfo,
			IsdRInfo isdRInfo, EcasdInfo ecasdInfo);
}
