package com.example.bookapp.data.remote

import com.example.bookapp.BuildConfig
import com.example.bookapp.data.model.Book
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// ApiService.kt
interface ApiService {
    @GET("getBooks")
    suspend fun getBooks(): Response<List<Book>>
}

// RetrofitInstance.kt
object RetrofitInstance {

    private val apiKeyInterceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("x-rapidapi-key", "99ce56a1a0msha23dcce4e8f1a70p17f625jsn0e0d7f49bc37") // Заміни на свій API-ключ, якщо потрібно
            .addHeader("x-rapidapi-host", "all-books-api.p.rapidapi.com")
            .build()
        chain.proceed(request)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(apiKeyInterceptor)
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://all-books-api.p.rapidapi.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
