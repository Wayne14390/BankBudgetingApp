package com.example.bankbudgetingapp.data

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.bankbudgetingapp.models.BudgetModel
import com.example.bankbudgetingapp.navigation.VIEW_BUDGET
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class BudgetViewModel : ViewModel() {

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val budgetRef: DatabaseReference = database.getReference("budgets")

    fun viewBudgets(
        budgets: SnapshotStateList<BudgetModel>,
        context: Context
    ) {
        budgetRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    budgets.clear()
                    for (snap in snapshot.children) {
                        snap.getValue(BudgetModel::class.java)?.let {
                            budgets.add(it)
                        }
                    }
                } catch (e: Exception) {
                    Log.e("FirebaseError", "Error parsing budget data", e)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseError", "Failed to fetch budgets: ${error.message}")
            }
        })
    }

    fun updateBudget(
        context: Context,
        navController: NavController,
        budget: BudgetModel
    ) {
        val databaseReference = budgetRef.child(budget.budgetId)
        databaseReference.setValue(budget)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Budget Updated Successfully", Toast.LENGTH_LONG).show()
                    navController.navigate(VIEW_BUDGET)
                } else {
                    Toast.makeText(context, "Budget update failed", Toast.LENGTH_LONG).show()
                }
            }
    }

    fun deleteBudget(
        context: Context,
        budgetId: String,
        navController: NavController
    ) {
        AlertDialog.Builder(context)
            .setTitle("Delete Budget")
            .setMessage("Are you sure you want to delete this budget?")
            .setPositiveButton("Yes") { _, _ ->
                budgetRef.child(budgetId).removeValue()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(context, "Budget deleted Successfully", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(context, "Budget not deleted", Toast.LENGTH_LONG).show()
                        }
                    }
            }
            .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}