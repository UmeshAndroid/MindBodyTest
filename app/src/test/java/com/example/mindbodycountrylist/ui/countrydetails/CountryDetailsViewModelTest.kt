package com.example.mindbodycountrylist.ui.countrydetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.mindbodycountrylist.TestCoroutineRule
import com.example.mindbodycountrylist.data.model.Item
import com.example.mindbodycountrylist.data.repository.CountryDetailsRepositoryImpl
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
class CountryDetailsViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var apiObserver: Observer<Resource<List<Item>>>

    @Mock
    private lateinit var countryDetailsRepositoryImpl: CountryDetailsRepositoryImpl

    private lateinit var countryDetailsViewModel: CountryDetailsViewModel

    @Before
    fun setUp() {
        countryDetailsViewModel = CountryDetailsViewModel(countryDetailsRepositoryImpl)
    }

    @Test
    fun `Test fetch country list successfully and list is empty`() {
        testCoroutineRule.runBlockingTest {
            Mockito.doReturn(emptyList<Item>())
                .`when`(countryDetailsRepositoryImpl)
                .getCountryDetails(1)

            countryDetailsViewModel.countryProvince.observeForever(apiObserver)
            countryDetailsViewModel.fetchCountryProvince(1)
            Mockito.verify(countryDetailsRepositoryImpl).getCountryDetails(1)
            Mockito.verify(apiObserver).onChanged(
                Resource.error(
                    "No province found",
                    null
                )
            )
            countryDetailsViewModel.countryProvince.removeObserver(apiObserver)
        }
    }

    @Test
    fun `Test fetch country list successfully and list is not empty`() {
        testCoroutineRule.runBlockingTest {
            Mockito.doReturn(getCountryDetailsResponse())
                .`when`(countryDetailsRepositoryImpl)
                .getCountryDetails(1)

            countryDetailsViewModel.countryProvince.observeForever(apiObserver)
            countryDetailsViewModel.fetchCountryProvince(1)
            Mockito.verify(countryDetailsRepositoryImpl).getCountryDetails(1)
            Mockito.verify(apiObserver)
                .onChanged(Resource.success(getCountryDetailsResponse()))
            countryDetailsViewModel.countryProvince.removeObserver(apiObserver)

        }
    }

    @Test
    fun `test server error with message`() {
        testCoroutineRule.runBlockingTest {
            val errorMessage = "Getting error while loading country province"
            Mockito.doThrow(java.lang.RuntimeException(errorMessage))
                .`when`(countryDetailsRepositoryImpl)
                .getCountryDetails(1)
            countryDetailsViewModel.countryProvince.observeForever(apiObserver)
            countryDetailsViewModel.fetchCountryProvince(1)
            Mockito.verify(countryDetailsRepositoryImpl).getCountryDetails(1)
            Mockito.verify(apiObserver).onChanged(
                Resource.error(
                    RuntimeException(errorMessage).toString(),
                    null
                )
            )
            countryDetailsViewModel.countryProvince.removeObserver(apiObserver)
        }
    }

    private fun getCountryDetailsResponse() =
        listOf(
            Item(
                id = 143,
                code = "AN",
                name = "Andaman and Nicobar Islands"
            ),
            Item(
                id = 144,
                code = "AP",
                name = "Andhra Pradesh"
            )
        )
}