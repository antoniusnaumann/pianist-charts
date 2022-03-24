package dev.antonius.pianist.demo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

internal class RadioItem<T>(val label: String, val option: T, val action: (T) -> Unit, val selected: (T) -> Boolean = { it == option })

@Composable
@ExperimentalMaterial3Api
internal fun <T> LabeledRadioGroup(active: T, items: List<RadioItem<T>>) {
    Column {
        items.forEach { item ->
            LabeledRadioButton(item.label, item.selected(active), onClick = { item.action(item.option) })
        }
    }
}

@Composable
@ExperimentalMaterial3Api
internal fun LabeledRadioButton(label: String, selected: Boolean, onClick: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(selected = selected, onClick = onClick)
        Text(label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onBackground)
    }
}