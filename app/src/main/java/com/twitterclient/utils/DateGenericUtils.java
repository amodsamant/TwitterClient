package com.twitterclient.utils;

import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateGenericUtils {

    /**
     * Function converts the twitter format date "Mon Apr 01 21:16:23 +0000 2014"
     * intp relative time span and truncates to show only one letter s,m,h,d
     * @param rawJsonDate
     * @return
     */
    public static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();

            int space = relativeDate.indexOf(" ");
            return relativeDate.substring(0,space) + relativeDate.charAt(space+1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }
}
