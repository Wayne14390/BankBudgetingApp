package com.example.bankbudgetingapp.network

import com.example.bankbudgetingapp.models.ImgurResponse
import retrofit2.Response
import okhttp3.MultipartBody
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImgurService{
    @Multipart
    @POST("3/image")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part,
        @Header("Authorization")auth:String
    ):Response<ImgurResponse>
}