package nl.theredhead.donderdagseweek

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import nl.theredhead.donderdagseweek.Logic.CalendarInfo
import nl.theredhead.donderdagseweek.Logic.CalendarService
import nl.theredhead.donderdagseweek.ui.theme.DonderdagseWeekTheme

val defaultPadding = 10.dp;

class MainActivity : ComponentActivity() {
    val calendarService : CalendarService = CalendarService(this);

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val calendars = calendarService.getCalendars();

        setContent {
            DonderdagseWeekTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colorResource(id = R.color.ns_yellow)),
                    color = colorResource(id = R.color.ns_grey_90),
                ) {
                    MainActivityLayout(calendars)
                }
            }
        }
    }
}

@Composable
fun MainActivityLayout(calendars: List<CalendarInfo>, modifier: Modifier = Modifier) {
    Column (
        modifier.fillMaxWidth()
    ) {
        Header(text = "Donderdagse Week Importer")
        Text(text = "This app imports donderdagse-week files into your calendar.",
            modifier.padding(defaultPadding)
        )
        Text(text = "Please choose a calendar to import into:",
            modifier.padding(defaultPadding)
        )
        LazyColumn (
            modifier
                .fillMaxSize()
                .padding(defaultPadding)
        ) {
            items(calendars.count()) { i ->
                CalendarListItem(calendars[i])
            }
        }
    }
}

@Composable
fun CalendarListItem(item: CalendarInfo, modifier: Modifier = Modifier) {
    Row (
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = modifier
            .size(26.dp)
            .clip(RoundedCornerShape(50))
            .background(item.color)
        )

        Column {
            Text(text = item.name,
                fontSize = 6.em,
                modifier = modifier.padding(defaultPadding))
            Text(text = item.color.toString(),
                fontSize = 3.em,
                modifier = modifier
                    .padding(defaultPadding))
            Text(text = item.accountType,
                fontSize = 3.em,
                modifier = modifier
                    .padding(defaultPadding))
            Text(text = item.accountName,
                fontSize = 3.em,
                modifier = modifier
                    .padding(defaultPadding))

        }
    }
}
@Composable
fun Header(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        fontSize = 7.em,
        color = (colorResource(id = R.color.ns_yellow)),
        modifier = modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.ns_blue))
            .padding(defaultPadding)
    )
}

@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    val example = ArrayList<CalendarInfo>();
    example.add(CalendarInfo(1, "Work", Color.Red));
    example.add(CalendarInfo(2, "Private", Color.Green));
    example.add(CalendarInfo(3, "Spouse", Color.Blue));
    DonderdagseWeekTheme {
        MainActivityLayout(example)
    }
}