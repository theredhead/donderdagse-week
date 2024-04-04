package nl.theredhead.donderdagseweek.models

import nl.theredhead.donderdagseweek.Logic.splitByMask

class DayPlan(text: String, year: Int)
{
    val date: DateOnly;
    val dayCode: String;
    val startTime: TimeOnly;
    val endTime: TimeOnly;
    val func: String;
    val shift: String;
    val station: String;
    val free: Boolean;

    init {
        // mask:    ________ ______  _____ _____ ___ ____
        // example: ma 25-03      5  05:06 13:24 Hc  ES
        try {
            val parts = text.splitByMask("________ ______  _____ _____ ___ ____");
            val weirdDate = parts[0];
            val (code, day, month) = weirdDate.splitByMask("__ __ __");
            dayCode = code;
            date = DateOnly(year, month.toInt(), day.toInt());
            shift = parts[1].trim();
            if (parts.count() > 3) {
                startTime = TimeOnly(parts[2]);
                endTime = TimeOnly(parts[3]);
                func = parts[4].trim();
                station = parts[5].trim();
                free = false;
            } else {
                startTime = TimeOnly("00:00");
                endTime = TimeOnly("00:00");
                func = "FREE";
                station = "";
                free = true;
            }
        } catch (ex: Throwable) {
            println(ex);
            throw Exception("Failed to parse DayPlan from '%s' in year %d".format(text, year), ex);
        }
    }

    fun description(): String {
        if (free) {
            return "Free"
        }
        return "${station} ${func}"
    }
}