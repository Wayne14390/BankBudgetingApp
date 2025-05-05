package com.example.bankbudgetingapp.ui.theme.screens.home


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.bankbudgetingapp.R
import com.example.bankbudgetingapp.data.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val selectedItem = remember { mutableStateOf(0) }
    Scaffold(
        bottomBar = { NavigationBar(containerColor = Color.Green,){
            NavigationBarItem(
                selected = selectedItem.value == 0,
                onClick = {selectedItem.value = 0},
                icon = { Icon(Icons.Filled.Email, contentDescription = "Email") },
                label = { Text(text = "Email") },
                alwaysShowLabel = true
            )
            NavigationBarItem(
                selected = selectedItem.value == 1,
                onClick = {selectedItem.value = 1},
                icon = { Icon(Icons.Filled.Share, contentDescription = "Share") },
                label = { Text(text = "Share") },
                alwaysShowLabel = true
            )
            NavigationBarItem(
                selected = selectedItem.value == 2,
                onClick = {selectedItem.value = 2},
                icon = { Icon(Icons.Filled.Phone, contentDescription = "Phone") },
                label = { Text(text = "Phone") },
                alwaysShowLabel = true
            )
        } }
    )
    { innerPadding ->
        Box() {
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = "background image",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.padding(innerPadding)
            )

        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
            // Center all children horizontally
        ) {

            Text(
                text = "Welcome to Budget App",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(16.dp))
            LineChartView(
                modifier = Modifier
                    .fillMaxWidth(0.9f) // Optional: restrict width for better centering
            )
        }
    }
}



@Preview(showBackground = true , showSystemUi = true)
@Composable
fun HomeScreenPreview(){
    HomeScreen(rememberNavController())
}