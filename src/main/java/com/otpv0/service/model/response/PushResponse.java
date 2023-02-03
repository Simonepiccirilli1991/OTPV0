package com.otpv0.service.model.response;

public class PushResponse extends BaseCacheResponse{

	private Boolean acepted;
	private Boolean sended;
	private String status;
	
	public Boolean getAcepted() {
		return acepted;
	}
	public void setAcepted(Boolean acepted) {
		this.acepted = acepted;
	}
	public Boolean getSended() {
		return sended;
	}
	public void setSended(Boolean sended) {
		this.sended = sended;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
