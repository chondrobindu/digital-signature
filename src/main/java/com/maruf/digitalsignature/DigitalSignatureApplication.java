package com.maruf.digitalsignature;

import java.io.*;

import com.maruf.digitalsignature.crypto.GenSig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;


@SpringBootApplication
public class DigitalSignatureApplication {

	@Autowired
	private GenSig genSig;

	public static void main(String[] args) {
		SpringApplication.run(DigitalSignatureApplication.class, args);
		System.out.println("Digital signature!");

	}
	@PostConstruct
	public void init() {
		System.out.println("Inside init().");
		String payLoad = "{id=\"12345\", value=\"Hello\"}";
		genSig.create(payLoad);
		genSig.verify(payLoad);
		System.out.println("Leaving init().");
	}

}
