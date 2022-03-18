package com.caremind.test.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256Encrypt {

    public static String encryptSHA256(String text) {
        MessageDigest md = null;
        String result = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes());

            StringBuilder builder = new StringBuilder();
            for (byte b : text.getBytes()) {
                builder.append(String.format("%02x", b));
            }
            result = builder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA256 암호화 에러");
        }

        return result;
    }
}
