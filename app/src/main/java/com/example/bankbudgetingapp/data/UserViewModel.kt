package com.example.bankbudgetingapp.data

import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import android.app.AlertDialog
import android.widget.Toast
import android.content.Context
import androidx.navigation.NavController
import com.example.bankbudgetingapp.models.UserModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.bankbudgetingapp.navigation.VIEW_PROFILE

class UserViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance().reference.child("Users")

    private val _userData = MutableStateFlow<UserModel?>(null)
    val userData: StateFlow<UserModel?> get() = _userData

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    /**
     * Fetches the current authenticated user's data.
     */
    fun fetchUserData() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            _isLoading.value = true
            database.child(userId).get().addOnSuccessListener { snapshot ->
                _userData.value = snapshot.getValue(UserModel::class.java)
                _isLoading.value = false
            }.addOnFailureListener {
                _isLoading.value = false
                it.printStackTrace()
            }
        }
    }

    /**
     * Updates the current user's data (excluding the password).
     */
    fun updateUser(
        context: Context,
        navController: NavController,
        fullname: String,
        email: String,
        imageUrl: String = ""
    ) {
        val userId = auth.currentUser?.uid ?: return
        val updatedUser = UserModel(
            fullname = fullname,
            email = email,
            imageUrl = imageUrl,
            userId = userId
        )

        database.child(userId).setValue(updatedUser)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                    navController.navigate(VIEW_PROFILE) // Replace "profile" with your desired route
                } else {
                    Toast.makeText(context, "Profile update failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    /**
     * Deletes the current user's account.
     */
    fun deleteUser(
        context: Context,
        navController: NavController
    ) {
        val userId = auth.currentUser?.uid ?: return
        AlertDialog.Builder(context)
            .setTitle("Delete Account")
            .setMessage("Are you sure you want to delete your account?")
            .setPositiveButton("Yes") { _, _ ->
                // Delete user from Firebase Database
                database.child(userId).removeValue().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Optionally, log the user out
                        auth.signOut()
                        Toast.makeText(context, "Account deleted successfully", Toast.LENGTH_LONG).show()
                        navController.navigate("login") // Replace "login" with your desired route
                    } else {
                        Toast.makeText(context, "Failed to delete account", Toast.LENGTH_LONG).show()
                    }
                }
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}