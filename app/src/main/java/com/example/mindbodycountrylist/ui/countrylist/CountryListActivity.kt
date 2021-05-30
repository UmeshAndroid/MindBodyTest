package com.example.mindbodycountrylist.ui.countrylist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mindbodycountrylist.data.model.Item
import com.example.mindbodycountrylist.R
import com.example.mindbodycountrylist.databinding.ActivityCountryListBinding
import com.example.mindbodycountrylist.ui.countrydetails.CountryDetailsActivity
import com.example.mindbodycountrylist.ui.ItemListAdapter
import com.example.mindbodycountrylist.utils.Status
import com.example.mindbodycountrylist.utils.gone
import com.example.mindbodycountrylist.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_country_list.*

@AndroidEntryPoint
class CountryListActivity : AppCompatActivity(), ItemListAdapter.OnCountryClickListener {
    private val countryListViewModel: CountryListViewModel by viewModels()
    private lateinit var itemListAdapter: ItemListAdapter
    private lateinit var binding: ActivityCountryListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = resources.getString(R.string.country_list)

        initRecyclerView()
        fetchData()
        observeData()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_refresh -> {
                fetchData()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun observeData() {
        countryListViewModel.countries.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    rvItems.visible()
                    layout_error.gone()
                    progress_circular.gone()
                    it.let {
                        itemListAdapter.setList(it.data!!)
                        itemListAdapter.notifyDataSetChanged()
                    }
                }
                Status.LOADING -> {
                    layout_error.gone()
                    progress_circular.visible()
                    rvItems.gone()
                }
                Status.ERROR -> {
                    layout_error.visible()
                    progress_circular.gone()
                    tvError.text = it.message
                    rvItems.gone()
                }
            }
        })
    }

    private fun initRecyclerView() {
        itemListAdapter = ItemListAdapter(this@CountryListActivity)

        binding.rvItems.apply {
            layoutManager = LinearLayoutManager(this@CountryListActivity)
            adapter = itemListAdapter
            setHasFixedSize(true)
        }

    }

    private fun fetchData() {
        countryListViewModel.fetchCountries()
    }

    // launch province list activity
    override fun onCountryClicked(country: Item) {
        val nextScreenIntent = Intent(this, CountryDetailsActivity::class.java).apply {
            putExtra(COUNTRY_NAME, country.name)
            putExtra(COUNTRY_ID, country.id)
        }
        startActivity(nextScreenIntent)
    }

    companion object {
        const val COUNTRY_NAME = "countryName"
        const val COUNTRY_ID = "countryId"
    }
}

