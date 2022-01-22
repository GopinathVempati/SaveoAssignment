package com.saveo.network

import com.google.gson.GsonBuilder
import com.saveo.BuildConfig
import com.saveo.utils.Constants
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object ApiClient {

    private val webServices: WebServices

    private var okHttpClient: OkHttpClient

    init {
        val okHttpBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            okHttpBuilder.addInterceptor(logging)
        }

        val httpClientBuilder = okHttpBuilder
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .connectTimeout(120, TimeUnit.SECONDS)
            .addInterceptor(SecurityInterceptor())


        okHttpClient = httpClientBuilder.build()

        val gsonBuilder = GsonBuilder()
        val gson = gsonBuilder.create()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

        webServices = retrofit.create(WebServices::class.java)
    }

    class SecurityInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val mainRequest = chain.request()
            val builder = mainRequest.newBuilder()
            return chain.proceed(builder.build())
        }
    }

    fun getService(): WebServices = webServices
}