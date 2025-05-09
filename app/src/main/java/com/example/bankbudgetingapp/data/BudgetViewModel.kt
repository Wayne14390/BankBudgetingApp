package com.example.bankbudgetingapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bankbudgetingapp.models.BudgetModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class BudgetViewModel : ViewModel() {

    // LiveData to hold the list of budgets
    private val _budgets = MutableLiveData<List<BudgetModel>>()
    val budgets: LiveData<List<BudgetModel>> = _budgets

    init {
        fetchBudgetsFromFirebase()
    }

    private fun fetchBudgetsFromFirebase() {
        val database = FirebaseDatabase.getInstance()
        val budgetRef = database.getReference("budgets")

        budgetRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val budgetList = mutableListOf<BudgetModel>()
                for (childSnapshot in snapshot.children) {
                    val budget = childSnapshot.getValue(BudgetModel::class.java)
                    budget?.let { budgetList.add(it) }
                }
                _budgets.value = budgetList
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
                _budgets.value = emptyList() // Set an empty list in case of error
            }
        })
    }
}