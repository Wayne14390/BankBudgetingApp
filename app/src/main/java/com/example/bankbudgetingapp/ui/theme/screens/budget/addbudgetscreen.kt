package com.example.bankbudgetingapp.ui.theme.screens.budget

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.bankbudgetingapp.data.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBudgetScreen(
    navController: NavController,
    viewModel: AuthViewModel = viewModel()
) {
    // State to hold the budget details
    val budgetName = remember { mutableStateOf("") }
    val selectedCategory = remember { mutableStateOf("") }
    val showCategoryDialog = remember { mutableStateOf(false) }
    val categories = listOf("Food and Drink", "Shopping", "Transport", "Entertainment", "Education")
    val budgetAmount = remember { mutableStateOf("") }
    val budgetPeriod = remember { mutableStateOf("") }
    val periods = listOf("Weekly", "Monthly", "Yearly")
    val showPeriodDialog = remember { mutableStateOf(false) }

    // Context for Toast messages
    val context = LocalContext.current
    val authViewModel: AuthViewModel= viewModel()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Add Budget", style = MaterialTheme.typography.titleMedium) },
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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // Validate inputs
                    if (budgetName.value.isNotEmpty() &&
                        selectedCategory.value.isNotEmpty() &&
                        budgetAmount.value.isNotEmpty() &&
                        budgetPeriod.value.isNotEmpty()
                    ) {
                        // Call the function from AuthViewModel
                        viewModel.pushBudgetToFirebase(
                            budgetName = budgetName.value,
                            selectedCategory = selectedCategory.value,
                            budgetAmount = budgetAmount.value,
                            budgetPeriod = budgetPeriod.value,
                            onSuccess = {
                                // Show a success message and navigate back
                                Toast.makeText(context, "Budget added successfully!", Toast.LENGTH_SHORT).show()
                                navController.popBackStack() // Navigate back to the previous screen
                            },
                            onError = { exception ->
                                // Show an error message if something goes wrong
                                Toast.makeText(context, "Error: ${exception.message}", Toast.LENGTH_SHORT).show()
                            }
                        )
                    } else {
                        // Show a message if any field is empty
                        Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                    }

                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Save Budget"
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Budget Name Input
            OutlinedTextField(
                value = budgetName.value,
                onValueChange = { budgetName.value = it },
                label = { Text(text = "Budget Name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Category Selector
            Button(
                onClick = { showCategoryDialog.value = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = if (selectedCategory.value.isEmpty()) "Select Category" else selectedCategory.value)
            }

            // Category Dialog
            if (showCategoryDialog.value) {
                AlertDialog(
                    onDismissRequest = { showCategoryDialog.value = false },
                    title = { Text(text = "Select Category") },
                    text = {
                        Column {
                            categories.forEach { category ->
                                Text(
                                    text = category,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            selectedCategory.value = category
                                            showCategoryDialog.value = false
                                        }
                                        .padding(8.dp)
                                )
                            }
                        }
                    },
                    confirmButton = {}
                )
            }

            // Budget Amount Input
            OutlinedTextField(
                value = budgetAmount.value,
                onValueChange = { budgetAmount.value = it },
                label = { Text(text = "Budget Amount") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Budget Period Selector
            Button(
                onClick = { showPeriodDialog.value = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = if (budgetPeriod.value.isEmpty()) "Select Period" else budgetPeriod.value)
            }

            // Period Dialog
            if (showPeriodDialog.value) {
                AlertDialog(
                    onDismissRequest = { showPeriodDialog.value = false },
                    title = { Text(text = "Select Budget Period") },
                    text = {
                        Column {
                            periods.forEach { period ->
                                Text(
                                    text = period,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            budgetPeriod.value = period
                                            showPeriodDialog.value = false
                                        }
                                        .padding(8.dp)
                                )
                            }
                        }
                    },
                    confirmButton = {}
                )
            }
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddBudgetScreenPreview() {
    AddBudgetScreen(rememberNavController())
}