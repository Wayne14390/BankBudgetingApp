package com.example.bankbudgetingapp.data

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.bankbudgetingapp.models.BudgetModel
import com.example.bankbudgetingapp.navigation.VIEW_BUDGET
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class BudgetViewModel : ViewModel() {

    private val database = FirebaseDatabase.getInstance().reference.child("budgets")

    fun viewBudget(
        budget: MutableState<BudgetModel?>,
        budgets: SnapshotStateList<BudgetModel>,
        context: Context
    ): SnapshotStateList<BudgetModel> {
        val ref = FirebaseDatabase.getInstance().getReference("budgets")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                budgets.clear()
                for (snap in snapshot.children) {
                    val value = snap.getValue(BudgetModel::class.java)
                    value?.let {
                        budgets.add(it)
                    }
                }
                if (budgets.isNotEmpty()) {
                    budget.value = budgets.first()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    context,
                    "Failed to fetch budgets: ${error.message}",
                    Toast.LENGTH_SHORT
                ).show()

            }
        })

        return budgets

    }
    fun updateBudget(context: Context, navController: NavController,
                     budgetId: String,budgetName: String ,selectedCategory: String,
                     budgetAmount: Double, budgetPeriod: String, createdAt: Long){
        val databaseReference = FirebaseDatabase.getInstance()
            .getReference("budgets/$budgetId")
        val updatedBudget =BudgetModel(
            budgetId,budgetName,selectedCategory,
            budgetAmount, budgetPeriod,createdAt
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