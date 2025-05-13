package com.example.bankbudgetingapp.data

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.bankbudgetingapp.models.BudgetModel
import com.example.bankbudgetingapp.navigation.VIEW_BUDGET
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class BudgetViewModel : ViewModel() {

    private val database = FirebaseDatabase.getInstance().reference.child("budgets")

    fun viewBudget(
        selectedBudget: MutableState<BudgetModel?>,
        budgets: SnapshotStateList<BudgetModel>,
        context: Context
    ) {
        viewModelScope.launch {
            try {
                // Simulate fetching budget data
                val budgetList = fetchBudgetsFromDatabase(context)

                // Update the budgets list
                budgets.clear()
                budgets.addAll(budgetList)

                // Reset selected budget
                selectedBudget.value = null
            } catch (e: Exception) {
                // Handle any errors (e.g., show a Toast message)
                Toast.makeText(context, "Failed to fetch budgets: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    // Simulated function to fetch budget data from a database
    private suspend fun fetchBudgetsFromDatabase(context: Context): List<BudgetModel> {
        // This simulates a delay for a real database/network operation
        delay(1000)

        // Return a sample list of budgets (replace with actual database query)
        return listOf(
            BudgetModel(budgetId = 1.toString(), budgetName = "Groceries", category = "Food", amount = 200.0, period = "Monthly"),
            BudgetModel(budgetId = 2.toString(), budgetName = "Rent", category = "Housing", amount = 800.0, period = "Monthly"),
            BudgetModel(budgetId = 3.toString(), budgetName = "Entertainment", category = "Leisure", amount = 150.0, period = "Monthly")
        )
    }
    fun updateBudget(context: Context, navController: NavController,
                     budgetId: String,budgetName: String ,category: String,
                     amount: Double, period: String, createdAt: Long){
        val databaseReference = FirebaseDatabase.getInstance()
            .getReference("budgets/$budgetId")
        val updatedBudget =BudgetModel(
            budgetId,budgetName,category,
            amount, period,createdAt
        )

        databaseReference.setValue(updatedBudget)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){

                    Toast.makeText(context,"Budget Updated Successfully", Toast.LENGTH_LONG).show()
                    navController.navigate(VIEW_BUDGET)
                }else{

                    Toast.makeText(context,"Budget update failed", Toast.LENGTH_LONG).show()
                }
            }
    }


    fun deleteBudget(context: Context,budgetId: String,
                      navController: NavController){
        AlertDialog.Builder(context)
            .setTitle("Delete Budget")
            .setMessage("Are you sure you want to delete this budget?")
            .setPositiveButton("Yes"){ _, _ ->
                val databaseReference = FirebaseDatabase.getInstance()
                    .getReference("budgets/$budgetId")
                databaseReference.removeValue().addOnCompleteListener {
                        task ->
                    if (task.isSuccessful){

                        Toast.makeText(context,"Budget deleted Successfully",Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(context,"Budget not deleted",Toast.LENGTH_LONG).show()
                    }
                }
            }
            .setNegativeButton("No"){ dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}