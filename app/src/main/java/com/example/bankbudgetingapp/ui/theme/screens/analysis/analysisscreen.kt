package com.example.bankbudgetingapp.ui.theme.screens.analysis

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
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalysisScreen(navController: NavController) {
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},

            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* action */ },
            ) {
                Icon(Icons.Default.Info, contentDescription = "Chart")
            }
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Simulated chart bar and labels
            Text("Spend", color = Color.White, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                // Chart line
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .align(Alignment.Center)
                        .background(Color.Green)
                )
                // Chart labels (just a mock)
                Column(
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Text("US$2", color = Color.Gray, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(30.dp))
                    Text("US$1", color = Color.Gray, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(30.dp))
                    Text("US$0", color = Color.Gray, fontSize = 12.sp)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Apr 21", color = Color.Gray, fontSize = 12.sp)
                    Text("Apr 28", color = Color.Gray, fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            Icon(
                Icons.Default.MailOutline,
                contentDescription = "Mail Icon",
                tint = Color.Gray,
                modifier = Modifier.size(64.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text("No purchases found", color = Color.White, fontSize = 18.sp)
            Text(
                "Change filters or check back when you get an email",
                color = Color.Gray,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}




@Preview(showBackground = true , showSystemUi = true)
@Composable
fun AnalysisScreenPreview(){
    AnalysisScreen(rememberNavController())
}