package com.example.challengechap5.model

import com.google.gson.annotations.SerializedName

data class ResponseDataUserItem(
    @SerializedName("createAt")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("username")
    val username: String
)