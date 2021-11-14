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
		String signingString = "";

		String requestTarget = "post /foo\n";
		String host = "example.org\n";
		String date = "Tue, 07 Jun 2014 20:51:35 GMT\n";
		String contentLength = "18\n";
		String payLoad = "{id=\"12345\", value=\"Hello\"}";
		signingString = requestTarget + host + date + contentLength + payLoad;

		byte[] signature = digitalSignatureUtil.generate(signingString);
		//String payLoad1 = "{id=\"12345\", value=\"Hello\"}";
		digitalSignatureUtil.verify(signingString, signature);
		System.out.println("Leaving init().");
	}

}
