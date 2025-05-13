package com.example.bankbudgetingapp.models

data class BudgetModel(
    val budgetId: String = "",
    val budgetName: String = "",
    val category: String = "",
    val amount: Double = 0.0,
    val period: String = "",
    val createdAt: Long = System.currentTimeMillis()
)