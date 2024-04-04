package nl.theredhead.donderdagseweek.Logic

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.CalendarContract
import androidx.compose.ui.graphics.Color
import androidx.core.database.getIntOrNull
import nl.theredhead.donderdagseweek.models.DateOnly
import nl.theredhead.donderdagseweek.models.DayPlan
import nl.theredhead.donderdagseweek.models.TimeOnly
import nl.theredhead.donderdagseweek.models.WeekPlan
import java.text.SimpleDateFormat


class CalendarService(private val context: Context)  {

    val selectedCalendarStorage: StorageService<CalendarInfo> = StorageService("selectedCalendar", context,
        object : StorageServiceSerializer<CalendarInfo> {
            override fun serialize(obj: CalendarInfo): String {
                return obj.toString()
            }
            override fun deserialize(str: String): CalendarInfo {
                return CalendarInfo(str)
            }
        }
    );

    fun getCalendars() : List<CalendarInfo>? {
        try {
            val cursor = context.contentResolver.query(
                CalendarContract.Calendars.CONTENT_URI,
                arrayOf(
                    CalendarContract.Calendars._ID,
                    CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
                    CalendarContract.Calendars.CALENDAR_COLOR,
                    CalendarContract.Calendars.ACCOUNT_NAME,
                    CalendarContract.Calendars.ACCOUNT_TYPE,
                    CalendarContract.Calendars.CALENDAR_TIME_ZONE,
                ),
                null,
                null,
                null);
            val result = ArrayList<CalendarInfo>();
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    for (i in 0 until cursor.getCount()) {
                        val id = cursor.getInt(0)
                        val name = cursor.getString(1)
                        val colorValue = cursor.getIntOrNull(2)
                        val accountName = cursor.getString(3)
                        val accountType = cursor.getString(4)
                        var timeZone = cursor.getString(5)
                        var color = Color.Red

                        if (colorValue != null) {
                            color = Color(colorValue)
                        }

                        if (timeZone == null) {
                            timeZone = "UTC"
                        }

                        result.add(CalendarInfo(id, name, color, accountName, accountType, timeZone));
                        cursor.moveToNext()
                    }
                }
                cursor.close();
            }
            return result;
        }
        catch (error: Throwable) {
            println(error)
            return null;
        }
    }

    fun getChosenCalendar() : CalendarInfo? {
        if (selectedCalendarStorage.exists()) {
            return selectedCalendarStorage.load()
        }
        return null;
    }
    fun setChosenCalendar(calendarInfo: CalendarInfo) {
        selectedCalendarStorage.save(calendarInfo)
    }
    fun haveChosenCalendar(): Boolean {
        return getChosenCalendar() != null
    }

    fun removeChosenCalendar() {
        selectedCalendarStorage.remove()
    }

    fun import(plan: WeekPlan) {
        val calendar = getChosenCalendar();
        plan.days.forEach() {
            importEvent(it, calendar!!);
        }
    }

    private fun importEvent(dayPlan: DayPlan, calendar: CalendarInfo): Uri? {
        val eventUri = CalendarContract.Events.CONTENT_URI;
        val ev = ContentValues();
        ev.put(CalendarContract.Events.CALENDAR_ID, calendar.id)
        ev.put(CalendarContract.Events.DESCRIPTION, dayPlan.description())
        ev.put(CalendarContract.Events.DTSTART, dt(dayPlan.date, dayPlan.startTime))
        ev.put(CalendarContract.Events.DTEND, dt(dayPlan.date, dayPlan.endTime))
        ev.put(CalendarContract.Events.STATUS, 1)
        ev.put(CalendarContract.Events.EVENT_LOCATION, LocationName("ns.station.${dayPlan.station}"))
        ev.put(CalendarContract.Events.EVENT_TIMEZONE, calendar.timeZone)
        ev.put(CalendarContract.Events.HAS_ALARM, 0)

        val result = context.contentResolver.insert(eventUri, ev)
        return result;
    }

    private fun LocationName(key: String): String {
        return key
    }

    @SuppressLint("SimpleDateFormat")
    private fun dt(date: DateOnly, time: TimeOnly): Long {
        val rep = "${date}T${time}:00"
        val parser =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val theDateTime = parser.parse(rep);
        return theDateTime.time
    }
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun dtO(date: DateOnly, time: TimeOnly): Long {
//        val zone = ZoneId.of("Europe/Amsterdam");
//        val rep = "${date}T${time}:00";
//        val result = LocalDateTime.parse(rep);
//        return result.atZone(zone).toInstant().toEpochMilli();
//    }
}