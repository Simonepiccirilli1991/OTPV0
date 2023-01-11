package com.cach0.service;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;

import com.otpv0.Otpv01Application;
import com.otpv0.fragment.cach0.Cach0Client;
import com.otpv0.service.CheckOtpService;
import com.otpv0.service.GenerateOtpService;
import com.otpv0.service.model.request.CheckOtpRequest;
import com.otpv0.service.model.request.GenerateOtpRequest;
import com.otpv0.service.model.request.OtpCacheRequest;
import com.otpv0.service.model.response.BaseCacheResponse;
import com.otpv0.service.model.response.CheckOtpCacheResponse;
import com.otpv0.service.model.response.CheckOtpResponse;
import com.otpv0.service.model.response.GenerateOtpResponse;

@SpringBootTest(classes = Otpv01Application.class)
public class OtpServiceTest {

	@Autowired
	CheckOtpService checkService;
	@Autowired
	GenerateOtpService generateService;
	@MockBean
	Cach0Client cacheClient;
	@MockBean
	JavaMailSender javaMailSender;
	
	
	@Test
	public void generateTestOK() {
		
		GenerateOtpRequest request = new GenerateOtpRequest();
		request.setAbi("abiProva");
		request.setBt("btProva");
		request.setEmail("emailProva@gmail.com");
		request.setProf("Web");
		request.setTipoEvento(0);
		
		BaseCacheResponse cacheDto = new BaseCacheResponse();
		cacheDto.setInsert(true);
		cacheDto.setMsg("t'appo!");
		
		when(cacheClient.insertCache(any(OtpCacheRequest.class))).thenReturn(cacheDto);
		
		
		GenerateOtpResponse response = generateService.generateOtpNoTransaction(request, null);
		
		assertThat(response.getMsg()).isEqualTo("otp sended");
		assertThat(response.getOtpSend()).isEqualTo(true);
	}
	
	
	@Test
	public void checkTestOK() {
		
		CheckOtpRequest request = new CheckOtpRequest();
		request.setBt("btProva");
		request.setOtp("1111");
		request.setProfile("Web");
		request.setTransactionId("trxId");
		
		CheckOtpCacheResponse cacheResp = new CheckOtpCacheResponse();
		cacheResp.setBt("btProva");
		cacheResp.setIsPresent(true);
		cacheResp.setMsg("t'appo");
		cacheResp.setOtp("1111");
		cacheResp.setProfilo("Web");
		cacheResp.setTransactionId("trxId");
		cacheResp.setTimestamp("timeStamp");
		cacheResp.setTimestampDate(LocalDateTime.now());
		
		when(cacheClient.getOtpCache(any())).thenReturn(cacheResp);
		
		CheckOtpResponse response = checkService.checkOtp(request);
		
		assertThat(response.getAutenticationSucc()).isEqualTo(true);
		assertThat(response.getMsg()).isEqualTo("Autenticazione effettuata");
	}
	
	@Test
	public void checkTestKO() {
		
		CheckOtpRequest request = new CheckOtpRequest();
		request.setBt("btProva");
		request.setOtp("1111");
		request.setProfile("Web");
		request.setTransactionId("trxId");
		
		CheckOtpCacheResponse cacheResp = new CheckOtpCacheResponse();
		cacheResp.setBt("btProva");
		cacheResp.setIsPresent(true);
		cacheResp.setMsg("t'appo");
		cacheResp.setOtp("11115");
		cacheResp.setProfilo("Web");
		cacheResp.setTransactionId("trxId");
		cacheResp.setTimestamp("timeStamp");
		cacheResp.setTimestampDate(LocalDateTime.now());
		
		when(cacheClient.getOtpCache(any())).thenReturn(cacheResp);
		
		CheckOtpResponse response = checkService.checkOtp(request);
		
		assertThat(response.getAutenticationSucc()).isEqualTo(false);
		assertThat(response.getMsg()).isEqualTo("Error");
		assertThat(response.getErrorMsg()).isEqualTo("Otp errato");
	}
}
