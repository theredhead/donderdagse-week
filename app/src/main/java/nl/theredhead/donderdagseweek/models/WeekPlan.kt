package nl.theredhead.donderdagseweek.models

import nl.theredhead.donderdagseweek.Logic.splitByChar
import nl.theredhead.donderdagseweek.Logic.splitByMask

class WeekPlan {
    val weekNumber: Int;
    val year: Int;
    val employee: Employee;
    val days: List<DayPlan>;

    constructor(text: String) {
        val lines = text.lines();
        val last = lines.count() - 1;
        val weekInYear = lines[0].trim().splitByChar(' ').last();
        val (w, y) = weekInYear.splitByMask("__ ____");
        year = y.toInt();
        weekNumber = w.toInt();
        employee = Employee(lines[2]);
        days = ArrayList<DayPlan>();

        for (index in 6..12) {
            val dayPlan = DayPlan(lines[index], year);
            days.add(dayPlan);
        }
    }
}