package com.otpv0.service.model.request;

public class PushRequest {

	private String bt;
	private String bancaId;
	
	public String getBt() {
		return bt;
	}
	public void setBt(String bt) {
		this.bt = bt;
	}
	public String getBancaId() {
		return bancaId;
	}
	public void setBancaId(String bancaId) {
		this.bancaId = bancaId;
	}
	@Override
	public String toString() {
		return "PushRequest [bt=" + bt + ", bancaId=" + bancaId + "]";
	}
	
	
}
