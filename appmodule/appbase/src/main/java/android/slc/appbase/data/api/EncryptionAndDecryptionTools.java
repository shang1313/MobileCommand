package android.slc.appbase.data.api;

import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.GsonUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionAndDecryptionTools {
    public final static String E_KEY = "xf2021#!11";
    public final static String APP_ID = "10000000";

    /**
     * 加密对象
     *
     * @param parameter
     * @return
     */
    public static Map<String, String> makeDataWithToken(Object parameter) {
        return makeDataWithToken(GsonUtils.toJson(parameter), APP_ID, E_KEY);
    }

    /**
     * 加密
     *
     * @param jsonData
     * @param appId
     * @param e_key
     * @return
     */
    @Deprecated
    public static String makeDataWithToken1(String jsonData, String appId, String e_key) {
        return GsonUtils.toJson(makeDataWithToken(jsonData, appId, e_key));
    }

    /**
     * 加密数据
     *
     * @param jsonData
     * @param appId
     * @param e_key
     * @return
     */
    public static Map<String, String> makeDataWithToken(String jsonData, String appId, String e_key) {
        String salt = makeSixChar();
        String data = "";
        try {
            data = aesEncrypt(salt + e_key, jsonData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String time = System.currentTimeMillis() + "";
        String token = makeToken(salt + appId + time + data + e_key);
        token = token.toLowerCase();
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("salt", salt);
        map.put("time", time);
        map.put("appId", appId);
        map.put("data", data);
        map.put("version", "1.0");
        return map;
    }

    public static Map<String, String> getPlaintext(Object parameter) {
        Map<String, String> map = new HashMap<>();
        map.put("token", E_KEY);
        map.put("salt", makeSixChar());
        map.put("time", System.currentTimeMillis() + "");
        map.put("appId", APP_ID);
        map.put("data", GsonUtils.toJson(parameter));
        map.put("version", "1.0");
        return map;
    }

    /**
     * 创建token
     *
     * @param param
     * @return
     */
    public static String makeToken(String param) {
        return EncryptUtils.encryptMD5ToString(param);
    }

    /**
     * aes加密
     *
     * @param key16
     * @param text
     * @return
     * @throws Exception
     */
    public static String aesEncrypt(String key16, String text)
            throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(key16.getBytes());
        SecretKeySpec key = new SecretKeySpec(key16.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(1, key, zeroIv);
        byte[] encryptedData = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
        return new String(EncodeUtils.base64Encode(encryptedData), StandardCharsets.UTF_8);
    }

    /**
     * aes解密
     *
     * @param text
     * @param key16
     * @return
     * @throws Exception
     */
    public static String aesDecrypt(String text, String key16) throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(key16.getBytes());
        SecretKeySpec key = new SecretKeySpec(key16.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(2, key, zeroIv);
        return new String(cipher.doFinal(EncodeUtils.base64Decode(text)), StandardCharsets.UTF_8);
    }

    public static String makeSixChar() {
        return randomString(6);
    }

    public static String randomString(int length) {
        if (length <= 0) {
            length = 10;
        } else if (length > 50) {
            length = 50;
        }
        StringBuilder s = new StringBuilder(50);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写还是小写
            s.append((char) (choice + random.nextInt(26)));
        }
        return s.toString();
    }
}
