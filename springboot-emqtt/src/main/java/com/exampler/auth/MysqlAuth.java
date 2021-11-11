package com.exampler.auth;

import java.security.MessageDigest;
import java.util.Random;

/**
 * @version 1.0.0
 * @description: mysql认证
 * @author: guorf
 * @time: 2021/11/9 11:12
 */
public class MysqlAuth {

    public static void main(String[] args) {
        String clientId = "000001";
        String gatewayId = "00001";
        String username = "test";
//        String clearPassword = clientId+"@12345678";
//        String salt = encrypt16(clientId+gatewayId+username);
//        System.out.println(salt);
//        String password = encrypt32(salt+clearPassword);
//        System.out.println(password);
//        超管 admin
//        String clearPassword = "Lgy@2021";
//        String salt = encrypt16("PCADMIN");
//        System.out.println(salt);
//        String password = encrypt32(salt+clearPassword);
//        System.out.println(password);

//        8位随机密码
//        System.out.println(getRandomPassword(8));
    }

    /**
     * @description: 加密32位小写
     * @param encryptStr
     * @return: java.lang.String
     * @author: guorf
     * @time: 2021/11/9 15:20
     */
    public static String encrypt32(String encryptStr) {
        MessageDigest md5;
            try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5.digest(encryptStr.getBytes());
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16)
                    hexValue.append("0");
                hexValue.append(Integer.toHexString(val));
            }
            encryptStr = hexValue.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
		return encryptStr;
    }

    /**
     * @description: 加密-16位小写
     * @param encryptStr
     * @return: java.lang.String
     * @author: guorf
     * @time: 2021/11/9 15:21
     */
    public static String encrypt16(String encryptStr) {
        return encrypt32(encryptStr).substring(8, 24);
    }

    /**
     * @description: 检查是否包含大小字母及符号
     * @param len 
     * @return: java.lang.String
     * @author: guorf
     * @time: 2021/11/9 15:33
     */
    public static String getRandomPassword(int len) {
        String result = null;
        while(len>=6){
            result = makeRandomPassword(len);
            if (result.matches(".*[a-z]{1,}.*") && result.matches(".*[A-Z]{1,}.*") && result.matches(".*\\d{1,}.*") && result.matches(".*[~!@#$%^&*\\.?]{1,}.*")) {
                return result;
            }
            result = makeRandomPassword(len);
        }
        return "长度不得少于6位!";
    }
    /**
     * @description: 生成随机密码
     * @param len
     * @return: java.lang.String
     * @author: guorf
     * @time: 2021/11/9 15:27
     */
    public static String makeRandomPassword(int len){
        char charr[] = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890~!@#$%^&*.?".toCharArray();
//        char charr[] = "aA1bB2cC3dD4eE5fF6gG7hH8iI9jJ0kK~lL!mM@nN#oO$pP%qQ^rR&sS*tT.uU?vVwWxXyYzZ".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for (int x = 0; x < len; ++x) {
            sb.append(charr[r.nextInt(charr.length)]);
        }
        return sb.toString();
    }
    
}
