package com.whty.efs.data.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.whty.efs.data.dao.EuiccAuditTrailRecordMapper;
import com.whty.efs.data.dao.EuiccCanonicalizationMethodTypeMapper;
import com.whty.efs.data.dao.EuiccCapabilitiesMapper;
import com.whty.efs.data.dao.EuiccCardMapper;
import com.whty.efs.data.dao.EuiccDigestMethodTypeMapper;
import com.whty.efs.data.dao.EuiccEcasdKeysetMapper;
import com.whty.efs.data.dao.EuiccEcasdMapper;
import com.whty.efs.data.dao.EuiccEcasdTarMapper;
import com.whty.efs.data.dao.EuiccEumSignatureMapper;
import com.whty.efs.data.dao.EuiccExecutionStatusTypeMapper;
import com.whty.efs.data.dao.EuiccIsdRMapper;
import com.whty.efs.data.dao.EuiccIsdrKeysetMapper;
import com.whty.efs.data.dao.EuiccIsdrTarMapper;
import com.whty.efs.data.dao.EuiccKeyInfoTypeMapper;
import com.whty.efs.data.dao.EuiccObjectTypeMapper;
import com.whty.efs.data.dao.EuiccPol2Mapper;
import com.whty.efs.data.dao.EuiccProfileMapper;
import com.whty.efs.data.dao.EuiccPropertyTypeMapper;
import com.whty.efs.data.dao.EuiccReferenceTransformTypeMapper;
import com.whty.efs.data.dao.EuiccReferenceTypeMapper;
import com.whty.efs.data.dao.EuiccRetrievalMethodTypeMapper;
import com.whty.efs.data.dao.EuiccRetrievalTransformTypeMapper;
import com.whty.efs.data.dao.EuiccSignedInfoTypeMapper;
import com.whty.efs.data.pojo.EIS;
import com.whty.efs.data.pojo.EuiccAuditTrailRecord;
import com.whty.efs.data.pojo.EuiccAuditTrailRecordExample;
import com.whty.efs.data.pojo.EuiccCanonicalizationMethodType;
import com.whty.efs.data.pojo.EuiccCapabilities;
import com.whty.efs.data.pojo.EuiccCard;
import com.whty.efs.data.pojo.EuiccDigestMethodType;
import com.whty.efs.data.pojo.EuiccEcasd;
import com.whty.efs.data.pojo.EuiccEcasdKeyset;
import com.whty.efs.data.pojo.EuiccEcasdTar;
import com.whty.efs.data.pojo.EuiccEumSignature;
import com.whty.efs.data.pojo.EuiccExecutionStatusType;
import com.whty.efs.data.pojo.EuiccIsdR;
import com.whty.efs.data.pojo.EuiccIsdRExample;
import com.whty.efs.data.pojo.EuiccIsdrKeyset;
import com.whty.efs.data.pojo.EuiccIsdrTar;
import com.whty.efs.data.pojo.EuiccKeyInfoType;
import com.whty.efs.data.pojo.EuiccObjectType;
import com.whty.efs.data.pojo.EuiccPol2;
import com.whty.efs.data.pojo.EuiccProfile;
import com.whty.efs.data.pojo.EuiccProfileExample;
import com.whty.efs.data.pojo.EuiccProfileWithBLOBs;
import com.whty.efs.data.pojo.EuiccPropertyType;
import com.whty.efs.data.pojo.EuiccReferenceTransformType;
import com.whty.efs.data.pojo.EuiccReferenceType;
import com.whty.efs.data.pojo.EuiccReferenceTypeExample;
import com.whty.efs.data.pojo.EuiccRetrievalMethodType;
import com.whty.efs.data.pojo.EuiccRetrievalTransformType;
import com.whty.efs.data.pojo.EuiccSignedInfoType;
import com.whty.efs.data.service.ES1RegisterEISService;

@Service
@Transactional
public class ES1RegisterEISServiceImpl implements ES1RegisterEISService{
	
	@Autowired
	EuiccCardMapper euiccCardMapper;
	
