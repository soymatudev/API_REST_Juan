package com.juan.apirestjuan.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;

import java.util.*;

public class AESUtil {
    private static final int KEY_LENGTH = 256;
    private static final int ITERATION_COUNT = 65536;
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static SecretKey secretKey = null;
    private static IvParameterSpec ivParameterSpec = null;

    public AESUtil() throws Exception {
        generateKey();
        generateIv();
    }

    public static void generateKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(KEY_LENGTH);
        secretKey = keyGenerator.generateKey();
        //return keyGenerator.generateKey();
    }

    public static void generateIv() throws Exception {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        ivParameterSpec = new IvParameterSpec(iv);
        //return new IvParameterSpec(iv);
    }

    public static String encrypt(String input, SecretKey secretKey, IvParameterSpec iv) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder().encodeToString(cipherText);
    }

    public static String decrypt(String cipherText, SecretKey key, IvParameterSpec iv) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
        return new String(plainText);
    }

    public static String getEncrypt(String input) throws Exception {
        return encrypt(input, secretKey, ivParameterSpec);
    }

    public static String getDecrypt(String cipherText) throws Exception {
        return decrypt(cipherText, secretKey, ivParameterSpec);
    }

}