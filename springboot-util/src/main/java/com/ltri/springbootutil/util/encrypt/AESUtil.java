package com.ltri.springbootutil.util.encrypt;


import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;

/**
 * @author ltri
 * AES可逆加密
 * AES-128-CBC加密模式
 */
public class AESUtil {

    /**
     * 默认密钥，可以自定义
     */
    private static final String KEY = "123456";
    /**
     * 向量密钥，可以自定义，必须128位(16字节)
     */
    private static final String PARAM_KEY = "1234561234567890";
    /**
     * 转型形式
     */
    private static final String CIPHER_KEY = "AES/CBC/PKCS5Padding";
    /**
     * 编码
     */
    private static final String CHARSET = "utf-8";
    /**
     * 算法名
     */
    private static final String MODE_AES = "AES";

    /**
     * 算法
     */
    private static final String SIGN_ALGORITHMS = "SHA1PRNG";

    /**
     * 加密
     *
     * @param content 内容
     * @return
     */
    public static String encrypt(String content) {
        return encrypt(content, KEY);
    }

    /**
     * 加密
     *
     * @param content 内容
     * @param key 密钥
     * @return
     */
    public static String encrypt(String content, String key) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_KEY);//创建加密Cipher类实例
            KeyGenerator kgen = KeyGenerator.getInstance(MODE_AES);//AES加密密钥生成器
            SecureRandom random = SecureRandom.getInstance(SIGN_ALGORITHMS);
            random.setSeed(key.getBytes(CHARSET));
            kgen.init(128, random);//生成密钥128位(16字节)
            SecretKey secretKey = kgen.generateKey();//生成密钥
            IvParameterSpec iv = new IvParameterSpec(PARAM_KEY.getBytes(CHARSET));//向量iv
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);//加密初始化
            byte[] encrypted = cipher.doFinal(content.getBytes(CHARSET));//完成加密操作
            return Base64.encodeBase64String(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解密
     *
     * @param content 内容
     * @return
     */
    public static String decrypt(String content) {
        return decrypt(content, KEY);
    }

    /**
     * 解密
     *
     * @param content 内容
     * @param key 密钥
     * @return
     */
    public static String decrypt(String content, String key) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_KEY);//创建加密Cipher类实例
            KeyGenerator kgen = KeyGenerator.getInstance(MODE_AES);//AES加密密钥生成器
            SecureRandom random = SecureRandom.getInstance(SIGN_ALGORITHMS);
            random.setSeed(key.getBytes(CHARSET));
            kgen.init(128, random);//生成密钥128位(16字节)
            SecretKey secretKey = kgen.generateKey();//生成密钥
            IvParameterSpec iv = new IvParameterSpec(PARAM_KEY.getBytes(CHARSET));//向量iv
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);//解密初始化
            byte[] original = cipher.doFinal(Base64.decodeBase64(content));//完成解密操作
            return new String(original);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void main(String[] args) {
        String encrypt = encrypt("测试内容");
        System.out.println("encrypt = " + encrypt);
        String decrypt = decrypt(encrypt);
        System.out.println("decrypt = " + decrypt);
    }
}