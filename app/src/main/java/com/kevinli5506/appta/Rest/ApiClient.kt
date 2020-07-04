package com.kevinli5506.appta.Rest

import android.content.Context
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private lateinit var apiService: ApiService
    fun getApiService(context: Context):ApiService{
        if(!::apiService.isInitialized){
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .client(okhttpClient((context)))
                .build()
            apiService = retrofit.create(ApiService::class.java)
        }
        return apiService
    }
    private fun okhttpClient(context: Context): OkHttpClient {
        val logging = HttpLoggingInterceptor(object :HttpLoggingInterceptor.Logger{
            override fun log(message: String?) {
                Log.d("header",message)
            }

        })
        logging.level = (HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .addInterceptor(logging)
            .build()
    }
}