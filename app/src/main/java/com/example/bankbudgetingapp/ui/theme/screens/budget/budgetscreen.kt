package com.example.bankbudgetingapp.ui.theme.screens.budget

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.bankbudgetingapp.ui.theme.screens.home.HomeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetScreen(navController: NavController){

}
@Preview(showBackground = true , showSystemUi = true)
@Composable
fun BudgetScreenPreview(){
    BudgetScreen(rememberNavController())
}