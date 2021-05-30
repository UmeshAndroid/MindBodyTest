package com.example.mindbodycountrylist.ui.countrylist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.mindbodycountrylist.TestCoroutineRule
import com.example.mindbodycountrylist.data.model.Item
import com.example.mindbodycountrylist.data.repository.CountriesRepositoryImpl
import com.example.mindbodycountrylist.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CountryListViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var apiObserver: Observer<Resource<List<Item>>>

    @Mock
    private lateinit var countriesRepositoryImpl: CountriesRepositoryImpl

    private lateinit var countryListViewModel: CountryListViewModel

    @Before
    fun setUp() {
        countryListViewModel = CountryListViewModel(countriesRepositoryImpl)
    }

    @Test
    fun `Test fetch country list successfully and list is empty`() {
        testCoroutineRule.runBlockingTest {
            Mockito.doReturn(emptyList<Item>())
                .`when`(countriesRepositoryImpl)
                .getCountries()

            countryListViewModel.countries.observeForever(apiObserver)
            countryListViewModel.fetchCountries()
            Mockito.verify(countriesRepositoryImpl).getCountries()
            Mockito.verify(apiObserver).onChanged(Resource.success(emptyList()))
            countryListViewModel.countries.removeObserver(apiObserver)

        }
    }

    @Test
    fun `Test fetch country list successfully and list is not empty`() {
        testCoroutineRule.runBlockingTest {
            Mockito.doReturn(getCountryListResponse())
                .`when`(countriesRepositoryImpl)
                .getCountries()

            countryListViewModel.countries.observeForever(apiObserver)
            countryListViewModel.fetchCountries()
            Mockito.verify(countriesRepositoryImpl).getCountries()
            Mockito.verify(apiObserver).onChanged(Resource.success(getCountryListResponse()))
            countryListViewModel.countries.removeObserver(apiObserver)

        }
    }

    @Test
    fun `test server error with message`() {
        testCoroutineRule.runBlockingTest {
            val errorMessage = "Getting error while loading countries"
            Mockito.doThrow(java.lang.RuntimeException(errorMessage))
                .`when`(countriesRepositoryImpl)
                .getCountries()
            countryListViewModel.countries.observeForever(apiObserver)
            countryListViewModel.fetchCountries()
            Mockito.verify(countriesRepositoryImpl).getCountries()
            Mockito.verify(apiObserver).onChanged(
                Resource.error(
                    RuntimeException(errorMessage).toString(),
                    null
                )
            )
            countryListViewModel.countries.removeObserver(apiObserver)
        }
    }
    private fun getCountryListResponse() =
        listOf(
            Item(
                id = 227,
                name = "UNITED STATES" ,
                code = "US",
                phoneCode = "1"
            ),
            Item(
                id = 4,
                name = "AFGHANISTAN" ,
                code = "AF",
                phoneCode = "93"
            )
        )

}