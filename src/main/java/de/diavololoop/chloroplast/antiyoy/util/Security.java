package de.diavololoop.chloroplast.antiyoy.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Chloroplast on 16.11.2017.
 */
public class Security {

    private final static MessageDigest MESSAGE_DIGEST;

    static {
        try {
            MESSAGE_DIGEST = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("There is no SHA-512 on this system (shouldn't happen)");
        }
    }

    public synchronized static String sha512(String str) {
        byte[] strBytes = str.getBytes(StandardCharsets.UTF_8);

        MESSAGE_DIGEST.reset();
        byte[] result = MESSAGE_DIGEST.digest(strBytes);

        return Hex.toHex(result);

    }

}
