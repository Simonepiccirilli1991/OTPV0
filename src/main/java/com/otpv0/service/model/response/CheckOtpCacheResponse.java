package com.otpv0.service.model.response;

import com.otpv0.service.model.request.OtpCacheRequest;

public class CheckOtpCacheResponse extends OtpCacheRequest{

	private Boolean isPresent;
	private String msg;
	
	public Boolean getIsPresent() {
		return isPresent;
	}
	public void setIsPresent(Boolean isPresent) {
		this.isPresent = isPresent;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
