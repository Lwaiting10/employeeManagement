package com.iweb.Util;

import java.util.Random;

/**
 * @author Liu Xiong
 * @date 26/11/2023 下午2:19
 */
public class VerificationCodeUtil {
    private final static String STRING_POOL = "qwertyuiopasdfghjklzxcvbnm1234567890QWERTYUIOPASDFGHJKLZXCVBNM";
    private final static int LENGTH = 4;

    public static String getVerificationCode() {
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < LENGTH; i++) {
            sb.append(STRING_POOL.charAt(r.nextInt(STRING_POOL.length())));
        }
        return sb.toString();
    }
}
