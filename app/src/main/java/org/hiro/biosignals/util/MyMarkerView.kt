package org.hiro.biosignals.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.github.mikephil.charting.utils.Utils
import org.hiro.biosignals.R

@SuppressLint("ViewConstructor")
class MyMarkerView(context: Context?, layoutResource: Int) : MarkerView(context, layoutResource) {
    private val tvContent: TextView = findViewById(R.id.tvContent)

    // runs every time the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    override fun refreshContent(e: Entry, highlight: Highlight) {
        val formatNumber: String? = if (e is CandleEntry) {
            Utils.formatNumber(e.high, 0, true)
        } else {
            Utils.formatNumber(e.y, 2, true) + " : " +
                    Utils.formatNumber(e.x, 2, true)
        }
        tvContent.text = formatNumber
        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF((-(width / 2)).toFloat(), (-height).toFloat())
    }

    init {
        tvContent.setTextColor(Color.BLACK)
    }
}