package com.example.mindbodycountrylist.data.repository

import com.example.mindbodycountrylist.data.api.APIService
import com.example.mindbodycountrylist.utils.SafeApiRequest
import javax.inject.Inject

class CountriesRepositoryImpl @Inject constructor(val apiService: APIService) : SafeApiRequest() {
    suspend fun getCountries() = apiRequest {
        apiService.getCountries()
    }
}