package com.henrybk.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 日期工具类
 * @author Henry
 * @since 2023-06-26
 */

public class DateUtil {

    public static List<String> getDatesOneWeekBefore(DateTime date) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        List<String> dates = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            dates.add(0, date.minusDays(i).toString(formatter));
        }

        return dates;
    }

    public static void main(String[] args) {

        List<String> datesOneWeekBefore = getDatesOneWeekBefore(DateTime.now());
        System.out.println(datesOneWeekBefore);
    }
}
