package com.otpv0.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otpv0.service.AceptPushService;
import com.otpv0.service.GetPushService;
import com.otpv0.service.SendPushService;
import com.otpv0.service.model.request.PushRequest;
import com.otpv0.service.model.response.PushResponse;

@RestController
@RequestMapping("push")
public class PushController {

	@Autowired
	AceptPushService accept;
	@Autowired
	SendPushService send;
	@Autowired
	GetPushService get;
	
	@PostMapping("send")
	public PushResponse sendPush(@RequestBody PushRequest request) {
		return send.sendNotifyPush(request);
	}
	
	@PostMapping("confirm")
	public PushResponse confirmPush(@RequestBody PushRequest request) {
		return accept.acceptPush(request);
	}
	@PostMapping("get")
	public PushResponse getPushStatus(@RequestBody PushRequest request) {
		return get.getStatusPush(request);
	}
}
