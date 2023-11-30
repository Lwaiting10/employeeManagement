package com.iweb.Util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日志打印
 *
 * @author Liu Xiong
 * @date 30/11/2023 下午12:44
 */
public class Log {
    private final static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void log(String message) {
        try (FileWriter fw = new FileWriter("./server/log.txt", true);
             PrintWriter pw = new PrintWriter(fw, true);
        ) {
            pw.println(SDF.format(new Date()) + " : " + message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
