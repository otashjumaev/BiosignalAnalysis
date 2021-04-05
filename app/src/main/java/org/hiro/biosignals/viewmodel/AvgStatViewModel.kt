package org.hiro.biosignals.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import org.hiro.biosignals.util.BioSignal
import java.io.File

class AvgStatViewModel(paths: MutableList<String?>) : ViewModel() {
    val data = arrayListOf<BarEntry>()
    private val bioSignals = arrayListOf<BioSignal>()

    init {
        paths.forEach {
            bioSignals.add(BioSignal(getDataSet(it)))
        }
        bioSignals.forEachIndexed { index, bioSignal ->
            data.add(BarEntry(index.toFloat(), bioSignal.calcAvg(0,false)))
        }
    }

    private fun getDataSet(path: String?): MutableList<Float> {
        val dataset = mutableListOf<Float>()
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
                    stat?.let { fl ->
                        dataset.add(fl)
                    }
                }
            }
        return dataset
    }

    fun updateSet(pos: Int) {
        data.clear()
        bioSignals.forEachIndexed { index, bioSignal ->
            data.add(BarEntry(index.toFloat(), bioSignal.calcAvg(pos,false)))
        }
    }
}
