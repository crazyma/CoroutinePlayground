package com.batu.coroutineplayground.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    fun loadSomething(){
        viewModelScope.launch {

        }
    }

}