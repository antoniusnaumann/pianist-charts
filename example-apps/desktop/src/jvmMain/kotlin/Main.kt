import androidx.compose.material.MaterialTheme
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.antonius.common.DemoApp

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        MaterialTheme {
            DemoApp()
        }
    }
}