package nl.theredhead.donderdagseweek.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Row;
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalStack(
    modifier: Modifier = Modifier,
    arrangement: Arrangement.Horizontal = Arrangement.spacedBy(DesignTimeConfiguration.spacing),
    alignment: Alignment.Vertical = Alignment.Top,
    content: @Composable () -> Unit
)
{
    Row(
        horizontalArrangement = arrangement,
        verticalAlignment = alignment,
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        content()
    }
}
