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
public class GetPushService {

	@Autowired
	CommonUtils utils;
	@Autowired
	PushCacheService push;
	
	Logger logger = LoggerFactory.getLogger(GetPushService.class);
	
	// get push per polling
	public PushResponse getStatusPush(PushRequest request) {

		logger.info("API :getStatusPush - START with raw request:"+ request.toString());
		
		PushResponse response = new PushResponse();
		//chiamo la get in cache
		PushDto pushDto = push.getPushDto(request.getBt());
		if(ObjectUtils.isEmpty(pushDto)) {
			response.setNoFound(true);
			response.setMsg("Error on retrive status push");
			logger.info("API :getStatusPush - END with response:"+ response.toString());
			return response;
		}

		//vediamo se è stata validata prima di tutto
		else if(ActConstants.PushStatus.ACEPTED.equals(pushDto.getStatus())) {
			response.setAcepted(true);
			response.setMsg("Push validated");
			response.setStatus(pushDto.getStatus());
			logger.info("API :getStatusPush - END with response:"+ response.toString());
			return response;
		}
		else if(ActConstants.PushStatus.PENDING.equals(pushDto.getStatus())) {
			//se e in pending controllo che sia ancora valida
			if(! utils.pushTimeValid(pushDto.getTime())) {
				pushDto.setStatus(ActConstants.PushStatus.TIMEOUT);
				// updato la cache
				push.updatePushStatus(pushDto);
				response.setAcepted(false);
				response.setStatus(pushDto.getStatus());
				response.setMsg("timeout");
				logger.info("API :getStatusPush - END with response:"+ response.toString());
				return response;
			}

			response.setStatus(pushDto.getStatus());
			response.setMsg("pending");
			logger.info("API :getStatusPush - END with response:"+ response.toString());
			return response;

		}
		else {
			response.setAcepted(false);
			response.setStatus(pushDto.getStatus());
			logger.info("API :getStatusPush - END with response:"+ response.toString());
			return response;
		}
	}
}
