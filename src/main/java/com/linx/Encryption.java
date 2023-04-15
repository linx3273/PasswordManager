package com.linx;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Encryption {
    /**
     *
     * @param str The string that is to be encoded
     * @return  SHA-256 encoded string
     * @throws NoSuchAlgorithmException In case the SHA256 algorithm does not exist
     */
    public String encrypt(String str) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] hash = messageDigest.digest(str.getBytes(StandardCharsets.UTF_8));

        StringBuilder stringBuilder = new StringBuilder();

        for (byte b: hash){
            stringBuilder.append(String.format("%02x", b));
        }
        return stringBuilder.toString();
    }
}
