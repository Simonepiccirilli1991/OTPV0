package com.otpv0.service.model.response;

public class PushResponse {

	private Boolean acepted;
	private Boolean sended;
	private String status;
	private String msg;
	private Boolean noFound;
	
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
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Boolean getNoFound() {
		return noFound;
	}
	public void setNoFound(Boolean noFound) {
		this.noFound = noFound;
	}
	
	@Override
	public String toString() {
		return "PushResponse [acepted=" + acepted + ", sended=" + sended + ", status=" + status + ", msg=" + msg
				+ ", noFound=" + noFound + "]";
	}
	
	
}
