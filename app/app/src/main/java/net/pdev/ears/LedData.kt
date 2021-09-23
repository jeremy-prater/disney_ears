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
    private val gain: MutableLiveData<Double> by lazy {
        MutableLiveData<Double>(0.2)
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

        for (index in 0..resizedData.size) {
            incomingData[index] =
                (resizedData[index].toDouble() * (1 / gain.value!!)).toInt().toUByte()
        }

        _ledRawData.value = incomingData
        _ledEarData.value = data
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

        for (index in 0..resizedData.size) {
            incomingData[index] =
                (resizedData[index].toDouble() * gain.value!!).toInt().toUByte()
        }

        _ledRawData.value = data
        _ledEarData.value = incomingData
    }
}
