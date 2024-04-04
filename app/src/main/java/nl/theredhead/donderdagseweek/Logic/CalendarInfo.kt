package nl.theredhead.donderdagseweek.Logic

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

class CalendarInfo(private val initBlob: String) {
    val id: Int
    val name: String
    val color: Color
    val accountName: String
    val accountType: String
    val timeZone: String

    constructor(id: Int, name: String, color: Color, accountName: String="", accountType: String="", timeZone: String="")
            : this("$id::$name::${color.toArgb()}::$accountName::$accountType::$timeZone") {
    }

    init {
        var begin = 0;
        var end = initBlob.indexOf("::");
        id = initBlob.substring(begin, end).toInt();

        begin = end + 2;
        end = initBlob.indexOf("::", begin);
        name = initBlob.substring(begin, end);

        begin = end + 2;
        end = initBlob.indexOf("::", begin);
        color = Color(initBlob.substring(begin, end).toInt());

        begin = end + 2;
        end = initBlob.indexOf("::", begin);
        accountName = initBlob.substring(begin, end);

        begin = end + 2;
        end = initBlob.indexOf("::", begin);
        accountType = initBlob.substring(begin, end);

        begin = end + 2;
        timeZone = initBlob.substring(begin);
    }
    override fun toString(): String {
        val argb = color.toArgb();
        val output = "$id::$name::$argb::$accountName::$accountType::$timeZone"
        return output;
    }

    fun isEqualTo(other: CalendarInfo?): Boolean {
        if (other == null) return false;

        return this.id == other.id
        && this.color == other.color
        && this.name == other.name
        && this.accountName == other.accountName
        && this.accountType == other.accountType
        && this.timeZone == other.timeZone
    }
}