	@Autowired
	EuiccCapabilitiesMapper euiccCapabilitiesMapper;
	
	@Autowired
	EuiccIsdRMapper euiccIsdRMapper;
	
	@Autowired
	EuiccEcasdMapper euiccEcasdMapper;
	
	@Autowired
	EuiccProfileMapper euiccProfileMapper;
	
	@Autowired
	EuiccPol2Mapper euiccPol2Mapper;
	
	@Autowired
	EuiccAuditTrailRecordMapper euiccAuditTrailRecordMapper;
	
	@Autowired
	EuiccEcasdKeysetMapper euiccEcasdKeysetMapper;
	
	@Autowired
	EuiccEcasdTarMapper euiccEcasdTarMapper;
	
	@Autowired
	EuiccIsdrKeysetMapper euiccIsdrKeysetMapper;
	
	@Autowired
	EuiccIsdrTarMapper euiccIsdrTarMapper;
	
	@Autowired
	EuiccExecutionStatusTypeMapper euiccExecutionStatusTypeMapper;
	
	@Autowired
	EuiccKeyInfoTypeMapper euiccKeyInfoTypeMapper;
	
	@Autowired
	EuiccPropertyTypeMapper euiccPropertyTypeMapper;
	
	@Autowired
	EuiccEumSignatureMapper euiccEumSignatureMapper;
	
	@Autowired
	EuiccRetrievalMethodTypeMapper euiccRetrievalMethodTypeMapper;
	
	@Autowired
	EuiccRetrievalTransformTypeMapper euiccRetrievalTransformTypeMapper;
	
	@Autowired
	EuiccCanonicalizationMethodTypeMapper euiccCanonicalizationMethodTypeMapper;
	
	@Autowired
	EuiccSignedInfoTypeMapper euiccSignedInfoTypeMapper;
	
	@Autowired
	EuiccReferenceTypeMapper euiccReferenceTypeMapper;
	
	@Autowired
	EuiccReferenceTransformTypeMapper euiccReferenceTransformTypeMapper;
	
	@Autowired
	EuiccDigestMethodTypeMapper euiccDigestMethodTypeMapper;
	
	@Autowired
	EuiccObjectTypeMapper euiccObjectTypeMapper;
	
