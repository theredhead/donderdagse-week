package nl.theredhead.donderdagseweek

import androidx.compose.ui.graphics.Color
import nl.theredhead.donderdagseweek.Logic.CalendarInfo
import nl.theredhead.donderdagseweek.Logic.splitByMask
import nl.theredhead.donderdagseweek.models.DayPlan
import nl.theredhead.donderdagseweek.models.Employee
import nl.theredhead.donderdagseweek.models.WeekPlan
import org.junit.Test

import org.junit.Assert.*


fun WEEK_PLAN() =
    """
Donderdagse Week van 13-2024

654321 JOHN DOE

Datum    Dienst  Begin Einde Fun Stdp
________ ______  _____ _____ ___ ____
ma 25-03      5  05:06 13:24 Hc  ES
di 26-03      1  04:36 11:24 Hc  ES
wo 27-03  ?  WR     
do 28-03      R     
vr 29-03  @  10  07:35 16:24 Hc  ES
za 30-03  @   5  06:36 15:24 Hc  ES
zo 31-03  @ 112  09:06 18:01 Hc  ES
    """.trimIndent();

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun employee_parses_as_expected() {
        var line = "26536 John Doe";
        var employee = Employee(line);

        assertEquals("John Doe", employee.name);
        assertEquals("26536", employee.number);
    }

    @Test
    fun test_splitByMask() {
        val postalCode = "1234 AA";
        val parts = postalCode.splitByMask("____ __");

        assertEquals(2, parts.count());
        assertEquals("1234", parts[0]);
        assertEquals("AA", parts[1]);
    }

    @Test
    fun test_parse_DayPlan() {
        val dayPlan = DayPlan("ma 25-03      5  05:06 13:24 Hc  ES", 2024);
        assertEquals("ES", dayPlan.station);
        assertEquals("05:06", dayPlan.startTime.toString());
        assertEquals("13:24", dayPlan.endTime.toString());
        assertEquals("2024-03-25", dayPlan.date.toString())
    }

    @Test
    fun test_parse_WeekPlan() {
        val text = WEEK_PLAN()
        val weekPlan = WeekPlan(text);
        assertEquals(2024, weekPlan.year);
        assertEquals(13, weekPlan.weekNumber);
        assertEquals("654321", weekPlan.employee.number);
        assertEquals("JOHN DOE", weekPlan.employee.name);
        assertEquals(7, weekPlan.days.count());
    }

    @Test
    fun test_parse_CalendarInfo() {
        val info = CalendarInfo(
            id = 65816,
            name = "My Calendar",
            color = Color.Red,
            accountName = "USER",
            accountType = "LOCAL"
        );
        val blob = info.toString()
        val copy = CalendarInfo(blob)

        assertEquals(info.id, copy.id)
        assertEquals(info.name, copy.name)
        assertEquals(info.color, copy.color)
        assertEquals(info.accountName, copy.accountName)
        assertEquals(info.accountType, copy.accountType)
        assert(info.isEqualTo(copy))
    }
}