package nl.theredhead.donderdagseweek.models

class Employee {
    val number: String
    val name: String;

    constructor(text: String) {
        val index = text.indexOf(' ');

        number = text.substring(0, index);
        name = text.substring(index+ 1);
    }

    constructor(employeeNumber: String, employeeName: String) {
        number = employeeNumber;
        name = employeeName;
    }
}