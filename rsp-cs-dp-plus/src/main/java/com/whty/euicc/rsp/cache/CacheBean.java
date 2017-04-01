package com.whty.euicc.rsp.cache;

import java.util.List;

/**
 * https://my.oschina.net/lianlupeng/blog/489828
 * @author 11
 *
 */
public class CacheBean {
	//必须参数
//	private String eid;
	private String transactionId;
	//可选参数
	private String eid;
	private String matchingId;
	private String confirmationCode;
	private String svn;
//	private String transactionId;
	private String smdpChallenge;
	private String pkEuiccEcdsa;
	private String smdpSignature2;
	private List<String> getBppPackage;
	
	public String getSmdpSignature2() {
		return smdpSignature2;
	}

	public List<String> getGetBppPackage() {
		return getBppPackage;
	}

	public String getPkEuiccEcdsa() {
		return pkEuiccEcdsa;
	}

	public void setPkEuiccEcdsa(String pkEuiccEcdsa) {
		this.pkEuiccEcdsa = pkEuiccEcdsa;
	}

	public String getSvn() {
		return svn;
	}

	public String getEid() {
		return eid;
	}

	public String getMatchingId() {
		return matchingId;
	}

	public String getConfirmationCode() {
		return confirmationCode;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public String getSmdpChallenge() {
		return smdpChallenge;
	}

	private CacheBean(CacheBeanBuilder builder){
//		this.eid = builder.eid;
		this.transactionId = builder.transactionId;
		this.eid = builder.eid;
		this.matchingId = builder.matchingId;
		this.confirmationCode = builder.confirmationCode;
		this.svn = builder.svn;
		this.smdpChallenge = builder.smdpChallenge;
		this.pkEuiccEcdsa = builder.pkEuiccEcdsa;
		this.smdpSignature2 = builder.smdpSignature2;
		this.getBppPackage = builder.getBppPackage;
	}

	@Override
	public String toString() {
		return "CacheBean [transactionId=" + transactionId + ", eid=" + eid
				+ ", matchingId=" + matchingId + ", confirmationCode="
				+ confirmationCode + ", svn=" + svn + ", smdpChallenge="
				+ smdpChallenge + ", pkEuiccEcdsa=" + pkEuiccEcdsa
				+ ", smdpSignature2=" + smdpSignature2 + ", getBppPackage="
				+ getBppPackage + "]";
	}


	public static class CacheBeanBuilder{
		//必须参数
//		private String eid;
		private String transactionId;
		//可选参数
		private String eid;
		private String matchingId;
		private String confirmationCode;
		private String svn;
//		private String transactionId;
		private String smdpChallenge;
		private String pkEuiccEcdsa;
		private String smdpSignature2;
		private List<String> getBppPackage;
		/*public CacheBeanBuilder(String eid) {
			this.eid = eid;
		}*/
		
		public CacheBeanBuilder(String transactionId){
			this.transactionId = transactionId;
		}
		
		public CacheBeanBuilder setEid(String eid){
			this.eid = eid;
			return this;
		}
		
		public CacheBeanBuilder setMatchingId(String matchingId){
			this.matchingId = matchingId;
			return this;
		}
		
		public CacheBeanBuilder setConfirmationCode(String confirmationCode){
			this.confirmationCode = confirmationCode;
			return this;
		}
		
		public CacheBeanBuilder setSvn(String svn) {
			this.svn = svn;
			return this;
		}
		
		/*public CacheBeanBuilder setTransactionId(String transactionId) {
			this.transactionId = transactionId;
			return this;
		}*/

		public CacheBeanBuilder setSmdpChallenge(String smdpChallenge) {
			this.smdpChallenge = smdpChallenge;
			return this;
		}
		
		public CacheBeanBuilder setPkEuiccEcdsa(String pkEuiccEcdsa){
			this.pkEuiccEcdsa = pkEuiccEcdsa;
			return this;
		}
		
		public CacheBeanBuilder setSmdpSignature2(String smdpSignature2){
			this.smdpSignature2 = smdpSignature2;
			return this;
		}
		
		public CacheBeanBuilder setGetBppPackage(List<String> getBppPackage){
			this.getBppPackage = getBppPackage;
			return this;
		}

		public CacheBean build(){
            return new CacheBean(this);
        }

	}
	
	

	public CacheBean() {
	}
}
