package com.otpv0.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.otpv0.model.PushDto;
import com.otpv0.service.model.request.PushRequest;
import com.otpv0.service.model.response.PushResponse;
import com.otpv0.util.ActConstants;

@Service
public class SendPushService {

	// manda push
	public PushResponse sendNotifyPush(PushRequest request) {

		PushResponse response = new PushResponse();

		// faccio finta di inviare push
		sendPush();

		//setto dto da salvare in cache
		PushDto pushDto = new PushDto();
		pushDto.setBt(request.getBt());
		pushDto.setBandaId(request.getBancaId());
		pushDto.setTime(LocalDateTime.now());
		pushDto.setStatus(ActConstants.PushStatus.PENDING);
		// chiamata a cach e inserisco

		response.setSended(true);
		return response;
	}
	// valida push
	public PushResponse acceptPush(PushRequest request) {

		PushResponse response = new PushResponse();
		//chiamo la getpush in cache
		PushDto pushDto = null;

		if(ObjectUtils.isEmpty(pushDto)) {
			response.setNoFound(true);
			response.setMsg("Error on retrive status push");
			return response;
		}
		// se status e pending e push e ancora valida
		if(ActConstants.PushStatus.PENDING.equals(pushDto.getStatus()) && pushTimeValid(pushDto.getTime())) {

			pushDto.setStatus(ActConstants.PushStatus.ACEPTED);
			//TODO updato cache
			response.setAcepted(true);
			return response;
		}
		else {
			pushDto.setStatus(ActConstants.PushStatus.REJECTED);
			//TODO updato cache
			response.setAcepted(false);
			return response;
		}
	}
	// get push per polling
	public PushResponse getStatusPush(PushRequest request) {

		PushResponse response = new PushResponse();
		//chiamo la get in cache
		PushDto pushDto = null;
		if(ObjectUtils.isEmpty(pushDto)) {
			response.setNoFound(true);
			response.setMsg("Error on retrive status push");
			return response;
		}

		//vediamo se è stata validata prima di tutto
		else if(ActConstants.PushStatus.ACEPTED.equals(pushDto.getStatus())) {
			response.setAcepted(true);
			response.setMsg("Push validated");
			response.setStatus(pushDto.getStatus());
			return response;
		}
		else if(ActConstants.PushStatus.PENDING.equals(pushDto.getStatus())) {
			//se e in pending controllo che sia ancora valida
			if(! pushTimeValid(pushDto.getTime())) {
				pushDto.setStatus(ActConstants.PushStatus.TIMEOUT);
				// updato la cache
				//-----------------//
				response.setAcepted(false);
				response.setStatus(pushDto.getStatus());
				response.setMsg("timeout");
				return response;
			}

			response.setStatus(pushDto.getStatus());
			response.setMsg("pending");
			return response;

		}
		else {
			response.setAcepted(false);
			response.setStatus(pushDto.getStatus());
			return response;
		}
	}

	private Boolean pushTimeValid(LocalDateTime timePush) {

		var timeNow = LocalDateTime.now();

		if(timeNow.isAfter(timePush.plusMinutes(1)))
			return false;
		else
			return true;
	}


	private void sendPush() {
		// fa un cazzo , fake push tanto per
	}
}
