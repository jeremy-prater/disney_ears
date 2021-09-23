package net.pdev.ears

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BatteryData : ViewModel() {
    private val _batteryLevel: MutableLiveData<Double> by lazy {
        MutableLiveData<Double>(0.0)
    }

    private val _batteryColor: MutableLiveData<UInt> by lazy {
        MutableLiveData<UInt>(0u)
    }

    fun getBatteryColor(): LiveData<UInt> {
        return _batteryColor
    }

    fun getBatteryLevel(): LiveData<Double> {
        return _batteryLevel
    }

    fun setBatteryLevel(level: Double) {
        _batteryLevel.value = level
    }
}