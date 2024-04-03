package nl.theredhead.donderdagseweek.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.em
import nl.theredhead.donderdagseweek.R
import nl.theredhead.donderdagseweek.defaultPadding

@Composable
fun Header(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        fontSize = 7.em,
        color = (colorResource(id = R.color.ns_blue)),
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.ns_yellow))
            .padding(defaultPadding)
            .then(modifier)
    )
}
