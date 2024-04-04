package nl.theredhead.donderdagseweek

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import nl.theredhead.donderdagseweek.components.Header
import nl.theredhead.donderdagseweek.components.HorizontalStack
import nl.theredhead.donderdagseweek.components.VerticalStack
import nl.theredhead.donderdagseweek.logic.CalendarService
import nl.theredhead.donderdagseweek.models.DayPlan
import nl.theredhead.donderdagseweek.models.WeekPlan
import nl.theredhead.donderdagseweek.ui.theme.DonderdagseWeekTheme
import java.io.InputStreamReader
import kotlin.system.exitProcess

class ConvertActivity : ComponentActivity() {
    private val calendarService = CalendarService(this)
    private var rawImportedText: String = ""
    private var plan: WeekPlan? = null
    private var importSuccess = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (intent != null && tryImportFromIntent(intent))
        {
            try {
                plan = WeekPlan(text = rawImportedText)
                importSuccess = import(plan!!)
            }
            catch (err: Throwable) {
                rawImportedText = getString(R.string.failed_to_read, intent.dataString, err)
            }
        }

        setContent {
            DonderdagseWeekTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    VerticalStack {
                        if (plan != null) {
                            Header(text = stringResource(
                                R.string.week_number_of_year,
                                plan!!.weekNumber,
                                plan!!.year
                            ))
                            PlanView(plan!!, importSuccess)
                        } else {
                            Header(text = stringResource(R.string.unknown_error))
                            Text(text = rawImportedText)
                        }
                    }
                }
            }
        }
    }

    private fun import(plan: WeekPlan): Boolean {
        try {
            calendarService.import(plan)
            return true
        } catch (err: Throwable) {
            println(err)
        }
        return false
    }

    private fun tryImportFromIntent(intent: Intent): Boolean {
        val uri = intent.data
        if (uri != null) {
            val stream = contentResolver.openInputStream(uri)
            stream?.run {
                val reader = InputStreamReader(stream)
                reader.readText().also {
                    this@ConvertActivity.rawImportedText = it
                }
                close()
            }
            return true
        }
        return false
    }
}

@Composable
fun PlanView(plan: WeekPlan, importSuccess: Boolean) {
    VerticalStack {
        if (!importSuccess) {
            ErrorMessage("Sorry, we were unable to import the file.")
        }
        LazyColumn {
            items(8) {
                if (it < 7) {
                    val dayPlan = plan.days[it]
                    DayPlanListItem(dayPlan)
                } else {
                    ExitAppButton()
                }
            }
        }
    }
}

@Composable
fun ErrorMessage(message: String) {
    Text (
        text = message, 
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .border(BorderStroke(4.dp, colorResource(id = R.color.ns_orange)))

    )
}

@Composable
fun ExitAppButton() {
    Row (
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Column (
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                exitProcess(0)
            }) {
                Text(text = stringResource(R.string.done_button_text))
            }
        }
    }
}

@Composable
fun DayPlanListItem(dayPlan: DayPlan) {
    val height = 64

    Surface (
        modifier = Modifier
            .border(BorderStroke(1.dp, colorResource(id = R.color.ns_grey_50)))
    ) {
        HorizontalStack {
            Box(
                Modifier
                    .background(colorResource(id = R.color.ns_blue))
                    .width(height.dp)
                    .height(height.dp)
            ) {
                Text(
                    text = dayPlan.dayCode,
                    color = colorResource(id = R.color.ns_yellow),
                    modifier = Modifier.padding(10.dp)
                )
            }
            if (dayPlan.free) {
                Text(text = "Free")
            } else {
                Column (
                    Modifier.width(80.dp)
                ) {
                    Box(Modifier.height((height / 2).dp)) {
                        Text(text = dayPlan.startTime.toString())
                    }
                    Box(Modifier.height((height / 2).dp)) {
                        Text(text = dayPlan.startTime.toString())
                    }
                }

                Column (
                    Modifier.width(80.dp)
                ){
                    Box(modifier = Modifier.height((height / 2).dp)) {
                        Row {
                            Text(text = "")
                            Text(text = dayPlan.func)
                        }
                    }
                    Box(modifier = Modifier.height((height / 2).dp)) {
                        Row {
                            Text(text = "")
                            Text(text = dayPlan.shift)
                        }
                    }
                }

                Column (
                    Modifier.width(80.dp)
                ){
                    Box(modifier = Modifier.height((height / 2).dp)) {
                        Row {
                            Text(text = "")
                            Text(text = dayPlan.station)
                        }
                    }
                    Box(modifier = Modifier.height((height / 2).dp)) {
                        Row {
                            Text(text = "")
                            Text(text = "")
                        }
                    }
                }
            }
        }
    }
}
