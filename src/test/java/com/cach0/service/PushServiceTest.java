package com.cach0.service;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.otpv0.Otpv01Application;
import com.otpv0.fragment.cach0.CachPushClient;
import com.otpv0.model.PushDto;
import com.otpv0.service.AceptPushService;
import com.otpv0.service.GetPushService;
import com.otpv0.service.SendPushService;
import com.otpv0.service.model.request.PushRequest;
import com.otpv0.service.model.response.BaseCacheResponse;
import com.otpv0.service.model.response.PushChResponse;
import com.otpv0.service.model.response.PushResponse;
import com.otpv0.util.ActConstants;

@SpringBootTest(classes = Otpv01Application.class)
public class PushServiceTest {

	@Autowired
	GetPushService polling;
	@Autowired
	AceptPushService accept;
	@Autowired
	SendPushService send;
	@MockBean
	CachPushClient pushClient;
	
	@Test
	public void sendPushTestOK() {
		
		PushRequest request = new PushRequest();
		request.setBancaId("badnaId");
		request.setBt("bt");
		
		BaseCacheResponse cacheResp = new BaseCacheResponse();
		cacheResp.setMsg("daje");
		cacheResp.setInsert(true);
		
		when(pushClient.insertPushCache(any())).thenReturn(cacheResp);
		
		PushResponse response = send.sendNotifyPush(request);
		
		assertThat(response.getSended()).isTrue();
	}
	
	@Test
	public void acceptPushTestOK() {
		
		PushRequest request = new PushRequest();
		request.setBancaId("badnaId");
		request.setBt("bt");
		
		BaseCacheResponse cacheResp = new BaseCacheResponse();
		cacheResp.setMsg("daje");
		cacheResp.setInsert(true);
		
		PushChResponse chResponse = new PushChResponse();
		chResponse.setNoFound(false);
		chResponse.setBandaId("bancaId");
		chResponse.setBt("bt-1");
		chResponse.setTime(LocalDateTime.now());
		chResponse.setStatus(ActConstants.PushStatus.PENDING);
		
		when(pushClient.getPushCache(any())).thenReturn(chResponse);
		
		when(pushClient.updatePushCache(any())).thenReturn(chResponse);
		
		PushResponse response = accept.acceptPush(request);
		
		assertThat(response.getAcepted()).isTrue();
	}
	
	@Test
	public void getPushTestOK() {
		
		PushRequest request = new PushRequest();
		request.setBancaId("badnaId");
		request.setBt("bt");
		
		PushChResponse chResponse = new PushChResponse();
		chResponse.setNoFound(false);
		chResponse.setBandaId("bancaId");
		chResponse.setBt("bt-1");
		chResponse.setTime(LocalDateTime.now());
		chResponse.setStatus(ActConstants.PushStatus.PENDING);
		
		when(pushClient.getPushCache(any())).thenReturn(chResponse);
		
		PushResponse response = polling.getStatusPush(request);
		
		assertThat(response.getMsg()).isEqualTo("pending");
		assertThat(response.getStatus()).isEqualTo(ActConstants.PushStatus.PENDING);
	}
}
