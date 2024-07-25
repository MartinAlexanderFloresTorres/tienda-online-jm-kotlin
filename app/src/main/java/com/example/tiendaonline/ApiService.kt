package com.example.tiendaonline

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("productos")
    suspend fun getProducts(@Query("page") page: Int): ProductResponse

    @GET("colecciones")
    suspend fun getColecciones(): List<Category>

    @GET("productos/search/producto")
    suspend fun searchProducts(
        @Query("q") query: String,
        @Query("page") page: Int
    ): ProductResponse

    @GET("productos/{slug}")
    suspend fun getProductById(@Path("slug") id: String): ProductBySlug
}
