package com.otpv0.fragment.classic;

import java.time.LocalDateTime;

public class ChiaveIdResponse {

	private String chiaveId;
	private String timeStamp;
	private LocalDateTime timestampDate;
	
	public String getChiaveId() {
		return chiaveId;
	}
	public void setChiaveId(String chiaveId) {
		this.chiaveId = chiaveId;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	
	public LocalDateTime getTimestampDate() {
		return timestampDate;
	}
	public void setTimestampDate(LocalDateTime timestampDate) {
		this.timestampDate = timestampDate;
	}
	public ChiaveIdResponse(String chiaveId, String timeStamp,  LocalDateTime timestampDate) {
		super();
		this.chiaveId = chiaveId;
		this.timeStamp = timeStamp;
		this.timestampDate = timestampDate;
	}
	
	
}
