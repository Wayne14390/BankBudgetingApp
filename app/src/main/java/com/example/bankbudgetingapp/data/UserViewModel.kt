package com.example.bankbudgetingapp.data

import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import android.app.AlertDialog
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation.NavController
import com.example.bankbudgetingapp.models.UserModel
import com.example.bankbudgetingapp.navigation.UPDATE_PROFILE
import com.example.bankbudgetingapp.network.ImgurService
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.mlkit.vision.barcode.common.Barcode.Email

class UserViewModel: ViewModel() {
    private val database = FirebaseDatabase.getInstance().reference.child("Users")

    private fun getImgurService(): ImgurService {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.imgur.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(ImgurService::class.java)
    }
    private fun getFileFromUri(context: Context, uri: Uri):
            File? {
        return try {
            val inputStream = context.contentResolver
                .openInputStream(uri)
            val file = File.createTempFile("temp_image", ".jpg", context.cacheDir)
            file.outputStream().use { output ->
                inputStream?.copyTo(output)
            }
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    fun uploadUserWithImage(
        uri: Uri,
        context: Context,
        fullname: String,
        email: String,
        password: String,
        navController: NavController
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val file = getFileFromUri(context, uri)
                if (file == null) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Failed to process image", Toast.LENGTH_SHORT).show()
                    }
                    return@launch
                }

                val reqFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                val body = MultipartBody.Part.createFormData("image", file.name, reqFile)

                val response = getImgurService().uploadImage(
                    body,
                    "Client-ID 66203b5a0595a51"
                )

                if (response.isSuccessful) {
                    val imageUrl = response.body()?.data?.link ?: ""

                    val userId = database.push().key ?: ""
                    val user = UserModel(
                        fullname,email,password,imageUrl, userId
                    )
                    database.child(userId).setValue(user)

                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Upload error", Toast.LENGTH_SHORT).show()
                    }
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Exception: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
//    fun updateUser(context: Context, navController: NavController,
//                      fullname: String,email: Email,password: String,){
//        val databaseReference = FirebaseDatabase.getInstance()
//            .getReference("User/$userId")
//        val updatedUser = UserModel(
//            fullname,email,password, "", userId
//        )
//
//        databaseReference.setValue(updatedUser)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful){
//
//                    Toast.makeText(context,"User Updated Successfully", Toast.LENGTH_LONG).show()
//                    navController.navigate(UPDATE_PROFILE)
//                }else{
//
//                    Toast.makeText(context,"User update failed", Toast.LENGTH_LONG).show()
//                }
//            }
//    }


    fun deleteStudent(context: Context,studentId: String,
                      navController: NavController){
        AlertDialog.Builder(context)
            .setTitle("Delete Student")
            .setMessage("Are you sure you want to delete this student?")
            .setPositiveButton("Yes"){ _, _ ->
                val databaseReference = FirebaseDatabase.getInstance()
                    .getReference("Students/$studentId")
                databaseReference.removeValue().addOnCompleteListener {
                        task ->
                    if (task.isSuccessful){

                        Toast.makeText(context,"Student deleted Successfully",Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(context,"Student not deleted",Toast.LENGTH_LONG).show()
                    }
                }
            }
            .setNegativeButton("No"){ dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}