package com.company;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Calendar;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
public class Crypto {
    private static final String encryptionKey           = "aeugusertouchsms";
    private static final String characterEncoding       = "UTF-8";
    private static final String cipherTransformation    = "AES/CBC/PKCS5PADDING";
    private static final String aesEncryptionAlgorithem = "AES";


    /**
     * Method for Encrypt Plain String Data
     * @param plainText
     * @return encryptedText
     */
    public static String encrypt(String plainText) {
        String encryptedText = "";
        try {
            Cipher cipher   = Cipher.getInstance(cipherTransformation);
            byte[] key      = encryptionKey.getBytes(characterEncoding);
            SecretKeySpec secretKey = new SecretKeySpec(key, aesEncryptionAlgorithem);
            IvParameterSpec ivparameterspec = new IvParameterSpec(key);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivparameterspec);
            byte[] cipherText = cipher.doFinal(plainText.getBytes("UTF8"));
            Base64.Encoder encoder = Base64.getEncoder();
            encryptedText = encoder.encodeToString(cipherText);

        } catch (Exception E) {
            System.err.println("Encrypt Exception : "+E.getMessage());
        }
        return encryptedText;
    }


    public static void main(String[] args) {
        String plainString = "555566666";
        System.out.println("Enter String : " + plainString);

        String encryptStr   = encrypt(plainString);
        String decryptStr  = new Test().decrypt(encryptStr);

        System.out.println("Plain   String  : "+plainString);
        System.out.println("Encrypt String  : "+encryptStr);
        System.out.println("Decrypt String  : "+decryptStr);

        TimeZone timeZone = TimeZone.getTimeZone("GMT-8:00");
        Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();
        String displayName = timeZone.getDisplayName();

        System.out.println(displayName);

    }
}