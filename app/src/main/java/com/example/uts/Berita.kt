// file: Berita.kt
package com.example.uts

import androidx.compose.foundation.Image
import com.example.uts.ui.theme.retroBeige
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.uts.ui.theme.UtsTheme

// --- Palet Warna Retro dari Anda ---

val retroDark = Color(0xFF22252D)
val retroRed = Color(0xFFE67A7A)
val retroGrey = Color(0xFFAAAAAA)
val retroCardBg = Color.White // Menggunakan putih agar kontras dengan background beige

// 1. Definisikan struktur data untuk setiap item berita
data class NewsArticle(
    val title: String,
    val source: String,
    val date: String,
    val imageUrl: String
)

// 2. Buat daftar berita statis (pisahkan antara Breaking dan Top Stories)
val breakingNewsList = listOf(
    NewsArticle(
        title = "Notre-Dame: Massive fire ravages Paris cathedral",
        source = "BBC",
        date = "April 16, 2019",
        imageUrl = "https://i.pinimg.com/1200x/a7/7d/f6/a77df6ad9c6a5547ccecb27d59414184.jpg"
    ),
    NewsArticle(
        title = "OpenAI announces platform for making custom GPTs",
        source = "The Verge",
        date = "November 6, 2023",
        imageUrl = "https://i.pinimg.com/1200x/bf/bf/05/bfbf05b11906b8a3a924829197da0e35.jpg"
    )
)

val topStoriesList = listOf(
    NewsArticle(
        title = "One million species at risk of extinction, UN report warns",
        source = "National Geographic",
        date = "May 6, 2019",
        imageUrl = "https://i.pinimg.com/1200x/3f/f6/6a/3ff66ae3288e012404f2784145ca518d.jpg"
    ),
    NewsArticle(
        title = "iOS 13: Everything you need to know about the new iPhone software",
        source = "Independent",
        date = "June 3, 2019",
        imageUrl = "https://i.pinimg.com/736x/15/54/5e/15545e327ae33ba278f97f39346f5f88.jpg"
    ),
    NewsArticle(
        title = "Indonesia Aims to Become a Global Hub for Digital Nomads",
        source = "Reuters",
        date = "June 10, 2024",
        imageUrl = "https://i.pinimg.com/1200x/06/95/26/0695260453ba01bb555490866edd093a.jpg"
    )
)

@Composable
fun BeritaPage() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(retroBeige), // PERUBAHAN WARNA LATAR BELAKANG UTAMA
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        // --- Bagian Header Tanggal ---
        item {
            Text(
                text = "Monday, 27 May", // Contoh tanggal
                fontSize = 14.sp,
                color = retroGrey, // PERUBAHAN WARNA TEKS TANGGAL
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
        }

        // --- Bagian Breaking News ---
        item {
            SectionHeader("Breaking News", isBreaking = true) // Tambah flag untuk warna khusus
            LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(breakingNewsList) { article ->
                    BreakingNewsCard(article = article)
                }
            }
        }

        // --- Bagian Top Stories ---
        item {
            Spacer(modifier = Modifier.height(24.dp))
            SectionHeader("Top Stories", showArrow = true)
            LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(topStoriesList) { article ->
                    TopStoryCard(article = article)
                }
            }
        }
    }
}

// Composable untuk judul setiap seksi
@Composable
fun SectionHeader(title: String, showArrow: Boolean = false, isBreaking: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = if (isBreaking) retroRed else retroDark // PERUBAHAN WARNA JUDUL SEKSI
        )
        if (showArrow) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = retroGrey // PERUBAHAN WARNA IKON PANAH
            )
        }
    }
}

// Composable untuk kartu berita besar (Breaking News)
@Composable
fun BreakingNewsCard(article: NewsArticle) {
    Card(
        modifier = Modifier.width(320.dp).height(280.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = retroCardBg), // PERUBAHAN WARNA KARTU
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(model = article.imageUrl),
                contentDescription = "Gambar berita: ${article.title}",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(14.dp).fillMaxSize(),verticalArrangement = Arrangement.SpaceBetween,horizontalAlignment = Alignment.Start) {
                Text(
                    text = article.source,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = retroGrey // PERUBAHAN WARNA TEKS SUMBER
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = article.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = retroDark, // PERUBAHAN WARNA JUDUL BERITA
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = article.date,
                    fontSize = 12.sp,
                    color = retroGrey // PERUBAHAN WARNA TANGGAL BERITA
                )
            }
        }
    }
}

// Composable untuk kartu berita kecil (Top Stories)
@Composable
fun TopStoryCard(article: NewsArticle) {
    Card(
        modifier = Modifier.width(160.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = retroCardBg), // PERUBAHAN WARNA KARTU
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(model = article.imageUrl),
                contentDescription = "Gambar berita: ${article.title}",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f / 1f)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .height(130.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = article.source,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = retroGrey // PERUBAHAN WARNA TEKS SUMBER
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = article.title,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = retroDark, // PERUBAHAN WARNA JUDUL BERITA
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    text = article.date,
                    fontSize = 10.sp,
                    color = retroGrey // PERUBAHAN WARNA TANGGAL BERITA
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewKontakPage() {
    UtsTheme {
        BeritaPage()
    }
}
