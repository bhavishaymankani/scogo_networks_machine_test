package com.bhavishaymankani.scogonetworksmachinetest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhavishaymankani.scogonetworksmachinetest.repository.AppRepository
import com.bhavishaymankani.scogonetworksmachinetest.model.CoinDetails
import com.bhavishaymankani.scogonetworksmachinetest.util.AppConstants
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {
    private val appRepository = AppRepository()

    private val _coinDetails = MutableLiveData<CoinDetails>()
    val coinDetails: LiveData<CoinDetails> = _coinDetails

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun getCoinDetails(coinId: String) = viewModelScope.launch {
        try {
            val response = appRepository.getCoinDetails(coinId)
            if (response.isSuccessful) {
                response.body()?.let {
                    _coinDetails.postValue(it)
                } ?: run {
                    _error.postValue(AppConstants.UNSUCCESSFUL_RESPONSE)
                }
            } else {
                _error.postValue(AppConstants.UNSUCCESSFUL_RESPONSE)
            }
        } catch (e: Exception) {
            _error.postValue("Exception: ${e.localizedMessage}")
        }
    }

    fun handleErrorShown() {
        _error.value = null
    }
}