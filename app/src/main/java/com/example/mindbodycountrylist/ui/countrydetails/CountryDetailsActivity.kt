package com.example.mindbodycountrylist.ui.countrydetails

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mindbodycountrylist.R
import com.example.mindbodycountrylist.databinding.ActivityCountryListBinding
import com.example.mindbodycountrylist.ui.ItemListAdapter
import com.example.mindbodycountrylist.ui.countrylist.CountryListActivity.Companion.COUNTRY_ID
import com.example.mindbodycountrylist.ui.countrylist.CountryListActivity.Companion.COUNTRY_NAME
import com.example.mindbodycountrylist.utils.Status
import com.example.mindbodycountrylist.utils.gone
import com.example.mindbodycountrylist.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_country_list.*

@AndroidEntryPoint
class CountryDetailsActivity : AppCompatActivity() {

    private val countryDetailsViewModel: CountryDetailsViewModel by viewModels()
    private lateinit var itemListAdapter: ItemListAdapter
    private lateinit var binding: ActivityCountryListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.apply {
            title = intent.getStringExtra(COUNTRY_NAME)
            setDisplayHomeAsUpEnabled(true)
        }

        initRecyclerView()
        fetchData()
        observeData()
    }

    private fun observeData() {
        countryDetailsViewModel.countryProvince.observe(this, Observer {
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
                    rvItems.gone()
                    layout_error.gone()
                    progress_circular.visible()
                }
                Status.ERROR -> {
                    rvItems.gone()
                    layout_error.visible()
                    progress_circular.gone()
                    tvError.text = it.message
                }
            }
        })
    }

    private fun fetchData() {
        countryDetailsViewModel.fetchCountryProvince(
            countryId = intent.getIntExtra(
                COUNTRY_ID,
                0
            )
        )
    }

    private fun initRecyclerView() {
        itemListAdapter = ItemListAdapter(null)

        binding.rvItems.apply {
            layoutManager = LinearLayoutManager(this@CountryDetailsActivity)
            adapter = itemListAdapter
            setHasFixedSize(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
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
}