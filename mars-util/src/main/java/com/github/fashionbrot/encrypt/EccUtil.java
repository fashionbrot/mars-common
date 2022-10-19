package com.github.fashionbrot.encrypt;


import com.github.fashionbrot.Base64Util;
import com.github.fashionbrot.ObjectUtil;

import javax.crypto.Cipher;
import javax.crypto.NullCipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fashionbrot
 */
public class EccUtil {

    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

//    public static void main(String[] args) {
//
//        KeyPair keyPair = getKeyPair(521,"1qazxsw2".getBytes());
//        System.out.println(Base64Util.encodeBase64String(keyPair.getPublic().getEncoded()));
//        System.out.println(Base64Util.encodeBase64String(keyPair.getPrivate().getEncoded()));
//
//        ECPublicKey publicKey = (ECPublicKey) keyPair.getPublic();
//        ECPrivateKey privateKey = (ECPrivateKey) keyPair.getPrivate();
//
//        Map<String,Object> map=new HashMap<>();
//        map.put("u",123);
//        LocalDateTime now = LocalDateTime.now();
//        map.put("iat",localDateTimeToSecond(now));
//        now = now.plusSeconds(45);
//        map.put("exp",localDateTimeToSecond(now));
//
//        String encrypt = encrypt(map.toString(),publicKey);
//        System.out.println(encrypt);
//
//        String decrypt = decrypt(encrypt, privateKey);
//        System.out.println(decrypt);
//    }

    public static long localDateTimeToSecond(LocalDateTime ldt){
        long timestamp = ldt.toInstant(ZoneOffset.of("+8")).getEpochSecond();
        return timestamp;
    }

    public static Map<String,String> getGenerateKey(int keySize,byte[] seed)  {
        KeyPair keyPair = getKeyPair(keySize,seed);
        Map<String,String> map = new HashMap<>();
        map.put("privateKey", Base64Util.encodeBase64String(keyPair.getPrivate().getEncoded()));
        map.put("publicKey", Base64Util.encodeBase64String(keyPair.getPublic().getEncoded()));
        return map;
    }


    /**
     * 生产公钥私钥
     * @param keySize 长度  192、239、256、224、384、521
     * @return  KeyPair
     */
    public static KeyPair getKeyPair(int keySize){
        return getKeyPair(keySize,null);
    }

    /**
     * 生产公钥私钥
     * @param keySize 长度  192、239、256、224、384、521
     * @return  KeyPair
     */
    public static KeyPair getKeyPair(int keySize,byte[] seed){
        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("EC","BC");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        if (ObjectUtil.isNotEmpty(seed)){
            keyPairGenerator.initialize(keySize, new SecureRandom(seed));
        }else{
            keyPairGenerator.initialize(keySize, new SecureRandom());
        }
        return keyPairGenerator.generateKeyPair();
    }


    /**
     * 加密
     * @param data      明文数组
     * @param publicKey 公钥字符串
     * @return  byte[]
     */
    public static byte[] encrypt(byte[] data, String publicKey) {
        try {
            byte[] keyBytes = Base64Util.decode(publicKey);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            ECPublicKey pubKey = (ECPublicKey) keyFactory.generatePublic(x509KeySpec);

            return encrypt(data,pubKey);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密
     * @param data      明文
     * @param publicKey 公钥
     * @return  String
     */
    public static String encrypt(String data, ECPublicKey publicKey) {
        if (ObjectUtil.isNotEmpty(data)){
            byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] encrypt = encrypt(bytes, publicKey);
            return Base64Util.encodeBase64String(encrypt);
        }
        return "";
    }

    /**
     * 加密
     * @param data      明文数组
     * @param publicKey 公钥
     * @return  byte[]
     */
    public static byte[] encrypt(byte[] data, ECPublicKey publicKey) {
        try {
            Cipher cipher = new NullCipher();
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(data);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密
     * @param data      明文
     * @param publicKey 公钥字符串
     * @return  String
     */
    public static String encrypt(String data, String publicKey) {
        if (ObjectUtil.isNotEmpty(data)){
            byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] encrypt = encrypt(bytes, publicKey);
            return Base64Util.encodeBase64String(encrypt);
        }
       return "";
    }





    /**
     * 解密
     * @param data       密文数组
     * @param privateKey 私钥
     * @return byte[]
     */
    public static byte[] decrypt(byte[] data, String privateKey)  {
        try {
            byte[] keyBytes = Base64Util.decode(privateKey);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            ECPrivateKey priKey = (ECPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);

            return decrypt(data,priKey);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 解密
     * @param data          数据数组
     * @param privateKey    私钥
     * @return byte[]
     */
    public static byte[] decrypt(byte[] data, ECPrivateKey privateKey)  {
        try {
            Cipher cipher = new NullCipher();
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(data);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 解密
     * @param data          数据
     * @param privateKey    私钥字符串
     * @return String
     */
    public static String decrypt(String data, String privateKey)  {
        if (ObjectUtil.isNotEmpty(data)) {
            byte[] inputByte = Base64Util.decode(data.getBytes());
            byte[] decrypt = decrypt(inputByte, privateKey);
            return new String(decrypt);
        }
        return "";
    }

    /**
     * 解密
     * @param data          数据
     * @param privateKey    私钥
     * @return String
     */
    public static String decrypt(String data, ECPrivateKey privateKey)  {
        if (ObjectUtil.isNotEmpty(data)) {
            byte[] inputByte = Base64Util.decode(data.getBytes());
            byte[] decrypt = decrypt(inputByte, privateKey);
            return new String(decrypt);
        }
        return "";
    }



}
