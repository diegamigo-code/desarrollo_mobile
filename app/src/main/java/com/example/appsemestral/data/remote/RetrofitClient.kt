package com.example.appsemestral.data.remote

import okhttp3.OkHttpClient
import com.example.appsemestral.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiConfig {
    /**
     * Emulador: 10.0.2.2 es el "localhost" del computador.
     * Dispositivo físico: usa la IP del computador en la red Wi-Fi, ej. http://192.168.1.50:8080/api/
     * La URL SIEMPRE termina en "/".
     */
    val BASE_URL = BuildConfig.WIM_API_URL
    const val RANDOM_USER_URL = "https://randomuser.me/"
}

object RetrofitClient {

    private fun clienteBase(): OkHttpClient.Builder = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)

    /** Backend propio: lleva el JWT de la sesión en cada llamada. */
    private val okHttpWim = clienteBase()
        .addInterceptor(AuthInterceptor())
        .build()

    /** API externa: nunca se le envía el token. */
    private val okHttpExterno = clienteBase().build()

    private fun retrofit(baseUrl: String, cliente: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(cliente)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val wimApi: WimApi by lazy { retrofit(ApiConfig.BASE_URL, okHttpWim).create(WimApi::class.java) }

    val randomUserApi: RandomUserApi by lazy {
        retrofit(ApiConfig.RANDOM_USER_URL, okHttpExterno).create(RandomUserApi::class.java)
    }
}
