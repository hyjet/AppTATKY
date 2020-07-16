package com.kevinli5506.appta.Rest

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private lateinit var apiService: ApiService
    fun getApiService(context: Context):ApiService{
        if(!::apiService.isInitialized){
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
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
            .addInterceptor(NoConnectionInterceptor(context))
            .build()
    }


}