package com.example.pemesanantiket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            var hargaTiket by rememberSaveable {
                mutableStateOf(50000)
            }

            var jumlahTiket by rememberSaveable {
                mutableStateOf(1)
            }

            var namaPembeli by rememberSaveable {
                mutableStateOf("")
            }

            var status by rememberSaveable {
                mutableStateOf("Silakan pesan tiket")
            }

            var prosesPesanan by rememberSaveable {
                mutableStateOf(0)
            }

            LaunchedEffect(prosesPesanan) {
                if (prosesPesanan > 0 && namaPembeli.isNotBlank()) {
                    status = "Memproses pesanan..."

                    delay(5000)

                    status = "Tiket telah dipesan"
                }
            }

            Scaffold(
                modifier = Modifier.fillMaxSize()
            ) { innerPadding ->

                TiketScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    hargaTiket = hargaTiket,
                    jumlahTiket = jumlahTiket,
                    namaPembeli = namaPembeli,
                    status = status,
                    onNamaChange = {
                        namaPembeli = it
                    },
                    onTambahTiket = {
                        jumlahTiket++
                    },
                    onKurangiTiket = {
                        if (jumlahTiket > 1) {
                            jumlahTiket--
                        }
                    },
                    onPesanTiket = {
                        if (namaPembeli.isBlank()) {
                            status = "Nama masih kosong"
                        } else {
                            prosesPesanan++
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun TiketScreen(
    modifier: Modifier,
    hargaTiket: Int,
    jumlahTiket: Int,
    namaPembeli: String,
    status: String,
    onNamaChange: (String) -> Unit,
    onTambahTiket: () -> Unit,
    onKurangiTiket: () -> Unit,
    onPesanTiket: () -> Unit
) {
    Column(
        modifier = modifier
            .background(Color.White)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF4059C9))
                .padding(
                    horizontal = 20.dp,
                    vertical = 18.dp
                )
        ) {
            Text(
                text = "Pemesanan Tiket",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {

            Text(
                text = "Nama",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            OutlinedTextField(
                value = namaPembeli,
                onValueChange = onNamaChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text("Masukkan nama Anda")
                },
                singleLine = true,
                shape = RoundedCornerShape(6.dp)
            )

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            Text(
                text = "Harga Tiket",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = "Rp$hargaTiket",
                fontSize = 16.sp
            )

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            Text(
                text = "Jumlah Tiket",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Button(
                    onClick = onKurangiTiket,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEFF3FB),
                        contentColor = Color.Black
                    )
                ) {
                    Text("-")
                }

                Text(
                    text = "$jumlahTiket",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Button(
                    onClick = onTambahTiket,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEFF3FB),
                        contentColor = Color.Black
                    )
                ) {
                    Text("+")
                }
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Text(
                text = "Total Harga: Rp${hargaTiket * jumlahTiket}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            Button(
                onClick = onPesanTiket,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4059C9)
                )
            ) {
                Text(
                    text = "Pesan Tiket",
                    color = Color.White
                )
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = when (status) {
                            "Nama masih kosong" ->
                                Color(0xFFFFEEEE)

                            "Tiket telah dipesan" ->
                                Color(0xFFEAF7EA)

                            "Memproses pesanan..." ->
                                Color(0xFFEAF2FF)

                            else ->
                                Color(0xFFF4F6FA)
                        },
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(14.dp)
            ) {
                Text(
                    text = "Status: $status",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}