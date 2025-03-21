package org.example.aprocmd.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
public class DateUtil {
    /************************
     * 기본 서버 시간 KST 확인 필요*
     ************************/

    public static LocalDateTime getCurrentLocalDateTime() {
        return LocalDateTime.now();
    }

    public static LocalDate getCurrentLocalDate() {
        return LocalDate.now();
    }

    public static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    // yyyy-MM-dd HH:mm:ss 형식으로 변환
    public static LocalDateTime stringToLocalDateTime(String date) {
        DateTimeFormatter dtfYMDTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(date, dtfYMDTime);
    }

    public static LocalDateTime stringToLocalDateTimePlusMonths(String date, int months) {
        DateTimeFormatter dtfYMDTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(date, dtfYMDTime)
                .plusMonths(months);
    }

    public static LocalDateTime stringToLocalDateTimePlusYears(String date, int years) {
        DateTimeFormatter dtfYMDTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(date, dtfYMDTime)
                .plusYears(years);
    }

    public static Date stringToDate(String date) {
        DateTimeFormatter dtfYMDTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime parsedTime = LocalDateTime.parse(date, dtfYMDTime);
        return Date.from(parsedTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static String dateToString(Date date) {
        if (date == null) {
            return null;
        }

        DateTimeFormatter dtfYMDTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .format(dtfYMDTime);
    }


    public static String localDateTimeToString(LocalDateTime localDateTime) {
        DateTimeFormatter dtfYMDTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(dtfYMDTime);
    }

    public static String localDateTimeToStringYearDivide(LocalDateTime localDateTime) {
        try {
            DateTimeFormatter dtfYMDTime = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss");
            return localDateTime.format(dtfYMDTime);
        } catch (Exception e) {
            log.error("failed to parse time {} -> string error", localDateTime, e);
        }
        return null;
    }

    public static String localDateTimeToDateString(LocalDateTime localDateTime) {
        DateTimeFormatter dtfYMD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return localDateTime.format(dtfYMD);
    }

    public static String localDateTimeToStringPlusMonths(LocalDateTime localDateTime, int months) {
        DateTimeFormatter dtfYMDTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime plusMonths = localDateTime.plusMonths(months);
        return plusMonths.format(dtfYMDTime);
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime) throws ParseException {
        return Date.from(
                localDateTime.atZone(ZoneId.systemDefault())
                        .toInstant());
    }

    public static Date localDateTimeToDatePlusYears(LocalDateTime localDateTime, int years) throws ParseException {
        LocalDateTime plusDate = localDateTime.plusYears(years);
        return Date.from(
                plusDate.atZone(ZoneId.systemDefault())
                        .toInstant());
    }

    public static Date localDateTimeToDatePlusMonths(LocalDateTime localDateTime, int months) throws ParseException {
        LocalDateTime plusDate = localDateTime.plusMonths(months);
        return Date.from(
                plusDate.atZone(ZoneId.systemDefault())
                        .toInstant());
    }

    public static Date localDateTimeToDatePlusYearMonthDays(
            LocalDateTime localDateTime,
            int year,
            int month,
            int days) {
        LocalDateTime plusDate = localDateTime
                .plusYears(year)
                .plusMonths(month)
                .plusDays(days);
        return Date.from(
                plusDate.atZone(ZoneId.systemDefault())
                        .toInstant());
    }

    public static String localDateTimeToStringPlusYearMonthDays(
            LocalDateTime localDateTime,
            int year,
            int month,
            int days) {
        DateTimeFormatter dtfYMDTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime plusDate = localDateTime
                .plusYears(year)
                .plusMonths(month)
                .plusDays(days);

        return plusDate.format(dtfYMDTime);
    }

    public static Date localDateTimeToDateMinusMonths(LocalDateTime localDateTime, int months) throws ParseException {
        LocalDateTime minusDate = localDateTime.minusMonths(months);
        return Date.from(minusDate.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static int diffMonthBetweenLocalDateTimesToMonths(LocalDateTime time1, LocalDateTime time2) {
        int monthValue1 = time1.getMonthValue();
        int monthValue2 = time2.getMonthValue();

        return Math.abs(monthValue2 - monthValue1);
    }

    public static int diffBetweenLocalDateTimesToMonths(LocalDateTime time1, LocalDateTime time2) {
        Period diff = Period.between(time1.toLocalDate(), time2.toLocalDate());
        return diff.getYears() * 12 + diff.getMonths();
    }

    // 두개의 localDateTime 차이를 일로 반환
    public static Long diffBetweenLocalDateTimesToDays(LocalDateTime time1, LocalDateTime time2) {
        return Duration.between(time1, time2).toDays();
    }

    // 두개의 localDateTime 차이를 시간으로 반환
    public static Long diffBetweenLocalDateTimesToHours(LocalDateTime time1, LocalDateTime time2) {
        return Duration.between(time1, time2).toHours();
    }

    // 두개의 localDateTime 차이를 시간으로 반환
    public static Long diffBetweenLocalDateTimesToMillis(LocalDateTime time1, LocalDateTime time2) {
        return Duration.between(time1, time2).toMillis();
    }

    // standardTime: 기준 시간, comparingTime: 비교 시간
    public static boolean isAfter(LocalDateTime standardTime, LocalDateTime comparingTime) {
        return standardTime.isAfter(comparingTime);
    }

    // standardTime: 기준 시간, comparingTime: 비교 시간
    public static boolean isBefore(LocalDateTime standardTime, LocalDateTime comparingTime) {
        return standardTime.isBefore(comparingTime);
    }

    public static Date getCurrentDate() {
        return new Date();
    }
}

