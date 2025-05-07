package com.example.bankbudgetingapp.ui.theme.screens.home


import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.bankbudgetingapp.R
import com.example.bankbudgetingapp.navigation.ROUTE_ANALYSIS
import com.example.bankbudgetingapp.navigation.UPDATE_PROFILE
import com.example.bankbudgetingapp.ui.theme.screens.scanning.ScannerActivity


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val selectedItem = remember { mutableStateOf(0) }
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    Scaffold(
        bottomBar = { NavigationBar(containerColor = Color(0xFF101924)){
            NavigationBarItem(
                selected = selectedItem.value == 2,
                onClick = {
                    selectedItem.value = 2
                    // Code to invoke scanner functionality
                    val scannerIntent = Intent(context, ScannerActivity::class.java)
                    context.startActivity(scannerIntent)
                },
                icon = {
                    Image(
                    painter = painterResource(id = R.drawable.baseline_qr_code_scanner_24),
                    contentDescription = "chart",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.padding()
                ) },
                label = { Text(text = "Scan", color = Color.White) },
                alwaysShowLabel = true
            )
            NavigationBarItem(
                selected = selectedItem.value == 1,
                onClick = {selectedItem.value = 1
                    val sendIntent = Intent().apply {
                        action=Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT,"Download app here: https://www.download.com")
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(sendIntent,null)
                    context.startActivity(shareIntent)},
                icon = { Icon(Icons.Filled.Share, contentDescription = "Share") },
                label = { Text(text = "Share",color = Color.White) },
                alwaysShowLabel = true
            )
            NavigationBarItem(
                selected = selectedItem.value == 2,
                onClick = {selectedItem.value = 2},
                icon = { Icon(Icons.Filled.Phone, contentDescription = "Phone") },
                label = { Text(text = "Phone",color = Color.White) },
                alwaysShowLabel = true
            )
        }},
        floatingActionButton = {
            FloatingActionButton(
                onClick = {navController.navigate(ROUTE_ANALYSIS) },
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_bar_chart_24),
                    contentDescription = "chart",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.padding()
                )
            }
        },
    )
    { innerPadding ->
        Box(modifier = Modifier.fillMaxHeight()) {
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = "background image",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.padding(innerPadding),
            )

        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
            // Center all children horizontally
        ) {
            TopAppBar(
                title = {      Text(
                    text = "Welcome to Budget App",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.Green,
                    fontFamily = FontFamily.SansSerif,
                    fontStyle = FontStyle.Normal,
                    textAlign = TextAlign.Center
                )},
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "Home"
                        )
                    }
                },
                actions = {

                    IconButton(
                        onClick = { expanded = true
                        navController.navigate(UPDATE_PROFILE)}) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Profile",
                            tint = Color.White
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {

                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF101924),
                    navigationIconContentColor = Color.White,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
            Spacer(modifier = Modifier.height(48.dp))

            Icon(
                Icons.Default.MailOutline,
                contentDescription = "Mail Icon",
                tint = Color.Gray,
                modifier = Modifier.size(64.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))



        }
    }
}



@Preview(showBackground = true , showSystemUi = true)
@Composable
fun HomeScreenPreview(){
    HomeScreen(rememberNavController())
}