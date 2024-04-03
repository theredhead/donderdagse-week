package nl.theredhead.donderdagseweek

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nl.theredhead.donderdagseweek.Components.Header
import nl.theredhead.donderdagseweek.Components.Paragraph
import nl.theredhead.donderdagseweek.Components.VerticalStack
import nl.theredhead.donderdagseweek.Logic.restartActivity

@Composable
fun PermissionRequiredLayout(activity: Activity) {
    Surface {
        VerticalStack {
            Header(text = "Permission required")
            Paragraph(text = "Sorry, we cannot read or write your calendars without permission.")
            Paragraph(text = "Please allow access through the app info screen")
            Paragraph(text = "We'll use proper permission API to request permissions at runtime in the future, but this is our first Android app, so please bear with us.")

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
                        val packageName = "nl.theredhead.donderdagseweek";

                        try {
                            //Open the specific App Info page:
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            intent.setData(Uri.parse("package:$packageName"))
                            activity.startActivity(intent);
                        } catch (e: ActivityNotFoundException) {
                            //e.printStackTrace();

                            //Open the generic Apps page:
                            val intent = Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS)
                            activity.startActivity(intent);
                        }
                    }) {
                        Text(text = "Open settings")
                    }

                    Button(onClick = { activity.restartActivity() }) {
                        Text(text = "Retry")
                    }
                }
            }
        }
    }
}
