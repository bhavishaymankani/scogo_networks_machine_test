package com.bhavishaymankani.scogonetworksmachinetest.fragment

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bhavishaymankani.scogonetworksmachinetest.R
import com.bhavishaymankani.scogonetworksmachinetest.base.BaseFragment
import com.bhavishaymankani.scogonetworksmachinetest.databinding.FragmentDetailsBinding
import com.bhavishaymankani.scogonetworksmachinetest.util.AppConstants
import com.bhavishaymankani.scogonetworksmachinetest.viewmodel.DetailsViewModel
import com.bumptech.glide.Glide

class DetailsFragment : BaseFragment() {


    private val viewModel: DetailsViewModel by viewModels()

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.visibility = View.GONE

        getCoinDetails()
        manageCoinDetails()

        binding.tvBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun getCoinDetails() {
        if (isInternetAvailable()) {
            showLoader()
            arguments?.getString(AppConstants.BITCOIN_ID)?.let { viewModel.getCoinDetails(it) }
        } else {
            showAlertDialog(
                getString(R.string.no_internet_connection),
                getString(R.string.retry)
            ) { dialog, _ ->
                dialog.dismiss()
                getCoinDetails()
            }
        }
    }

    private fun manageCoinDetails() {
        viewModel.coinDetails.observe(viewLifecycleOwner) {
            binding.apply {
                tvBitcoinName.text = it.name
                tvSymbolValue.text = it.symbol
                tvRankValue.text = it.rank.toString()
                tvAboutUs.text = getString(R.string.about_us, it.name)
                tvDescription.text = it.description
                Glide.with(requireContext()).load(it.logo).into(binding.imageView)
            }
            dismissLoader()
            binding.root.visibility = View.VISIBLE
        }

        // Handle error
        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                showAlertDialog(it) { dialog, _ -> dialog.dismiss() }
                viewModel.handleErrorShown()
                dismissLoader()
            }
        }
    }
}