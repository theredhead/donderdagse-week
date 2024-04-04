package nl.theredhead.donderdagseweek.models;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import nl.theredhead.donderdagseweek.logic.Sanity;

public class TimeOnly {
    private int Hour;
    public int getHour() {
        return Hour;
    }
    public void setHour(int hour) throws Exception {
        Sanity.Enforce(hour > -1 && hour < 24, "Hour must be between 0 and 23");
        Hour = hour;
    }

    private int Minute;
    public int getMinute() {
        return Minute;
    }
    public void setMinute(int minute) throws Exception {
        Sanity.Enforce(minute > -1 && minute < 60, "Minute must be between 0 and 59");
        Minute = minute;
    }

    public TimeOnly(int hour, int minute) throws Exception {
        setHour(hour);
        setMinute(minute);
    }
    @SuppressLint("DefaultLocale")
    public TimeOnly(String text) throws Exception {
        try {
            Sanity.Enforce(text.length() == 5, String.format("Unexpected length for time (%d) '%s'", text.length(), text));
            String h = text.substring(0, 2);
            String m = text.substring(3);
            int hh = Integer.parseInt(h);
            int mm = Integer.parseInt(m);
            setHour(hh);
            setMinute(mm);
        }
        catch (Throwable exception) {
            String msg = String.format("Failed to parse TimeOnly from '%s'", text);
            throw new Exception(msg, exception);
        }
    }

    @SuppressLint("DefaultLocale")
    @NonNull
    @Override
    public String toString() {
        return
                String.format("%02d", getHour()) + ":" +
                String.format("%02d", getMinute());
    }
}
