package com.otpv0.fragment.cach0;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otpv0.service.model.request.OtpCacheRequest;
import com.otpv0.service.model.response.BaseCacheResponse;
import com.otpv0.util.ActConstants;

@Service
public class OtpCacheService {

	@Autowired
	CachOtpClient cachClient;
	
	public void generaESalva(String transactionId, String bt, String timestamp, String profilo, LocalDateTime timestampDate,
			String otp) {

		// setto dto da passare in cache con le varie info per fare check
		OtpCacheRequest request = new OtpCacheRequest();
		request.setBt(bt);
		request.setProfilo(getProfilo(profilo));
		request.setTimestamp(timestamp);
		request.setTimestampDate(timestampDate);
		request.setTransactionId(transactionId);
		request.setOtp(otp);

		// chiamo cache 
		BaseCacheResponse cacheDTO = cachClient.insertOtpCache(request);
		
		if(cacheDTO.getInsert() != true) {
			// TODO lanciare eccezzione, da implementare
		}
	}


	private String getProfilo(String profilo) {

		switch (profilo) {
		case ActConstants.ProfMatch.APP:
			return ActConstants.Profili.APP_PROFILE;

		case ActConstants.ProfMatch.WEB:
			return ActConstants.Profili.WEB_PROFILE;

		default:
			return ActConstants.Profili.COMBO_PROFILE;
		}
	}


}
