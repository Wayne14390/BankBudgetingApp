package com.example.bankbudgetingapp.ui.theme.screens.viewbudget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.bankbudgetingapp.data.BudgetViewModel
import com.example.bankbudgetingapp.navigation.ADD_BUDGET
import java.text.SimpleDateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewBudgetScreen(navController: NavController, viewModel: BudgetViewModel = viewModel()) {
    // Observe budgets LiveData as a state
    val budgets by viewModel.budgets.observeAsState(emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Budget", style = MaterialTheme.typography.titleMedium) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) { // Navigate back to the previous screen
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
                    // Navigate to the "Add Budget" screen
                    navController.navigate(ADD_BUDGET)
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Budget"
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End // Place FAB at the bottom-right corner
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (budgets.isEmpty()) {
                // Show a placeholder if no budgets exist
                Text(
                    text = "Your Budgets Will Appear Here",
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                // Show a list of budgets
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(budgets) { budget -> // Correctly iterate over individual budget items
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(text = "Name: ${budget.name}", style = MaterialTheme.typography.titleSmall) // Use individual budget.name
                                Text(text = "Category: ${budget.category}", style = MaterialTheme.typography.bodySmall)
                                Text(text = "Amount: \$${budget.amount}", style = MaterialTheme.typography.bodySmall)
                                Text(text = "Period: ${budget.period}", style = MaterialTheme.typography.bodySmall)
                                Text(
                                    text = "Created At: ${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(budget.createdAt)}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ViewBudgetScreenPreview() {
    ViewBudgetScreen(rememberNavController())
}