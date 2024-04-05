package nl.theredhead.donderdagseweek.models

import nl.theredhead.donderdagseweek.logic.splitByChar
import nl.theredhead.donderdagseweek.logic.splitByMask

class WeekPlan(text: String) {
    val weekNumber: Int
    val year: Int
    val employee: Employee
    val days: List<DayPlan>

    init {
        val lines = text.lines()
        val weekInYear = lines[0].trim().splitByChar(' ').last()
        val (w, y) = weekInYear.splitByMask("__ ____")
        year = y.toInt()
        weekNumber = w.toInt()
        employee = Employee(lines[2])
        days = ArrayList()
        for (index in 6..12) {
            val dayPlan = DayPlan(lines[index], year)
            days.add(dayPlan)
        }
    }
}