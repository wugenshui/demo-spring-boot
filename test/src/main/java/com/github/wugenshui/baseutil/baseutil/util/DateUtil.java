package com.github.wugenshui.baseutil.baseutil.util;

import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 时间操作工具类
 *
 * @author : chenbo
 * @date : 2019-12-07
 */
@Slf4j
public class DateUtil {

    private DateUtil() {
        throw new IllegalStateException("工具类无需实例化！");
    }

    /**
     * yyyy-MM-dd
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * yyyy-MM-dd HH:mm
     */
    public static final String FORMAT_HM = "yyyy-MM-dd HH:mm";
    /**
     * yyyy-MM
     */
    public static final String FORMAT_YM = "yyyy-MM";
    /**
     * yyyy年MM月dd日
     */
    public static final String FORMAT_DATE_CN = "yyyy年MM月dd日";
    /**
     * MM月dd日
     */
    public static final String FORMAT_DATE_MD_CN = "MM月dd日";
    /**
     * yyyy年MM月dd日 HH时mm分
     */
    public static final String FORMAT_DATETIME_CN = "yyyy年MM月dd日 HH时mm分";
    /**
     * yyyy年MM月dd日 HH时mm分ss秒
     */
    public static final String FORMAT_FULL_CN = "yyyy年MM月dd日 HH时mm分ss秒";
    /**
     * HH:mm
     */
    public static final String FORMAT_ONLY_HM = "HH:mm";

    /**
     * 日期格式化
     */
    private static DateFormat dateFormat;
    /**
     * 日期时间格式化
     */
    private static DateFormat dateTimeFormat;

    static {
        dateFormat = new SimpleDateFormat(DATE_FORMAT);
        dateTimeFormat = new SimpleDateFormat(DATETIME_FORMAT);
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * 获取当前日期
     *
     * @return yyyy-MM-dd
     */
    public static Date getDate() {
        return getDateFormat(getDateString());
    }

    /**
     * 获取当前日期
     *
     * @return yyyy-MM-dd
     */
    public static String getDateString() {
        return dateFormat.format(new Date());
    }

    /**
     * 获取当前日期时间
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static Date getDateTime() {
        return getDateTimeFormat(getDateTimeString());
    }

    /**
     * 获取当前日期时间
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getDateTimeString() {
        return dateTimeFormat.format(new Date());
    }

    /**
     * 获取指定日期的第一天
     *
     * @param date
     * @return yyyy-MM-dd
     */
    public static String getFirstDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;

        return getFirstDayOfMonth(year, month);
    }

    /**
     * 获取指定年月的第一天
     *
     * @param year
     * @param month
     * @return yyyy-MM-dd
     */
    public static String getFirstDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1, 0, 0, 0);
        return formatDate(cal.getTime());
    }

    /**
     * 获取指定日期最后一天
     *
     * @param date
     * @return yyyy-MM-dd
     */
    public static String getLastDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        return getLastDayOfMonth(year, month);
    }

    /**
     * 获取指定年月的最后一天
     *
     * @param year
     * @param month
     * @return yyyy-MM-dd
     */
    public static String getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1, 23, 59, 59);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return formatDate(cal.getTime());
    }

    /**
     * 格式化日期
     *
     * @param obj
     * @return yyyy-MM-dd
     */
    public static String formatDate(Object obj) {
        if (obj == null || obj.toString() == "") {
            return "";
        } else if (obj instanceof Date) {
            return dateFormat.format(obj);
        } else {
            Date newDate = formatByReg(obj.toString());
            return newDate == null ? "" : dateFormat.format(newDate);
        }
    }

    /**
     * 按指定格式日期，返回日期
     *
     * @param obj
     * @param pattern
     * @return
     */
    public static Date formatDate(Object obj, String pattern) {
        try {
            String newDate = formatDateString(obj, pattern);
            if (isBlank(newDate)) {
                return null;
            }
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            return format.parse(newDate);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 按指定格式日期，返回日期字符串
     *
     * @param obj
     * @param pattern
     * @return
     */
    public static String formatDateString(Object obj, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        if (obj == null || obj.toString() == "" || isBlank(pattern)) {
            return "";
        } else if (obj instanceof Date) {
            return format.format(obj);
        } else {
            Date newDate = formatByReg(obj.toString());
            return newDate == null ? "" : format.format(newDate);
        }
    }

    /**
     * 格式化日期时间
     *
     * @param obj
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String formatDatetime(Object obj) {
        if (obj == null || obj.toString() == "") {
            return "";
        } else if (obj instanceof Date) {
            return dateTimeFormat.format(obj);
        } else {
            Date newDate = formatByReg(obj.toString());
            return newDate == null ? "" : dateTimeFormat.format(newDate);
        }
    }

    /**
     * 日期格式化
     *
     * @param date
     * @return
     */
    public static Date getDateFormat(String date) {
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 日期时间格式化
     *
     * @param dateTime
     * @return
     */
    public static Date getDateTimeFormat(String dateTime) {
        try {
            return dateTimeFormat.parse(dateTime);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 计算时间差的工具类,只支持跨一天
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static Integer countMinutes(String startTime, String endTime) {
        String[] startTimeArr = startTime.split(":");
        String[] endTimeArr = endTime.split(":");
        int startHour = Integer.parseInt(startTimeArr[0]);
        int startMinute = Integer.parseInt(startTimeArr[1]);
        int endHour = Integer.parseInt(endTimeArr[0]);
        int endMinute = Integer.parseInt(endTimeArr[1]);
        Integer count = 0;
        if (startHour > endHour) {
            count = ((24 - startHour) + endHour) * 60 + (endMinute - startMinute);
        } else {
            count = (endHour - startHour) * 60 + (endMinute - startMinute);
        }
        return count;
    }

    /**
     * 验证日期
     *
     * @param data yyyy-MM-dd
     * @return
     */
    public static boolean checkData(String data) {
        if (isBlank(data)) {
            return true;
        }
        //整数
        String regEx = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(data);
        // 字符串是否与正则表达式相匹配
        return matcher.matches();
    }

    /**
     * 正则匹配时间字符串，转换为对应格式的date对象
     *
     * @param dateString
     * @return
     */
    public static Date formatByReg(String dateString) {
        Date date = matcher(dateString, "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}", DATETIME_FORMAT);
        if (date == null) {
            date = matcher(dateString, "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}", FORMAT_HM);
        }
        if (date == null) {
            date = matcher(dateString, "\\d{4}-\\d{2}-\\d{2}", DATE_FORMAT);
        }
        if (date == null) {
            date = matcher(dateString, "\\d{4}年\\d{2}月\\d{2}日 \\d{2}时\\d{2}分\\d{2}秒", FORMAT_FULL_CN);
        }
        if (date == null) {
            date = matcher(dateString, "\\d{4}年\\d{2}月\\d{2}日 \\d{2}时\\d{2}分", FORMAT_DATETIME_CN);
        }
        if (date == null) {
            date = matcher(dateString, "\\d{4}年\\d{2}月\\d{2}日", FORMAT_DATE_CN);
        }
        return date;
    }

    /**
     * 正则匹配时间字符串
     *
     * @param date
     * @param regex
     * @param pattern
     * @return
     */
    private static Date matcher(String date, String regex, String pattern) {
        try {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(date);
            if (m.find()) {
                SimpleDateFormat format = new SimpleDateFormat(pattern);
                return format.parse(date);
            }
        } catch (Exception e) {
            log.error("正则匹配时间字符串异常", e);
        }
        return null;
    }


}
