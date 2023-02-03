package com.otpv0.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otpv0.fragment.cach0.PushCacheService;
import com.otpv0.model.PushDto;
import com.otpv0.service.model.request.PushRequest;
import com.otpv0.service.model.response.PushResponse;
import com.otpv0.util.ActConstants;
import com.otpv0.util.CommonUtils;

@Service
public class SendPushService {

	@Autowired
	CommonUtils utils;
	@Autowired
	PushCacheService push;
	
	// manda push
	public PushResponse sendNotifyPush(PushRequest request) {

		PushResponse response = new PushResponse();

		// faccio finta di inviare push
		utils.sendPush();

		//setto dto da salvare in cache
		PushDto pushDto = new PushDto();
		pushDto.setBt(request.getBt());
		pushDto.setBandaId(request.getBancaId());
		pushDto.setTime(LocalDateTime.now());
		pushDto.setStatus(ActConstants.PushStatus.PENDING);
		// chiamata a cach e inserisco
		push.insertCachPush(pushDto);
		
		response.setSended(true);
		return response;
	}

}
