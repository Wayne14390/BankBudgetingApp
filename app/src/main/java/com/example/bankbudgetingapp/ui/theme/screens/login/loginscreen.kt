package com.example.bankbudgetingapp.ui.theme.screens.login

import androidx.compose.foundation.background
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.bankbudgetingapp.data.AuthViewModel
import com.example.bankbudgetingapp.R
import com.example.bankbudgetingapp.navigation.ROUTE_REGISTER

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController,viewModel: AuthViewModel = viewModel()){
    var email by remember { mutableStateOf(value = "") }
    var password by remember { mutableStateOf(value = "") }
    val authViewModel:AuthViewModel= viewModel()
    val context = LocalContext.current
    val passwordVisible by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }
    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101D3D)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .size(600.dp) // Square shape (adjust as needed)
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column (modifier = Modifier.fillMaxHeight().fillMaxWidth()) {
                TopAppBar(
                    title = { Text(text = "") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    } },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        navigationIconContentColor = Color.Blue,
                        titleContentColor = Color.Black,
                        actionIconContentColor = Color.Blue
                    )

                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.logo),
                        contentDescription = "logo",
                        modifier = Modifier
                            .size(100.dp) // smaller logo
                            .padding(end = 8.dp)
                    )
                    Column() {
                        Text(
                            text = "Login to your",
                            fontSize = 24.sp,
                            color = Color.Blue,
                            fontFamily = FontFamily.SansSerif,
                            fontStyle = FontStyle.Normal,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "BBA Account",
                            fontSize = 24.sp,
                            color = Color.Blue,
                            fontFamily = FontFamily.SansSerif,
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { newEmail -> email = newEmail },
                    label = { Text(text = "Enter your email") },
                    placeholder = { Text(text = "Please enter email") },
                    modifier = Modifier.wrapContentWidth().align(Alignment.CenterHorizontally),
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Email") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { newPassword -> password = newPassword },
                    label = { Text(text = "Enter your password") },
                    placeholder = { Text(text = "Please enter password") },
                    modifier = Modifier.wrapContentWidth().align(Alignment.CenterHorizontally),
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Password") },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),

                    )
                Spacer(modifier = Modifier.height(8.dp))
                Button (onClick = {
                    authViewModel.login(email,password,navController, context)
                }, modifier = Modifier.fillMaxWidth().width(150.dp), colors = ButtonDefaults.buttonColors(Color.Green)) { Text(text = "Login") }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = buildAnnotatedString { append("If you don't have an account,register here") }, modifier = Modifier.wrapContentWidth().align(
                    Alignment.CenterHorizontally).clickable {
                    navController.navigate(ROUTE_REGISTER)
                })
                
            }
        }
    }

}

@Preview(showBackground = true , showSystemUi = true)
@Composable
fun LoginScreenPreview(){
    LoginScreen(rememberNavController())
}
