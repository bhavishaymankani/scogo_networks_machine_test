package com.bhavishaymankani.scogonetworksmachinetest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhavishaymankani.scogonetworksmachinetest.repository.AppRepository
import com.bhavishaymankani.scogonetworksmachinetest.model.Coin
import com.bhavishaymankani.scogonetworksmachinetest.util.AppConstants
import kotlinx.coroutines.launch

class BitcoinListViewModel : ViewModel() {

    private val appRepository = AppRepository()

    private val _coins = MutableLiveData<List<Coin>>()
    val coins: LiveData<List<Coin>> = _coins

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun getCoins() = viewModelScope.launch {
        try {
            val response = appRepository.getCoins()
            if (response.isSuccessful) {
                response.body()?.let {
                    _coins.postValue(it)
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