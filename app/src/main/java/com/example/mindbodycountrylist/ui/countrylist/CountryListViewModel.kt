package com.example.mindbodycountrylist.ui.countrylist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindbodycountrylist.data.repository.CountriesRepositoryImpl
import com.example.mindbodycountrylist.data.model.Item
import com.example.mindbodycountrylist.utils.APIException
import com.example.mindbodycountrylist.utils.NoInternetException
import com.example.mindbodycountrylist.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
This  class is responsible for fetching country list
and handle api call
 */
@HiltViewModel
class CountryListViewModel @Inject constructor(
    private val countriesRepositoryImpl: CountriesRepositoryImpl
    ) : ViewModel() {
    private val _countries = MutableLiveData<Resource<List<Item>>>()
    val countries: LiveData<Resource<List<Item>>>
        get() = _countries

    fun fetchCountries() {
        if(! _countries.value?.data.isNullOrEmpty()){
            return
        }
        _countries.postValue(Resource.loading(data = null))
        viewModelScope.launch {
            try {
                val data = countriesRepositoryImpl.getCountries()
                _countries.postValue(Resource.success(data))
            } catch (e: APIException) {
                _countries.postValue(Resource.error(data = null, msg = e.message!!))
            } catch (e: NoInternetException) {
                _countries.postValue((Resource.error(data = null, msg = e.message!!)))
            } catch (e: Exception) {
                _countries.postValue(Resource.error(e.toString(), null))
            }
        }
    }
}