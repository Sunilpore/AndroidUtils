package com.example.apinotification.utils.helper;

import android.support.annotation.NonNull;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateHelper {


    /**
     * Get current year
     *
     * @return -> current year
     */
    public static int getCurrentYear() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * Get Date with time from timestamp
     *
     * @param time     -> timestamp
     * @param timeZone -> timeZone
     * @return -> returns String in format of "yyyy-MM-dd HH:mm:ss"
     */

    public static String removeTimeFromDate(long time, String timeZone) {

        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));


        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int seconds = cal.get(Calendar.SECOND);


        return sdf.format(date) + " 00:00:00";

    }

    public static String getDateStr(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        return df.format(date);
    }

    //get Date in string format
    public static String getDate(int day, int month, int year) {
        String date;
        String monthStr = null;
        switch (month) {
            case 1:
                monthStr = "Jan";
                break;
            case 2:
                monthStr = "Feb";
                break;
            case 3:
                monthStr = "Mar";
                break;
            case 4:
                monthStr = "Apr";
                break;
            case 5:
                monthStr = "May";
                break;
            case 6:
                monthStr = "Jun";
                break;
            case 7:
                monthStr = "Jul";
                break;
            case 8:
                monthStr = "Aug";
                break;
            case 9:
                monthStr = "Sep";
                break;
            case 10:
                monthStr = "Oct";
                break;
            case 11:
                monthStr = "Nov";
                break;
            case 12:
                monthStr = "Dec";
                break;
        }

        date = day + " " + monthStr + " " + year;

        return date;
    }

    public static int getDay(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("dd", Locale.ENGLISH);
        return Integer.valueOf(df.format(date));
    }

    public static int getMonth(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("MM", Locale.ENGLISH);
        return Integer.valueOf(df.format(date));
    }

    public static int getYear(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy", Locale.ENGLISH);
        return Integer.valueOf(df.format(date));
    }

    public static String getYearStr(Date dateObj) {
        return new SimpleDateFormat("yyyy", Locale.ENGLISH).format(dateObj);
    }

    public static String getMonthStr(Date dateObj) {
        return new SimpleDateFormat("MM", Locale.ENGLISH).format(dateObj);
    }

    @NonNull
    public static String setTime(@NonNull String timestamp, @NonNull String DEFAULT_DATE_FORMAT) {
        String time = "";
        try {
            Date date = null;
            date = getDateFormat(timestamp, DEFAULT_DATE_FORMAT);
            time = showDateAsStr(date.getTime(), "IST");

        } catch (ParseException e) {
            HelperMethods.showLogError("ParseException: " + e.getMessage());
        }
        return time;
    }

    /**
     * Get Date object in specified format
     *
     * @param dateString -> date in String format
     * @param dateFormat -> Default Date format
     * @return -> will return the Date object
     */
    public static Date getDateFormat(String dateString, String dateFormat) throws ParseException {
        /*Use date format as according to your need! Ex. - yyyy/MM/dd HH:mm:ss */
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat/*"yyyy-MM-dd HH:mm:ss"*/, Locale.ENGLISH);
        //long millis = date.getTime();

        return sdf.parse(dateString);
    }


    /**
     * Get Date with time from timestamp
     *
     * @param time     -> timestamp
     * @param timeZone -> timeZone
     * @return -> returns String in format of "dd/MM/yyy HH:mm:ss (AM/PM)"
     */

    public static String showDateAsStr(long time, String timeZone) {

        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR);
        int minute = cal.get(Calendar.MINUTE);
        int seconds = cal.get(Calendar.SECOND);
        String min = String.valueOf(minute);
        String hrs = String.valueOf(hour);

        if (min.length() == 1) {
            min = "0" + minute;
        }

        if (!hrs.equals("0") && hrs.length() == 1) {
            hrs = "0" + hour;
        }

        if (cal.get(Calendar.AM_PM) == Calendar.AM) {
            // AM
            if (hour != 0)
                return sdf.format(date) + " " + hrs + ":" + min + " AM";
            else
                return sdf.format(date) + " " + 12 + ":" + min + " AM";
        } else {
            // PM
            if (hour != 0)
                return sdf.format(date) + " " + hrs + ":" + min + " PM";
            else
                return sdf.format(date) + " " + 12 + ":" + min + " PM";
        }

    }

}
