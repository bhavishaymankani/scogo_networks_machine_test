package com.bhavishaymankani.scogonetworksmachinetest.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bhavishaymankani.scogonetworksmachinetest.model.Coin
import com.bhavishaymankani.scogonetworksmachinetest.R
import com.bhavishaymankani.scogonetworksmachinetest.databinding.LayoutCoinBinding
import com.bhavishaymankani.scogonetworksmachinetest.fragment.BitcoinListFragment
import com.bhavishaymankani.scogonetworksmachinetest.util.AppConstants
import com.bumptech.glide.Glide


class BitcoinRVAdapter(val fragment: Fragment) : RecyclerView.Adapter<BitcoinRVAdapter.ViewHolder>() {

    var coins: List<Coin> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutCoinBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = coins.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvBitcoinName.text = coins[position].name
        loadLogo(holder.binding.ivLogo, position)
        holder.binding.root.setOnClickListener {
            (fragment as BitcoinListFragment).findNavController()
                .navigate(R.id.action_bitcoinListFragment_to_detailsFragment, Bundle()
                    .apply { putString(AppConstants.BITCOIN_ID, coins[position].id) })
        }
    }

    private fun loadLogo(ivLogo: ImageView, position: Int) {
        Glide
            .with(fragment)
            .load("https://static.coinpaprika.com/coin/${coins[position].id}/logo.png")
            .into(ivLogo)
    }

    inner class ViewHolder(val binding: LayoutCoinBinding) : RecyclerView.ViewHolder(binding.root)
}
