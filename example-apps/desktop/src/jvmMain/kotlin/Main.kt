import androidx.compose.material.MaterialTheme
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.antonius.pianist.demo.DemoApp

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        MaterialTheme {
            DemoApp(32.dp)
        }
    }
}