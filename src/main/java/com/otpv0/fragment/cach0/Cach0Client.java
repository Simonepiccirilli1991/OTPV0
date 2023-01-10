package com.otpv0.fragment.cach0;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.otpv0.service.model.request.OtpCacheRequest;
import com.otpv0.service.model.response.BaseCacheResponse;

import reactor.core.publisher.Mono;

@Component
public class Cach0Client {

	private String iwdbUri = "http://localhost:8086";
	WebClient webClient = WebClient.create(iwdbUri);

	// inserisco otp in cache
	public BaseCacheResponse insertCache(OtpCacheRequest request) {

		BaseCacheResponse iResp = new BaseCacheResponse();
		Mono<BaseCacheResponse> response = null;
		
		try {
			response = webClient.post()
					.uri("otp/insert")
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.body(Mono.just(request), OtpCacheRequest.class)
					.retrieve()
					.bodyToMono(BaseCacheResponse.class);
		}
		catch(Exception e) {
			
			iResp.setMsg(e.getMessage());
			iResp.setInsert(false);
			return iResp;
		}
		iResp = response.block();
		
		
		return iResp;
	}
}
