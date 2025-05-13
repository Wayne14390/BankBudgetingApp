package com.example.bankbudgetingapp.data

import android.widget.Toast
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.bankbudgetingapp.models.UserModel
import com.example.bankbudgetingapp.navigation.ROUTE_HOME
import com.example.bankbudgetingapp.navigation.ROUTE_LOGIN
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow

class AuthViewModel: ViewModel() {
    private val mAuth: FirebaseAuth =FirebaseAuth.getInstance()
    private val _isloading = MutableStateFlow(false)
    private val _errorMessage = MutableStateFlow<String?>(null)

    fun signup(fullname:String, email:String, password:String, navController: NavController, context: Context){
        if (fullname.isBlank() || email.isBlank() || password.isBlank()){
            Toast.makeText(context,"Please fill all the fields",Toast.LENGTH_LONG).show()

            return
        }
        _isloading.value = true

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _isloading.value = false
                if (task.isSuccessful){
                    val userId  = mAuth.currentUser?.uid ?: ""
                    val userData = UserModel(
                        fullname = fullname,email = email,password = password, userId = userId
                        )
                    saveUserToDatabase(userId,userData,navController,context)
                } else{
                    _errorMessage.value = task.exception?.message

                    Toast.makeText(context,"Registration failed",Toast.LENGTH_LONG).show()
                }
            }
    }
    fun saveUserToDatabase (userId:String, userData:UserModel, navController: NavController, context: Context){
        val regRef = FirebaseDatabase.getInstance().getReference("Users/$userId")
        regRef.setValue(userData).addOnCompleteListener { regRef ->
            if (regRef.isSuccessful){

                Toast.makeText(context,"Registration successful",Toast.LENGTH_LONG).show()
                navController.navigate(ROUTE_HOME)
            }else{
                _errorMessage.value = regRef.exception?.message

                Toast.makeText(context,"Database error",Toast.LENGTH_LONG).show()
            }
        }
    }
    fun login(email: String,password: String,navController: NavController,context: Context){
        if (email.isBlank() || password.isBlank()){

            Toast.makeText(context,"Email amd password required",Toast.LENGTH_LONG).show()
            return
        }
        _isloading.value = true

        mAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                _isloading.value=false
                if (task.isSuccessful){

                    Toast.makeText(context,"User Successfully logged in",Toast.LENGTH_LONG).show()
                    navController.navigate(ROUTE_HOME)
                }else{
                    _errorMessage.value=task.exception?.message

                    Toast.makeText(context,"Login failed",Toast.LENGTH_LONG).show()
                }
            }
    }
    fun logout(navController: NavController, context: Context) {
        // Sign out the user
        mAuth.signOut()

        // Show a confirmation message
        Toast.makeText(context, "Successfully logged out", Toast.LENGTH_LONG).show()

        // Navigate to login and clear all previous destinations
        navController.navigate(ROUTE_LOGIN) {
            popUpTo(0) { inclusive = true } // Clears the entire back stack
            launchSingleTop = true
        }
    }
    fun pushBudgetToFirebase(
        budgetName: String,
        selectedCategory: String,
        budgetAmount: String,
        budgetPeriod: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        val database = FirebaseDatabase.getInstance()
        val budgetRef = database.getReference("budgets")

        val budgetData = mapOf(
            "budgetName" to budgetName,
            "category" to selectedCategory,
            "amount" to budgetAmount,
            "period" to budgetPeriod
        )

        budgetRef.push().setValue(budgetData)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onError(exception)
            }
    }



}