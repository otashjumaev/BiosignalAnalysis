package org.hiro.biosignals.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.hiro.biosignals.R
import org.hiro.biosignals.databinding.BarChartFragmentBinding
import org.hiro.biosignals.util.BioSignal
import org.hiro.biosignals.util.MyMarkerView
import org.hiro.biosignals.util.XYMarkerView
import org.hiro.biosignals.viewmodel.BarChartViewModel
import org.hiro.biosignals.viewmodel.factory.BarChartViewModelFactory


class BarChartFragment : BaseFragment<BarChartFragmentBinding>(BarChartFragmentBinding::inflate),
    AdapterView.OnItemSelectedListener {

    private lateinit var chart: LineChart
    private lateinit var viewModel: BarChartViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        val path = arguments?.getString("FILE_PATH")!!
        viewModel = ViewModelProvider(this, BarChartViewModelFactory(path))
            .get(BarChartViewModel::class.java)
        chart = binding.lineChart
        initSpinner()
        initChart()
    }

    private fun initLayout() {
        val displaymetrics = DisplayMetrics()
        getMain().windowManager.defaultDisplay.getMetrics(displaymetrics)
        val height: Int = displaymetrics.heightPixels
        binding.layout1.layoutParams.height = height - getStatusBarHeight() - 55
    }

    private fun getStatusBarHeight(): Int {
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


    private fun initChart() {
        progressOn()
        chart.setTouchEnabled(true)
        chart.setDrawGridBackground(true)
        val mv = MyMarkerView(requireContext(), R.layout.custom_marker_view)
        mv.chartView = chart
        chart.marker = mv
        chart.isDragEnabled = true
        chart.setScaleEnabled(true)
        chart.setPinchZoom(false)
        chart.animateX(1000)
        chart.description.isEnabled = false
        chart.axisLeft.setDrawGridLines(false)
        chart.axisRight.setDrawGridLines(false)
        chart.xAxis.setDrawGridLines(false)
        progressOff()
        chart.invalidate()
    }

    private fun update(pos: Int) {
        if (chart.data != null && chart.data.dataSetCount > 0) {
            chart.data = viewModel.updateSet(pos)
            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()
        } else {
            chart.data = viewModel.updateSet(pos)
            chart.data.setValueTextColor(Color.BLUE)
            chart.invalidate()
        }
        chart.animateX(2000)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        update(position)
        binding.barDescText.text = BioSignal.getProperties()[position].second
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}
