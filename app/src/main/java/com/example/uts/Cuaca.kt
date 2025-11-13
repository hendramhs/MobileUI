// file: Cuaca.kt
package com.example.uts

import androidx.compose.foundation.BorderStroke
import com.example.uts.ui.theme.retroTextPrimary
import com.example.uts.ui.theme.retroTextSecondary
import com.example.uts.ui.theme.retroBeige
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Air
import androidx.compose.material.icons.outlined.Thermostat
import androidx.compose.material.icons.outlined.Umbrella
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uts.ui.theme.UtsTheme

// --- PALET WARNA RETRO CUACA (BARU) ---

val retroTeal = Color(0xFF6DA8A5)  // Warna aksen untuk kartu


// --- Data Statis untuk Cuaca (tidak ada perubahan) ---
data class WeatherInfo(
    val temperature: Int = 22,
    val condition: String = "Mostly Clear",
    val location: String = "Yogyakarta",
    val date: String = "Monday, 1 January 10:00",
    val precipitation: Int = 30, // Persentase
    val humidity: Int = 20,      // Persentase
    val windSpeed: Int = 12,     // km/h
    val uvIndex: Int = 5,        // Contoh nilai UV Index
    val feelsLike: Int = 24,
    val weatherIcon: Int = R.drawable.cloudy,
)

data class HourlyForecast(
    val time: String,
    val temperature: Int,
    val icon: Int,
)

val hourlyForecasts = listOf(
    HourlyForecast("9:00", 22, R.drawable.sun),
    HourlyForecast("10:00", 22, R.drawable.cloudy),
    HourlyForecast("11:00", 21, R.drawable.clouds),
    HourlyForecast("12:00", 18, R.drawable.rain),
    HourlyForecast("13:00", 19, R.drawable.clouds),
    HourlyForecast("14:00", 20, R.drawable.sun),
)

@Composable
fun CuacaPage() {
    val weatherData = WeatherInfo()
    val scrollState = rememberScrollState()

    // Ganti latar belakang gradasi biru menjadi warna solid beige
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(retroBeige)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            TopSection(location = weatherData.location, date = weatherData.date)
            Spacer(modifier = Modifier.height(24.dp))
            MainWeatherCard(weatherData)
            Spacer(modifier = Modifier.height(24.dp))
            HourlyForecastSection()
            Spacer(modifier = Modifier.height(24.dp))
            // ▼▼▼ FIX IS HERE ▼▼▼
            WeatherDetailsCard(
                precipitation = weatherData.precipitation,
                humidity = weatherData.humidity,
                windSpeed = weatherData.windSpeed,
                uvIndex = weatherData.uvIndex,       // Add this line
                feelsLike = weatherData.feelsLike     // Add this line
            )
            // ▲▲▲ FIX IS HERE ▲▲▲
        }
    }
}

@Composable
fun TopSection(location: String, date: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location Icon",
                    // Warna ikon diubah menjadi gelap agar kontras
                    tint = retroTextPrimary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                // Warna teks diubah menjadi gelap
                Text(text = location, color = retroTextPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
            // Warna teks sekunder
            Text(text = date, color = retroTextSecondary, fontSize = 12.sp)
        }
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "More Options",
            // Warna ikon diubah menjadi gelap
            tint = retroTextPrimary
        )
    }
}

@Composable
fun MainWeatherCard(weather: WeatherInfo) {
    // Kartu utama menggunakan warna aksen Teal
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = retroTeal)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.Start) {
                Row(verticalAlignment = Alignment.Top) {
                    Text(text = weather.temperature.toString(), color = Color.White, fontSize = 80.sp, fontWeight = FontWeight.Bold)
                    Text(text = "°", color = Color.White, fontSize = 40.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 4.dp, top = 8.dp))
                }
                Text(text = weather.condition, color = Color.White, fontSize = 18.sp)
            }
            Image(
                painter = painterResource(id = weather.weatherIcon),
                contentDescription = "Weather Condition Icon",
                modifier = Modifier.size(100.dp)
            )
        }
    }
}

@Composable
fun HourlyForecastSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Today",
            // Warna teks diubah menjadi gelap
            color = retroTextPrimary,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(hourlyForecasts) { forecast ->
                HourlyForecastItem(forecast = forecast)
            }
        }
    }
}

@Composable
fun HourlyForecastItem(forecast: HourlyForecast) {
    // Kartu per jam menggunakan warna beige dengan border teal
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = retroBeige),
        border = BorderStroke(1.dp, retroTeal.copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "${forecast.temperature}°", color = retroTextPrimary, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Image(painter = painterResource(id = forecast.icon), contentDescription = "Hourly weather icon", modifier = Modifier.size(36.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = forecast.time, color = retroTextSecondary, fontSize = 14.sp)
        }
    }
}

@Composable
fun WeatherDetailsCard(precipitation: Int, humidity: Int, windSpeed: Int, uvIndex: Int, feelsLike: Int) {
    // Kartu detail menggunakan warna aksen Teal
    Card(
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier.padding(vertical = 20.dp),
        colors = CardDefaults.cardColors(containerColor = retroTeal)
    ) {
        // --- DIUBAH MENJADI COLUMN AGAR SEMUA ITEM MUAT ---
        Column(modifier = Modifier.padding(vertical = 20.dp)) {
            // Baris Pertama
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                WeatherDetailItem(icon = Icons.Outlined.Umbrella, value = "$precipitation%", label = "Precipitation")
                WeatherDetailItem(icon = Icons.Outlined.WaterDrop, value = "$humidity%", label = "Humidity")
                WeatherDetailItem(icon = Icons.Outlined.Air, value = "$windSpeed km/h", label = "Wind speed")
            }
            Spacer(modifier = Modifier.height(24.dp)) // Beri jarak antar baris
            // Baris Kedua (Item Baru)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                WeatherDetailItem(icon = Icons.Outlined.WbSunny, value = "$uvIndex", label = "UV Index")
                WeatherDetailItem(icon = Icons.Outlined.Thermostat, value = "$feelsLike°", label = "Feels Like")
            }
        }
    }
}


@Composable
fun WeatherDetailItem(icon: ImageVector, value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(imageVector = icon, contentDescription = label, tint = Color.White, modifier = Modifier.size(28.dp))
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = value, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        // Warna label dibuat sedikit transparan agar tidak terlalu ramai
        Text(text = label, color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
    }
}

// Pratinjau untuk melihat di Android Studio
@Preview(showBackground = true)
@Composable
fun cuacaPreview() {
    UtsTheme {
        CuacaPage()
    }
}