package com.example.bankbudgetingapp.models

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


data class UserModel(
    val fullname: String = "",
    val email: String = "",
    val password: String = "", // Stored securely, not fetched or displayed
    val imageUrl: String = "",
    val userId: String = ""
)

