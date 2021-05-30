package com.example.mindbodycountrylist.ui.countrydetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindbodycountrylist.data.model.Item
import com.example.mindbodycountrylist.data.repository.CountryDetailsRepositoryImpl
import com.example.mindbodycountrylist.utils.APIException
import com.example.mindbodycountrylist.utils.NoInternetException
import com.example.mindbodycountrylist.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
This  class is responsible for fetching country province
and handle api call
 */
@HiltViewModel
class CountryDetailsViewModel @Inject constructor(
    private val countryDetailsRepositoryImpl: CountryDetailsRepositoryImpl
) : ViewModel() {
    private val _countryProvince = MutableLiveData<Resource<List<Item>>>()
    val countryProvince: LiveData<Resource<List<Item>>>
        get() = _countryProvince

    fun fetchCountryProvince(countryId: Int) {
        // preventing calling api on orientation change
        if (!_countryProvince.value?.data.isNullOrEmpty()) {
            return
        }
        _countryProvince.postValue(Resource.loading(data = null))
        viewModelScope.launch {
            try {
                val data = countryDetailsRepositoryImpl.getCountryDetails(countryId)
                if (data.isNullOrEmpty())
                    _countryProvince.postValue(
                        (Resource.error(
                            data = null,
                            msg = "No province found"
                        ))
                    )
                else
                    _countryProvince.postValue(Resource.success(data))
            } catch (e: APIException) {
                _countryProvince.postValue(Resource.error(data = null, msg = e.message!!))
            } catch (e: NoInternetException) {
                _countryProvince.postValue((Resource.error(data = null, msg = e.message!!)))
            } catch (e: Exception) {
                _countryProvince.postValue(Resource.error(e.toString(), null))
            }
        }
    }
}