import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.antonius.pianist.DemoApp

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        MaterialTheme {
            DemoApp(Modifier.padding(64.dp))
        }
    }
}