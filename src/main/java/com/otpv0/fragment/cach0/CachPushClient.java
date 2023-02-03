package com.otpv0.fragment.cach0;

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
	private String iwdbUri;
	WebClient webClient = WebClient.create(iwdbUri);

	// inserisco push in cache
	public BaseCacheResponse insertOtpCache(PushDto request) {

		BaseCacheResponse iResp = new BaseCacheResponse();
		Mono<BaseCacheResponse> response = null;
		
		String uri = UriComponentsBuilder.fromHttpUrl(iwdbUri + "/push/insert").toUriString();
		
		try {
			response = webClient.post()
					.uri(uri)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.body(Mono.just(request), PushDto.class)
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
	
	//update push cache
	public BaseCacheResponse updateOtpCache(PushDto request) {

		BaseCacheResponse iResp = new BaseCacheResponse();
		Mono<BaseCacheResponse> response = null;
		
		String uri = UriComponentsBuilder.fromHttpUrl(iwdbUri + "/push/update").toUriString();
		
		try {
			response = webClient.put()
					.uri(uri)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.body(Mono.just(request), PushDto.class)
					.retrieve()
					.bodyToMono(BaseCacheResponse.class);
		}
		catch(Exception e) {
			//TODO implementare eccezzione anche qui per 2 casi
			iResp.setMsg(e.getMessage());
			iResp.setInsert(false);
			return iResp;
		}
		iResp = response.block();
		return iResp;
	}
	
	//get push cache
	public PushChResponse getOtpCache(String bt) {

		PushChResponse iResp = new PushChResponse();
		Mono<PushChResponse> response = null;
		
		String uri = UriComponentsBuilder.fromHttpUrl(iwdbUri + "/push/get"+bt).toUriString();
		
		try {
			response = webClient.get()
					.uri(uri)
					.accept(MediaType.APPLICATION_JSON)
					.retrieve()
					.bodyToMono(PushChResponse.class);
		}
		catch(Exception e) {
			
			iResp.setMsg(e.getMessage());
			iResp.setNoFound(true);
			return iResp;
		}
		iResp = response.block();
		return iResp;
	}
	
}
