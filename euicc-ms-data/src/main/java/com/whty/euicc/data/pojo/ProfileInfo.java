package com.whty.euicc.data.pojo;

import java.util.List;

public class ProfileInfo {
	public List<ProfileList> PROFILE_LIST;
	
	public static class ProfileList {
		public List<Profile> PROFILE;
	}
	
	public static class Profile {
		public String ICCID;
		public String EID;
		public String ISD_P_AID;
		public String MNO_ID;
		public String FALLBACK_ATTRIBUTE;
		public String IMSI;
		public String MSISDN;
		public String STATE;
		public String SMDP_ID;
		public String PROFILE_TYPE;
		public String POL2_ID;
		public String PHONE_NO;
	}
}
