package com.example.uts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.graphics.Path
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.uts.ui.theme.UtsTheme
import kotlinx.coroutines.delay



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UtsTheme {
                AppNavigation()

            }
        }
    }
}

// 1. Navigasi Utama Aplikasi
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(navController = navController)
        }
        composable("dashboard") {
            DashboardScreen()
        }
    }
}

// file: MainActivity.kt

// ... (kode lain di atasnya)

// file: MainActivity.kt

// ... (kode lain di atasnya)

// 2. Halaman Splash Screen (GAYA RETRO BARU & LEBIH BERWARNA)
@Composable
fun SplashScreen(navController: NavController) {
    var startAnimation by remember { mutableStateOf(false) }

    // --- Animasi utama ---
    val alphaAnim by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1200), label = "alpha"
    )

    val scaleAnim by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.6f,
        animationSpec = tween(durationMillis = 1400), label = "scale"
    )

    val offsetTextAnim by animateFloatAsState(
        targetValue = if (startAnimation) 0f else 30f,
        animationSpec = tween(durationMillis = 1000, delayMillis = 500), label = "offsetText"
    )

    // Delay sebelum berpindah ke dashboard
    LaunchedEffect(Unit) {
        startAnimation = true
        delay(4000L)
        navController.navigate("dashboard") {
            popUpTo("splash") { inclusive = true }
        }
    }

    // --- Warna dominan dan aksen ---
    val beige = Color(0xFFF3EFE3)
    val accentTeal = Color(0xFF6DA8A5)
    val darkText = Color(0xFF22252D)
    val subText = Color(0xFF4B4F58)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        beige,
                        beige.copy(alpha = 0.95f),
                        accentTeal.copy(alpha = 0.15f)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ▼▼▼ TAMBAHKAN TEKS JUDUL APLIKASI DI SINI ▼▼▼
            Text(
                text = "RetroHub",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = darkText,
                modifier = Modifier
                    .graphicsLayer {
                        alpha = alphaAnim
                        translationY = offsetTextAnim
                    }
            )

            Spacer(modifier = Modifier.height(24.dp))
            // ▲▲▲ SELESAI ▲▲▲


            // --- Efek glow lembut di belakang foto ---
            Box(
                modifier = Modifier
                    .size(230.dp)
                    .graphicsLayer {
                        scaleX = scaleAnim
                        scaleY = scaleAnim
                        alpha = alphaAnim
                    },
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawCircle(
                        brush = Brush.radialGradient(
                            listOf(
                                accentTeal.copy(alpha = 0.25f),
                                Color.Transparent
                            )
                        ),
                        radius = size.minDimension / 2.5f
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.foto),
                    contentDescription = "Foto Profil",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .border(4.dp, accentTeal.copy(alpha = 0.6f), CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- Nama (Fade-in dengan delay halus) ---
            Text(
                text = "NAMA: Hendra Darmawan", // ganti nama kamu
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = darkText,
                modifier = Modifier
                    .graphicsLayer {
                        alpha = alphaAnim
                        translationY = offsetTextAnim
                    }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // --- NIM ---
            Text(
                text = "NIM: 152023074", // ganti NIM kamu
                fontSize = 16.sp,
                color = subText.copy(alpha = alphaAnim),
                modifier = Modifier
                    .graphicsLayer {
                        translationY = offsetTextAnim / 2
                    }
            )

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}




// ... (kode lainnya biarkan saja)



// ... (kode lainnya biarkan saja)


// Data class untuk item navigasi
data class BottomNavItem(val label: String, val icon: ImageVector, val route: String)

// 3. Halaman Dashboard Utama dengan Bottom Navigation
// file: MainActivity.kt

// ... (Biarkan kode di atas, termasuk data class BottomNavItem)

// 3. Halaman Dashboard Utama dengan Bottom Navigation (VERSI BARU)
// file: MainActivity.kt

// ... (Biarkan kode di atasnya)

// Warna retro dari Kalkulator
val retroDarkBackground = Color(0xFF22252D)
val retroButtonRed = Color(0xFFE67A7A)
val retroIconGray = Color(0xFFAAAAAA)

// 3. Halaman Dashboard Utama dengan Bottom Navigation (GAYA RETRO BARU)
@Composable
fun DashboardScreen() {
    val navController = rememberNavController()
    val navItems = listOf(
        BottomNavItem("Biodata", Icons.Default.Person, "biodata"),
        BottomNavItem("Kontak", Icons.Default.Call, "kontak"),
        BottomNavItem("Kalkulator", Icons.Filled.Calculate, "kalkulator"),
        BottomNavItem("Cuaca", Icons.Default.WbSunny, "cuaca"),
        BottomNavItem("Berita", Icons.AutoMirrored.Filled.Article, "berita")
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "biodata"
    val selectedIndex = navItems.indexOfFirst { it.route == currentRoute }

    // Simpan posisi center X tiap item (dalam px)
    val centers = remember { mutableStateListOf<Float>().apply { repeat(navItems.size) { add(0f) } } }

    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(85.dp)
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                // Canvas di bawah
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                ) {
                    val itemWidth = size.width / navItems.size

                    // Gunakan center yang diukur kalau ada, else fallback ke center proporsional
                    val measuredCenter = centers.getOrNull(selectedIndex) ?: 0f
                    val centerX = if (measuredCenter > 0f) measuredCenter else itemWidth * (selectedIndex + 0.5f)

                    // Ukuran curve dibuat proporsional terhadap itemWidth
                    val curveWidth = itemWidth * 1.5f
                    // Tinggi lekukan (atur agar tidak terlalu dalam)
                    val curveHeight = 190f

                    val path = Path().apply {
                        moveTo(0f, 0f)
                        lineTo(centerX - curveWidth / 2f, 0f)
                        cubicTo(
                            centerX - curveWidth / 4f, 0f,
                            centerX - curveWidth / 4f, curveHeight,
                            centerX, curveHeight
                        )
                        cubicTo(
                            centerX + curveWidth / 4f, curveHeight,
                            centerX + curveWidth / 4f, 0f,
                            centerX + curveWidth / 2f, 0f
                        )
                        lineTo(size.width, 0f)
                        lineTo(size.width, size.height)
                        lineTo(0f, size.height)
                        close()
                    }

                    drawPath(
                        path = path,
                        color = retroDarkBackground,
                    )
                }

                // Icons Layer — ukur posisinya untuk Canvas
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(horizontal = 0.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    navItems.forEachIndexed { index, item ->
                        val selected = currentRoute == item.route
                        val animatedOffset by animateFloatAsState(
                            targetValue = if (selected) (-20f) else 0f,
                            animationSpec = tween(400),
                            label = ""
                        )

                        // setiap Box mengukur posisinya di parent dan menyimpan center X (px)
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .offset(y = Dp(animatedOffset / 3))
                                .onGloballyPositioned { coords ->
                                    // posisi relatif terhadap parent Box (yang ukurannya sama dengan Canvas)
                                    val posX = coords.positionInParent().x
                                    val w = coords.size.width.toFloat()
                                    centers[index] = posX + w / 2f
                                }
                                .clickable {
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.startDestinationId)
                                        launchSingleTop = true
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            if (selected) {
                                Box(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .background(retroButtonRed, CircleShape)
                                )
                            }

                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label,
                                tint = if (selected) Color.White else retroIconGray,
                                modifier = Modifier.size(26.dp)
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "biodata",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("biodata") { BiodataPage() }
            composable("kontak") { KontakPage() }
            composable("kalkulator") { KalkulatorPage() }
            composable("cuaca") { CuacaPage() }
            composable("berita") { BeritaPage() }
        }
    }
}






// ... (Sisa kode di bawahnya biarkan saja)


// 4. "Fragment" atau Halaman untuk setiap menu
@Composable
fun PageTemplate(title: String, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F0)) // Latar belakang abu-abu muda
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(title, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Spacer(modifier = Modifier.height(20.dp))
        content()
    }
}






// Pratinjau untuk melihat di Android Studio
@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    UtsTheme {
        DashboardScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    UtsTheme {
        // NavController dummy untuk preview
        SplashScreen(navController = rememberNavController())
    }
}
