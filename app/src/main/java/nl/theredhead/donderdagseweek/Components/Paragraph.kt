package nl.theredhead.donderdagseweek.Components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.Paragraph
import androidx.compose.ui.unit.dp

@Composable
fun Paragraph(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = Modifier
            .padding(10.dp)
            .then(modifier)
    )
}