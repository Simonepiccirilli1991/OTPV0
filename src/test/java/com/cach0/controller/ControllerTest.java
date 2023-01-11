package com.cach0.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.otpv0.Otpv01Application;
import com.otpv0.fragment.cach0.Cach0Client;
import com.otpv0.service.model.request.CheckOtpRequest;
import com.otpv0.service.model.request.GenerateOtpRequest;
import com.otpv0.service.model.request.OtpCacheRequest;
import com.otpv0.service.model.response.BaseCacheResponse;
import com.otpv0.service.model.response.CheckOtpCacheResponse;
import com.otpv0.service.model.response.CheckOtpResponse;
import com.otpv0.service.model.response.GenerateOtpResponse;

@AutoConfigureMockMvc
@SpringBootTest(classes = Otpv01Application.class)
public class ControllerTest {

	@Autowired
	MockMvc mvc;
	@MockBean
	Cach0Client cacheClient;
	@MockBean
	JavaMailSender javaMailSender;
	
	ObjectMapper mapper = new ObjectMapper();
	
	@Test
	public void generateOtpTestOK() throws Exception {
		
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
		
		String iResp = mvc.perform(post("/v1/generateOtp")
				.contentType("application/json")
				.content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();


		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		GenerateOtpResponse response = mapper.readValue(iResp, GenerateOtpResponse.class);
		
		assertThat(response.getMsg()).isEqualTo("otp sended");
		assertThat(response.getOtpSend()).isEqualTo(true);
		
	}
	
	@Test
	public void checkOtpTestOK() throws Exception {
		
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
		
		String iResp = mvc.perform(post("/v1/checkotp")
				.contentType("application/json")
				.content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		CheckOtpResponse response = mapper.readValue(iResp, CheckOtpResponse.class);
		
		assertThat(response.getAutenticationSucc()).isEqualTo(true);
		assertThat(response.getMsg()).isEqualTo("Autenticazione effettuata");
		
	}
	
	@Test
	public void checkOtpTestKO() throws Exception {
		
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
		
		String iResp = mvc.perform(post("/v1/checkotp")
				.contentType("application/json")
				.content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		CheckOtpResponse response = mapper.readValue(iResp, CheckOtpResponse.class);
		
		assertThat(response.getAutenticationSucc()).isEqualTo(false);
		assertThat(response.getMsg()).isEqualTo("Error");
		assertThat(response.getErrorMsg()).isEqualTo("Otp errato");
		
	}
}