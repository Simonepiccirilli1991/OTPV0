package com.otpv0.fragment.cach0;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.otpv0.service.model.request.OtpCacheRequest;
import com.otpv0.service.model.response.BaseCacheResponse;
import com.otpv0.service.model.response.CheckOtpCacheResponse;

import reactor.core.publisher.Mono;

@Component
public class CachOtpClient {

	@Value("${config.cach0.end-point}")
	private String iwdbUri;
	WebClient webClient = WebClient.create(iwdbUri);
	
	Logger logger = LoggerFactory.getLogger(CachOtpClient.class);
	
	// inserisco otp in cache
	public BaseCacheResponse insertOtpCache(OtpCacheRequest request) {

		BaseCacheResponse iResp = new BaseCacheResponse();
		Mono<BaseCacheResponse> response = null;
		logger.info("Client :insertOtpCache - START with raw request:"+ request);
		
		String uri = UriComponentsBuilder.fromHttpUrl(iwdbUri + "/otp/insert").toUriString();
		
		try {
			response = webClient.post()
					.uri(uri)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.body(Mono.just(request), OtpCacheRequest.class)
					.retrieve()
					.bodyToMono(BaseCacheResponse.class);
			logger.info("Client :insertOtpCache - raw response:"+ response);
		}
		catch(Exception e) {
			logger.info("Client :insertOtpCache - EXCEPTION with :"+ e);
			iResp.setMsg(e.getMessage());
			iResp.setInsert(false);
			return iResp;
		}
		iResp = response.block();
		
		logger.info("Client :insertOtpCache - END with response:"+ iResp);
		return iResp;
	}
	
	
	//getCacheOtp
	public CheckOtpCacheResponse getOtpCache(String trxId) {
		
		logger.info("Client :insertOtpCache - START with raw request:"+ trxId);
		
		CheckOtpCacheResponse response = new CheckOtpCacheResponse();
		Mono<CheckOtpCacheResponse> iResp = null;
		
		String uri = UriComponentsBuilder.fromHttpUrl(iwdbUri + "/otp/get").toUriString();
		
		try {
			iResp = webClient.post()
					.uri(uri)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.body(Mono.just(trxId), String.class)
					.retrieve()
					.bodyToMono(CheckOtpCacheResponse.class);
			
			logger.info("Client :insertOtpCache - END with response:"+ iResp);
		}
		catch(Exception e) {
			
			response.setMsg(e.getMessage());
			response.setIsPresent(false);
			return response;
		}
		response = iResp.block();
		
		logger.info("Client :insertOtpCache - raw response:"+ response);
		return response;
	}
	
}
