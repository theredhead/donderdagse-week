package nl.theredhead.donderdagseweek.models;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import java.util.Date;

import nl.theredhead.donderdagseweek.logic.Sanity;


public class DateOnly {

    private static final int MinYear = 1000;
    private static final int MaxYear = 9999;
    private int Year;

    public int getYear() {
        return Year;
    }

    @SuppressLint("DefaultLocale")
    public void setYear(int year) throws Exception {
        Sanity.Enforce(year > MinYear, String.format("Year must be greater than %d", MinYear));
        Sanity.Enforce(year < MaxYear, String.format("Year must be less than %d", MaxYear));
        Year = year;
    }

    private int Month;
    public int getMonth() {
        return Month;
    }

    public void setMonth(int month) throws Exception {
        Sanity.Enforce(month > 1, "Month must be greater than 0");
        Sanity.Enforce(month <= 12, "Month must be less than or equal to 12");

        Month = month;
    }

    private int Day;
    public int getDay() {
        return Day;
    }

    public void setDay(int day) throws Exception {
        Sanity.Enforce(day > 1, "Month must be greater than 0");
        Sanity.Enforce(day <= 31, "Month must be less than or equal to 12");

        Day = day;
    }

    public DateOnly(String text) throws Exception {
        try {
            Sanity.Enforce(text.length() == 10, "Unexpected length");
            setYear(Integer.parseInt(text.substring(0, 3)));
            setMonth(Integer.parseInt(text.substring(5, 6)));
            setDay(Integer.parseInt(text.substring(8, 9)));
        }
        catch (Throwable exception) {
            String msg = String.format("Failed to parse DateOnly from '%s'", text);
            throw new Exception(msg, exception);
        }
    }

    public DateOnly(int year, int month, int day) throws Exception {
        setYear(year);
        setMonth(month);
        setDay(day);
    }

    @SuppressLint("DefaultLocale")
    @NonNull
    @Override
    public String toString() {
        return String.format("%04d", getYear()) + "-" +
            String.format("%02d", getMonth()) + "-" +
            String.format("%02d", getDay());
    }

    public Date toDate() {
        return new Date(getYear(), getMonth(), getDay());
    }
}
