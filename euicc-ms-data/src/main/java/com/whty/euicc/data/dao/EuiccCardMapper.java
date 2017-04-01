package com.whty.euicc.data.dao;

import java.util.List;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.data.pojo.CapabilitiesInfo;
import com.whty.euicc.data.pojo.CardInfo;
import com.whty.euicc.data.pojo.EcasdInfo;
import com.whty.euicc.data.pojo.EuiccCard;
import com.whty.euicc.data.pojo.EuiccCardExample;
import com.whty.euicc.data.pojo.IsdPInfo;
import com.whty.euicc.data.pojo.IsdRInfo;
import com.whty.euicc.data.pojo.Scp03Info;
import com.whty.euicc.data.pojo.Scp80Info;
import com.whty.euicc.data.pojo.Scp81Info;

public interface EuiccCardMapper {
	int deleteAllByEid(String eid);

    int deleteByExample(EuiccCardExample example);

    int deleteCardInfoByPrimaryKey(String eid);

    int deleteCapabilitiesInfoByPrimaryKey(String eid);
    
    int deleteEcasdInfoByPrimaryKey(String ecasdId);
    
    int deleteIsdRInfoByEid(String eid);
    
    int deleteIsdPInfoByEid(String eid);
    
    int deleteScp80InfoByEid(String eid);
    
    int deleteScp81InfoByEid(String eid);
    
    int deleteScp03InfoByEid(String eid);
    
    void batchDeleteCard(List<String>eidList);
    void batchDeleteCapabilities(List<String>eidList);
    void batchDeleteIsdR(List<String>eidList);
    void batchDeleteEcasd(List<String> ecasdIDList);
    
    void batchDeleteSCP80(List<String>eidList);
    void batchDeleteSCP81(List<String>eidList);
    void batchDeleteSCP03(List<String>eidList);
    void batchDeleteIsdP(List<String>eidList);
    
    void insertCardByBatch(List<CardInfo> cardinfoList);
    void insertCapabilitiesByBatch(List<CapabilitiesInfo> capabilitieList);
    void insertIsdRByBatch(List<IsdRInfo> isdRInfoList);
    void insertEcasdByBatch(List<EcasdInfo> ecasdInfoList);
    
    void insertScp80ByBatch(List<Scp80Info> scp80InfoList);
    void insertScp81ByBatch(List<Scp81Info> scp81InfoList);
    void insertScp03ByBatch(List<Scp03Info> scp03InfoList);
    void insertIsdPByBatch(List<IsdPInfo> isdPInfoList);

    int insertEuiccCardSelective(CardInfo record);
    
    int insertEuiccCapabilitiesSelective(CapabilitiesInfo record);
    
    int insertIsdRSelective(IsdRInfo record);
    
    int insertEcasdSelective(EcasdInfo record);
    
    int insertIsdPSelective(IsdPInfo record);
    
    int insertScp03Selective(Scp03Info record);
    
    int insertScp80Selective(Scp80Info record);
    
    int insertScp81Selective(Scp81Info record);

    PageList<EuiccCard> selectByExample(EuiccCardExample example,PageBounds pageBounds);
    
    PageList<Scp03Info> selectScp03ByExample(EuiccCardExample example,PageBounds pageBounds);
    
    PageList<Scp80Info> selectScp80ByExample(EuiccCardExample example,PageBounds pageBounds);
    
    PageList<Scp81Info> selectScp81ByExample(EuiccCardExample example,PageBounds pageBounds);
    
    PageList<IsdPInfo> selectIsdPByExample(EuiccCardExample example,PageBounds pageBounds);
    
    PageList<Scp03Info> selectScp03ByEid(String eid);
    
    PageList<Scp80Info> selectScp80ByEid(String eid);
    
    PageList<Scp81Info> selectScp81ByEid(String eid);
    
    EuiccCard selectByPrimaryKey(String eid);
    
    Scp03Info selectScp03ByPrimaryKey(String scp03Id);
    
    Scp80Info selectScp80ByPrimaryKey(String scp80Id);
    
    Scp81Info selectScp81ByPrimaryKey(String scp81Id);
    
    IsdPInfo selectIsdPByPrimaryKey(String pId);

    int selectCardInfoCount(String eid);
    
    int selectCapabilitiesInfoCount(String eid);
    
    int selectIsdRInfoCount(String eid);
    
    int selectEcasdInfoCount(String ecasdId);
    
    int selectIsdPInfoCount(String eid);
    
    int selectScp03InfoCount(String eid);
    
    int selectScp80InfoCount(String eid);
    
    int selectScp81InfoCount(String eid);

    int updateEuiccCardSelective(CardInfo record);

    int updateEuiccCapabilitiesSelective(CapabilitiesInfo record);
    
    int updateIsdRSelective(IsdRInfo record);
    
    int updateEcasdSelective(EcasdInfo record);
    
    int updateIsdPSelective(IsdPInfo record);
    
    int updateScp03Selective(Scp03Info record);
    
    int updateScp80Selective(Scp80Info record);
    
    int updateScp81Selective(Scp81Info record);

}