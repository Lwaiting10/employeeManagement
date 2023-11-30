package com.iweb.Util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Scanner;

import static com.iweb.Util.PrintUtil.log;

/**
 * @author Liu Xiong
 * @date 27/11/2023 下午9:44
 */
public class ScannerUtil {
    private final static Scanner SCANNER = new Scanner(System.in);

    public static Date getDate() {
        // 创建DateTimeFormatter对象
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        while (true) {
            try {
                String dateString = SCANNER.nextLine();
                // 将字符串解析为LocalDate对象
                LocalDate localDate = LocalDate.parse(dateString, formatter);
                if (localDate.getDayOfMonth() >= 28) {
                    // 将转换后的日期与输入的日期进行匹配，自动纠正检测
                    if (!String.valueOf(localDate.getDayOfMonth()).equals(dateString.substring(dateString.length() - 2))) {
                        log("格式错误！重新输入(yyyy-MM-dd):");
                        continue;
                    }
                }
                return sdf.parse(localDate.toString());
            } catch (DateTimeParseException | ParseException e) {
                log("格式错误！重新输入(yyyy-MM-dd):");
            }
        }

    }

    public static int getInt() {
        while (true) {
            try {
                String intStr = SCANNER.nextLine();
                if ("".equals(intStr)) {
                    log("不能为空！");
                    continue;
                }
                return Integer.parseInt(intStr);
            } catch (Exception e) {
                System.out.println("输入有误,请重新输入:");
            }
        }
    }

    public static String getString() {
        while (true) {
            String s = SCANNER.nextLine();
            if ("".equals(s)) {
                log("不能为空！");
                continue;
            }
            return s;
        }
    }

    public static BigDecimal getBigDecimal() {
        while (true) {
            String s = SCANNER.nextLine();
            if ("".equals(s)) {
                log("不能为空！");
                continue;
            }
            try {
                return new BigDecimal(s);
            } catch (NumberFormatException e) {
                log("格式有误！请输入数字:");
            }

        }
    }

    public static String getType() {
        while (true) {
            String inputType = SCANNER.nextLine();
            if ("0".equals(inputType)) {
                return inputType;
            }
            if ("1".equals(inputType)) {
                log("暂时关闭对管理员的相关操作！请重新输入:");
                continue;
            }
            log("输入有误,重新输入(0-普通用户/1-管理员):");
        }
    }

    public static String getGender() {
        while (true) {
            String inputGender = SCANNER.nextLine();
            if ("男".equals(inputGender) || "女".equals(inputGender)) {
                return inputGender;
            }
            log("输入有误,重新输入(男/女):");
        }
    }
}