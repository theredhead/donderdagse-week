package nl.theredhead.donderdagseweek.components

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
fun SubHeader(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        fontSize = 5.em,
        color = (colorResource(id = R.color.ns_yellow)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(defaultPadding)
            .then(modifier)
    )
}
