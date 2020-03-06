package com.ne.paypasssample

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitBuilder {

   fun <T> create(type: Class<T>, baseUrl:String): T{

       val loggingInterceptor = HttpLoggingInterceptor()
       loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

       val httpClient = OkHttpClient.Builder()
               .connectTimeout(30, TimeUnit.SECONDS)
               .readTimeout(30, TimeUnit.SECONDS)
               .addInterceptor(loggingInterceptor).build()

       val retrofit: Retrofit = Retrofit.Builder()
               .baseUrl(baseUrl)
               .client(httpClient)
               .addConverterFactory(GsonConverterFactory.create())
               .build()

       return retrofit.create(type)
   }
}