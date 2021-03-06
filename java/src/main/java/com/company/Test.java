package com.company;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author: yansu
 * @date: 2020/9/2
 */
public class Test {
    private static final String encryptionKey           = "aeugusertouchsms";
    private static final String characterEncoding       = "UTF-8";
    private static final String cipherTransformation    = "AES/CBC/PKCS5PADDING";
    private static final String aesEncryptionAlgorithem = "AES";
    /**
     * Method For Get encryptedText and Decrypted provided String
     * @param encryptedText
     * @return decryptedText
     */
    public static String decrypt(String encryptedText) {
        String decryptedText = "";
        try {
            Cipher cipher = Cipher.getInstance(cipherTransformation);
            byte[] key = encryptionKey.getBytes(characterEncoding);
            SecretKeySpec secretKey = new SecretKeySpec(key, aesEncryptionAlgorithem);
            IvParameterSpec ivparameterspec = new IvParameterSpec(key);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivparameterspec);
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] cipherText = decoder.decode(encryptedText.getBytes("UTF8"));
            decryptedText = new String(cipher.doFinal(cipherText), "UTF-8");

        } catch (Exception E) {
            System.err.println("decrypt Exception : "+E.getMessage());
        }
        return decryptedText;
    }

    public static void main(String[] args) {
        try {
            String surl = "http://www.aliexpress.ru";
            URL url = new URL(surl);
            URLConnection rulConnection   = url.openConnection();
            HttpURLConnection httpUrlConnection  =  (HttpURLConnection) rulConnection;
            httpUrlConnection.setConnectTimeout(300000);
            httpUrlConnection.setReadTimeout(300000);
            httpUrlConnection.connect();
            String code = Integer.toString(httpUrlConnection.getResponseCode());
            String message = httpUrlConnection.getResponseMessage();
            System.out.println("getResponseCode code ="+ code);
            System.out.println("getResponseMessage message ="+ message);
            if(!code.startsWith("2")){
                throw new Exception("ResponseCode is not begin with 2,code="+code);
            }
            System.out.println("连接"+surl+"正常");
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
