package vmodev.base.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by thanhle on 11/6/15.
 */
public class StringUtil {

    public static Date stringToDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date stringToDateNotT(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String dateToString(Date date, String fomat) {
        SimpleDateFormat sdf = new SimpleDateFormat(fomat, Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String dateString = null;
        try {
            dateString = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateString;
    }

    public static String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String dateString = null;
        try {
            dateString = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateString;
    }

    public static String parseDateToString(Date date, String pattern) {
        return date != null && pattern != null ? new SimpleDateFormat(pattern,
                Locale.getDefault()).format(date) : null;
    }

    public static Date parseStringToDate(String data, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.getDefault());
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = formatter.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * Check string 's empty or no
     *
     * @param string
     * @return true if string's null or length = 0, false otherwise
     */
    public static boolean isEmptyString(String string) {
        return string == null || string.trim().equals("") || string.trim().length() <= 0;
    }

    public static boolean validateEmail(String email) {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean validatePassword(String password) {
        Pattern pattern = Pattern.compile("\\s");
        Matcher matcher = pattern.matcher(password);
        return !matcher.find() && password.length() > 5;
    }

    public static boolean validateBirthday(String strDate, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Calendar calendar = Calendar.getInstance();
        calendar.get(Calendar.YEAR);
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 15);
        try {
            Date date = dateFormat.parse(strDate);
            return (date.before(calendar.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<String> getColorIdList(String str) {
        List<String> list = new ArrayList<>();
        if (str != null && !str.equals("")) {
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) == ',') {
                    list.add(str.substring(0, i));
                    str = str.substring(i + 1);
                    i = 0;
                }
                if (i == str.length() - 1) {
                    list.add(str);
                }
            }
        }
        return list;
    }

    public static String formatPrice(String price) {
        DecimalFormat formatter = new DecimalFormat("#,###,###,##0.00");
        return formatter.format(Double.valueOf(price));
    }

    public static Map<String, Long> getTimeBetweenDateWithCurrent(String strDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        //TimeZone.getTimeZone()
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        Date currentDate = calendar.getTime();
//        Date currentDate = new Date();
        Date d2 = null;

        Map<String, Long> result = new HashMap<>();

        try {
            d2 = format.parse(strDate);

            //in milliseconds
            long diff = d2.getTime() - currentDate.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            result.put("days", diffDays);
            result.put("hours", diffHours);
            result.put("minutes", diffMinutes);
            result.put("seconds", diffSeconds);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public synchronized static Map<String, Long> getTimeLeftOfClaimed(String strDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss'Z'");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        //TimeZone.getTimeZone()
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        Date currentDate = calendar.getTime();
//        Date currentDate = new Date();
        Date d2 = null;

        Map<String, Long> result = new HashMap<>();

        try {
            d2 = format.parse(strDate);

            //in milliseconds
            long diff = d2.getTime() - currentDate.getTime();

            long leftTime = (24 * 60 * 60 * 1000) + diff;

            long diffSeconds = leftTime / 1000 % 60;
            long diffMinutes = leftTime / (60 * 1000) % 60;
            long diffHours = leftTime / (60 * 60 * 1000) % 24;
            long diffDays = leftTime / (24 * 60 * 60 * 1000);

            result.put("days", diffDays);
            result.put("hours", diffHours);
            result.put("minutes", diffMinutes);
            result.put("seconds", diffSeconds);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String capitalize(String str) {
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    public static Long getFormattedDate(Date created) {
        long time;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = dateFormat.parse(dateToString(created));
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Timestamp tm = Timestamp.valueOf(dateFormat.format(date));
            time = tm.getTime();
            return time;
        } catch (ParseException ignored) {
        }
        return null;
    }

    //hiển thị ngày tháng năm của gói dịch vụ
    public static String convertDate(String date) {
        String dateString = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date tempDate = simpleDateFormat.parse(date);
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            dateString = outputDateFormat.format(tempDate);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return dateString;
    }

    public static File createCaptureFilename(Context context) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "capture_" + timeStamp + "_";
        File storageDir = new File(context.getExternalCacheDir() + "/pictures");
        if (!storageDir.exists()) storageDir.mkdirs();
        File captureFilename = null;
        try {
            captureFilename = File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException ignored) {
        }
        return captureFilename;
    }

    public static String getPath2(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {"_data"};
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }
}

