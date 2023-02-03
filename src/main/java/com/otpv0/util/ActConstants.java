package com.otpv0.util;

public class ActConstants {

	
	public static class TipiEvento {
		
		private TipiEvento() {}
		
		public static final int OTP_1PROFILO = 1;
		public static final int OTP_2PROFILO = 2;
		public static final int OTP_3DOUBLE = 3;
	}
	
	
	public static class Profili {
		
		private Profili() {}
		
		public static final String APP_PROFILE = "CBM_APP_OTP1-AG";
		public static final String WEB_PROFILE = "CBM_WEB_OTP1-AL";
		public static final String COMBO_PROFILE = "CBM_APP/ABI_OTP1-AM";
	}
	
	public static class ProfMatch{
		
		private ProfMatch() {}
		
		public static final String APP = "AG";
		public static final String WEB = "AL";
		public static final String FIL = "AM";
	}
	
	public static class PushStatus{
		
		private PushStatus() {}
		
		public static final String PENDING = "pending";
		public static final String REJECTED = "rejected";
		public static final String ACEPTED = "acepted";
		public static final String TIMEOUT = "timeout";
	}
	
}
