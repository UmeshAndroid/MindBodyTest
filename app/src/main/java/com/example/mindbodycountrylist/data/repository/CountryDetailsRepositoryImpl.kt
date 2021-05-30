package com.example.mindbodycountrylist.data.repository

import com.example.mindbodycountrylist.utils.SafeApiRequest
import com.example.mindbodycountrylist.data.api.APIService
import javax.inject.Inject

class CountryDetailsRepositoryImpl @Inject constructor(val apiService: APIService) :
    SafeApiRequest() {
    suspend fun getCountryDetails(countryId: Int) = apiRequest {
        apiService.getCountryDetails(countryId)
    }
}