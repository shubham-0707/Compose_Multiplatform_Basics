import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application

fun main() = application {
    val state = WindowState(
        width = 300.dp,
        height = 300.dp,
        position = WindowPosition(x = 100.dp, y = 100.dp)
    )
    Window(
        onCloseRequest = ::exitApplication,
        title = "ComposeDemo",
        state = state
    ) {
        App()
    }
}