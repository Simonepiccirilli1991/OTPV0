package com.otpv0.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otpv0.service.CheckOtpService;
import com.otpv0.service.GenerateOtpService;
import com.otpv0.service.model.request.CheckOtpRequest;
import com.otpv0.service.model.request.GenerateOtpRequest;
import com.otpv0.service.model.response.CheckOtpResponse;
import com.otpv0.service.model.response.GenerateOtpResponse;

@RestController
@RequestMapping("v1")
public class OtpControllerV1 {

	@Autowired
	GenerateOtpService otpService;
	@Autowired
	CheckOtpService checkService;
	
	// non passo transactionId in request, lo genero internamente.
	@PostMapping("generateOtp")
	public GenerateOtpResponse generateOtp(@RequestBody GenerateOtpRequest request, @RequestHeader HttpHeaders header) {
		
		return otpService.generateOtpNoTransaction(request, header);
	}
	
	
	@PostMapping("checkotp")
	public CheckOtpResponse checkOtp(@RequestBody CheckOtpRequest request, @RequestHeader HttpHeaders header) {
		
		return checkService.checkOtp(request);
	}
	
	// mock service that don't send otp really cause otp is hardcoded for svil e test
	@PostMapping("mock/generateOtp")
	public GenerateOtpResponse generateOtpMock(@RequestBody GenerateOtpRequest request, @RequestHeader HttpHeaders header) {
		
		return otpService.generateOtpNoTransaction(request, header);
	}

	
	
}
