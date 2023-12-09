package com.findme.app.services

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ApiManager {
    private val BASE_URL = "https://ca97-152-58-111-226.ngrok-free.app"

    // API response interceptor
    val loggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    // Client with logging interceptor and extended timeouts
    val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(180, TimeUnit.SECONDS) // Adjust the timeout as needed
        .readTimeout(180, TimeUnit.SECONDS)    // Adjust the timeout as needed
        .writeTimeout(180, TimeUnit.SECONDS)   // Adjust the timeout as needed
        .build()

    var gson = GsonBuilder()
        .setLenient()
        .create()

    val apiInterface: ApiInterface by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiInterface::class.java)
    }

    fun getImageUrl(imagePath: String): String {
        return BASE_URL + imagePath
    }
}

