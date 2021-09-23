package net.pdev.ears

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BatteryData : ViewModel() {
    private val _batteryLevel: MutableLiveData<Float> by lazy {
        MutableLiveData<Float>(0F)
    }

    private val _batteryColor: MutableLiveData<UInt> by lazy {
        MutableLiveData<UInt>(0u)
    }

    fun getBatteryColor(): LiveData<UInt> {
        return _batteryColor
    }

    fun getBatteryLevel(): LiveData<Float> {
        return _batteryLevel
    }

    fun setBatteryLevel(level: Float) {
        _batteryLevel.value = level
    }
}