package com.otpv0.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.otpv0.fragment.cach0.PushCacheService;
import com.otpv0.model.PushDto;
import com.otpv0.service.model.request.PushRequest;
import com.otpv0.service.model.response.PushResponse;
import com.otpv0.util.ActConstants;
import com.otpv0.util.CommonUtils;

@Service
public class AceptPushService {

	@Autowired
	CommonUtils utils;
	@Autowired
	PushCacheService push;
	
	Logger logger = LoggerFactory.getLogger(AceptPushService.class);
	
	
	public PushResponse acceptPush(PushRequest request) {
		
		logger.info("API :acceptPush - START with raw request:"+ request);
		
		PushResponse response = new PushResponse();
		//chiamo la getpush in cache
		PushDto pushDto = push.getPushDto(request.getBt());

		if(ObjectUtils.isEmpty(pushDto)) {
			response.setNoFound(true);
			response.setMsg("Error on retrive status push");
			logger.info("API :acceptPush - END with response:"+ response.toString());
			return response;
		}
		// se status e pending e push e ancora valida
		if(ActConstants.PushStatus.PENDING.equals(pushDto.getStatus()) && utils.pushTimeValid(pushDto.getTime())) {

			pushDto.setStatus(ActConstants.PushStatus.ACEPTED);
			push.updatePushStatus(pushDto);
			response.setAcepted(true);
			logger.info("API :acceptPush - END with response:"+ response.toString());
			return response;
			
		}
		else {
			pushDto.setStatus(ActConstants.PushStatus.REJECTED);
			push.updatePushStatus(pushDto);
			response.setAcepted(false);
			logger.info("API :acceptPush - END with response:"+ response.toString());
			return response;
		}
	}
}
