package com.otpv0.service;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.otpv0.fragment.cach0.Cach0Client;
import com.otpv0.fragment.cach0.OtpCacheService;
import com.otpv0.fragment.classic.ChiaveIdResponse;
import com.otpv0.fragment.classic.CreaChiaveId;
import com.otpv0.service.model.request.GenerateOtpRequest;
import com.otpv0.service.model.response.GenerateOtpResponse;
import com.otpv0.util.ActConstants;
import com.otpv0.util.GeneraEInviaService;

@Service
public class GenerateOtpService {
	
	/* ==== PROPERTIES AND BEANS ========================================================================================= */
	@Autowired 
	CreaChiaveId chiaveId;
	@Autowired
	GeneraEInviaService generate;
	@Autowired
	Cach0Client client;
	@Autowired
	OtpCacheService otpService;

	Logger logger = LoggerFactory.getLogger(GenerateOtpService.class);

	// genera e gestisce transactionId internamente , no imput
	public GenerateOtpResponse generateOtpNoTransaction(GenerateOtpRequest request, HttpHeaders header) {
		logger.info("API :generateOtpNoTransaction - START");
		GenerateOtpResponse response = new GenerateOtpResponse();

		// genero TransactionId
		ChiaveIdResponse transactionId = chiaveId.crea(request.getBt(), request.getAbi(), request.getProf());
		// genero otp
		String otp = generate.randomString(7);
		//salvo in cache con trxId come chiave
		otpService.generaESalva(transactionId.getChiaveId(), request.getBt(), transactionId.getTimeStamp(), request.getProf(), transactionId.getTimestampDate(), otp);
		// invia otp via mail
		generate.invia(otp, request.getEmail());	
		
		response.setTrxId(transactionId.getChiaveId());
		response.setOtpSend(true);
		response.setMsg("otp sended");
		
		logger.info("API :generateOtpNoTransaction - END");
		
		return response;
	}
	
	//Mocked geterateOtp - otp hardcoded for svil e test
	public GenerateOtpResponse mockOtpGenerate(GenerateOtpRequest request) {
		
		logger.info("API: mockOtpGenerate - START");
		GenerateOtpResponse response = new GenerateOtpResponse();

		// genero TransactionId
		ChiaveIdResponse transactionId = chiaveId.crea(request.getBt(), request.getAbi(), request.getProf());
		// otp hardcodato per test
		String otp = "111111";
		//salvo in cache con trxId come chiave
		otpService.generaESalva(transactionId.getChiaveId(), request.getBt(), transactionId.getTimeStamp(), request.getProf(), transactionId.getTimestampDate(), otp);
		
		
		response.setTrxId(transactionId.getChiaveId());
		response.setOtpSend(true);
		response.setMsg("otp sended");
		
		logger.info("API: mockOtpGenerate - END");
		
		return response;
	}
}
