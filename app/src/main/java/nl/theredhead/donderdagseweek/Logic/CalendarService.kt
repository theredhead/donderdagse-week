package nl.theredhead.donderdagseweek.Logic

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.CalendarContract
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.core.database.getBlobOrNull
import androidx.core.database.getIntOrNull
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.function.Predicate
import kotlin.experimental.and
import kotlin.experimental.or

class CalendarService(val context: Context)  {
    fun getCalendars() : List<CalendarInfo> {
        val cursor = context.contentResolver.query(
            CalendarContract.Calendars.CONTENT_URI,
                arrayOf(
                    CalendarContract.Calendars._ID,
                    CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
                    CalendarContract.Calendars.CALENDAR_COLOR,
                    CalendarContract.Calendars.ACCOUNT_NAME,
                    CalendarContract.Calendars.ACCOUNT_TYPE
                ),
                null,
                null,
                null);
        val result = ArrayList<CalendarInfo>();
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                for (i in 0 until cursor.getCount()) {
                    val id = cursor.getInt(0);
                    val name = cursor.getString(1);
                    val colorValue = cursor.getIntOrNull(2);
                    val accountName = cursor.getString(3);
                    val accountType = cursor.getString(4);

                    var color = Color.Red;

                    if (colorValue != null) {
                        color = Color(colorValue);
                    }


                    result.add(CalendarInfo(id, name, color, accountName, accountType));
                    cursor.moveToNext()
                }
            }
            cursor.close();
        }
        return result;
    }
}