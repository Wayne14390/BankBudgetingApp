package com.example.bankbudgetingapp.models

data class Transaction(
    val category: String = "",
    val amount: Double = 0.0,
    val date: String = ""
)
