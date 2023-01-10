package com.otpv0.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otpv0.fragment.cach0.Cach0Client;
import com.otpv0.service.model.request.CheckOtpRequest;
import com.otpv0.service.model.response.CheckOtpCacheResponse;
import com.otpv0.service.model.response.CheckOtpResponse;

@Service
public class CheckOtpService {

	
	@Autowired
	Cach0Client cacheClient;
	
	
	public CheckOtpResponse checkOtp(CheckOtpRequest request) {
		
		CheckOtpResponse response = new CheckOtpResponse();
		
		// chiamo cache per trxId e prendo dto salvato
		CheckOtpCacheResponse cacheDto = cacheClient.getOtpCache(request.getTransactionId());
		
		if(cacheDto.getIsPresent() != true) {
			response.setMsg("Error");
			response.setAutenticationSucc(false);
			return response;
		}
		
		// checko otp
		if(Boolean.TRUE.equals(cacheDto.getOtp().equals(request.getOtp()))) {
			
			// controllo che profilo e bt siano uguali
			if(Boolean.TRUE.equals(cacheDto.getBt().equals(request.getBt())) &&
					Boolean.TRUE.equals(cacheDto.getProfilo().equals(request.getProfile()))) {
				
				response.setAutenticationSucc(true);
				response.setMsg("Autenticazione effettuata");
			}
			else {
				response.setAutenticationSucc(false);
				response.setMsg("Errore profilo, usare profilo corretto");
				response.setMsg("Error");
			}
		}
		else {
			response.setAutenticationSucc(false);
			response.setErrorMsg("Otp errato");
			response.setMsg("Error");
		}
		
		return response;
	}
}
