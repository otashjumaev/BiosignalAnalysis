package org.hiro.biosignals.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import org.hiro.biosignals.R
import java.text.DecimalFormat

/**
 * Custom implementation of the MarkerView.
 *
 * @author Philipp Jahoda
 */
@SuppressLint("ViewConstructor")
class XYMarkerView(context: Context?) :
    MarkerView(context, R.layout.custom_marker_view) {
    private val tvContent: TextView = findViewById(R.id.tvContent)
    private val format: DecimalFormat = DecimalFormat("###.0")

    // runs every time the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    override fun refreshContent(e: Entry, highlight: Highlight) {
        tvContent.setTextColor(Color.BLACK)
        tvContent.text = format.format(e.y.toDouble()).toString()
        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF = MPPointF(-(width / 2f), -height.toFloat())

}