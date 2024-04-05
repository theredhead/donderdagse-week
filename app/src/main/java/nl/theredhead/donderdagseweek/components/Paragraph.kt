package nl.theredhead.donderdagseweek.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Paragraph(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = Modifier
            .padding(DesignTimeConfiguration.padding)
            .then(modifier)
    )
}