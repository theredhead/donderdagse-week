package nl.theredhead.donderdagseweek

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import nl.theredhead.donderdagseweek.components.Header
import nl.theredhead.donderdagseweek.components.Paragraph
import nl.theredhead.donderdagseweek.components.SubHeader
import nl.theredhead.donderdagseweek.components.VerticalStack
import nl.theredhead.donderdagseweek.logic.CalendarInfo
import nl.theredhead.donderdagseweek.logic.CalendarService
import nl.theredhead.donderdagseweek.logic.restartActivity
import nl.theredhead.donderdagseweek.ui.theme.DonderdagseWeekTheme

val defaultPadding = 10.dp;

class MainActivityViewModel(private val calendarService: CalendarService, val activity: MainActivity) {
    val calendars: List<CalendarInfo>?

    init {
        calendars = calendarService.getCalendars();
    }
    fun chosenCalendar(): CalendarInfo? {
        return calendarService.getChosenCalendar();
    }
    fun chooseCalendar(calendarInfo: CalendarInfo) {
        calendarService.setChosenCalendar(calendarInfo)
        activity.restartActivity()
    }

    fun removeChosenCalendar() {
        calendarService.removeChosenCalendar()
    }
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = MainActivityViewModel(CalendarService(this), this);
        setContent {
            DonderdagseWeekTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    if (viewModel.calendars != null) {
                        MainActivityLayout(viewModel)
                    } else {
                        PermissionRequiredLayout(this)
                    }
                }
            }
        }
    }
}

@Composable
fun AllSetView(calendar: CalendarInfo, viewModel: MainActivityViewModel) {
    VerticalStack {
        SubHeader(text = stringResource(R.string.you_re_all_set))
        Paragraph(text = stringResource(
            R.string.events_will_be_created_in,
            calendar.name
        ))

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
                    viewModel.removeChosenCalendar()
                    viewModel.activity.restartActivity()
                }) {
                    Text(text = stringResource(R.string.reset_your_choice))
                }
            }
        }
    }
}
@Composable
fun MainActivityLayout(viewModel: MainActivityViewModel) {
    VerticalStack {
        Header(text = "Donderdagse Week Importer")
        Paragraph(text = stringResource(R.string.app_purpose_intro))

        val chosenCalendar = viewModel.chosenCalendar()
        if (chosenCalendar != null) {
            AllSetView(chosenCalendar, viewModel)
        }
        CalendarPicker(viewModel)
    }
}

@Composable
fun CalendarPicker(viewModel: MainActivityViewModel) {
    val calendars = viewModel.calendars ?: emptyList();
    val chosenCalendar = viewModel.chosenCalendar();

    if (calendars.any()) {
        Paragraph(text = stringResource(R.string.please_choose_a_calendar_to_import_into),
            Modifier.padding(defaultPadding)
        )
        LazyColumn (
            Modifier
                .fillMaxSize()
                .padding(defaultPadding)
        ) {
            items(calendars.count()) { i ->
                val calendar = calendars[i]
                val isChosen = calendar.isEqualTo(chosenCalendar)

                Surface(
                    modifier = Modifier
                        .clickable(
                            onClick = { viewModel.chooseCalendar(calendar) }
                        )
                ) {
                    CalendarListItem(
                        calendar = calendar,
                        isSelected = isChosen
                    )
                }
            }
        }
    } else {
        Paragraph(text = stringResource(R.string.no_calendar_exists))
    }
}

@Composable
fun CalendarListItem(calendar: CalendarInfo, isSelected: Boolean = false) {
    val background: Color = if (isSelected)
        colorResource(id = R.color.ns_blue)
    else
        colorResource(id = R.color.ns_gray_alpha_10)

    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(defaultPadding)
            .background(background)
    ) {
        Box(modifier = Modifier
            .size(26.dp)
            .clip(RoundedCornerShape(50))
            .background(calendar.color)
        )

        Column(modifier = Modifier
            .fillMaxWidth()
        )
        {
            Text(text = calendar.name,
                fontSize = 6.em,
                modifier = Modifier.padding(
                    PaddingValues(
                        horizontal = defaultPadding,
                        vertical = defaultPadding / 3
                    )
                ))
            if ((calendar.accountType + calendar.accountName).isNotEmpty()) {
                Text(text = "${calendar.accountName} → ${calendar.accountType}",
                    fontSize = 3.em,
                    modifier = Modifier
                        .padding(defaultPadding)
                    )
            }
        }
    }
}
