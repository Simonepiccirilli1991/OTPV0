package com.otpv0.util;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

@Component
public class CommonUtils {

	public Boolean pushTimeValid(LocalDateTime timePush) {

		var timeNow = LocalDateTime.now();

		if(timeNow.isAfter(timePush.plusMinutes(1)))
			return false;
		else
			return true;
	}


	public void sendPush() {
		// fa un cazzo , fake push tanto per
	}
}
