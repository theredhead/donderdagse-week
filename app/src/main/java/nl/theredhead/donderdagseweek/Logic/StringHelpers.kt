package nl.theredhead.donderdagseweek.Logic

import kotlin.math.min

fun String.splitByMask(mask: String): List<String> {
    val result = ArrayList<String>();
    val len = min(mask.length, this.length) -1;
    val sb = StringBuilder();
    for (index in 0..len) {

        val m = mask[index];
        val ch = this[index];

        if (m == ' ') {
            if (sb.isNotEmpty()) {
                result.add(sb.toString());
            }
            sb.clear();
        } else {
            sb.append(ch);
        }
    }
    if (sb.isNotEmpty()) {
        result.add(sb.toString());
    }

    return result;
}

fun String.splitByChar(char: Char): List<String> {
    val result = ArrayList<String>();
    val len = this.length -1;
    val sb = StringBuilder();
    for (index in 0..len) {
        val ch = this[index];
        if (ch == char) {
            if (sb.isNotEmpty()) {
                result.add(sb.toString());
            }
            sb.clear();
        } else {
            sb.append(ch);
        }
    }
    if (sb.isNotEmpty()) {
        result.add(sb.toString());
    }
    return result;
}