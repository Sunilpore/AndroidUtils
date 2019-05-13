
package com.example.apinotification.utils.date;

import android.text.TextUtils;

import com.example.apinotification.utils.constants.Constants;
import com.example.apinotification.utils.log.AppLoggerHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateTimeHelper {

    public DateTimeHelper() {
        AppLoggerHelper.init();
    }

    public static String getCurrentTime() {
        String currentTime;
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATETIME.DATETIME_FORMAT_2, Locale.US);
            dateFormat.setTimeZone(TimeZone.getTimeZone(Constants.DATETIME.TIMEZONE_UTC));
            currentTime = dateFormat.format(new Date());
        } catch (Exception e) {
            AppLoggerHelper.e(Constants.DATETIME.TAG, e.getMessage());
            currentTime = "";
        }
        return currentTime != null ? currentTime : "";
    }

    public static String getFormattedTime(Date date) {
        if (date == null)
            return null;

        try {
            // Quoted "Z" to indicate UTC, no timezone offset
            SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATETIME.DATETIME_FORMAT_2, Locale.US);
            dateFormat.setTimeZone(TimeZone.getTimeZone(Constants.DATETIME.TIMEZONE_UTC));
            return dateFormat.format(date);
        } catch (Exception e) {
            AppLoggerHelper.e(Constants.DATETIME.TAG, e.getMessage());
        }

        return getCurrentTime();
    }

    public static Date getFormattedDate(String time) {
        if (TextUtils.isEmpty(time))
            return null;

        try {
            DateFormat format = new SimpleDateFormat(Constants.DATETIME.DATETIME_FORMAT_2, Locale.US);
            format.setTimeZone(TimeZone.getTimeZone(Constants.DATETIME.TIMEZONE_UTC));
            return format.parse(time);
        } catch (Exception e) {
            AppLoggerHelper.e(Constants.DATETIME.TAG, e.getMessage());
        }

        return new Date();
    }

    public static String getTimeStamp() {
        return new SimpleDateFormat(Constants.DATETIME.DATETIME_FORMAT_6, Locale.US).format(new Date());
    }

    public static String convertToTime(String timestamp) {
        long datetime = Long.parseLong(timestamp);
        Date date = new Date(datetime);
        DateFormat formatter = new SimpleDateFormat(Constants.DATETIME.DATETIME_FORMAT_7);
        return formatter.format(date);
    }

    public static String getCurrentDateFormat() {

        Calendar calendar = Calendar.getInstance();
        return DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
    }


    //Created New Content


    /**
     * get time elapsed from start date to end date
     *
     * @param format    -> format in which date string is
     * @param startDate -> start date in string format
     * @param endDate   -> end date to calculate time lapsed
     * @return -> elapsed DATETIME
     */
    public static String getTimeLapsed(String format, String startDate, Date endDate) {
        //String startDate = "2018-09-14T00:00:00";
        //String endDate = "";
        String time_elapsed_one = "";/*days + " days, " +
                hours + " hours, " +
                minutes + " minutes, " +
                seconds + " seconds%n";*/

        try {

            // Date endDate = new Date();//simpleDateFormat.parse("13/10/2013 20:35:55");

            long days = printDifference(format, startDate, endDate, Constants.DATETIME.D);
            long hours = printDifference(format, startDate, endDate, Constants.DATETIME.H);
            long minutes = printDifference(format, startDate, endDate, Constants.DATETIME.M);
            long seconds = printDifference(format, startDate, endDate, Constants.DATETIME.S);


            if (days <= 0) {
                if (hours <= 0) {
                    if (minutes <= 0) {
                        time_elapsed_one = seconds + "sec";
                    } else {
                        time_elapsed_one = minutes + "min";
                    }
                } else {
                    time_elapsed_one = hours + "hr";
                }
            } else {
                time_elapsed_one = days + "day";
            }

            return time_elapsed_one;
        } catch (ParseException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }

    }


    /**
     * get time elapsed from start date to end date
     *
     * @param startDateStr -> start Date to calculate with end date
     * @param endDate      -> end date/ current time to get time lapsed
     * @param detector     ->
     *                     DAYS - get days elapsed
     *                     HOURS - get hours elapsed
     *                     MINUTES - get minutes elapsed
     *                     SECONDS - get seconds elapsed
     */
    public static long printDifference(String format, String startDateStr/*,Date startDate*/, Date endDate, String detector) throws ParseException {


        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat(format, Locale.getDefault());


        Date date1 = simpleDateFormat.parse(startDateStr);

        //milliseconds
        long different = endDate.getTime() - date1.getTime();

        System.out.println("startDate : " + date1);
        System.out.println("endDate : " + endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        switch (detector) {
            case Constants.DATETIME.D:
                return elapsedDays;
            case Constants.DATETIME.H:
                return elapsedHours;
            case Constants.DATETIME.M:
                return elapsedMinutes;
            case Constants.DATETIME.S:
                return elapsedSeconds;
            default:
                return -1;
        }

    }


    /**
     * get time elapsed from start date to end date
     *
     * @param startDateStr -> start Date to calculate with end date
     * @param endDate      -> end date/ current time to get time lapsed
     * @return -> returns elapsed time in string format
     */
    public static String printDifferenceToString(Date startDateStr, Date endDate) throws ParseException {


        //milliseconds
        long different = endDate.getTime() - startDateStr.getTime();

        System.out.println("startDate : " + startDateStr);
        System.out.println("endDate : " + endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        long elapsedMilli = (different / 100) % 1000;


        return elapsedDays + " day " + elapsedHours + " hr " + elapsedMinutes + " min " + elapsedSeconds + "." + elapsedMilli + " sec";

    }


}
