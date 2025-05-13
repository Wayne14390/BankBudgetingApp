package com.example.bankbudgetingapp.models

data class BudgetModel(
    val budgetId: String = "",
    val budgetName: String = "",
    val selectedCategory: String = "",
    val budgetAmount: Double = 0.0,
    val budgetPeriod: String = "",
    val createdAt: Long = System.currentTimeMillis()
)