package com.otpv0.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otpv0.service.GenerateOtpService;
import com.otpv0.service.model.request.GenerateOtpRequest;
import com.otpv0.service.model.response.GenerateOtpResponse;

@RestController
@RequestMapping("v1")
public class OtpControllerV1 {

	@Autowired
	GenerateOtpService otpService;
	
	// non passo transactionId in request, lo genero internamente.
	@RequestMapping("generateOtp/notransaction")
	public GenerateOtpResponse generateOtp(@RequestBody GenerateOtpRequest request, @RequestHeader HttpHeaders header) {	
		GenerateOtpResponse response = new GenerateOtpResponse();
		
		return otpService.generateOtpNoTransaction(request, header);
	}
	
}