	@Override
	@Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
	public int insertEuiccCardSelective(EIS eis) {
		if (eis == null || eis.getEuiccCard() == null || eis.getEuiccCard().getEid() == null) return 0;
		String eid = eis.getEuiccCard().getEid();
		String ecasdId = null;
		String signatureId = null;
		String signedInfoId = null;
		String keyInfoId = null;
		String retrievalId = null;
		
		EuiccCard euiccCard = eis.getEuiccCard();
		EuiccCapabilities euiccCapabilities = eis.getEuiccCapabilities();
		List<EuiccProfileWithBLOBs> euiccProfileList = eis.getEuiccProfileList();
		List<EuiccPol2> euiccPol2List = eis.getEuiccPol2List();
		EuiccIsdR euiccIsdR = eis.getEuiccIsdR();
		EuiccEcasd euiccEcasd = eis.getEuiccEcasd();
		List<EuiccEcasdKeyset> euiccEcasdKeysetList = eis.getEuiccEcasdKeysetList();
		List<EuiccEcasdTar> euiccEcasdTarList = eis.getEuiccEcasdTarList();
		List<EuiccIsdrKeyset> euiccIsdrKeysetList = eis.getEuiccIsdrKeysetList();
		List<EuiccIsdrTar> euiccIsdrTarList = eis.getEuiccIsdrTarList();
		List<EuiccAuditTrailRecord> euiccAuditTrailRecordList = eis.getEuiccAuditTrailRecordList();
		List<EuiccExecutionStatusType> euiccExecutionStatusTypeList = eis.getEuiccExecutionStatusTypeLsit();
		List<EuiccPropertyType> euiccPropertyTypeList = eis.getEuiccPropertyTypeList();
		EuiccEumSignature euiccEumSignature = eis.getEuiccEumSignature();
		EuiccKeyInfoType euiccKeyInfoType = eis.getEuiccKeyInfoType();
		EuiccRetrievalMethodType euiccRetrievalMethodType = eis.getEuiccRetrievalMethodType();
		List<EuiccRetrievalTransformType> euiccRetrievalTransformTypeList = eis.getEuiccRetrievalTransformTypeList();
		EuiccSignedInfoType euiccSignedInfoType = eis.getEuiccSignedInfoType();
		List<EuiccCanonicalizationMethodType> euiccCanonicalizationMethodTypeList = eis.getEuiccCanonicalizationMethodTypeList();
		List<EuiccReferenceType> euiccReferenceTypeList = eis.getEuiccReferenceTypeList();
		List<EuiccReferenceTransformType> euiccReferenceTransformTypeList = eis.getEuiccReferenceTransformTypeList();
		List<EuiccDigestMethodType> euiccDigestMethodTypeList = eis.getEuiccDigestMethodTypeList();
		List<EuiccObjectType> euiccObjectTypeList = eis.getEuiccObjectTypeList();
		
		EuiccCard cardInfo = euiccCardMapper.selectByPrimaryKey(eid);
		if(cardInfo != null) {
			ecasdId = cardInfo.getEcasdId();
			if (ecasdId != null) {
				euiccEcasdMapper.deleteByPrimaryKey(ecasdId);
				euiccEcasdKeysetMapper.deleteByEcasdId(ecasdId);
				euiccEcasdTarMapper.deleteByEcasdId(ecasdId);
			}
			signatureId = cardInfo.getSignatureId();
			if (signatureId != null) {
				EuiccEumSignature signatureInfo = euiccEumSignatureMapper.selectByPrimaryKey(signatureId);
				euiccEumSignatureMapper.deleteByPrimaryKey(signatureId);
				euiccObjectTypeMapper.deleteBySignatureId(signatureId);
				if (signatureInfo != null) {
					signedInfoId = signatureInfo.getSingedInfoId();
					if (signedInfoId != null) {
						EuiccReferenceTypeExample euiccReferenceTypeExample = new EuiccReferenceTypeExample();
						EuiccReferenceTypeExample.Criteria euiccReferenceTypeCriteria = euiccReferenceTypeExample.createCriteria();
						euiccReferenceTypeCriteria.andSignedInfoIdEqualTo(signedInfoId);
						List<EuiccReferenceType> referenceTypeList = euiccReferenceTypeMapper.selectByExample(euiccReferenceTypeExample);
						if (referenceTypeList != null && referenceTypeList.size() > 0) {
							for (EuiccReferenceType referenceType : referenceTypeList) {
								if (referenceType.getReferenceId() != null) {
									euiccReferenceTransformTypeMapper.deleteByReferenceId(referenceType.getReferenceId());
									euiccDigestMethodTypeMapper.deleteByReferenceId(referenceType.getReferenceId());
								}
							}
						}
						euiccReferenceTypeMapper.deleteBySingedInfoId(signedInfoId);
						euiccSignedInfoTypeMapper.deleteByPrimaryKey(signedInfoId);
						euiccCanonicalizationMethodTypeMapper.deleteBySingedInfoId(signedInfoId);
					}
					keyInfoId = signatureInfo.getKeyInfoId();
					if (keyInfoId != null) {
						EuiccKeyInfoType keyInfo = euiccKeyInfoTypeMapper.selectByPrimaryKey(keyInfoId);
						if (keyInfo != null) {
							retrievalId = keyInfo.getRetrievalId();
							if (retrievalId != null) {
								euiccRetrievalMethodTypeMapper.deleteByPrimaryKey(retrievalId);
								euiccRetrievalTransformTypeMapper.deleteByRetrievalId(retrievalId);
							}
						}
						euiccKeyInfoTypeMapper.deleteByPrimaryKey(keyInfoId);
					}
				}
			}
		}
		
		euiccCardMapper.deleteByPrimaryKey(eid);
		euiccCapabilitiesMapper.deleteByEid(eid);
		
		EuiccIsdRExample euiccIsdRExample = new EuiccIsdRExample();
		EuiccIsdRExample.Criteria euiccIsdRCriteria = euiccIsdRExample.createCriteria();
		euiccIsdRCriteria.andEidEqualTo(eid);
		List<EuiccIsdR> isdRInfoList = euiccIsdRMapper.selectByExample(euiccIsdRExample);
		if (isdRInfoList != null && isdRInfoList.size() > 0) {
			for(EuiccIsdR isdR : isdRInfoList) {
				euiccIsdrKeysetMapper.deleteByRid(isdR.getrId());
				euiccIsdrTarMapper.deleteByRid(isdR.getrId());
			}
		}
		euiccIsdRMapper.deleteByEid(eid);
		
		EuiccProfileExample euiccProfileExample = new EuiccProfileExample();
		EuiccProfileExample.Criteria euiccProfileCriteria = euiccProfileExample.createCriteria();
		euiccProfileCriteria.andEidEqualTo(eid);
		List<EuiccProfile> profileList = euiccProfileMapper.selectByExample(euiccProfileExample);
		for (EuiccProfile profile : profileList) {
			if (profile.getPol2Id() != null) {
				euiccPol2Mapper.deleteByPrimaryKey(profile.getPol2Id());
			}
		}
		euiccProfileMapper.deleteByEid(eid);
			
		EuiccAuditTrailRecordExample euiccAuditTrailRecordExample = new EuiccAuditTrailRecordExample();
		EuiccAuditTrailRecordExample.Criteria euiccAuditTrailRecordCriteria = euiccAuditTrailRecordExample.createCriteria();
		euiccAuditTrailRecordCriteria.andEidEqualTo(eid);
		List<EuiccAuditTrailRecord> auditTrailRecordList = euiccAuditTrailRecordMapper.selectByExample(euiccAuditTrailRecordExample);
		if (auditTrailRecordList != null && auditTrailRecordList.size() > 0) {
			for (EuiccAuditTrailRecord auditTrailRecord : auditTrailRecordList) {
				if (auditTrailRecord.getAuditId() != null) {
					euiccExecutionStatusTypeMapper.deleteByAuditId(auditTrailRecord.getAuditId());
				}
			}
		}
		euiccAuditTrailRecordMapper.deleteByEid(eid);
		euiccPropertyTypeMapper.deleteByEid(eid);

		int flg = euiccCardMapper.insertSelective(euiccCard);
		if (euiccCapabilities != null) {
			flg += euiccCapabilitiesMapper.insertSelective(euiccCapabilities);
		}
		if (euiccIsdR != null) {
			flg += euiccIsdRMapper.insertSelective(euiccIsdR);
		}
		if (euiccEcasd != null) {
			flg += euiccEcasdMapper.insertSelective(euiccEcasd);
		}
		
		if (euiccProfileList != null && euiccProfileList.size() > 0) {
			for (EuiccProfileWithBLOBs profile : euiccProfileList) {
				flg += euiccProfileMapper.insertSelective(profile);
			}
		}
		
		if (euiccPol2List != null && euiccPol2List.size() > 0) {
			for(EuiccPol2 pol2 : euiccPol2List) {
				flg += euiccPol2Mapper.insertSelective(pol2);
			}
		}
		
		if (euiccEcasdKeysetList != null && euiccEcasdKeysetList.size() > 0) {
			for (EuiccEcasdKeyset euiccEcasdKeyset : euiccEcasdKeysetList) {
				flg += euiccEcasdKeysetMapper.insertSelective(euiccEcasdKeyset);
			}
		}
		
		if (euiccEcasdTarList != null && euiccEcasdTarList.size() > 0) {
			for (EuiccEcasdTar euiccEcasdTar : euiccEcasdTarList) {
				flg += euiccEcasdTarMapper.insertSelective(euiccEcasdTar);
			}
		}
		
		if (euiccIsdrKeysetList != null && euiccIsdrKeysetList.size() > 0) {
			for (EuiccIsdrKeyset euiccIsdrKeyset : euiccIsdrKeysetList) {
				flg += euiccIsdrKeysetMapper.insertSelective(euiccIsdrKeyset);
			}
		}
		
		if (euiccIsdrTarList != null && euiccIsdrTarList.size() > 0) {
			for (EuiccIsdrTar euiccIsdrTar : euiccIsdrTarList) {
				flg += euiccIsdrTarMapper.insertSelective(euiccIsdrTar);
			}
		}
		
		if (euiccAuditTrailRecordList != null && euiccAuditTrailRecordList.size() > 0) {
			for (EuiccAuditTrailRecord auditTrail : euiccAuditTrailRecordList) {
				flg += euiccAuditTrailRecordMapper.insertSelective(auditTrail);
			}
		}
		
		if (euiccExecutionStatusTypeList != null && euiccExecutionStatusTypeList.size() > 0) {
			for (EuiccExecutionStatusType euiccExecutionStatusType : euiccExecutionStatusTypeList) {
				flg += euiccExecutionStatusTypeMapper.insertSelective(euiccExecutionStatusType);
			}
		}
		
		if (euiccKeyInfoType != null) {
			flg += euiccKeyInfoTypeMapper.insertSelective(euiccKeyInfoType);
		}
		
		if (euiccPropertyTypeList != null && euiccPropertyTypeList.size() > 0) {
			for (EuiccPropertyType euiccPropertyType : euiccPropertyTypeList) {
				flg += euiccPropertyTypeMapper.insertSelective(euiccPropertyType);
			}
		}
		
		if (euiccEumSignature != null) {
			flg += euiccEumSignatureMapper.insertSelective(euiccEumSignature);
		}
		
		if (euiccRetrievalMethodType != null) {
			flg += euiccRetrievalMethodTypeMapper.insertSelective(euiccRetrievalMethodType);
		}
		
		if (euiccRetrievalTransformTypeList != null && euiccRetrievalTransformTypeList.size() > 0) {
			for (EuiccRetrievalTransformType euiccRetrievalTransformType : euiccRetrievalTransformTypeList) {
				flg += euiccRetrievalTransformTypeMapper.insertSelective(euiccRetrievalTransformType);
			}
		}
		
		if (euiccCanonicalizationMethodTypeList != null && euiccCanonicalizationMethodTypeList.size() > 0) {
			for (EuiccCanonicalizationMethodType euiccCanonicalizationMethodType : euiccCanonicalizationMethodTypeList) {
				flg += euiccCanonicalizationMethodTypeMapper.insertSelective(euiccCanonicalizationMethodType);
			}
		}
		
		if (euiccSignedInfoType != null) {
			flg += euiccSignedInfoTypeMapper.insertSelective(euiccSignedInfoType);
		}
		
		if (euiccReferenceTypeList != null && euiccReferenceTypeList.size() > 0) {
			for (EuiccReferenceType euiccReferenceType : euiccReferenceTypeList) {
				flg += euiccReferenceTypeMapper.insertSelective(euiccReferenceType);
			}
		}
		
		if (euiccReferenceTransformTypeList != null && euiccReferenceTransformTypeList.size() > 0) {
			for (EuiccReferenceTransformType euiccReferenceTransformType : euiccReferenceTransformTypeList) {
				flg += euiccReferenceTransformTypeMapper.insertSelective(euiccReferenceTransformType);
			}
		}
		
		if (euiccDigestMethodTypeList != null && euiccDigestMethodTypeList.size() > 0) {
			for (EuiccDigestMethodType euiccDigestMethodType : euiccDigestMethodTypeList) {
				flg += euiccDigestMethodTypeMapper.insertSelective(euiccDigestMethodType);
			}
		}
		
		if (euiccObjectTypeList != null && euiccObjectTypeList.size() > 0) {
			for (EuiccObjectType euiccObjectType : euiccObjectTypeList) {
				flg += euiccObjectTypeMapper.insertSelective(euiccObjectType);
			}
		}
		return flg;
	}

}
