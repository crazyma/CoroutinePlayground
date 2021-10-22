package com.batu.coroutineplayground.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    val test = MutableLiveData<String>()

    fun loadSomething() {
        viewModelScope.launch {

        }
    }
}