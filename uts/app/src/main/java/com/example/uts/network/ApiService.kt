package com.example.uts.network

import com.example.uts.userHelper.ResponseUser
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("api/users")
    fun getListUser(@Query("page") page:String): Call<ResponseUser>

    @GET("api/users/{id}")
    fun getUser(@Path("id") id: String): Call<ResponseUser>

    @FormUrlEncoded
    @POST("api/users")
    fun createUser(
        @Field("name") name: String,
    ): Call<ResponseUser>

    @Multipart
    @PUT("api/uploadfile")
    fun updateUser(
        @Part("file") file: MultipartBody.Part,
        @PartMap data: Map<String, RequestBody>
    ): Call<ResponseUser>
}