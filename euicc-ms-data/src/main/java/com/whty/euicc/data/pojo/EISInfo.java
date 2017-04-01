package com.whty.euicc.data.pojo;

import java.util.List;

public class EISInfo {
	public List<EisInfo> EIS;
	
	public static class EisInfo {
		public List<CardInfo> CARD;
		public List<IsdPListInfo> ISD_P_LIST;
		public List<IsdRInfo> ISD_R;
		public List<EcasdInfo> ECASD;
		public List<Scp80Info> SCP80;
		public List<Scp81Info> SCP81;
		public List<Scp03Info> SCP03;
	}
	
	public static class CardInfo {
		public String EID;
		public String EUM_ID;
		public String PRODUCTION_DATE;
		public String PLATFORM_TYPE;
		public String PLATFORM_VERSION;
		public String REMAINING_MEMORY;
		public String AVAILABLEMEMORYFORPROFILES;
		public String LAST_AUDIT_DATE;
		public String SMSR_ID;
		public String ECASD_ID;
		public String COUNT;
	}
	
	public static class IsdPListInfo {
		public List<IsdPInfo> ISD_P;
	}
	
	public static class IsdPInfo {
		public String P_ID;
		public String EID;
		public String ISD_P_STATE;
		public String CDREATE_DT;
		public String UPDATE_DT;
		public String ISD_P_LOADFILE_AID;
		public String ISD_P_MODULE_AID;
		public String CONNECTIVITY_PARAM;
		public String ISD_P_AID;
	}
	
	public static class IsdRInfo {
		public String R_ID;
		public String ISD_R_AID;
		public String EID;
		public String POL1_ID;
	}
	
	public static class EcasdInfo {
		public String ECASD_ID;
		public String CERT_ECASD_ECKA;
	}
	
	public static class Scp80Info {
		public String SCP80_ID;
		public String EID;
		public String ID;
		public String VERSION;
		public String DATA;
	}
	
	public static class Scp81Info {
		public String SCP81_ID;
		public String EID;
		public String ID;
		public String VERSION;
		public String DATA;
	}
	
	public static class Scp03Info {
		public String SCP03_ID;
		public String EID;
		public String ISD_P_AID;
		public String ID;
		public String VERSION;
		public String DATA;
	}
}
