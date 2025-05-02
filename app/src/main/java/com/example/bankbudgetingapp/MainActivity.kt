package com.example.bankbudgetingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.bankbudgetingapp.navigation.AppNavHost
import com.example.bankbudgetingapp.ui.theme.BankBudgetingAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BankBudgetingAppTheme {
                AppNavHost()
            }
        }
    }
}
