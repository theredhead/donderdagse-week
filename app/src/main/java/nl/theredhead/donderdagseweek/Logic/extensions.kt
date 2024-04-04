package nl.theredhead.donderdagseweek.Logic

import android.app.Activity
fun Activity.restartActivity() {
    this.recreate();
}

fun String.fmt(vararg args: Unit): String {
    return String.format(this, *args)
}