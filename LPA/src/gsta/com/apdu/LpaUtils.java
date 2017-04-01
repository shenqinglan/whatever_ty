package gsta.com.apdu;

import gsta.com.utils.RATBean;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class LpaUtils {
	
	/**
	 * 检查dp地址
	 * @return
	 */
	public static boolean checkDpAddress() {
		return true;
	}
	
	/**
	 * 生成上下文参数
	 * @return
	 */
	public static String generateCtxParams1() {
		return "0102030405060708";
	}
	
	/**
	 * 是否包含PPR
	 * @return
	 */
	public static boolean isContainPPR(String profileMetadata) {
		profileMetadata = profileMetadata.substring(profileMetadata.indexOf("B7"));
		if (profileMetadata.length() > 14) {
			return true;
		}
		return false;
	}
	
	/**
	 * RAT是否允许PPR
	 * @param list 
	 * @param ppr 
	 * @param profileOwner 
	 * @return
	 */
	public static boolean isRATAllowedPPR(List<RATBean> list, String profileOwner, String ppr) {
		StringBuilder pprIdBuilder = new StringBuilder();
		for(RATBean bean : list){
			String pprId = bean.getPprId();
			pprIdBuilder.append(pprId);
		}
		//返回AllowedOperator字段
		if(pprIdBuilder.toString().contains(ppr)){
			String ratProfileOwner = list.get(pprIdBuilder.toString().indexOf(ppr)/4).getAllowedOperator();
			if(StringUtils.equals("EEEEEE", ratProfileOwner) || StringUtils.equals(profileOwner, ratProfileOwner)){
				return true;
			}
			return false;
		}
		
		return false;
		
	}
	
	/**
	 * 是否需要终端用户同意此ppr
	 * @param list
	 * @param profileOwner
	 * @param ppr
	 * @return
	 */
	public static String isEndUserConsent(List<RATBean> list, String profileOwner, String ppr) {
		StringBuilder pprIdBuilder = new StringBuilder();
		for(RATBean bean : list){
			String pprId = bean.getPprId();
			pprIdBuilder.append(pprId);
		}
		//返回endUserConsent字段
		return list.get(pprIdBuilder.toString().indexOf(ppr)/4).getEndUserConsent();
		
	}
	
	/**
	 * 是否需要确认码
	 * @return
	 */
	public static boolean isConfirmationCodeRequired(String smdpSignature2) {
		return smdpSignature2.endsWith("01");
	}
	
	/**
	 * 检查元数据
	 */
	public static boolean checkMetadata() {
		return true;
	}
}
