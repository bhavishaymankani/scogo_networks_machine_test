package com.bhavishaymankani.scogonetworksmachinetest.fragment

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bhavishaymankani.scogonetworksmachinetest.model.Coin
import com.bhavishaymankani.scogonetworksmachinetest.R
import com.bhavishaymankani.scogonetworksmachinetest.adapter.BitcoinRVAdapter
import com.bhavishaymankani.scogonetworksmachinetest.base.BaseFragment
import com.bhavishaymankani.scogonetworksmachinetest.databinding.FragmentBitcoinListBinding
import com.bhavishaymankani.scogonetworksmachinetest.viewmodel.BitcoinListViewModel

class BitcoinListFragment : BaseFragment() {

    private var _binding: FragmentBitcoinListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BitcoinListViewModel by viewModels()

    private var coins: List<Coin> = mutableListOf()
    private lateinit var bitcoinRVAdapter: BitcoinRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBitcoinListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        manageSearchBitcoin()
        manageBitcoinList()
        manageSwipeToRefresh()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun manageSearchBitcoin() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    bitcoinRVAdapter.coins = coins
                    bitcoinRVAdapter.coins = bitcoinRVAdapter.coins.filter { coin ->
                        coin.name.lowercase().contains(text.toString().lowercase())
                    }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }


    private fun manageBitcoinList() {
        bitcoinRVAdapter = BitcoinRVAdapter(this)
        binding.list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = bitcoinRVAdapter
            setHasFixedSize(true)
        }

        if (isInternetAvailable()) {
            showLoader()
            viewModel.getCoins()
        } else {
            retry()
        }
        viewModel.coins.observe(viewLifecycleOwner) {
            coins = it
            bitcoinRVAdapter.coins = coins
            binding.swiperefresh.isRefreshing = false
            dismissLoader()
            binding.etSearch.text.clear()
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                showAlertDialog(it) {dialog, _ -> dialog.dismiss()}
                viewModel.handleErrorShown()
                binding.swiperefresh.isRefreshing = false
                dismissLoader()
                binding.etSearch.text.clear()
            }
        }

    }

    private fun manageSwipeToRefresh() {
        binding.swiperefresh.setOnRefreshListener {
            getCoins()
        }
    }

    private fun getCoins() {
        if (isInternetAvailable()) {
            viewModel.getCoins()
        } else {
            retry()
        }
    }

    private fun retry() {
        showAlertDialog(getString(R.string.no_internet_connection), getString(R.string.retry)) { dialog, _ ->
            dialog.dismiss()
            getCoins()
        }
    }
}