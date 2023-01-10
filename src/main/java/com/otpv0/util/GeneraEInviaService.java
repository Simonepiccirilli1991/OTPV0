package com.otpv0.util;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class GeneraEInviaService {

	
	@Autowired
	private JavaMailSender javaMailSender;
	
	
	public String invia(String otp, String mail) {

		// creo codice otp
		
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(mail);

		msg.setSubject("Codice Sicurezza the-Simone");
		msg.setText("Hello this is your OTP: "+otp);
		try {
			javaMailSender.send(msg);
		}
		catch (Exception e) {
			return "Errore durante invio ots via mail";
		}

		return "OTS inviato con successo";
        
	}
	
	
	
	// usati per generare otp
	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static SecureRandom rnd = new SecureRandom();

	public String randomString(int len){
		StringBuilder sb = new StringBuilder(len);
		for(int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}
}
