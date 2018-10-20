package me.sparker0i.mvvmsample.ui.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class CurrencyViewModelFactory @Inject constructor(
        private val currencyViewModel: CurrencyViewModel
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrencyViewModel::class.java))
            return currencyViewModel as T
        throw IllegalArgumentException("Unknown Class Name")
    }
}