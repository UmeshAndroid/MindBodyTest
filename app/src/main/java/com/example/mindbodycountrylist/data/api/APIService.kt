package com.example.mindbodycountrylist.data.api

import com.example.mindbodycountrylist.data.model.Item
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {
    @GET("rest/worldregions/country")
    suspend fun getCountries(): Response<List<Item>>

    @GET("rest/worldregions/country/{id}/province")
    suspend fun getCountryDetails(@Path("id") id : Int):  Response<List<Item>>
}