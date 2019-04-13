package com.bingdeng.sso.utils;

import org.apache.log4j.Logger;

import java.security.MessageDigest;

/**
 * Created by bingdeng on 2017/8/31.
 */
public class MD5Util {
    private static Logger log = Logger.getLogger(MD5Util.class);

    private static final String ecrypt = "bingdengfsq520";

    public static String encryptMD5(String pwd){
        try {
            //初始化md5
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update((pwd+ecrypt).getBytes("utf-8"));
            byte b[] = md.digest();
            int i;
            String buf = "";
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf += ("0");
                buf +=(Integer.toHexString(i));
            }
            String result = buf.toString();//32位
//            String result = buf.substring(8,24);//16位
            return result;
        }catch (Exception e){
            log.error("MD5加密失败："+e.getMessage());
        }
        return null;
    }


    public static void main(String[] args) {
        System.out.println(encryptMD5("830520"));
    }


}
