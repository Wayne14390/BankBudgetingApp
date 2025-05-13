package com.example.bankbudgetingapp.data

import com.example.bankbudgetingapp.models.Transaction
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

fun fetchTransactionsByCategory(
    email: String,
    categories: List<String>,
    onResult: (Map<String, Double>) -> Unit,
    onError: (Exception) -> Unit
) {
    val database = FirebaseDatabase.getInstance().reference.child("transactions")
    val sanitizedEmail = email.replace(".", ",") // Firebase keys can't have periods

    database.child(sanitizedEmail).addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val categoryTotals = mutableMapOf<String, Double>()

            for (transactionSnapshot in snapshot.children) {
                val transaction = transactionSnapshot.getValue(Transaction::class.java)
                if (transaction != null && categories.contains(transaction.category)) {
                    categoryTotals[transaction.category] =
                        categoryTotals.getOrDefault(transaction.category, 0.0) + transaction.amount
                }
            }

            onResult(categoryTotals)
        }

        override fun onCancelled(error: DatabaseError) {
            onError(error.toException())
        }
    })
}