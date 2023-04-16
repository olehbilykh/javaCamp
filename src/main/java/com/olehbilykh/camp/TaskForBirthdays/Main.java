package com.olehbilykh.camp.TaskForBirthdays;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.util.Calendar.*;

public class Main {
    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(DAY_OF_MONTH, 29);
        calendar.set(MONTH, 0);
        for (int i = 1; i <= 5; i++) {
            calendar.add(YEAR, i);
            System.out.println(getDayStringOld(calendar.getTime(), Locale.ENGLISH));
        }
    }

    public static String getDayStringOld(Date date, Locale locale) {
        DateFormat formatter = new SimpleDateFormat("EEEE", locale);
        return formatter.format(date);
    }
}
