package com.example.bankbudgetingapp.ui.theme.screens.budget

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController

import androidx.navigation.compose.rememberNavController
import com.example.bankbudgetingapp.data.AuthViewModel
import com.example.bankbudgetingapp.data.BudgetViewModel
import com.google.firebase.database.FirebaseDatabase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateBudgetScreen(
    navController: NavController,
    budgetId: String
) {
    val context = LocalContext.current
    val budgetViewModel: BudgetViewModel = viewModel()
    val currentDataRef  = FirebaseDatabase.getInstance()
        .getReference().child("budgets/$budgetId")

    // State variables for form inputs
    val name = remember { mutableStateOf("") }
    val category = remember { mutableStateOf("") }
    val amount = remember { mutableStateOf("") }
    val period = remember { mutableStateOf("") }

    // Simulating a fetch for existing budget details (dummy data for now)
    LaunchedEffect(budgetId) {
        // Here, fetch the budget details using `budgetId` if required
        // For now, populate state with dummy data
        name.value = "Sample Budget"
        category.value = "Food"
        amount.value = "100.0"
        period.value = "Monthly"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Update Budget", style = MaterialTheme.typography.titleMedium) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Input fields
            OutlinedTextField(
                value = name.value,
                onValueChange = { name.value = it },
                label = { Text("Budget Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = category.value,
                onValueChange = { category.value = it },
                label = { Text("Category") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = amount.value,
                onValueChange = { amount.value = it },
                label = { Text("Amount") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = period.value,
                onValueChange = { period.value = it },
                label = { Text("Period") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Submit button
            Button(
                onClick = {
                    val budgetRepository = BudgetViewModel()
                        budgetRepository.updateBudget(
                            context = context,
                            navController = navController,
                            budgetId = budgetId,
                            budgetName = name.value,
                            category = category.value,
                            amount = amount.value.toDouble(),
                            period = period.value,
                            createdAt = System.currentTimeMillis()
                        )

                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Update Budget")
            }
        }
    }
}