package com.example.mindbodycountrylist.data.model

import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("ID")
    val id: Int = 0,
    @SerializedName("Name")
    val name: String = "",
    @SerializedName("Code")
    val code: String = "",
    @SerializedName("PhoneCode")
    val phoneCode: String = "",
    val countryFlagUrl: String = COUNTRY_FLAG_URL
) {
    companion object {
        const val COUNTRY_FLAG_URL =
            "https://raw.githubusercontent.com/hampusborgos/country-flags/main/png250px/"
    }
}