package org.hiro.biosignals.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.charts.LineChart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.hiro.biosignals.R
import org.hiro.biosignals.databinding.HomeFragmentBinding
import org.hiro.biosignals.util.MyMarkerView
import org.hiro.biosignals.viewmodel.HomeViewModel
import org.hiro.biosignals.viewmodel.factory.HomeViewModelFactory


class HomeFragment : BaseFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate),
    AdapterView.OnItemSelectedListener {
    private lateinit var viewModel: HomeViewModel
    private lateinit var chart: LineChart
    private var lastSpinerItem = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        initSpinner()
        val path = arguments?.getString("FILE_PATH")!!
        viewModel = ViewModelProvider(this, HomeViewModelFactory(path))
            .get(HomeViewModel::class.java)
        chart = binding.chart1
        initChart()
    }

    private fun initLayout() {
        val displaymetrics = DisplayMetrics()
        getMain().windowManager.defaultDisplay.getMetrics(displaymetrics)
        val height: Int = displaymetrics.heightPixels
        val params = binding.layoutHF.layoutParams
        params.height = height - getStatusBarHeight() - 55
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
        val list = listOf("Analog", "FFT", "DCT")
        val adapter = ArrayAdapter(requireContext(), R.layout.item_spinner, list)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner1.adapter = adapter
        binding.spinner1.onItemSelectedListener = this
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

    private fun setData(type: Int = 0) {
        lifecycleScope.launch {
            progressOn()
            withContext(Dispatchers.Default) {
                chart.data = viewModel.getDataSet(type)
            }
            progressOff()
            chart.invalidate()
        }
    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (position != lastSpinerItem) {
            when (position) {
                0 -> setData()
                1 -> setData(1)
                2 -> setData(2)
                else -> throw IllegalArgumentException()
            }
            lastSpinerItem = position
        } else setData(0)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}