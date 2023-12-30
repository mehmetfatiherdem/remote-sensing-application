package main.java.utils;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static main.java.AdvancedLogger.logException;

public class Helpers {
    public static StringBuilder ByteToStr(byte[] a) {
        if (a == null) return null;

        StringBuilder ret = new StringBuilder();

        int i = 0;
        while(a[i] != 0){
            ret.append((char)a[i++]);
        }

        return ret;
    }

    public static Date strToDate(String str){
        Date timeStamp;
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        try {
            timeStamp = sdf.parse(str);
        } catch (ParseException e) {
            logException(e);
            throw new RuntimeException(e);
        }
        return  timeStamp;
    }

    public static long calculateSecondsPassed(Date date){

        Date currentDate = new Date();

        // Calculate the difference in milliseconds
        long timeDifference = currentDate.getTime() - date.getTime();

        // Convert the difference to seconds
        long secondsPassed = timeDifference / 1000;

        return secondsPassed;
    }
}
