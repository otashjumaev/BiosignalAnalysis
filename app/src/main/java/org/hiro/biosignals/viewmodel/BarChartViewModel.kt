package org.hiro.biosignals.viewmodel

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import org.hiro.biosignals.util.BioSignal
import java.io.File

class BarChartViewModel(private val path: String) : ViewModel() {
    private var dataset: MutableList<Float>? = null
    val bioSignal = BioSignal(getDataSet())

    fun getDataSet(): MutableList<Float> {
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
        return dataset!!
    }

    fun updateSet(pos: Int): LineData {
        val set = LineDataSet(
            bioSignal.selectByOrder(pos, true).mapIndexed { index, fl ->
                Entry(index.toFloat(), fl)
            }, "DataSet1"
        )
        set.color = ColorTemplate.rgb("FF018786")
        set.setDrawCircles(false)
        set.lineWidth = 1f

        return LineData(set)
    }
}