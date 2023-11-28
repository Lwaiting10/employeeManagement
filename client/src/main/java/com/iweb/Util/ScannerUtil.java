package com.iweb.Util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import static com.iweb.Util.PrintUtil.log;

/**
 * @author Liu Xiong
 * @date 27/11/2023 下午9:44
 */
public class ScannerUtil {
    private final static Scanner SCANNER = new Scanner(System.in);
    private final static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");


    public static Date getDate() {
        while (true) {
            try {
                return SDF.parse(SCANNER.nextLine());
            } catch (ParseException e) {
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
                System.out.println("输入有误,必须为纯数字,请重新输入:");
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
            if ("0".equals(inputType) || "1".equals(inputType)) {
                return inputType;
            }
            log("输入有误,重新输入(0-普通用户/1-管理员):");
        }
    }

    public static void main(String[] args) {
        System.out.println(getBigDecimal());
    }

    public static int getId() {
        while (true) {
            try {
                log("员工id(纯数字):");
                String inputIdStr = SCANNER.nextLine();
                if ("".equals(inputIdStr)) {
                    log("不能为空！");
                    continue;
                }
                return Integer.parseInt(inputIdStr);
            } catch (Exception e) {
                System.out.println("输入有误,员工id必须为纯数字,请重新输入:");
            }
        }
    }

    public static String getName() {
        while (true) {
            log("员工姓名:");
            String inputName = SCANNER.nextLine();
            if ("".equals(inputName)) {
                log("不能为空！");
                continue;
            }
            return inputName;
        }
    }

    public static String getGender() {
        while (true) {
            log("员工性别(男/女):");
            String inputGender = SCANNER.nextLine();
            if ("男".equals(inputGender) || "女".equals(inputGender)) {
                return inputGender;
            }
            log("输入有误,重新输入(男/女):");
        }
    }

    public static String getDepartment() {
        while (true) {
            log("员工部门:");
            String inputDepartment = SCANNER.nextLine();
            if ("".equals(inputDepartment)) {
                log("不能为空！");
                continue;
            }
            return inputDepartment;
        }
    }

    public static Date getHireDate() {
        while (true) {
            log("员工入职日期(yyyy-MM-dd):");
            try {
                return SDF.parse(SCANNER.nextLine());
            } catch (ParseException e) {
                log("格式错误！重新输入(yyyy-MM-dd):");
            }
        }
    }

    public static Date getBirthday() {
        while (true) {
            log("员工出生日期(yyyy-MM-dd):");
            try {
                return SDF.parse(SCANNER.nextLine());
            } catch (ParseException e) {
                log("格式错误！重新输入(yyyy-MM-dd):");
            }
        }
    }

    public static String getPassword() {
        while (true) {
            String inputPassword = SCANNER.nextLine();
            if ("".equals(inputPassword)) {
                log("不能为空！");
                continue;
            }
            return inputPassword;
        }
    }
}