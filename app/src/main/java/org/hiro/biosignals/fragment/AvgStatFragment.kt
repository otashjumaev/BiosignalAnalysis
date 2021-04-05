package org.hiro.biosignals.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import org.hiro.biosignals.R
import org.hiro.biosignals.databinding.FragmentAvgStatBinding
import org.hiro.biosignals.util.BioSignal
import org.hiro.biosignals.util.XYMarkerView
import org.hiro.biosignals.viewmodel.AvgStatViewModel
import org.hiro.biosignals.viewmodel.factory.AvgStatViewModelFactory

class AvgStatFragment : BaseFragment<FragmentAvgStatBinding>(FragmentAvgStatBinding::inflate),
    AdapterView.OnItemSelectedListener {

    private lateinit var chart: BarChart
    private lateinit var viewModel: AvgStatViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        viewModel = ViewModelProvider(this, AvgStatViewModelFactory(getPaths()))
            .get(AvgStatViewModel::class.java)
        initBarChart()
        update(0)
        initSpinner()
    }

    private fun initLayout() {
        val displaymetrics = DisplayMetrics()
        getMain().windowManager.defaultDisplay.getMetrics(displaymetrics)
        val height: Int = displaymetrics.heightPixels
        binding.layout1.layoutParams.height = height - getStatusBarHeight() - 55
    }

    fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }


    private fun initSpinner() {
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.item_spinner,
            BioSignal.getProperties().map { it.first }
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter
        binding.spinner.onItemSelectedListener = this
    }

    private fun getPaths(): MutableList<String?> {
        val pathCount = arguments?.getInt("FILE_COUNT") ?: 0
        val paths = mutableListOf<String?>()
        repeat(pathCount) {
            paths.add(arguments?.getString("FILE_PATH$it"))
        }
        return paths
    }

    private fun initBarChart() {
        chart = binding.avgStatBarChart
        chart.description.isEnabled = false
        chart.setPinchZoom(false)
        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chart.xAxis.setDrawGridLines(false)
        chart.xAxis.granularity = 1f // only intervals of 1 day

        chart.axisLeft.setLabelCount(8, false)
        chart.axisLeft.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        chart.axisLeft.axisMinimum = 0f
        chart.axisLeft.setDrawGridLines(false)

        chart.axisRight.setDrawGridLines(false)
        chart.axisRight.setLabelCount(8, false)
        chart.axisRight.axisMinimum = 0f
        chart.axisRight.setDrawGridLines(false)

        chart.legend.isEnabled = false

        val mv = XYMarkerView(requireContext())
        mv.chartView = chart

        chart.marker = mv
    }

    private fun update(position: Int) {
        viewModel.updateSet(position)
        chart.data = BarData(BarDataSet(viewModel.data, "AVG").apply {
            setColors(
                ColorTemplate.rgb("FF018786")
            )
        })
        if (chart.data != null && chart.data.dataSetCount > 0) {
            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()
        } else {
            chart.setFitBars(true)
            chart.data.setValueTextColor(Color.BLUE)
            chart.invalidate()
        }

        chart.animateY(1500)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        update(position)
        binding.barDescText.text = BioSignal.getProperties()[position].second
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

}