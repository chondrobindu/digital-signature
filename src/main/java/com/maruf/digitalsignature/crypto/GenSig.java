package com.maruf.digitalsignature.crypto;

import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;

@Component
public class GenSig {

    public void create(String payload) {

        System.out.println("creating signature for: " + payload);
        /* Generate a DSA signature */

        try {

            /* Generate a key pair */

            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");

            keyGen.initialize(1024, random);

            KeyPair pair = keyGen.generateKeyPair();
            PrivateKey priv = pair.getPrivate();
            PublicKey pub = pair.getPublic();


            /* Create a Signature object and initialize it with the private key */

            Signature dsa = Signature.getInstance("SHA1withDSA", "SUN");

            dsa.initSign(priv);


            /* Now that all the data to be signed has been read in,
                    generate a signature for it */

            //String payload = "payload that needs signature!";
            byte[] b = payload.getBytes(StandardCharsets.UTF_8); // Java 7+ onl`
            dsa.update(b);
            byte[] realSig = dsa.sign();

            System.out.println("Signature created=" + realSig.toString());
            /* Save the signature in a file * */
            FileOutputStream sigfos = new FileOutputStream("sig");
            sigfos.write(realSig);

            sigfos.close();


            /* Save the public key in a file
             * */
            byte[] key = pub.getEncoded();
            FileOutputStream keyfos = new FileOutputStream("suepk");
            keyfos.write(key);

            keyfos.close();

        } catch (Exception e) {
            System.err.println("Caught exception " + e.toString());
        }

    }

    public static void verify(String payload) {

        /* Verify a DSA signature */
        try {

            /* import encoded public key */

            FileInputStream keyfis = new FileInputStream("suepk");
            byte[] encKey = new byte[keyfis.available()];
            keyfis.read(encKey);

            keyfis.close();

            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);

            KeyFactory keyFactory = KeyFactory.getInstance("DSA", "SUN");
            PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);

            /* input the signature bytes */
            FileInputStream sigfis = new FileInputStream("sig");
            byte[] sigToVerify = new byte[sigfis.available()];
            sigfis.read(sigToVerify);

            sigfis.close();

            /* create a Signature object and initialize it with the public key */
            Signature sig = Signature.getInstance("SHA1withDSA", "SUN");
            sig.initVerify(pubKey);

            /* Update and verify the data

            FileInputStream datafis = new FileInputStream(args[2]);
            BufferedInputStream bufin = new BufferedInputStream(datafis);

            byte[] buffer = new byte[1024];
            int len;
            while (bufin.available() != 0) {
                len = bufin.read(buffer);
                sig.update(buffer, 0, len);
            }

            bufin.close();
            * */

            byte[] b = payload.getBytes(StandardCharsets.UTF_8); // Java 7+ onl`
            sig.update(b);



            boolean verifies = sig.verify(sigToVerify);

            System.out.println("signature verifies: " + verifies);


        } catch (Exception e) {
            System.err.println("Caught exception " + e.toString());
        }
    }
}