package com.maruf.digitalsignature.crypto;

import org.springframework.stereotype.Component;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.Certificate;

@Component
public class GenSig {
    public byte[] create(String payload) {
        System.out.println("creating signature for: " + payload);
        byte[] realSig = null;
        /* Generate a signature */
        try {
            /* Read private key from JKS */
            KeyStore ks = KeyStore.getInstance("JKS");
            FileInputStream ksfis = new FileInputStream("apiProvider.jks");
            BufferedInputStream ksbufin = new BufferedInputStream(ksfis);
            char [] ksPwd = "changeme".toCharArray();
            ks.load(ksbufin, ksPwd);
            PrivateKey privateKey = (PrivateKey) ks.getKey("signature", ksPwd);
            Certificate cert = ks.getCertificate("signature");

            /* Create a Signature object and initialize it with the private key */
            Signature dsa = Signature.getInstance("SHA256withDSA", "SUN");
            dsa.initSign(privateKey);

            /* generate a signature */
            byte[] b = payload.getBytes(StandardCharsets.UTF_8); // Java 7+ onl`
            dsa.update(b);
            realSig = dsa.sign();

            System.out.println("Signature created=" + realSig.toString());
        } catch (Exception e) {
            System.err.println("Caught exception " + e.toString());
        }
        return realSig;
    }

    public static void verify(String payload, byte[] sigToVerify) {
        /* Verify a signature */
        try {
            /* import encoded public key from JKS */
            KeyStore ks = KeyStore.getInstance("JKS");
            FileInputStream ksfis = new FileInputStream("apiConsumer.jks");
            BufferedInputStream ksbufin = new BufferedInputStream(ksfis);
            char [] ksPwd = "changeme".toCharArray();
            ks.load(ksbufin, ksPwd);
            Certificate cert = ks.getCertificate("signature");
            PublicKey publicKey = cert.getPublicKey();

            /* create a Signature object and initialize it with the public key */
            Signature sig = Signature.getInstance("SHA256withDSA", "SUN");
            sig.initVerify(publicKey);

            byte[] b = payload.getBytes(StandardCharsets.UTF_8); // Java 7+ onl`
            sig.update(b);

            boolean verifies = sig.verify(sigToVerify);

            System.out.println("signature verifies: " + verifies);

        } catch (Exception e) {
            System.err.println("Caught exception " + e.toString());
        }
    }
}