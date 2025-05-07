package com.example.bankbudgetingapp.models

data class Budget(
    val name: String,
    val amount: Double,
    val type: String, // "Expense" or "Income" //
     val category: String,
     val period: String
)
