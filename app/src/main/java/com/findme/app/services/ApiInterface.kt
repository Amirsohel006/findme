package com.findme.app.services

import com.findme.app.ImageResponse
import com.findme.app.responses.SimilarImage
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiInterface {

@Multipart
    @POST("api/image-search/")
    fun signUp(@Part("event")event:Int,
               @Part file: MultipartBody.Part)
            : Call<SimilarImage>
    //,@Part("profile_picture") profile_picture: Part?




}