package nl.theredhead.donderdagseweek.Components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun VerticalStack(
    modifier: Modifier = Modifier,
    arrangement: Arrangement.Vertical = Arrangement.spacedBy(default.spacing),
    alignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable () -> Unit
)
{
    Column(
        verticalArrangement = arrangement,
        horizontalAlignment = alignment,
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        content()
    }
}