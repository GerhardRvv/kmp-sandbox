package ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import model.City

@Composable
fun CitiesScreen() {
    val cities = rememberCities()
    val cityTimes = rememberCityTimes(cities)

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(cityTimes) { (city, dateTime) ->
            CityTimeRow(city, dateTime)
        }
    }
}

@Composable
fun CityTimeRow(city: City, dateTime: LocalDateTime) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = city.name, fontSize = MaterialTheme.typography.h5.fontSize)
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = dateTime.format(
                    LocalDateTime.Format {
                        hour()
                        char(':')
                        minute()
                        char(':')
                        second()
                    }
                ),
                fontSize = 30.sp
            )
            Text(
                text = dateTime.format(
                    LocalDateTime.Format {
                        dayOfMonth()
                        char('/')
                        monthNumber()
                        char('/')
                        year()
                    }
                ),
                fontSize = 16.sp,
                textAlign = TextAlign.End
            )
        }
    }
}

@Composable
fun rememberCities(): List<City> = remember {
    listOf(
        City("Berlin", TimeZone.of("Europe/Berlin")),
        City("London", TimeZone.of("Europe/London")),
        City("New York", TimeZone.of("America/New_York")),
        City("Los Angeles", TimeZone.of("America/Los_Angeles")),
        City("Tokyo", TimeZone.of("Asia/Tokyo")),
        City("Sydney", TimeZone.of("Australia/Sydney")),
    )
}

@Composable
fun rememberCityTimes(cities: List<City>): List<Pair<City, LocalDateTime>> {
    var cityTimes by remember { mutableStateOf(listOf<Pair<City, LocalDateTime>>()) }

    LaunchedEffect(Unit) {
        while (true) {
            cityTimes = cities.map {
                val now = Clock.System.now()
                it to now.toLocalDateTime(it.timeZone)
            }
            delay(1000L)
        }
    }
    return cityTimes
}
