// file: Biodata.kt

package com.example.uts

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uts.ui.theme.UtsTheme
import java.text.SimpleDateFormat
import java.util.*

// --- Skema Warna Final (berdasarkan palet yang kamu berikan) ---
val pageBackground = Color(0xFFF3EFE3)      // Background beige lembut
val accentColor = Color(0xFFE67A7A)         // Aksen merah retro (tombol, konfirmasi)
val primaryText = Color(0xFF22252D)         // Teks utama gelap elegan
val secondaryText = Color(0xFF4B4F58)       // Teks sekunder abu gelap
val borderColor = Color(0xFFAAAAAA)         // Garis pemisah abu terang
val highlightColor = Color(0xFF6DA8A5)      // Aksen tambahan biru kehijauan


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BiodataPage() {
    var nama by remember { mutableStateOf("Hendra Darmawan") }
    var nim by remember { mutableStateOf(TextFieldValue("")) }
    var alamat by remember { mutableStateOf("") }
    var jurusan by remember { mutableStateOf("Informatics Student") }
    var universitas by remember { mutableStateOf("Institut Teknologi Nasional Bandung") }
    var tahunAngkatan by remember { mutableStateOf<String?>(null) }
    val tahunAngkatanOptions = (2020..2024).map { it.toString() }
    var expanded by remember { mutableStateOf(false) }
    var preferensiTinggal by remember { mutableStateOf("On-Campus") }
    var tanggalLahir by remember { mutableStateOf<String?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let {
                            val formatter = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                            tanggalLahir = formatter.format(Date(it))
                        }
                        showDatePicker = false
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = accentColor)
                ) { Text("OK") }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDatePicker = false },
                    colors = ButtonDefaults.textButtonColors(contentColor = accentColor)
                ) { Text("Cancel") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Scaffold(
        containerColor = pageBackground,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Profile Settings", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = pageBackground,
                    titleContentColor = primaryText
                ),
            )
        },
        bottomBar = {
            ActionButton(
                text = "Confirm Details",
                onClick = { /* Logika konfirmasi */ },
                modifier = Modifier.padding(16.dp)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileHeader(nama = nama, jurusan = jurusan)
            Spacer(modifier = Modifier.height(32.dp))

            StyledTextField(
                label = "Email",
                value = nim.text,
                onValueChange = { nim = TextFieldValue(it) },
                placeholder = "Enter Your Email"
            )

            // ▼▼▼ TAMBAHKAN BLOK KODE INI ▼▼▼
            StyledTextField(
                label = "Address",
                value = alamat,
                onValueChange = { alamat = it },
                placeholder = "Enter Your Address"
            )
            StyledDropdown(
                label = "Year of Study",
                selectedValue = tahunAngkatan ?: "Select Year",
                options = tahunAngkatanOptions,
                onOptionSelected = { tahunAngkatan = it }
            )
            StyledDatePicker(
                label = "Date of birth",
                selectedValue = tanggalLahir ?: "Select a date",
                onClick = { showDatePicker = true }
            )
            StyledRadioButton(
                label = "Gender",
                options = listOf("Male", "Female"),
                selectedOption = preferensiTinggal,
                onOptionSelected = { preferensiTinggal = it }
            )
        }
    }
}

// --- Komponen UI ---

@Composable
fun ProfileHeader(nama: String, jurusan: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 10.dp)

    ) {
        Image(
            painter = painterResource(id = R.drawable.foto),
            contentDescription = "Foto Profil",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .border(2.dp, highlightColor, CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = nama, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = primaryText)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = jurusan, fontSize = 16.sp, color = secondaryText)
    }
}

@Composable
fun StyledTextField(label: String, value: String, onValueChange: (String) -> Unit, placeholder: String) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 3.dp)) {
        Text(label, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = secondaryText)
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            textStyle = LocalTextStyle.current.copy(fontSize = 18.sp, color = primaryText),
            decorationBox = { innerTextField ->
                Column {
                    if (value.isEmpty()) {
                        Text(placeholder, fontSize = 18.sp, color = secondaryText.copy(alpha = 0.5f))
                    }
                    innerTextField()
                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(color = borderColor.copy(alpha = 0.6f), thickness = 1.dp)
                }
            }
        )
    }
}

@Composable
fun StyledDropdown(label: String, selectedValue: String, options: List<String>, onOptionSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)) {
        Text(label, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = secondaryText)
        Box {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true }
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = selectedValue,
                    fontSize = 18.sp,
                    color = if (selectedValue.startsWith("Select")) secondaryText.copy(alpha = 0.5f) else primaryText
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Dropdown",
                    tint = highlightColor
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(pageBackground.copy(alpha = 0.95f))
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option, color = primaryText) },
                        onClick = {
                            onOptionSelected(option)
                            expanded = false
                        }
                    )
                }
            }
        }
        Divider(color = borderColor.copy(alpha = 0.6f), thickness = 1.dp)
    }
}

@Composable
fun StyledDatePicker(label: String, selectedValue: String, onClick: () -> Unit) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)) {
        Text(label, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = secondaryText)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = selectedValue,
                fontSize = 18.sp,
                color = if (selectedValue.startsWith("Select")) secondaryText.copy(alpha = 0.5f) else primaryText
            )
            Icon(Icons.Default.DateRange, contentDescription = "Date", tint = highlightColor)
        }
        Divider(color = borderColor.copy(alpha = 0.6f), thickness = 1.dp)
    }
}

@Composable
fun StyledRadioButton(label: String, options: List<String>, selectedOption: String, onOptionSelected: (String) -> Unit) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)) {
        Text(label, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = secondaryText)
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            options.forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onOptionSelected(option) }
                ) {
                    RadioButton(
                        selected = (option == selectedOption),
                        onClick = { onOptionSelected(option) },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = accentColor,
                            unselectedColor = highlightColor
                        )
                    )
                    Text(text = option, color = primaryText, modifier = Modifier.padding(start = 4.dp))
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Divider(color = borderColor.copy(alpha = 0.6f), thickness = 1.dp)
    }
}

@Composable
fun ActionButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = accentColor,
            contentColor = pageBackground
        )
    ) {
        Text(text, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewKBiodata() {
    UtsTheme {
        BiodataPage()
    }
}