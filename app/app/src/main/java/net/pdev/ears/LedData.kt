package net.pdev.ears

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

@ExperimentalUnsignedTypes
class LedData : ViewModel() {
    private val maxSize = 3 * 96
    private val _ledEarData: MutableLiveData<UByteArray> by lazy {
        MutableLiveData<UByteArray>(UByteArray(maxSize))
    }
    private val _ledRawData: MutableLiveData<UByteArray> by lazy {
        MutableLiveData<UByteArray>(UByteArray(maxSize))
    }
    private val _gain: MutableLiveData<Double> by lazy {
        MutableLiveData<Double>(0.05)
    }

    fun getLEDData(): LiveData<UByteArray> {
        return _ledEarData
    }

    fun setLEDData(data: UByteArray) {
        var resizedData = data;
        if (data.size > (maxSize)) {
            resizedData = data.slice(0..maxSize).toUByteArray()
        }

        val incomingData = UByteArray(resizedData.size)

        for (index in resizedData.indices) {
            incomingData[index] =
                (resizedData[index].toDouble() * (1 / _gain.value!!)).toInt().toUByte()
        }

        _ledRawData.value = incomingData
        _ledEarData.value = data.toUByteArray()
    }

    fun getRawData(): LiveData<UByteArray> {
        return _ledRawData
    }

    fun setRawData(data: UByteArray) {
        var resizedData = data;
        if (data.size > (maxSize)) {
            resizedData = data.slice(0..maxSize).toUByteArray()
        }

        val incomingData = UByteArray(resizedData.size)

        for (index in resizedData.indices) {
            incomingData[index] =
                (resizedData[index].toDouble() * _gain.value!!).toInt().toUByte()
        }

        _ledRawData.value = data.toUByteArray()
        _ledEarData.value = incomingData
    }

    fun getBrightness() : LiveData<Double> {
        return _gain
    }

    fun setBrightness(brightness: Double) {
        _gain.value = brightness
    }
}
