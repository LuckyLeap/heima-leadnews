package com.heima.utils.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static String DATE_FORMAT = "yyyy-MM-dd";

    public static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_TIME_STAMP_FORMATE = "yyyyMMddHHmmss";
    
    public static String DATE_FORMAT_CHINESE = "yyyy年M月d日";
    
    public static String DATE_TIME_FORMAT_CHINESE = "yyyy年M月d日 HH:mm:ss";
    
    /**
     * 获取当前日期
     */
    public static String getCurrentDate() {
        String dateStr;
        SimpleDateFormat df = new SimpleDateFormat(DateUtils.DATE_FORMAT);
        dateStr = df.format(new Date());
        return dateStr;
    }

    /**
     * 获取当前日期时间
     */
    public static String getCurrentDateTime() {
        String dateStr;
        SimpleDateFormat df = new SimpleDateFormat(DateUtils.DATE_TIME_FORMAT);
        dateStr = df.format(new Date());
        return dateStr;
    }

    /**
     * 获取当前日期时间
     */
    public static String getCurrentDateTime(String Dateformat) {
        String dateStr = null;
        SimpleDateFormat df = new SimpleDateFormat(Dateformat);
        dateStr = df.format(new Date());
        return dateStr;
    }

    public static String dateToDateTime(Date date) {
        String dateStr = null;
        SimpleDateFormat df = new SimpleDateFormat(DateUtils.DATE_TIME_FORMAT);
        dateStr = df.format(date);
        return dateStr;
    }

    /**
     * 将字符串日期转换为日期格式
     */
    public static Date stringToDate(String dateStr) {

        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        Date date;
        SimpleDateFormat df = new SimpleDateFormat(DateUtils.DATE_FORMAT);
        try {
            date = df.parse(dateStr);
        } catch (ParseException e) {
            date = DateUtils.stringToDate(dateStr, "yyyyMMdd");
        }
        return date;
    }

    /**
     * 将字符串日期转换为日期格式
     * 自定義格式
     */
    public static Date stringToDate(String dateStr, String dateformat) {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat(dateformat);
        try {
            date = df.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 将日期格式日期转换为字符串格式
     */
    public static String dateToString(Date date) {
        String dateStr;
        SimpleDateFormat df = new SimpleDateFormat(DateUtils.DATE_FORMAT);
        dateStr = df.format(date);
        return dateStr;
    }

    /**
     * 将日期格式日期转换为字符串格式 自定義格式
     */
    public static String dateToString(Date date, String dateformat) {
        String dateStr = null;
        SimpleDateFormat df = new SimpleDateFormat(dateformat);
        dateStr = df.format(date);
        return dateStr;
    }

    /**
     * 获取日期的DAY值
     */
    public static int getDayOfDate(Date date) {
        int d = 0;
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        d = cd.get(Calendar.DAY_OF_MONTH);
        return d;
    }

    /**
     * 获取日期的MONTH值
     */
    public static int getMonthOfDate(Date date) {
        int m = 0;
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        m = cd.get(Calendar.MONTH) + 1;
        return m;
    }

    /**
     * 获取日期的YEAR值
     */
    public static int getYearOfDate(Date date) {
        int y = 0;
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        y = cd.get(Calendar.YEAR);
        return y;
    }

    /**
     * 获取星期几
     */
    public static int getWeekOfDate(Date date) {
        int wd = 0;
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        wd = cd.get(Calendar.DAY_OF_WEEK) - 1;
        return wd;
    }

    /**
     * 获取输入日期的当月第一天
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.set(Calendar.DAY_OF_MONTH, 1);
        return cd.getTime();
    }

    /**
     * 获得输入日期的当月最后一天
     */
    public static Date getLastDayOfMonth(Date date) {
        return DateUtils.addDay(DateUtils.getFirstDayOfMonth(DateUtils.addMonth(date, 1)), -1);
    }

    /**
     * 判断是否是闰年
     * @param date 输入日期
     * @return 是true 否false
     */
    public static boolean isLeapYEAR(Date date) {

        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        int year = cd.get(Calendar.YEAR);

        return year % 4 == 0 && year % 100 != 0 | year % 400 == 0;
    }

    /**
     * 根据整型数表示的年月日，生成日期类型格式
     * @param year  年
     * @param month 月
     * @param day   日
     */
    public static Date getDateByYMD(int year, int month, int day) {
        Calendar cd = Calendar.getInstance();
        cd.set(year, month - 1, day);
        return cd.getTime();
    }

    /**
     * 获取年周期对应日
     */
    public static Date getYearCycleOfDate(Date date, int iyear) {
        return addYear(date, iyear);
    }

    /**
     * 获取月周期对应日
     */
    public static Date getMonthCycleOfDate(Date date, int i) {
        return addMonth(date, i);
    }

    /**
     * 计算 fromDate 到 toDate 相差多少年
     */
    public static int getYearByMinusDate(Date fromDate, Date toDate) {
        Calendar df = Calendar.getInstance();
        df.setTime(fromDate);

        Calendar dt = Calendar.getInstance();
        dt.setTime(toDate);

        return dt.get(Calendar.YEAR) - df.get(Calendar.YEAR);
    }

    /**
     * 计算 fromDate 到 toDate 相差多少个月
     */
    public static int getMonthByMinusDate(Date fromDate, Date toDate) {
        Calendar df = Calendar.getInstance();
        df.setTime(fromDate);

        Calendar dt = Calendar.getInstance();
        dt.setTime(toDate);

        return dt.get(Calendar.YEAR) * 12 + dt.get(Calendar.MONTH) -
                (df.get(Calendar.YEAR) * 12 + df.get(Calendar.MONTH));
    }

    /**
     * 计算 fromDate 到 toDate 相差多少天
     * @return 天数
     */
    public static long getDayByMinusDate(Object fromDate, Object toDate) {
        Date f = DateUtils.chgObject(fromDate);

        Date t = DateUtils.chgObject(toDate);

        long fd = f.getTime();
        long td = t.getTime();

        return (td - fd) / (24L * 60L * 60L * 1000L);
    }

    /**
     * 计算年龄
     */
    public static int calcAge(Date birthday, Date calcDate) {

        int cYear = DateUtils.getYearOfDate(calcDate);
        int cMonth = DateUtils.getMonthOfDate(calcDate);
        int cDay = DateUtils.getDayOfDate(calcDate);
        int bYear = DateUtils.getYearOfDate(birthday);
        int bMonth = DateUtils.getMonthOfDate(birthday);
        int bDay = DateUtils.getDayOfDate(birthday);

        if (cMonth > bMonth || (cMonth == bMonth && cDay > bDay)) {
            return cYear - bYear;
        } else {
            return cYear - 1 - bYear;
        }
    }

    /**
     * 从身份证中获取出生日期
     */
    public static String getBirthDayFromIDCard(String idno) {
        Calendar cd = Calendar.getInstance();
        if (idno.length() == 15) {
            cd.set(Calendar.YEAR, Integer.parseInt("19" + idno.substring(6, 8)));
            cd.set(Calendar.MONTH, Integer.parseInt(idno.substring(8, 10)) - 1);
            cd.set(Calendar.DAY_OF_MONTH, Integer.parseInt(idno.substring(10, 12)));
        } else if (idno.length() == 18) {
            cd.set(Calendar.YEAR, Integer.parseInt(idno.substring(6, 10)));
            cd.set(Calendar.MONTH, Integer.parseInt(idno.substring(10, 12)) - 1);
            cd.set(Calendar.DAY_OF_MONTH, Integer.parseInt(idno.substring(12, 14)));
        }
        return DateUtils.dateToString(cd.getTime());
    }

    /**
     * 在输入日期上增加（+）或减去（-）天数
     * @param date   输入日期
     * @param day 要增加或减少的天数
     */
    public static Date addDay(Date date, int day) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.add(Calendar.DAY_OF_MONTH, day);
        return cd.getTime();
    }

    /**
     * 在输入日期上增加（+）或减去（-）月份
     * @param date   输入日期
     * @param month 要增加或减少的月分数
     */
    public static Date addMonth(Date date, int month) {
        Calendar cd = Calendar.getInstance();

        cd.setTime(date);

        cd.add(Calendar.MONTH, month);

        return cd.getTime();
    }

    /**
     * 在输入日期上增加（+）或减去（-）年份
     */
    public static Date addYear(Date date, int iyear) {
        Calendar cd = Calendar.getInstance();

        cd.setTime(date);

        cd.add(Calendar.YEAR, iyear);

        return cd.getTime();
    }

    /**
     * 將OBJECT類型轉換為Date
     */
    public static Date chgObject(Object date) {

        if (date instanceof Date) {
            return (Date) date;
        }

        if (date instanceof String) {
            return DateUtils.stringToDate((String) date);
        }

        return null;

    }

    public static long getAgeByBirthday(String date) {

        Date birthday = stringToDate(date, "yyyy-MM-dd");
        long sec = new Date().getTime() - birthday.getTime();

        return sec / (1000 * 60 * 60 * 24) / 365;
    }

    public static void main(String[] args) {
        //String temp = DateUtil.dateToString(getLastDayOfMonth(new Date()),
        ///   DateUtil.DATE_FORMAT_CHINESE);
        //String s=DateUtil.dateToString(DateUtil.addDay(DateUtil.addYear(new Date(),1),-1));

        long s = DateUtils.getDayByMinusDate("2012-01-01", "2012-12-31");
        System.err.println(s);
    }
}