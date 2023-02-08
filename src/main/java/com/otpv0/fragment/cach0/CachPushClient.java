package com.otpv0.fragment.cach0;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.otpv0.model.PushDto;
import com.otpv0.service.model.response.BaseCacheResponse;
import com.otpv0.service.model.response.PushChResponse;

import reactor.core.publisher.Mono;

@Component
public class CachPushClient {

	@Value("${config.cach0.end-point}")
	private String cachUri;
	WebClient webClient = WebClient.create(cachUri);

	Logger logger = LoggerFactory.getLogger(CachPushClient.class);
	
	// inserisco push in cache
	public BaseCacheResponse insertPushCache(PushDto request) {

		logger.info("Client :CachPushClient- insert - START with raw request: {}", request);
		
		BaseCacheResponse iResp = new BaseCacheResponse();
		Mono<BaseCacheResponse> response = null;
		
		String uri = UriComponentsBuilder.fromHttpUrl(cachUri + "/push/insert").toUriString();
		
		try {
			response = webClient.post()
					.uri(uri)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.body(Mono.just(request), PushDto.class)
					.retrieve()
					.bodyToMono(BaseCacheResponse.class);
			
			logger.info("Client :CachPushClient- insert - RAW response: {}", response);
		}
		catch(Exception e) {
			logger.error("Error on CachePushClient - insert", e);
			iResp.setMsg(e.getMessage());
			iResp.setInsert(false);
			
			return iResp;
		}
		iResp = response.block();
		
		logger.info("Client :CachPushClient- insert - response: {}", iResp);
		return iResp;
	}
	
	//update push cache
	public BaseCacheResponse updatePushCache(PushDto request) {

		logger.info("Client :CachPushClient- updatePush - START with raw request: {}", request);
		
		BaseCacheResponse iResp = new BaseCacheResponse();
		Mono<BaseCacheResponse> response = null;
		
		String uri = UriComponentsBuilder.fromHttpUrl(cachUri + "/push/update").toUriString();
		
		try {
			response = webClient.put()
					.uri(uri)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.body(Mono.just(request), PushDto.class)
					.retrieve()
					.bodyToMono(BaseCacheResponse.class);
			
			logger.info("Client :CachPushClient- insert - RAW response: {}", response);
		}
		catch(Exception e) {
			logger.error("Error on CachePushClient - update :", e);
			//TODO implementare eccezzione anche qui per 2 casi
			iResp.setMsg(e.getMessage());
			iResp.setInsert(false);
			return iResp;
		}
		iResp = response.block();
		logger.info("Client :CachPushClient- updatePush - response: {}", iResp);
		
		return iResp;
	}
	
	//get push cache
	public PushChResponse getPushCache(String bt) {
		
		logger.info("Client :CachPushClient- updatePush - START with raw request: {}", bt);
		
		PushChResponse iResp = new PushChResponse();
		Mono<PushChResponse> response = null;
		
		String uri = UriComponentsBuilder.fromHttpUrl(cachUri + "/push/get/"+bt).toUriString();
		
		try {
			response = webClient.get()
					.uri(uri)
					.accept(MediaType.APPLICATION_JSON)
					.retrieve()
					.bodyToMono(PushChResponse.class);
			
			logger.info("Client :CachPushClient- insert - RAW response: {}", response);
		}
		catch(Exception e) {
			logger.error("Error on CachePushClient - get :", e);
			iResp.setMsg(e.getMessage());
			iResp.setNoFound(true);
			return iResp;
		}
		iResp = response.block();
		logger.info("Client :CachPushClient- updatePush - get: {}", iResp);
		return iResp;
	}
	
}
