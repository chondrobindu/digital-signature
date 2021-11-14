package com.maruf;

import com.maruf.Utils.DigitalSignatureUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;


@SpringBootApplication
public class DigitalSignatureApplication {

	@Autowired
	private DigitalSignatureUtil digitalSignatureUtil;

	public static void main(String[] args) {
		SpringApplication.run(DigitalSignatureApplication.class, args);
		System.out.println("Digital signature!");

	}
	@PostConstruct
	public void init() {
		System.out.println("Inside init().");
		String payLoad = "{id=\"12345\", value=\"Hello\"}";
		byte[] signature = digitalSignatureUtil.generate(payLoad);
		String payLoad1 = "{id=\"12345\", value=\"Hello\"}";
		digitalSignatureUtil.verify(payLoad1, signature);
		System.out.println("Leaving init().");
	}

}
