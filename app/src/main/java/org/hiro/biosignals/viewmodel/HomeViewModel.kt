package org.hiro.biosignals.viewmodel

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import org.hiro.biosignals.util.Complex
import org.hiro.biosignals.util.DCT
import org.hiro.biosignals.util.FFT
import org.hiro.biosignals.util.Mapper
import java.io.File
import java.lang.Exception
import java.lang.IllegalArgumentException

class HomeViewModel(val path: String) : ViewModel() {

    private var dataset: MutableList<Float>? = null
    private var dataFFT: MutableList<Complex>? = null
    private var dataDCT: MutableList<Double>? = null

    init {
        initDataSet()
    }

    private fun initDataSet() {
        if (dataset == null) {
            dataset = mutableListOf()
            var stat: Float? = null
            var i = 0
            val f = File(path)
            if (f.exists())
                f.bufferedReader().forEachLine { m ->
                    if (m.isNotEmpty()) {
                        i++
                        try {
                            stat = m.split(" ")[1].toFloatOrNull()
                        } catch (e: Exception) {
                            Log.d("TAG", "BAR_CHART: d:$m i:$i")
                            Log.d("TAG", "BAR_CHART: ${e.message}")
                        }
                        stat?.let {
                            dataset!!.add(it)
                        }
                    }
                }
        }
    }

    fun getDataSet(type: Int = 0): LineData {
        val set1: LineDataSet = when (type) {
            0 -> LineDataSet(
                dataset?.mapIndexed { index, fl -> Entry(index.toFloat(), fl) },
                "DataSet1"
            )
            1 -> LineDataSet(
                calcFFT()?.mapIndexed { index, fl -> Entry(index.toFloat(), fl.abs().toFloat()) },
                "DataSet1"
            )
            2 -> LineDataSet(
                calcDCT()?.mapIndexed { index, fl -> Entry(index.toFloat(), fl.toFloat()) },
                "DataSet1"
            )
            else -> throw IllegalArgumentException()
        }

        set1.color = Color.DKGRAY
        set1.setDrawCircles(false)
        set1.lineWidth = 1f
        return LineData(set1)
    }


    fun calcFFT(): MutableList<Complex>? {
        if (dataFFT == null) {
            dataFFT = FFT.fft(Mapper.doubleToComplex(dataset ?: mutableListOf())).toMutableList()
        }
        return dataFFT
    }

    fun calcDCT(): MutableList<Double>? {
        if (dataDCT == null) {
            dataDCT = DCT.transform(Mapper.listToArray(dataset ?: mutableListOf())).toMutableList()
        }
        return dataDCT
    }


}

