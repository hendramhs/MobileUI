// file: Kontak.kt

package com.example.uts

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import com.example.uts.ui.theme.retroBeige
import com.example.uts.ui.theme.retroRed
import com.example.uts.ui.theme.retroTeal
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.uts.ui.theme.UtsTheme

//import com.example.uts.ui.theme.retroButtonRed // Pastikan ini juga diimpor jika ada

// --- PALET WARNA RETRO BARU (TERANG) ---
// --- PALET WARNA RETRO BARU (TERANG & BERWARNA) ---


val retroDarkText = Color(0xFF333333)     // Teks Utama (Hitam Pekat)
val retroSecondaryText = Color(0xFFAAAAAA) // Teks Sekunder (Abu-abu)



// 1. Struktur data tidak perlu diubah
data class Contact(
    val id: Int,
    val name: String,
    val company: String,
    val imageUrl: String
)

// 2. Daftar kontak tidak perlu diubah
val contactList = listOf(
    Contact(1, "Andi Wijaya", "Stanford University", "https://picsum.photos/id/10/200"),
    Contact(2, "Joshua Allison", "Hooli Inc.", "https://picsum.photos/id/20/200"),
    Contact(3, "Sam Barnard", "UC Berkeley", "https://picsum.photos/id/30/200"),
    Contact(4, "Megan Blakely", "Husky Energy", "https://picsum.photos/id/40/200"),
    Contact(5, "Joel Cannon", "Hooli Inc.", "https://picsum.photos/id/50/200"),
    Contact(6, "Kyle Dickenson", "Pied Piper", "https://picsum.photos/id/60/200"),
    Contact(7, "Lauren Davis", "Facebook", "https://picsum.photos/id/70/200"),
    Contact(8, "David Gregory", "Google", "https://picsum.photos/id/80/200"),
    Contact(9, "Ethan Hunt", "IMF", "https://picsum.photos/id/90/200"),
    Contact(10, "Grace Fuller", "Apple", "https://picsum.photos/id/100/200"),
    Contact(11, "Henry Cavill", "Netflix", "https://picsum.photos/id/110/200"),
    Contact(12, "John Wick", "The Continental", "https://picsum.photos/id/120/200"),
    Contact(13, "Michael Scott", "Dunder Mifflin", "https://picsum.photos/id/130/200"),
    Contact(14, "Nathan Drake", "Treasure Hunter", "https://picsum.photos/id/140/200"),
    Contact(15, "Sarah Connor", "Cyberdyne Systems", "https://picsum.photos/id/150/200")
).sortedBy { it.name }

@Composable
fun KontakPage() {
    val groupedContacts = contactList.groupBy { it.name.first() }

    Scaffold(
        topBar = {
            RetroContactTopAppBar()
        },
        // Ganti warna container utama menjadi TERANG
        containerColor = retroBeige
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding).padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            groupedContacts.forEach { (initial, contacts) ->
                // Header untuk setiap grup huruf
                item {
                    Text(
                        text = initial.toString(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        // Warna header grup disesuaikan
                        color = retroSecondaryText,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp)
                    )
                }

                // Daftar kontak di dalam grup
                items(contacts) { contact ->
                    // Gunakan item kontak versi retro
                    RetroContactItem(contact = contact)
                    // Tambahkan garis pemisah tipis
                    HorizontalDivider(
                        color = retroRed.copy(alpha = 0.3f),
                        thickness = 1.dp,
                        modifier = Modifier.padding(start = 56.dp)
                    )
                }
            }
        }
    }
}

// Composable untuk TopAppBar dengan gaya retro terang
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RetroContactTopAppBar() {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 8.dp)) {
                Text(
                    text = "All Contacts",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        },
        actions = {
            IconButton(onClick = { /* Aksi Pencarian */ }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.White.copy(alpha = 0.8f)  // Warna ikon disesuaikan
                )
            }
            IconButton(onClick = { /* Aksi Tambah Kontak */ }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Contact",
                    tint = Color.White.copy(alpha = 0.8f)
                )
            }
        },
        // Atur warna TopAppBar agar sesuai dengan tema retro terang
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = retroTeal, // Latar belakang terang
            titleContentColor = Color.White, // Warna judul gelap
            scrolledContainerColor = retroTeal
        )
    )
}

// Composable untuk setiap item kontak dengan gaya retro terang
@Composable
fun RetroContactItem(contact: Contact) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = contact.imageUrl),
            contentDescription = "Foto profil ${contact.name}",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = contact.name,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = retroDarkText // Warna teks utama gelap
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = contact.company,
                fontSize = 14.sp,
                color = retroSecondaryText // Warna teks sekunder
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun kontaPreview() {
    UtsTheme {
        // NavController dummy untuk preview
        KontakPage()
    }
}