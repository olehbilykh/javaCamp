package com.olehbilykh.camp.TaskForWarPeriod;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static java.util.Calendar.SEPTEMBER;

public class Main {
    public static void main(String[] args) {
        LocalDate fromDate = LocalDate.of(1939, SEPTEMBER, 1);
        LocalDate toDate = LocalDate.of(1945, SEPTEMBER, 2);
        long duration = ChronoUnit.DAYS.between(fromDate, toDate) + 1;

        System.out.println("WWII continued for " + duration + " days.");
    }
}
