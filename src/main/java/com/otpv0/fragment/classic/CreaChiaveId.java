package com.otpv0.fragment.classic;

import static java.time.ZoneId.systemDefault;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.otpv0.util.ActConstants;

@Service
public class CreaChiaveId {
	
	/* ==== PROPERTIES AND BEANS ========================================================================================= */
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
	private static final String DEFAULT_ABI = "99999";
	
	public static ChiaveIdResponse crea(String bt, String abi, String prof) {
		
		LocalDateTime timestampDate = LocalDateTime.now();
		String timestampFormatted = timestampDate.format(DATE_FORMATTER);
		String timestamp = String.valueOf(timestampDate.atZone(systemDefault()).toInstant().toEpochMilli());
		
		String chiave = (ActConstants.ProfMatch.FIL.equals(prof) ? DEFAULT_ABI : abi)  + bt + timestampFormatted;
		
		return new ChiaveIdResponse(chiave, timestamp,timestampDate);
	}
}
