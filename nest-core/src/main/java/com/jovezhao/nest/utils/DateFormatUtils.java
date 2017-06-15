//package com.jovezhao.nest.utils;
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang3.time.FastDateFormat;
//
//import java.sql.Timestamp;
//import java.text.DateFormat;
//import java.text.Format;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Locale;
//
///**
// * Created by Jove on 2015-11-24.
// */
//public class DateFormatUtils {
//    /**
//     * ISO8601 formatter for date-time without time zone.</br>
//     * The format used is <tt>yyyy-MM-dd HH:mm:ss</tt>.
//     */
//    public static final FastDateFormat DATETIME_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss", Locale.US);
//
//    /**
//     * today time format
//     */
//    public static final FastDateFormat TODAY_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd 00:00:00", Locale.US);
//    /**
//     * ISO8601 formatter for date without time zone.
//     * The format used is <tt>yyyy-MM-dd</tt>.
//     */
//    public static final FastDateFormat DATE_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd", Locale.US);
//    /**
//     * ISO8601-like formatter for time without time zone.
//     * The format used is <tt>HH:mm:ss</tt>.
//     */
//    public static final FastDateFormat TIME_FORMAT = FastDateFormat.getInstance("HH:mm:ss", Locale.US);
//    /**The format used is <tt>HH:mm</tt>.*/
//    public static final FastDateFormat HM_TIME_FORMAT = FastDateFormat.getInstance("HH:mm", Locale.US);
//    /**The format used is <tt>yyyy-MM-dd HH</tt>.*/
//    public static final FastDateFormat HOUR_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH", Locale.US);
//    /**The format used is <tt>yyyy-MM</tt>.*/
//    public static final FastDateFormat MONTH_FORMAT = FastDateFormat.getInstance("yyyy-MM", Locale.US);
//    /**The format used is <tt>yyyy-MM-dd HH:mm</tt>.*/
//    public static final FastDateFormat MIN_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm", Locale.US);
//    /**The format used is <tt>yyyyMMdd</tt>.*/
//    public static final FastDateFormat DATE_8CHAR = FastDateFormat.getInstance("yyyyMMdd", Locale.US);
//    /**The format used is <tt>yyyy.MM.dd</tt>.*/
//    public static final FastDateFormat DATE_DOT = FastDateFormat.getInstance("yyyy.MM.dd", Locale.US);
//    /**The format used is <tt>yyyy/MM/dd</tt>.*/
//    public static final FastDateFormat DATE_SLASH = FastDateFormat.getInstance("yyyy/MM/dd", Locale.US);
//    /**<tt>MM/dd/yyyy</tt>*/
//    public static final FastDateFormat DATET_SLASH = FastDateFormat.getInstance("MM/dd/yyyy", Locale.US);
//    /**<tt>MM/dd/yyyy HH:mm</tt>*/
//    public static final FastDateFormat DATETTIME_SLASH = FastDateFormat.getInstance("MM/dd/yyyy HH:mm", Locale.US);
//    /**ORA标准日期格式<tt>yyyyMMdd</tt>*/
//    public static final FastDateFormat ORA_DATE_FORMAT = FastDateFormat.getInstance("yyyyMMdd", Locale.US);
//    /**ORA标准时间格式<tt>yyyyMMddHHmm</tt>*/
//    public static final FastDateFormat ORA_DATE_TIME_FORMAT = FastDateFormat.getInstance("yyyyMMddHHmm", Locale.US);
//    /**ORA标准时间格式<tt>yyyyMMddHHmmss</tt>*/
//    public static final FastDateFormat ORA_DATE_TIME_ALL__FORMAT = FastDateFormat.getInstance("yyyyMMddHHmmss",
//            Locale.US);
//    /**The format used is <tt>2009年6月1日 星期一</tt>.*/
//    public static final FastDateFormat FULL_CHN = FastDateFormat.getDateInstance(FastDateFormat.FULL, Locale.CHINA);
//    /**The format used is <tt>yyyy年MM月dd日 HH时mm分ss秒</tt>.*/
//    public static final FastDateFormat DATETIME_CHN = FastDateFormat.getInstance("yyyy年MM月dd日 HH时mm分ss秒");
//    /**The format used is <tt>2009年06月01日 20时48分59秒 星期一</tt>.*/
//    public static final FastDateFormat DATETIME_WEEK_CHN = FastDateFormat.getInstance("yyyy年MM月dd日 HH时mm分ss秒 E");
//    /**The format used is <tt>yyyy年MM月dd日</tt>.*/
//    public static final FastDateFormat DATE_CHN = FastDateFormat.getDateInstance(FastDateFormat.LONG);
//    /**The format used is <tt>yyyy/MM/dd HH:mm:ss</tt>.*/
//    public static final FastDateFormat DATETIME_SLASH = FastDateFormat.getInstance("yyyy/MM/dd HH:mm:ss", Locale.US);
//    /**The format used is <tt>yyyy.MM.dd HH:mm:ss</tt>.*/
//    public static final FastDateFormat DATATIME_DOT = FastDateFormat.getInstance("yyyy.MM.dd HH:mm:ss", Locale.US);
//    /**The format used is <tt>星期一</tt>.*/
//    public static final FastDateFormat WEEK_FORMAT = FastDateFormat.getInstance("E", Locale.US);
//    /**The format used is <tt>Sat, 13 Sep 2008 00:33:00 GMT</tt>.*/
//    public static final SimpleDateFormat GMT_FORMAT = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss", Locale.US);
//    /**The format used is <tt>yyyyMMddHHmmssSSSS</tt>.*/
//    public static final FastDateFormat LongCodeFormat = FastDateFormat.getInstance("yyyyMMddHHmmssSSSS", Locale.US);
//
//    // some static date formats
//    private static Format[] mDateFormats = loadDateFormats();
//
//    private static Format[] loadDateFormats() {
//        Format[] temp = {
//                DATETIME_FORMAT,
//                DATE_FORMAT,
//                MIN_FORMAT,
//                DATETIME_SLASH,
//                DATE_SLASH,
//                DATE_8CHAR,
//                DATETIME_CHN,
//                DATE_CHN,
//                GMT_FORMAT,
//                FastDateFormat.getInstance("EEE MMM d HH:mm:ss z yyyy", Locale.US), //星期一 六月 1 21:01:13 CST 2009
//                FastDateFormat.getInstance("M/d/yy hh:mm:ss", Locale.US),//6/1/09 09:01:41
//                FastDateFormat.getInstance("M/d/yyyy hh:mm:ss", Locale.US),
//                FastDateFormat.getInstance("M/d/yy hh:mm a", Locale.US),
//                FastDateFormat.getInstance("M/d/yyyy hh:mm a", Locale.US),
//                FastDateFormat.getInstance("M/d/yy HH:mm", Locale.US),
//                FastDateFormat.getInstance("M/d/yyyy HH:mm", Locale.US),
//                FastDateFormat.getInstance("dd.MM.yyyy HH:mm:ss", Locale.US),
//                FastDateFormat.getInstance("yy-MM-dd HH:mm:ss.SSS", Locale.US),
//                FastDateFormat.getInstance("M-d-yy HH:mm", Locale.US),
//                FastDateFormat.getInstance("M-d-yyyy HH:mm", Locale.US),
//                FastDateFormat.getInstance("MM/dd/yyyy HH:mm:ss.SSS", Locale.US),
//                FastDateFormat.getInstance("M/d/yy", Locale.US), FastDateFormat.getInstance("M/d/yyyy", Locale.US),
//                FastDateFormat.getInstance("M-d-yy", Locale.US), FastDateFormat.getInstance("M-d-yyyy", Locale.US),
//                FastDateFormat.getInstance("MMMM d, yyyyy", Locale.US),
//                FastDateFormat.getInstance("MMM d, yyyyy", Locale.US) };
//
//        return temp;
//    }
//
//    /**
//     * Gets the array of SimpleDateFormats that DateUtil knows about.
//     **/
//    private static Format[] getFormats() {
//        return mDateFormats;
//    }
//
//    /**
//     * 匹配字符串返回DATE 不需要匹配FORMAT
//     * @param aValue
//     * @return
//     */
//    public static Date parseFromFormats(String aValue) {
//        if (StringUtils.isEmpty(aValue))
//            return null;
//
//        // get DateUtil's formats
//        Format formats[] = getFormats();
//        if (formats == null)
//            return null;
//
//        // iterate over the array and parse
//        Date myDate = null;
//        for (int i = 0; i < formats.length; i++) {
//            myDate= parse(aValue, formats[i]);
//            if(myDate!=null) return myDate;
//        }
//        // haven't returned so couldn't parse
//        return null;
//    }
//
//    /**
//     * Returns a string the represents the passed-in date parsed
//     * according to the passed-in format.  Returns an empty string
//     * if the date or the format is null.
//     **/
//    public static String format(Date aDate, FastDateFormat aFormat) {
//        if (aDate == null || aFormat == null) {
//            return "";
//        }
//        return aFormat.format(aDate);
//    }
//
//    public static String format(Date aDate, DateFormat aFormat) {
//        if (aDate == null || aFormat == null) {
//            return "";
//        }
//        return aFormat.format(aDate);
//    }
//
//    public static String format(long date, String pattern) {
//        return format(new Date(date), pattern);
//    }
//
//    /** 格式化日期为字符串. yyyy-MM-dd HH:mm:ss
//     * @param date
//     * @return
//     */
//    public static String format(Date date) {
//        return format(date, DATETIME_FORMAT);
//    }
//
//    /**
//     *  格式化日期为字符串. yyyy-MM-dd HH:mm:ss
//     *  @param date 日期字符串
//     *  @param pattern 类型
//     *  @return 结果字符串     */
//    public static String format(Date date, String pattern) {
//        if (date == null || pattern == null) {
//            return null;
//        }
//        return FastDateFormat.getInstance(pattern).format(date);
//    }//System.out.println(formatDate(new Date(),"yyyy-MM-dd HH"));
//
//    public static String format(String date, String pattern) {
//        Date date2 = parseFromFormats(date);
//        System.out.println(date2);
//        return FastDateFormat.getInstance(pattern).format(date2);
//    }//System.out.println(format("2009-05-04","yyyy/MM/dd"));
//
//    /**
//     *  格式化日期到时分秒时间格式的显示.
//     *
//     * @return - String 格式化后的时间     */
//    public static String formatDateToHMSString(Date date) {
//        if (date == null) {
//            return "";
//        }
//        return TIME_FORMAT.format(date);
//    }//System.out.println(formatDateToHMSString(date));
//
//    /**
//     *  格式化日期到yyyy-MM-dd HH:mm:ss格式的显示.
//     *
//     * @return - String 格式化后的时间     */
//    public static String formatDateToISOString(Date date) {
//        if (date == null) {
//            return "";
//        }
//        return DATETIME_FORMAT.format(date);
//    }//System.out.println(formatDateToHMSString(date));
//
//    /**
//     * 根据字符串和FORMAT返回DATE 字符串必须和FORMAT配套
//     * <br>
//     * Returns a Date using the passed-in string and format.  Returns null if the string
//     * is null or empty or if the format is null.  The string must match the format.
//     **/
//    public static Date parse(String aValue, Format aFormat){
//        if (StringUtils.isEmpty(aValue) || aFormat == null) {
//            return null;
//        }
//        try{
//            if (aFormat instanceof FastDateFormat) {
//                return ((FastDateFormat) aFormat).parse(aValue);
//            } else if (aFormat instanceof SimpleDateFormat) {
//                return ((SimpleDateFormat) aFormat).parse(aValue);
//            } else
//                return (Date) aFormat.parseObject(aValue);
//        }catch(ParseException e){
//            return null;
//        }
//    }
//
//    /**根据字符串返回DATE 字符串格式：yyyy-MM-dd HH:mm:ss*/
//    public static Date parse(String aValue){
//        try{
//            return  DateFormatUtils.DATETIME_FORMAT.parse(aValue);
//        }catch(ParseException e){
//            return null;
//        }
//    }
//
//
//
//    //System.out.println(parse("2009-04-05",DATE_FORMAT));
//    public static Date parseDateStr(String aValue, Format aFormat){
//        if (StringUtils.isEmpty(aValue) || aFormat == null) {
//            return null;
//        }
//        try {
//            if (aFormat instanceof FastDateFormat) {
//
//                return ((FastDateFormat) aFormat).parse(aValue);
//
//            } else if (aFormat instanceof SimpleDateFormat) {
//                return ((SimpleDateFormat) aFormat).parse(aValue);
//            } else
//                return (Date) aFormat.parseObject(aValue);
//        } catch (ParseException e) {
//            return null;
//        }
//    }//System.out.println(parse("2009-04-05",DATE_FORMAT));
//
//    /**
//     * 把字符串转换为数据库类型Timestamp。
//     *
//     * @param dateStr 日期
//     * @param pattern 日期格式
//     * @return 格式化之后的字符
//     * @throws ParseException
//     */
//    public static Timestamp convertStrToTimestamp(String dateStr, String pattern) throws ParseException {
//        Date date = parse(dateStr, FastDateFormat.getInstance(pattern));
//        return new Timestamp(date.getTime());
//    }
//}