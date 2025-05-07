package com.example.bankbudgetingapp.ui.theme.screens.home


import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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
import com.example.bankbudgetingapp.navigation.ROUTE_BUDGET
import com.example.bankbudgetingapp.navigation.UPDATE_PROFILE
import com.example.bankbudgetingapp.ui.theme.screens.scanning.ScannerActivity
import com.google.android.libraries.places.api.model.Money
import com.google.androidgamesdk.gametextinput.Settings
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val selectedItem = remember { mutableStateOf(0) }
    val context = LocalContext.current
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed) // State for the navigation drawer
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(200.dp), // Set the width of the sliding menu
                drawerContainerColor = Color(0xFF101924), // Background color of the drawer
                drawerContentColor = Color.White // Text and icon color in the drawer
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Menu",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
                Divider(color = Color.Gray)

                // Drawer items
                NavigationDrawerItem(
                    label = { Text("Expenses") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() } // Close the drawer
                        navController.navigate("expenses") // Navigate to Expenses screen
                    },
                    icon = {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_money_24),
                            contentDescription = "Expenses",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier.padding(),
                        )
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )

                NavigationDrawerItem(
                    label = { Text("Income") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() } // Close the drawer
                        navController.navigate("income") // Navigate to Income screen
                    },
                    icon = {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_attach_money_24),
                            contentDescription = "Income",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier.padding(),
                        )
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )

                NavigationDrawerItem(
                    label = { Text("Settings") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() } // Close the drawer
                        navController.navigate("settings") // Navigate to Settings screen
                    },
                    icon = {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_settings_24),
                            contentDescription = "Settings",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier.padding(),
                        )
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Budget", // Changed the text from "Welcome to Budget App" to "Budget"
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.Green,
                            fontFamily = FontFamily.SansSerif,
                            fontStyle = FontStyle.Normal,
                            textAlign = TextAlign.Center
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) { // Open the drawer
                            Icon(
                                imageVector = Icons.Filled.Menu, // Menu icon
                                contentDescription = "Menu",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF673AB7),
                        navigationIconContentColor = Color.White,
                        titleContentColor = Color.White,
                        actionIconContentColor = Color.White
                    )
                )
            },
            bottomBar = {
                BottomAppBar(
                    containerColor = Color(0xFF673AB7) // Background color of the bottom bar
                ) {
                    NavigationBarItem(
                        selected = selectedItem.value == 1,
                        onClick = {
                            selectedItem.value = 1
                            val sendIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, "Download app here: https://www.download.com")
                                type = "text/plain"
                            }
                            val shareIntent = Intent.createChooser(sendIntent, null)
                            context.startActivity(shareIntent)
                        },
                        icon = { Icon(Icons.Filled.Share, contentDescription = "Share", tint = Color.White) },
                        label = { Text(text = "Share", color = Color.White) },
                        alwaysShowLabel = true
                    )
                    NavigationBarItem(
                        selected = selectedItem.value == 1,
                        onClick = {selectedItem.value = 1
                                  navController.navigate(ROUTE_BUDGET)},
                        icon = { Icon(Icons.Filled.Create, contentDescription = "Share",tint = Color.White) },
                        label = { Text(text = "Budget",color = Color.White) },
                        alwaysShowLabel = true
                    )

                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        // Code to invoke scanner functionality
                        val scannerIntent = Intent(context, ScannerActivity::class.java)
                        context.startActivity(scannerIntent)
                    },
                    shape = CircleShape, // Ensure the FAB is circular
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_qr_code_scanner_24),
                        contentDescription = "Scan",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.padding(8.dp),
                        colorFilter = ColorFilter.tint(Color.White) // Ensure the icon is white
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.Center // Position the FAB at the center of the bottom bar
        ) { innerPadding ->
            Box(modifier = Modifier.fillMaxHeight()) {
                Image(
                    painter = painterResource(id = R.drawable.background),
                    contentDescription = "background image",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.padding(innerPadding),
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(rememberNavController())
}