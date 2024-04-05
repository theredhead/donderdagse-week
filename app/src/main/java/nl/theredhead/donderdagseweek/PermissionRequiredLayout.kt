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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import nl.theredhead.donderdagseweek.components.Header
import nl.theredhead.donderdagseweek.components.Paragraph
import nl.theredhead.donderdagseweek.components.VerticalStack
import nl.theredhead.donderdagseweek.logic.restartActivity

@Composable
fun PermissionRequiredLayout(activity: Activity) {
    Surface {
        VerticalStack {
            Header(text = stringResource(R.string.permission_required))
            Paragraph(text = stringResource(R.string.cannot_read_or_write_without_permission))
            Paragraph(text = stringResource(R.string.please_allow_access))
            Paragraph(text = stringResource(R.string.permission_api_message))

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
                        val packageName = "nl.theredhead.donderdagseweek"

                        try {
                            //Open the specific App Info page:
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            intent.setData(Uri.parse("package:$packageName"))
                            activity.startActivity(intent)
                        } catch (e: ActivityNotFoundException) {
                            //e.printStackTrace();

                            //Open the generic Apps page:
                            val intent = Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS)
                            activity.startActivity(intent)
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
