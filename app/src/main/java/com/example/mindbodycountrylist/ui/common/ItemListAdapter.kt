package com.example.mindbodycountrylist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mindbodycountrylist.data.model.Item
import com.example.mindbodycountrylist.R
import com.example.mindbodycountrylist.databinding.CountryItemBinding

class ItemListAdapter(private val countryClickListener: OnCountryClickListener?) :
    RecyclerView.Adapter<CountryViewHolder>() {
    private val countryList = ArrayList<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: CountryItemBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.country_item,
            parent,
            false
        )
        return CountryViewHolder(binding)
    }

    override fun getItemCount() = this.countryList.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(this.countryList[position], countryClickListener)
    }

    fun setList(countries: List<Item>) {
        this.countryList.apply {
            clear()
            addAll(countries)
        }
    }

    fun clear() {
        this.countryList.clear()
        notifyDataSetChanged()
    }

    interface OnCountryClickListener {
        fun onCountryClicked(country: Item)
    }
}

class CountryViewHolder(val binding: CountryItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Item, listener: ItemListAdapter.OnCountryClickListener?) {

        binding.apply {
            country = item
            countryItemClick = listener
            executePendingBindings()
        }
    }
}
