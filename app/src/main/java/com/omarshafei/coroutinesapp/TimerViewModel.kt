package com.omarshafei.coroutinesapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TimerViewModel: ViewModel() {
    private val _timerStateFlow = MutableStateFlow<Int>(0)
    public val timerStateFlow = _timerStateFlow

    public fun startTimer() {
        viewModelScope.launch {
            val list = listOf<Int>(1,2,1,1,1,1,1,5)
            for(i in 1..5) {
                _timerStateFlow.emit(i)
                delay(1000)
            }
        }
    }
}