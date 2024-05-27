import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import composedemo.composeapp.generated.resources.Res
import composedemo.composeapp.generated.resources.egypt
import composedemo.composeapp.generated.resources.france
import composedemo.composeapp.generated.resources.indonesia
import composedemo.composeapp.generated.resources.japan
import composedemo.composeapp.generated.resources.mexico
import kotlinx.datetime.Clock
import kotlinx.datetime.IllegalTimeZoneException
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalResourceApi::class)
data class Country (val name : String, val zone: TimeZone, val image: DrawableResource)

@OptIn(ExperimentalResourceApi::class)
fun countries() = listOf(
    Country("Japan", TimeZone.of("Asia/Tokyo"), Res.drawable.japan),
    Country("France", TimeZone.of("Europe/Paris"), Res.drawable.france),
    Country("Mexico", TimeZone.of("America/Mexico_City"), Res.drawable.mexico),
    Country("Indonesia", TimeZone.of("Asia/Jakarta"), Res.drawable.indonesia),
    Country("Egypt", TimeZone.of("Africa/Cairo"), Res.drawable.egypt)
)

@OptIn(ExperimentalResourceApi::class)
@Composable
@Preview
fun App(countries : List<Country> = countries()) {
    var showCountries by remember { mutableStateOf(false) }
    var timeAtLocation: String? by remember { mutableStateOf("No time selected") }
    MaterialTheme {
        Column(Modifier.fillMaxWidth().padding(20.dp)) {
            Text(
                timeAtLocation.toString(),
                style = TextStyle(fontSize = 20.sp),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)
            )
            Row(modifier = Modifier.padding(top = 10.dp, bottom = 20.dp)) {
                DropdownMenu(
                    expanded = showCountries,
                    onDismissRequest = { showCountries = false }
                ) {
                    countries.forEach { country ->
                        DropdownMenuItem(onClick = {
                            timeAtLocation = currentTimeAt(country.name, country.zone)
                            showCountries = false
                        }) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(country.image),
                                    modifier = Modifier.size(50.dp).padding(end = 10.dp),
                                    contentDescription = null
                                )
                            }
                            Text(country.name)
                        }
                    }
                }
            }
            Button(
                modifier = Modifier.padding(top = 10.dp, bottom = 20.dp),
                onClick = { showCountries = !showCountries }) {
                Text("Show Time")
            }
        }
    }
}

private fun currentTimeAt(location: String, zone: TimeZone): String? {
    fun LocalTime.formatted() = "$hour:$minute:$second"
    return try {
        val time = Clock.System.now()
        val localTime = time.toLocalDateTime(zone).time
        "The time in $location is ${localTime.formatted()}"
    } catch (ex: IllegalTimeZoneException) {
        null
    }
}