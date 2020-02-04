package com.miaosha.day1.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {
    private static final String salt="liaozeyu";

    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    public static String inputPassFormPass(String inputPass){
        String str=salt.charAt(0)+salt.charAt(2)+inputPass+salt.charAt(5)+salt.charAt(4);
        return md5(str);
    }
    public static String formPassToDBPass(String formPass,String salt){
        String str=salt+formPass;
        return md5(str);
    }

    /**
     * 2次MD5加密数据
     * @param inputpass
     * @param saltDB
     * @return
     */
    public static String inputPassToDBPass(String inputpass,String saltDB){
        String formPass=inputPassFormPass(inputpass);
        String dbPass=formPassToDBPass(formPass,saltDB);
        return dbPass;
    }
    public static void main(String[] args) {
        System.out.println(inputPassFormPass("123456"));
    }
}
