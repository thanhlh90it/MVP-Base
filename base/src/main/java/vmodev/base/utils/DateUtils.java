package vmodev.base.utils;

import android.content.Context;
import android.text.format.DateFormat;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by thanhle on 7/14/16.
 */
public class DateUtils {
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_FORMAT_ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    public static final String TIME_FORMAT = "h:mm a";
    public static final String DATE_TIME_NO_SECONDS_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String DATE_TIME_DAY_MONTH = "MM-dd h:mm a";

    public static Date now() {
        SimpleDateFormat formatLocal = new SimpleDateFormat(DATE_TIME_FORMAT);
        formatLocal.setTimeZone(TimeZone.getTimeZone("UTC"));
        String gtmTime = nowString();
        Date date;
        try {
            date = formatLocal.parse(gtmTime);
        } catch (ParseException e) {
            date = new Date();
        }
        return date;
    }

    public static String nowString() {
        return getDateString(new Date());
    }

    public static Date getDate(String date) throws ParseException {
        if (StringUtil.isEmptyString(date)) return null;
        SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));

        return format.parse(date);
    }

    public static Date getDateIso8601(String date) throws ParseException {
        if (StringUtil.isEmptyString(date)) return null;
        SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT_ISO_8601);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));

        return format.parse(date);
    }

    public static String getDateTimeString(Date date) {
        if (date == null) return "";
        SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));

        String gtmTime = format.format(date);
        return gtmTime;
    }

    public static String getDateTimeString(Date date, String FORMAT) {
        if (date == null) return "";
        SimpleDateFormat format = new SimpleDateFormat(FORMAT);
        //format.setTimeZone(TimeZone.getTimeZone("UTC"));

        String gtmTime = format.format(date);
        return gtmTime;
    }

    public static String getTimeString(Date date) {
        if (date == null) return "";
        SimpleDateFormat format = new SimpleDateFormat(TIME_FORMAT);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));

        String gtmTime = format.format(date);
        return gtmTime;
    }

    public static String getDateString(Date date) {
        if (date == null) return "";
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));

        String gtmTime = format.format(date);
        return gtmTime;
    }

    public static String getTripDate(Date date) {
        if (date == null) return "";
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        return dateFormat.format(date);
    }

    public static Long getFormattedDate(Date date) {
        long time;
        try {
            Timestamp tm = Timestamp.valueOf(getDateTimeString(date));
            time = tm.getTime();
            return time;
        } catch (Exception ignored) {
        }
        return null;
    }

    @SuppressWarnings("deprecation")
    public static String formatSameDayTime(final Context context, final long timestamp) {
        if (context == null) return null;
        if (android.text.format.DateUtils.isToday(timestamp))
            return android.text.format.DateUtils.formatDateTime(context, timestamp,
                    DateFormat.is24HourFormat(context) ? android.text.format.DateUtils.FORMAT_SHOW_TIME | android.text.format.DateUtils.FORMAT_24HOUR
                            : android.text.format.DateUtils.FORMAT_SHOW_TIME | android.text.format.DateUtils.FORMAT_12HOUR);
        return android.text.format.DateUtils.formatDateTime(context, timestamp, android.text.format.DateUtils.FORMAT_SHOW_DATE);
    }

    /**
     * @return
     */
    public static String convertLongTimeToDateTime(Long time) {
        return new SimpleDateFormat("HH:mm").format(new Date(time));
    }

    public static int durationBetweenDate(Date targetDate) {

        long nowTime = System.currentTimeMillis();

        long targetTime = targetDate.getTime();

        long duration = targetTime - nowTime;

        return (int) duration / 86400000;
    }

    public static String switchTypeFormat(Date date) {
        Date currentDate = new Date();
        if (currentDate.getYear() - date.getYear() > 0)
            return DATE_FORMAT;
        if (currentDate.getMonth() - date.getMonth() > 0)
            return DATE_FORMAT;
        if (currentDate.getDate() - date.getDate() > 0)
            return DATE_TIME_DAY_MONTH;
        return TIME_FORMAT;
    }
}
