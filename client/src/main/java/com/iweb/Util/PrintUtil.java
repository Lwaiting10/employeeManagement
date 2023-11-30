package com.iweb.Util;

/**
 * @author Liu Xiong
 * @date 26/11/2023 下午2:39
 */
public class PrintUtil {
    public static void log(String message) {
        if (message.isEmpty()) {
            System.out.println();
            return;
        }
        for (int i = 0; i < message.length(); i++) {
            System.out.print(message.charAt(i));
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println();
    }
}
