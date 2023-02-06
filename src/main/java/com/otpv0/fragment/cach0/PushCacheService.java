package com.otpv0.fragment.cach0;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otpv0.model.PushDto;
import com.otpv0.service.model.response.BaseCacheResponse;
import com.otpv0.service.model.response.PushChResponse;

@Service
public class PushCacheService {

	@Autowired
	CachPushClient push;
	
	//insert push cache
	public Boolean insertCachPush(PushDto dto) {
		
		BaseCacheResponse cachResp = push.insertPushCache(dto);
		if(!cachResp.getInsert()) {
			return false;
		}
		else 
			return true;
	}
	
	//get push cache
	public PushDto getPushDto(String bt) {
		
		PushDto response = null;
		PushChResponse iResp = null;
		
		try {
			iResp = push.getPushCache(bt);
			// ritorno dto del cazzo a nullo , gestico nel service principale e faccio lanciare eccezzione a orchestratore
			if(iResp.getNoFound())
				return response;
			
			response = respToDto(iResp);
		}catch(Exception e) {
			
			return response;
		}
		
		return response;
	}
	
	//update status cache
	public void updatePushStatus(PushDto request) {
		
		 push.updatePushCache(request);
		
	}
	
	private PushDto respToDto(PushChResponse request) {
		
		PushDto response = new PushDto();
		response.setBandaId(request.getBandaId());
		response.setBt(request.getBt());
		response.setStatus(request.getStatus());
		response.setTime(request.getTime());
		
		return response;
	}
}
