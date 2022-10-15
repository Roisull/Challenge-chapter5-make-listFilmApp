package com.example.challengechap5.network

import com.example.challengechap5.model.DataContent
import com.example.challengechap5.model.PutResponseContent
import com.example.challengechap5.model.ResponseDataContentItem
import com.example.challengechap5.model.ResponseDataUserItem
import retrofit2.http.*
import retrofit2.Call

interface RetrofitService {
    @GET("user")
    fun getAllUser(): Call<List<ResponseDataUserItem>>

    @POST("user")
    fun postUser(@Body user: ResponseDataUserItem): Call<ResponseDataUserItem>

    // REST Content
    @GET("content")
    fun getAllContent() : Call<List<ResponseDataContentItem>>

    @POST("content")
    fun addContent(@Body content: DataContent): Call<ResponseDataContentItem>

    @PUT("content/{id}")
    fun updateContent(@Path("id") id: Int, @Body request: DataContent): Call<List<PutResponseContent>>

    @DELETE("content/{id}")
    fun deleteContent(@Path("id")id : Int) : Call<Int>
}