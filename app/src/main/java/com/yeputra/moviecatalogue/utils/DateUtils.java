package com.yeputra.moviecatalogue.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtils {
    static String format(Date date, String format){
        return new SimpleDateFormat(format).format(date);
    }

    @SuppressLint("SimpleDateFormat")
    static Date parser(String date, String format) {
        try {
            return new SimpleDateFormat(format).parse(date);
        } catch (ParseException e) {
            return null;
        }
    }


    static String parser(String date, String fromFormat, String toFormat) {
        try {
            return format(parser(date,fromFormat),toFormat);
        } catch (Exception e) {
            return "";
        }
    }
}