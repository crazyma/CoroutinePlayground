package com.batu.coroutineplayground.ui.second

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SecondViewModel : ViewModel() {

    val test = MutableLiveData<String>()
    val test2 = MutableStateFlow<String?>(null)
    val test3 = MutableSharedFlow<String?>(replay = 0, extraBufferCapacity = 1)

    fun loadSomethingWithStateFlow() {
        viewModelScope.launch {
            Log.i("badu", "start load something")
            for(i in 1 .. 3) {
                for (i in 1..5) {
                    Log.d("badu", "$i second.")
                    delay(1000)
                }
                test2.value = "XD $i"
            }
        }
    }

    fun loadSomethingWithSharedFlow(){
        viewModelScope.launch(Dispatchers.IO) {
            Log.i("badu", "loadSomething")
            for(x in 1..5) {
                for (i in 1..3) {
                    Log.d("badu", "$i second.")
                    delay(1000)
                }
                test3.emit("loop $x")
            }
        }
    }
}