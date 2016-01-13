package com.madding.shared.restful.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 * 类Md5.java的实现描述：MD5工具类
 * 
 * @author madding.lip May 8, 2012 1:59:09 PM
 */
public class MD5Util {

    private static final String ALGORITHM       = "MD5";
    private static final String DEFAULT_CHARSET = "UTF-8";

    // 用来将字节转换成 16 进制表示的字符
    private static char         hexDigits[]     = { '0', '1', '2', '3', '4',
                                                    '5', '6', '7', '8', '9',
                                                    'a', 'b', 'c', 'd', 'e',
                                                    'f' };

    public static String md5(byte[] source) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
            messageDigest.update(source);
            byte[] digest = messageDigest.digest();

            int j = digest.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = digest[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String md5(String text) {
        return md5(text, DEFAULT_CHARSET);
    }

    public static String md5(String str, String charset) {
        try {
            return md5(str.getBytes(charset));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
